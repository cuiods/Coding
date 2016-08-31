#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

def uniform_distribution():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.gca(projection='3d')
    #set X,Y,Z
    X = np.arange(-1, 1, 0.02)
    Y = np.arange(-1, 1, 0.02)
    #create coordinate matrices
    X, Y = np.meshgrid(X, Y)
    Z1 = np.zeros((100,100))
    Z2 = 0
    #create surface 1
    surf = ax.plot_surface(X, Y, inArea(X,Y), color='b')
    #create surface 2
    surf = ax.plot_surface(X, Y, Z2, color='r')

    plt.show()

def inArea(x,y):
    r = x**2+y**2
    for i in range(len(x)):
        for j in range(len(y)):
            if r[i,j]>1:
                r[i,j] = 0
            else:
                r[i,j] = 1
    return r

#the code should not be changed
if __name__ == '__main__':
    uniform_distribution()
