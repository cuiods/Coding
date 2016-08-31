#-*- coding:utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import binom as B

def binom_pmf():
    rv=B(100, 1.0/6)#10 independent trials, probability of success is 0.5
    x=np.arange(0, 101, 1)#return evenly spaced values within 1 interval between [0,11)
    y=rv.pmf(x)#probability mass function

    plt.bar(x, y, width=0.6,  color='grey')#make bar chart
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    binom_pmf()
