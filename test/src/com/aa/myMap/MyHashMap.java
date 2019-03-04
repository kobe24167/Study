package com.aa.myMap;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class MyHashMap<K, V> extends AbstractMyMap<K, V> implements MyMap<K, V>, Cloneable, Serializable {

	private static final long serialVersionUID = 292303217387536076L;

	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
	
	static final int MAXUMUM_CAPACITY = 1 << 30;
	
	static final float DEFAULT_LOAD_FACTOR = 0.75f; 
	
	static final int TREEIFY_THRESHOLD = 8;
	
	static final int UNTREEIFY_THRESHOLD = 6;
	
	static final int MIN_TREEIFY_CAPACITY = 64;
	
	static class Node<K,V> implements MyMapEntry<K, V> {
		final int hash;
		final K key;
		V value;
		Node<K,V> next;
		
		Node(int hash, K key, V value, Node<K,V> next){
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}
		

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}
		
		public final String toString() {
			return key + "=" + value;
		}
		
		public final int hashCode() {
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}
		
		public final boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (o instanceof MyMapEntry) {
				MyMapEntry<?, ?> e = (MyMapEntry<?, ?>) o;
				if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue())) {
					return true;
				}
			}
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Set<MyMapEntry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
