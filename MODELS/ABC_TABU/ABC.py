import random
import numpy as np
from collections import defaultdict
from Decode import Decode


class ABC_FJSSP:
    def __init__(self, J, Processing_time, M_num, J_num, n_eb, n_ob, n_sb, Maxiter, seed=42):
        # --- Strict Seed Locking ---
        self.seed = seed
        random.seed(self.seed)
        np.random.seed(self.seed)

        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.J_num = J_num

        self.n_eb = n_eb
        self.n_ob = n_ob
        self.n_sb = n_sb
        self.Maxiter = Maxiter

        self.T0 = sum(J.values())
        self.D = 2 * self.T0
        self.Len = self.T0

    def init_machine_assignment(self):
        MS = []
        rule = random.random()
        for job_idx, ops in enumerate(self.Processing_time):
            for op_idx, m_times in enumerate(ops):
                valid_machines = [m for m, t in enumerate(m_times) if t != 9999]
                if rule < 0.10:
                    MS.append(random.choice(range(len(valid_machines))))
                elif rule < 0.40:
                    valid_times = [t for t in m_times if t != 9999]
                    MS.append(np.argmin(valid_times))
                else:
                    valid_times = [t for t in m_times if t != 9999]
                    noisy_times = [t + random.uniform(0, 2) for t in valid_times]
                    MS.append(np.argmin(noisy_times))
        return MS

    def init_operation_sequence(self):
        rule = random.random()
        OS = []
        if rule < 0.20:
            for job_id, num_ops in self.J.items():
                OS.extend([job_id - 1] * num_ops)
            random.shuffle(OS)
            return OS
        elif rule < 0.60:
            ops_remaining = {j: self.J[j + 1] for j in range(self.J_num)}
            while sum(ops_remaining.values()) > 0:
                available_jobs = [j for j, count in ops_remaining.items() if count > 0]
                best_job = max(available_jobs, key=lambda j: ops_remaining[j])
                OS.append(best_job)
                ops_remaining[best_job] -= 1
            return OS
        else:
            time_remaining = defaultdict(float)
            ops_remaining = {j: self.J[j + 1] for j in range(self.J_num)}
            for j in range(self.J_num):
                total_time = 0
                for op in self.Processing_time[j]:
                    valid_t = [t for t in op if t != 9999]
                    total_time += sum(valid_t) / len(valid_t) if valid_t else 0
                time_remaining[j] = total_time
            while sum(ops_remaining.values()) > 0:
                available_jobs = [j for j, count in ops_remaining.items() if count > 0]
                best_job = max(available_jobs, key=lambda j: time_remaining[j])
                OS.append(best_job)
                ops_remaining[best_job] -= 1
                op_idx = self.J[best_job + 1] - ops_remaining[best_job] - 1
                valid_t = [t for t in self.Processing_time[best_job][op_idx] if t != 9999]
                time_remaining[best_job] -= sum(valid_t) / len(valid_t) if valid_t else 0
            return OS

    def generate_initial_population(self):
        population = []
        for _ in range(self.n_eb):
            MS = self.init_machine_assignment()
            OS = self.init_operation_sequence()
            population.append(np.hstack((MS, OS)))
        return np.array(population)

    def crossover_MS(self, MS1, MS2):
        if random.random() < 0.5:
            p1, p2 = sorted(random.sample(range(self.Len), 2))
            child = np.copy(MS1)
            child[p1:p2] = MS2[p1:p2]
            return child
        else:
            mask = np.random.randint(0, 2, self.Len)
            return np.where(mask == 1, MS1, MS2)

    def crossover_OS_MPOX(self, OS1, OS2):
        job_subset = random.sample(range(self.J_num), random.randint(1, self.J_num - 1))
        child_OS = np.full(self.Len, -1)
        for i, job in enumerate(OS1):
            if job in job_subset:
                child_OS[i] = job
        p2_idx = 0
        for i in range(self.Len):
            if child_OS[i] == -1:
                while p2_idx < self.Len and OS2[p2_idx] in job_subset:
                    p2_idx += 1
                if p2_idx < self.Len:
                    child_OS[i] = OS2[p2_idx]
                    p2_idx += 1
        return child_OS

    def mutate_MS(self, MS):
        if random.random() < 0.5:
            child = np.copy(MS)
            num_mutations = random.randint(1, max(1, self.Len // 10))
            positions = random.sample(range(self.Len), num_mutations)
            for pos in positions:
                cum = 0
                job_idx, op_idx = 0, 0
                for j_id, num_ops in self.J.items():
                    if pos < cum + num_ops:
                        job_idx = j_id - 1
                        op_idx = pos - cum
                        break
                    cum += num_ops
                valid_count = sum(1 for t in self.Processing_time[job_idx][op_idx] if t != 9999)
                if valid_count > 1:
                    current_val = child[pos]
                    options = [x for x in range(valid_count) if x != current_val]
                    if options:
                        child[pos] = random.choice(options)
            return child
        return MS

    def tabu_local_search(self, CHS, num_steps, Best_fit):
        """
        Tabu Search as a local search operator.
        Evaluates a neighborhood, allows non-improving moves to escape local optima,
        and uses a Tabu List to prevent cycling.
        """
        best_CHS = np.copy(CHS)
        current_CHS = np.copy(CHS)

        best_fit_overall = Best_fit

        tabu_list = []  # List of recent moves to forbid
        tabu_tenure = 5  # Number of iterations a move stays Tabu
        neighborhood_size = 8  # Number of neighbors to explore per step

        for _ in range(num_steps):
            neighbors = []
            moves = []

            # 1. Generate Neighborhood
            for _ in range(neighborhood_size):
                neighbor = np.copy(current_CHS)

                # 50/50 chance to mutate Machine Selection (MS) or Operation Sequence (OS)
                if random.random() < 0.5:
                    # MS Mutation: Change machine for a random operation
                    pos = random.randint(0, self.Len - 1)
                    cum = 0
                    job_idx, op_idx = 0, 0
                    for j_id, num_ops in self.J.items():
                        if pos < cum + num_ops:
                            job_idx, op_idx = j_id - 1, pos - cum
                            break
                        cum += num_ops

                    valid_count = sum(1 for t in self.Processing_time[job_idx][op_idx] if t != 9999)
                    if valid_count > 1:
                        neighbor[pos] = random.choice(range(valid_count))

                    moves.append(("MS", pos))  # Record move type and position
                else:
                    # OS Mutation: Swap two operations
                    p1, p2 = random.sample(range(self.Len, self.D), 2)
                    neighbor[p1], neighbor[p2] = neighbor[p2], neighbor[p1]
                    moves.append(("OS", tuple(sorted((p1, p2)))))  # Sort so (1,2) and (2,1) are treated the same

                neighbors.append(neighbor)

            # 2. Evaluate Neighborhood
            best_neighbor_fit = float('inf')
            best_neighbor_idx = -1

            for idx, neighbor in enumerate(neighbors):
                d = Decode(self.J, self.Processing_time, self.M_num)
                n_fit = d.decode(neighbor, self.Len)

                move = moves[idx]
                is_tabu = move in tabu_list

                # 3. Aspiration Criterion
                # If it's Tabu, but the fitness is better than our ALL TIME best, we allow it.
                if is_tabu and n_fit < best_fit_overall:
                    is_tabu = False

                # Find the best non-tabu neighbor (even if it's worse than our current fit)
                if not is_tabu and n_fit < best_neighbor_fit:
                    best_neighbor_fit = n_fit
                    best_neighbor_idx = idx

            # 4. Make the Move
            if best_neighbor_idx != -1:
                current_CHS = np.copy(neighbors[best_neighbor_idx])

                # Update Tabu List
                tabu_list.append(moves[best_neighbor_idx])
                if len(tabu_list) > tabu_tenure:
                    tabu_list.pop(0)  # Remove oldest move

                # Update overall best if we found a strictly better solution
                if best_neighbor_fit < best_fit_overall:
                    best_fit_overall = best_neighbor_fit
                    best_CHS = np.copy(current_CHS)

        return best_CHS, best_fit_overall

    def optimize(self):
        Pop = self.generate_initial_population()

        Fitness = []
        for i in range(self.n_eb):
            d = Decode(self.J, self.Processing_time, self.M_num)
            Fitness.append(d.decode(Pop[i], self.Len))

        gbest_fit = min(Fitness)
        gbest_chs = np.copy(Pop[np.argmin(Fitness)])

        # --- History Trackers ---
        convergence_curve = [gbest_fit]
        avg_convergence_curve = [np.mean(Fitness)]

        for iter_num in range(self.Maxiter):
            # 1. Employed Bee Phase
            for i in range(self.n_eb):
                partner_idx = random.choice([x for x in range(self.n_eb) if x != i])
                new_MS = self.crossover_MS(Pop[i][0:self.Len], Pop[partner_idx][0:self.Len])
                new_OS = self.crossover_OS_MPOX(Pop[i][self.Len:], Pop[partner_idx][self.Len:])
                new_MS = self.mutate_MS(new_MS)
                new_CHS = np.hstack((new_MS, new_OS))

                d = Decode(self.J, self.Processing_time, self.M_num)
                new_fit = d.decode(new_CHS, self.Len)

                if new_fit < Fitness[i]:
                    Pop[i] = new_CHS
                    Fitness[i] = new_fit
                    if new_fit < gbest_fit:
                        gbest_fit = new_fit
                        gbest_chs = np.copy(new_CHS)

            # 2. Onlooker Bee Phase
            inv_fits = [1.0 / f for f in Fitness]
            sum_inv = sum(inv_fits)
            probs = [f / sum_inv for f in inv_fits]
            for i in range(self.n_eb):
                N_i = int(round(probs[i] * self.n_ob))
                if N_i > 0:
                    # IMPLEMENTED TABU SEARCH HERE
                    refined_chs, refined_fit = self.tabu_local_search(Pop[i], N_i, Fitness[i])
                    if refined_fit < Fitness[i]:
                        Pop[i] = refined_chs
                        Fitness[i] = refined_fit
                        if refined_fit < gbest_fit:
                            gbest_fit = refined_fit
                            gbest_chs = np.copy(refined_chs)

            # 3. Scout Bee Phase
            worst_indices = np.argsort(Fitness)[-self.n_sb:]
            for idx in worst_indices:
                scout_MS = self.init_machine_assignment()
                scout_OS = self.init_operation_sequence()
                scout_CHS = np.hstack((scout_MS, scout_OS))

                d = Decode(self.J, self.Processing_time, self.M_num)
                scout_fit = d.decode(scout_CHS, self.Len)

                if scout_fit < Fitness[idx]:
                    Pop[idx] = scout_CHS
                    Fitness[idx] = scout_fit
                    if scout_fit < gbest_fit:
                        gbest_fit = scout_fit
                        gbest_chs = np.copy(scout_CHS)

            # --- Update Trackers ---
            convergence_curve.append(gbest_fit)
            avg_convergence_curve.append(np.mean(Fitness))

        return gbest_fit, gbest_chs, convergence_curve, avg_convergence_curve