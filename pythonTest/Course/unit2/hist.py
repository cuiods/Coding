import scipy.stats as sta
import matplotlib.pyplot as plt
X = sta.norm(loc=950,scale=20)
plt.hist(X.rvs(size=100),color='yellowgreen')
plt.show()
