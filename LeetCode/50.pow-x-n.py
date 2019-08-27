#
# @lc app=leetcode.cn id=50 lang=python3
#
# [50] Pow(x, n)
#
class Solution:
    def myPow(self, x: float, n: int) -> float:
        judge = True
        if n < 0:
            n = -n
            judge = False 
        f = 1
        while n>0:
            if n & 1:
                f *= x
            x *= x
            n >>= 1
        return f if judge else 1/f
