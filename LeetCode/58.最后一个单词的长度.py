#
# @lc app=leetcode.cn id=58 lang=python3
#
# [58] 最后一个单词的长度
#
class Solution:
    def lengthOfLastWord(self, s: str) -> int:
        n = len(s)
        r = []
        while n>0:
            n -= 1
            if s[n] != ' ':
                r.append(s[n])
            elif r == [] and s[n] == ' ':
                continue
            elif r != [] and s[n] == ' ':
                break
        return len(r)
