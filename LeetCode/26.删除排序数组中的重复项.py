#
# @lc app=leetcode.cn id=26 lang=python3
#
# [26] 删除排序数组中的重复项
#
from typing import List
class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        if not nums: return 0
        k = 1
        for i in range(1, len(nums)):
            if nums[i] != nums[i - 1]:
                nums[k] = nums[i]
                k += 1
        return k
print(Solution().removeDuplicates([0,0,1,1,1,2,2,2]))
