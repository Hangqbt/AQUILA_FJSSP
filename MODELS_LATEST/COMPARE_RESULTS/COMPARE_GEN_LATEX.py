import pandas as pd
import numpy as np
from scipy.stats import wilcoxon


def generate_true_statistical_latex():
    # 1. Map your uploaded raw seed files
    files = {
        'GA': 'ga_seedwise_fitness_results_newinit.csv',
        'GA_T': 'ga_tabu_seedwise_fitness_results_newinit.csv',
        'DAO': 'ao_seedwise_fitness_results_no_hybrid_newinit.csv',
        'DAO_H': 'ao_seedwise_fitness_results_newinit.csv'
    }

    # Dictionary of optimal lower bounds
    optimal_dict = {
        'mt10c1': 927, 'mt10cc': 908, 'mt10x': 918, 'mt10xx': 918, 'mt10xxx': 918,
        'mt10xy': 905, 'mt10xyz': 847, 'setb4c9': 914, 'setb4cc': 907, 'setb4x': 925,
        'setb4xx': 925, 'setb4xxx': 925, 'setb4xy': 910, 'setb4xyz': 902,
        'seti5c12': 1169, 'seti5cc': 1135, 'seti5x': 1198, 'seti5xx': 1194,
        'seti5xxx': 1194, 'seti5xy': 1135, 'seti5xyz': 1125
    }

    # Load into DataFrames
    dfs = {name: pd.read_csv(path).set_index('instance_name') for name, path in files.items()}
    instances = dfs['GA'].index

    # Helper function to perform the true Wilcoxon test on raw arrays
    def compare_statistical_arrays(arr_test, arr_base):
        if arr_base is None or arr_test is None:
            return 'N/A'

        # If the arrays are completely identical, there is no difference
        if np.array_equal(arr_test, arr_base):
            return '='

        # Wilcoxon Test handling ties and zero-differences gracefully
        stat, p = wilcoxon(arr_test, arr_base, zero_method='zsplit')

        # p < 0.05 means it is statistically significant
        if p < 0.05:
            # Lower is better (+ means test array is significantly better)
            return '+' if np.mean(arr_test) < np.mean(arr_base) else '-'
        else:
            # p >= 0.05 means no statistically significant difference
            return '='

    latex_rows = []

    for inst in instances:
        # Extract the 20-seed raw arrays
        arr_ga = dfs['GA'].loc[inst].values if inst in dfs['GA'].index else None
        arr_gat = dfs['GA_T'].loc[inst].values if inst in dfs['GA_T'].index else None
        arr_dao = dfs['DAO'].loc[inst].values if inst in dfs['DAO'].index else None
        arr_daoh = dfs['DAO_H'].loc[inst].values if inst in dfs['DAO_H'].index else None

        # Calculate Means and Sample Standard Deviations (ddof=1)
        m_ga, s_ga = (np.mean(arr_ga), np.std(arr_ga, ddof=1)) if arr_ga is not None else (None, None)
        m_gat, s_gat = (np.mean(arr_gat), np.std(arr_gat, ddof=1)) if arr_gat is not None else (None, None)
        m_dao, s_dao = (np.mean(arr_dao), np.std(arr_dao, ddof=1)) if arr_dao is not None else (None, None)
        m_daoh, s_daoh = (np.mean(arr_daoh), np.std(arr_daoh, ddof=1)) if arr_daoh is not None else (None, None)

        # Run Pairwise Wilcoxon tests
        c_dao_ga = compare_statistical_arrays(arr_dao, arr_ga)
        c_dao_gat = compare_statistical_arrays(arr_dao, arr_gat)

        c_daoh_ga = compare_statistical_arrays(arr_daoh, arr_ga)
        c_daoh_gat = compare_statistical_arrays(arr_daoh, arr_gat)
        c_daoh_dao = compare_statistical_arrays(arr_daoh, arr_dao)

        # String formatting helpers
        def fmt(m, s):
            if m is None: return '-'
            return f"{m:.2f} $\\pm$ {s:.2f}"

        s_ga_str = fmt(m_ga, s_ga)
        s_gat_str = fmt(m_gat, s_gat)
        s_dao_str = fmt(m_dao, s_dao)
        s_daoh_str = fmt(m_daoh, s_daoh)

        dao_out_str = f"{s_dao_str} ({c_dao_ga}, {c_dao_gat})" if m_dao is not None else '-'
        daoh_out_str = f"{s_daoh_str} ({c_daoh_ga}, {c_daoh_gat}, {c_daoh_dao})" if m_daoh is not None else '-'

        # Find absolute best Mean to bold the value
        means = {}
        if m_ga is not None: means['GA'] = m_ga
        if m_gat is not None: means['GA_T'] = m_gat
        if m_dao is not None: means['DAO'] = m_dao
        if m_daoh is not None: means['DAO_H'] = m_daoh

        best_algo = min(means, key=means.get) if means else None

        if best_algo == 'GA': s_ga_str = f"\\textbf{{{s_ga_str}}}"
        if best_algo == 'GA_T': s_gat_str = f"\\textbf{{{s_gat_str}}}"
        if best_algo == 'DAO': dao_out_str = f"\\textbf{{{s_dao_str}}} ({c_dao_ga}, {c_dao_gat})"
        if best_algo == 'DAO_H': daoh_out_str = f"\\textbf{{{s_daoh_str}}} ({c_daoh_ga}, {c_daoh_gat}, {c_daoh_dao})"

        opt_val = optimal_dict.get(inst, '-')
        row = f"{inst} & {opt_val} & {s_ga_str} & {s_gat_str} & {dao_out_str} & {daoh_out_str} \\\\"
        latex_rows.append(row)

    # Print LaTeX Table structure
    print("\\begin{table*}[htbp]")
    print("\\centering")
    print("\\caption{Comparison of optimization results for DAO and GA variants (Statistical Significance)}")
    print("\\label{tab:dao_ga_comparison}")
    print("\\resizebox{\\textwidth}{!}{")
    print("\\begin{tabular}{lccccc}")
    print("\\toprule")
    print(
        "\\textbf{Instance} & \\textbf{Best} & \\textbf{GA} & \\textbf{GA\\_T} & \\textbf{DAO (vs GA, GA\\_T)} & \\textbf{DAO\\_HYBRID (vs GA, GA\\_T, DAO)} \\\\")
    print("\\midrule")
    print("\n".join(latex_rows))
    print("\\bottomrule")
    print("\\end{tabular}")
    print("}")
    print("\\end{table*}")


if __name__ == "__main__":
    generate_true_statistical_latex()