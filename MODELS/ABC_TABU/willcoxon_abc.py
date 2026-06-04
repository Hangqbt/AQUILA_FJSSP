import pandas as pd
import numpy as np
from scipy.stats import wilcoxon


def compare_pairwise(df_base, df_comp, base_name, comp_name, metric_name, alpha=0.05):
    """
    Performs a paired Wilcoxon signed-rank test between a base algorithm and a competitor.
    "+" : Base is significantly better (smaller value)
    "-" : Base is significantly worse (larger value)
    "=" : No significant difference
    """
    # Keep only common instances
    common_instances = sorted(set(df_base["instance_name"]).intersection(set(df_comp["instance_name"])))

    df_base = df_base[df_base["instance_name"].isin(common_instances)].copy().sort_values("instance_name").reset_index(
        drop=True)
    df_comp = df_comp[df_comp["instance_name"].isin(common_instances)].copy().sort_values("instance_name").reset_index(
        drop=True)

    # Identify common seed columns
    seed_cols_base = [c for c in df_base.columns if c.startswith("seed_")]
    seed_cols_comp = [c for c in df_comp.columns if c.startswith("seed_")]
    common_seeds = sorted(set(seed_cols_base).intersection(set(seed_cols_comp)))

    results = []

    for idx, row_base in df_base.iterrows():
        inst = row_base["instance_name"]
        row_comp = df_comp[df_comp["instance_name"] == inst].iloc[0]

        vals_base = row_base[common_seeds].values.astype(float)
        vals_comp = row_comp[common_seeds].values.astype(float)

        mean_base = np.mean(vals_base)
        mean_comp = np.mean(vals_comp)

        # Handle identical vectors
        if np.allclose(vals_base, vals_comp):
            p_val = 1.0
            stat = np.nan
        else:
            try:
                stat, p_val = wilcoxon(vals_base, vals_comp)
            except ValueError:
                p_val = 1.0
                stat = np.nan

        # Determine Significance Symbol
        if pd.isna(p_val) or p_val >= alpha:
            symbol = '='
        else:
            if mean_base < mean_comp:
                symbol = '+'  # Base is better (smaller)
            else:
                symbol = '-'  # Base is worse (larger)

        results.append({
            "Instance": inst,
            f"{base_name}_Mean": mean_base,
            f"{comp_name}_Mean": mean_comp,
            f"p-value ({comp_name})": p_val,
            f"vs_{comp_name}": symbol
        })

    return pd.DataFrame(results)


def run_triple_comparison(file_abc, file_ga, file_dao, metric_name):
    print(f"\n================ PROCESSING {metric_name.upper()} ================")

    # Load data
    df_abc = pd.read_csv(file_abc)
    df_ga = pd.read_csv(file_ga)
    df_dao = pd.read_csv(file_dao)

    # 1. ABC vs GA
    res_ga = compare_pairwise(df_abc, df_ga, "ABC", "GA", metric_name)

    # 2. ABC vs DAO
    res_dao = compare_pairwise(df_abc, df_dao, "ABC", "DAO", metric_name)

    # Combine into a single presentation table
    # We take the ABC Mean from the first result, and append the comparisons
    combined = res_ga[["Instance", "ABC_Mean", "GA_Mean", "p-value (GA)", "vs_GA"]].copy()

    # Merge DAO results
    combined["DAO_Mean"] = res_dao["DAO_Mean"]
    combined["p-value (DAO)"] = res_dao["p-value (DAO)"]
    combined["vs_DAO"] = res_dao["vs_DAO"]

    # Reorder columns for a clean paper-ready table
    cols = [
        "Instance",
        "ABC_Mean",
        "GA_Mean", "p-value (GA)", "vs_GA",
        "DAO_Mean", "p-value (DAO)", "vs_DAO"
    ]
    combined = combined[cols]

    return combined


if __name__ == "__main__":
    # File Paths (Ensure these match your exact file names in the folder)
    abc_fit_file = "abc_seedwise_fitness_results.csv"
    ga_fit_file = "ga_tabu_seedwise_fitness_results.csv"
    dao_fit_file = "ao_seedwise_fitness_results.csv"  # DAO is labeled as AO in your files

    abc_time_file = "abc_seedwise_time_results.csv"
    ga_time_file = "ga_tabu_seedwise_time_results.csv"
    dao_time_file = "ao_seedwise_time_results.csv"

    # --- FITNESS COMPARISON ---
    try:
        df_fitness = run_triple_comparison(abc_fit_file, ga_fit_file, dao_fit_file, "Fitness")
        print("\n--- FITNESS RESULTS (Baseline: ABC) ---")
        print("Interpretation: '+' = ABC is better (smaller makespan), '-' = ABC is worse, '=' = Tie")
        print(df_fitness.to_string(index=False))
        df_fitness.to_csv("wilcoxon_fitness_abc_vs_all.csv", index=False)
    except Exception as e:
        print(f"Error processing fitness: {e}")

    # --- TIME COMPARISON ---
    try:
        df_time = run_triple_comparison(abc_time_file, ga_time_file, dao_time_file, "Computational Time")
        print("\n--- TIME RESULTS (Baseline: ABC) ---")
        print("Interpretation: '+' = ABC is faster (less time), '-' = ABC is slower, '=' = Tie")
        print(df_time.to_string(index=False))
        df_time.to_csv("wilcoxon_time_abc_vs_all.csv", index=False)
    except Exception as e:
        print(f"Error processing time: {e}")