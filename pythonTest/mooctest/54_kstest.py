#-*- coding:utf-8 -*-
from scipy import stats

def kstest():
    n1=200
    n2=300
    a = stats.norm.rvs(size=n1, loc=0, scale=1)
    b = stats.norm.rvs(size=n2, loc=0.5, scale=1.5)
    c = stats.norm.rvs(size=n2, loc=0.01, scale=1)

    print (stats.ks_2samp(a, b))
    print (stats.ks_2samp(a, c))

#the code should not be changed
if __name__ == '__main__':
    kstest()