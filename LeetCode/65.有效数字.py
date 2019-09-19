#
# @lc app=leetcode.cn id=65 lang=python3
#
# [65] 有效数字
#
class Solution:
    def isNumber(self, s: str) -> bool:
        try:
            num = float(s)
            return True
        except:
            return False

