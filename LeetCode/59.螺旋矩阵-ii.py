#
# @lc app=leetcode.cn id=59 lang=python3
#
# [59] 螺旋矩阵 II
#
class Solution:
    def generateMatrix(self, n: int) -> List[List[int]]:
        def spiral_coords(r1, c1, r2, c2):
            for c in range(c1, c2 + 1):
                yield r1, c
            for r in range(r1 + 1, r2 + 1):
                yield r, c2
            if r1 < r2 and c1 < c2:
                for c in range(c2 - 1, c1, -1):
                    yield r2, c
                for r in range(r2, r1, -1):
                    yield r, c1
        
        matrix = [[0 for x in range(n)] for x in range(n)]
        if not matrix: return []
        r1, r2 = 0, n-1
        c1, c2 = 0, n - 1
        i = 1
        while r1 <= r2 and c1 <= c2:
            for r, c in spiral_coords(r1, c1, r2, c2):
                matrix[r][c] = i
                i+=1
            r1 += 1; r2 -= 1
            c1 += 1; c2 -= 1
            
        return matrix

