#
# @lc app=leetcode.cn id=23 lang=python3
#
# [23] 合并K个排序链表
#
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None
from typing import List
class Solution:
    def mergeKLists(self, lists: List[ListNode]) -> ListNode:
        interval = 1
        n = len(lists)
        while interval < n:
            for i in range(0, n - interval, interval*2):
                lists[i] = self.mergeTwoLists(lists[i], lists[i+interval])
            interval = 2*interval
        return lists[0] if n > 0 else lists
    def mergeTwoLists(self, l1: ListNode, l2: ListNode) -> ListNode:
        head = point = ListNode(0)
        while l1 and l2:
            if l1.val <= l2.val:
                point.next = l1
                l1 = l1.next
            else:
                point.next = l2
                l2 = l1
                l1 = point.next.next
            point = point.next
        if not l1:
            point.next=l2
        else:
            point.next=l1
        return head.next
