import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        """
        Pure Discrete Aquila Optimizer for FJSSP
        - Keeps Aquila-style exploration/exploitation structure
        - Uses elite archive + center guidance
        - STRIPPED of all Local Search, Tabu, and Stagnation elements
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

    # =========================================================
    # Basic helpers
    # =========================================================
    def flat_index_to_job_op(self, idx):
        cum = 0
        for j_id, num_ops in self.J.items():
            if idx < cum + num_ops:
                return j_id - 1, idx - cum
            cum += num_ops
        return 0, 0

    def relative_to_actual_machine(self, flat_idx, rel_idx):
        job_idx, op_idx = self.flat_index_to_job_op(flat_idx)
        p_times = self.Processing_time[job_idx][op_idx]
        rel_counter = 0
        for m_idx, t in enumerate(p_times):
            if t != 9999:
                if rel_counter == rel_idx:
                    return m_idx
                rel_counter += 1
        return 0

    def actual_to_relative_machine(self, flat_idx, actual_machine):
        job_idx, op_idx = self.flat_index_to_job_op(flat_idx)
        p_times = self.Processing_time[job_idx][op_idx]
        rel_counter = 0
        for m_idx, t in enumerate(p_times):
            if t != 9999:
                if m_idx == actual_machine:
                    return rel_counter
                rel_counter += 1
        return 0

    def processing_time_of_choice(self, flat_idx, rel_idx):
        actual_m = self.relative_to_actual_machine(flat_idx, rel_idx)
        job_idx, op_idx = self.flat_index_to_job_op(flat_idx)
        return self.Processing_time[job_idx][op_idx][actual_m]

    # =========================================================
    # Initialization
    # =========================================================
    def init_discrete_population(self):
        X_MS = np.zeros((self.N, self.T0), dtype=int)
        X_OS = np.zeros((self.N, self.T0), dtype=int)

        num_gs = int(self.N * 0.25)  # global greedy
        num_ls = int(self.N * 0.25)  # local greedy
        # remaining random

        for i in range(self.N):
            # Global greedy initialization
            if i < num_gs:
                machine_workloads = np.zeros(self.M_num, dtype=np.int64)
                flat_idx = 0

                for job_id, num_ops in self.J.items():
                    job_idx = job_id - 1
                    for op_idx in range(num_ops):
                        p_times = self.Processing_time[job_idx][op_idx]

                        best_m = -1
                        best_rel_idx = -1
                        min_load = float('inf')

                        rel_idx = 0
                        for m_idx, t in enumerate(p_times):
                            if t != 9999:
                                projected = machine_workloads[m_idx] + t
                                if projected < min_load:
                                    min_load = projected
                                    best_m = m_idx
                                    best_rel_idx = rel_idx
                                rel_idx += 1

                        X_MS[i, flat_idx] = best_rel_idx
                        machine_workloads[best_m] += p_times[best_m]
                        flat_idx += 1

            # Local greedy initialization
            elif i < num_gs + num_ls:
                flat_idx = 0
                for job_id, num_ops in self.J.items():
                    local_loads = np.zeros(self.M_num, dtype=np.int64)
                    job_idx = job_id - 1

                    for op_idx in range(num_ops):
                        p_times = self.Processing_time[job_idx][op_idx]

                        best_m = -1
                        best_rel_idx = -1
                        min_load = float('inf')

                        rel_idx = 0
                        for m_idx, t in enumerate(p_times):
                            if t != 9999:
                                projected = local_loads[m_idx] + t
                                if projected < min_load:
                                    min_load = projected
                                    best_m = m_idx
                                    best_rel_idx = rel_idx
                                rel_idx += 1

                        X_MS[i, flat_idx] = best_rel_idx
                        local_loads[best_m] += p_times[best_m]
                        flat_idx += 1

            # Random initialization
            else:
                for j in range(self.T0):
                    X_MS[i, j] = random.randint(0, self.Feasible_Machines_Count[j] - 1)

            os_copy = self.standard_OS_list.copy()
            np.random.shuffle(os_copy)
            X_OS[i] = os_copy

        return X_MS, X_OS

    # =========================================================
    # Fitness
    # =========================================================
    def evaluate_fitness(self, MS, OS):
        CHS_discrete = np.hstack((MS, OS))
        decoder = Decode(self.J, self.Processing_time, self.M_num)
        return decoder.decode(CHS_discrete, self.T0)

    # =========================================================
    # MS Operators
    # =========================================================
    def mutate_ms(self, ms, num_mutations):
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            max_m = self.Feasible_Machines_Count[idx] - 1
            if max_m > 0:
                ms[idx] = random.randint(0, max_m)

    def guided_ms_mutation(self, ms, num_mutations):
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            max_m = self.Feasible_Machines_Count[idx]
            if max_m <= 1:
                continue

            job_idx, op_idx = self.flat_index_to_job_op(idx)
            p_times = self.Processing_time[job_idx][op_idx]

            feasible = [(m, t) for m, t in enumerate(p_times) if t != 9999]
            feasible_sorted = sorted(feasible, key=lambda x: x[1])

            if len(feasible_sorted) == 1:
                chosen_machine = feasible_sorted[0][0]
            elif len(feasible_sorted) == 2:
                r = random.random()
                if r < 0.75:
                    chosen_machine = feasible_sorted[0][0]
                elif r < 0.95:
                    chosen_machine = feasible_sorted[1][0]
                else:
                    chosen_machine = random.choice(feasible_sorted)[0]
            else:
                r = random.random()
                if r < 0.45:
                    chosen_machine = feasible_sorted[0][0]
                elif r < 0.70:
                    chosen_machine = feasible_sorted[1][0]
                elif r < 0.90:
                    chosen_machine = feasible_sorted[2][0]
                else:
                    chosen_machine = random.choice(feasible_sorted[:min(4, len(feasible_sorted))])[0]

            ms[idx] = self.actual_to_relative_machine(idx, chosen_machine)

    def targeted_ms_refine(self, ms, num_moves=1):
        for _ in range(num_moves):
            machine_loads = np.zeros(self.M_num, dtype=np.float64)
            assigned_actual = []

            for idx in range(self.T0):
                actual_m = self.relative_to_actual_machine(idx, ms[idx])
                assigned_actual.append(actual_m)
                machine_loads[actual_m] += self.processing_time_of_choice(idx, ms[idx])

            busiest = int(np.argmax(machine_loads))

            candidates = []
            for idx in range(self.T0):
                if assigned_actual[idx] != busiest:
                    continue

                job_idx, op_idx = self.flat_index_to_job_op(idx)
                p_times = self.Processing_time[job_idx][op_idx]
                current_m = assigned_actual[idx]
                current_t = p_times[current_m]

                feasible = [(m, t) for m, t in enumerate(p_times) if t != 9999 and m != current_m]
                if not feasible:
                    continue

                best_alt = None
                best_score = float('inf')
                for alt_m, alt_t in feasible:
                    score = machine_loads[alt_m] + alt_t
                    if score < best_score:
                        best_score = score
                        best_alt = alt_m

                if best_alt is not None:
                    gain = machine_loads[busiest] - machine_loads[best_alt]
                    candidates.append((best_score - gain, idx, best_alt))

            if candidates:
                candidates.sort(key=lambda x: x[0])
                _, idx, best_alt = candidates[0]
                ms[idx] = self.actual_to_relative_machine(idx, best_alt)

    # =========================================================
    # OS Operators
    # =========================================================
    def swap_os(self, os_arr, num_swaps):
        for _ in range(num_swaps):
            i, j = random.sample(range(self.T0), 2)
            os_arr[i], os_arr[j] = os_arr[j], os_arr[i]

    def insert_os(self, os_arr, num_inserts):
        for _ in range(num_inserts):
            i, j = random.sample(range(self.T0), 2)
            os_list = list(os_arr)
            val = os_list.pop(i)
            os_list.insert(j, val)
            os_arr[:] = np.array(os_list, dtype=os_arr.dtype)

    def invert_os(self, os_arr, num_inversions):
        for _ in range(num_inversions):
            i, j = sorted(random.sample(range(self.T0), 2))
            os_arr[i:j + 1] = os_arr[i:j + 1][::-1]

    def targeted_os_refine(self, os_arr, num_moves=1):
        for _ in range(num_moves):
            if random.random() < 0.6:
                self.insert_os(os_arr, 1)
            else:
                self.invert_os(os_arr, 1)

    def perturb_os(self, os_arr, strength, phase_ratio):
        for _ in range(max(1, strength)):
            r = random.random()
            if phase_ratio < 0.33:
                if r < 0.30:
                    self.swap_os(os_arr, 1)
                elif r < 0.70:
                    self.insert_os(os_arr, 1)
                else:
                    self.invert_os(os_arr, 1)
            elif phase_ratio < 0.66:
                if r < 0.35:
                    self.swap_os(os_arr, 1)
                elif r < 0.65:
                    self.insert_os(os_arr, 1)
                else:
                    self.invert_os(os_arr, 1)
            else:
                if r < 0.30:
                    self.swap_os(os_arr, 1)
                elif r < 0.55:
                    self.insert_os(os_arr, 1)
                else:
                    self.invert_os(os_arr, 1)

    # =========================================================
    # Elite archive and center
    # =========================================================
    def elite_archive(self, X_MS, X_OS, fitness, k):
        idx = np.argsort(fitness, kind='stable')[:k]
        archive_MS = [np.copy(X_MS[i]) for i in idx]
        archive_OS = [np.copy(X_OS[i]) for i in idx]
        archive_fit = [fitness[i] for i in idx]
        return archive_MS, archive_OS, archive_fit

    def elite_center(self, X_MS, X_OS, fitness, top_k):
        top_k = max(1, min(top_k, len(fitness)))
        elite_indices = np.argsort(fitness, kind='stable')[:top_k]

        center_MS = np.zeros(self.T0, dtype=int)
        for j in range(self.T0):
            col = [X_MS[idx][j] for idx in elite_indices]
            values, counts = np.unique(col, return_counts=True)
            center_MS[j] = values[np.argmax(counts)]

        best_elite_idx = elite_indices[0]
        center_OS = np.copy(X_OS[best_elite_idx])

        return center_MS, center_OS

    # =========================================================
    # AO-inspired burst (Simplified - No Stagnation)
    # =========================================================
    def discrete_levy_burst(self, iter_num):
        phase = iter_num / self.Maxiter

        if phase < 0.33:
            base = random.choice([2, 3, 4, 5])
        elif phase < 0.66:
            base = random.choice([2, 3, 4])
        else:
            base = random.choice([1, 1, 2, 2, 3])

        return min(base, max(3, self.T0 // 4))

    # =========================================================
    # Main optimization
    # =========================================================
    def optimize(self):
        X_MS, X_OS = self.init_discrete_population()
        fitness = np.zeros(self.N)

        BEST_MS = np.zeros(self.T0, dtype=int)
        BEST_OS = np.zeros(self.T0, dtype=int)
        Bestfitval = 1e200

        convergence_history = []
        avg_convergence_history = []  # Added for population average tracking

        # Initial evaluation
        for i in range(self.N):
            fitness[i] = self.evaluate_fitness(X_MS[i], X_OS[i])
            if fitness[i] < Bestfitval:
                Bestfitval = fitness[i]
                BEST_MS, BEST_OS = np.copy(X_MS[i]), np.copy(X_OS[i])

        convergence_history.append(Bestfitval)
        avg_convergence_history.append(np.mean(fitness))  # Track initial average
        iter_num = 1

        Center_MS, Center_OS = self.elite_center(
            X_MS, X_OS, fitness, top_k=max(2, self.N // 5)
        )

        while iter_num <= self.Maxiter:
            phase_ratio = iter_num / self.Maxiter

            # Build elite archive every iteration
            archive_MS, archive_OS, archive_fit = self.elite_archive(
                X_MS, X_OS, fitness, k=max(3, self.N // 8)
            )

            elite_idx = random.choice(np.argsort(fitness)[:max(1, self.N // 5)])
            Elite_MS = np.copy(X_MS[elite_idx])
            Elite_OS = np.copy(X_OS[elite_idx])

            if iter_num % 4 == 1:
                Center_MS, Center_OS = self.elite_center(
                    X_MS, X_OS, fitness, top_k=max(2, self.N // 5)
                )

            for i in range(self.N):
                MS_new = np.copy(X_MS[i])
                OS_new = np.copy(X_OS[i])

                # =====================================================
                # Stage 1: Exploration
                # =====================================================
                if iter_num <= (0.666 * self.Maxiter):

                    # Mode 1: Expanded exploration
                    if random.random() < 0.5:
                        if iter_num <= (0.333 * self.Maxiter):
                            q = 1.0 - (iter_num / self.Maxiter)
                        else:
                            shifted_t = iter_num - (0.333 * self.Maxiter)
                            shifted_max = self.Maxiter * 0.666
                            q = math.exp(-2.0 * (shifted_t / shifted_max))

                        archive_id = random.randint(0, len(archive_MS) - 1)
                        Ref_MS = archive_MS[archive_id]
                        Ref_OS = archive_OS[archive_id]

                        for j in range(self.T0):
                            r_val = random.random()
                            if r_val < 0.15 * q:
                                MS_new[j] = BEST_MS[j]
                            elif r_val < 0.35 * q:
                                MS_new[j] = Elite_MS[j]
                            elif r_val < 0.60:
                                MS_new[j] = Center_MS[j]
                            elif r_val < 0.80:
                                MS_new[j] = Ref_MS[j]
                            else:
                                MS_new[j] = X_MS[i][j]

                        # 70% Personal OS Retention
                        ref_pick = random.random()
                        if ref_pick < 0.10:
                            OS_new = np.copy(Elite_OS)
                        elif ref_pick < 0.20:
                            OS_new = np.copy(Center_OS)
                        elif ref_pick < 0.30:
                            OS_new = np.copy(Ref_OS)
                        else:
                            OS_new = np.copy(X_OS[i])

                        burst = self.discrete_levy_burst(iter_num)

                        self.targeted_ms_refine(MS_new, max(1, burst // 3))
                        if random.random() < 0.5:
                            self.guided_ms_mutation(MS_new, max(1, burst // 3))
                        else:
                            self.mutate_ms(MS_new, max(1, burst // 3))

                        self.perturb_os(OS_new, max(1, burst // 2), phase_ratio)

                    # Mode 2: Narrowed exploration
                    else:
                        archive_id = random.randint(0, len(archive_MS) - 1)
                        Ref_MS = archive_MS[archive_id]
                        Ref_OS = archive_OS[archive_id]

                        for j in range(self.T0):
                            r_val = random.random()
                            if r_val < 0.22:
                                MS_new[j] = Ref_MS[j]
                            elif r_val < 0.50:
                                MS_new[j] = Center_MS[j]
                            else:
                                MS_new[j] = X_MS[i][j]

                        # 70% Personal OS Retention (Mode 2)
                        ref_pick = random.random()
                        if ref_pick < 0.15:
                            OS_new = np.copy(Ref_OS)
                        elif ref_pick < 0.30:
                            OS_new = np.copy(Center_OS)
                        else:
                            OS_new = np.copy(X_OS[i])
                        burst = self.discrete_levy_burst(iter_num)

                        self.targeted_ms_refine(MS_new, max(1, burst // 3))
                        self.perturb_os(OS_new, max(1, burst // 2), phase_ratio)

                # =====================================================
                # Stage 2: Exploitation
                # =====================================================
                else:
                    # Mode 3: Expanded exploitation
                    if random.random() < 0.5:
                        for j in range(self.T0):
                            r_val = random.random()
                            if r_val < 0.72:
                                MS_new[j] = BEST_MS[j]
                            elif r_val < 0.90:
                                MS_new[j] = Center_MS[j]
                            else:
                                MS_new[j] = Elite_MS[j]

                        OS_new = np.copy(BEST_OS if random.random() < 0.80 else Elite_OS)

                        self.targeted_ms_refine(MS_new, 1)
                        if random.random() < 0.5:
                            self.guided_ms_mutation(MS_new, 1)

                        if random.random() < 0.7:
                            self.targeted_os_refine(OS_new, 1)
                        else:
                            self.perturb_os(OS_new, 1, phase_ratio)

                    # Mode 4: Narrowed exploitation
                    else:
                        MS_new = np.copy(BEST_MS)
                        OS_new = np.copy(BEST_OS)

                        self.targeted_ms_refine(MS_new, 1)
                        if random.random() < 0.5:
                            self.guided_ms_mutation(MS_new, 1)

                        self.targeted_os_refine(OS_new, 1)

                fit_new = self.evaluate_fitness(MS_new, OS_new)

                if fit_new < fitness[i]:
                    X_MS[i], X_OS[i] = np.copy(MS_new), np.copy(OS_new)
                    fitness[i] = fit_new

                    if fit_new < Bestfitval:
                        Bestfitval = fit_new
                        BEST_MS, BEST_OS = np.copy(MS_new), np.copy(OS_new)

            convergence_history.append(Bestfitval)
            avg_convergence_history.append(np.mean(fitness))  # Track iteration average
            iter_num += 1

        best_discrete_CHS = np.hstack((BEST_MS, BEST_OS))
        return best_discrete_CHS, Bestfitval, convergence_history, avg_convergence_history  # Modified return