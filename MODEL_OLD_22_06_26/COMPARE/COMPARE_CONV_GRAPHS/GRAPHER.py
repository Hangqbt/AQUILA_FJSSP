import matplotlib.pyplot as plt
import numpy as np


def plot_comparative_convergence(algo_data_dict, inst_name, save_path="multi_convergence.png"):
    """
    Generates a comparative convergence graph for multiple algorithms.

    Parameters
    ----------
    algo_data_dict : dict
        Format: {'Algorithm Name': [history_array]}
        Example: {'Pure GA': ga_hist, 'GA+Tabu': ga_tabu_hist, ...}
    inst_name : str
        The name of the benchmark instance being plotted (e.g., 'mt10c1').
    save_path : str
        Where to save the image.
    """
    plt.figure(figsize=(9, 6))

    # Distinct colors that mimic your reference image style
    colors = ['#1f77b4', '#d62728', '#ff7f0e', '#2ca02c']

    # Find the maximum length to normalize the X-axis (e.g., 300 generations)
    max_len = max(len(hist) for hist in algo_data_dict.values())

    # Plot each algorithm
    for idx, (algo_name, history) in enumerate(algo_data_dict.items()):
        # Forward-fill shorter histories (like your 50-iteration AO) so they stretch across the graph
        padded_history = list(history)
        if len(padded_history) < max_len:
            pad_length = max_len - len(padded_history)
            padded_history.extend([padded_history[-1]] * pad_length)

        # X-axis from 1 to max_len
        x = np.arange(1, max_len + 1)

        # Plotting with a thick line mimicking the step-down nature of convergence
        plt.plot(x, padded_history, label=algo_name, color=colors[idx % len(colors)], linewidth=2.5)

    # Graph Formatting
    plt.title(f'Convergence Graph - {inst_name}', fontweight='bold', fontsize=14)
    plt.xlabel('Iteration / Generation', fontsize=12)
    plt.ylabel('Fitness Value (Makespan)', fontsize=12)

    # Add a solid grid like the reference image
    plt.grid(True, linestyle='-', alpha=0.7)

    # Legend formatting
    plt.legend(loc='upper right', framealpha=1.0, edgecolor='black', fontsize=11)

    plt.tight_layout()
    plt.savefig(save_path, dpi=300)
    plt.close()
    print(f"Graph successfully saved to {save_path}")


# ==========================================
# EXAMPLE USAGE:
# ==========================================
if __name__ == '__main__':
    # REPLACE THESE LISTS WITH YOUR ACTUAL EXTRACTED ARRAYS FROM YOUR RUNNERS
    mock_pure_ga = [20, 19.5, 18.2, 17.8, 17.8, 17.1] * 50  # Reaches 17.1 late
    mock_ga_tabu = [20, 18.5, 17.5, 17.2, 17.0, 16.8] * 50  # Better convergence
    mock_pure_dao = [19, 18.0, 17.5, 17.5, 17.5]  # Shorter array (50 iters)
    mock_dao_tabu = [19, 17.2, 16.5, 16.2, 16.2]  # Shorter array (50 iters)

    algorithms_to_plot = {
        'Pure GA': mock_pure_ga,
        'GA + Tabu': mock_ga_tabu,
        'Pure DAO': mock_pure_dao,
        'DAO + Tabu': mock_dao_tabu
    }

    plot_comparative_convergence(algorithms_to_plot, inst_name="mt10c1")