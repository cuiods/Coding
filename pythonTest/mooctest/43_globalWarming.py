# Is there strong evidence of global warming? 
# Let's consider a small scale example, comparing how temperatures have 
# changed is the US from 1968 to 2008. The daily high temperature reading 
# on January 1 was collected in 1968 and 2008 for 51 randomly selected locations 
# in the continental US. Then the difference between the two readings 
# (temperature in 2008 - temperature in 1968) was calculated for each of the 
# 51 values was 1.1 degree with a standard deviation of 4.9 degrees. We are 
# interested in determining whether these data provide strong evidence of 
# temperature warming in the continental US.
# (1) Is there a relationship between the observations collected in 1968 and 
# 2008? Or are the observations in the two groups independent? Explain
# (2) What's the hypothesis for this research?
# (3) Check the conditions required to complete this test.
# (4) Calculate the freedom, test statistical value and give the conclusion. 
# alpha is 0.05, coding this part
# (5) What type of error might we have made?
# (6) Based on the results of this hypothesis test, would you expect a confidence 
# interval for the average difference between the temperature measurements from 
# 1968 and 2008 to include 0? Explain
from scipy.stats import t
class Solution():
    def solve(self):
        df = 51-1
        statistic = 1.1/(4.9/51**0.5)
        statistic = round(statistic,2)
        a = t.ppf(0.025, df)
        b = t.ppf(0.975, df)
        test = False
        if statistic>=a and statistic<=b:
            test = True
        return [df,statistic,test]
    
