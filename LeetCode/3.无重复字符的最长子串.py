#
# @lc app=leetcode.cn id=3 lang=python3
#
# [3] 无重复字符的最长子串
#
class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        if s == "":
            return 0
        i, j, lmax = 0, 1, 1
        slen = len(s)
        while j < slen:
            if s[i:j].find(s[j]) > -1:
                i += 1
            else:
                j += 1
                lmax = max(lmax, j-i)
        return lmax
