#
# @lc app=leetcode.cn id=14 lang=python3
#
# [14] 最长公共前缀
#
from typing import List
class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        if strs == [] :
            return ""
        p = strs[0]
        for s in strs:
            while s.find(p) != 0:
                p = p[0: -1]
                if p == '':
                    return ''
        return p
    