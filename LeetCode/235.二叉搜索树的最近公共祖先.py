#
# @lc app=leetcode.cn id=235 lang=python3
#
# [235] 二叉搜索树的最近公共祖先
#
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        if q.val < root.val > p.val:
            return self.lowestCommonAncestor(root.left, p, q)
        elif q.val > root.val < p.val:
            return self.lowestCommonAncestor(root.right, p, q)
        return root

