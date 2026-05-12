import math
import random
import numpy as np
from Decode import Decode


class Aquila_Optimizer_FJSSP:
    """
    Discrete Aquila Optimizer for FJSSP
    Improved version:
      - adaptive operator selection
      - elite intensification on top individuals
      - stronger OS sequence exploitation
      - light plateau escape
      - stronger best-solution polishing

    Still keeps the overall computational structure close to the original code.
    """

    def __init__(self, J, Processing_time, M_num, Pop_size, Maxiter):
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.N = Pop_size
        self.Maxiter = Maxiter

        self.T0 = sum(J.values())
        self.D = 2 * self.T0

        self.alpha = 0.10
        self.delta = 0.10

        # Per-operation feasibility data
        self.Feasible_Machines_Count = []
        self.Min_Proc_Time = []
        self.Feasible_Machines = []

        for _, ops in enumerate(Processing_time):
            for _, machine_times in enumerate(ops):
                feasible = [(m, t) for m, t in enumerate(machine_times) if t != 9999]
                self.Feasible_Machines_Count.append(len(feasible))
                self.Min_Proc_Time.append(min(t for _, t in feasible))
                self.Feasible_Machines.append(feasible)

        # Standard OS (job ids, 0-indexed)
        self.standard_OS_list = []
        for job_id, num_ops in self.J.items():
            self.standard_OS_list.extend([job_id - 1] * num_ops)
        self.standard_OS_list = np.array(self.standard_OS_list)

        # Flat-index to (job_idx, op_idx)
        self._flat_to_job_op = []
        for job_id, num_ops in self.J.items():
            for op_idx in range(num_ops):
                self._flat_to_job_op.append((job_id - 1, op_idx))

        # Job start flat-index map
        self._job_start_idx = {}
        cum = 0
        for job_id, num_ops in self.J.items():
            self._job_start_idx[job_id] = cum
            cum += num_ops

        # Adaptive operator memory
        self.op_stats = {
            "ms_refine": {"score": 1.0, "tries": 1},
            "os_refine": {"score": 1.0, "tries": 1},
            "mixed_refine": {"score": 1.0, "tries": 1},
            "diversify": {"score": 1.0, "tries": 1},
        }

    # =========================================================
    # Index helpers
    # =========================================================
    def flat_index_to_job_op(self, idx):
        return self._flat_to_job_op[idx]

    def relative_to_actual_machine(self, flat_idx, rel_idx):
        feasible = self.Feasible_Machines[flat_idx]
        return feasible[max(0, min(rel_idx, len(feasible) - 1))][0]

    def actual_to_relative_machine(self, flat_idx, actual_machine):
        for rel_idx, (m, _) in enumerate(self.Feasible_Machines[flat_idx]):
            if m == actual_machine:
                return rel_idx
        return 0

    def processing_time_of_choice(self, flat_idx, rel_idx):
        feasible = self.Feasible_Machines[flat_idx]
        return feasible[max(0, min(rel_idx, len(feasible) - 1))][1]

    # =========================================================
    # Fitness
    # =========================================================
    def evaluate_fitness(self, MS, OS):
        CHS = np.hstack((MS, OS))
        decoder = Decode(self.J, self.Processing_time, self.M_num)
        return decoder.decode(CHS, self.T0)

    # =========================================================
    # Machine-load vector
    # =========================================================
    def compute_machine_loads(self, ms):
        loads = np.zeros(self.M_num, dtype=np.float64)
        for idx in range(self.T0):
            m = self.relative_to_actual_machine(idx, ms[idx])
            loads[m] += self.processing_time_of_choice(idx, ms[idx])
        return loads

    def compute_population_mean_loads(self, X_MS):
        total = np.zeros(self.M_num, dtype=np.float64)
        for i in range(self.N):
            total += self.compute_machine_loads(X_MS[i])
        return total / self.N

    # =========================================================
    # Diversity / distance helpers
    # =========================================================
    def ms_distance(self, ms1, ms2):
        return np.sum(ms1 != ms2) / self.T0

    def pick_far_reference(self, X_MS, X_OS, fitness, base_ms, top_pool=None):
        if top_pool is None:
            top_pool = max(3, self.N // 4)
        idx = np.argsort(fitness)[:top_pool]
        if len(idx) == 1:
            i = idx[0]
            return np.copy(X_MS[i]), np.copy(X_OS[i])

        dists = [(self.ms_distance(base_ms, X_MS[i]), i) for i in idx]
        dists.sort(reverse=True)
        chosen = dists[random.randint(0, min(2, len(dists) - 1))][1]
        return np.copy(X_MS[chosen]), np.copy(X_OS[chosen])

    def pick_mixed_reference(self, X_MS, X_OS, fitness, base_ms):
        if random.random() < 0.5:
            elite_idx = np.argsort(fitness)[:max(3, self.N // 5)]
            chosen = random.choice(list(elite_idx))
            return np.copy(X_MS[chosen]), np.copy(X_OS[chosen])
        return self.pick_far_reference(X_MS, X_OS, fitness, base_ms)

    # =========================================================
    # Initialization
    # =========================================================
    def init_discrete_population(self):
        X_MS = np.zeros((self.N, self.T0), dtype=int)
        X_OS = np.zeros((self.N, self.T0), dtype=int)

        n_gs = max(1, int(self.N * 0.16))
        n_ls = max(1, int(self.N * 0.12))
        n_spt = max(1, int(self.N * 0.12))
        n_brd = max(1, int(self.N * 0.20))
        n_rnd = max(1, int(self.N * 0.15))

        for i in range(self.N):
            if i < n_gs:
                ms = self._init_ms_global_greedy()
            elif i < n_gs + n_ls:
                ms = self._init_ms_local_greedy()
            elif i < n_gs + n_ls + n_spt:
                ms = self._init_ms_spt()
            elif i < n_gs + n_ls + n_spt + n_brd:
                ms = self._init_ms_broad_biased()
            elif i < n_gs + n_ls + n_spt + n_brd + n_rnd:
                ms = self._init_ms_random()
            else:
                ms = self._init_ms_hybrid_random()

            X_MS[i] = ms
            X_OS[i] = self._init_os(i)

        return X_MS, X_OS

    def _init_ms_global_greedy(self):
        ms = np.zeros(self.T0, dtype=int)
        loads = np.zeros(self.M_num, dtype=np.float64)
        for idx in range(self.T0):
            feasible = self.Feasible_Machines[idx]
            best_rel = min(range(len(feasible)),
                           key=lambda r: loads[feasible[r][0]] + feasible[r][1])
            ms[idx] = best_rel
            loads[feasible[best_rel][0]] += feasible[best_rel][1]
        return ms

    def _init_ms_local_greedy(self):
        ms = np.zeros(self.T0, dtype=int)
        for job_id, num_ops in self.J.items():
            loads = np.zeros(self.M_num, dtype=np.float64)
            start = self._job_start_idx[job_id]
            for op_idx in range(num_ops):
                flat_idx = start + op_idx
                feasible = self.Feasible_Machines[flat_idx]
                best_rel = min(range(len(feasible)),
                               key=lambda r: loads[feasible[r][0]] + feasible[r][1])
                ms[flat_idx] = best_rel
                loads[feasible[best_rel][0]] += feasible[best_rel][1]
        return ms

    def _init_ms_spt(self):
        ms = np.zeros(self.T0, dtype=int)
        for idx in range(self.T0):
            feasible = self.Feasible_Machines[idx]
            ms[idx] = min(range(len(feasible)), key=lambda r: feasible[r][1])
        return ms

    def _init_ms_broad_biased(self):
        ms = np.zeros(self.T0, dtype=int)
        for idx in range(self.T0):
            feasible = self.Feasible_Machines[idx]
            n = len(feasible)
            if n == 1:
                ms[idx] = 0
                continue

            sorted_rel = sorted(range(n), key=lambda r: feasible[r][1])
            rv = random.random()
            if rv < 0.35:
                ms[idx] = sorted_rel[0]
            elif rv < 0.60:
                ms[idx] = sorted_rel[min(1, n - 1)]
            elif rv < 0.80:
                ms[idx] = sorted_rel[min(2, n - 1)]
            else:
                ms[idx] = random.randint(0, n - 1)
        return ms

    def _init_ms_hybrid_random(self):
        ms = np.zeros(self.T0, dtype=int)
        greedy = self._init_ms_global_greedy()
        rnd = self._init_ms_random()
        for idx in range(self.T0):
            if random.random() < 0.45:
                ms[idx] = greedy[idx]
            else:
                ms[idx] = rnd[idx]
        return ms

    def _init_ms_random(self):
        ms = np.zeros(self.T0, dtype=int)
        for idx in range(self.T0):
            ms[idx] = random.randint(0, self.Feasible_Machines_Count[idx] - 1)
        return ms

    def _init_os(self, individual_idx):
        if individual_idx % 4 == 1:
            return self._mor_os()
        elif individual_idx % 4 == 2:
            os_copy = self.standard_OS_list.copy()
            np.random.shuffle(os_copy)
            if self.T0 > 4:
                i, j = sorted(random.sample(range(self.T0), 2))
                os_copy[i:j + 1] = os_copy[i:j + 1][::-1]
            return os_copy
        elif individual_idx % 4 == 3:
            return self._block_diverse_os()
        else:
            os_copy = self.standard_OS_list.copy()
            np.random.shuffle(os_copy)
            return os_copy

    def _mor_os(self):
        remaining = {job_id - 1: num_ops for job_id, num_ops in self.J.items()}
        result = []
        while sum(remaining.values()) > 0:
            candidates = [j for j, r in remaining.items() if r > 0]
            max_rem = max(remaining[j] for j in candidates)
            top = [j for j in candidates if remaining[j] == max_rem]
            chosen = random.choice(top)
            result.append(chosen)
            remaining[chosen] -= 1
        return np.array(result, dtype=self.standard_OS_list.dtype)

    def _block_diverse_os(self):
        os_copy = self.standard_OS_list.copy()
        block = max(2, self.T0 // 8)
        lst = list(os_copy)
        random.shuffle(lst)
        chunks = [lst[i:i + block] for i in range(0, len(lst), block)]
        random.shuffle(chunks)
        mixed = [x for c in chunks for x in c]
        return np.array(mixed, dtype=self.standard_OS_list.dtype)

    # =========================================================
    # MS operators
    # =========================================================
    def mutate_ms(self, ms, num_mutations):
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            n = self.Feasible_Machines_Count[idx]
            if n > 1:
                ms[idx] = random.randint(0, n - 1)

    def guided_ms_mutation(self, ms, num_mutations):
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            feasible = self.Feasible_Machines[idx]
            if len(feasible) <= 1:
                continue
            times = np.array([t for _, t in feasible], dtype=np.float64)
            weights = 1.0 / (times + 1e-9)
            weights /= weights.sum()
            ms[idx] = int(np.random.choice(len(feasible), p=weights))

    def exploratory_ms_mutation(self, ms, num_mutations):
        for _ in range(num_mutations):
            idx = random.randint(0, self.T0 - 1)
            feasible = self.Feasible_Machines[idx]
            n = len(feasible)
            if n <= 1:
                continue
            sorted_rel = sorted(range(n), key=lambda r: feasible[r][1])
            rv = random.random()
            if rv < 0.25:
                ms[idx] = sorted_rel[0]
            elif rv < 0.50:
                ms[idx] = sorted_rel[min(1, n - 1)]
            elif rv < 0.75:
                ms[idx] = sorted_rel[min(2, n - 1)]
            else:
                ms[idx] = random.randint(0, n - 1)

    def bottleneck_ms_refine(self, ms, loads=None):
        if loads is None:
            loads = self.compute_machine_loads(ms)

        busiest = int(np.argmax(loads))
        ops_on_busiest = []

        for idx in range(self.T0):
            m = self.relative_to_actual_machine(idx, ms[idx])
            if m == busiest:
                t = self.processing_time_of_choice(idx, ms[idx])
                ops_on_busiest.append((t, idx))

        if not ops_on_busiest:
            return False

        ops_on_busiest.sort(reverse=True)

        best_move = None
        best_peak = np.max(loads)
        best_var = float("inf")

        for pt, idx in ops_on_busiest[:min(8, len(ops_on_busiest))]:
            feasible = self.Feasible_Machines[idx]
            for alt_m, alt_t in feasible:
                if alt_m == busiest:
                    continue
                new_loads = loads.copy()
                new_loads[busiest] -= pt
                new_loads[alt_m] += alt_t
                new_peak = np.max(new_loads)
                new_var = np.var(new_loads)
                if (new_peak < best_peak) or (new_peak == best_peak and new_var < best_var):
                    best_peak = new_peak
                    best_var = new_var
                    best_move = (idx, alt_m)

        if best_move is not None:
            idx, alt_m = best_move
            ms[idx] = self.actual_to_relative_machine(idx, alt_m)
            return True
        return False

    def multi_bottleneck_refine(self, ms, num_passes=2):
        for _ in range(num_passes):
            loads = self.compute_machine_loads(ms)
            if not self.bottleneck_ms_refine(ms, loads):
                break

    def load_balance_ms(self, ms, mean_loads, t, T):
        factor = 1.0 - t / T
        loads = self.compute_machine_loads(ms)
        global_mean = np.mean(loads)
        threshold = global_mean * (1.0 + factor * 0.55)

        overloaded = {m for m in range(self.M_num) if loads[m] > threshold}
        underloaded = {m for m in range(self.M_num) if loads[m] < global_mean}

        if not overloaded or not underloaded:
            if random.random() < 0.5:
                self.bottleneck_ms_refine(ms, loads)
            return

        candidates = []
        for idx in range(self.T0):
            curr_m = self.relative_to_actual_machine(idx, ms[idx])
            if curr_m not in overloaded:
                continue
            curr_t = self.processing_time_of_choice(idx, ms[idx])
            for alt_m, alt_t in self.Feasible_Machines[idx]:
                if alt_m in underloaded and alt_m != curr_m:
                    gain = (loads[curr_m] - loads[alt_m] - alt_t + curr_t)
                    gain += 0.35 * (mean_loads[curr_m] - mean_loads[alt_m])
                    candidates.append((gain, idx, alt_m))

        if candidates:
            candidates.sort(reverse=True)
            _, idx, alt_m = random.choice(candidates[:min(3, len(candidates))])
            ms[idx] = self.actual_to_relative_machine(idx, alt_m)
        else:
            if random.random() < 0.6:
                self.exploratory_ms_mutation(ms, 1)
            else:
                self.bottleneck_ms_refine(ms, loads)

    def levy_ms_step(self, ms, best_MS, ref1_MS, ref2_MS=None, beta=1.5):
        sigma = (math.gamma(1 + beta) * math.sin(math.pi * beta / 2) /
                 (math.gamma((1 + beta) / 2) * beta *
                  2 ** ((beta - 1) / 2))) ** (1 / beta)
        u = random.gauss(0, sigma)
        v = abs(random.gauss(0, 1)) + 1e-9
        step = abs(u / (v ** (1 / beta)))

        n_flips = max(2, min(int(step * self.T0 / 16.0), max(2, self.T0 // 3)))
        positions = random.sample(range(self.T0), min(n_flips, self.T0))

        for idx in positions:
            rv = random.random()
            if rv < 0.30:
                ms[idx] = best_MS[idx]
            elif rv < 0.60:
                ms[idx] = ref1_MS[idx]
            elif rv < 0.82 and ref2_MS is not None:
                ms[idx] = ref2_MS[idx]
            else:
                feasible = self.Feasible_Machines[idx]
                n = len(feasible)
                if n > 1:
                    sorted_rel = sorted(range(n), key=lambda r: feasible[r][1])
                    if random.random() < 0.6:
                        ms[idx] = sorted_rel[random.randint(0, min(2, n - 1))]
                    else:
                        ms[idx] = random.randint(0, n - 1)

    def qf_ms_step(self, ms, best_MS, t, T):
        rand_val = random.random()
        try:
            exp = (2 * rand_val - 1) / ((1 - t / T) ** 2 + 1e-9)
            exp = max(-50.0, min(50.0, exp))
            QF = float(t) ** exp
        except (ValueError, ZeroDivisionError, OverflowError):
            QF = 1.0
        QF = min(max(QF, 0.0), 2.0)
        G2 = 2.0 * (1.0 - t / T)

        n_changes = max(1, min(self.T0 // 6, 2 + int(QF * 3)))
        positions = random.sample(range(self.T0), n_changes)

        for idx in positions:
            feasible = self.Feasible_Machines[idx]
            n = len(feasible)
            if n <= 1:
                continue
            rv = random.random()
            if rv < 0.55:
                ms[idx] = best_MS[idx]
            elif rv < 0.55 + G2 * 0.20:
                sorted_rel = sorted(range(n), key=lambda r: feasible[r][1])
                ms[idx] = sorted_rel[min(1, n - 1)]
            else:
                if random.random() < 0.35:
                    ms[idx] = random.randint(0, n - 1)

    # =========================================================
    # OS operators
    # =========================================================
    def swap_os(self, os_arr, num_swaps):
        for _ in range(num_swaps):
            i, j = random.sample(range(self.T0), 2)
            os_arr[i], os_arr[j] = os_arr[j], os_arr[i]

    def insert_os(self, os_arr, num_inserts):
        for _ in range(num_inserts):
            i, j = random.sample(range(self.T0), 2)
            lst = list(os_arr)
            val = lst.pop(i)
            lst.insert(j, val)
            os_arr[:] = np.array(lst, dtype=os_arr.dtype)

    def invert_os(self, os_arr, num_inversions):
        for _ in range(num_inversions):
            i, j = sorted(random.sample(range(self.T0), 2))
            os_arr[i:j + 1] = os_arr[i:j + 1][::-1]

    def adjacent_job_swap_os(self, os_arr):
        for _ in range(self.T0):
            i = random.randint(0, self.T0 - 2)
            if os_arr[i] != os_arr[i + 1]:
                os_arr[i], os_arr[i + 1] = os_arr[i + 1], os_arr[i]
                return

    def block_insert_os(self, os_arr, block_len=None):
        if self.T0 < 4:
            self.insert_os(os_arr, 1)
            return
        if block_len is None:
            block_len = max(2, min(6, self.T0 // 10))
        i = random.randint(0, self.T0 - block_len)
        j = random.randint(0, self.T0 - block_len)
        if i == j:
            return
        lst = list(os_arr)
        block = lst[i:i + block_len]
        del lst[i:i + block_len]
        if j > len(lst):
            j = len(lst)
        lst[j:j] = block
        os_arr[:] = np.array(lst, dtype=os_arr.dtype)

    def spiral_os_perturbation(self, os_arr, ref_OS, r_spiral):
        from collections import Counter
        n_copy = max(1, min(int(r_spiral * self.T0), self.T0 - 1))
        positions = sorted(random.sample(range(self.T0), n_copy))
        result = list(os_arr)
        need = {}
        for p in positions:
            v = ref_OS[p]
            need[v] = need.get(v, 0) + 1

        placed = {v: 0 for v in need}
        for p in positions:
            v = ref_OS[p]
            if placed[v] < need[v]:
                result[p] = v
                placed[v] += 1

        if Counter(result) == Counter(self.standard_OS_list):
            os_arr[:] = np.array(result, dtype=os_arr.dtype)

    def perturb_os(self, os_arr, strength, phase_ratio):
        for _ in range(max(1, strength)):
            r = random.random()
            if phase_ratio < 0.33:
                if r < 0.18:
                    self.swap_os(os_arr, 1)
                elif r < 0.42:
                    self.insert_os(os_arr, 1)
                elif r < 0.64:
                    self.invert_os(os_arr, 1)
                elif r < 0.84:
                    self.block_insert_os(os_arr)
                else:
                    self.adjacent_job_swap_os(os_arr)
            elif phase_ratio < 0.66:
                if r < 0.20:
                    self.swap_os(os_arr, 1)
                elif r < 0.45:
                    self.insert_os(os_arr, 1)
                elif r < 0.67:
                    self.invert_os(os_arr, 1)
                elif r < 0.84:
                    self.block_insert_os(os_arr)
                else:
                    self.adjacent_job_swap_os(os_arr)
            else:
                if r < 0.20:
                    self.swap_os(os_arr, 1)
                elif r < 0.48:
                    self.insert_os(os_arr, 1)
                elif r < 0.68:
                    self.invert_os(os_arr, 1)
                else:
                    self.adjacent_job_swap_os(os_arr)

    # =========================================================
    # Adaptive operator memory
    # =========================================================
    def choose_operator(self, names):
        weights = []
        for name in names:
            s = self.op_stats[name]["score"]
            tr = self.op_stats[name]["tries"]
            weights.append(max(0.1, s / tr))
        weights = np.array(weights, dtype=np.float64)
        weights /= weights.sum()
        return np.random.choice(names, p=weights)

    def update_operator(self, name, improved, gain=0.0):
        self.op_stats[name]["tries"] += 1
        if improved:
            self.op_stats[name]["score"] += 1.0 + max(0.0, gain)
        else:
            self.op_stats[name]["score"] += 0.02

    # =========================================================
    # Elite archive & center
    # =========================================================
    def elite_archive(self, X_MS, X_OS, fitness, k):
        idx = np.argsort(fitness)[:k]
        return ([np.copy(X_MS[i]) for i in idx],
                [np.copy(X_OS[i]) for i in idx],
                [fitness[i] for i in idx])

    def elite_center(self, X_MS, X_OS, fitness, top_k):
        top_k = max(1, min(top_k, len(fitness)))
        elite_idx = np.argsort(fitness)[:top_k]
        center_MS = np.zeros(self.T0, dtype=int)
        for j in range(self.T0):
            col = [X_MS[i][j] for i in elite_idx]
            vals, counts = np.unique(col, return_counts=True)
            center_MS[j] = vals[np.argmax(counts)]
        center_OS = np.copy(X_OS[elite_idx[0]])
        return center_MS, center_OS

    # =========================================================
    # Population diversity
    # =========================================================
    def population_entropy(self, X_MS):
        n_samples = min(50, self.N * (self.N - 1) // 2)
        if n_samples == 0:
            return 1.0
        total = 0.0
        for _ in range(n_samples):
            i, j = random.sample(range(self.N), 2)
            total += np.sum(X_MS[i] != X_MS[j]) / self.T0
        return total / n_samples

    def inject_diversity(self, X_MS, X_OS, fitness, archive_MS, archive_OS,
                         iter_num, frac=0.20):
        sorted_idx = np.argsort(fitness)
        num_replace = max(1, int(self.N * frac))
        worst_idx = sorted_idx[-num_replace:]
        phase_ratio = iter_num / self.Maxiter

        for pos in worst_idx:
            r = random.random()
            if r < 0.30 and archive_MS:
                ref = random.randint(0, len(archive_MS) - 1)
                X_MS[pos] = np.copy(archive_MS[ref])
                X_OS[pos] = np.copy(archive_OS[ref])
                burst = max(4, self.T0 // 5)
                self.exploratory_ms_mutation(X_MS[pos], burst)
                self.perturb_os(X_OS[pos], burst, phase_ratio)
            elif r < 0.55:
                X_MS[pos] = self._init_ms_broad_biased()
                X_OS[pos] = self._block_diverse_os()
            elif r < 0.80:
                X_MS[pos] = self._init_ms_hybrid_random()
                X_OS[pos] = self._init_os(pos + iter_num)
            else:
                X_MS[pos] = self._init_ms_random()
                os_tmp = self.standard_OS_list.copy()
                np.random.shuffle(os_tmp)
                X_OS[pos] = os_tmp

            fitness[pos] = self.evaluate_fitness(X_MS[pos], X_OS[pos])

    # =========================================================
    # Adaptive burst size
    # =========================================================
    def levy_burst(self, iter_num, stagnation_level=0, slow_convergence=False):
        phase = iter_num / self.Maxiter
        extra = 0
        if stagnation_level >= 3:
            extra += 1
        if stagnation_level >= 6:
            extra += 1
        if slow_convergence:
            extra += 1

        if phase < 0.33:
            base = random.choice([3, 4, 5, 6])
        elif phase < 0.66:
            base = random.choice([2, 3, 4, 5])
        else:
            base = random.choice([2, 2, 3, 4])

        return min(base + extra, max(4, self.T0 // 3))

    # =========================================================
    # Reactivation
    # =========================================================
    def reactivate_exploration(self, X_MS, X_OS, fitness, archive_MS, archive_OS,
                               iter_num):
        sorted_idx = np.argsort(fitness)
        num_reset = max(1, self.N // 8)
        worst_idx = sorted_idx[-num_reset:]
        phase_ratio = iter_num / self.Maxiter

        for pos in worst_idx:
            ref = random.randint(0, len(archive_MS) - 1)
            X_MS[pos] = np.copy(archive_MS[ref])
            X_OS[pos] = np.copy(archive_OS[ref])

            burst = self.levy_burst(iter_num, stagnation_level=10)
            self.exploratory_ms_mutation(X_MS[pos], max(2, burst // 2))
            if random.random() < 0.5:
                self.multi_bottleneck_refine(X_MS[pos], num_passes=1)
            self.perturb_os(X_OS[pos], burst, phase_ratio)

            fitness[pos] = self.evaluate_fitness(X_MS[pos], X_OS[pos])

    # =========================================================
    # Stronger OS exploitation
    # =========================================================
    def os_intensify(self, ms, os_arr, cur_fit, rounds=4):
        best_ms = np.copy(ms)
        best_os = np.copy(os_arr)
        best_fit = cur_fit

        for _ in range(rounds):
            candidates = []

            tmp = np.copy(best_os)
            self.insert_os(tmp, 1)
            candidates.append((best_ms, tmp))

            tmp = np.copy(best_os)
            self.adjacent_job_swap_os(tmp)
            candidates.append((best_ms, tmp))

            tmp = np.copy(best_os)
            self.invert_os(tmp, 1)
            candidates.append((best_ms, tmp))

            tmp = np.copy(best_os)
            self.block_insert_os(tmp)
            candidates.append((best_ms, tmp))

            improved = False
            local_best_fit = best_fit
            local_best_os = np.copy(best_os)

            for cand_ms, cand_os in candidates:
                fit = self.evaluate_fitness(cand_ms, cand_os)
                if fit < local_best_fit:
                    local_best_fit = fit
                    local_best_os = np.copy(cand_os)
                    improved = True

            if improved:
                best_fit = local_best_fit
                best_os = local_best_os
            else:
                break

        return best_ms, best_os, best_fit

    def mixed_intensify(self, ms, os_arr, cur_fit, phase_ratio):
        best_ms = np.copy(ms)
        best_os = np.copy(os_arr)
        best_fit = cur_fit

        for _ in range(3):
            cand_ms = np.copy(best_ms)
            cand_os = np.copy(best_os)

            if random.random() < 0.65:
                self.multi_bottleneck_refine(cand_ms, num_passes=2)
            else:
                self.guided_ms_mutation(cand_ms, 1)

            if random.random() < 0.55:
                self.insert_os(cand_os, 1)
            else:
                self.adjacent_job_swap_os(cand_os)

            if random.random() < 0.25:
                self.perturb_os(cand_os, 1, phase_ratio)

            fit = self.evaluate_fitness(cand_ms, cand_os)
            if fit < best_fit:
                best_fit = fit
                best_ms = cand_ms
                best_os = cand_os
            else:
                break

        return best_ms, best_os, best_fit

    # =========================================================
    # Best-solution polishing
    # =========================================================
    def refine_best(self, best_MS, best_OS, best_fit, iter_num):
        cur_MS, cur_OS, cur_fit = np.copy(best_MS), np.copy(best_OS), best_fit
        phase_ratio = iter_num / self.Maxiter

        for _ in range(8):
            op = self.choose_operator(["ms_refine", "os_refine", "mixed_refine"])

            if op == "ms_refine":
                cand_MS = np.copy(cur_MS)
                cand_OS = np.copy(cur_OS)
                if random.random() < 0.65:
                    self.multi_bottleneck_refine(cand_MS, num_passes=2)
                else:
                    self.guided_ms_mutation(cand_MS, 1)
                fit = self.evaluate_fitness(cand_MS, cand_OS)

            elif op == "os_refine":
                cand_MS, cand_OS, fit = self.os_intensify(cur_MS, cur_OS, cur_fit, rounds=3)

            else:
                cand_MS, cand_OS, fit = self.mixed_intensify(cur_MS, cur_OS, cur_fit, phase_ratio)

            improved = fit < cur_fit
            gain = (cur_fit - fit) / max(1.0, cur_fit) if improved else 0.0
            self.update_operator(op, improved, gain)

            if improved:
                cur_fit, cur_MS, cur_OS = fit, cand_MS, cand_OS

        return cur_MS, cur_OS, cur_fit

    # =========================================================
    # Local search
    # =========================================================
    def local_search(self, start_MS, start_OS, start_fit,
                     num_steps=10, neighbors_per_step=6):
        cur_MS, cur_OS = np.copy(start_MS), np.copy(start_OS)
        best_MS, best_OS, best_fit = np.copy(start_MS), np.copy(start_OS), start_fit
        no_improve = 0

        for _ in range(num_steps):
            hood = []
            for _ in range(neighbors_per_step):
                tmp_MS, tmp_OS = np.copy(cur_MS), np.copy(cur_OS)
                r = random.random()
                if r < 0.18:
                    self.multi_bottleneck_refine(tmp_MS, num_passes=2)
                elif r < 0.34:
                    self.guided_ms_mutation(tmp_MS, 1)
                elif r < 0.48:
                    self.exploratory_ms_mutation(tmp_MS, 1)
                elif r < 0.66:
                    tmp_MS, tmp_OS, _ = self.os_intensify(tmp_MS, tmp_OS, self.evaluate_fitness(tmp_MS, tmp_OS), rounds=2)
                elif r < 0.82:
                    self.insert_os(tmp_OS, 1)
                else:
                    self.block_insert_os(tmp_OS)

                hood.append((self.evaluate_fitness(tmp_MS, tmp_OS), tmp_MS, tmp_OS))

            hood.sort(key=lambda x: x[0])
            if hood and hood[0][0] < best_fit:
                cur_MS, cur_OS = np.copy(hood[0][1]), np.copy(hood[0][2])
                best_MS, best_OS, best_fit = np.copy(hood[0][1]), np.copy(hood[0][2]), hood[0][0]
                no_improve = 0
            elif hood and hood[0][0] <= best_fit * 1.01:
                cur_MS, cur_OS = np.copy(hood[0][1]), np.copy(hood[0][2])
                no_improve += 1
            else:
                no_improve += 1

            if no_improve > 4:
                break

        return best_MS, best_OS, best_fit

    # =========================================================
    # Elite intensification
    # =========================================================
    def intensify_elite_pool(self, X_MS, X_OS, fitness, iter_num):
        top_k = max(2, min(5, self.N // 8))
        elite_idx = np.argsort(fitness)[:top_k]
        phase_ratio = iter_num / self.Maxiter

        for idx in elite_idx:
            cur_fit = fitness[idx]
            op = self.choose_operator(["os_refine", "mixed_refine"])

            if op == "os_refine":
                cand_MS, cand_OS, cand_fit = self.os_intensify(X_MS[idx], X_OS[idx], cur_fit, rounds=3)
            else:
                cand_MS, cand_OS, cand_fit = self.mixed_intensify(X_MS[idx], X_OS[idx], cur_fit, phase_ratio)

            improved = cand_fit < cur_fit
            gain = (cur_fit - cand_fit) / max(1.0, cur_fit) if improved else 0.0
            self.update_operator(op, improved, gain)

            if improved:
                X_MS[idx] = cand_MS
                X_OS[idx] = cand_OS
                fitness[idx] = cand_fit

    # =========================================================
    # ILS kick
    # =========================================================
    def ils_kick(self, best_MS, best_OS, best_fit, iter_num):
        phase_ratio = iter_num / self.Maxiter
        kicked_MS, kicked_OS = np.copy(best_MS), np.copy(best_OS)
        intensity = max(3, self.T0 // 7)
        self.exploratory_ms_mutation(kicked_MS, intensity)
        if random.random() < 0.5:
            self.multi_bottleneck_refine(kicked_MS, num_passes=1)
        self.perturb_os(kicked_OS, intensity, phase_ratio)
        kicked_fit = self.evaluate_fitness(kicked_MS, kicked_OS)
        ms_r, os_r, fit_r = self.local_search(
            kicked_MS, kicked_OS, kicked_fit, num_steps=7, neighbors_per_step=5
        )
        return (ms_r, os_r, fit_r) if fit_r < best_fit else (best_MS, best_OS, best_fit)

    # =========================================================
    # Main AO loop
    # =========================================================
    def optimize(self):
        X_MS, X_OS = self.init_discrete_population()
        fitness = np.array([self.evaluate_fitness(X_MS[i], X_OS[i]) for i in range(self.N)], dtype=np.float64)

        best_idx = int(np.argmin(fitness))
        BEST_MS = np.copy(X_MS[best_idx])
        BEST_OS = np.copy(X_OS[best_idx])
        Bestfitval = float(fitness[best_idx])

        convergence_history = [Bestfitval]
        iter_num = 1
        stagnation_counter = 0
        recent_improvements = []
        diversity_injected = False
        phase_boundary = 0.60

        while iter_num <= self.Maxiter:
            prev_best = Bestfitval
            phase_ratio = iter_num / self.Maxiter

            archive_MS, archive_OS, archive_fit = self.elite_archive(
                X_MS, X_OS, fitness, k=max(3, self.N // 6)
            )
            Elite_MS = np.copy(archive_MS[0])
            Elite_OS = np.copy(archive_OS[0])

            entropy_now = self.population_entropy(X_MS)
            slow_convergence = False
            if len(recent_improvements) >= 10:
                slow_convergence = (sum(recent_improvements[-10:]) <= 2)

            Center_MS, Center_OS = self.elite_center(
                X_MS, X_OS, fitness, top_k=max(2, self.N // 5)
            )
            XM_loads = self.compute_population_mean_loads(X_MS)

            if iter_num % 4 == 0 and iter_num > int(0.25 * self.Maxiter):
                self.intensify_elite_pool(X_MS, X_OS, fitness, iter_num)
                best_idx2 = int(np.argmin(fitness))
                if fitness[best_idx2] < Bestfitval:
                    Bestfitval = float(fitness[best_idx2])
                    BEST_MS = np.copy(X_MS[best_idx2])
                    BEST_OS = np.copy(X_OS[best_idx2])

            r1 = random.uniform(1, 20)
            D1 = max(1, int(random.uniform(1, self.T0)))
            theta = -0.005 * D1 + 1.5 * math.pi
            r_spiral = abs(math.cos(theta)) * (r1 + 0.00565 * D1) / 22.0

            for i in range(self.N):
                MS_new = np.copy(X_MS[i])
                OS_new = np.copy(X_OS[i])

                if iter_num <= phase_boundary * self.Maxiter:
                    if random.random() < 0.58:
                        if iter_num <= 0.40 * self.Maxiter:
                            q = 1.0 - 0.7 * phase_ratio
                        else:
                            shifted = iter_num - 0.40 * self.Maxiter
                            q = math.exp(-1.6 * shifted / (self.Maxiter * phase_boundary))

                        Ref1_MS, Ref1_OS = self.pick_mixed_reference(X_MS, X_OS, fitness, X_MS[i])
                        Ref2_MS, Ref2_OS = self.pick_far_reference(X_MS, X_OS, fitness, X_MS[i])

                        for j in range(self.T0):
                            rv = random.random()
                            if rv < 0.08 * q:
                                MS_new[j] = BEST_MS[j]
                            elif rv < 0.22 * q:
                                MS_new[j] = Elite_MS[j]
                            elif rv < 0.42:
                                MS_new[j] = Center_MS[j]
                            elif rv < 0.66:
                                MS_new[j] = Ref1_MS[j]
                            elif rv < 0.85:
                                MS_new[j] = Ref2_MS[j]
                            else:
                                MS_new[j] = X_MS[i][j]

                        r_os = random.random()
                        if r_os < 0.35:
                            OS_new = np.copy(Ref1_OS)
                        elif r_os < 0.60:
                            OS_new = np.copy(Ref2_OS)
                        elif r_os < 0.80:
                            OS_new = np.copy(Center_OS)
                        else:
                            OS_new = np.copy(X_OS[i])

                        burst = self.levy_burst(iter_num, stagnation_counter, slow_convergence)
                        if entropy_now < 0.18:
                            burst += 1

                        self.load_balance_ms(MS_new, XM_loads, iter_num, self.Maxiter)

                        rv2 = random.random()
                        if rv2 < 0.45:
                            self.exploratory_ms_mutation(MS_new, max(1, burst // 2))
                        elif rv2 < 0.70:
                            self.guided_ms_mutation(MS_new, max(1, burst // 3))
                        else:
                            self.mutate_ms(MS_new, max(1, burst // 3))

                        self.perturb_os(OS_new, max(1, burst // 2), phase_ratio)
                        if random.random() < 0.35:
                            self.block_insert_os(OS_new)

                    else:
                        Ref1_MS, Ref1_OS = self.pick_mixed_reference(X_MS, X_OS, fitness, X_MS[i])
                        Ref2_MS, Ref2_OS = self.pick_far_reference(X_MS, X_OS, fitness, X_MS[i])

                        self.levy_ms_step(MS_new, BEST_MS, Ref1_MS, Ref2_MS)

                        selector = random.random()
                        if selector < 0.40:
                            OS_new = np.copy(Ref1_OS)
                        elif selector < 0.75:
                            OS_new = np.copy(Ref2_OS)
                        else:
                            OS_new = np.copy(Center_OS)

                        self.spiral_os_perturbation(OS_new, Ref1_OS, r_spiral)

                        burst = self.levy_burst(iter_num, stagnation_counter, slow_convergence)
                        self.perturb_os(OS_new, max(1, burst // 2), phase_ratio)
                        if random.random() < 0.30:
                            self.block_insert_os(OS_new)

                else:
                    mode = random.random()

                    if mode < 0.35:
                        for j in range(self.T0):
                            rv = random.random()
                            if rv < 0.70:
                                MS_new[j] = BEST_MS[j]
                            elif rv < 0.86:
                                MS_new[j] = Center_MS[j]
                            else:
                                MS_new[j] = Elite_MS[j]

                        OS_new = np.copy(BEST_OS if random.random() < 0.82 else Elite_OS)
                        self.multi_bottleneck_refine(MS_new, num_passes=2)
                        _, OS_new, _ = self.os_intensify(MS_new, OS_new, self.evaluate_fitness(MS_new, OS_new), rounds=2)

                    elif mode < 0.70:
                        MS_new = np.copy(BEST_MS)
                        OS_new = np.copy(BEST_OS)
                        self.qf_ms_step(MS_new, BEST_MS, iter_num, self.Maxiter)
                        self.multi_bottleneck_refine(MS_new, num_passes=1)
                        _, OS_new, _ = self.os_intensify(MS_new, OS_new, self.evaluate_fitness(MS_new, OS_new), rounds=2)

                    else:
                        Ref1_MS, Ref1_OS = self.pick_mixed_reference(X_MS, X_OS, fitness, X_MS[i])
                        for j in range(self.T0):
                            rv = random.random()
                            if rv < 0.55:
                                MS_new[j] = BEST_MS[j]
                            elif rv < 0.80:
                                MS_new[j] = Center_MS[j]
                            else:
                                MS_new[j] = Ref1_MS[j]
                        OS_new = np.copy(BEST_OS if random.random() < 0.65 else Ref1_OS)
                        self.multi_bottleneck_refine(MS_new, num_passes=1)
                        if random.random() < 0.5:
                            self.insert_os(OS_new, 1)
                        else:
                            self.adjacent_job_swap_os(OS_new)

                fit_new = self.evaluate_fitness(MS_new, OS_new)

                accept = False
                if fit_new < fitness[i]:
                    accept = True
                elif fit_new == fitness[i] and phase_ratio > 0.45 and random.random() < 0.12:
                    accept = True
                elif fit_new <= fitness[i] * 1.005 and 0.35 < phase_ratio < 0.75 and random.random() < 0.04:
                    accept = True

                if accept:
                    X_MS[i], X_OS[i] = np.copy(MS_new), np.copy(OS_new)
                    fitness[i] = fit_new
                    if fit_new < Bestfitval:
                        Bestfitval = fit_new
                        BEST_MS, BEST_OS = np.copy(MS_new), np.copy(OS_new)

            improved = Bestfitval < prev_best
            recent_improvements.append(improved)
            if len(recent_improvements) > 20:
                recent_improvements.pop(0)

            stagnation_counter = 0 if improved else stagnation_counter + 1
            if improved:
                diversity_injected = False

            if iter_num > int(0.20 * self.Maxiter) and stagnation_counter >= 2:
                r_MS, r_OS, r_fit = self.refine_best(BEST_MS, BEST_OS, Bestfitval, iter_num)
                if r_fit < Bestfitval:
                    Bestfitval, BEST_MS, BEST_OS = r_fit, r_MS, r_OS
                    stagnation_counter = 0

            if stagnation_counter >= 4 and not diversity_injected:
                if self.population_entropy(X_MS) < 0.14:
                    self.inject_diversity(X_MS, X_OS, fitness, archive_MS, archive_OS, iter_num, frac=0.26)
                    diversity_injected = True
                    best_idx = int(np.argmin(fitness))
                    if fitness[best_idx] < Bestfitval:
                        Bestfitval = fitness[best_idx]
                        BEST_MS = np.copy(X_MS[best_idx])
                        BEST_OS = np.copy(X_OS[best_idx])

            if stagnation_counter >= 6:
                best_idx = int(np.argmin(fitness))
                r_MS, r_OS, r_fit = self.local_search(
                    X_MS[best_idx], X_OS[best_idx], fitness[best_idx],
                    num_steps=10, neighbors_per_step=6
                )
                X_MS[best_idx], X_OS[best_idx] = np.copy(r_MS), np.copy(r_OS)
                fitness[best_idx] = r_fit

                if r_fit < Bestfitval:
                    Bestfitval, BEST_MS, BEST_OS = r_fit, r_MS, r_OS
                    stagnation_counter = 0
                else:
                    self.reactivate_exploration(X_MS, X_OS, fitness, archive_MS, archive_OS, iter_num)
                    best_idx2 = int(np.argmin(fitness))
                    if fitness[best_idx2] < Bestfitval:
                        Bestfitval = fitness[best_idx2]
                        BEST_MS = np.copy(X_MS[best_idx2])
                        BEST_OS = np.copy(X_OS[best_idx2])
                    stagnation_counter = 0

            if (iter_num > int(0.45 * self.Maxiter) and
                    iter_num % max(1, self.Maxiter // 10) == 0):
                BEST_MS, BEST_OS, Bestfitval = self.ils_kick(BEST_MS, BEST_OS, Bestfitval, iter_num)

            convergence_history.append(Bestfitval)
            iter_num += 1

        return np.hstack((BEST_MS, BEST_OS)), Bestfitval, convergence_history