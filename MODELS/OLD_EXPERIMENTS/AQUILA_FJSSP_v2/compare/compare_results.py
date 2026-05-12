import pandas as pd
import numpy as np
import os
import glob


def clean_value(val):
    """Extracts the float mean from strings like '42.70 ± 1.64'."""
    if pd.isna(val):
        return np.nan
    val_str = str(val)
    if '±' in val_str:
        return float(val_str.split('±')[0].strip())
    return float(val_str)


def generate_master_excel_report(baseline_csv, ao_csv_list, output_excel="Algorithm_Evolution_Report.xlsx"):
    print(f"Loading Baseline: {baseline_csv}")

    # Load and clean baseline
    try:
        df_base = pd.read_csv(baseline_csv)
    except FileNotFoundError:
        print(f"ERROR: Baseline file '{baseline_csv}' not found.")
        return

    if 'Instance' in df_base.columns:
        df_base.rename(columns={'Instance': 'instance_name'}, inplace=True)

    df_base['Obj_Base'] = df_base['objective_value'].apply(clean_value)
    df_base['Time_Base'] = df_base['computational_time'].apply(clean_value)

    summary_data = []

    # We will store the individual sheet dataframes in a dictionary to write them AFTER the summary
    sheets_dict = {}

    for ao_file in ao_csv_list:
        if not os.path.exists(ao_file):
            print(f"Skipping missing file: {ao_file}")
            continue

        print(f"Processing: {ao_file}")
        df_ao = pd.read_csv(ao_file)

        if 'Instance' in df_ao.columns:
            df_ao.rename(columns={'Instance': 'instance_name'}, inplace=True)

        # Merge datasets (Safely dropping the duplicate lower_bound column from AO to prevent _x / _y collisions)
        df = pd.merge(df_base[['instance_name', 'Obj_Base', 'Time_Base', 'optimal(lower_bound)']],
                      df_ao.drop(columns=['optimal(lower_bound)'], errors='ignore'),
                      on='instance_name', how='inner')

        df['Obj_AO'] = df['objective_value'].apply(clean_value)
        df['Time_AO'] = df['computational_time'].apply(clean_value)

        # Calculate Metrics
        df['Cycle_Time_Diff'] = df['Obj_AO'] - df['Obj_Base']
        df['Improvement_%'] = ((df['Obj_Base'] - df['Obj_AO']) / df['Obj_Base']) * 100
        df['Time_Ratio(AO/GA)'] = df['Time_AO'] / df['Time_Base']

        # Format output dataframe for this specific AO version
        out_df = df[['instance_name', 'optimal(lower_bound)', 'Obj_Base', 'Obj_AO', 'Cycle_Time_Diff',
                     'Improvement_%', 'Time_Base', 'Time_AO', 'Time_Ratio(AO/GA)']]

        # Excel sheet names can only be 31 characters max
        sheet_name = os.path.basename(ao_file).replace('.csv', '')[:31]
        sheets_dict[sheet_name] = out_df

        # Calculate Summary Stats for the Master Sheet
        ao_wins = len(df[df['Cycle_Time_Diff'] < 0])
        ties = len(df[df['Cycle_Time_Diff'] == 0])
        ga_wins = len(df[df['Cycle_Time_Diff'] > 0])
        avg_imp = df['Improvement_%'].mean()
        avg_time = df['Time_Ratio(AO/GA)'].mean()

        summary_data.append({
            'Algorithm_Version': os.path.basename(ao_file),
            'AO_Wins': ao_wins,
            'GA_Wins': ga_wins,
            'Ties': ties,
            'Avg_Improvement_%': avg_imp,
            'Avg_Time_Multiplier': avg_time
        })

    # Create the Excel Writer
    print(f"\nWriting to Excel: {output_excel}...")
    with pd.ExcelWriter(output_excel, engine='openpyxl') as writer:

        # 1. Write the Master Summary Sheet FIRST so it's the first tab you see
        summary_df = pd.DataFrame(summary_data)
        summary_df.to_excel(writer, sheet_name="00_MASTER_SUMMARY", index=False)

        # 2. Write all the individual comparison sheets
        for sheet_name, dataframe in sheets_dict.items():
            dataframe.to_excel(writer, sheet_name=sheet_name, index=False)

    print("Done! Your master report is ready.")


# ==========================================
# HOW TO USE:
# 1. Set your baseline GA file.
# 2. Add all your AO run CSVs to the list. Or use glob to grab all of them automatically!
# ==========================================
if __name__ == "__main__":
    BASELINE = "ga_benchmark_results_parallel.csv"

    # You can manually list them:
    #AO_FILES = [
    #    "ao_results_author_brandimarte_20260223_165811.csv",
    #    "ao_results_author_brandimarte_20260224_180757.csv",
    #    "ao_results_author_brandimarte_20260225_162614.csv",
    #    "ao_results_author_brandimarte_20260225_174157.csv"
    #]

    # OR, if you want it to grab EVERY csv in the folder that starts with 'ao_results':
    AO_FILES = glob.glob("ao_results_*.csv")
    #AO_FILES = ["ao_results_author_brandimarte_20260302_165654.csv"]

    generate_master_excel_report(BASELINE, AO_FILES, output_excel="AO_Evolution_vs_GA.xlsx")