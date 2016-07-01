import numpy as np
from scipy.stats import chi2
from scipy.stats import norm
import matplotlib.pyplot as plt

def sampling_distribution():
    fig, ax = plt.subplots(1, 1)
    #display the probability density function
    df = 10
    x=np.linspace(chi2.ppf(0.01, df), chi2.ppf(0.99, df), 100)
    ax.plot(x, chi2.pdf(x, df))

    #simulate the sampling distribution
    y = []
    for i in range(1000):
        r = norm.rvs(loc=5, scale=2, size=df+1)
        rchi2 =(df)*np.var(r)/4
        y.append(rchi2)

    ax.hist(y, normed=True, alpha=0.2) 
    plt.savefig('sampling_distribution.png')

#the code should not be changed
if __name__ == '__main__':
    sampling_distribution()
