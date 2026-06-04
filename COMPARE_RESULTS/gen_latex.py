import pandas as pd


def generate_latex_tables(excel_file, metric_name):
    print(f"% ==========================================")
    print(f"% LaTeX Tables for: {excel_file}")
    print(f"% ==========================================\n")

    # Load the Excel file and all its sheets
    try:
        sheets = pd.read_excel(excel_file, sheet_name=None)
    except FileNotFoundError:
        print(f"Error: Could not find {excel_file}. Please ensure it is in the same directory.")
        return

    for sheet_name, df in sheets.items():
        print(f"% --- Code for Sheet: {sheet_name} ---")

        # Create clean labels and captions for the paper
        caption = f"Wilcoxon Signed-Rank Test Results: {sheet_name} ({metric_name})"

        # Clean up the sheet name to make a valid LaTeX label
        safe_label = sheet_name.replace(" ", "_").replace("+", "plus").replace("=", "eq").replace("-", "minus").replace(
            ",", "").replace("(", "").replace(")", "").lower()
        label_name = f"tab:{metric_name.lower()}_{safe_label}"

        # Setup column formatting (First column left-aligned, the rest centered: l c c c c)
        col_format = "l" + "c" * (len(df.columns) - 1)

        try:
            # Modern Pandas (v2.0+) formatting utilizing booktabs (toprule, midrule, bottomrule)
            latex_code = df.style.hide(axis="index").format(precision=4).to_latex(
                column_format=col_format,
                position="H",  # Uses the float package for strict positioning
                position_float="centering",
                hrules=True,  # Triggers booktabs style lines
                caption=caption,
                label=label_name
            )
        except AttributeError:
            # Fallback for older Pandas versions
            latex_code = df.to_latex(
                index=False,
                column_format=col_format,
                position="H",
                escape=True,
                caption=caption,
                label=label_name
            )

        print(latex_code)
        print("\n" + "=" * 50 + "\n")


if __name__ == "__main__":
    # Run this for the Fitness Excel doc first
    generate_latex_tables("OLD/DAO_vs_All_Fitness_Wilcoxon.xlsx", "Fitness")

    # You can easily uncomment the line below later to run your Time tables too!
    # generate_latex_tables("DAO_vs_All_Time_Wilcoxon.xlsx", "Computational Time")