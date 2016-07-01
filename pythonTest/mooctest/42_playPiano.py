# Georgianna claims that in a small city renowned for its music school, 
# the average child takes at least 5 years of piano lessons. We have a 
# random sample of 20 children from the city with a mean of 4.6 years of 
# piano lessons and a standard deviation of 2.2 years. Evaluate Georgianna's 
# claims using a hypothesis test. alpha is 0.05. 
from scipy.stats import t
class Solution():
    def solve(self):
        df = 20-1
        statistic = (4.6-5)/(2.2/20**0.5)
        statistic = round(statistic,2)
        a = t.ppf(0.025, df)
        b = t.ppf(0.975, df)
        test = False
        if statistic>=a and statistic<=b:
            test = True
        return [df,statistic,test]