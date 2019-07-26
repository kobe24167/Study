#
# @lc app=leetcode.cn id=32 lang=python3
#
# [32] 最长有效括号
#
from typing import List
class Solution:
    def longestValidParentheses(self, s: str) -> int:
        l = [-1]
        ml = 0
        for i in range(len(s)):
            if s[i] == '(':
                l.append(i)
            else:
                l.pop()
                if l != []:
                    ml = max(ml, i - l[-1])
                else:
                    l.append(i)
        return ml
