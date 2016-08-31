#-*- coding:utf-8 -*-
import numpy as np
from scipy.stats import norm
from scipy.stats import chi2
from scipy.stats import t
from scipy.stats import f
import matplotlib.pyplot as plt

def chi2_distribution():
    fig, ax = plt.subplots(1, 1)
    #display the probability density function
    df = 10
    x=np.linspace(chi2.ppf(0.01, df), chi2.ppf(0.99, df), 100)
    ax.plot(x, chi2.pdf(x,df))
    
    #simulate the chi2 distribution
    y = []
    n=10
    for i in range(1000):
        chi2r=0.0
        r = norm.rvs(size=n)
        for j in range(n):
            chi2r=chi2r+r[j]**2
        y.append(chi2r)

    ax.hist(y, normed=True, alpha=0.2) 
    plt.show()
    
    fig, ax = plt.subplots(1, 1)
    #display the probability density function
    df = 10
    x=np.linspace(-4, 4, 100)
    ax.plot(x, t.pdf(x,df))
    
    #simulate the t-distribution
    y = []
    for i in range(1000):
        rx = norm.rvs()
        ry = chi2.rvs(df)
        rt = rx/np.sqrt(ry/df)
        y.append(rt)

    ax.hist(y, normed=True, alpha=0.2)
    plt.show()
    
    fig, ax = plt.subplots(1, 1)
    #display the probability density function
    dfn, dfm = 10, 5
    x = np.linspace(f.ppf(0.01, dfn, dfm), f.ppf(0.99, dfn, dfm), 100)
    ax.plot(x, f.pdf(x, dfn, dfm))
    
    #simulate the F-distribution
    y = []
    for i in range(1000):
        rx = chi2.rvs(dfn)
        ry = chi2.rvs(dfm)
        rf = np.sqrt(rx/dfn)/np.sqrt(ry/dfm)
        y.append(rf)

    ax.hist(y, normed=True, alpha=0.2)
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    chi2_distribution()
