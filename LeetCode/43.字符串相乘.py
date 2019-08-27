#
# @lc app=leetcode.cn id=43 lang=python3
#
# [43] 字符串相乘
#
class Solution:
    def multiply(self, num1: str, num2: str) -> str:
        nlen = len(num2)
        i = nlen-1
        count = 0
        while i >= 0:
            count += int(num2[i])*int(num1)*(10**(nlen-1-i))
            i -= 1
        return str(count)


