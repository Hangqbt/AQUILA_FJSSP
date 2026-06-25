import pandas as pd
import numpy as np
from scipy.stats import wilcoxon


def compare_per_instance(file_ga, file_ao, metric_name, alpha=0.05):
    """
    Per-instance Wilcoxon signed-rank comparison between GA and AO.

    Interpretation:
      For fitness:
        "+" : AO significantly smaller (better) than GA
        "-" : AO significantly larger (worse) than GA
        "=" : no significant difference

      For time:
        "+" : AO significantly faster (smaller time) than GA
        "-" : AO significantly slower (larger time) than GA
        "=" : no significant difference
    """
    # Load data
    df_ga = pd.read_csv(file_ga)
    df_ao = pd.read_csv(file_ao)

    # Ensure instance_name exists
    if "instance_name" not in df_ga.columns or "instance_name" not in df_ao.columns:
        raise ValueError(f"Both CSV files for {metric_name} must contain a column named 'instance_name'.")

    # Keep only common instances
    common_instances = sorted(set(df_ga["instance_name"]).intersection(set(df_ao["instance_name"])))
    if not common_instances:
        raise ValueError(f"No common instances found between the two CSV files for {metric_name}.")

    df_ga = df_ga[df_ga["instance_name"].isin(common_instances)].copy()
    df_ao = df_ao[df_ao["instance_name"].isin(common_instances)].copy()

    # Keep only common seed columns
    ga_seed_cols = [c for c in df_ga.columns if c.startswith("seed_")]
    ao_seed_cols = [c for c in df_ao.columns if c.startswith("seed_")]
    common_seed_cols = sorted(set(ga_seed_cols).intersection(set(ao_seed_cols)))

    if not common_seed_cols:
        raise ValueError(f"No common seed columns found between the two CSV files for {metric_name}.")

    # Reorder columns and sort rows by instance name
    df_ga = df_ga[["instance_name"] + common_seed_cols].sort_values("instance_name").reset_index(drop=True)
    df_ao = df_ao[["instance_name"] + common_seed_cols].sort_values("instance_name").reset_index(drop=True)

    results = []

    for _, row_ga in df_ga.iterrows():
        inst = row_ga["instance_name"]
        row_ao = df_ao[df_ao["instance_name"] == inst].iloc[0]

        ga_vals = pd.to_numeric(row_ga[common_seed_cols], errors="coerce").to_numpy(dtype=float)
        ao_vals = pd.to_numeric(row_ao[common_seed_cols], errors="coerce").to_numpy(dtype=float)

        # Remove NaN pairs if any
        valid_mask = ~(np.isnan(ga_vals) | np.isnan(ao_vals))
        ga_vals = ga_vals[valid_mask]
        ao_vals = ao_vals[valid_mask]

        if len(ga_vals) == 0:
            symbol = "="
            p_value = np.nan
            stat = np.nan
        elif np.allclose(ga_vals, ao_vals):
            symbol = "="
            p_value = 1.0
            stat = 0.0
        else:
            try:
                # AO < GA
                stat_less, p_less = wilcoxon(ao_vals, ga_vals, alternative="less", zero_method="wilcox")
                # AO > GA
                stat_greater, p_greater = wilcoxon(ao_vals, ga_vals, alternative="greater", zero_method="wilcox")

                if p_less < alpha:
                    symbol = "+"
                    p_value = p_less
                    stat = stat_less
                elif p_greater < alpha:
                    symbol = "-"
                    p_value = p_greater
                    stat = stat_greater
                else:
                    symbol = "="
                    p_value = min(p_less, p_greater)
                    stat = stat_less if p_less <= p_greater else stat_greater

            except ValueError:
                symbol = "="
                p_value = 1.0
                stat = 0.0

        results.append({
            "instance_name": inst,
            "GA_mean": np.mean(ga_vals) if len(ga_vals) > 0 else np.nan,
            "AO_mean": np.mean(ao_vals) if len(ao_vals) > 0 else np.nan,
            "wilcoxon_symbol": symbol,
            "p_value": p_value,
            "statistic": stat,
            "num_seeds": len(ga_vals)
        })

    df_results = pd.DataFrame(results)

    # Optional rounding for cleaner tables
    df_results["GA_mean"] = df_results["GA_mean"].round(4)
    df_results["AO_mean"] = df_results["AO_mean"].round(4)
    df_results["p_value"] = df_results["p_value"].round(8)
    df_results["statistic"] = df_results["statistic"].round(4)

    return df_results


if __name__ == "__main__":
    alpha = 0.05

    # =========================
    # File paths
    # =========================
    ga_fitness_file = "ga_seedwise_fitness_results.csv"
    ao_fitness_file = "ao_seedwise_fitness_results.csv"

    ga_time_file = "ga_seedwise_time_results.csv"
    ao_time_file = "ao_seedwise_time_results.csv"

    # =========================
    # Fitness comparison
    # "+" means AO significantly better (smaller fitness)
    # "-" means AO significantly worse (larger fitness)
    # =========================
    df_fitness_results = compare_per_instance(
        file_ga=ga_fitness_file,
        file_ao=ao_fitness_file,
        metric_name="fitness",
        alpha=alpha
    )

    fitness_outfile = "ga_vs_ao_wilcoxon_fitness_per_instance.csv"
    df_fitness_results.to_csv(fitness_outfile, index=False)

    print("\n================ FITNESS COMPARISON ================\n")
    print(df_fitness_results)
    print(f"\nSaved fitness comparison to: {fitness_outfile}")

    # =========================
    # Time comparison
    # "+" means AO significantly faster (smaller time)
    # "-" means AO significantly slower (larger time)
    # =========================
    df_time_results = compare_per_instance(
        file_ga=ga_time_file,
        file_ao=ao_time_file,
        metric_name="computational_time",
        alpha=alpha
    )

    time_outfile = "ga_vs_ao_wilcoxon_time_per_instance.csv"
    df_time_results.to_csv(time_outfile, index=False)

    print("\n================ COMPUTATIONAL TIME COMPARISON ================\n")
    print(df_time_results)
    print(f"\nSaved computational time comparison to: {time_outfile}")