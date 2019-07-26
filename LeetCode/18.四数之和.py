#
# @lc app=leetcode.cn id=18 lang=python3
#
# [18] 四数之和
#
from typing import List
class Solution:
    def threeSum(self, nums, target) -> List[List[int]]:
        n = len(nums)
        res = []
        #print(nums)
        for i in range(n):
            if i > 0 and nums[i] == nums[i-1]:
                continue
            left = i + 1
            right = n - 1
            while left < right:
                cur_sum = nums[i] + nums[left] + nums[right]
                if cur_sum == target:
                    tmp = [nums[i],nums[left],nums[right]]
                    res.append(tmp)
                    while left < right and nums[left] == nums[left+1]:
                        left += 1
                    while left < right and nums[right] == nums[right-1]:
                        right -= 1
                    left += 1
                    right -= 1
                elif cur_sum > target:
                    right -= 1
                else:
                    left += 1
        return res
    def fourSum(self, nums: List[int], target: int) -> List[List[int]]:
        nums.sort()
        r = []
        n = len(nums)
        last = None
        for i in range(n-3):
            a = nums.pop(0)
            if last == a:
                continue
            if a > 0 and a > target:
                return r
            last = a
            r1 = self.threeSum(nums, target-a)
            if r1 != []:
                for b in r1:
                    b.insert(0, a)
                    r.append(b)
        return r
