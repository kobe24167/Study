#
# @lc app=leetcode.cn id=16 lang=python3
#
# [16] 最接近的三数之和
#
from typing import List
class Solution:
    def threeSumClosest(self, nums: List[int], target: int) -> int:
        nums.sort()
        n = len(nums)
        r = nums[0]+nums[1]+nums[2]
        for i in range(n):
            if i > 0 and nums[i] == nums[i-1]:
                continue
            left = i + 1
            right = n - 1
            while left < right:
                cur_sum = nums[i] + nums[left] + nums[right]
                if cur_sum == target:
                    return target
                elif cur_sum > target:
                    right -= 1
                else:
                    left += 1
                if abs(cur_sum-target) < abs(r-target):
                    r = cur_sum
        return r
