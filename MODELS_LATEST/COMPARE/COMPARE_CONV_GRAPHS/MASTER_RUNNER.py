import os

# Best-effort hash seed for the main process
os.environ['PYTHONHASHSEED'] = str(123)

import time
import random
import importlib.util
import sys
from contextlib import redirect_stdout
from concurrent.futures import ProcessPoolExecutor

import numpy as np
import matplotlib.pyplot as plt

# Shared dependencies
from Instance import get_instance
from Encode import Encode


# =====================================================================
# DYNAMIC MODULE LOADER
# =====================================================================
def load_module_from_path(module_name, file_path):
    """
    Dynamically loads a Python file from an absolute path.
    """
    spec = importlib.util.spec_from_file_location(module_name, file_path)
    module = importlib.util.module_from_spec(spec)
    sys.modules[module_name] = module
    spec.loader.exec_module(module)
    return module


# =====================================================================
# 1. RUNNER: PURE GA
# =====================================================================
def run_ga_pure(args):
    inst_name, seed, file_path, data_dir = args

    # --- STRICT DETERMINISM LOCK ---
    # Lock RNG states BEFORE loading dynamic modules or external dependencies
    # to prevent import-time stochasticity or state leakage across recycled workers.
    os.environ['PYTHONHASHSEED'] = str(123)
    random.seed(seed)
    np.random.seed(seed)
    # -------------------------------

    # Force a unique namespace so recycled process workers don't share module state
    unique_mod_name = f"GA_pure_{inst_name}_{seed}"
    ga_module = load_module_from_path(unique_mod_name, file_path)
    GA = ga_module.GA

    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance(
            source="benchmark", name=inst_name, data_dir=data_dir
        )

    POP_SIZE = 50
    PC = 0.85
    PM = 0.15
    GENERATIONS = 300

    g = GA(Pop_size=POP_SIZE, Pc=PC, Pm=PM, Generations=GENERATIONS, seed=seed)
    e = Encode(Processing_time, g.Pop_size, J, J_num, M_num)

    CHS1 = e.Global_initial()
    CHS2 = e.Random_initial()
    CHS3 = e.Local_initial()
    C = np.vstack((CHS1, CHS2, CHS3))

    Optimal_fit = float('inf')
    history_best = []

    for gen in range(g.Generations):
        Fit = g.fitness(C, J, Processing_time, M_num, O_num)
        best_idx = Fit.index(min(Fit))
        Best = C[best_idx]
        best_fitness = Fit[best_idx]

        if best_fitness < Optimal_fit:
            Optimal_fit = best_fitness

        history_best.append(Optimal_fit)

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

    print(f"[Pure GA] Seed {seed} Finished. Best: {Optimal_fit}")
    return history_best


# =====================================================================
# 2. RUNNER: GA + TABU
# =====================================================================
def run_ga_tabu(args):
    inst_name, seed, file_path, data_dir = args

    # --- STRICT DETERMINISM LOCK ---
    os.environ['PYTHONHASHSEED'] = str(123)
    random.seed(seed)
    np.random.seed(seed)
    # -------------------------------

    unique_mod_name = f"GA_tabu_{inst_name}_{seed}"
    ga_module = load_module_from_path(unique_mod_name, file_path)
    GA = ga_module.GA

    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance(
            source="benchmark", name=inst_name, data_dir=data_dir
        )

    Pop_size = 50
    Pc = 0.8
    Pm = 0.05
    Generations = 300

    GA_Instance = GA(Pop_size=Pop_size, Pc=Pc, Pm=Pm, Generations=Generations, seed=seed)
    e = Encode(Processing_time, Pop_size, J, J_num, M_num)
    Len = e.Len_Chromo

    CHS1 = e.Global_initial()
    CHS2 = e.Random_initial()
    CHS3 = e.Local_initial()
    C = np.vstack((CHS1, CHS2, CHS3))

    Fit = GA_Instance.fitness(C, J, Processing_time, M_num, Len)
    best_fitness = min(Fit)
    best_chs = C[Fit.index(best_fitness)]

    run_best_history = [best_fitness]
    stagnation_counter = 0

    for i in range(GA_Instance.Generations):
        C_new = [best_chs.copy()]

        while len(C_new) < GA_Instance.Pop_size:
            tourney_size = 5
            tourney1 = random.sample(range(len(C)), tourney_size)
            best_idx1 = min(tourney1, key=lambda idx: Fit[idx])
            p1 = C[best_idx1]

            tourney2 = random.sample(range(len(C)), tourney_size)
            best_idx2 = min(tourney2, key=lambda idx: Fit[idx])
            p2 = C[best_idx2]

            if random.random() < GA_Instance.Pc:
                p1, p2 = GA_Instance.machine_cross(p1.copy(), p2.copy(), Len)
                p1, p2 = GA_Instance.operation_cross(p1.copy(), p2.copy(), Len, J_num)

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

        gen_best = min(Fit)
        record_broken = False

        if gen_best < best_fitness:
            best_fitness = gen_best
            best_chs = C[Fit.index(gen_best)]
            record_broken = True

        if record_broken:
            stagnation_counter = 0
        else:
            stagnation_counter += 1

        if stagnation_counter >= 3:
            best_idx = int(np.argmin(Fit))
            refined_chs, refined_fit = GA_Instance.tabu_local_search(
                CHS=C[best_idx], current_best_fit=Fit[best_idx], J=J,
                Processing_time=Processing_time, M_num=M_num, T0=Len,
                num_steps=12, neighbors_per_step=6, tenure=5
            )
            C[best_idx] = refined_chs
            Fit[best_idx] = refined_fit

            if refined_fit < best_fitness:
                best_fitness = refined_fit
                best_chs = np.copy(refined_chs)
                stagnation_counter = 0

        run_best_history.append(best_fitness)

    print(f"[GA + Tabu] Seed {seed} Finished. Best: {best_fitness}")
    return run_best_history


# =====================================================================
# 3. RUNNER: DAO (Works for both Pure and Tabu)
# =====================================================================
def run_ao(args):
    inst_name, seed, file_path, algo_label, data_dir = args

    # --- STRICT DETERMINISM LOCK ---
    os.environ['PYTHONHASHSEED'] = str(123)
    random.seed(seed)
    np.random.seed(seed)
    # -------------------------------

    unique_mod_name = f"AO_{algo_label.replace(' ', '_')}_{inst_name}_{seed}"
    ao_module = load_module_from_path(unique_mod_name, file_path)
    Aquila_Optimizer_FJSSP = ao_module.Aquila_Optimizer_FJSSP

    with open(os.devnull, 'w') as f, redirect_stdout(f):
        Processing_time, J, M_num, O_num, J_num, meta = get_instance(
            source="benchmark", name=inst_name, data_dir=data_dir
        )

    POP_SIZE = 300
    MAX_ITER = 50

    ao = Aquila_Optimizer_FJSSP(J=J, Processing_time=Processing_time, M_num=M_num, Pop_size=POP_SIZE, Maxiter=MAX_ITER)

    optimize_results = ao.optimize()
    best_makespan = optimize_results[1]
    conv_history = optimize_results[2]

    print(f"[{algo_label}] Seed {seed} Finished. Best: {best_makespan:.2f}")
    return conv_history


# =====================================================================
# 4. PLOTTING FUNCTION
# =====================================================================
def plot_comparative_convergence(algo_data_dict, inst_name, save_path="multi_algo_convergence.png"):
    plt.figure(figsize=(9, 6))

    colors = ['#0072BD', '#D95319', '#EDB120', '#7E2F8E']

    for idx, (algo_name, history) in enumerate(algo_data_dict.items()):
        # Map this algorithm's specific generation count to a normalized 0-1 scale
        x = np.linspace(0, 1, len(history))
        plt.plot(x, history, label=algo_name, color=colors[idx % len(colors)], linewidth=2.5)

    plt.title(f'Convergence Analysis - {inst_name}', fontweight='bold', fontsize=14)
    plt.xlabel('Computational Budget', fontsize=12)
    plt.ylabel('Makespan', fontsize=12)

    # Set x-ticks to display every 20%
    plt.xticks(np.arange(0, 1.2, 0.2), ['0%', '20%', '40%', '60%', '80%', '100%'])

    plt.grid(True, linestyle='-', alpha=0.7)
    plt.legend(loc='upper right', framealpha=1.0, edgecolor='black', fontsize=11)

    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
    plt.close()
    print(f"\n✅ Graph successfully saved to {save_path}")


# =====================================================================
# 5. MAIN EXECUTION
# =====================================================================
if __name__ == '__main__':
    # --- CONFIGURATION ---
    instances = [
        "mt10c1", "mt10cc", "mt10x", "mt10xx", "mt10xxx", "mt10xy", "mt10xyz",
        "setb4c9", "setb4cc", "setb4x", "setb4xx", "setb4xxx", "setb4xy", "setb4xyz",
        "seti5c12", "seti5cc", "seti5x", "seti5xx", "seti5xxx", "seti5xy", "seti5xyz"
    ]
    DATA_DIR = "Data"

    # Setup your absolute paths
    PATH_GA_PURE = r"C:\Users\DELL\PycharmProjects\AQUILA_FJSSP\MODELS_LATEST\PAPER_VERSION_50prob\GA.py"
    PATH_GA_TABU = r"C:\Users\DELL\PycharmProjects\AQUILA_FJSSP\MODELS_LATEST\GA_TABU_1ISTO1\GA.py"
    PATH_DAO_PURE = r"C:\Users\DELL\PycharmProjects\AQUILA_FJSSP\MODELS_LATEST\DAO_NO_HYBRID_50prob\aquila_jssp_NO_HYBRID_W_GRAPH.py"
    PATH_DAO_TABU = r"C:\Users\DELL\PycharmProjects\AQUILA_FJSSP\MODELS_LATEST\PAPER_VERSION_50prob\aquila_jssp_TABU_W_GRAPH.py"

    # Exact requested 20 seeds
    SEEDS = [
        55589, 97402, 89190, 25814, 16969, 31181, 1573, 64163, 32093, 62154,
        61472, 90888, 93148, 60149, 54209, 7839, 27714, 17168, 62709, 26491
    ]

    # Global lock for the orchestrator
    random.seed(123)
    np.random.seed(123)

    print(f"🚀 Starting Comparative Benchmark across {len(instances)} instances and {len(SEEDS)} seeds...\n")

    for INSTANCE_TO_PLOT in instances:
        print(f"\n=================================================================")
        print(f"🚀 PROCESSING INSTANCE: {INSTANCE_TO_PLOT}")
        print(f"=================================================================\n")

        # Dictionary to hold the final averaged histories for the current instance
        algorithms_to_plot = {}

        # ---------------------------------------------------------
        # Execution Block 1: Pure GA
        # ---------------------------------------------------------
        print("-----------------------------------------")
        print(f"PHASE 1: Running Pure GA for {INSTANCE_TO_PLOT}...")
        ga_pure_tasks = [(INSTANCE_TO_PLOT, s, PATH_GA_PURE, DATA_DIR) for s in SEEDS]
        with ProcessPoolExecutor() as executor:
            ga_pure_hists = list(executor.map(run_ga_pure, ga_pure_tasks))
        algorithms_to_plot['Pure GA'] = np.mean(ga_pure_hists, axis=0).tolist()
        print(">> Pure GA Data Collected and Averaged.\n")

        # ---------------------------------------------------------
        # Execution Block 2: GA + Tabu
        # ---------------------------------------------------------
        print("-----------------------------------------")
        print(f"PHASE 2: Running GA + Tabu for {INSTANCE_TO_PLOT}...")
        ga_tabu_tasks = [(INSTANCE_TO_PLOT, s, PATH_GA_TABU, DATA_DIR) for s in SEEDS]
        with ProcessPoolExecutor() as executor:
            ga_tabu_hists = list(executor.map(run_ga_tabu, ga_tabu_tasks))
        algorithms_to_plot['GA + Tabu'] = np.mean(ga_tabu_hists, axis=0).tolist()
        print(">> GA + Tabu Data Collected and Averaged.\n")

        # ---------------------------------------------------------
        # Execution Block 3: Pure DAO
        # ---------------------------------------------------------
        print("-----------------------------------------")
        print(f"PHASE 3: Running Pure DAO for {INSTANCE_TO_PLOT}...")
        ao_pure_tasks = [(INSTANCE_TO_PLOT, s, PATH_DAO_PURE, "Pure DAO", DATA_DIR) for s in SEEDS]
        with ProcessPoolExecutor() as executor:
            ao_pure_hists = list(executor.map(run_ao, ao_pure_tasks))
        algorithms_to_plot['Pure DAO'] = np.mean(ao_pure_hists, axis=0).tolist()
        print(">> Pure DAO Data Collected and Averaged.\n")

        # ---------------------------------------------------------
        # Execution Block 4: DAO + Tabu
        # ---------------------------------------------------------
        print("-----------------------------------------")
        print(f"PHASE 4: Running DAO + Tabu for {INSTANCE_TO_PLOT}...")
        ao_tabu_tasks = [(INSTANCE_TO_PLOT, s, PATH_DAO_TABU, "DAO + Tabu", DATA_DIR) for s in SEEDS]
        with ProcessPoolExecutor() as executor:
            ao_tabu_hists = list(executor.map(run_ao, ao_tabu_tasks))
        algorithms_to_plot['DAO + Tabu'] = np.mean(ao_tabu_hists, axis=0).tolist()
        print(">> DAO + Tabu Data Collected and Averaged.\n")

        # ---------------------------------------------------------
        # Final Generation
        # ---------------------------------------------------------
        print("-----------------------------------------")
        print(f"Generating Final Comparative Plot for {INSTANCE_TO_PLOT}...")
        plot_name = f"comparative_convergence_{INSTANCE_TO_PLOT}.png"
        plot_comparative_convergence(algorithms_to_plot, INSTANCE_TO_PLOT, save_path=plot_name)