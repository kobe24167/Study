package com.aa.myMap;

public interface MyMapEntry<K, V> {
	K getKey();
	V getValue();
	V setValue(V value);
	boolean equals(Object o);
	int hashCode();
	
}
