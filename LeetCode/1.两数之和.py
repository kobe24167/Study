#
# @lc app=leetcode.cn id=1 lang=python3
#
# [1] 两数之和
#
from typing import List
class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        if nums != []:
            pass
        a = sorted(nums)
        i, j = 0, len(a)-1
        a.index
        while j > 0:
            if a[i]+a[j] > target:
                j -= 1
            elif a[i]+a[j] < target:
                i += 1
            else:
                return [nums.index(a[i]), nums.index(a[j])]

