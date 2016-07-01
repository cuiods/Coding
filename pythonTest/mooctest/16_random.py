#-*- coding:utf-8 -*-
import math
import random
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import expon as E
from scipy.stats import norm as N
import numpy as np

def exp(lam):
    p=random.random()#return float in [0,1)
    return -math.log(1-p)/lam#calculate expon value

def mnorm(loc, scale):
    p = random.random()
    return 

#the code should not be changed
if __name__ == '__main__':
    #generate 100 random variables calculated by self-defined exp function
    x1=[]
    for i in range(100):
        x1.append(exp(1))
    x1=sorted(x1)
    y1=np.linspace(0, 1, 100)
    plt.plot(x1,y1,color='blue')

    #generate 100 random varaibles calculated by inner-defined expon function
    rv=E(scale=1)
    x2=np.linspace(0, 5, 100)
    plt.plot(x2, rv.cdf(x2), color='red')
    plt.show()#make chart
