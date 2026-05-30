import pandas as pd
import numpy as np
from scipy.stats import wilcoxon


def compare_per_instance(file_gwo, file_ao, metric_name, alpha=0.05):
    """
    Per-instance Wilcoxon signed-rank comparison between GWO and AO.

    Interpretation (GWO Perspective):
      For fitness:
        "+" : GWO significantly smaller (better) than AO
        "-" : GWO significantly larger (worse) than AO
        "=" : no significant difference

      For time:
        "+" : GWO significantly faster (smaller time) than AO
        "-" : GWO significantly slower (larger time) than AO
        "=" : no significant difference
    """
    # Load data
    df_gwo = pd.read_csv(file_gwo)
    df_ao = pd.read_csv(file_ao)

    # Ensure instance_name exists
    if "instance_name" not in df_gwo.columns or "instance_name" not in df_ao.columns:
        raise ValueError(f"Both CSV files for {metric_name} must contain a column named 'instance_name'.")

    # Keep only common instances
    common_instances = sorted(set(df_gwo["instance_name"]).intersection(set(df_ao["instance_name"])))
    if not common_instances:
        raise ValueError(f"No common instances found between the two CSV files for {metric_name}.")

    df_gwo = df_gwo[df_gwo["instance_name"].isin(common_instances)].copy()
    df_ao = df_ao[df_ao["instance_name"].isin(common_instances)].copy()

    # Sort to ensure matching order
    df_gwo.sort_values("instance_name", inplace=True)
    df_ao.sort_values("instance_name", inplace=True)

    df_gwo.reset_index(drop=True, inplace=True)
    df_ao.reset_index(drop=True, inplace=True)

    # Identify seed columns
    seed_cols_gwo = [c for c in df_gwo.columns if c.startswith("seed_")]
    seed_cols_ao = [c for c in df_ao.columns if c.startswith("seed_")]

    common_seeds = sorted(set(seed_cols_gwo).intersection(set(seed_cols_ao)))
    if not common_seeds:
        raise ValueError("No common seed columns found.")

    results = []

    for idx, row_gwo in df_gwo.iterrows():
        inst = row_gwo["instance_name"]
        row_ao = df_ao[df_ao["instance_name"] == inst].iloc[0]

        # Extract pairs
        vals_gwo = row_gwo[common_seeds].values.astype(float)
        vals_ao = row_ao[common_seeds].values.astype(float)

        mean_gwo = np.mean(vals_gwo)
        mean_ao = np.mean(vals_ao)

        # Handle identical vectors
        if np.allclose(vals_gwo, vals_ao):
            # No difference at all
            p_val = 1.0
            stat = np.nan
        else:
            try:
                # Default Wilcoxon two-sided
                stat, p_val = wilcoxon(vals_gwo, vals_ao)
            except ValueError:
                # Occurs if all non-zero differences are tied, etc.
                p_val = 1.0
                stat = np.nan

        results.append({
            "instance_name": inst,
            "GWO_mean": mean_gwo,
            "AO_mean": mean_ao,
            "p_value": p_val,
            "statistic": stat,
            "num_seeds": len(common_seeds)
        })

    df_results = pd.DataFrame(results)

    # Apply significance symbol logic (GWO perspective)
    wilcoxon_symbol = []
    for i in range(len(df_results)):
        p_val = df_results['p_value'][i]

        if pd.isna(p_val):
            wilcoxon_symbol.append('=')
            continue

        if p_val < alpha:
            # Significant difference exists
            # Since smaller fitness/time is better, check if GWO is smaller than AO
            if df_results['GWO_mean'][i] < df_results['AO_mean'][i]:
                wilcoxon_symbol.append('+')  # GWO wins
            else:
                wilcoxon_symbol.append('-')  # GWO loses
        else:
            # No significant difference
            wilcoxon_symbol.append('=')  # Tie

    df_results.insert(3, 'wilcoxon_symbol', wilcoxon_symbol)
    return df_results


if __name__ == "__main__":
    # Standard significance level
    alpha = 0.05

    # File paths (Aligned with your GWO and AO output files)
    gwo_fitness_file = "gwo_seedwise_fitness_results.csv"
    ao_fitness_file = "ao_seedwise_fitness_results.csv"

    gwo_time_file = "gwo_seedwise_time_results.csv"
    ao_time_file = "ao_seedwise_time_results.csv"

    # =========================
    # Fitness comparison
    # "+" means GWO significantly better (smaller fitness)
    # "-" means GWO significantly worse (larger fitness)
    # =========================
    df_fitness_results = compare_per_instance(
        file_gwo=gwo_fitness_file,
        file_ao=ao_fitness_file,
        metric_name="fitness",
        alpha=alpha
    )

    fitness_outfile = "gwo_wilcoxon_fitness_per_instance.csv"
    df_fitness_results.to_csv(fitness_outfile, index=False)

    print("\n================ FITNESS COMPARISON (GWO PERSPECTIVE) ================\n")
    print(df_fitness_results)
    print(f"\nSaved fitness comparison to: {fitness_outfile}")

    # =========================
    # Time comparison
    # "+" means GWO significantly faster (smaller time)
    # "-" means GWO significantly slower (larger time)
    # =========================
    df_time_results = compare_per_instance(
        file_gwo=gwo_time_file,
        file_ao=ao_time_file,
        metric_name="computational_time",
        alpha=alpha
    )

    time_outfile = "gwo_wilcoxon_time_per_instance.csv"
    df_time_results.to_csv(time_outfile, index=False)

    print("\n================ TIME COMPARISON (GWO PERSPECTIVE) ================\n")
    print(df_time_results)
    print(f"\nSaved time comparison to: {time_outfile}")