package com.exmaple;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exmaple.annotation.Log;

@Service
public class Solution {

	public static void main(String[] args) {
//		int[] nums = { 2, 7, 11, 15 };
//		int target = 9;
//		twoSum(nums, target);
		ListNode l1 = new ListNode(2);
		insert(insert(l1, 4), 3);
		ListNode l2 = new ListNode(5);
		insert(insert(l2, 6), 4);
		ListNode l3 = new ListNode(9);
		insert(l3, 9);
		ListNode l4 = new ListNode(5);
		ListNode l5 = new ListNode(5);
		System.out.println(addTwoNumbers(l5, l4).val);
	}

	@Log(name = "twoSum", difficult = 1, executionTime = "执行用时: 127 ms, 在Two Sum的Java提交中击败了4.05% 的用户")
	public static int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums.length; j++) {
				if (i != j && (nums[i] + nums[j]) == target) {
					int[] result = { i, j };
					return result;
				}
			}
		}
		return null;
	}

	@Log(name = "twoSum", difficult = 1, executionTime = "执行用时: 127 ms, 在Two Sum的Java提交中击败了4.05% 的用户")
	public int[] twoSumExmaple(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement)) {
				return new int[] { map.get(complement), i };
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No two sum solution");
	}
	
    public static ListNode insert(ListNode old, int value){
    	ListNode node = new ListNode(value);
    	old.next = node;
    	return node;
    }

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	@Log(name = "addTwoNumbers", difficult = 2, executionTime = "54 ms, 在Add Two Numbers的Java提交中击败了58.13% 的用户")
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode s = reservePrt(l1, l2);
		return s;
	}
	static int carry = 0;
	public static ListNode reservePrt(ListNode node1, ListNode node2){
		if (node1 != null && node2 != null ) {
			int s = node1.val+node2.val+carry;
			ListNode node;
			if(s >= 10){
				node = new ListNode(s-10);
				carry = 1;
			} else {
				node = new ListNode(s);
				carry = 0;
			}
			node.next = reservePrt(node1.next, node2.next);
			return node;
		} else if (node1 != null) {
			int s = node1.val+carry;
			ListNode node;
			if(s >= 10){
				node = new ListNode(s-10);
				carry = 1;
			} else {
				node = new ListNode(s);
				carry = 0;
			}
			node.next = reservePrt(node1.next, null);
			return node;
		} else if (node2 != null) {
			int s = node2.val+carry;
			ListNode node;
			if(s >= 10){
				node = new ListNode(s-10);
				carry = 1;
			} else {
				node = new ListNode(s);
				carry = 0;
			}
			node.next = reservePrt(null, node2.next);
			return node;
		}
		if (carry == 1) {
			return new ListNode(carry);
		}
		return null;
    }
	
	public ListNode addTwoNumbersExmaple(ListNode l1, ListNode l2) {
	    ListNode dummyHead = new ListNode(0);
	    ListNode p = l1, q = l2, curr = dummyHead;
	    int carry = 0;
	    while (p != null || q != null) {
	        int x = (p != null) ? p.val : 0;
	        int y = (q != null) ? q.val : 0;
	        int sum = carry + x + y;
	        carry = sum / 10;
	        curr.next = new ListNode(sum % 10);
	        curr = curr.next;
	        if (p != null) p = p.next;
	        if (q != null) q = q.next;
	    }
	    if (carry > 0) {
	        curr.next = new ListNode(carry);
	    }
	    return dummyHead.next;
	}

}
