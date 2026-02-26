import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        Native Discrete Aquila Optimizer (ND-AO) + Critical Machine Heuristic.
        Uses targeted Tabu Search to mathematically offload the bottleneck machine.
        """
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        self.T0 = sum(J.values())
        self.D = 2 * self.T0

        # Feasible machine boundaries and mapping for the Heuristic
        self.Feasible_Machines_Count = []
        self.op_mapping = []
        self.valid_machines_map = []

        for job_idx, ops in enumerate(Processing_time):
            for op_idx, machine_times in enumerate(ops):
                feasible_count = sum(1 for t in machine_times if t != 9999)
                self.Feasible_Machines_Count.append(feasible_count)

                # Store mappings to instantly calculate bottleneck loads later
                self.op_mapping.append((job_idx, op_idx))
                valid_m = [m for m, t in enumerate(machine_times) if t != 9999]
                self.valid_machines_map.append(valid_m)

        self.standard_OS_list = []
        for job_id, num_ops in self.J.items():
            self.standard_OS_list.extend([job_id - 1] * num_ops)
        self.standard_OS_list = np.array(self.standard_OS_list)

    def init_discrete_population(self):
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
        CHS_discrete = np.hstack((MS, OS))
        decoder = Decode(self.J, self.Processing_time, self.M_num)
        return decoder.decode(CHS_discrete, self.T0)

    def pox_crossover(self, OS1, OS2):
        jobs = list(self.J.keys())
        num_jobs = random.randint(1, len(jobs) - 1)
        selected_jobs = set(random.sample(jobs, num_jobs))
        selected_jobs = {j - 1 for j in selected_jobs}

        new_OS = np.full(self.T0, -1)
        for idx, job in enumerate(OS1):
            if job in selected_jobs:
                new_OS[idx] = job

        os2_idx = 0
        for idx in range(self.T0):
            if new_OS[idx] == -1:
                while OS2[os2_idx] in selected_jobs:
                    os2_idx += 1
                new_OS[idx] = OS2[os2_idx]
                os2_idx += 1
        return new_OS

    def mutate_ms(self, ms, num_mutations):
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            max_m = self.Feasible_Machines_Count[idx] - 1
            if max_m > 0:
                ms[idx] = random.randint(0, max_m)

    def swap_os(self, os_arr, num_swaps):
        for _ in range(num_swaps):
            idx1, idx2 = random.sample(range(self.T0), 2)
            os_arr[idx1], os_arr[idx2] = os_arr[idx2], os_arr[idx1]

    def discrete_levy_burst(self):
        r = random.random()
        if r < 0.7:
            return random.randint(1, 2)
        elif r < 0.95:
            return random.randint(3, 5)
        else:
            return random.randint(6, 12)

    def get_bottleneck_ops(self, MS):
        """
        HEURISTIC ENGINE: Calculates the exact processing load on every machine
        based on the current MS array and returns the operations assigned to the bottleneck.
        """
        machine_loads = np.zeros(self.M_num)
        op_to_machine = np.zeros(self.T0, dtype=int)

        for i in range(self.T0):
            job_idx, op_idx = self.op_mapping[i]
            selected_m_idx = MS[i]
            actual_m = self.valid_machines_map[i][selected_m_idx]

            time_taken = self.Processing_time[job_idx][op_idx][actual_m]
            machine_loads[actual_m] += time_taken
            op_to_machine[i] = actual_m

        bottleneck_m = np.argmax(machine_loads)
        bottleneck_ops = np.where(op_to_machine == bottleneck_m)[0]
        return bottleneck_ops

    def local_search(self, best_MS, best_OS, current_best_fit, num_steps):
        current_MS = np.copy(best_MS)
        current_OS = np.copy(best_OS)

        best_overall_MS = np.copy(best_MS)
        best_overall_OS = np.copy(best_OS)
        best_overall_fit = current_best_fit

        tabu_list = []
        tabu_tenure = 5

        # Identify the critical operations before we start digging
        bottleneck_ops = self.get_bottleneck_ops(current_MS)

        for _ in range(num_steps):
            neighborhood = []

            for _ in range(10):
                temp_MS = np.copy(current_MS)
                temp_OS = np.copy(current_OS)
                move_signature = ""

                # 50% chance to do Targeted Heuristic MS offloading, 50% for standard OS swap
                if random.random() < 0.5 and len(bottleneck_ops) > 0:
                    # TARGETED HEURISTIC: Force an operation off the bottleneck machine
                    idx_m = random.choice(bottleneck_ops)
                    max_m = self.Feasible_Machines_Count[idx_m] - 1
                    if max_m > 0:
                        new_m = random.randint(0, max_m)
                        # Ensure it actually changes machines if possible
                        while new_m == temp_MS[idx_m] and max_m > 0:
                            new_m = random.randint(0, max_m)

                        temp_MS[idx_m] = new_m
                        move_signature = f"MS_{idx_m}_{new_m}"
                else:
                    # Discrete OS Swap (Blind sequence adjustment)
                    idx1, idx2 = random.sample(range(self.T0), 2)
                    temp_OS[idx1], temp_OS[idx2] = temp_OS[idx2], temp_OS[idx1]
                    move_signature = f"OS_{min(idx1, idx2)}_{max(idx1, idx2)}"

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

                    # Recalculate bottlenecks if we successfully broke the record
                    bottleneck_ops = self.get_bottleneck_ops(current_MS)
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

        stagnation_counter = 0
        convergence_history = []

        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X_MS[i], X_OS[i])
            if fitness[i] < Bestfitval:
                Bestfitval = fitness[i]
                BEST_MS, BEST_OS = np.copy(X_MS[i]), np.copy(X_OS[i])

        convergence_history.append(Bestfitval)
        iter_num = 1

        while iter_num <= self.Maxiter:
            previous_best_fit = Bestfitval

            sorted_indices = np.argsort(fitness)
            elite_idx = random.choice(sorted_indices[:max(1, self.N // 5)])
            Elite_MS = X_MS[elite_idx]
            Elite_OS = X_OS[elite_idx]

            for i in range(self.N):
                MS_new = np.copy(X_MS[i])
                OS_new = np.copy(X_OS[i])

                if iter_num <= (0.666 * self.Maxiter):
                    if random.random() < 0.5:
                        Pc = 1.0 - (iter_num / self.Maxiter)
                        for j in range(self.T0):
                            MS_new[j] = BEST_MS[j] if random.random() < Pc else Elite_MS[j]
                        OS_new = self.pox_crossover(BEST_OS if random.random() < Pc else Elite_OS, X_OS[i])
                        self.mutate_ms(MS_new, 1)
                    else:
                        rand_idx = random.randint(0, self.N - 1)
                        MS_new = np.copy(X_MS[rand_idx])
                        OS_new = np.copy(BEST_OS)
                        burst = self.discrete_levy_burst()
                        self.mutate_ms(MS_new, burst)
                        self.swap_os(OS_new, burst)
                else:
                    if random.random() < 0.5:
                        for j in range(self.T0):
                            if BEST_MS[j] == Elite_MS[j]:
                                MS_new[j] = BEST_MS[j]
                            else:
                                MS_new[j] = Elite_MS[j] if random.random() < 0.2 else BEST_MS[j]
                        OS_new = self.pox_crossover(BEST_OS, X_OS[i])
                    else:
                        MS_new = np.copy(BEST_MS)
                        OS_new = np.copy(BEST_OS)
                        self.mutate_ms(MS_new, 1)
                        self.swap_os(OS_new, 1)

                fit_new = self.evaluate_fitness(MS_new, OS_new)
                if fit_new < fitness[i]:
                    X_MS[i], X_OS[i] = np.copy(MS_new), np.copy(OS_new)
                    fitness[i] = fit_new

                    if fit_new < Bestfitval:
                        Bestfitval = fit_new
                        BEST_MS, BEST_OS = np.copy(MS_new), np.copy(OS_new)

            if Bestfitval >= previous_best_fit:
                stagnation_counter += 1
            else:
                stagnation_counter = 0

            tabu_steps = 5

            if stagnation_counter >= 15:
                tabu_steps = 40  # INCREASED DEEP DRILL BUDGET
                stagnation_counter = 0

            BEST_MS, BEST_OS, Bestfitval = self.local_search(BEST_MS, BEST_OS, Bestfitval, tabu_steps)

            convergence_history.append(Bestfitval)
            iter_num += 1

        best_discrete_CHS = np.hstack((BEST_MS, BEST_OS))
        return best_discrete_CHS, Bestfitval, convergence_history