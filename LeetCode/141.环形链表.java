/*
 * @lc app=leetcode.cn id=141 lang=java
 *
 * [141] 环形链表
 */
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

public class Solution {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode n1 = head;
        ListNode n2 = head.next;

        while (n1 != n2) {
            if (n2 == null || n2.next == null) {
                return false;
            }
            n1 = n1.next;
            n2 = n2.next.next;
        }
        return true;
    }
    
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n1;
        System.out.println(hasCycle(n1));
    }
    
    
}

