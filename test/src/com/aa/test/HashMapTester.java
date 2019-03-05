package com.aa.test;

import java.util.HashMap;
import java.util.Map;

import com.aa.myMap.MyHashMap;
import com.aa.myMap.MyMap;

public class HashMapTester {
	void test(){
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
	}
	
	public static void main (String[] args) {
		MyMap<String, String> mymap = new MyHashMap<String, String>(5,5);
		mymap.put("key1", "value1");
		mymap.put("key1", "value3");
		mymap.put("key2", "value2");
		for (String value : mymap.values()) {
			System.out.println(value);
		}
	}
}
