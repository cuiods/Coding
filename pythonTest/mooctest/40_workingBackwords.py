# A 95% confidence interval for a population mean, u, is given as (18.985, 21.015). 
# This confidence interval is based on a simple random samples of 36 observations. 
# Calculate the sample mean and standard deviation. Assume that all conditions 
# necessary for inference are satisfied. Use the t-distribution in any calculations.
from scipy.stats import t
import numpy as np
class Solution():
    def solve(self):
        x = round((18.985+21.015)/2,2)
        n = 36
        df = n-1
        s = -(21.015-18.985)/2 * np.sqrt(n) / t.ppf(0.025, df)
        s = round(s,2)
        return [x,s]
        
x = round((18.985+21.015)/2,2)
n = 36
df = n-1
s = -(21.015-18.985)/2 * np.sqrt(n) / t.ppf(0.025, df)
s = round(s,2)
print [x,s]