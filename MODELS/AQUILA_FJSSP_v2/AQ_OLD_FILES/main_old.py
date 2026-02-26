import time
import random
from pathlib import Path
from concurrent.futures import ProcessPoolExecutor

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# Import the adapted Aquila Optimizer
from aquila_jssp import Aquila_Optimizer_FJSSP
from Instance import *  # assumes: get_instance,  list_available_instances, DEFAULT_DATA_DIR
from Decode import Decode


def Gantt(Machines, inst_name, save_path):
    """
    Plot a Gantt chart from the decoded machine schedules.
    """
    M = [
        'red', 'blue', 'yellow', 'orange', 'green', 'palegoldenrod', 'purple', 'pink',
        'Thistle', 'Magenta', 'SlateBlue', 'RoyalBlue', 'Cyan', 'Aqua', 'floralwhite',
        'ghostwhite', 'goldenrod', 'mediumslateblue', 'navajowhite', 'navy',
        'sandybrown', 'moccasin'
    ]

    plt.figure(figsize=(10, 6))
    for i in range(len(Machines)):
        Machine = Machines[i]
        Start_time = Machine.O_start
        End_time = Machine.O_end

        for i_1 in range(len(End_time)):
            job_id = Machine.assigned_task[i_1][0]
            width = End_time[i_1] - Start_time[i_1]

            plt.barh(
                i,
                width=width,
                height=0.8,
                left=Start_time[i_1],
                color=M[(job_id - 1) % len(M)],
                edgecolor='black'
            )

            plt.text(
                x=Start_time[i_1] + width / 2 - 0.5,
                y=i,
                s=job_id,
                color='white' if M[(job_id - 1) % len(M)] in ['navy', 'blue', 'purple'] else 'black',
                fontweight='bold'
            )

    plt.yticks(np.arange(len(Machines)), np.arange(1, len(Machines) + 1))
    plt.title(f'Scheduling Gantt Chart - {inst_name}')
    plt.ylabel('Machines')
    plt.xlabel('Time')

    # Save chart to the specified path
    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
    plt.close()


def plot_convergence(avg_history, inst_name, save_path):
    """Plots the average convergence curve of the algorithm."""
    plt.figure(figsize=(8, 5))
    plt.plot(avg_history, label='AO Average Convergence', color='blue', linewidth=2)
    plt.title(f'Convergence Curve - {inst_name}')
    plt.xlabel('Iteration')
    plt.ylabel('Makespan (Cycle Time)')
    plt.grid(True, linestyle='--', alpha=0.7)
    plt.legend()
    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
    plt.close()


def plot_boxplot(run_best_objs, inst_name, save_path):
    """Plots the boxplot distribution of the final makespans."""
    plt.figure(figsize=(6, 5))
    # Note: 'labels' updated to 'tick_labels' to fix the Matplotlib warning
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
    """Run a single Aquila Optimizer execution for one instance."""
    inst_name, run_seed, POP_SIZE, MAX_ITER, data_dir = args

    random.seed(run_seed)
    np.random.seed(run_seed)

    Processing_time, J, M_num, O_num, J_num, meta = get_instance(
        source="benchmark",
        name=inst_name,
        data_dir=data_dir,
    )

    start_time = time.time()

    ao = Aquila_Optimizer_FJSSP(
        J=J,
        Processing_time=Processing_time,
        M_num=M_num,
        Pop_size=POP_SIZE,
        Maxiter=MAX_ITER
    )

    # Capture the history array alongside the chromosome and best value
    best_chromosome, best_makespan, conv_history = ao.optimize()

    end_time = time.time()
    elapsed = end_time - start_time

    print(f"[{inst_name}] Run seed={run_seed}: best Cmax = {best_makespan}, time = {elapsed:.4f} s")

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

    # =========================================================================
    # --- DIRECTORY CONFIGURATION ---
    # =========================================================================
    ROOT_DIR = Path(r"C:\Users\Guhan\Desktop\AQUILA_FJSSP\MODELS\AQUILA_FJSSP_v2")
    VISUALS_DIR = ROOT_DIR / "Visuals"

    GANTT_DIR = VISUALS_DIR / "gantt"
    CONV_DIR = VISUALS_DIR / "convergence"
    BOX_DIR = VISUALS_DIR / "boxplots"

    # Automatically create the directories if they don't exist
    for directory in [GANTT_DIR, CONV_DIR, BOX_DIR]:
        directory.mkdir(parents=True, exist_ok=True)

    # --- EXPERIMENT HYPERPARAMETERS ---
    num_runs = 10
    POP_SIZE = 30
    MAX_ITER = 500

    # =========================================================================
    # --- EXECUTION CONFIGURATION ---
    # Set RUN_MODE to one of the following: 'SINGLE', 'AUTHOR', or 'ALL'
    # =========================================================================
    RUN_MODE = 'AUTHOR'

    TARGET_AUTHOR = 'brandimarte'  # Used if RUN_MODE = 'AUTHOR' (e.g., 'kacem', 'hurink')
    TARGET_INSTANCE = 'mk01'  # Used if RUN_MODE = 'SINGLE'
    # =========================================================================

    instance_names = list_available_instances(DEFAULT_DATA_DIR)
    print("Available instances loaded from directory.")
    print(f"Current Execution Mode: {RUN_MODE}")

    results = []

    # Loops through instances and filters based on your configuration
    for inst_name in instance_names:
        try:
            # 1. Fetch metadata early to check the author/path and apply filters
            Processing_time, J, M_num, O_num, J_num, meta_for_instance = get_instance(
                source="benchmark",
                name=inst_name,
                data_dir=DEFAULT_DATA_DIR,
            )

            # 2. Routing logic based on RUN_MODE
            if RUN_MODE == 'SINGLE':
                if inst_name != TARGET_INSTANCE:
                    continue
            elif RUN_MODE == 'AUTHOR':
                if TARGET_AUTHOR.lower() not in meta_for_instance.get("path", "").lower():
                    continue
            elif RUN_MODE == 'ALL':
                pass  # Runs everything, no skips

            # If it passes the filter, print the header and execute
            print(f"\n================ INSTANCE: {inst_name} ================\n")

            run_seeds = [random.randint(0, 10_000_000) for _ in range(num_runs)]

            run_args = [
                (inst_name, run_seeds[i], POP_SIZE, MAX_ITER, DEFAULT_DATA_DIR)
                for i in range(num_runs)
            ]

            with ProcessPoolExecutor() as executor:
                outputs = list(executor.map(run_single_ao, run_args))

            run_times = [o["time"] for o in outputs]
            run_best_obj = [o["best_obj"] for o in outputs]
            run_histories = [o["history"] for o in outputs]

            # Generate a clean timestamp for this specific instance run
            timestamp = time.strftime("%Y%m%d_%H%M%S")

            # 1. Generate the Convergence Plot (Averaged across 10 runs)
            avg_history = np.mean(run_histories, axis=0)
            conv_path = CONV_DIR / f"convergence_{inst_name}_{timestamp}.png"
            plot_convergence(avg_history, inst_name, conv_path)

            # 2. Generate the Boxplot
            box_path = BOX_DIR / f"boxplot_{inst_name}_{timestamp}.png"
            plot_boxplot(run_best_obj, inst_name, box_path)

            # 3. Decode the absolute best run to generate the Gantt chart
            best_run_idx = np.argmin(run_best_obj)
            best_overall_chrom = outputs[best_run_idx]["best_chromosome"]

            decoder = Decode(J, Processing_time, M_num)
            decoder.decode(best_overall_chrom, sum(J.values()))

            gantt_path = GANTT_DIR / f"gantt_{inst_name}_{timestamp}.png"
            Gantt(decoder.Machines, inst_name, gantt_path)

            # Statistics mapping
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

    # Export to CSV
    df_results = pd.DataFrame(
        results,
        columns=["instance_name", "computational_time", "objective_value", "optimal(lower_bound)"]
    )

    print("\n================ FINAL SUMMARY TABLE ================\n")
    print(df_results)

    # Name the file dynamically based on the run mode so you don't overwrite your data!
    if RUN_MODE == 'SINGLE':
        out_path = ROOT_DIR / f"ao_results_single_{TARGET_INSTANCE}_{timestamp}.csv"
    elif RUN_MODE == 'AUTHOR':
        out_path = ROOT_DIR / f"ao_results_author_{TARGET_AUTHOR}_{timestamp}.csv"
    else:
        out_path = ROOT_DIR / f"ao_results_ALL_{timestamp}.csv"

    df_results.to_csv(out_path, index=False)
    print(f"\nResults saved to: {out_path.resolve()}")