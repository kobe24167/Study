#
# @lc app=leetcode.cn id=19 lang=python3
#
# [19] 删除链表的倒数第N个节点
#
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def removeNthFromEnd(self, head: ListNode, n: int) -> ListNode:
        a = b = head
        for x in range(n):
            a = a.next
        if not a:
            return head.next
        while a and a.next:
            a = a.next
            b = b.next
        b.next = b.next.next
        return head
