import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        Native Discrete Aquila Optimizer (ND-AO).
        Inspired by Set-Based PSO discrete operations. No ROV mapping. No continuous math.
        """
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        self.T0 = sum(J.values())
        self.D = 2 * self.T0

        # Feasible machine boundaries for discrete mutation
        self.Feasible_Machines_Count = []
        for job_idx, ops in enumerate(Processing_time):
            for op_idx, machine_times in enumerate(ops):
                feasible_count = sum(1 for t in machine_times if t != 9999)
                self.Feasible_Machines_Count.append(feasible_count)

        # Standard OS list for initialization and crossover validation
        self.standard_OS_list = []
        for job_id, num_ops in self.J.items():
            self.standard_OS_list.extend([job_id - 1] * num_ops)
        self.standard_OS_list = np.array(self.standard_OS_list)

    def init_discrete_population(self):
        """Initializes exact discrete MS and OS arrays. No decimals."""
        X_MS = np.zeros((self.N, self.T0), dtype=int)
        X_OS = np.zeros((self.N, self.T0), dtype=int)
        for i in range(self.N):
            for j in range(self.T0):
                X_MS[i, j] = random.randint(0, self.Feasible_Machines_Count[j] - 1)

            os_copy = self.standard_OS_list.copy()
            np.random.shuffle(os_copy)
            X_OS[i] = os_copy
        return X_MS, X_OS

    def evaluate_fitness(self, MS, OS):
        """Directly evaluates discrete arrays."""
        CHS_discrete = np.hstack((MS, OS))
        decoder = Decode(self.J, self.Processing_time, self.M_num)
        return decoder.decode(CHS_discrete, self.T0)

    def pox_crossover(self, OS1, OS2):
        """
        Precedence Operation Crossover (Discrete Set Merging).
        Perfectly preserves valid factory job sequences.
        """
        jobs = list(self.J.keys())
        num_jobs = random.randint(1, len(jobs) - 1)
        selected_jobs = set(random.sample(jobs, num_jobs))
        selected_jobs = {j - 1 for j in selected_jobs}  # 0-indexed

        new_OS = np.full(self.T0, -1)

        # Inherit from Parent 1 (Alpha)
        for idx, job in enumerate(OS1):
            if job in selected_jobs:
                new_OS[idx] = job

        # Fill remainder from Parent 2
        os2_idx = 0
        for idx in range(self.T0):
            if new_OS[idx] == -1:
                while OS2[os2_idx] in selected_jobs:
                    os2_idx += 1
                new_OS[idx] = OS2[os2_idx]
                os2_idx += 1
        return new_OS

    def mutate_ms(self, ms, num_mutations):
        """Discrete Machine Selection Mutation"""
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            max_m = self.Feasible_Machines_Count[idx] - 1
            if max_m > 0:
                ms[idx] = random.randint(0, max_m)

    def swap_os(self, os_arr, num_swaps):
        """Discrete Operation Sequence Swap"""
        for _ in range(num_swaps):
            idx1, idx2 = random.sample(range(self.T0), 2)
            os_arr[idx1], os_arr[idx2] = os_arr[idx2], os_arr[idx1]

    def discrete_levy_burst(self):
        """Replaces continuous Levy flight with a discrete burst of swaps."""
        r = random.random()
        if r < 0.7:
            return random.randint(1, 2)
        elif r < 0.95:
            return random.randint(3, 5)
        else:
            return random.randint(6, 12)

    def local_search(self, best_MS, best_OS, current_best_fit):
        """Intermittent Lightweight Tabu working natively on discrete sets."""
        current_MS = np.copy(best_MS)
        current_OS = np.copy(best_OS)

        best_overall_MS = np.copy(best_MS)
        best_overall_OS = np.copy(best_OS)
        best_overall_fit = current_best_fit

        tabu_list = []
        tabu_tenure = 3

        for _ in range(5):  # 5 Steps
            neighborhood = []

            for _ in range(5):  # 5 Neighbors
                temp_MS = np.copy(current_MS)
                temp_OS = np.copy(current_OS)
                move_signature = ""

                if random.random() < 0.5:
                    idx1, idx2 = random.sample(range(self.T0), 2)
                    temp_OS[idx1], temp_OS[idx2] = temp_OS[idx2], temp_OS[idx1]
                    move_signature = f"OS_{min(idx1, idx2)}_{max(idx1, idx2)}"
                else:
                    idx_m = random.randint(0, self.T0 - 1)
                    max_m = self.Feasible_Machines_Count[idx_m] - 1
                    if max_m > 0:
                        new_m = random.randint(0, max_m)
                        temp_MS[idx_m] = new_m
                        move_signature = f"MS_{idx_m}_{new_m}"

                temp_fit = self.evaluate_fitness(temp_MS, temp_OS)
                neighborhood.append((temp_MS, temp_OS, temp_fit, move_signature))

            neighborhood.sort(key=lambda x: x[2])

            move_accepted = False
            for n_MS, n_OS, neighbor_fit, signature in neighborhood:
                if neighbor_fit < best_overall_fit:
                    current_MS, current_OS = np.copy(n_MS), np.copy(n_OS)
                    best_overall_MS, best_overall_OS = np.copy(n_MS), np.copy(n_OS)
                    best_overall_fit = neighbor_fit

                    if signature:
                        tabu_list.append(signature)
                        if len(tabu_list) > tabu_tenure: tabu_list.pop(0)
                    move_accepted = True
                    break

                elif signature and signature not in tabu_list:
                    current_MS, current_OS = np.copy(n_MS), np.copy(n_OS)
                    tabu_list.append(signature)
                    if len(tabu_list) > tabu_tenure: tabu_list.pop(0)
                    move_accepted = True
                    break

            if not move_accepted and neighborhood:
                current_MS, current_OS = np.copy(neighborhood[0][0]), np.copy(neighborhood[0][1])

        return best_overall_MS, best_overall_OS, best_overall_fit

    def optimize(self):
        X_MS, X_OS = self.init_discrete_population()
        fitness = np.zeros(self.N)

        BEST_MS = np.zeros(self.T0, dtype=int)
        BEST_OS = np.zeros(self.T0, dtype=int)
        Bestfitval = 1E+200

        convergence_history = []

        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X_MS[i], X_OS[i])
            if fitness[i] < Bestfitval:
                Bestfitval = fitness[i]
                BEST_MS, BEST_OS = np.copy(X_MS[i]), np.copy(X_OS[i])

        convergence_history.append(Bestfitval)
        iter_num = 1

        while iter_num <= self.Maxiter:

            # Form an Elite Pool to simulate the continuous "Mean" knowledge
            sorted_indices = np.argsort(fitness)
            elite_idx = random.choice(sorted_indices[:max(1, self.N // 5)])
            Elite_MS = X_MS[elite_idx]
            Elite_OS = X_OS[elite_idx]

            for i in range(self.N):
                MS_new = np.copy(X_MS[i])
                OS_new = np.copy(X_OS[i])

                # --- NATIVE DISCRETE AQUILA STAGES ---
                if iter_num <= (0.666 * self.Maxiter):
                    if random.random() < 0.5:
                        # Stage 1: Expanded Exploration (Learn from Elite)
                        Pc = 1.0 - (iter_num / self.Maxiter)
                        for j in range(self.T0):
                            MS_new[j] = BEST_MS[j] if random.random() < Pc else Elite_MS[j]
                        OS_new = self.pox_crossover(BEST_OS if random.random() < Pc else Elite_OS, X_OS[i])
                        self.mutate_ms(MS_new, 1)  # Slight mutation
                    else:
                        # Stage 2: Narrowed Exploration (Levy Burst)
                        rand_idx = random.randint(0, self.N - 1)
                        MS_new = np.copy(X_MS[rand_idx])
                        OS_new = np.copy(BEST_OS)
                        burst = self.discrete_levy_burst()
                        self.mutate_ms(MS_new, burst)
                        self.swap_os(OS_new, burst)
                else:
                    if random.random() < 0.5:
                        # Stage 3: Expanded Exploitation (Blend Best & Elite)
                        for j in range(self.T0):
                            if BEST_MS[j] == Elite_MS[j]:
                                MS_new[j] = BEST_MS[j]
                            else:
                                MS_new[j] = Elite_MS[j] if random.random() < 0.2 else BEST_MS[j]
                        OS_new = self.pox_crossover(BEST_OS, X_OS[i])
                    else:
                        # Stage 4: Narrowed Exploitation (The Swoop)
                        MS_new = np.copy(BEST_MS)
                        OS_new = np.copy(BEST_OS)
                        self.mutate_ms(MS_new, 1)  # Only 1 precise tweak
                        self.swap_os(OS_new, 1)

                # Evaluate New Discrete Schedule
                fit_new = self.evaluate_fitness(MS_new, OS_new)
                if fit_new < fitness[i]:
                    X_MS[i], X_OS[i] = np.copy(MS_new), np.copy(OS_new)
                    fitness[i] = fit_new

                    if fit_new < Bestfitval:
                        Bestfitval = fit_new
                        BEST_MS, BEST_OS = np.copy(MS_new), np.copy(OS_new)

            # --- Intermittent Lightweight Tabu ---
            if iter_num % 10 == 0:
                BEST_MS, BEST_OS, Bestfitval = self.local_search(BEST_MS, BEST_OS, Bestfitval)

            convergence_history.append(Bestfitval)
            iter_num += 1

        best_discrete_CHS = np.hstack((BEST_MS, BEST_OS))
        return best_discrete_CHS, Bestfitval, convergence_history