#-*- coding:utf-8 -*-
'''
Rock-paper-scissors is a hand game played by two or more people where players 
choose to sign either rock, paper or scissors with their hands. For your statistics 
class project, you want to evaluate whether players choose between these three options 
randomly, or if certain options are favoured above others. You ask two friends to play 
rock-paper-scissors and count the time each option is played. The following table summaries 
the data:
Rock    Paper    Scissors
43    21    35

Use these data to evaluate whether players choose between these three options randomly, 
or if certain options are favored above others. alpha is 0.05.

none 

[degree-of-freedom-of-distribution, statistical values, conclusion],
'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second
 decimal place, 'conclusion' is True, which means the H0 is accepted, or False


none

[2, 1.63, True]
'''
from scipy.stats import chi2
class Solution():
    def solve(self):
        chi2StaValue = 43**2/(99*1.0/3)+21**2/(99*1.0/3)+35**2/(99*1.0/3) - 99
        chi2StaValue = round(chi2StaValue,2)
        df = 2
        a = chi2.ppf(0.95,df)
        test = True
        if chi2StaValue > a:
            test =False
        return [df,chi2StaValue,test]

a = Solution()
print a.solve()