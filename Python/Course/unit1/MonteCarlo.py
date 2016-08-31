import random

n = 1000000
k = 0
for i in range(n):
    x = random.uniform(-1,1)
    y = random.uniform(-1,1)
    if x**2 + y**2 < 1:
        k = k + 1

print 4 * float(k) / float(n)
