import os
import sys
import time
import random
import numpy as np
import pandas as pd
from scipy.stats import wilcoxon
from concurrent.futures import ProcessPoolExecutor
from contextlib import redirect_stdout

# ==========================================
# 1. DIRECTORY CONFIGURATION
# ==========================================
# Change these three paths to match your exact machine setup!
AO_DIR = r"C:\Users\Guhan\Desktop\AQUILA_FJSSP\MODELS\AQUILA_FJSSP_v2"
GA_DIR = r"C:\Users\Guhan\Desktop\AQUILA_FJSSP\MODELS\Code_share"
DATA_DIR = r"C:\Users\Guhan\Desktop\AQUILA_FJSSP\MODELS\AQUILA_FJSSP_v2\data"

# Inject folders into the path so Python can find both algorithms
sys.path.insert(0, AO_DIR)
sys.path.insert(0, GA_DIR)

# ==========================================
# 2. ALGORITHM IMPORTS
# ==========================================
from aquila_jssp import Aquila_Optimizer_FJSSP
from GA import GA
from Encode_GA import Encode

# Alias the Instance loaders to avoid cross-contamination
from Instance import get_instance as get_instance_ao
from Instance_GA import get_instance as get_instance_ga


# ==========================================
# 3. WORKER FUNCTIONS
# ==========================================
def run_ao(inst_name, seed, data_dir):
    random.seed(seed)
    np.random.seed(seed)

    # Silence output so the console remains clean
    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance_ao(
            source="benchmark", name=inst_name, data_dir=data_dir
        )

    ao = Aquila_Optimizer_FJSSP(J=J, Processing_time=Processing_time, M_num=M_num, Pop_size=30, Maxiter=500)
    _, best_makespan, _ = ao.optimize()
    return best_makespan


def run_ga(inst_name, seed, data_dir):
    random.seed(seed)
    np.random.seed(seed)

    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance_ga(
            source="benchmark", name=inst_name, data_dir=data_dir
        )

    g = GA(Pop_size=300, Pc=0.85, Pm=0.15, Generations=50, seed=seed)
    e = Encode(Processing_time, g.Pop_size, J, J_num, M_num)
    CHS1 = e.Global_initial()
    CHS2 = e.Random_initial()
    CHS3 = e.Local_initial()
    C = np.vstack((CHS1, CHS2, CHS3))

    Optimal_fit = float('inf')

    for gen in range(g.Generations):
        Fit = g.fitness(C, J, Processing_time, M_num, O_num)
        best_fitness = min(Fit)
        if best_fitness < Optimal_fit:
            Optimal_fit = best_fitness

        for j in range(len(C)):
            Cafter = []
            if random.random() < g.Pc:
                N_i = random.choice(np.arange(len(C)))
                Cross = g.machine_cross(C[j], C[N_i], O_num)
                Cafter.extend([Cross[0], Cross[1], C[j]])
            if random.random() < g.Pm:
                Variance = g.machine_variation(C[j], Processing_time, O_num, J)
                Cafter.append(Variance)
            if Cafter:
                Fit_after = g.fitness(Cafter, J, Processing_time, M_num, O_num)
                C[j] = Cafter[Fit_after.index(min(Fit_after))]

    return Optimal_fit


def worker_wrapper(args):
    algo, inst, seed, data_dir = args
    if algo == 'AO':
        res = run_ao(inst, seed, data_dir)
    else:
        res = run_ga(inst, seed, data_dir)
    return algo, inst, seed, res


# ==========================================
# 4. MAIN EXECUTION & STATISTICS
# ==========================================
if __name__ == '__main__':
    instances = [f"mk{str(i).zfill(2)}" for i in range(1, 16)]
    num_runs = 10

    # 10 universal seeds so both algorithms face the exact same RNG starting states
    master_seeds = [50,100,150,200,250,300,350,400,450,500]

    tasks = []
    for inst in instances:
        for seed in master_seeds:
            tasks.append(('AO', inst, seed, DATA_DIR))
            tasks.append(('GA', inst, seed, DATA_DIR))

    print(f"Starting execution of {len(tasks)} total runs (10 per algorithm per instance)...")
    start_time = time.time()

    # Setup dictionaries to store the incoming parallel results
    results_dict = {'AO': {inst: [] for inst in instances}, 'GA': {inst: [] for inst in instances}}

    # Run the massive benchmark in parallel
    with ProcessPoolExecutor() as executor:
        for algo, inst, seed, res in executor.map(worker_wrapper, tasks):
            results_dict[algo][inst].append(res)

    print(f"All runs completed in {time.time() - start_time:.2f} seconds. Computing Wilcoxon stats...")

    final_rows = []
    for inst in instances:
        ao_scores = np.array(results_dict['AO'][inst])
        ga_scores = np.array(results_dict['GA'][inst])

        mean_ao = np.mean(ao_scores)
        mean_ga = np.mean(ga_scores)
        std_ao = np.std(ao_scores, ddof=1) if len(ao_scores) > 1 else 0.0
        std_ga = np.std(ga_scores, ddof=1) if len(ga_scores) > 1 else 0.0

        # ---------------------------------------------------------
        # THE WILCOXON TEST
        # ---------------------------------------------------------
        if np.array_equal(ao_scores, ga_scores):
            # If both arrays are mathematically identical, the p-value is 1.0 (no difference)
            w_stat, p_val = 0.0, 1.0
        else:
            try:
                w_stat, p_val = wilcoxon(ao_scores, ga_scores, alternative='two-sided')
            except Exception as e:
                # Fallback if differences are perfectly 0 but arrays aren't exactly identical due to float math
                w_stat, p_val = -1, 1.0

        # Determine Statistical Significance (Standard alpha threshold = 0.05)
        if p_val < 0.05:
            if mean_ao < mean_ga:
                conclusion = "AO is Sig. BETTER (+)"
            else:
                conclusion = "GA is Sig. BETTER (-)"
        else:
            conclusion = "No Sig. Difference (=)"

        final_rows.append({
            "Instance": inst,
            "AO_Mean": f"{mean_ao:.2f} ± {std_ao:.2f}",
            "GA_Mean": f"{mean_ga:.2f} ± {std_ga:.2f}",
            "W-Statistic": round(w_stat, 2),
            "P-Value": round(p_val, 4),
            "Significance": conclusion,
            "AO_Raw_Scores": [round(x, 1) for x in ao_scores],
            "GA_Raw_Scores": [round(x, 1) for x in ga_scores]
        })

    df = pd.DataFrame(final_rows)
    print("\n================ WILCOXON TEST RESULTS ================")
    print(df[["Instance", "AO_Mean", "GA_Mean", "P-Value", "Significance"]])

    df.to_csv("wilcoxon_benchmark_results.csv", index=False)
    print("\nFull statistical results saved to 'wilcoxon_benchmark_results.csv'")