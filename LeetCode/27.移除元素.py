#
# @lc app=leetcode.cn id=27 lang=python3
#
# [27] 移除元素
#
class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:
        if not nums: return 0
        k = 0
        for i in range(len(nums)):
            if nums[i] != val:
                nums[k] = nums[i]
                k += 1
        return k

