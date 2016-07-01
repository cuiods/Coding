import numpy as np
from scipy.stats import binom as B
from scipy.stats import geom as G
from scipy.stats import poisson as P
from scipy.stats import norm as N
import matplotlib.pyplot as plt

# rv = B(100,1.0/6)
# x = np.arange(0,101,1)
# y = rv.pmf(x)
# 
# print y
# plt.bar(x,y,width=0.6,color='grey')
# plt.show()

# rv = G(0.2)
# x = np.arange(0,11,1)
# y = rv.pmf(x)
# 
# print y
# plt.bar(x,y,width=0.6,color='grey')
# plt.show()

# rv = P(4.5)
# x = np.arange(0,11,1)
# y = rv.pmf(x)
# 
# print y
# plt.bar(x,y,width=0.6,color='grey')
# plt.show()

x = np.linspace(-10, 10, 100)
rv1 = N(loc=0, scale=1)
rv2 = N(loc=-5, scale=1)
rv3 = N(loc=0, scale=3)

plt.plot(x,rv1.pdf(x),color='green')
plt.plot(x,rv2.pdf(x),color='blue')
plt.plot(x,rv3.pdf(x),color='red')
plt.show()

