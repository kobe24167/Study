#
# @lc app=leetcode.cn id=64 lang=python3
#
# [64] 最小路径和
#
class Solution:
    def minPathSum(self, grid: List[List[int]]) -> int:
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if i == j == 0: continue
                elif i == 0: grid[i][j] += grid[i][j-1]
                elif j == 0: grid[i][j] += grid[i-1][j]
                else: grid[i][j] += min(grid[i-1][j], grid[i][j-1])
        return grid[-1][-1]


        
