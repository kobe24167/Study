#
# @lc app=leetcode.cn id=8 lang=python3
#
# [8] 字符串转换整数 (atoi)
#
import re
class Solution:
    def myAtoi(self, str: str) -> int:
        return max(min(int(*re.findall('^[\+\-]?\d+', str.lstrip())), 2**31 - 1), -2**31)
            
        

