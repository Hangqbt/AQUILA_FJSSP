import random
import numpy as np
from collections import deque
from Decode import Decode


class GA():
    def __init__(self,
                 Pop_size=300,
                 Pc=0.8,
                 Pm=0.3,
                 Generations=50,
                 seed=42):
        # --- Reproducibility: fix seeds for Python and NumPy ---
        self.seed = seed
        random.seed(self.seed)
        np.random.seed(self.seed)

        # GA hyperparameters from arguments
        self.Pop_size = Pop_size  # population size
        self.Pc = Pc  # crossover prob
        self.Pm = Pm  # mutation prob
        self.Generations = Generations

    # Fitness of a batch of chromosomes
    def fitness(self, CHS, J, Processing_time, M_num, Len):
        Fit = []
        for i in range(len(CHS)):
            d = Decode(J, Processing_time, M_num)
            Fit.append(d.decode(CHS[i], Len))
        return Fit

    # Crossover on machine-selection (MS) part
    def machine_cross(self, CHS1, CHS2, T0):
        T_r = [j for j in range(T0)]
        r = random.randint(1, T0)
        random.shuffle(T_r)
        R = T_r[0:r]
        OS_1 = CHS1[T0:2 * T0]
        OS_2 = CHS2[T0:2 * T0]
        MS_1 = CHS1[0:T0]
        MS_2 = CHS2[0:T0]
        for i in R:
            MS_1[i], MS_2[i] = MS_2[i], MS_1[i]
        CHS1 = np.hstack((MS_1, OS_1))
        CHS2 = np.hstack((MS_2, OS_2))
        return CHS1, CHS2

    # Crossover on operation-sequence (OS) part (Precedence Preserving Order-Based Crossover)
    def operation_cross(self, CHS1, CHS2, T0, J_num):
        OS_1 = CHS1[T0:2 * T0]
        OS_2 = CHS2[T0:2 * T0]
        MS_1 = CHS1[0:T0]
        MS_2 = CHS2[0:T0]
        Job_list = [i for i in range(J_num)]
        random.shuffle(Job_list)
        r = random.randint(1, J_num - 1)
        Set1 = Job_list[0:r]
        Set2 = Job_list[r:J_num]

        new_os1 = list(OS_1)
        new_os2 = list(OS_2)

        # Step 1: Retain Set1 in Child 1, Set2 in Child 2. Replace the rest with -1
        for k in range(len(OS_1)):
            if OS_1[k] in Set1:
                new_os1[k] = OS_1[k]
            else:
                new_os1[k] = -1

            if OS_2[k] in Set2:
                new_os2[k] = OS_2[k]
            else:
                new_os2[k] = -1

        # Step 2: Fill the -1 slots
        idx1 = 0
        idx2 = 0

        # Fill Child 1's empty slots (which are reserved for Set2) using Parent 2's order
        for val in OS_2:
            if val in Set2:
                while idx1 < len(new_os1) and new_os1[idx1] != -1:
                    idx1 += 1
                if idx1 < len(new_os1):
                    new_os1[idx1] = val

        # Fill Child 2's empty slots (which are reserved for Set1) using Parent 1's order
        for val in OS_1:
            if val in Set1:
                while idx2 < len(new_os2) and new_os2[idx2] != -1:
                    idx2 += 1
                if idx2 < len(new_os2):
                    new_os2[idx2] = val

        CHS1 = np.hstack((MS_1, new_os1))
        CHS2 = np.hstack((MS_2, new_os2))
        return CHS1, CHS2

    # Mutation on machine-selection (MS) part
    def machine_variation(self, CHS, T0, J, O):
        MS = CHS[0:T0]
        OS = CHS[T0:2 * T0]
        Tr = [i for i in range(T0)]
        r = random.randint(1, T0)
        random.shuffle(Tr)
        T_r = Tr[0:r]
        for num in T_r:
            T_0 = [j for j in range(T0)]
            K = []
            site = 0
            for _, v in J.items():
                K.append(T_0[site:site + v])
                site += v
            for i in range(len(K)):
                if num in K[i]:
                    O_i = i
                    O_j = K[i].index(num)
                    break
            Machine_using = O[O_i][O_j]
            Machine_time = [t for t in Machine_using if t != 9999]
            Min_index = Machine_time.index(min(Machine_time))
            MS[num] = Min_index
        CHS = np.hstack((MS, OS))
        return CHS

    # Mutation on operation-sequence (OS) part
    def operation_variation(self, CHS, T0, J_num, J, O, M_num):
        MS = CHS[0:T0]
        OS = list(CHS[T0:2 * T0])
        r = random.randint(1, J_num - 1)
        Tr = [i for i in range(J_num)]
        random.shuffle(Tr)
        Tr = Tr[0:r]
        Site = [OS.index(Tr[i]) for i in range(r)]
        random.shuffle(Site)
        for i in range(r):
            OS[Site[i]] = Tr[i]
        CHS = np.hstack((MS, OS))
        return CHS

    # =========================================================
    # TABU SEARCH HELPERS (1:1 with AO)
    # =========================================================
    def _flat_to_job_op(self, flat_idx, J):
        cum = 0
        for job_id, num_ops in J.items():
            if flat_idx < cum + num_ops:
                return job_id - 1, flat_idx - cum
            cum += num_ops
        return 0, 0

    def _relative_to_actual_machine(self, flat_idx, rel_idx, J, Processing_time):
        job_idx, op_idx = self._flat_to_job_op(flat_idx, J)
        p_times = Processing_time[job_idx][op_idx]
        rel_counter = 0
        for m_idx, t in enumerate(p_times):
            if t != 9999:
                if rel_counter == rel_idx:
                    return m_idx
                rel_counter += 1
        return 0

    def _actual_to_relative_machine(self, flat_idx, actual_machine, J, Processing_time):
        job_idx, op_idx = self._flat_to_job_op(flat_idx, J)
        p_times = Processing_time[job_idx][op_idx]
        rel_counter = 0
        for m_idx, t in enumerate(p_times):
            if t != 9999:
                if m_idx == actual_machine:
                    return rel_counter
                rel_counter += 1
        return 0

    def _get_feasible_counts(self, Processing_time):
        counts = []
        for job_idx, ops in enumerate(Processing_time):
            for op_idx, machine_times in enumerate(ops):
                counts.append(sum(1 for t in machine_times if t != 9999))
        return counts

    # --- MS Operators ---
    def targeted_ms_refine(self, ms, num_moves, J, Processing_time, M_num, T0):
        for _ in range(num_moves):
            machine_loads = np.zeros(M_num, dtype=np.float64)
            assigned_actual = []

            for idx in range(T0):
                actual_m = self._relative_to_actual_machine(idx, ms[idx], J, Processing_time)
                assigned_actual.append(actual_m)
                job_idx, op_idx = self._flat_to_job_op(idx, J)
                machine_loads[actual_m] += Processing_time[job_idx][op_idx][actual_m]

            busiest = int(np.argmax(machine_loads))
            candidates = []

            for idx in range(T0):
                if assigned_actual[idx] != busiest:
                    continue

                job_idx, op_idx = self._flat_to_job_op(idx, J)
                p_times = Processing_time[job_idx][op_idx]
                current_m = assigned_actual[idx]

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
                ms[idx] = self._actual_to_relative_machine(idx, best_alt, J, Processing_time)

    def guided_ms_mutation(self, ms, num_mutations, J, Processing_time, T0, feasible_counts):
        for _ in range(num_mutations):
            idx = random.randint(0, T0 - 1)
            max_m = feasible_counts[idx]
            if max_m <= 1:
                continue

            job_idx, op_idx = self._flat_to_job_op(idx, J)
            p_times = Processing_time[job_idx][op_idx]

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

            ms[idx] = self._actual_to_relative_machine(idx, chosen_machine, J, Processing_time)

    # --- OS Operators ---
    def targeted_os_refine(self, os_arr, num_moves, T0):
        for _ in range(num_moves):
            if random.random() < 0.6:
                # Insert
                i, j = random.sample(range(T0), 2)
                os_list = list(os_arr)
                val = os_list.pop(i)
                os_list.insert(j, val)
                os_arr[:] = np.array(os_list, dtype=os_arr.dtype)
            else:
                # Invert
                i, j = sorted(random.sample(range(T0), 2))
                os_arr[i:j + 1] = os_arr[i:j + 1][::-1]

    # --- Main Tabu Routine ---
    def tabu_local_search(self, CHS, current_best_fit, J, Processing_time, M_num, T0, num_steps=12,
                          neighbors_per_step=6, tenure=5):
        current_MS = np.copy(CHS[0:T0])
        current_OS = np.copy(CHS[T0:2 * T0])

        best_overall_MS = np.copy(current_MS)
        best_overall_OS = np.copy(current_OS)
        best_overall_fit = current_best_fit

        tabu_list = []
        tabu_patience = 0
        patience_limit = 6

        feasible_counts = self._get_feasible_counts(Processing_time)

        for _ in range(num_steps):
            neighborhood = []

            for _ in range(neighbors_per_step):
                temp_MS = np.copy(current_MS)
                temp_OS = np.copy(current_OS)

                r = random.random()
                if r < 0.35:
                    self.targeted_ms_refine(temp_MS, 1, J, Processing_time, M_num, T0)
                    move_signature = "TMS"
                elif r < 0.60:
                    self.guided_ms_mutation(temp_MS, 1, J, Processing_time, T0, feasible_counts)
                    move_signature = "GMS"
                elif r < 0.80:
                    self.targeted_os_refine(temp_OS, 1, T0)
                    move_signature = "TOS"
                else:
                    self.targeted_ms_refine(temp_MS, 1, J, Processing_time, M_num, T0)
                    self.targeted_os_refine(temp_OS, 1, T0)
                    move_signature = "BOTH"

                temp_CHS = np.hstack((temp_MS, temp_OS))
                d = Decode(J, Processing_time, M_num)
                temp_fit = d.decode(temp_CHS, T0)

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
                        if len(tabu_list) > tenure:
                            tabu_list.pop(0)

                    move_accepted = True
                    record_broken = True
                    break

                elif signature and signature not in tabu_list:
                    current_MS, current_OS = np.copy(n_MS), np.copy(n_OS)
                    tabu_list.append(signature)
                    if len(tabu_list) > tenure:
                        tabu_list.pop(0)
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

        final_CHS = np.hstack((best_overall_MS, best_overall_OS))
        return final_CHS, best_overall_fit