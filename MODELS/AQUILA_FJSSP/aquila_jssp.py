import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size=50, Maxiter=100):
        """
        Adapted Aquila Optimizer for FJSSP.
        """
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        # Calculate total operations (T0)
        self.T0 = sum(J.values())
        self.D = 2 * self.T0  # Dimension of continuous search space (MS + OS)

        # Identify the number of feasible machines for each operation to set MS Bounds
        self.Feasible_Machines_Count = []
        for job_idx, ops in enumerate(Processing_time):
            for op_idx, machine_times in enumerate(ops):
                # Count machines that don't have 9999 as processing time
                feasible_count = sum(1 for t in machine_times if t != 9999)
                self.Feasible_Machines_Count.append(feasible_count)

        # Define Lower and Upper Bounds for the continuous space
        self.Lower = np.zeros(self.D)
        self.Upper = np.zeros(self.D)

        # Bounds for MS (Machine Selection): 0 to (Feasible Count - epsilon)
        for i in range(self.T0):
            self.Lower[i] = 0.0
            self.Upper[i] = self.Feasible_Machines_Count[i] - 1e-5

        # Bounds for OS (Operation Sequence): Arbitrary continuous space [-10, 10]
        for i in range(self.T0, self.D):
            self.Lower[i] = -10.0
            self.Upper[i] = 10.0

        # Aquila Parameters (from Java implementation)
        self.alpha = 0.5
        self.delta = 0.5
        self.u_val = 0.0265
        self.omega = 0.005
        self.phi0 = 3.0 * math.pi / 2.0
        self.D1 = np.arange(1, self.D + 1)

        # Build standard OS list for mapping
        self.standard_OS_list = []
        for job_id, num_ops in self.J.items():
            self.standard_OS_list.extend([job_id - 1] * num_ops)
        self.standard_OS_list = np.array(self.standard_OS_list)

    def init_population(self):
        """Randomly initialize the continuous population within bounds"""
        X = np.zeros((self.N, self.D))
        for i in range(self.N):
            for j in range(self.D):
                X[i, j] = self.Lower[j] + (self.Upper[j] - self.Lower[j]) * random.random()
        return X

    def discretize(self, X_cont):
        """
        Translates continuous Aquila vector into FJSSP discrete chromosome [MS + OS]
        """
        # 1. Map MS (First half) to discrete machine indices
        MS_cont = X_cont[:self.T0]
        MS_discrete = np.floor(MS_cont).astype(int)
        # Safety clip to ensure indices are valid
        for i in range(self.T0):
            MS_discrete[i] = np.clip(MS_discrete[i], 0, self.Feasible_Machines_Count[i] - 1)

        # 2. Map OS (Second half) to valid job sequence via Ranked Order Value (ROV)
        OS_cont = X_cont[self.T0:]
        ranked_indices = np.argsort(OS_cont)
        OS_discrete = np.zeros(self.T0, dtype=int)
        for i in range(self.T0):
            OS_discrete[ranked_indices[i]] = self.standard_OS_list[i]

        # Return combined discrete chromosome exactly as Decode.py expects
        return np.hstack((MS_discrete, OS_discrete))

    def evaluate_fitness(self, X_cont):
        """Discretizes the vector and runs it through the FJSSP Decoder"""
        CHS_discrete = self.discretize(X_cont)
        decoder = Decode(self.J, self.Processing_time, self.M_num)
        return decoder.decode(CHS_discrete, self.T0)

    def boundary(self, X):
        """Keeps eagles from flying outside the problem bounds"""
        for i in range(self.N):
            for j in range(self.D):
                if X[i, j] < self.Lower[j] or X[i, j] > self.Upper[j]:
                    X[i, j] = self.Lower[j] + (self.Upper[j] - self.Lower[j]) * random.random()
        return X

    def levy(self):
        """Calculates Mantegna's Levy Flight"""
        beta = 1.5
        num = math.gamma(1 + beta) * math.sin(math.pi * beta / 2)
        den = math.gamma((1 + beta) / 2) * beta * (2 ** ((beta - 1) / 2))
        sigma = (num / den) ** (1 / beta)

        u = np.random.randn(self.D) * sigma
        v = np.random.randn(self.D)
        step = u / (np.abs(v) ** (1.0 / beta))
        return step

    def optimize(self):
        """Main Loop faithfully mirroring the Java implementation"""
        X = self.init_population()
        XNEW = np.copy(X)
        fitness = np.zeros(self.N)
        fitnessnew = np.zeros(self.N)

        BEST = np.zeros(self.D)
        Bestfitval = 1E+200  # Initialized to infinity

        # Array to track convergence history
        convergence_history = []

        # Initial evaluation
        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X[i])
            if fitness[i] < Bestfitval:
                Bestfitval = fitness[i]
                BEST = np.copy(X[i])

        # Record initial best fitness
        convergence_history.append(Bestfitval)

        iter_num = 1

        while iter_num <= self.Maxiter:
            X = self.boundary(X)

            # Recalculate fitness after boundary correction
            for i in range(self.N):
                fitness[i] = self.evaluate_fitness(X[i])
                if fitness[i] < Bestfitval:
                    Bestfitval = fitness[i]
                    BEST = np.copy(X[i])

            # Aquila Control Parameters (From Java)
            G2 = 2.0 * random.random() - 1.0
            G1 = 2.0 * (1.0 - (iter_num / self.Maxiter))
            r0 = math.ceil(random.random() * 20.0)

            # Spiral Math
            r = r0 + self.u_val * self.D1
            phi = -self.omega * self.D1 + self.phi0
            xx = r * np.cos(phi)
            yy = r * np.sin(phi)

            QF = (iter_num) ** ((2.0 * random.random() - 1.0) / ((1.0 - self.Maxiter) ** 2))

            # --- THE 4 STAGES ---
            if iter_num <= (0.4 * self.Maxiter):
                for i in range(self.N):
                    if random.random() < 0.5:
                        # Stage 1: Expanded Exploration
                        xmean = np.mean(X[i])
                        for j in range(self.D):
                            XNEW[i, j] = BEST[j] * (1.0 - iter_num / self.Maxiter) + (xmean - BEST[j]) * random.random()
                    else:
                        # Stage 2: Narrowed Exploration
                        levy_vals = self.levy()
                        NRAND = int(math.floor(self.N * random.random()))
                        for j in range(self.D):
                            XNEW[i, j] = BEST[j] * levy_vals[j] + X[NRAND, j] + (yy[j] - xx[j]) * random.random()

                    # Apply greedy selection
                    fitnessnew[i] = self.evaluate_fitness(XNEW[i])
                    if fitnessnew[i] < fitness[i]:
                        X[i] = np.copy(XNEW[i])
                        fitness[i] = fitnessnew[i]
            else:
                for i in range(self.N):
                    if random.random() < 0.5:
                        # Stage 3: Expanded Exploitation
                        XMEAN2D = np.mean(X, axis=0)
                        for j in range(self.D):
                            XNEW[i, j] = (BEST[j] - XMEAN2D[j]) * self.alpha - random.random() + \
                                         (self.Lower[j] + (
                                                 self.Upper[j] - self.Lower[j]) * random.random()) * self.delta
                    else:
                        # Stage 4: Narrowed Exploitation
                        levy_vals = self.levy()
                        for j in range(self.D):
                            XNEW[i, j] = QF * BEST[j] - (G2 * X[i, j] * random.random()) - \
                                         G1 * levy_vals[j] + random.random() * G2

                    # Apply greedy selection
                    fitnessnew[i] = self.evaluate_fitness(XNEW[i])
                    if fitnessnew[i] < fitness[i]:
                        X[i] = np.copy(XNEW[i])
                        fitness[i] = fitnessnew[i]

            # Track the best cycle time at the end of this iteration
            convergence_history.append(Bestfitval)
            iter_num += 1

        # Return the discrete form of the final BEST, the best makespan, and the historical data
        best_discrete_CHS = self.discretize(BEST)
        return best_discrete_CHS, Bestfitval, convergence_history