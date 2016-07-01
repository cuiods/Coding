import math
import matplotlib.pyplot as plt
import random
import numpy as np
from scipy.stats import expon as E

def exp(lam):
    if lam == 0:
        return 0
    p = random.random()
    return -math.log(1-p)/lam

x1 = []
for i in range(100):
    x1.append(exp(i))
x1 = sorted(x1)
y = np.linspace(0, 1, 100)
plt.plot(x1, y, color='green')
 
rv = E(scale=1)
x2 = np.linspace(0,5,100)
plt.plot(x2, rv.cdf(x2), color='red')
plt.show()