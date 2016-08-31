#-*- coding:utf-8 -*-
from scipy.stats import binom
from scipy.stats import uniform
from scipy.stats._continuous_distns import expon
from scipy.stats._discrete_distns import poisson


def nc_of_binom():
    rv = binom(10,0.2)
    print (rv.mean())
    print (rv.var())
    print (rv.moment(1))
    print (rv.moment(2))
    print (rv.moment(3))
    print (rv.moment(4))
    print (rv.stats(moments='mvsk'))
    
    rv = poisson(mu=5)
    print (rv.mean())
    print (rv.var())
    print (rv.moment(1))
    print (rv.moment(2))
    print (rv.moment(3))
    print (rv.moment(4))
    print (rv.stats(moments='mvsk'))
    
    rv = uniform(loc=2, scale=6)
    print (rv.stats(moments='mvsk'))
    
    rv = expon(scale=2)
    print (rv.stats(moments='mvsk'))

#the code should not be changed
if __name__ == '__main__':
    nc_of_binom()
