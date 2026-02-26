import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size=30, Maxiter=500):
        """
        Hybrid Aquila Optimizer (HAO) for FJSSP.
        Includes a Discrete Local Search operator to break flat ROV gradients.
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
                feasible_count = sum(1 for t in machine_times if t != 9999)
                self.Feasible_Machines_Count.append(feasible_count)

        # Define Lower and Upper Bounds
        self.Lower = np.zeros(self.D)
        self.Upper = np.zeros(self.D)

        for i in range(self.T0):
            self.Lower[i] = 0.0
            self.Upper[i] = self.Feasible_Machines_Count[i] - 1e-5

        for i in range(self.T0, self.D):
            self.Lower[i] = -10.0
            self.Upper[i] = 10.0

        # Aquila Parameters
        self.alpha = 0.1
        self.delta = 0.1
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
        X = np.zeros((self.N, self.D))
        for i in range(self.N):
            for j in range(self.D):
                X[i, j] = self.Lower[j] + (self.Upper[j] - self.Lower[j]) * random.random()
        return X

    def discretize(self, X_cont):
        # 1. Map MS
        MS_cont = X_cont[:self.T0]
        MS_discrete = np.floor(MS_cont).astype(int)
        for i in range(self.T0):
            MS_discrete[i] = np.clip(MS_discrete[i], 0, self.Feasible_Machines_Count[i] - 1)

        # 2. Map OS (ROV Mapping)
        OS_cont = X_cont[self.T0:]
        ranked_indices = np.argsort(OS_cont)
        OS_discrete = np.zeros(self.T0, dtype=int)
        for i in range(self.T0):
            OS_discrete[ranked_indices[i]] = self.standard_OS_list[i]

        return np.hstack((MS_discrete, OS_discrete))

    def evaluate_fitness(self, X_cont):
        CHS_discrete = self.discretize(X_cont)
        decoder = Decode(self.J, self.Processing_time, self.M_num)
        return decoder.decode(CHS_discrete, self.T0)

    def boundary(self, X):
        for i in range(self.N):
            for j in range(self.D):
                if X[i, j] < self.Lower[j] or X[i, j] > self.Upper[j]:
                    X[i, j] = self.Lower[j] + (self.Upper[j] - self.Lower[j]) * random.random()
        return X

    def levy(self):
        beta = 1.5
        num = math.gamma(1 + beta) * math.sin(math.pi * beta / 2)
        den = math.gamma((1 + beta) / 2) * beta * (2 ** ((beta - 1) / 2))
        sigma = (num / den) ** (1 / beta)

        u = np.random.randn(self.D) * sigma
        v = np.random.randn(self.D)
        step = u / (np.abs(v) ** (1.0 / beta))
        return step

    def local_search(self, best_cont_vector, current_best_fit):
        """
        THE HYBRID UPGRADE:
        Injects domain-specificn discrete knowledge ito the continuous swarm.
        Mutates the best eagle to break flat gradients.
        """
        new_cont = np.copy(best_cont_vector)

        # Give the Alpha Eagle 5 attempts to find a discrete shortcut
        for _ in range(5):
            temp_cont = np.copy(new_cont)

            if random.random() < 0.5:
                # Neighborhood 1: OS Swap (Swap two jobs in the sequence)
                idx1 = random.randint(self.T0, self.D - 1)
                idx2 = random.randint(self.T0, self.D - 1)

                # Brilliant math trick: Swapping the continuous decimals identically
                # swaps their discrete ROV rank without breaking the algorithm!
                temp_cont[idx1], temp_cont[idx2] = temp_cont[idx2], temp_cont[idx1]
            else:
                # Neighborhood 2: MS Mutation (Change a machine assignment)
                idx_m = random.randint(0, self.T0 - 1)
                max_machine_idx = self.Feasible_Machines_Count[idx_m] - 1

                if max_machine_idx > 0:
                    new_machine_discrete = random.randint(0, max_machine_idx)
                    # Assign a random float inside the integer bracket to keep it continuous
                    temp_cont[idx_m] = new_machine_discrete + random.random()

            # Evaluate the discrete mutation
            temp_fit = self.evaluate_fitness(temp_cont)

            # If the shortcut lowered the cycle time, keep it!
            if temp_fit < current_best_fit:
                new_cont = np.copy(temp_cont)
                current_best_fit = temp_fit

        return new_cont, current_best_fit

    def optimize(self):
        X = self.init_population()
        XNEW = np.copy(X)
        fitness = np.zeros(self.N)
        fitnessnew = np.zeros(self.N)

        BEST = np.zeros(self.D)
        Bestfitval = 1E+200

        convergence_history = []

        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X[i])
            if fitness[i] < Bestfitval:
                Bestfitval = fitness[i]
                BEST = np.copy(X[i])

        convergence_history.append(Bestfitval)
        iter_num = 1

        while iter_num <= self.Maxiter:
            X = self.boundary(X)

            for i in range(self.N):
                fitness[i] = self.evaluate_fitness(X[i])
                if fitness[i] < Bestfitval:
                    Bestfitval = fitness[i]
                    BEST = np.copy(X[i])

            G2 = 2.0 * random.random() - 1.0
            G1 = 2.0 * (1.0 - (iter_num / self.Maxiter))
            r0 = math.ceil(random.random() * 20.0)

            r = r0 + self.u_val * self.D1
            phi = -self.omega * self.D1 + self.phi0
            xx = r * np.cos(phi)
            yy = r * np.sin(phi)

            QF = (iter_num) ** ((2.0 * random.random() - 1.0) / ((1.0 - self.Maxiter) ** 2))

            # Reverted to standard 66% boundary with normal alpha/delta
            if iter_num <= (0.666 * self.Maxiter):
                for i in range(self.N):
                    if random.random() < 0.5:
                        xmean = np.mean(X[i])
                        for j in range(self.D):
                            XNEW[i, j] = BEST[j] * (1.0 - iter_num / self.Maxiter) + (xmean - BEST[j]) * random.random()
                    else:
                        levy_vals = self.levy()
                        NRAND = int(math.floor(self.N * random.random()))
                        for j in range(self.D):
                            XNEW[i, j] = BEST[j] * levy_vals[j] + X[NRAND, j] + (yy[j] - xx[j]) * random.random()

                    fitnessnew[i] = self.evaluate_fitness(XNEW[i])
                    if fitnessnew[i] < fitness[i]:
                        X[i] = np.copy(XNEW[i])
                        fitness[i] = fitnessnew[i]
            else:
                for i in range(self.N):
                    if random.random() < 0.5:
                        XMEAN2D = np.mean(X, axis=0)
                        for j in range(self.D):
                            XNEW[i, j] = (BEST[j] - XMEAN2D[j]) * self.alpha - random.random() + \
                                         (self.Lower[j] + (
                                                     self.Upper[j] - self.Lower[j]) * random.random()) * self.delta
                    else:
                        levy_vals = self.levy()
                        for j in range(self.D):
                            XNEW[i, j] = QF * BEST[j] - (G2 * X[i, j] * random.random()) - \
                                         G1 * levy_vals[j] + random.random() * G2

                    fitnessnew[i] = self.evaluate_fitness(XNEW[i])
                    if fitnessnew[i] < fitness[i]:
                        X[i] = np.copy(XNEW[i])
                        fitness[i] = fitnessnew[i]

            # --- HYBRIDIZATION INJECTION ---
            # The Alpha eagle runs a neighborhood search and forces the continuous vector to learn from it
            BEST, Bestfitval = self.local_search(BEST, Bestfitval)

            convergence_history.append(Bestfitval)
            iter_num += 1

        best_discrete_CHS = self.discretize(BEST)
        return best_discrete_CHS, Bestfitval, convergence_history