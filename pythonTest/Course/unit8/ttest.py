from scipy import stats

x = stats.norm.rvs(loc=5, scale=10, size=50)

print stats.ttest_1samp(x,5.0)
print stats.ttest_1samp(x,1.0)

x = stats.norm.rvs(loc=5, scale=1, size=50)
y = stats.norm.rvs(loc=2, scale=10, size=50)

print stats.ttest_ind(x,y)
print stats.ttest_ind(x,y,equal_var=False)