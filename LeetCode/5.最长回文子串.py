#
# @lc app=leetcode.cn id=5 lang=python3
#
# [5] 最长回文子串
#
class Solution:
    def longestPalindrome(self, s: str) -> str:
        slen = len(s)
        if slen == 0:
            return ''
        index = 0
        count = 0
        result = ''
        while index >= 0 and index < slen:
            i = 1
            b = int(index)
            if (index/0.5)%2 == 1:
                h = s[b]
                while b >= i and b+i < slen:
                    if s[b-i] == s[b+i]:
                        h = s[b-i] + h + s[b+i]
                        i += 1
                    else:
                        break
            else:
                h = ""
                while b >= i and b+i <= slen:
                    if s[b-i] == s[b+i-1]:
                        h = s[b-i] + h + s[b+i-1]
                        i += 1
                    else:
                        break
            if len(h)>count:
                count = len(h)
                result = h
            index +=0.5
        if result == '':
            return s[0]
        return result
