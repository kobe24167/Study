package com.aa.myMap;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public interface MyMapEntry<K, V> {
	K getKey();
	V getValue();
	V setValue(V value);
	boolean equals(Object o);
	int hashCode();
	
	public static <K extends Comparable<? super K>, V> Comparator<MyMapEntry<K,V>> comparingByKey() {
        return (Comparator<MyMapEntry<K, V>> & Serializable)
            (c1, c2) -> c1.getKey().compareTo(c2.getKey());
    }
	
	public static <K, V extends Comparable<? super V>> Comparator<MyMapEntry<K,V>> comparingByValue() {
        return (Comparator<MyMapEntry<K, V>> & Serializable)
            (c1, c2) -> c1.getValue().compareTo(c2.getValue());
    }
	
	public static <K, V> Comparator<MyMapEntry<K, V>> comparingByKey(Comparator<? super K> cmp) {
        Objects.requireNonNull(cmp);
        return (Comparator<MyMapEntry<K, V>> & Serializable)
            (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
    }
	
	public static <K, V> Comparator<MyMapEntry<K, V>> comparingByValue(Comparator<? super V> cmp) {
        Objects.requireNonNull(cmp);
        return (Comparator<MyMapEntry<K, V>> & Serializable)
            (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }
}
