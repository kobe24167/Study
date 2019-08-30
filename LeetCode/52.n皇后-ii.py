#
# @lc app=leetcode.cn id=52 lang=python3
#
# [52] N皇后 II
#
class Solution:
    def totalNQueens(self, n: int) -> int:
        res = 0
        columns = [True for i in range(n)]
        left = [True for i in range(2*n-1)]
        right = [True for i in range(2*n-1)]
        queens = set()
        def addQueen(x = 0, res = 0):
            for y in range(n):
                if columns[y] and left[x+y] and right[n-y+x-1]:
                    queens.add((x, y))
                    columns[y] = False
                    left[x+y] = False
                    right[n-y+x-1] = False
                    if x == n-1:
                        res+=1
                    else:
                        res = addQueen(x+1, res)
                    queens.remove((x, y))
                    columns[y] = True
                    left[x+y] = True
                    right[n-y+x-1] = True
            return res
        return addQueen()
