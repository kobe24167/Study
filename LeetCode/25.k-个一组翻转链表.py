#
# @lc app=leetcode.cn id=25 lang=python3
#
# [25] K 个一组翻转链表
#
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None
from typing import List
class Solution:
    def reverseKGroup(self, head: ListNode, k: int) -> ListNode:
        dummy = ListNode(0)
        p = dummy
        while True:
            count = k 
            stack = []
            tmp = head
            while count and tmp:
                stack.append(tmp)
                tmp = tmp.next
                count -= 1
            # 注意,目前tmp所在k+1位置
            # 说明剩下的链表不够k个,跳出循环
            if count : 
                p.next = head
                break
            # 翻转操作
            while stack:
                p.next = stack.pop()
                p = p.next
            #与剩下链表连接起来 
            p.next = tmp
            head = tmp
        return dummy.next
        