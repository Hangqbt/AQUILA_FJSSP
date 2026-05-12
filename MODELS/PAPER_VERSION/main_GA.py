import os
import time
import random
from contextlib import redirect_stdout
from concurrent.futures import ProcessPoolExecutor

import pandas as pd
import matplotlib.pyplot as plt

from GA import GA
from Instance import *
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
                i,  # y position (machine row)
                width=width,  # bar length
                height=0.8,  # bar thickness
                left=Start_time[i_1],  # x start (time)
                color=M[job_id - 1],  # color by job
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
    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance(
            source="benchmark", name=inst_name, data_dir=data_dir
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
    MASTER_SEED = 123
    random.seed(MASTER_SEED)
    np.random.seed(MASTER_SEED)

    POP_SIZE = 300
    PC = 0.85
    PM = 0.15
    GENERATIONS = 50

    instance_names = list_available_instances(DEFAULT_DATA_DIR)
    print("Available instances:", instance_names)
    instances = [
        "mt10c1", "mt10cc", "mt10x", "mt10xx", "mt10xxx", "mt10xy", "mt10xyz",
        "setb4c9", "setb4cc", "setb4x", "setb4xx", "setb4xxx", "setb4xy", "setb4xyz",
        "seti5c12", "seti5cc", "seti5x", "seti5xx", "seti5xxx", "seti5xy", "seti5xyz"
    ]

    results = []
    seedwise_results = []
    seedwise_time_results = []

    for inst_name in instances:
        print(f"\n================ INSTANCE: {inst_name} ================\n")

        run_seeds = [55589, 97402, 89190, 25814, 16969, 31181, 1573, 64163, 32093, 62154,
                     61472, 90888, 93148, 60149, 54209, 7839, 27714, 17168, 62709, 26491]

        with open(os.devnull, 'w') as f, redirect_stdout(f):
            _, _, _, _, _, meta_for_instance = get_instance(
                source="benchmark",
                name=inst_name,
                data_dir=DEFAULT_DATA_DIR,
            )

        run_args = [
            (inst_name, run_seeds[i], POP_SIZE, PC, PM, GENERATIONS, DEFAULT_DATA_DIR)
            for i in range(len(run_seeds))
        ]

        with ProcessPoolExecutor() as executor:
            outputs = list(executor.map(run_single_ga, run_args))

        run_times = [o["time"] for o in outputs]
        run_best_obj = [o["best_obj"] for o in outputs]

        # Seed-wise fitness
        seed_row = {"instance_name": inst_name}
        for i, seed in enumerate(run_seeds):
            seed_row[f"seed_{seed}"] = run_best_obj[i]

        seedwise_results.append(seed_row)

        # Seed-wise runtime
        time_row = {"instance_name": inst_name}
        for i, seed in enumerate(run_seeds):
            time_row[f"seed_{seed}"] = run_times[i]

        seedwise_time_results.append(time_row)

        # Summary stats
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
    df_seedwise = pd.DataFrame(seedwise_results, columns=seed_columns)

    print("\n================ SEED-WISE FITNESS TABLE ================\n")
    print(df_seedwise)

    # Seed-wise time table
    df_seedwise_time = pd.DataFrame(seedwise_time_results, columns=seed_columns)

    print("\n================ SEED-WISE TIME TABLE ================\n")
    print(df_seedwise_time)

    # Save CSV files
    out_path_summary = Path("ga_benchmark_results.csv")
    df_results.to_csv(out_path_summary, index=False)

    out_path_seedwise = Path("ga_seedwise_fitness_results.csv")
    df_seedwise.to_csv(out_path_seedwise, index=False)

    # NEW FILE
    out_path_seedwise_time = Path("ga_seedwise_time_results.csv")
    df_seedwise_time.to_csv(out_path_seedwise_time, index=False)

    print(f"\nSummary results saved to: {out_path_summary.resolve()}")
    print(f"Seed-wise fitness saved to: {out_path_seedwise.resolve()}")
    print(f"Seed-wise time saved to: {out_path_seedwise_time.resolve()}")
