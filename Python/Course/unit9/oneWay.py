from scipy import stats
A1 = [27.0,26.2,28.8,33.5,28.8]
A2 = [22.8,23.1,27.7,27.6,24.0]
A3 = [21.9,23.4,20.1,27.8,19.3]
A4 = [23.5,19.6,23.7,20.8,23.9]

print stats.f_oneway(A1,A2,A3,A4)