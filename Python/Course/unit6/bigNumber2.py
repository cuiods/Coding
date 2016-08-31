import numpy as np
from scipy.stats import binom
import matplotlib.pyplot as plt
from scipy.stats import poisson
from scipy.stats import norm

x = np.arange(1,1001,1)
r1 = binom.rvs(10,0.6,size=1000)
r2 = poisson.rvs(mu=6,size=1000)
r3 = norm.rvs(loc=6, size=1000)

y = []
rsum=0.0
for i in range(1000):
    rsum = rsum+r1[i]+r2[i]+r3[i]
    y.append(rsum/((i+1)*3))
    
plt.plot(x,y,color='red')
plt.show()
