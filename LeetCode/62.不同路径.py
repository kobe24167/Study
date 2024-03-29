#
# @lc app=leetcode.cn id=62 lang=python3
#
# [62] 不同路径
#
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        cur = [1]*n
        for i in range(1, m):
            for j in range(1,n):
                cur[j] += cur[j-1]
        return cur[-1]
