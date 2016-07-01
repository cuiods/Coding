from scipy import stats

A = [16,18,16,14,12,12]
B = [16,16,16,16,16,8]

print stats.chisquare(A);
print stats.chisquare(A,f_exp=B)