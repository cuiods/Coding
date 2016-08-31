#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import matplotlib.mlab as mlab
import numpy as np

def bivariate_normal():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.add_subplot(111, projection='3d')
    #set X,Y,Z
    x = np.linspace(-10, 10, 200)
    y = x
    X,Y = np.meshgrid(x, y)
    #create bivariate gaussian distribution for equal shape X,Y
    Z = mlab.bivariate_normal(X, Y, 1, 5, 0, -5, 0)
    #create surface
    ax.plot_surface(X, Y, Z, cmap='OrRd')

    plt.show()

#the code should not be changed
if __name__ == '__main__':
    bivariate_normal()


# （1）此题为普通编程练习题
# （2）add_subplot(*args,**kwargs)当设定参数projection='3d'时返回Axes3D对象
# （3）numpy.meshgrid(*xi,**kwargs)利用坐标向量生成并返回坐标矩阵
# （4）matplotlib.mlab.bivariate_normal(X,Y,sigmax=1.0,sigmay=1.0,mux=0.0,muy=0.0,sigmaxy=0.0)返回符合二维高斯分布的变量值
# （5）Axes3D.plot_surface(X,Y,Z,*args,**kwargs)生成曲面图
