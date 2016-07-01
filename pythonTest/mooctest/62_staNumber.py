class Solution():
    def solve(self, A):
        dic = {0:0,1:0,2:0,3:0,4:0,5:0,6:0,7:0,8:0,9:0}
        for num in A:
            for number in num:
                number = int(number)
                if dic.has_key(number):
                    dic[number] = dic[number]+1
                else:
                    dic[number] = 1
        return dic
    
a = Solution()
print a.solve(['12','34','567','36','809','120'])