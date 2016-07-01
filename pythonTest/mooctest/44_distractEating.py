# A group of researchers are interested in the possible effects of distracting 
# stimuli during eating, such as an increase or decrease in the amount of food 
# consumption. To test this hypothesis, they monitored food intake for a group 
# of 44 patients who were randomised into two equal groups. The treatment group
#  ate lunch while playing solitaire, and the control group ate lunch without any 
#  added distractions. Patients in the treatment group ate 52.1 grams of biscuits, 
#  with a standard deviation of 45.1 grams, and patients in the control group ate 
#  27.1 grams of biscuits with a standard deviation of 26.4 grams. Do these data 
#  provide convincing evidence that the average food intake is different for the 
#  patients in the treatment group? Assume the conditions for inference are satisfied.
# Null hypothesis is H0: u_t - u_c = 0, alpha is 0.05
from scipy.stats import norm
class Solution():
    def solve(self):
        df = (44-2)/2
        statistic = (52.1-27.1)/((45.1**2/22+26.4**2/22)**0.5)
        statistic = round(statistic,2)
        a = norm.ppf(0.025)
        b = norm.ppf(0.975)
        test = False
        if statistic>=a and statistic<=b:
            test = True
        return [df,statistic,test]
    
df = (44-2)/2
statistic = (52.1-27.1)/((45.1**2/22+26.4**2/22)**0.5)
statistic = round(statistic,2)
a = norm.ppf(0.025)
b = norm.ppf(0.975)
test = False
if statistic>=a and statistic<=b:
    test = True
print [df,statistic,test,a,b]