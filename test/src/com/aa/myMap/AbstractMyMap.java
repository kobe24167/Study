package com.aa.myMap;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractMyMap<K, V> implements MyMap<K, V> {
	
	protected AbstractMyMap() {
	}

	public int size() {
		return entrySet().size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	
	public boolean isContainsKey(Object key) {
		Iterator<MyMapEntry<K,V>> i = entrySet().iterator();
		if (key == null) {
			while (i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if (e.getKey() == null) {
					return true;
				}
			}
		} else {
			while (i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if (key.equals(e.getKey()))
					return true;
			}
		}
		return false;
	}

	
	public boolean isContainsValue(Object value) {
		Iterator<MyMapEntry<K,V>> i = entrySet().iterator();
		if (value == null) {
			while (i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if (e.getValue() == null) {
					return true;
				}
			}
		} else {
			while (i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if (value.equals(e.getValue()))
					return true;
			}
		}
		return false;
	}

	
	public V get(Object key) {
		Iterator<MyMapEntry<K, V>> i = entrySet().iterator();
		if (key == null) {
			while(i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if (e.getKey() == null) {
					return e.getValue();
				}
			}
		} else {
			while (i.hasNext()) {
				MyMapEntry<K,V> e = i.next();
				if (key.equals(e.getKey()))
					return e.getValue();
			}
		}
		return null;
	}

	
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}
	
	public V remove(Object key) {
		Iterator<MyMapEntry<K, V>> i = entrySet().iterator();
		MyMapEntry<K, V> correntEntry = null;
		if (key == null) {
			while (correntEntry == null && i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if (e.getKey() == null)
					correntEntry = e;
			}
		} else {
			while (correntEntry == null && i.hasNext()) {
				MyMapEntry<K, V> e = i.next();
				if(key.equals(e.getKey()))
					correntEntry = e;
			}
		}
		
		V oldValue = null;
		if (correntEntry != null) {
			oldValue = correntEntry.getValue();
			i.remove();
		}
		return oldValue;
	}
	
	public void putAll(MyMap<? extends K, ? extends V> m) {
		for (MyMapEntry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}
	
	public void clear() {
		entrySet().clear();
	}
	
	transient Set<K>        keySet;
    transient Collection<V> values;
    
	public Set<K> keySet() {
		Set<K> ks = keySet;
		if (ks == null) {
			ks = new AbstractSet<K>() {
				public Iterator<K> iterator() {
					return new Iterator<K>() {
						private Iterator<MyMapEntry<K, V>> i = entrySet().iterator();

						public boolean hasNext() {
							return i.hasNext();
						}

						public K next() {
							return i.next().getKey();
						}
						
						public void remove() {
							i.remove();
						}
					};
				}

				public int size() {
					return AbstractMyMap.this.size();
				}
				
				public boolean isEmpty() {
					return AbstractMyMap.this.isEmpty();
				}
				
				public void clear() {
					AbstractMyMap.this.clear();
				}
				
				public boolean contains(Object k) {
					return AbstractMyMap.this.isContainsKey(k);
				}
			};
		}
		return ks;
	}

	
	public Collection<V> values() {
		Collection<V> vals = values;
		if (vals == null) {
			vals = new AbstractCollection<V>() {
				public Iterator<V> iterator() {
					return new Iterator<V>() {
						private Iterator<MyMapEntry<K, V>> i = entrySet().iterator();
						
						public boolean hasNext() {
							return i.hasNext();
						}
						
						public V next() {
							return i.next().getValue();
						}
						
						public void remove() {
							i.remove();
						}
					};
				}

				public int size() {
					return AbstractMyMap.this.size();
				}
				
				public boolean isEmpty() {
					return AbstractMyMap.this.isEmpty();
				}
				
				public void clear() {
					AbstractMyMap.this.clear();
				}
				
				public boolean contains(Object v) {
					return AbstractMyMap.this.isContainsValue(v);
				}
			};
			values = vals;
		}
		return vals;
	}

	
	public abstract Set<MyMapEntry<K,V>> entrySet();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
