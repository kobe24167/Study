#
# @lc app=leetcode.cn id=33 lang=python3
#
# [33] 搜索旋转排序数组
#
from typing import List
class Solution:
    def search(self, nums: List[int], target: int) -> int:
        i = 0
        nlen = len(nums) - 1
        while (nums[i] >= nums[i+1] and i < nlen and i<target):
            i+=1
        return i

s = Solution()
print(s.search(nums = [4,5,6,7,0,1,2]), target = 0)

