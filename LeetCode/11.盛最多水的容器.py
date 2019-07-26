#
# @lc app=leetcode.cn id=11 lang=python3
#
# [11] 盛最多水的容器
#
from typing import List
class Solution:
    def maxArea(self, height: List[int]) -> int:
        maxarea,l = 0,0
        r = len(height) - 1
        while l < r:
            maxarea = max(maxarea, min(height[l],height[r])*(r-l))
            if height[l] < height[r]:
                l += 1
            else:
                r -= 1
        return maxarea
print(Solution().maxArea(height = [1,3,4,2]))
