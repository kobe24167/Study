package com.leetCode;

import java.util.HashMap;
import java.util.Map;

import com.exmaple.annotation.Log;

public class Solution {

	public static void main(String[] args) {
		int[] nums = { 2, 7, 11, 15 };
		int target = 9;
		twoSum(nums, target);
	}

	@Log(difficult=1, executionTime="ִ����ʱ: 127 ms, ��Two Sum��Java�ύ�л�����4.05% ���û�")
	public static int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums.length; j++) {
				if (i!=j && (nums[i] + nums[j]) == target) {
					int [] result = {i, j};
					return result;
				}
			}
		}
		return null;
	}
	
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

}
