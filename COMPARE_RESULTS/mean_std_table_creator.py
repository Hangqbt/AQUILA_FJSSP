import pandas as pd
import numpy as np


def generate_mean_std_table(dao_file, competitor_files, metric_name, output_sheet, writer):
    print(f"\n% ==========================================")
    print(f"% Processing Mean ± STD for: {output_sheet}")
    print(f"% ==========================================\n")

    # Load DAO baseline
    df_dao = pd.read_csv(dao_file)

    # Load competitors
    dfs_comp = {name: pd.read_csv(path) for name, path in competitor_files.items()}

    # Find common instances across ALL files
    common_instances = set(df_dao["instance_name"])
    for df in dfs_comp.values():
        common_instances = common_instances.intersection(set(df["instance_name"]))

    common_instances = sorted(list(common_instances))

    # Setup two dictionaries: one for Excel (clean text), one for LaTeX (math mode)
    results_data_excel = {"Instance": common_instances}
    results_data_latex = {"Instance": common_instances}

    # We will process DAO first, then competitors
    all_algos = {"AO_no_hybrid (DAO)": df_dao}
    all_algos.update(dfs_comp)

    for algo_name, df_algo in all_algos.items():
        excel_list = []
        latex_list = []
        seed_cols = [c for c in df_algo.columns if c.startswith("seed_")]

        for inst in common_instances:
            row = df_algo[df_algo["instance_name"] == inst].iloc[0]
            vals = row[seed_cols].values.astype(float)

            # Drop NaNs
            vals = vals[~np.isnan(vals)]

            if len(vals) > 0:
                mean_val = np.mean(vals)
                std_val = np.std(vals, ddof=1)  # ddof=1 for Sample Standard Deviation

                # Excel gets the standard symbol, LaTeX gets math mode $\pm$
                excel_list.append(f"{mean_val:.2f} ± {std_val:.2f}")
                latex_list.append(f"${mean_val:.2f} \\pm {std_val:.2f}$")
            else:
                excel_list.append("NaN")
                latex_list.append("NaN")

        results_data_excel[algo_name] = excel_list
        results_data_latex[algo_name] = latex_list

    # --- 1. EXPORT TO EXCEL ---
    df_excel = pd.DataFrame(results_data_excel)
    df_excel.to_excel(writer, sheet_name=output_sheet, index=False)

    # --- 2. GENERATE LATEX CODE ---
    df_latex = pd.DataFrame(results_data_latex)

    # Escape underscores in column names to prevent LaTeX math-mode errors
    df_latex.columns = [str(col).replace('_', '\\_') for col in df_latex.columns]

    col_format = "l" + "c" * (len(df_latex.columns) - 1)
    caption = f"Raw Values: Mean $\\pm$ Standard Deviation ({metric_name})"
    label_name = f"tab:mean_std_{metric_name.replace(' ', '_').lower()}"

    try:
        # Modern Pandas formatting utilizing booktabs and hiding the index
        latex_code = df_latex.style.hide(axis="index").to_latex(
            column_format=col_format,
            position="H",
            position_float="centering",
            hrules=True,
            caption=caption,
            label=label_name
        )
    except AttributeError:
        # Fallback for older Pandas versions
        latex_code = df_latex.to_latex(
            index=False,
            column_format=col_format,
            position="H",
            escape=False,  # We don't want to escape our $ \pm $ math syntax
            caption=caption,
            label=label_name
        )

    print(latex_code)


if __name__ == "__main__":

    # --- FILE SETUP ---
    # Baseline: AO_no_hybrid (DAO)
    dao_fitness = "ao_no_hybrid_seedwise_fitness_results.csv"
    dao_time = "ao_no_hybrid_seedwise_time_results.csv"

    # Competitors
    competitors_fitness = {
        "AO_hybrid": "ao_seedwise_fitness_results.csv",
        "Original_GA": "ga_seedwise_fitness_results.csv",
        "GA_TABU": "ga_tabu_seedwise_fitness_results.csv",
        "ABC": "abc_seedwise_fitness_results.csv",
        "ABC_TABU": "abc_tabu_seedwise_fitness_results.csv"
    }

    competitors_time = {
        "AO_hybrid": "ao_seedwise_time_results.csv",
        "Original_GA": "ga_seedwise_time_results.csv",
        "GA_TABU": "ga_tabu_seedwise_time_results.csv",
        "ABC": "abc_seedwise_time_results.csv",
        "ABC_TABU": "abc_tabu_seedwise_time_results.csv"
    }

    output_excel = "All_Algorithms_Mean_STD.xlsx"

    # Create a single Excel workbook with both sheets
    try:
        with pd.ExcelWriter(output_excel) as writer:
            generate_mean_std_table(dao_fitness, competitors_fitness, "Fitness", "Fitness", writer)
            generate_mean_std_table(dao_time, competitors_time, "Time", "Time", writer)

        print(f"\n==========================================")
        print(f"Success! Excel file saved as: '{output_excel}'")
        print(f"==========================================")
    except Exception as e:
        print(f"Error: {e}")