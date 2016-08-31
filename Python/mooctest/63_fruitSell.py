class Solution():
    def solve(self, A):
        result = {}
        for fruit in A:
            if result.has_key(fruit):
                result[fruit] = result[fruit]+1
            else:
                result[fruit] = 1
        return result
    
a = Solution()
print a.solve(['apple','cherry','apple'])