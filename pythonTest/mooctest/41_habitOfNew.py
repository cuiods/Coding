# New York is known as "the city that never sleeps". 
# A random sample of 25 New Yorkers were asked how much sleep they get per night.
#  Statistical summaries of these data are shown below. 
#  Do these data provide strong evidence that New Yorkers sleep 
#  less than 8 hours per night on average? Null-hypothesis is H0: u=8, and alpha is 0.05
#  n    mean    stand-variance    min    max
#  25    7.73    0.77    6.17    9.78
from scipy.stats import t
class Solution():
    def solve(self):
        df = 25-1
        statistic = (7.73-8)/(0.77/5)
        statistic = round(statistic,2)
        a = t.ppf(0.025,df)
        b = t.ppf(0.975,df)
        test = True
        if statistic<=a and statistic>=b:
            test = False
        return [df,statistic,test]
    
df = 25-1
statistic = (7.73-8)/(0.77/5)
statistic = round(statistic,2)
a = t.ppf(0.025,df)
b = t.ppf(0.975,df)
print a,b
test = True
if statistic<=a and statistic>=b:
    test = False
print [df,statistic,test]
