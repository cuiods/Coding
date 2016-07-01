#-*- coding:utf-8 -*-
import numpy as np

class Solution():
    def solve(self, A):
        f = np.array([2.0,0.0,-1.0,1.0])
        f = np.poly1d(f)
        g = np.array(A)
        g = np.poly1d(g)
        f = np.polymul(f, g)
        return f

a = Solution()
print a.solve([1,2])