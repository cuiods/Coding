from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

fig = plt.figure()
ax = fig.add_subplot(111,projection='3d')
for c,z in zip(['r','g','b','y'],[30,20,10,0]):
    x = np.arange(20)
    y = np.random.rand(20)
    cs = [c]*len(x)
    ax.bar(x,y,z,zdir='y',color=cs)
    
plt.show()