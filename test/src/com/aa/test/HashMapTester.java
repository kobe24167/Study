package com.aa.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aa.myMap.MyHashMap;
import com.aa.myMap.MyMap;

public class HashMapTester {
	void test(){
		List<String> list = new ArrayList<>();
		list.spliterator().trySplit();
		
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.entrySet().spliterator();
		
		Map<String, String> ccHashMap = new ConcurrentHashMap<>();
		ccHashMap.put("key2", "value2");
		
		Map<String, String> ccHashMap1 = new ConcurrentHashMap<>(16, 0.75f, 50);
		ccHashMap1.put("key2", "value2");
	}
	
	static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
	static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
	
	public static void main (String[] args) {
		MyMap<String, String> mymap = new MyHashMap<String, String>();
//		for (int i = 0; i < 550; i++) {
//			System.out.println("²åÈëµÚ: "+i+"¸ö");
//			mymap.put("key"+i, "value"+i);
//		}
//		for (String value : mymap.values()) {
//			System.out.println(value);
//		}
		
		mymap.values();
		
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		
		int n = 512;
		int hash = 1232131072;
		int hash1 = 1534534534;
		System.out.println((n - 1) & hash);
		System.out.println((n - 1) & hash1);
		
		System.out.println(spread(hash));
		System.out.println(spread(hash1));
		
		System.out.println(1 << 30);
		System.out.println(32 >> 1);
		System.out.println(32 >>> 1);
		System.out.println(1 << 30 >>> 1);
		
		Map<String, String> ccHashMap = new ConcurrentHashMap<>();
		ccHashMap.put("key2", "value2");
		
	}
}
