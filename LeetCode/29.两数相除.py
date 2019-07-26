#
# @lc app=leetcode.cn id=29 lang=python3
#
# [29] 两数相除
#
class Solution:
    def divide(self, dividend: int, divisor: int) -> int:
        res = 0
        f = 1 if dividend ^ divisor >= 0 else -1
        dividend = abs(dividend)
        divisor = abs(divisor)
        while dividend >= divisor:
            t = divisor
            i = 1
            while dividend >= t:
                dividend -= t
                res += i
                i <<= 1
                t <<= 1
        res *= f
        return min(max(-2**31, res), 2**31-1)
