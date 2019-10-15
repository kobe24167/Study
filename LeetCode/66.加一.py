#
# @lc app=leetcode.cn id=66 lang=python3
#
# [66] 加一
#
# @lc code=start
class Solution:
    def plusOne(self, digits: List[int]) -> List[int]:
        index = 1
        n = len(digits)
        while digits[-index] == 9:
            digits[-index] = 0
            if index == n:
                digits.insert(0, 1)
                break
            index += 1
        else:
            digits[-index] += 1
        return digits
# @lc code=end