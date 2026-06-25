import os

# Force deterministic dictionary/set ordering across all OS processes
os.environ['PYTHONHASHSEED'] = str(123)
import time
import random
from concurrent.futures import ProcessPoolExecutor
from contextlib import redirect_stdout
from pathlib import Path

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from aquila_jssp_TABU_W_GRAPH import Aquila_Optimizer_FJSSP
from Instance import *


def Gantt(Machines, inst_name, save_path):
    M = ['red', 'blue', 'yellow', 'orange', 'green', 'palegoldenrod', 'purple', 'pink',
         'Thistle', 'Magenta', 'SlateBlue', 'RoyalBlue', 'Cyan', 'Aqua', 'floralwhite',
         'ghostwhite', 'goldenrod', 'mediumslateblue', 'navajowhite', 'navy',
         'sandybrown', 'moccasin']

    plt.figure(figsize=(10, 6))
    for i in range(len(Machines)):
        Machine = Machines[i]
        Start_time = Machine.O_start
        End_time = Machine.O_end
        for i_1 in range(len(End_time)):
            job_id = Machine.assigned_task[i_1][0]
            width = End_time[i_1] - Start_time[i_1]
            plt.barh(i, width=width, height=0.8, left=Start_time[i_1],
                     color=M[(job_id - 1) % len(M)], edgecolor='black')
            plt.text(x=Start_time[i_1] + width / 2 - 0.5, y=i, s=job_id,
                     color='white' if M[(job_id - 1) % len(M)] in ['navy', 'blue', 'purple'] else 'black',
                     fontweight='bold')

    plt.yticks(np.arange(len(Machines)), np.arange(1, len(Machines) + 1))
    plt.title(f'Scheduling Gantt Chart - {inst_name}')
    plt.ylabel('Machines')
    plt.xlabel('Time')
    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
    plt.close()


def plot_diagnostic(history_best, history_avg, inst_name, seed):
    """
    Plots the average population fitness vs the global best fitness for a single run.
    """
    plt.figure(figsize=(8, 5))
    gens = range(1, len(history_best) + 1)

    plt.plot(gens, history_avg, label='Average Population Fitness', color='orange', linestyle='--', linewidth=1.5)
    plt.plot(gens, history_best, label='Global Best Fitness', color='blue', linewidth=2)

    plt.title(f'Diagnostic Population Health: {inst_name} (Seed {seed})')
    plt.xlabel('Iterations')
    plt.ylabel('Makespan')
    plt.grid(True)
    plt.legend()
    plt.tight_layout()
    plt.savefig(f'diagnostic_pop_AO_{inst_name}_seed{seed}.png', dpi=300)
    plt.close()


def plot_average_convergence(all_histories_best, inst_name, num_seeds):
    """
    Plots the mean best makespan and +/- 1 standard deviation spread across all seeds.
    Safely pads arrays in case runs exited early due to stagnation.
    """
    max_len = max(len(h) for h in all_histories_best)
    padded_histories = []
    for h in all_histories_best:
        pad_length = max_len - len(h)
        if pad_length > 0:
            padded_histories.append(h + [h[-1]] * pad_length)
        else:
            padded_histories.append(h)

    histories_matrix = np.array(padded_histories)
    mean_history = np.mean(histories_matrix, axis=0)
    std_history = np.std(histories_matrix, axis=0)
    iterations = range(1, len(mean_history) + 1)

    plt.figure(figsize=(8, 5))
    plt.plot(iterations, mean_history, label='Mean Best Makespan', color='blue', linewidth=2)
    plt.fill_between(iterations, mean_history - std_history, mean_history + std_history,
                     color='blue', alpha=0.2, label='±1 Std Dev Spread')

    plt.title(f'AO Average Convergence: {inst_name} ({num_seeds} Seeds)')
    plt.xlabel('Iterations')
    plt.ylabel('Makespan')
    plt.grid(True)
    plt.legend()
    plt.tight_layout()
    plt.savefig(f'convergence_avg_AO_{inst_name}.png', dpi=300)
    plt.close()


def run_single_ao(args):
    inst_name, run_seed, POP_SIZE, MAX_ITER, data_dir = args
    random.seed(run_seed)
    np.random.seed(run_seed)

    # Silence the console spam inside the worker too!
    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance(
            source="benchmark", name=inst_name, data_dir=data_dir
        )

    start_time = time.time()
    ao = Aquila_Optimizer_FJSSP(J=J, Processing_time=Processing_time, M_num=M_num, Pop_size=POP_SIZE, Maxiter=MAX_ITER)

    # Safely unpack in case the AO file hasn't been updated to return the average history yet
    optimize_results = ao.optimize()
    if len(optimize_results) >= 4:
        best_chromosome, best_makespan, conv_history, avg_history = optimize_results[0:4]
    else:
        best_chromosome, best_makespan, conv_history = optimize_results[0:3]
        avg_history = None

    elapsed = time.time() - start_time
    print(f"[{inst_name}] Run seed={run_seed}: best Cmax = {best_makespan:.2f}, time = {elapsed:.4f} s")

    return {
        "instance": inst_name,
        "seed": run_seed,
        "best_obj": best_makespan,
        "time": elapsed,
        "best_chromosome": best_chromosome,
        "history": conv_history,
        "history_avg": avg_history
    }


if __name__ == '__main__':
    MASTER_SEED = 123
    random.seed(MASTER_SEED)
    np.random.seed(MASTER_SEED)

    POP_SIZE = 300
    MAX_ITER = 50

    instance_names = list_available_instances(DEFAULT_DATA_DIR)
    print("Available instances:", instance_names)

    instances = [
        "mt10c1", "mt10cc", "mt10x", "mt10xx", "mt10xxx", "mt10xy", "mt10xyz",
        "setb4c9", "setb4cc", "setb4x", "setb4xx", "setb4xxx", "setb4xy", "setb4xyz",
        "seti5c12", "seti5cc", "seti5x", "seti5xx", "seti5xxx", "seti5xy", "seti5xyz"
    ]
    results = []
    seedwise_fitness_results = []
    seedwise_time_results = []

    run_seeds = [
        55589, 97402, 89190, 25814, 16969, 31181, 1573, 64163, 32093, 62154,
        61472, 90888, 93148, 60149, 54209, 7839, 27714, 17168, 62709, 26491
    ]

    for inst_name in instances:
        print(f"\n================ INSTANCE: {inst_name} ================\n")

        with open(os.devnull, 'w') as f, redirect_stdout(f):
            _, _, _, _, _, meta_for_instance = get_instance(
                source="benchmark",
                name=inst_name,
                data_dir=DEFAULT_DATA_DIR,
            )

        run_args = [
            (inst_name, seed, POP_SIZE, MAX_ITER, DEFAULT_DATA_DIR)
            for seed in run_seeds
        ]

        with ProcessPoolExecutor() as executor:
            outputs = list(executor.map(run_single_ao, run_args))

        run_times = [o["time"] for o in outputs]
        run_best_obj = [o["best_obj"] for o in outputs]
        all_best_histories = [o["history"] for o in outputs]

        # Generate the average convergence graph for this instance
        plot_average_convergence(all_best_histories, inst_name, len(run_seeds))

        # Generate a diagnostic graph for just the first seed (if data is available)
        first_output = outputs[0]
        if first_output["history_avg"] is not None:
            plot_diagnostic(first_output["history"], first_output["history_avg"], inst_name, first_output["seed"])

        # Seed-wise fitness row
        fitness_row = {"instance_name": inst_name}
        for output in outputs:
            fitness_row[f"seed_{output['seed']}"] = output["best_obj"]
        seedwise_fitness_results.append(fitness_row)

        # Seed-wise runtime row
        time_row = {"instance_name": inst_name}
        for output in outputs:
            time_row[f"seed_{output['seed']}"] = output["time"]
        seedwise_time_results.append(time_row)

        mean_time = float(np.mean(run_times))
        std_time = float(np.std(run_times, ddof=1)) if len(run_times) > 1 else 0.0
        mean_obj = float(np.mean(run_best_obj))
        std_obj = float(np.std(run_best_obj, ddof=1)) if len(run_best_obj) > 1 else 0.0

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

    # Main summary table
    df_results = pd.DataFrame(
        results,
        columns=["instance_name", "computational_time", "objective_value", "optimal(lower_bound)"]
    )

    print("\n================ FINAL SUMMARY TABLE ================\n")
    print(df_results)

    # Seed-wise fitness table
    seed_columns = ["instance_name"] + [f"seed_{seed}" for seed in run_seeds]
    df_seedwise_fitness = pd.DataFrame(seedwise_fitness_results, columns=seed_columns)

    print("\n================ SEED-WISE FITNESS TABLE ================\n")
    print(df_seedwise_fitness)

    # Seed-wise runtime table
    df_seedwise_time = pd.DataFrame(seedwise_time_results, columns=seed_columns)

    print("\n================ SEED-WISE TIME TABLE ================\n")
    print(df_seedwise_time)

    # Save CSV files
    out_path_summary = Path("ao_benchmark_results_newinit.csv")
    df_results.to_csv(out_path_summary, index=False)

    out_path_seedwise_fitness = Path("ao_seedwise_fitness_results_newinit.csv")
    df_seedwise_fitness.to_csv(out_path_seedwise_fitness, index=False)

    out_path_seedwise_time = Path("ao_seedwise_time_results_newinit.csv")
    df_seedwise_time.to_csv(out_path_seedwise_time, index=False)

    print(f"\nSummary results saved to: {out_path_summary.resolve()}")
    print(f"Seed-wise fitness results saved to: {out_path_seedwise_fitness.resolve()}")
    print(f"Seed-wise time results saved to: {out_path_seedwise_time.resolve()}")