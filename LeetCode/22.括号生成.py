#
# @lc app=leetcode.cn id=22 lang=python3
#
# [22] 括号生成
#
class Solution:
    def generateParenthesis(self, n: int) -> List[str]:
        r = []
        def add(s = '', i=0, j=0):
            if len(s) == 2*n:
                r.append(s)
                return
            if i<n :
                add(s+"(", i+1, j)
            if j<i :
                add(s+')', i, j+1)
        add()
        return r

