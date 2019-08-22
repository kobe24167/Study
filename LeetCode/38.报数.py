#
# @lc app=leetcode.cn id=38 lang=python3
#
# [38] 报数
#
class Solution:
    def countAndSay(self, n: int) -> str:
        if n == 1: return '1'
        s = self.countAndSay(n-1)
        r, c = '', 0
        for i in range(len(s)):
            c += 1
            if i == len(s) - 1 or s[i] != s[i+1]:
                r+=str(c)
                r+=s[i]
                c = 0
        return r
