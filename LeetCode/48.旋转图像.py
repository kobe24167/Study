#
# @lc app=leetcode.cn id=48 lang=python3
#
# [48] 旋转图像
#
class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        n = len(matrix)
        for i in range(int(n/2)):
            for j in range(i, n-i-1):
                temp = matrix[i][j]                     #1->t
                matrix[i][j] = matrix[n-j-1][i]         #4->1
                matrix[n-j-1][i] = matrix[n-i-1][n-j-1] #3->4
                matrix[n-i-1][n-j-1] = matrix[j][n-i-1] #2->3
                matrix[j][n-i-1] = temp                 #t->2
