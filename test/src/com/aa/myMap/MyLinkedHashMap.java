package com.aa.myMap;

public class MyLinkedHashMap<K,V> extends MyHashMap<K, V> implements MyMap<K, V> {
	
	private static final long serialVersionUID = 2253651681210412441L;

	static class Entry<K,V> extends MyHashMap.Node<K, V>{
		Entry<K,V> before, after;
		Entry(int hash, K key, V value, Node<K,V> next) {
			super(hash, key, value, next);
		}
	}
}
