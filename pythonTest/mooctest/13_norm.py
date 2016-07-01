#-*- coding:utf-8 -*-
import sys
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm as N

def norm_pdf():
    x = np.linspace(-10, 10, 100)#return evenly spaced samples, calculated over the interval [-10,10]
    rv1=N(loc=0, scale = 1)#the mean is 0, the standard deviation is 1
    rv2=N(loc=-5, scale = 1)#the mean is -5, the standard deviation is 1
    rv3=N(loc=0, scale = 3)#the mean is 0, the standard deviation is 3

    plt.plot(x, rv1.pdf(x), color='green')#make chart
    plt.plot(x, rv2.pdf(x), color='blue')#make chart
    plt.plot(x, rv3.pdf(x), color='red')#make chart
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    norm_pdf()
