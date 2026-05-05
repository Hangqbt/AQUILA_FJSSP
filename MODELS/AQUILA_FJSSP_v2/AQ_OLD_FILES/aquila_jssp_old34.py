import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        Native Discrete Aquila Optimizer (ND-AO) + Early-Exit Sledgehammer Tabu.
        Features Pure Logarithmic Decay, 80/20 Hybrid GREEDY MG, and Phase-Aware Early Stopping.
        """
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        self.T0 = sum(J.values())
        self.D = 2 * self.T0

        self.Feasible_Machines_Count = []
        for job_idx, ops in enumerate(Processing_time):
            for op_idx, machine_times in enumerate(ops):
                feasible_count = sum(1 for t in machine_times if t != 9999)
                self.Feasible_Machines_Count.append(feasible_count)

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

    # =========================================================================
    # GREEDY HYBRID TABU SEARCH (Fast Swaps + GREEDY Critical Path)
    # =========================================================================
    def local_search(self, best_MS, best_OS, current_best_fit, num_steps, neighbors_per_step=10):
        current_MS = np.copy(best_MS)
        current_OS = np.copy(best_OS)

        best_overall_MS = np.copy(best_MS)
        best_overall_OS = np.copy(best_OS)
        best_overall_fit = current_best_fit

        tabu_list = []
        tabu_tenure = 7
        tabu_patience = 0
        patience_limit = 15

        for step in range(num_steps):
            neighborhood = []

            use_mg_heuristic = (random.random() < 0.20)
            cp = []

            if use_mg_heuristic:
                decoder = Decode(self.J, self.Processing_time, self.M_num)
                _ = decoder.decode(np.hstack((current_MS, current_OS)), self.T0)
                cp = decoder.get_critical_path()

            for _ in range(neighbors_per_step):
                temp_MS = np.copy(current_MS)
                temp_OS = np.copy(current_OS)
                move_signature = ""

                if use_mg_heuristic and cp and len(cp) > 1:
                    c_job, c_op, c_mach = random.choice(cp)

                    if random.random() < 0.5:
                        # GREEDY MACHINE REASSIGNMENT
                        # Find the machine with the shortest processing time for this specific operation!
                        flat_idx = 0
                        for k in range(1, c_job + 1):
                            flat_idx += self.J[k]
                        flat_idx += c_op

                        # Get processing times for this job/op across all machines
                        p_times = self.Processing_time[c_job][c_op]
                        # Filter out infeasible machines (9999) and the current bottleneck machine
                        valid_machines = [m for m, t in enumerate(p_times) if t != 9999 and m != c_mach]

                        if valid_machines:
                            # Pick the fastest alternative machine!
                            best_m = min(valid_machines, key=lambda m: p_times[m])

                            # Convert absolute machine index to relative index for the MS array
                            # Count how many feasible machines exist before best_m
                            relative_idx = sum(1 for m in range(best_m) if p_times[m] != 9999)

                            temp_MS[flat_idx] = relative_idx
                            move_signature = f"CP_MS_{flat_idx}_{relative_idx}"
                    else:
                        # GREEDY SEQUENCE SWAP
                        same_mach_ops = [x for x in cp if x[2] == c_mach and x != (c_job, c_op, c_mach)]
                        if same_mach_ops:
                            target = random.choice(same_mach_ops)
                            t_job, t_op = target[0], target[1]

                            def get_os_idx(os_arr, j_idx, o_idx):
                                count = -1
                                for i, val in enumerate(os_arr):
                                    if val == j_idx:
                                        count += 1
                                        if count == o_idx: return i
                                return -1

                            idx1 = get_os_idx(temp_OS, c_job, c_op)
                            idx2 = get_os_idx(temp_OS, t_job, t_op)

                            if idx1 != -1 and idx2 != -1:
                                temp_OS[idx1], temp_OS[idx2] = temp_OS[idx2], temp_OS[idx1]
                                move_signature = f"CP_OS_{min(idx1, idx2)}_{max(idx1, idx2)}"

                if not move_signature:
                    if random.random() < 0.5:
                        idx1, idx2 = random.sample(range(self.T0), 2)
                        temp_OS[idx1], temp_OS[idx2] = temp_OS[idx2], temp_OS[idx1]
                        move_signature = f"R_OS_{min(idx1, idx2)}_{max(idx1, idx2)}"
                    else:
                        idx_m = random.randint(0, self.T0 - 1)
                        max_m = self.Feasible_Machines_Count[idx_m] - 1
                        if max_m > 0:
                            new_m = random.randint(0, max_m)
                            temp_MS[idx_m] = new_m
                            move_signature = f"R_MS_{idx_m}_{new_m}"

                temp_fit = self.evaluate_fitness(temp_MS, temp_OS)
                neighborhood.append((temp_MS, temp_OS, temp_fit, move_signature))

            neighborhood.sort(key=lambda x: x[2])

            move_accepted = False
            record_broken = False

            for n_MS, n_OS, neighbor_fit, signature in neighborhood:
                if neighbor_fit < best_overall_fit:
                    current_MS, current_OS = np.copy(n_MS), np.copy(n_OS)
                    best_overall_MS, best_overall_OS = np.copy(n_MS), np.copy(n_OS)
                    best_overall_fit = neighbor_fit

                    if signature:
                        tabu_list.append(signature)
                        if len(tabu_list) > tabu_tenure: tabu_list.pop(0)

                    move_accepted = True
                    record_broken = True
                    break

                elif signature and signature not in tabu_list:
                    current_MS, current_OS = np.copy(n_MS), np.copy(n_OS)
                    tabu_list.append(signature)
                    if len(tabu_list) > tabu_tenure: tabu_list.pop(0)
                    move_accepted = True
                    break

            if not move_accepted and neighborhood:
                current_MS, current_OS = np.copy(neighborhood[0][0]), np.copy(neighborhood[0][1])

            if record_broken:
                tabu_patience = 0
            else:
                tabu_patience += 1

            if tabu_patience >= patience_limit:
                break

        return best_overall_MS, best_overall_OS, best_overall_fit

    def optimize(self):
        X_MS, X_OS = self.init_discrete_population()
        fitness = np.zeros(self.N)

        BEST_MS = np.zeros(self.T0, dtype=int)
        BEST_OS = np.zeros(self.T0, dtype=int)
        Bestfitval = 1E+200

        stagnation_counter = 0
        global_stagnation = 0
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
                        Pc = 1.0 - (math.log(1 + 9 * (iter_num / self.Maxiter)) / math.log(10))

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

            num_elites = max(1, self.N // 10)

            if stagnation_counter >= 15:
                sorted_idx = np.argsort(fitness)
                for rank in range(num_elites):
                    idx = sorted_idx[rank]
                    refined_MS, refined_OS, refined_fit = self.local_search(X_MS[idx], X_OS[idx], fitness[idx],
                                                                            num_steps=100, neighbors_per_step=20)

                    X_MS[idx], X_OS[idx] = np.copy(refined_MS), np.copy(refined_OS)
                    fitness[idx] = refined_fit

                    if refined_fit < Bestfitval:
                        Bestfitval = refined_fit
                        BEST_MS, BEST_OS = np.copy(refined_MS), np.copy(refined_OS)

                stagnation_counter = 0

            elif iter_num > (0.666 * self.Maxiter):
                sorted_idx = np.argsort(fitness)
                for rank in range(num_elites):
                    idx = sorted_idx[rank]
                    refined_MS, refined_OS, refined_fit = self.local_search(X_MS[idx], X_OS[idx], fitness[idx],
                                                                            num_steps=5, neighbors_per_step=10)

                    X_MS[idx], X_OS[idx] = np.copy(refined_MS), np.copy(refined_OS)
                    fitness[idx] = refined_fit

                    if refined_fit < Bestfitval:
                        Bestfitval = refined_fit
                        BEST_MS, BEST_OS = np.copy(refined_MS), np.copy(refined_OS)

            # ==========================================================
            # --- PHASE-AWARE EARLY STOPPING CHECK ---
            # ==========================================================
            if Bestfitval < previous_best_fit:
                global_stagnation = 0
            else:
                global_stagnation += 1

            convergence_history.append(Bestfitval)

            if global_stagnation >= 100 and iter_num > (0.5 * self.Maxiter):
                print(f"Early Stopping Triggered at Iteration {iter_num}!")
                while iter_num <= self.Maxiter:
                    convergence_history.append(Bestfitval)
                    iter_num += 1
                break

            iter_num += 1

        best_discrete_CHS = np.hstack((BEST_MS, BEST_OS))
        return best_discrete_CHS, Bestfitval, convergence_history