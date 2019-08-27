#
# @lc app=leetcode.cn id=45 lang=python3
#
# [45] 跳跃游戏 II
#
class Solution:
    def jump(self, nums: List[int]) -> int:
        nlen = len(nums)
        if(nlen < 2):return 0
        index = 0
        jmax = nums[index]
        res = 1
        while True:
            maxL = 1
            maxI = 1
            for i in range(1, jmax+1):
                if index+i >= nlen:
                    return res
                if (nums[index+i]+i) > maxL:
                    maxL = nums[index+i]+i
                    maxI = i
            if jmax + index >= nlen -1:
                return res
            res += 1
            index = index+maxI
            jmax = nums[index]
