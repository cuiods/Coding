#-*- coding:utf-8 -*-

from scipy.stats import expon
from scipy.stats._discrete_distns import binom

import matplotlib.pyplot as plt
import numpy as np


def central_limit_theorem():
    y = []
    n=100
    for i in range(1000):
        r = expon.rvs(scale=1, size=n)
        rsum=np.sum(r)
        z=(rsum-n)/np.sqrt(n)
        y.append(z)
    
    plt.hist(y,color='grey')
    plt.show()
    
#     y = []
#     n=100
#     for i in range(1000):
#         r = binom.rvs(n, 0.3)
#         rsum=np.sum(r)
#         z=(rsum-n*0.3)/np.sqrt(n*0.3*0.7)
#         y.append(z)
#     
#     plt.hist(y,color='grey')
#     plt.show()

#the code should not be changed
if __name__ == '__main__':
    central_limit_theorem()
