import os
import sys
import time
import random
from pathlib import Path
from concurrent.futures import ProcessPoolExecutor
from contextlib import redirect_stdout

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# Import the adapted Aquila Optimizer
from aquila_jssp_check import Aquila_Optimizer_FJSSP
from Instance import *  # assumes: get_instance, list_available_instances, DEFAULT_DATA_DIR
from Decode import Decode


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


def plot_convergence(run_histories, inst_name, save_path):
    # Safely pad arrays in case runs exited early due to stagnation
    max_len = max(len(h) for h in run_histories)
    padded_histories = []
    for h in run_histories:
        pad_length = max_len - len(h)
        if pad_length > 0:
            padded_histories.append(h + [h[-1]] * pad_length)
        else:
            padded_histories.append(h)

    histories_matrix = np.array(padded_histories)
    avg_history = np.mean(histories_matrix, axis=0)
    std_history = np.std(histories_matrix, axis=0)
    best_history = np.min(histories_matrix, axis=0)
    iterations = np.arange(1, len(avg_history) + 1)

    plt.figure(figsize=(10, 6))
    plt.fill_between(iterations, avg_history - std_history, avg_history + std_history,
                     color='blue', alpha=0.2, label='Standard Deviation (Spread)')
    plt.plot(iterations, avg_history, label='Average Convergence', color='blue', linewidth=2)
    plt.plot(iterations, best_history, label='Lowest (Best) Solution', color='red', linestyle='--', linewidth=2)
    plt.title(f'Convergence Analysis - {inst_name}')
    plt.xlabel('Iteration')
    plt.ylabel('Makespan (Cycle Time)')
    plt.grid(True, linestyle='--', alpha=0.7)
    plt.legend()
    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
    plt.close()


def plot_boxplot(run_best_objs, inst_name, save_path):
    plt.figure(figsize=(6, 5))
    plt.boxplot(run_best_objs, tick_labels=['Aquila Optimizer (AO)'], patch_artist=True,
                boxprops=dict(facecolor="lightblue", color="blue"),
                medianprops=dict(color="red", linewidth=2))
    plt.title(f'Algorithm Stability (10 Runs) - {inst_name}')
    plt.ylabel('Makespan (Cycle Time)')
    plt.grid(True, axis='y', linestyle='--', alpha=0.7)
    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
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
    best_chromosome, best_makespan, conv_history = ao.optimize()

    elapsed = time.time() - start_time
    print(f"[{inst_name}] Run seed={run_seed}: best Cmax = {best_makespan:.2f}, time = {elapsed:.4f} s")

    return {
        "instance": inst_name,
        "best_obj": best_makespan,
        "time": elapsed,
        "best_chromosome": best_chromosome,
        "history": conv_history
    }


if __name__ == '__main__':
    MASTER_SEED = 123
    random.seed(MASTER_SEED)
    np.random.seed(MASTER_SEED)

    global_timestamp = time.strftime("%Y%m%d_%H%M%S")

    ROOT_DIR = Path(r"C:\Users\Guhan\Desktop\AQUILA_FJSSP\MODELS\AQUILA_FJSSP_v2")
    VISUALS_DIR = ROOT_DIR / "Visuals"
    GANTT_DIR = VISUALS_DIR / "gantt" / global_timestamp
    CONV_DIR = VISUALS_DIR / "convergence" / global_timestamp
    BOX_DIR = VISUALS_DIR / "boxplots" / global_timestamp

    for directory in [GANTT_DIR, CONV_DIR, BOX_DIR]:
        directory.mkdir(parents=True, exist_ok=True)

    num_runs = 10
    POP_SIZE = 50
    MAX_ITER = 300
    RUN_MODE = 'AUTHOR'
    TARGET_AUTHOR = 'brandimarte'
    TARGET_INSTANCE = 'mk01'

    instance_names = list_available_instances(DEFAULT_DATA_DIR)
    print(f"Current Execution Mode: {RUN_MODE} | Target: {TARGET_AUTHOR}")

    results = []
    for inst_name in instance_names:
        try:
            # Safely fetch metadata without spamming the console
            with open(os.devnull, 'w') as f, redirect_stdout(f):
                Processing_time, J, M_num, O_num, J_num, meta_for_instance = get_instance(
                    source="benchmark", name=inst_name, data_dir=DEFAULT_DATA_DIR
                )

            if RUN_MODE == 'SINGLE' and inst_name != TARGET_INSTANCE:
                continue
            elif RUN_MODE == 'AUTHOR' and TARGET_AUTHOR.lower() not in meta_for_instance.get("path", "").lower():
                continue

            print(f"\n================ INSTANCE: {inst_name} ================")

            run_seeds = [9735743, 9518981, 8883341, 7204497, 9324999,
                         615432, 8429556, 9385245, 7320139, 8462810]
            run_args = [(inst_name, run_seeds[i], POP_SIZE, MAX_ITER, DEFAULT_DATA_DIR) for i in range(num_runs)]

            with ProcessPoolExecutor() as executor:
                outputs = list(executor.map(run_single_ao, run_args))

            run_times = [o["time"] for o in outputs]
            run_best_obj = [o["best_obj"] for o in outputs]
            run_histories = [o["history"] for o in outputs]

            conv_path = CONV_DIR / f"convergence_{inst_name}.png"
            plot_convergence(run_histories, inst_name, conv_path)

            box_path = BOX_DIR / f"boxplot_{inst_name}.png"
            plot_boxplot(run_best_obj, inst_name, box_path)

            best_run_idx = np.argmin(run_best_obj)
            best_overall_chrom = outputs[best_run_idx]["best_chromosome"]

            decoder = Decode(J, Processing_time, M_num)
            decoder.decode(best_overall_chrom, sum(J.values()))
            gantt_path = GANTT_DIR / f"gantt_{inst_name}.png"
            Gantt(decoder.Machines, inst_name, gantt_path)

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

        except Exception as e:
            print(f"FAILED on instance {inst_name}. Error: {e}")
            results.append({
                "instance_name": inst_name,
                "computational_time": "ERROR",
                "objective_value": "ERROR",
                "optimal(lower_bound)": "ERROR",
            })

    df_results = pd.DataFrame(results, columns=["instance_name", "computational_time", "objective_value",
                                                "optimal(lower_bound)"])
    print("\n================ FINAL SUMMARY TABLE ================\n")
    print(df_results)

    if RUN_MODE == 'SINGLE':
        out_path = ROOT_DIR / f"ao_results_single_{TARGET_INSTANCE}_{global_timestamp}.csv"
    elif RUN_MODE == 'AUTHOR':
        out_path = ROOT_DIR / f"ao_results_author_{TARGET_AUTHOR}_{global_timestamp}.csv"
    else:
        out_path = ROOT_DIR / f"ao_results_ALL_{global_timestamp}.csv"

    df_results.to_csv(out_path, index=False)
    print(f"\nResults saved to: {out_path.resolve()}")