import time
import random
from pathlib import Path
from concurrent.futures import ProcessPoolExecutor

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from GA import GA
from Instance import *   # assumes: get_instance, list_available_instances, DEFAULT_DATA_DIR
from Encode import Encode


def Gantt(Machines):
    """
    Plot a Gantt chart from the decoded machine schedules.

    Parameters
    ----------
    Machines : list
        A list of machine objects produced by Decode(...).decode(...)
        Each machine is expected to expose:
          - O_start : list[int]  start times of operations on this machine
          - O_end   : list[int]  end times  of operations on this machine
          - assigned_task : list[tuple]  typically (job_id, op_id) or (job_id, ...)
                            The first element (job_id) is used for bar color & label.
    """
    # Color palette used to differentiate jobs (index: job_id - 1)
    M = [
        'red', 'blue', 'yellow', 'orange', 'green', 'palegoldenrod', 'purple', 'pink',
        'Thistle', 'Magenta', 'SlateBlue', 'RoyalBlue', 'Cyan', 'Aqua', 'floralwhite',
        'ghostwhite', 'goldenrod', 'mediumslateblue', 'navajowhite', 'navy',
        'sandybrown', 'moccasin'
    ]

    # Iterate over machines by index (y-axis row)
    for i in range(len(Machines)):
        Machine = Machines[i]
        Start_time = Machine.O_start
        End_time = Machine.O_end

        # Draw one horizontal bar for each scheduled operation on this machine
        for i_1 in range(len(End_time)):
            # Job ID is expected at index 0 of assigned_task tuple; subtract 1 to index the color list
            job_id = Machine.assigned_task[i_1][0]

            # Bar width = processing time = end - start
            width = End_time[i_1] - Start_time[i_1]

            plt.barh(
                i,                                  # y position (machine row)
                width=width,                        # bar length
                height=0.8,                         # bar thickness
                left=Start_time[i_1],               # x start (time)
                color=M[job_id - 1],                # color by job
                edgecolor='black'
            )

            # Put job ID label roughly centered on the bar
            plt.text(
                x=Start_time[i_1] + width / 2 - 0.5,
                y=i,
                s=job_id
            )

    # y-axis ticks: show 1..(num_machines) for readability
    plt.yticks(np.arange(len(Machines) + 1), np.arange(1, len(Machines) + 2))
    plt.title('Scheduling Gantt chart')
    plt.ylabel('Machines')
    plt.xlabel('Time (min)')
    plt.savefig('Optimized schedule Gantt chart.png')
    plt.show()


def run_single_ga(args):
    """
    Run a single GA execution for one instance (one run).

    Parameters
    ----------
    args : tuple
        (inst_name, run_seed, POP_SIZE, PC, PM, GENERATIONS, data_dir)

    Returns
    -------
    dict with keys:
        - "instance": instance name
        - "best_obj": best Cmax found
        - "time": elapsed time in seconds
    """
    inst_name, run_seed, POP_SIZE, PC, PM, GENERATIONS, data_dir = args

    # Make run reproducible
    random.seed(run_seed)
    np.random.seed(run_seed)

    # Load instance (benchmark source)
    Processing_time, J, M_num, O_num, J_num, meta = get_instance(
        source="benchmark",
        name=inst_name,
        data_dir=data_dir,
    )

    # Initialize GA
    g = GA(
        Pop_size=POP_SIZE,
        Pc=PC,
        Pm=PM,
        Generations=GENERATIONS,
        seed=run_seed,
    )

    # Initialize population using Encode
    e = Encode(Processing_time, g.Pop_size, J, J_num, M_num)
    CHS1 = e.Global_initial()
    CHS2 = e.Random_initial()
    CHS3 = e.Local_initial()
    C = np.vstack((CHS1, CHS2, CHS3))

    Optimal_fit = float('inf')
    Optimal_CHS = None

    start_time = time.time()

    for gen in range(g.Generations):
        Fit = g.fitness(C, J, Processing_time, M_num, O_num)
        best_idx = Fit.index(min(Fit))
        Best = C[best_idx]
        best_fitness = Fit[best_idx]

        if best_fitness < Optimal_fit:
            Optimal_fit = best_fitness
            Optimal_CHS = Best

        # Evolution: crossover and mutation
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

    end_time = time.time()
    elapsed = end_time - start_time

    print(f"[{inst_name}] Run seed={run_seed}: best Cmax = {Optimal_fit}, time = {elapsed:.4f} s")

    return {
        "instance": inst_name,
        "best_obj": Optimal_fit,
        "time": elapsed,
    }


if __name__ == '__main__':
    # Global random seed for reproducibility of run-level seeds
    MASTER_SEED = 123
    random.seed(MASTER_SEED)
    np.random.seed(MASTER_SEED)

    num_runs = 10  # Number of independent GA runs per instance

    # Hyperparameters for GA
    POP_SIZE = 300
    PC = 0.85
    PM = 0.15
    GENERATIONS = 50

    # Get all instance names from instances.json via Instance.py helpers
    instance_names = list_available_instances(DEFAULT_DATA_DIR)
    print("Available instances:", instance_names)

    results = []  # will store dicts for DataFrame rows

    # For now, you were running only the first instance; keep that:
    for inst_name in instance_names[:15]:
        print(f"\n================ INSTANCE: {inst_name} ================\n")

        # Pre-generate seeds for this instance's runs
        run_seeds = [random.randint(0, 10_000_000) for _ in range(num_runs)]

        # Get metadata once (for optimum / bounds)
        _, _, _, _, _, meta_for_instance = get_instance(
            source="benchmark",
            name=inst_name,
            data_dir=DEFAULT_DATA_DIR,
        )

        # Prepare arguments for each run
        run_args = [
            (inst_name, run_seeds[i], POP_SIZE, PC, PM, GENERATIONS, DEFAULT_DATA_DIR)
            for i in range(num_runs)
        ]

        # Run GA runs in parallel using multiprocessing
        with ProcessPoolExecutor() as executor:
            outputs = list(executor.map(run_single_ga, run_args))

        # Collect times and best objectives from parallel runs
        run_times = [o["time"] for o in outputs]
        run_best_obj = [o["best_obj"] for o in outputs]

        # Compute statistics across runs for this instance
        mean_time = float(np.mean(run_times))
        std_time = float(np.std(run_times, ddof=1)) if len(run_times) > 1 else 0.0

        mean_obj = float(np.mean(run_best_obj))
        std_obj = float(np.std(run_best_obj, ddof=1)) if len(run_best_obj) > 1 else 0.0

        # Format as "mean ± std"
        time_str = f"{mean_time:.4f} ± {std_time:.4f}"
        obj_str = f"{mean_obj:.2f} ± {std_obj:.2f}"

        # Get optimal value or lower bound from metadata
        optimum = meta_for_instance.get("optimum", None)
        bounds = meta_for_instance.get("bounds", {})

        if optimum is not None:
            opt_or_lb = optimum
        else:
            # If optimum is None, use lower bound if available
            opt_or_lb = bounds.get("lower", None)

        # Add to results list
        results.append({
            "instance_name": inst_name,
            "computational_time": time_str,
            "objective_value": obj_str,
            "optimal(lower_bound)": opt_or_lb,
        })

    # Build DataFrame
    df_results = pd.DataFrame(
        results,
        columns=["instance_name", "computational_time", "objective_value", "optimal(lower_bound)"]
    )

    print("\n================ FINAL SUMMARY TABLE ================\n")
    print(df_results)

    # Optionally save to CSV
    out_path = Path("ga_benchmark_results_parallel.csv", index=False)
    df_results.to_csv(out_path)
    print(f"\nResults saved to: {out_path.resolve()}")
