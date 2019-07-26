#
# @lc app=leetcode.cn id=20 lang=python3
#
# [20] 有效的括号
#
from typing import List
class Solution:
    
    def isValid(self, s: str) -> bool:
        m = {')':'(', ']':'[','}':'{'}
        l = []
        for c in s:
            b = m.get(c)
            if b != None and len(l) != 0:
                a = l.pop()
                if a != b:
                    return False
            else :
                l.append(c)
        if len(l) == 0:
            return True
        else:
            return False

