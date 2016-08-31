class Solution():
    def solve(self, A):
        #use isPalindrom function to check if the string is palindrome or not
        lengthA = len(A)
        result = []
        for it in range(lengthA):
            str1 = A[it]
            if self.isPalindrome(str1):
                result.append(str1)
        return result

    def isPalindrome(self, x):
        length = len(x)
        for i in range(length/2):
            if x[i]!=x[length-i-1]:
                return False
        return True

a = Solution()
print a.solve(['123','232','4556554','12123','3443'])