package com.aa.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aa.myMap.MyHashMap;
import com.aa.myMap.MyMap;

public class HashMapTester {
	void test() {
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
		
		LinkedHashMap<String, String> ccHashMap2 = new LinkedHashMap<>();
		ccHashMap2.put("key2", "value2");
	}

	static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

	static final int spread(int h) {
		return (h ^ (h >>> 16)) & HASH_BITS;
	}

	private static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * The default initial table capacity. Must be a power of 2 (i.e., at least 1)
	 * and at most MAXIMUM_CAPACITY.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	private static final int tableSizeFor(int c) {
		int n = c - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	public static void main(String[] args) {
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

		int n1 = 512;
		int hash = 1232131072;
		int hash1 = 1534534534;
		System.out.println((n1 - 1) & hash);
		System.out.println((n1 - 1) & hash1);

		System.out.println(spread(hash));
		System.out.println(spread(hash1));

		int initialCapacity = 16;
		int concurrencyLevel = 16;
		float loadFactor = 0.75f;
		if (initialCapacity < concurrencyLevel) // Use at least as many bins
			initialCapacity = concurrencyLevel; // as estimated threads
		long size = (long) (1.0 + (long) initialCapacity / loadFactor);
		int cap = (size >= (long) MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : tableSizeFor((int) size);
		System.out.println(cap);
		
		int n = 64;
		int NCPU = 1;
		int stride = (NCPU > 1) ? (n >>> 3) / NCPU : n;
		System.out.println(stride);

		Map<String, String> ccHashMap = new ConcurrentHashMap<>();
		ccHashMap.put("key2", "value2");

	}
}
