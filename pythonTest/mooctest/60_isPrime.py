class Solution():
    def solve(self, A):
        #call function prime
        result = []
        for num in A:
            if self.prime(num):
                result.append(num)
        return result

    #judge whether x is prime or not
    def prime(self, x):
        if x <= 1:
            return False
        for i in range(2,int(x**0.5)):
            if x % i == 0:
                return False
        return True

a = Solution()
print a.solve([23,45,76,67,17])