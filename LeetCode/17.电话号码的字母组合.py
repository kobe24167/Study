#
# @lc app=leetcode.cn id=17 lang=python3
#
# [17] 电话号码的字母组合
#
from typing import List
class Solution:
    def addLetter(self, letter, strs) -> List[str]:
        if not strs:
            strs = []
        newStrs = []
        if strs == []:
            for s in letter:
                newStrs.append(s)
        else:
            for s in letter:
                for a in strs:
                    newStrs.append(a+s)
        return newStrs
    def letterCombinations(self, digits: str) -> List[str]:
        keyboard = {
            '2':'abc',
            '3':'def',
            '4':'ghi',
            '5':'jkl',
            '6':'mno',
            '7':'pqrs',
            '8':'tuv',
            '9':'wxyz',
        }
        r = []
        for c in digits:
            r = self.addLetter(keyboard.get(c), r)
        return r
