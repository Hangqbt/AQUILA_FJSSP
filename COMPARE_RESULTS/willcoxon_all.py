import pandas as pd
import numpy as np
from scipy.stats import wilcoxon


def run_unified_comparison(dao_file, competitor_files, output_excel, alpha=0.05):
    """
    Compares DAO against multiple algorithms and exports results to a multi-sheet Excel file.

    Perspective (DAO):
      "+" : DAO is significantly better (smaller value)
      "-" : DAO is significantly worse (larger value)
      "=" : No significant difference
    """
    print(f"\n================ Processing {output_excel} ================")
    print(f"Loading DAO baseline from: {dao_file}")
    df_dao = pd.read_csv(dao_file)

    # Load all competitor DataFrames
    dfs_comp = {}
    for comp_name, file_path in competitor_files.items():
        print(f"Loading {comp_name} from: {file_path}")
        dfs_comp[comp_name] = pd.read_csv(file_path)

    # 1. Find common instances across ALL files to ensure a 1-to-1 comparison
    common_instances = set(df_dao["instance_name"])
    for df in dfs_comp.values():
        common_instances = common_instances.intersection(set(df["instance_name"]))

    common_instances = sorted(list(common_instances))
    if not common_instances:
        raise ValueError("No common instances found across the provided files!")

    # 2. Setup dictionaries to build our final DataFrames
    symbols_data = {"Instance": common_instances}
    pvalues_data = {"Instance": common_instances}

    # For raw means, we want DAO first, then the competitors
    raw_means_data = {"Instance": common_instances, "DAO_Mean": []}
    for comp_name in competitor_files.keys():
        symbols_data[comp_name] = []
        pvalues_data[comp_name] = []
        raw_means_data[f"{comp_name}_Mean"] = []

    # 3. Process each instance
    for inst in common_instances:
        # Extract DAO data
        row_dao = df_dao[df_dao["instance_name"] == inst].iloc[0]
        dao_seed_cols = [c for c in df_dao.columns if c.startswith("seed_")]

        for comp_name, df_comp in dfs_comp.items():
            row_comp = df_comp[df_comp["instance_name"] == inst].iloc[0]
            comp_seed_cols = [c for c in df_comp.columns if c.startswith("seed_")]

            # Find common seeds between DAO and this specific competitor
            common_seeds = sorted(set(dao_seed_cols).intersection(set(comp_seed_cols)))

            vals_dao = row_dao[common_seeds].values.astype(float)
            vals_comp = row_comp[common_seeds].values.astype(float)

            # Drop NaNs if any exist in the pairs
            valid_mask = ~(np.isnan(vals_dao) | np.isnan(vals_comp))
            vals_dao = vals_dao[valid_mask]
            vals_comp = vals_comp[valid_mask]

            mean_dao = np.mean(vals_dao) if len(vals_dao) > 0 else np.nan
            mean_comp = np.mean(vals_comp) if len(vals_comp) > 0 else np.nan

            # Initialize Wilcoxon vars
            p_val = np.nan
            symbol = "="

            if len(vals_dao) > 0 and np.allclose(vals_dao, vals_comp):
                p_val = 1.0
                symbol = "="
            elif len(vals_dao) > 0:
                try:
                    # Two-sided Wilcoxon
                    stat, p_val = wilcoxon(vals_dao, vals_comp)

                    # Determine Significance Symbol (Smaller is better for Fitness/Time)
                    if p_val < alpha:
                        if mean_dao < mean_comp:
                            symbol = "+"  # DAO wins
                        else:
                            symbol = "-"  # DAO loses
                    else:
                        symbol = "="  # Tie
                except ValueError:
                    p_val = 1.0
                    symbol = "="

            # Append to lists
            symbols_data[comp_name].append(symbol)
            pvalues_data[comp_name].append(p_val)
            raw_means_data[f"{comp_name}_Mean"].append(mean_comp)

            # Only append DAO mean once per instance
            if comp_name == list(competitor_files.keys())[0]:
                raw_means_data["DAO_Mean"].append(mean_dao)

    # 4. Convert dictionaries to DataFrames
    df_symbols = pd.DataFrame(symbols_data)
    df_pvalues = pd.DataFrame(pvalues_data)
    df_raw = pd.DataFrame(raw_means_data)

    # Clean up formatting for P-values and Means
    df_pvalues = df_pvalues.round(5)
    df_raw = df_raw.round(4)

    # 5. Export to a single Excel file with multiple sheets
    with pd.ExcelWriter(output_excel) as writer:
        df_symbols.to_excel(writer, sheet_name="Symbols (+, =, -)", index=False)
        df_pvalues.to_excel(writer, sheet_name="P-Values", index=False)
        df_raw.to_excel(writer, sheet_name="Raw Means", index=False)

    print(f"Success! Results saved to '{output_excel}'")
    print("Sheets included: 'Symbols (+, =, -)', 'P-Values', 'Raw Means'")


if __name__ == "__main__":

    # --- FITNESS SETUP ---
    dao_fitness = "ao_seedwise_fitness_results.csv"
    competitors_fitness = {
        "Original_GA": "ga_seedwise_fitness_results.csv",
        "GA_TABU": "ga_tabu_seedwise_fitness_results.csv",
        "ABC": "abc_seedwise_fitness_results.csv",
        "GWO": "gwo_seedwise_fitness_results.csv"
    }

    # --- TIME SETUP ---
    dao_time = "ao_seedwise_time_results.csv"
    competitors_time = {
        "Original_GA": "ga_seedwise_time_results.csv",
        "GA_TABU": "ga_tabu_seedwise_time_results.csv",
        "ABC": "abc_seedwise_time_results.csv",
        "GWO": "gwo_seedwise_time_results.csv"
    }

    # --- RUN EXECUTIONS ---
    try:
        run_unified_comparison(
            dao_file=dao_fitness,
            competitor_files=competitors_fitness,
            output_excel="DAO_vs_All_Fitness_Wilcoxon.xlsx"
        )
    except Exception as e:
        print(f"Error processing fitness files: {e}")

    try:
        run_unified_comparison(
            dao_file=dao_time,
            competitor_files=competitors_time,
            output_excel="DAO_vs_All_Time_Wilcoxon.xlsx"
        )
    except Exception as e:
        print(f"Error processing time files: {e}")