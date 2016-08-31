import numpy as np
class Solution():
    def solve(self, A):
        result = []
        sum = 0.0
        row = [0.0,0.0,0.0,0.0]
        col = [0.0,0.0,0.0,0.0]
        for i in range(4):
            for j in range(4):
                sum = sum + A[i,j]
                row[i] = row[i]+A[i,j]
                col[j] = col[j]+A[i,j]
        result.append(sum)
        for i in range(4):
            result.append(row[i]/4.0)
        for i in range(4):
            result.append(col[i]/4.0)
        return result
a = Solution()
n = np.array([[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]])
print a.solve(n)