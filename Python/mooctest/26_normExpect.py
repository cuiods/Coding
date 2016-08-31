from scipy.stats import norm
import numpy as np
from scipy import integrate
from scipy.stats import expon

def nc_of_norm():
    f1 = lambda x: x**4
    f2 = lambda x: x**2-x+1
    
    print norm.expect(f1,loc=1,scale=2)
    print norm.expect(f2,loc=1,scale=2)
    
    #1st non-center moment of expon distribution whose lambda is 0.5
    E1 = lambda x: x*0.5*np.exp(-x/2)
    #2nd non-center moment of expon distribution whose lambda is 0.5
    E2 = lambda x: x**2*0.5*np.exp(-x/2)
    print (integrate.quad(E1, 0, np.inf))
    print (integrate.quad(E2, 0, np.inf))

    print (expon(scale=2).moment(1))
    print (expon(scale=2).moment(2))
    
nc_of_norm()
