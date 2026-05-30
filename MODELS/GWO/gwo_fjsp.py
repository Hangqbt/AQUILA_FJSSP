import numpy as np
import random
import copy
from Decode import Decode


class Discrete_GWO_FJSSP:
    def __init__(self, J, Processing_time, M_num, Pop_size=300, Maxiter=50):
        self.J = J
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.Pop_size = Pop_size
        self.Maxiter = Maxiter

        self.J_num = len(J)

        # J uses 1-based indexing in Instance.py (1 to J_num)
        self.total_operations = sum([self.J[i + 1] for i in range(self.J_num)])

        # Pre-calculate the number of feasible machines for every operation.
        # This is CRITICAL for the MS array, as Decode.py expects the index
        # of the feasible machine, not the absolute machine ID.
        self.feasible_counts = []
        for i in range(self.J_num):
            for j in range(self.J[i + 1]):
                O_j = self.Processing_time[i][j]
                # Count how many machines are NOT 9999 (INFTY)
                feasible_count = sum(1 for pt in O_j if pt != 9999)
                self.feasible_counts.append(feasible_count)

        # VNS Parameters from paper
        self.q_max = 10
        self.rho_max = 30
        self.M_constant = 1.0  # Fitness scaling constant

    def initialize_population(self):
        population = []
        for _ in range(self.Pop_size):
            # Phase 1: Machine Assignment (Random feasible index as expected by Decode.py)
            MS = []
            for feasible_count in self.feasible_counts:
                # Pick a random index representing the n-th feasible machine
                MS.append(random.randint(0, feasible_count - 1))

            # Phase 2: Operation Permutation (0-based job indices as expected by Decode.py)
            OS = []
            for i in range(self.J_num):
                OS.extend([i] * self.J[i + 1])
            random.shuffle(OS)

            population.append({'MS': MS, 'OS': OS, 'Cmax': float('inf'), 'fitness': 0.0})
        return population

    def evaluate_chromosome(self, ind):
        # Instantiate your actual DES Decoder
        decoder = Decode(self.J, self.Processing_time, self.M_num)

        # Combine MS and OS into the flat CHS list your decoder expects
        CHS = ind['MS'] + ind['OS']

        # Decode returns the makespan
        cmax = decoder.decode(CHS, self.total_operations)
        return cmax

    def fitness_eval(self, population):
        for ind in population:
            ind['Cmax'] = self.evaluate_chromosome(ind)
            ind['fitness'] = self.M_constant / ind['Cmax']

        # Sort descending (maximizing fitness)
        population.sort(key=lambda x: x['fitness'], reverse=True)
        return population

    def POX_crossover(self, parent1, parent2):
        # Precedence Preserving Order-based Crossover (POX) for OS
        jobs = list(range(self.J_num))  # 0-based job IDs
        sub1 = random.sample(jobs, k=len(jobs) // 2)

        child1_OS = [None] * self.total_operations
        child2_OS = [None] * self.total_operations

        # Step 1 & 2: Copy SUB1 jobs
        for i in range(self.total_operations):
            if parent1['OS'][i] in sub1:
                child1_OS[i] = parent1['OS'][i]
            if parent2['OS'][i] in sub1:
                child2_OS[i] = parent2['OS'][i]

        # Step 3: Fill the rest from the other parent
        p2_idx = 0
        for i in range(self.total_operations):
            if child1_OS[i] is None:
                while parent2['OS'][p2_idx] in sub1:
                    p2_idx += 1
                child1_OS[i] = parent2['OS'][p2_idx]
                p2_idx += 1

        p1_idx = 0
        for i in range(self.total_operations):
            if child2_OS[i] is None:
                while parent1['OS'][p1_idx] in sub1:
                    p1_idx += 1
                child2_OS[i] = parent1['OS'][p1_idx]
                p1_idx += 1

        return child1_OS, child2_OS

    def two_point_crossover(self, p1_MS, p2_MS):
        pt1, pt2 = sorted(random.sample(range(len(p1_MS)), 2))
        child1_MS = p1_MS[:pt1] + p2_MS[pt1:pt2] + p1_MS[pt2:]
        return child1_MS

    def search_operator(self, target, alpha, beta, delta):
        # Eq (9) from paper
        r = random.random()
        if r <= 1 / 3:
            guide = alpha
        elif 1 / 3 < r < 2 / 3:
            guide = beta
        else:
            guide = delta

        new_OS, _ = self.POX_crossover(target, guide)
        new_MS = self.two_point_crossover(target['MS'], guide['MS'])

        return {'MS': new_MS, 'OS': new_OS, 'Cmax': float('inf'), 'fitness': 0.0}

    def mutate_assignment(self, MS):
        new_MS = copy.deepcopy(MS)
        idx = random.randint(0, len(MS) - 1)

        # Only mutate if there is more than 1 feasible machine available
        if self.feasible_counts[idx] > 1:
            current_val = new_MS[idx]
            # Pick a feasible index that is DIFFERENT from the current one
            choices = [c for c in range(self.feasible_counts[idx]) if c != current_val]
            new_MS[idx] = random.choice(choices)

        return new_MS

    def mutation_swap(self, OS):
        new_OS = copy.deepcopy(OS)
        idx1, idx2 = random.sample(range(len(OS)), 2)
        new_OS[idx1], new_OS[idx2] = new_OS[idx2], new_OS[idx1]
        return new_OS

    def mutation_insert(self, OS):
        new_OS = copy.deepcopy(OS)
        idx1, idx2 = random.sample(range(len(OS)), 2)
        val = new_OS.pop(idx1)
        new_OS.insert(idx2, val)
        return new_OS

    def mutation_inverse(self, OS):
        new_OS = copy.deepcopy(OS)
        idx1, idx2 = sorted(random.sample(range(len(OS)), 2))
        new_OS[idx1:idx2 + 1] = reversed(new_OS[idx1:idx2 + 1])
        return new_OS

    def adaptive_mutation(self, population):
        fit_max = population[0]['fitness']
        fit_min = population[-1]['fitness']

        for k in range(3, self.Pop_size):  # Skip alpha, beta, delta
            if fit_max == fit_min:
                p_k = 0.5
            else:
                p_k = 1 - ((fit_max - population[k]['fitness']) / (fit_max - fit_min))

            r = random.random()
            if r < p_k:
                if p_k < 0.5:
                    population[k]['OS'] = self.mutation_swap(population[k]['OS'])
                elif 0.5 <= p_k <= 0.8:
                    population[k]['OS'] = self.mutation_insert(population[k]['OS'])
                else:
                    population[k]['OS'] = self.mutation_inverse(population[k]['OS'])

                # Apply machine assignment mutation as dictated by the paper's +Assignment logic
                population[k]['MS'] = self.mutate_assignment(population[k]['MS'])

        return population

    def variable_neighborhood_search(self, wolf):
        # Applied to top 3 wolves
        best_wolf = copy.deepcopy(wolf)
        q = 1

        while q <= self.q_max:
            l = 1
            while l <= 3:  # 3 neighborhood structures
                new_wolf = copy.deepcopy(best_wolf)

                # Apply local search
                rho = 1
                local_best = copy.deepcopy(new_wolf)

                while rho <= self.rho_max:
                    neighbor = copy.deepcopy(local_best)
                    if l == 1:
                        neighbor['OS'] = self.mutation_swap(neighbor['OS'])
                    elif l == 2:
                        neighbor['OS'] = self.mutation_insert(neighbor['OS'])
                    elif l == 3:
                        neighbor['OS'] = self.mutation_inverse(neighbor['OS'])

                    neighbor['MS'] = self.mutate_assignment(neighbor['MS'])
                    neighbor['Cmax'] = self.evaluate_chromosome(neighbor)

                    if neighbor['Cmax'] < local_best['Cmax']:
                        local_best = copy.deepcopy(neighbor)
                    rho += 1

                if local_best['Cmax'] < best_wolf['Cmax']:
                    best_wolf = copy.deepcopy(local_best)
                    l = 1  # Return to first neighborhood
                else:
                    l += 1
            q += 1

        # Re-calculate fitness before returning
        best_wolf['fitness'] = self.M_constant / best_wolf['Cmax']
        return best_wolf

    def optimize(self):
        population = self.initialize_population()
        population = self.fitness_eval(population)

        alpha = copy.deepcopy(population[0])
        beta = copy.deepcopy(population[1])
        delta = copy.deepcopy(population[2])

        conv_history = []

        for t in range(self.Maxiter):
            # 1. VNS on Top 3 Wolves
            alpha = self.variable_neighborhood_search(alpha)
            beta = self.variable_neighborhood_search(beta)
            delta = self.variable_neighborhood_search(delta)

            # Repack best wolves into population
            population[0], population[1], population[2] = alpha, beta, delta

            # 2. Update omegas using Discrete Search Operator
            for i in range(3, self.Pop_size):
                population[i] = self.search_operator(population[i], alpha, beta, delta)

            # 3. Adaptive Mutation
            population = self.adaptive_mutation(population)

            # 4. Evaluate and sort
            population = self.fitness_eval(population)

            # Update Alpha, Beta, Delta
            alpha = copy.deepcopy(population[0])
            beta = copy.deepcopy(population[1])
            delta = copy.deepcopy(population[2])

            conv_history.append(alpha['Cmax'])

            # Optional: Print progress
            # print(f"Iter {t+1}/{self.Maxiter} - Best Cmax: {alpha['Cmax']}")

        # Return the final CHS list for the Gantt chart, the makespan, and history
        best_chs = alpha['MS'] + alpha['OS']
        return best_chs, alpha['Cmax'], conv_history