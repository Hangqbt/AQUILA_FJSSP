import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        Hybrid Aquila Optimizer (HAO) for FJSSP.
        Incorporates the Island Model (Sub-Population Quarantine) and Hyper-Aggressive Local Search.
        """
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        # --- ISLAND MODEL PARAMETERS ---
        self.num_islands = 3
        self.island_size = self.N // self.num_islands
        self.migration_interval = 25  # How often the islands share their Alpha eagles

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

        # Aquila Parameters (Native continuous mode)
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

    def boundary(self, X_vector):
        """Boundary logic applied to a 1D vector safely"""
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

    def local_search(self, best_cont_vector, current_best_fit):
        """
        Hyper-Aggressive Discrete Local Search.
        Forces the overall Alpha eagle to attempt 200 physical schedule permutations.
        """
        new_cont = np.copy(best_cont_vector)

        for _ in range(200):
            temp_cont = np.copy(new_cont)

            # 33% chance to swap operations, 33% chance to change machine, 33% to do BOTH
            rand_choice = random.random()

            if rand_choice < 0.33 or rand_choice > 0.66:
                # Neighborhood 1: OS Swap
                idx1 = random.randint(self.T0, self.D - 1)
                idx2 = random.randint(self.T0, self.D - 1)
                temp_cont[idx1], temp_cont[idx2] = temp_cont[idx2], temp_cont[idx1]

            if rand_choice >= 0.33:
                # Neighborhood 2: MS Mutation
                idx_m = random.randint(0, self.T0 - 1)
                max_m = self.Feasible_Machines_Count[idx_m] - 1
                if max_m > 0:
                    temp_cont[idx_m] = random.randint(0, max_m) + random.random()

            temp_fit = self.evaluate_fitness(temp_cont)

            if temp_fit < current_best_fit:
                new_cont = np.copy(temp_cont)
                current_best_fit = temp_fit

        return new_cont, current_best_fit

    def optimize(self):
        X = self.init_population()
        XNEW = np.copy(X)
        fitness = np.zeros(self.N)

        # --- ISLAND MODEL TRACKING ---
        Island_BEST = np.zeros((self.num_islands, self.D))
        Island_Bestfitval = np.full(self.num_islands, 1E+200)

        Global_BEST = np.zeros(self.D)
        Global_Bestfitval = 1E+200

        convergence_history = []

        # Initial Evaluation & Sorting into Islands
        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X[i])
            island_idx = i // self.island_size

            # Check if it's the Alpha of its specific Island
            if fitness[i] < Island_Bestfitval[island_idx]:
                Island_Bestfitval[island_idx] = fitness[i]
                Island_BEST[island_idx] = np.copy(X[i])

            # Check if it's the Alpha of the entire swarm
            if fitness[i] < Global_Bestfitval:
                Global_Bestfitval = fitness[i]
                Global_BEST = np.copy(X[i])

        convergence_history.append(Global_Bestfitval)
        iter_num = 1

        while iter_num <= self.Maxiter:
            # Dynamic AO variables
            G2 = 2.0 * random.random() - 1.0
            G1 = 2.0 * (1.0 - (iter_num / self.Maxiter))
            r0 = math.ceil(random.random() * 20.0)

            r = r0 + self.u_val * self.D1
            phi = -self.omega * self.D1 + self.phi0
            xx = r * np.cos(phi)
            yy = r * np.sin(phi)
            QF = (iter_num) ** ((2.0 * random.random() - 1.0) / ((1.0 - self.Maxiter) ** 2))

            # --- PROCESS EACH ISLAND INDEPENDENTLY ---
            for k in range(self.num_islands):
                start_idx = k * self.island_size
                end_idx = (k + 1) * self.island_size if k < self.num_islands - 1 else self.N

                # The sub-population mean (eagles only look at their island)
                XMEAN2D = np.mean(X[start_idx:end_idx], axis=0)

                for i in range(start_idx, end_idx):
                    if iter_num <= (0.666 * self.Maxiter):
                        if random.random() < 0.5:
                            xmean = np.mean(X[i])
                            for j in range(self.D):
                                # Eagles chase their ISLAND'S Best, not the Global Best
                                XNEW[i, j] = Island_BEST[k][j] * (1.0 - iter_num / self.Maxiter) + (
                                            xmean - Island_BEST[k][j]) * random.random()
                        else:
                            levy_vals = self.levy()
                            NRAND = random.randint(start_idx, end_idx - 1)  # Pick a friend from the same island
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

                    # Greedy Selection
                    if fitnessnew < fitness[i]:
                        X[i] = np.copy(XNEW[i])
                        fitness[i] = fitnessnew

                        # Update Island Alpha
                        if fitness[i] < Island_Bestfitval[k]:
                            Island_Bestfitval[k] = fitness[i]
                            Island_BEST[k] = np.copy(X[i])

                            # Update Global Alpha
                            if fitness[i] < Global_Bestfitval:
                                Global_Bestfitval = fitness[i]
                                Global_BEST = np.copy(X[i])

            # --- THE HYBRIDIZATION: Local Search on the Global Alpha ---
            Global_BEST, Global_Bestfitval = self.local_search(Global_BEST, Global_Bestfitval)

            # To ensure the swarm actually learns from the discrete local search,
            # we overwrite the weakest eagle in the entire swarm with the buffed Alpha
            worst_overall_idx = np.argmax(fitness)
            X[worst_overall_idx] = np.copy(Global_BEST)
            fitness[worst_overall_idx] = Global_Bestfitval

            # Recalculate Island_BEST for the island that just received the upgraded eagle
            island_idx = worst_overall_idx // self.island_size
            if Global_Bestfitval < Island_Bestfitval[island_idx]:
                Island_Bestfitval[island_idx] = Global_Bestfitval
                Island_BEST[island_idx] = np.copy(Global_BEST)

            # --- MIGRATION PHASE (Ring Topology) ---
            if iter_num % self.migration_interval == 0:
                for k in range(self.num_islands):
                    # Island k sends its Alpha to Island k+1
                    target_island = (k + 1) % self.num_islands
                    target_start = target_island * self.island_size
                    target_end = (
                                             target_island + 1) * self.island_size if target_island < self.num_islands - 1 else self.N

                    # Identify the weakest eagle in the target island
                    worst_local_idx = np.argmax(fitness[target_start:target_end])
                    worst_global_idx = target_start + worst_local_idx

                    # The target island's worst eagle is replaced by the sender island's Alpha
                    X[worst_global_idx] = np.copy(Island_BEST[k])
                    fitness[worst_global_idx] = Island_Bestfitval[k]

            convergence_history.append(Global_Bestfitval)
            iter_num += 1

        best_discrete_CHS = self.discretize(Global_BEST)
        return best_discrete_CHS, Global_Bestfitval, convergence_history