#
# @lc app=leetcode.cn id=9 lang=python3
#
# [9] 回文数
#
class Solution:
    def isPalindrome(self, x: int) -> bool:
        a = str(x)
        b = [];
        l = len(a)
        i = 0
        helf = 0
        if l%2==1:
            helf = l//2
            while i < l:
                if i > helf:
                    if b.pop() != a[i]:
                        return False
                elif i == helf:
                    pass
                else:
                    b.append(a[i])
                i += 1
        else:
            helf = l/2-1
            while i < l:
                if i>helf:
                    if b.pop() != a[i]:
                        return False
                else:
                    b.append(a[i])
                i += 1
        
        return True
print(Solution().isPalindrome(x = 12))

