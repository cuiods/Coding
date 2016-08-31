#-*- coding:utf-8 -*-
from scipy.stats import bernoulli
from scipy.stats._continuous_distns import norm
from scipy.stats._discrete_distns import binom, poisson

import matplotlib.pyplot as plt
import numpy as np


def law_of_large_numbers():
    x = np.arange(1, 1001, 1) 
    r = bernoulli.rvs(0.3, size=1000)
    y = []
    rsum =0.0
    for i in range(1000):
        if r[i]==1:
            rsum=rsum+1
        y.append(rsum/(i+1)-0.3)
    plt.plot(x, y, color='red')
    plt.show()
    
    x = np.arange(1, 1001, 1) 
    r1 = binom.rvs(10, 0.6, size=1000)
    r2 = poisson.rvs(mu=6, size=1000)
    r3 = norm.rvs(loc=6, size=1000)

    y = []
    rsum=0.0
    for i in range(1000):
        rsum=rsum+(r1[i]+r2[i]+r3[i])
        y.append(rsum/((i+1)*3)-6)

    plt.plot(x, y, color='red')
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    law_of_large_numbers()
