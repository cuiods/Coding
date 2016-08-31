'''
A large farm wants to try out a new type of fertilizer to evaluate whether it will improve 
the farm's corn production. The land is broken into plots that produce an average of 1215 
pounds of corn with a standard deviation of 94 pounds per plot. The owner is interested 
in detecting any average difference of at least 40 pounds per plot. How many plots of 
land would be needed for the experiment if the desired power level is 90%? Assume each plot 
of land gets treated with either the current fertilizer or the new fertilizer.

none

plot_num, int type

none

20

'''
from scipy.stats import norm
class Solution():
    def solve(self):
        n = (norm.ppf(0.95)*94/40)**2
        return int(n)+1
        
n = (norm.ppf(0.95)*94/40)**2
print int(n)+1
