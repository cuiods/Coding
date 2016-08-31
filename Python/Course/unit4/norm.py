from scipy.stats import norm 
from scipy.stats import expon
from scipy import integrate
import numpy as np

f1 = lambda x: x**4
f2 = lambda x:x**2 - x + 2

rv = norm(loc = 1, scale=2);
print rv.mean();
print rv.var();
print rv.moment(1)
print rv.moment(2)

print norm.expect(f1, loc=1, scale=2)
print "============================"

E1 = lambda x: x*0.5*np.exp(-x/2)
E2 = lambda x: x**2*0.5*np.exp(-x/2)
print integrate.quad(E1,0,np.inf)
print integrate.quad(E2,0,np.inf)

print expon(scale=2).moment(1)
print expon(scale=2).moment(2)
