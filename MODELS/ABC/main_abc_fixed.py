import os
import time
from concurrent.futures import ProcessPoolExecutor
from contextlib import redirect_stdout
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

from ABC import ABC_FJSSP
from Instance import get_instance, get_instance_metadata


def run_abc_for_instance(inst_name, seed):
    Processing_time, J, M_num, O_num, J_num, meta = get_instance(
        source="benchmark",
        name=inst_name,
        data_dir="Data",
    )

    # --- CUSTOM POPULATION & GENERATION SIZES ---
    n_eb = 50  # Set Population size (Employed Bees)
    n_ob = 50  # Set Onlooker Bees (e.g., equal to n_eb, or a multiple)
    n_sb = 5  # Set Scout Bees

    Maxiter = 150  # Set total Generations (Iterations)
    # --------------------------------------------

    abc_optimizer = ABC_FJSSP(
        J=J, Processing_time=Processing_time, M_num=M_num, J_num=J_num,
        n_eb=n_eb, n_ob=n_ob, n_sb=n_sb, Maxiter=Maxiter, seed=seed
    )

    start_time = time.time()
    best_fit, best_chs, best_hist, avg_hist = abc_optimizer.optimize()
    comp_time = time.time() - start_time

    return best_fit, comp_time, best_hist, avg_hist


def worker(args):
    inst_name, seed = args
    with open(os.devnull, 'w') as fnull:
        with redirect_stdout(fnull):
            res_obj, res_time, best_hist, avg_hist = run_abc_for_instance(inst_name, seed)
    return inst_name, seed, res_obj, res_time, best_hist, avg_hist


if __name__ == "__main__":
    benchmark_instances = [
        "mt10c1", "mt10cc", "mt10x", "mt10xx", "mt10xxx", "mt10xy", "mt10xyz",
        "setb4c9", "setb4cc", "setb4x", "setb4xx", "setb4xxx", "setb4xy", "setb4xyz"
    ]

    run_seeds = [55589, 97402, 89190, 25814, 16969, 31181, 1573, 64163, 32093, 62154,
                 61472, 90888, 93148, 60149, 54209, 7839, 27714, 17168, 62709, 26491]

    tasks = [(inst, seed) for inst in benchmark_instances for seed in run_seeds]

    print(f"Total Instances: {len(benchmark_instances)}")
    print(f"Total Seeds: {len(run_seeds)}")
    print(f"Total Runs: {len(tasks)}")
    print("Starting parallel execution of ABC (Wang et al. 2012)...")

    results_data = []
    with ProcessPoolExecutor() as executor:
        for out in executor.map(worker, tasks):
            results_data.append(out)

    print("All runs finished. Aggregating results & plotting graphs...")

    seedwise_results = []
    seedwise_time_results = []
    summary_results = []

    for inst_name in benchmark_instances:
        inst_runs = sorted([r for r in results_data if r[0] == inst_name], key=lambda x: x[1])

        run_best_vals = [r[2] for r in inst_runs]
        run_times = [r[3] for r in inst_runs]
        run_best_histories = [r[4] for r in inst_runs]
        run_avg_histories = [r[5] for r in inst_runs]

        # ========================================================
        # PLOT 1: AVERAGE CONVERGENCE GRAPH WITH SPREAD BAND
        # ========================================================
        if len(run_best_histories) > 0:
            hist_matrix = np.array(run_best_histories)
            mean_history = np.mean(hist_matrix, axis=0)
            std_history = np.std(hist_matrix, axis=0, ddof=1)
            generations = np.arange(len(mean_history))

            plt.figure(figsize=(8, 5))
            plt.plot(generations, mean_history, label='Mean Best Makespan', color='blue', linewidth=2)
            plt.fill_between(generations,
                             mean_history - std_history,
                             mean_history + std_history,
                             color='blue', alpha=0.2, label='±1 Std Dev Spread')
            plt.title(f'ABC (Wang) Average Convergence: {inst_name} (20 Seeds)')
            plt.xlabel('Iterations')
            plt.ylabel('Makespan')
            plt.legend()
            plt.grid(True)
            plt.savefig(f"convergence_avg_ABC_{inst_name}.png")
            plt.close()

        # ========================================================
        # PLOT 2: DIAGNOSTIC GRAPH (BEST VS AVERAGE POPULATION)
        # ========================================================
        if len(run_best_histories) > 0 and len(run_avg_histories) > 0:
            rep_seed = inst_runs[0][1]
            rep_best = run_best_histories[0]
            rep_avg = run_avg_histories[0]

            plt.figure(figsize=(8, 5))
            plt.plot(generations, rep_avg, label='Average Population Fitness', color='orange', linestyle='--',
                     linewidth=2)
            plt.plot(generations, rep_best, label='Global Best Fitness', color='blue', linewidth=2)
            plt.title(f'ABC Diagnostic Population Health: {inst_name} (Seed {rep_seed})')
            plt.xlabel('Iterations')
            plt.ylabel('Makespan')
            plt.legend()
            plt.grid(True)
            plt.savefig(f"diagnostic_pop_ABC_{inst_name}.png")
            plt.close()
        # ========================================================

        row_dict = {"instance_name": inst_name}
        row_time_dict = {"instance_name": inst_name}
        for r in inst_runs:
            row_dict[f"seed_{r[1]}"] = r[2]
            row_time_dict[f"seed_{r[1]}"] = round(r[3], 4)

        seedwise_results.append(row_dict)
        seedwise_time_results.append(row_time_dict)

        time_str = f"{np.mean(run_times):.4f} ± {np.std(run_times, ddof=1):.4f}"
        obj_str = f"{np.mean(run_best_vals):.2f} ± {np.std(run_best_vals, ddof=1):.2f}"

        summary_results.append({
            "instance_name": inst_name,
            "computational_time": time_str,
            "objective_value": obj_str
        })

    df_results = pd.DataFrame(summary_results)
    df_seedwise = pd.DataFrame(seedwise_results)
    df_seedwise_time = pd.DataFrame(seedwise_time_results)

    df_results.to_csv("abc_benchmark_results.csv", index=False)
    df_seedwise.to_csv("abc_seedwise_fitness_results.csv", index=False)
    df_seedwise_time.to_csv("abc_seedwise_time_results.csv", index=False)

    print("\n================ FINAL SUMMARY TABLE ================\n")
    print(df_results)
    print("\nFiles & Graphs successfully saved!")