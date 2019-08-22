#
# @lc app=leetcode.cn id=41 lang=python3
#
# [41] 缺失的第一个正数
#
class Solution:
    def firstMissingPositive(self, nums: List[int]) -> int:
        res = 1
        for i in nums:
            if res == i:
                res = i+1
        return res
