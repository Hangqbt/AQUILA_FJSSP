import pandas as pd


def calculate_and_export_percentages(excel_file, metric_name, writer):
    print(f"\n% ==========================================")
    print(f"% Processing Percentage Differences for: {metric_name}")
    print(f"% ==========================================\n")

    try:
        # We read the Raw Means sheet directly from the Excel file we already made
        df_means = pd.read_excel(excel_file, sheet_name="Raw Means")
    except FileNotFoundError:
        print(f"Error: Could not find {excel_file}. Make sure it is in the same folder.")
        return

    # Find the competitor columns (everything except Instance and DAO_Mean)
    comp_cols = [c for c in df_means.columns if c not in ['Instance', 'DAO_Mean'] and '_Mean' in c]

    # Create a new DataFrame for the percentages
    df_pct = pd.DataFrame()
    df_pct['Instance'] = df_means['Instance']

    # Calculate Percentage Difference: ((Competitor - DAO) / DAO) * 100
    for col in comp_cols:
        # Strip "_Mean" from the column name for a cleaner title
        algo_name = col.replace('_Mean', '')
        col_name = f"{algo_name}_%_Diff"

        df_pct[col_name] = ((df_means[col] - df_means['DAO_Mean']) / df_means['DAO_Mean']) * 100

    # Save to the shared Excel Writer
    sheet_title = f"{metric_name} Pct Diff"
    df_pct.to_excel(writer, sheet_name=sheet_title, index=False)

    # ---------------------------------------------------------
    # GENERATE LATEX CODE
    # ---------------------------------------------------------
    caption = f"Percentage Difference Relative to DAO ({metric_name})"
    label_name = f"tab:pct_diff_{metric_name.replace(' ', '_').lower()}"

    # Format: First column left-aligned, the rest centered
    col_format = "l" + "c" * (len(df_pct.columns) - 1)

    # Escape underscores in column names for LaTeX math mode safety
    df_pct_latex = df_pct.copy()
    df_pct_latex.columns = [str(col).replace('_', '\\_').replace('%', '\\%') for col in df_pct_latex.columns]

    # Generate the LaTeX string with 2 decimal places and a '%' sign
    latex_code = df_pct_latex.style.hide(axis="index").format(precision=2).to_latex(
        column_format=col_format,
        position="H",
        position_float="centering",
        hrules=True,
        caption=caption,
        label=label_name
    )

    print(latex_code)


if __name__ == "__main__":
    # The two Excel files we generated previously
    fitness_file = "DAO_vs_All_Fitness_Wilcoxon.xlsx"
    time_file = "DAO_vs_All_Time_Wilcoxon.xlsx"

    output_excel = "DAO_Percentage_Differences.xlsx"

    # Use Pandas ExcelWriter to save both tables into one neat workbook
    with pd.ExcelWriter(output_excel) as writer:
        calculate_and_export_percentages(fitness_file, "Fitness", writer)
        calculate_and_export_percentages(time_file, "Time", writer)

    print(f"\n==========================================")
    print(f"Success! Excel file saved as: '{output_excel}'")
    print(f"==========================================")