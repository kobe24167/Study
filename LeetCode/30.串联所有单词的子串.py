#
# @lc app=leetcode.cn id=30 lang=python3
#
# [30] 串联所有单词的子串
#
from typing import List
class Solution:
    def findSubstring(self, s: str, words: List[str]) -> List[int]:
        if not s or not words:return []
        onelen = len(words[0])
        wlen = len(words)
        count = wlen*onelen
        slen = len(s)
        if slen < count:return []
        res = []
        for i in range(slen-count):
            temp = words.copy()
            j = 0
            while j<wlen:
                a = s[onelen*j+i:onelen*(j+1)+i]
                try:
                    if temp.index(a) > -1 :
                        temp.remove(a)
                except ValueError as e:
                    break
                j+=1
            if j==wlen:
                res.append(i)
        return res



s = Solution()
print(s.findSubstring(s="wordgoodgoodgoodbestword", words=["foo","bar"]))

