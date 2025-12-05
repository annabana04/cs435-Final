import pandas as pd 
import statsmodels.formula.api as sm
import itertools


df = pd.read_csv('outputViolence.csv') # change file path to desired score analysis
df['release_date'] = df['release_date'].astype(int)

unique_years = df['release_date'].unique()
year_pairs = list(itertools.combinations(unique_years, 2))

results = []

# Loop for performing the OLS model comparisons for Year vs. Year, and creating desired results table
for year1, year2 in year_pairs:
    data_year1 = df[df['release_date'] == year1]
    data_year2 = df[df['release_date'] == year2]
    combined_data = pd.concat([data_year1, data_year2])
    combined_data['year'] = combined_data['release_date'].astype(str)
    
    model = sm.ols('score ~ C(year)', data=combined_data).fit()

    year1_coef = model.params['C(year)[T.' + str(year1) + ']'] if f'C(year)[T.{year1}]' in model.params else 0
    year2_coef = model.params['C(year)[T.' + str(year2) + ']'] if f'C(year)[T.{year2}]' in model.params else 0
    results.append({
        'year1': year1,
        'year2': year2,
        'coef_year2': year2_coef,
        'std_err_year2': model.bse['C(year)[T.' + str(year2) + ']'] if f'C(year)[T.{year2}]' in model.bse else 0,
        't_stat_year2': model.tvalues['C(year)[T.' + str(year2) + ']'] if f'C(year)[T.{year2}]' in model.tvalues else 0,
        'p_value_year2': model.pvalues['C(year)[T.' + str(year2) + ']'] if f'C(year)[T.{year2}]' in model.pvalues else 1.0,
    })

# Create dataframe object for results table
results_df = pd.DataFrame(results)

# Output results of OLS Model into a .csv file
results_df.to_csv('OLSViolence.csv', index=False)


# Searches through OLS Model results for the statistically significant entries and prints those into a .csv file
ols_results = pd.read_csv('OLSViolence.csv')
ols_results['p_value_year2'] = pd.to_numeric(ols_results['p_value_year2'], errors='coerce')
filtered_results = ols_results[ols_results['p_value_year2'] <= 0.05]
print(filtered_results)
filtered_results.to_csv('statisticallySigViolence.csv', index=False)

