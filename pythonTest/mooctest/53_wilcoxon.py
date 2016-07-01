#-*- coding:utf-8 -*-
from scipy import stats

def chisquare():
    A=[16, 18, 16, 14, 12, 12]
    B=[16, 16, 16, 16, 16, 8]

    print (stats.chisquare(A))
    print (stats.chisquare(A, f_exp=B))

#the code should not be changed
if __name__ == '__main__':
    chisquare()