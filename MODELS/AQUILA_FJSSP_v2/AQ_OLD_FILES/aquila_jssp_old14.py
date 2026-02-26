import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        Original Aquila Optimizer (AO) + Ultra-Lightweight Tabu Search.
        Designed specifically to keep computational time massively below GA.
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

        # Aquila Parameters (From original architecture)
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
        MS_cont = X_cont[:self.T0]
        MS_discrete = np.floor(MS_cont).astype(int)
        for i in range(self.T0):
            MS_discrete[i] = np.clip(MS_discrete[i], 0, self.Feasible_Machines_Count[i] - 1)

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

    def boundary(self, X_vector):
        """Boundary logic applied safely"""
        for i in range(self.N):
            for j in range(self.D):
                if X_vector[i, j] < self.Lower[j] or X_vector[i, j] > self.Upper[j]:
                    X_vector[i, j] = self.Lower[j] + (self.Upper[j] - self.Lower[j]) * random.random()
        return X_vector

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
        Ultra-Lightweight Tabu Search.
        Budget: 5 steps * 5 neighbors = 25 evaluations.
        """
        current_cont = np.copy(best_cont_vector)
        best_overall_cont = np.copy(best_cont_vector)
        best_overall_fit = current_best_fit

        tabu_list = []
        tabu_tenure = 3

        for _ in range(5):  # Only 5 steps
            neighborhood = []

            for _ in range(5):  # Only 5 neighbors
                temp_cont = np.copy(current_cont)
                move_signature = ""

                if random.random() < 0.5:
                    idx1 = random.randint(self.T0, self.D - 1)
                    idx2 = random.randint(self.T0, self.D - 1)
                    temp_cont[idx1], temp_cont[idx2] = temp_cont[idx2], temp_cont[idx1]
                    move_signature = f"OS_{min(idx1, idx2)}_{max(idx1, idx2)}"
                else:
                    idx_m = random.randint(0, self.T0 - 1)
                    max_m = self.Feasible_Machines_Count[idx_m] - 1
                    if max_m > 0:
                        new_m = random.randint(0, max_m)
                        temp_cont[idx_m] = new_m + random.random()
                        move_signature = f"MS_{idx_m}_{new_m}"

                temp_fit = self.evaluate_fitness(temp_cont)
                neighborhood.append((temp_cont, temp_fit, move_signature))

            neighborhood.sort(key=lambda x: x[1])

            move_accepted = False
            for neighbor_cont, neighbor_fit, signature in neighborhood:
                if neighbor_fit < best_overall_fit:
                    current_cont = np.copy(neighbor_cont)
                    best_overall_cont = np.copy(neighbor_cont)
                    best_overall_fit = neighbor_fit

                    if signature:
                        tabu_list.append(signature)
                        if len(tabu_list) > tabu_tenure:
                            tabu_list.pop(0)
                    move_accepted = True
                    break

                elif signature and signature not in tabu_list:
                    current_cont = np.copy(neighbor_cont)
                    tabu_list.append(signature)
                    if len(tabu_list) > tabu_tenure:
                        tabu_list.pop(0)
                    move_accepted = True
                    break

            if not move_accepted and neighborhood:
                current_cont = np.copy(neighborhood[0][0])

        return best_overall_cont, best_overall_fit

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

            # --- ORIGINAL 4 STAGES ---
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

                        if fitnessnew[i] < Bestfitval:
                            Bestfitval = fitnessnew[i]
                            BEST = np.copy(XNEW[i])
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

                        if fitnessnew[i] < Bestfitval:
                            Bestfitval = fitnessnew[i]
                            BEST = np.copy(XNEW[i])

            # --- INTERMITTENT LIGHTWEIGHT TABU ---
            # Only runs once every 10 iterations to save massive amounts of CPU time
            if iter_num % 10 == 0:
                BEST, Bestfitval = self.local_search(BEST, Bestfitval)

            convergence_history.append(Bestfitval)
            iter_num += 1

        best_discrete_CHS = self.discretize(BEST)
        return best_discrete_CHS, Bestfitval, convergence_history