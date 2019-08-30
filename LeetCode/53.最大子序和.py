#
# @lc app=leetcode.cn id=53 lang=python3
#
# [53] 最大子序和
#
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        a=nums[0]
        s=0
        for i in nums:
            if s>0:
                s+=i
            else:
                s = i
            a = max(a, s)
        return a

