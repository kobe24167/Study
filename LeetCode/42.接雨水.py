#
# @lc app=leetcode.cn id=42 lang=python3
#
# [42] 接雨水
#
class Solution:
    def trap(self, height: List[int]) -> int:
        sum, maxl, maxr = 0,0,[0 for i in range(len(height))]
        i = len(height) - 2
        while i>=0:
            maxr[i] = max(maxr[i+1], height[i+1])
            i-=1
        for i in range(1, len(height)):
            maxl = max(maxl,height[i-1])
            minn = min(maxl,maxr[i])
            if minn > height[i]:
                sum += (minn - height[i])
        return sum

