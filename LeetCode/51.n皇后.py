#
# @lc app=leetcode.cn id=51 lang=python3
#
# [51] N皇后
#
class Solution:
    def solveNQueens(self, n: int) -> List[List[str]]:
        res = []
        columns = [True for i in range(n)]
        left = [True for i in range(2*n-1)]
        right = [True for i in range(2*n-1)]
        queens = set()
        def addQueen(x=0):
            for y in range(n):
                if columns[y] and left[x+y] and right[n-y+x-1]:
                    queens.add((x, y))
                    columns[y] = False
                    left[x+y] = False
                    right[n-y+x-1] = False
                    if x == n-1:
                        solution = []
                        for _, col in sorted(queens):
                            solution.append('.' * col + 'Q' + '.' * (n - col - 1))
                        res.append(solution)
                    else:
                        addQueen(x+1)
                    queens.remove((x, y))
                    columns[y] = True
                    left[x+y] = True
                    right[n-y+x-1] = True
        addQueen()
        return res
