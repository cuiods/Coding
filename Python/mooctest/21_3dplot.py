#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
from matplotlib import cm
from matplotlib.ticker import LinearLocator, FormatStrFormatter
import matplotlib.pyplot as plt
import numpy as np

def surface_3d():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.gca(projection='3d')
    #set X,Y,Z
    X = np.arange(-5, 5, 0.25)
    Y = np.arange(-5, 5, 0.25)
    X, Y = np.meshgrid(X, Y)
    R = np.sqrt(X**2 + Y**2)
    Z = np.sin(R)
    #create surface
    surf = ax.plot_surface(X, Y, Z, rstride=1, cstride=1, cmap=cm.coolwarm,
            linewidth=0, antialiased=False)

    ax.set_zlim(-1.01, 1.01)#set z limits
    ax.zaxis.set_major_locator(LinearLocator(10))#set tick locator to linear locator
    ax.zaxis.set_major_formatter(FormatStrFormatter('%.02f'))#set tick formatter to str formatter
    fig.colorbar(surf, shrink=0.5, aspect=5)#add a colorbar to plot

    plt.show()

#the code should not be changed
if __name__ == '__main__':
    surface_3d()
