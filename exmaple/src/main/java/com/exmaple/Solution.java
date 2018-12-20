package com.exmaple;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exmaple.annotation.Log;

@Service
public class Solution {

	public static void main(String[] args) {
		int[] nums = { 2, 7, 11, 15 };
		int target = 9;
		twoSum(nums, target);
	}

	@Log(name="twoSum", difficult=1, executionTime="执行用时: 127 ms, 在Two Sum的Java提交中击败了4.05% 的用户")
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
	
	@Log(name="twoSum", difficult=1, executionTime="执行用时: 127 ms, 在Two Sum的Java提交中击败了4.05% 的用户")
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
