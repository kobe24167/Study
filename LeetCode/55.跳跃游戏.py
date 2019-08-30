#
# @lc app=leetcode.cn id=55 lang=python3
#
# [55] 跳跃游戏
#
class Solution:
    def canJump(self, nums: List[int]) -> bool:
        if nums == [0]:
            return True
        i = len(nums)-1
        while i>0:
            i -=1
            if nums[i] == 0:
                j = 0
                fleg = False
                while i-j >=0:
                    j+=1
                    if i-j >= 0 and nums[i-j]-j > 0:
                        fleg = True
                        break
                if fleg:
                    continue
                else:
                    return False
        return True

