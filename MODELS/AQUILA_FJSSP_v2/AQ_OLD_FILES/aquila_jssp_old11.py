import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        The "Final Boss" Hybrid Aquila Optimizer (HAO) for FJSSP.
        Architecture: Adaptive Islands + Stagnation-Triggered EOBL + Tabu Search.
        """
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        # Calculate total operations (T0) - This is our "Complexity Metric"
        self.T0 = sum(J.values())
        self.D = 2 * self.T0  # Dimension of continuous search space (MS + OS)

        # =================================================================
        # --- THE ADAPTIVE ISLAND CONFIGURATION ---
        # =================================================================
        if self.T0 < 100:
            self.num_islands = 1  # Small puzzle: 1 massive swarm
        elif self.T0 < 200:
            self.num_islands = 2  # Medium puzzle: 2 sub-populations
        else:
            self.num_islands = 3  # Massive puzzle: 3 islands

        self.island_size = self.N // self.num_islands
        self.migration_interval = 25

        # Fairly distribute the Tabu Search budget (~200 evaluations total per iteration)
        # Tabu budget = steps * 10 neighbors
        self.ts_steps_per_island = max(1, 20 // self.num_islands)
        # =================================================================

        # Identify the number of feasible machines for each operation
        self.Feasible_Machines_Count = []
        for job_idx, ops in enumerate(Processing_time):
            for op_idx, machine_times in enumerate(ops):
                feasible_count = sum(1 for t in machine_times if t != 9999)
                self.Feasible_Machines_Count.append(feasible_count)

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
        for j in range(self.D):
            if X_vector[j] < self.Lower[j] or X_vector[j] > self.Upper[j]:
                X_vector[j] = self.Lower[j] + (self.Upper[j] - self.Lower[j]) * random.random()
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

    def eobl(self, best_cont_vector, current_best_fit):
        """
        Elite Opposition-Based Learning (EOBL) - The Emergency Escape Hatch
        """
        opposite_vector = np.zeros(self.D)
        for j in range(self.D):
            opposite_vector[j] = self.Lower[j] + self.Upper[j] - best_cont_vector[j]

        opposite_vector = self.boundary(opposite_vector)
        mirror_fit = self.evaluate_fitness(opposite_vector)

        if mirror_fit < current_best_fit:
            return opposite_vector, mirror_fit
        else:
            return best_cont_vector, current_best_fit

    def local_search(self, best_cont_vector, current_best_fit, num_steps):
        """
        Discrete Tabu Search (TS) - The Scalpel
        """
        current_cont = np.copy(best_cont_vector)
        best_overall_cont = np.copy(best_cont_vector)
        best_overall_fit = current_best_fit

        tabu_list = []
        tabu_tenure = 5

        for _ in range(num_steps):
            neighborhood = []

            for _ in range(10):  # 10 neighbors per step
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
                        if len(tabu_list) > tabu_tenure: tabu_list.pop(0)
                    move_accepted = True
                    break
                elif signature and signature not in tabu_list:
                    current_cont = np.copy(neighbor_cont)
                    tabu_list.append(signature)
                    if len(tabu_list) > tabu_tenure: tabu_list.pop(0)
                    move_accepted = True
                    break

            if not move_accepted and neighborhood:
                current_cont = np.copy(neighborhood[0][0])

        return best_overall_cont, best_overall_fit

    def optimize(self):
        X = self.init_population()
        XNEW = np.copy(X)
        fitness = np.zeros(self.N)

        Island_BEST = np.zeros((self.num_islands, self.D))
        Island_Bestfitval = np.full(self.num_islands, 1E+200)

        # --- NEW: STAGNATION TRACKING FOR EOBL ---
        Island_Stagnation = np.zeros(self.num_islands)

        Global_BEST = np.zeros(self.D)
        Global_Bestfitval = 1E+200

        convergence_history = []

        # Initial Evaluation
        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X[i])
            island_idx = min(i // self.island_size, self.num_islands - 1)

            if fitness[i] < Island_Bestfitval[island_idx]:
                Island_Bestfitval[island_idx] = fitness[i]
                Island_BEST[island_idx] = np.copy(X[i])

            if fitness[i] < Global_Bestfitval:
                Global_Bestfitval = fitness[i]
                Global_BEST = np.copy(X[i])

        convergence_history.append(Global_Bestfitval)
        iter_num = 1

        while iter_num <= self.Maxiter:
            G2 = 2.0 * random.random() - 1.0
            G1 = 2.0 * (1.0 - (iter_num / self.Maxiter))
            r0 = math.ceil(random.random() * 20.0)
            r = r0 + self.u_val * self.D1
            phi = -self.omega * self.D1 + self.phi0
            xx = r * np.cos(phi)
            yy = r * np.sin(phi)
            QF = (iter_num) ** ((2.0 * random.random() - 1.0) / ((1.0 - self.Maxiter) ** 2))

            for k in range(self.num_islands):
                start_idx = k * self.island_size
                end_idx = (k + 1) * self.island_size if k < self.num_islands - 1 else self.N
                XMEAN2D = np.mean(X[start_idx:end_idx], axis=0)

                # Record the island's best fit BEFORE this iteration
                previous_island_fit = Island_Bestfitval[k]

                for i in range(start_idx, end_idx):
                    if iter_num <= (0.666 * self.Maxiter):
                        if random.random() < 0.5:
                            xmean = np.mean(X[i])
                            for j in range(self.D):
                                XNEW[i, j] = Island_BEST[k][j] * (1.0 - iter_num / self.Maxiter) + (
                                            xmean - Island_BEST[k][j]) * random.random()
                        else:
                            levy_vals = self.levy()
                            NRAND = random.randint(start_idx, end_idx - 1)
                            for j in range(self.D):
                                XNEW[i, j] = Island_BEST[k][j] * levy_vals[j] + X[NRAND, j] + (
                                            yy[j] - xx[j]) * random.random()
                    else:
                        if random.random() < 0.5:
                            for j in range(self.D):
                                XNEW[i, j] = (Island_BEST[k][j] - XMEAN2D[j]) * self.alpha - random.random() + \
                                             (self.Lower[j] + (
                                                         self.Upper[j] - self.Lower[j]) * random.random()) * self.delta
                        else:
                            levy_vals = self.levy()
                            for j in range(self.D):
                                XNEW[i, j] = QF * Island_BEST[k][j] - (G2 * X[i, j] * random.random()) - \
                                             G1 * levy_vals[j] + random.random() * G2

                    XNEW[i] = self.boundary(XNEW[i])
                    fitnessnew = self.evaluate_fitness(XNEW[i])

                    if fitnessnew < fitness[i]:
                        X[i] = np.copy(XNEW[i])
                        fitness[i] = fitnessnew
                        if fitness[i] < Island_Bestfitval[k]:
                            Island_Bestfitval[k] = fitness[i]
                            Island_BEST[k] = np.copy(X[i])

                # =========================================================
                # --- STAGNATION TRIGGER: EOBL ESCAPE HATCH ---
                # =========================================================
                if Island_Bestfitval[k] >= previous_island_fit:
                    Island_Stagnation[k] += 1
                else:
                    Island_Stagnation[k] = 0  # Reset if improved

                if Island_Stagnation[k] >= 15:
                    Island_BEST[k], Island_Bestfitval[k] = self.eobl(Island_BEST[k], Island_Bestfitval[k])
                    Island_Stagnation[k] = 0  # Reset after teleporting
                # =========================================================

                # --- ALWAYS DIG DEEP WITH TABU SEARCH ---
                Island_BEST[k], Island_Bestfitval[k] = self.local_search(Island_BEST[k], Island_Bestfitval[k],
                                                                         self.ts_steps_per_island)

                # Global Update
                if Island_Bestfitval[k] < Global_Bestfitval:
                    Global_Bestfitval = Island_Bestfitval[k]
                    Global_BEST = np.copy(Island_BEST[k])

                # Replace worst eagle in island with buffed Alpha
                worst_local_idx = np.argmax(fitness[start_idx:end_idx])
                worst_global_idx = start_idx + worst_local_idx
                X[worst_global_idx] = np.copy(Island_BEST[k])
                fitness[worst_global_idx] = Island_Bestfitval[k]

            # --- MIGRATION PHASE ---
            if self.num_islands > 1 and iter_num % self.migration_interval == 0:
                for k in range(self.num_islands):
                    target_island = (k + 1) % self.num_islands
                    target_start = target_island * self.island_size
                    target_end = (
                                             target_island + 1) * self.island_size if target_island < self.num_islands - 1 else self.N

                    worst_local_idx = np.argmax(fitness[target_start:target_end])
                    worst_global_idx = target_start + worst_local_idx
                    X[worst_global_idx] = np.copy(Island_BEST[k])
                    fitness[worst_global_idx] = Island_Bestfitval[k]

            convergence_history.append(Global_Bestfitval)
            iter_num += 1

        best_discrete_CHS = self.discretize(Global_BEST)
        return best_discrete_CHS, Global_Bestfitval, convergence_history