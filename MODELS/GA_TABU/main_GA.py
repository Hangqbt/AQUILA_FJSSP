import os
import time
import random
from contextlib import redirect_stdout
from concurrent.futures import ProcessPoolExecutor

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

from GA import GA
from Instance import *
from Encode import Encode


def run_ga_for_instance(inst_name, seed):
    """
    Run the GA + Tabu (Memetic Algorithm) for a single instance and a single seed.
    """
    # 1. Load Instance
    Processing_time, J, M_num, O_num, J_num, meta = get_instance(
        source="benchmark",
        name=inst_name,
        data_dir="Data",
    )

    # 2. Setup GA Hyperparameters
    Pop_size = 50
    Pc = 0.8
    Pm = 0.05
    Generations = 300

    GA_Instance = GA(
        Pop_size=Pop_size,
        Pc=Pc,
        Pm=Pm,
        Generations=Generations,
        seed=seed
    )

    # 3. Initialization
    e = Encode(Processing_time, Pop_size, J, J_num, M_num)
    OS_List = e.OS_List()
    Len = len(OS_List)

    start_time = time.time()

    # Generate initial population
    CHS1 = e.Global_initial()
    CHS2 = e.Random_initial()
    CHS3 = e.Local_initial()
    C = np.vstack((CHS1, CHS2, CHS3))

    # Initial fitness evaluation
    Fit = GA_Instance.fitness(C, J, Processing_time, M_num, Len)

    best_fitness = min(Fit)
    best_chs = C[Fit.index(best_fitness)]

    # Store convergence histories
    run_best_history = [best_fitness]
    run_avg_history = [np.mean(Fit)]  # <-- NEW: Track Population Average

    # 4. Generational Loop (GA + Tabu)
    for i in range(GA_Instance.Generations):
        # Elitism: Keep the best individual
        C_new = [best_chs.copy()]

        # Crossover & Mutation to fill the rest of the new population
        while len(C_new) < GA_Instance.Pop_size:
            tourney_size = 5

            # Grab 5 random indices, find the one with the best (minimum) fitness
            tourney1 = random.sample(range(len(C)), tourney_size)
            best_idx1 = min(tourney1, key=lambda i: Fit[i])
            p1 = C[best_idx1]

            # Do it again for the second parent
            tourney2 = random.sample(range(len(C)), tourney_size)
            best_idx2 = min(tourney2, key=lambda i: Fit[i])
            p2 = C[best_idx2]

            # Crossover
            if random.random() < GA_Instance.Pc:
                p1, p2 = GA_Instance.machine_cross(p1.copy(), p2.copy(), Len)
                p1, p2 = GA_Instance.operation_cross(p1.copy(), p2.copy(), Len, J_num)

            # Mutation
            if random.random() < GA_Instance.Pm:
                p1 = GA_Instance.machine_variation(p1.copy(), Len, J, Processing_time)
                p1 = GA_Instance.operation_variation(p1.copy(), Len, J_num, J, Processing_time, M_num)
            if random.random() < GA_Instance.Pm:
                p2 = GA_Instance.machine_variation(p2.copy(), Len, J, Processing_time)
                p2 = GA_Instance.operation_variation(p2.copy(), Len, J_num, J, Processing_time, M_num)

            C_new.append(p1)
            if len(C_new) < GA_Instance.Pop_size:
                C_new.append(p2)

        C = np.array(C_new)
        Fit = GA_Instance.fitness(C, J, Processing_time, M_num, Len)

        # --- TABU SEARCH INTEGRATION ---
        # Run Tabu Search every 5 generations
        if (i + 1) % 5 == 0:
            # 50/50 Split Target Selection
            if i < (GA_Instance.Generations / 2):
                # Exploration Phase: Apply Tabu to Top 3 individuals
                elite_indices = np.argsort(Fit)[:3]
            else:
                # Exploitation Phase: Apply Tabu to only the #1 Global Best
                elite_indices = [np.argmin(Fit)]

            for idx in elite_indices:
                refined_chs, refined_fit = GA_Instance.tabu_local_search(
                    CHS=C[idx],
                    best_global_fitness=best_fitness,
                    J=J,
                    Processing_time=Processing_time,
                    M_num=M_num,
                    Len=Len,
                    num_steps=10,
                    neighbors_per_step=8,
                    tenure=7
                )
                # Apply refinement back to population
                C[idx] = refined_chs
                Fit[idx] = refined_fit

        # Update Generation Best
        gen_best = min(Fit)
        if gen_best < best_fitness:
            best_fitness = gen_best
            best_chs = C[Fit.index(gen_best)]

        # <-- NEW: Track metrics for the diagnostic graph
        run_best_history.append(best_fitness)
        run_avg_history.append(np.mean(Fit))

    end_time = time.time()
    comp_time = end_time - start_time

    return best_fitness, comp_time, run_best_history, run_avg_history


def worker(args):
    """
    Wrapper for multiprocessing to ignore prints from inside run_ga_for_instance
    """
    inst_name, seed = args
    with open(os.devnull, 'w') as fnull:
        with redirect_stdout(fnull):
            res_obj, res_time, run_best_hist, run_avg_hist = run_ga_for_instance(inst_name, seed)
    return inst_name, seed, res_obj, res_time, run_best_hist, run_avg_hist


if __name__ == "__main__":
    # The standard benchmark instances you are using
    benchmark_instances = [
        "mt10c1", "mt10cc", "mt10x", "mt10xx", "mt10xxx", "mt10xy", "mt10xyz",
        "setb4c9", "setb4cc", "setb4x", "setb4xx", "setb4xxx", "setb4xy", "setb4xyz"
    ]

    run_seeds = [55589, 97402, 89190, 25814, 16969, 31181, 1573, 64163, 32093, 62154,
                 61472, 90888, 93148, 60149, 54209, 7839, 27714, 17168, 62709, 26491]

    tasks = []
    for inst in benchmark_instances:
        for seed in run_seeds:
            tasks.append((inst, seed))

    print(f"Total instances  : {len(benchmark_instances)}")
    print(f"Seeds per inst   : {len(run_seeds)}")
    print(f"Total runs       : {len(tasks)}")
    print("Starting parallel execution of GA+Tabu...")

    results_data = []

    # Run everything in parallel
    with ProcessPoolExecutor() as executor:
        for out in executor.map(worker, tasks):
            results_data.append(out)

    print("All runs finished. Aggregating results...")

    seedwise_results = []
    seedwise_time_results = []
    results = []

    for inst_name in benchmark_instances:
        meta_for_instance = get_instance_metadata(inst_name, data_dir="Data")

        # Filter results for this instance
        inst_runs = [r for r in results_data if r[0] == inst_name]
        inst_runs.sort(key=lambda x: x[1])  # sort by seed

        # Collect objectives, times, and histories
        run_best_vals = [r[2] for r in inst_runs]
        run_times = [r[3] for r in inst_runs]
        run_best_histories = [r[4] for r in inst_runs]
        run_avg_histories = [r[5] for r in inst_runs]

        # ========================================================
        # PLOT 1: THE AVERAGE CONVERGENCE GRAPH WITH SPREAD BAND
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
            plt.title(f'GA+Tabu Average Convergence: {inst_name} (20 Seeds)')
            plt.xlabel('Generations')
            plt.ylabel('Makespan')
            plt.legend()
            plt.grid(True)
            plot_filename = f"convergence_avg_GA_{inst_name}.png"
            plt.savefig(plot_filename)
            plt.close()

        # ========================================================
        # PLOT 2: NEW DIAGNOSTIC GRAPH (BEST VS AVERAGE POPULATION)
        # ========================================================
        if len(run_best_histories) > 0 and len(run_avg_histories) > 0:
            # We select the very first seed as our representative diagnostic case
            rep_seed = inst_runs[0][1]
            rep_best = run_best_histories[0]
            rep_avg = run_avg_histories[0]

            plt.figure(figsize=(8, 5))
            plt.plot(generations, rep_avg, label='Average Population Fitness', color='orange', linestyle='--',
                     linewidth=2)
            plt.plot(generations, rep_best, label='Global Best Fitness', color='blue', linewidth=2)
            plt.title(f'Diagnostic Population Health: {inst_name} (Seed {rep_seed})')
            plt.xlabel('Generations')
            plt.ylabel('Makespan')
            plt.legend()
            plt.grid(True)
            diagnostic_filename = f"diagnostic_pop_GA_{inst_name}.png"
            plt.savefig(diagnostic_filename)
            plt.close()
        # ========================================================

        # Seed-wise row logic
        row_dict = {"instance_name": inst_name}
        row_time_dict = {"instance_name": inst_name}
        for r in inst_runs:
            sd = r[1]
            obj = r[2]
            t = r[3]
            row_dict[f"seed_{sd}"] = obj
            row_time_dict[f"seed_{sd}"] = round(t, 4)

        seedwise_results.append(row_dict)
        seedwise_time_results.append(row_time_dict)

        mean_time = np.mean(run_times)
        std_time = np.std(run_times, ddof=1) if len(run_times) > 1 else 0.0

        mean_obj = np.mean(run_best_vals)
        std_obj = np.std(run_best_vals, ddof=1) if len(run_best_vals) > 1 else 0.0

        time_str = f"{mean_time:.4f} ± {std_time:.4f}"
        obj_str = f"{mean_obj:.2f} ± {std_obj:.2f}"

        optimum = meta_for_instance.get("optimum", None)
        bounds = meta_for_instance.get("bounds", {})
        opt_or_lb = optimum if optimum is not None else bounds.get("lower", None)

        results.append({
            "instance_name": inst_name,
            "computational_time": time_str,
            "objective_value": obj_str,
            "optimal(lower_bound)": opt_or_lb,
        })

    # Output DataFrames
    df_results = pd.DataFrame(
        results,
        columns=["instance_name", "computational_time", "objective_value", "optimal(lower_bound)"]
    )
    print("\n================ FINAL SUMMARY TABLE ================\n")
    print(df_results)

    seed_columns = ["instance_name"] + [f"seed_{seed}" for seed in run_seeds]
    df_seedwise = pd.DataFrame(seedwise_results, columns=seed_columns)
    print("\n================ SEED-WISE FITNESS TABLE ================\n")
    print(df_seedwise)

    df_seedwise_time = pd.DataFrame(seedwise_time_results, columns=seed_columns)
    print("\n================ SEED-WISE TIME TABLE ================\n")
    print(df_seedwise_time)

    # Save CSV files
    out_path_summary = Path("ga_tabu_benchmark_results.csv")
    df_results.to_csv(out_path_summary, index=False)

    out_path_seedwise = Path("ga_tabu_seedwise_fitness_results.csv")
    df_seedwise.to_csv(out_path_seedwise, index=False)

    out_path_seedwise_time = Path("ga_tabu_seedwise_time_results.csv")
    df_seedwise_time.to_csv(out_path_seedwise_time, index=False)