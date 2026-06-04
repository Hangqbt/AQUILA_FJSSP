import itertools
import random
import numpy as np
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
        self.Pop_size = Pop_size   # population size
        self.Pc = Pc               # crossover prob
        self.Pm = Pm               # mutation prob
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
        MS_1 = CHS2[0:T0].copy()
        MS_2 = CHS1[0:T0].copy()
        for i in R:
            MS_1[i], MS_2[i] = MS_2[i], MS_1[i]
        CHS1 = np.hstack((MS_1, OS_1))
        CHS2 = np.hstack((MS_2, OS_2))
        return CHS1, CHS2

    # Crossover on operation-sequence (OS) part
    def operation_cross(self, CHS1, CHS2, T0, J_num):
        OS_1 = CHS1[T0:2 * T0]
        OS_2 = CHS2[T0:2 * T0]
        MS_1 = CHS1[0:T0]
        MS_2 = CHS2[0:T0]
        Job_list = [i for i in range(J_num)]
        random.shuffle(Job_list)
        r = random.randint(1, J_num - 1)
        Set1 = Job_list[0:r]
        new_os = list(np.zeros(T0, dtype=int))
        for k, v in enumerate(OS_1):
            if v in Set1:
                new_os[k] = v + 1
        for i in OS_2:
            if i not in Set1:
                site = new_os.index(0)
                new_os[site] = i + 1
        new_os = np.array([j - 1 for j in new_os])
        CHS1 = np.hstack((MS_1, new_os))
        CHS2 = np.hstack((MS_2, new_os))
        return CHS1, CHS2

    # Mutation on machine-selection (MS) part
    def machine_variation(self, CHS, O, T0, J):
        Tr = [i_num for i_num in range(T0)]
        MS = CHS[0:T0].copy()
        OS = CHS[T0:2 * T0]
        r = random.randint(1, T0 - 1)
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
        A = list(itertools.permutations(Tr, r))
        A_CHS = []
        for perm in A:
            OS_try = OS.copy()
            for j in range(r):
                OS_try[Site[j]] = perm[j]
            C_I = np.hstack((MS, OS_try))
            A_CHS.append(C_I)
        Fit = []
        for cand in A_CHS:
            d = Decode(J, O, M_num)
            Fit.append(d.decode(cand, T0))
        return A_CHS[Fit.index(min(Fit))]
