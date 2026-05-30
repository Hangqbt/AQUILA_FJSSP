import pandas as pd


def generate_latex_tables(excel_file, metric_name):
    print(f"% ==========================================")
    print(f"% LaTeX Tables for: {excel_file}")
    print(f"% ==========================================\n")

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
        label_name = f"tab:{metric_name.replace(' ', '_').lower()}_{safe_label}"

        # Setup column formatting (First column left-aligned, the rest centered: l c c c c)
        col_format = "l" + "c" * (len(df.columns) - 1)

        # CRITICAL FIX: Escape underscores in column names to prevent LaTeX math-mode errors
        df.columns = [str(col).replace('_', '\\_') for col in df.columns]

        try:
            # Modern Pandas formatting utilizing booktabs and hiding the index
            latex_code = df.style.hide(axis="index").format(precision=4).to_latex(
                column_format=col_format,
                position="H",
                position_float="centering",
                hrules=True,
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
    # We are running this specifically for your Time document!
    generate_latex_tables("DAO_vs_All_Time_Wilcoxon.xlsx", "Computational Time")