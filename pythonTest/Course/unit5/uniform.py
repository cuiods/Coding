#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
import math

def uniform_distribution():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.gca(projection='3d')
    #set X,Y,Z
    X = np.arange(-1, 1, 0.01)
    Y = np.arange(-1, 1, 0.01)
    #create coordinate matrices
    X, Y = np.meshgrid(X, Y)
    Z1 = np.zeros((len(X),len(Y)))
    for i in range(200):
        for j in range(200):
            if X[0,i]**2+Y[j,0]**2<1:
                Z1[i,j] = 1.0/math.pi
    #create surface 1
    surf = ax.plot_surface(X, Y, Z1, color='b')
    #create surface 2
    #surf = ax.plot_surface(X, Y, Z2, color='r')

    plt.savefig('uniform_distribution.png')

#the code should not be changed
if __name__ == '__main__':
    uniform_distribution()
