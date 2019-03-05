package com.aa.test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTester {
	void test(){
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
	}
	
	public static void main (String[] args) {
		int MAXIMUM_CAPACITY = 1 << 30;
		System.out.println(MAXIMUM_CAPACITY);
		int cap = 1023;
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		System.out.println(n);
	}
}
