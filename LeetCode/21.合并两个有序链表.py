#
# @lc app=leetcode.cn id=21 lang=python3
#
# [21] 合并两个有序链表
#
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
    def mergeTwoLists(self, l1: ListNode, l2: ListNode) -> ListNode:
        n = ListNode(0)
        c = n
        while 1:
            if l1 == None:
                c.next = l2
                return n.next
            elif l2 == None:
                c.next = l1
                return n.next
            elif l1.val <= l2.val:
                c.next = l1
                l1 = l1.next
            else:
                c.next = l2
                l2 = l2.next
            c = c.next
        return n.next
    