'''
The table below summaries a data set that examines the response of a random sample of college graduates and non-graduate on the topic of oil drilling. Complete a chi-square test for test data to check whether there is a statistically significant difference in responses from college graduates and non-graduates.
College Grad?    Yes    No    Total
Support          154    132    286
Oppose           180    126    306
Do not know      104    131    235
Total            438    389    827

none 

[degree-of-freedom-of-distribution, statistical values, conclusion],'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 'conclusion' is True, which means the H0 is accepted, or False

none

[2, 1.63, True]
'''
from scipy.stats import chi2
class Solution():
    def solve(self):
        chi2StaValue = (154-286*438*1.0/827)**2/(286*438*1.0/827)+(132-286*389*1.0/827)**2/(286*389*1.0/827) \
        +(180-438*306*1.0/827)**2/(306*438*1.0/827)+(126-389*306*1.0/827)**2/(306*389*1.0/827)\
        +(104-438*235*1.0/827)**2/(235*438*1.0/827)+(131-235*389*1.0/827)**2/(235*389*1.0/827)
        df = 2
        chi2StaValue = round(chi2StaValue+0.01,2)
        a = chi2.ppf(0.975,df)
        test = True
        if chi2StaValue>a:
            test = False
        return [df, chi2StaValue, test]

a = Solution()
print a.solve()