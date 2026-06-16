import random
import numpy as np
from collections import deque
from Decode import Decode


class GA():
    def __init__(self,
                 Pop_size=400,
                 Pc=0.8,
                 Pm=0.3,
                 Generations=100,
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
            if val in Set2:  # <--- FIXED (Was Set1)
                while idx1 < len(new_os1) and new_os1[idx1] != -1:
                    idx1 += 1
                if idx1 < len(new_os1):
                    new_os1[idx1] = val

        # Fill Child 2's empty slots (which are reserved for Set1) using Parent 1's order
        for val in OS_1:
            if val in Set1:  # <--- FIXED (Was Set2)
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

    # --- TABU SEARCH HELPERS ---

    def _flat_to_job_op(self, flat_idx, J):
        """Maps a flat MS index to its (Job, Operation) indices for feasibility checking."""
        cum = 0
        for job_id, num_ops in J.items():
            if flat_idx < cum + num_ops:
                return job_id - 1, flat_idx - cum
            cum += num_ops
        return 0, 0

    def tabu_local_search(self, CHS, best_global_fitness, J, Processing_time, M_num, Len, num_steps=10,
                          neighbors_per_step=8, tenure=7):
        """
        Executes a Tabu Search to refine a single chromosome.
        """
        cur_CHS = np.copy(CHS)
        best_CHS = np.copy(CHS)

        # Calculate initial fitness
        d = Decode(J, Processing_time, M_num)
        cur_fit = d.decode(cur_CHS, Len)
        best_fit = cur_fit

        tabu_list = deque(maxlen=tenure)
        tabu_list.append(tuple(cur_CHS.tolist()))

        for _ in range(num_steps):
            neighbors = []

            # Generate Neighbors
            for _ in range(neighbors_per_step):
                neighbor = np.copy(cur_CHS)

                # 50% chance for OS Swap, 50% chance for MS Mutation
                if random.random() < 0.5:
                    # OS Swap
                    i, j = random.sample(range(Len, 2 * Len), 2)
                    neighbor[i], neighbor[j] = neighbor[j], neighbor[i]
                else:
                    # MS Mutation (to another feasible machine relative index)
                    flat_idx = random.randint(0, Len - 1)
                    job_idx, op_idx = self._flat_to_job_op(flat_idx, J)
                    feasible_times = Processing_time[job_idx][op_idx]

                    # Count how many machines are feasible for this operation
                    feasible_count = sum(1 for t in feasible_times if t != 9999)

                    if feasible_count > 1:
                        # The chromosome stores the relative index (0 to feasible_count - 1)
                        current_rel_idx = neighbor[flat_idx]
                        alt_indices = [idx for idx in range(feasible_count) if idx != current_rel_idx]

                        if alt_indices:
                            neighbor[flat_idx] = random.choice(alt_indices)

                # Decode neighbor fitness
                d_neigh = Decode(J, Processing_time, M_num)
                n_fit = d_neigh.decode(neighbor, Len)
                neighbors.append((n_fit, neighbor))

            # Sort neighbors by fitness
            neighbors.sort(key=lambda x: x[0])

            # Pick best non-tabu or aspiration-meeting neighbor
            step_taken = False
            for n_fit, n_chs in neighbors:
                state_tuple = tuple(n_chs.tolist())

                # Aspiration Criterion: if it beats the absolute global best, ignore Tabu
                if n_fit < best_global_fitness:
                    cur_CHS = np.copy(n_chs)
                    cur_fit = n_fit
                    best_CHS = np.copy(n_chs)
                    best_fit = n_fit
                    best_global_fitness = n_fit  # Update global reference
                    tabu_list.append(state_tuple)
                    step_taken = True
                    break

                # Standard Tabu Check
                if state_tuple not in tabu_list:
                    cur_CHS = np.copy(n_chs)
                    cur_fit = n_fit
                    if cur_fit < best_fit:
                        best_CHS = np.copy(n_chs)
                        best_fit = cur_fit
                    tabu_list.append(state_tuple)
                    step_taken = True
                    break

            # If all neighbors were tabu and didn't meet aspiration (rare), just take the best and push through
            if not step_taken and neighbors:
                cur_CHS = np.copy(neighbors[0][1])
                cur_fit = neighbors[0][0]
                tabu_list.append(tuple(cur_CHS.tolist()))

        return best_CHS, best_fit