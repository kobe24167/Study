package com.aa.myMap;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.HashMap.EntryIterator;
import java.util.HashMap.EntrySet;
import java.util.HashMap.EntrySpliterator;
import java.util.HashMap.KeyIterator;
import java.util.HashMap.KeySet;
import java.util.HashMap.KeySpliterator;
import java.util.HashMap.TreeNode;
import java.util.HashMap.ValueIterator;
import java.util.HashMap.ValueSpliterator;
import java.util.HashMap.Values;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import com.aa.myMap.MyHashMap.Node;

import sun.misc.SharedSecrets;

public class MyHashMap<K, V> extends AbstractMyMap<K, V> implements MyMap<K, V>, Cloneable, Serializable {

	private static final long serialVersionUID = 292303217387536076L;

	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

	static final int MAXIMUM_CAPACITY = 1 << 30;

	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	static final int TREEIFY_THRESHOLD = 8;

	static final int UNTREEIFY_THRESHOLD = 6;

	static final int MIN_TREEIFY_CAPACITY = 64;

	static class Node<K, V> implements MyMapEntry<K, V> {
		final int hash;
		final K key;
		V value;
		Node<K, V> next;

		Node(int hash, K key, V value, Node<K, V> next) {
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

	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	static Class<?> comparableClassFor(Object x) {
		if (x instanceof Comparable) {
			Class<?> c;
			Type[] ts, as;
			Type t;
			ParameterizedType p;
			if ((c = x.getClass()) == String.class) {
				return c;
			}
			if ((ts = c.getGenericInterfaces()) != null) {
				for (int i = 0; i < ts.length; ++i) {
					if (((t = ts[i]) instanceof ParameterizedType)
							&& ((p = (ParameterizedType) t).getRawType() == Comparable.class)
							&& (as = p.getActualTypeArguments()) != null && as.length == 1 && as[0] == c) {
						return c;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static int compareComparables(Class<?> kc, Object k, Object x) {
		return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x));
	}

	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	transient Node<K, V>[] table;
	transient Set<MyMapEntry<K, V>> entrySet;
	transient int size;
	transient int modeCount;
	int threshold;
	final float loadFactor;

	public MyHashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		}
		if (initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		}
		this.loadFactor = loadFactor;
		this.threshold = tableSizeFor(initialCapacity);
	}

	public MyHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	public MyHashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
	}

	public MyHashMap(MyMap<? extends K, ? extends V> m) {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		putMapEntries(m, false);
	}

	private void putMapEntries(MyMap<? extends K, ? extends V> m, boolean evict) {
		int s = m.size();
		if (s > 0) {
			if (table == null) {
				float ft = ((float) s / loadFactor) + 1.0F;
				int t = ((ft < (float) MAXIMUM_CAPACITY) ? (int) ft : MAXIMUM_CAPACITY);
				if (t > threshold)
					threshold = tableSizeFor(t);
			} else if (s > threshold) {
				resize();
			}
			for (MyMapEntry<? extends K, ? extends V> e : m.entrySet()) {
				K key = e.getKey();
				V value = e.getValue();
				putVal(hash(key), key, value, false, evict);
			}
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V get(Object key) {
		Node<K, V> e;
		return (e = getNode(hash(key), key)) == null ? null : e.value;
	}

	final Node<K, V> getNode(int hash, Object key) {
		Node<K, V>[] tab;
		Node<K, V> first;
		Node<K, V> e;
		int n;
		K k;
		if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
			if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k)))) {
				return first;
			}
			if ((e = first.next) != null) {
				if (first instanceof MyTreeNode) {
					return ((MyTreeNode<K, V>) first).getTreeNode(hash, key);
				}
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
						return e;
				} while ((e = e.next) != null);
			}
		}
		return null;
	}

	public boolean containsKey(Object key) {
		return getNode(hash(key), key) != null;
	}

	public V put(K key, V value) {
		return putVal(hash(key), key, value, false, true);
	}

	// TODO need do
	final V putVal(int hash, K key, V value, boolean onlyIfAbsnt, boolean evict) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, i;

		if ((tab = table) == null || (n = tab.length) == 0) {
			n = (tab = resize()).length;
		}
		if ((p = tab[i = (n - 1) & hash]) == null) {
			tab[i] = newNode(hash, key, value, null);
		} else {
			Node<K, V> e;
			K k;
			if (p.hash = hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
				e = p;
			} else if (p instanceof MyTreeNode) {
				e = ((MyTreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
			} else {
				for (int binCount = 0;; ++binCount) {
					if ((e = p.next) == null) {
						p.next = newNode(hash, key, value, null);
					}
				}
			}
		}
	}

	final Node<K, V>[] resize() {
		Node<K, V>[] oldTab = table;
		int oldCap = (oldTab == null) ? 0 : oldTab.length;
		int oldThr = threshold;
		int newCap, newThr = 0;
		if (oldCap > 0) {
			if (oldCap >= MAXIMUM_CAPACITY) {
				threshold = Integer.MAX_VALUE;
				return oldTab;
			} else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
				newThr = oldThr << 1;
			}
		} else if (oldThr > 0) {
			newCap = oldThr;
		} else {
			newCap = DEFAULT_INITIAL_CAPACITY;
			newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
		}
		if (newThr == 0) {
			float ft = (float) newCap * loadFactor;
			newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
		}

		threshold = newThr;

		@SuppressWarnings("unchecked")
		Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
		table = newTab;
		if (oldTab != null) {
			for (int j = 0; j < oldCap; ++j) {
				Node<K, V> e;
				if ((e = oldTab[j]) != null) {
					oldTab[j] = null;
					if (e.next == null) {
						newTab[e.hash & (newCap - 1)] = e;
					} else if (e instanceof MyTreeNode) {
						((MyTreeNode<K, V>) e).split(this, newTab, j, oldCap);
					} else {
						Node<K, V> loHead = null, loTail = null;
						Node<K, V> hiHead = null, hiTail = null;
						Node<K, V> next;
						do {
							next = e.next;
							if ((e.hash & oldCap) == 0) {
								if (loTail == null)
									loHead = e;
								else
									loTail.next = e;
								loTail = e;
							} else {
								if (hiTail == null)
									hiHead = e;
								else
									hiTail.next = e;
								hiTail = e;
							}
						} while ((e = next) != null);
						if (loTail != null) {
							loTail.next = null;
							newTab[j] = loHead;
						}
						if (hiTail != null) {
							hiTail.next = null;
							newTab[j + oldCap] = hiHead;
						}
					}
				}
			}
		}
		return newTab;
	}

	final void treeifyBin(Node<K, V>[] tab, int hash) {
		int n, index;
		Node<K, V> e;
		if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY) {
			resize();
		} else if ((e = tab[index = (n - 1) & hash]) != null) {
			MyTreeNode<K, V> hd = null, tl = null;
			do {
				MyTreeNode<K, V> p = replacementTreeNode(e, null);
				if (tl == null) {
					hd = p;
				} else {
					p.prev = tl;
					tl.next = p;
				}
				tl = p;
			} while ((e = e.next) != null);
			if ((tab[index] = hd) != null) {
				hd.treeify(tab);
			}
		}
	}

	public void putAll(MyMap<? extends K, ? extends V> m) {
		putMapEntries(m, true);
	}

	public V remove(Object key) {
		Node<K, V> e;
		return (e = removeNode(hash(key), key, null, false, true)) == null ? null : e.value;
	}

	final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, index;
		if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
			Node<K, V> node = null, e;
			K k;
			V v;
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
				node = p;
			else if ((e = p.next) != null) {
				if (p instanceof MyTreeNode)
					node = ((MyTreeNode<K, V>) p).getTreeNode(hash, key);
				else {
					do {
						if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
							node = e;
							break;
						}
						p = e;
					} while ((e = e.next) != null);
				}
			}
			if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
				if (node instanceof MyTreeNode)
					((MyTreeNode<K, V>) node).removeTreeNode(this, tab, movable);
				else if (node == p)
					tab[index] = node.next;
				else
					p.next = node.next;
				++modCount;
				--size;
				afterNodeRemoval(node);
				return node;
			}
		}
		return null;
	}

	public void clear() {
		Node<K, V>[] tab;
		modCount++;
		if ((tab = table) != null && size > 0) {
			size = 0;
			for (int i = 0; i < tab.length; ++i)
				tab[i] = null;
		}
	}

	public boolean containsValue(Object value) {
		Node<K, V>[] tab;
		V v;
		if ((tab = table) != null && size > 0) {
			for (int i = 0; i < tab.length; ++i) {
				for (Node<K, V> e = tab[i]; e != null; e = e.next) {
					if ((v = e.value) == value || (value != null && value.equals(v)))
						return true;
				}
			}
		}
		return false;
	}

	public Set<K> keySet() {
		Set<K> ks = keySet;
		if (ks == null) {
			ks = new MyKeySet();
			keySet = ks;
		}
		return ks;
	}

	final class MyKeySet extends AbstractSet<K> {
		public final int size() {
			return size;
		}

		public final void clear() {
			MyHashMap.this.clear();
		}

		public final Iterator<K> iterator() {
			return new KeyIterator();
		}

		public final boolean contains(Object o) {
			return containsKey(o);
		}

		public final boolean remove(Object key) {
			return removeNode(hash(key), key, null, false, true) != null;
		}

		public final Spliterator<K> spliterator() {
			return new KeySpliterator<>(MyHashMap.this, 0, -1, 0, 0);
		}

		public final void forEach(Consumer<? super K> action) {
			Node<K, V>[] tab;
			if (action == null)
				throw new NullPointerException();
			if (size > 0 && (tab = table) != null) {
				int mc = modCount;
				for (int i = 0; i < tab.length; ++i) {
					for (Node<K, V> e = tab[i]; e != null; e = e.next)
						action.accept(e.key);
				}
				if (modCount != mc)
					throw new ConcurrentModificationException();
			}
		}
	}

	public Collection<V> values() {
		Collection<V> vs = values;
		if (vs == null) {
			vs = new MyValues();
			values = vs;
		}
		return vs;
	}

	final class MyValues extends AbstractCollection<V> {
		public final int size() {
			return size;
		}

		public final void clear() {
			MyHashMap.this.clear();
		}

		public final Iterator<V> iterator() {
			return new ValueIterator();
		}

		public final boolean contains(Object o) {
			return containsValue(o);
		}

		public final Spliterator<V> spliterator() {
			return new ValueSpliterator<>(MyHashMap.this, 0, -1, 0, 0);
		}

		public final void forEach(Consumer<? super V> action) {
			Node<K, V>[] tab;
			if (action == null)
				throw new NullPointerException();
			if (size > 0 && (tab = table) != null) {
				int mc = modCount;
				for (int i = 0; i < tab.length; ++i) {
					for (Node<K, V> e = tab[i]; e != null; e = e.next)
						action.accept(e.value);
				}
				if (modCount != mc)
					throw new ConcurrentModificationException();
			}
		}
	}

	public Set<MyMapEntry<K, V>> entrySet() {
		Set<MyMapEntry<K, V>> es;
		return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
	}

	final class EntrySet extends AbstractSet<MyMapEntry<K, V>> {
		public final int size() {
			return size;
		}

		public final void clear() {
			MyHashMap.this.clear();
		}

		public final Iterator<MyMapEntry<K, V>> iterator() {
			return new EntryIterator();
		}

		public final boolean contains(Object o) {
			if (!(o instanceof MyMapEntry))
				return false;
			MyMapEntry<?, ?> e = (MyMapEntry<?, ?>) o;
			Object key = e.getKey();
			Node<K, V> candidate = getNode(hash(key), key);
			return candidate != null && candidate.equals(e);
		}

		public final boolean remove(Object o) {
			if (o instanceof MyMapEntry) {
				Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
				Object key = e.getKey();
				Object value = e.getValue();
				return removeNode(hash(key), key, value, true, true) != null;
			}
			return false;
		}

		public final Spliterator<MyMapEntry<K, V>> spliterator() {
			return new EntrySpliterator<>(MyHashMap.this, 0, -1, 0, 0);
		}

		public final void forEach(Consumer<? super MyMapEntry<K, V>> action) {
			Node<K, V>[] tab;
			if (action == null)
				throw new NullPointerException();
			if (size > 0 && (tab = table) != null) {
				int mc = modCount;
				for (int i = 0; i < tab.length; ++i) {
					for (Node<K, V> e = tab[i]; e != null; e = e.next)
						action.accept(e);
				}
				if (modCount != mc)
					throw new ConcurrentModificationException();
			}
		}
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		Node<K, V> e;
		return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return putVal(hash(key), key, value, true, true);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return removeNode(hash(key), key, value, true, true) != null;
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		Node<K, V> e;
		V v;
		if ((e = getNode(hash(key), key)) != null && ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) {
			e.value = newValue;
			afterNodeAccess(e);
			return true;
		}
		return false;
	}

	@Override
	public V replace(K key, V value) {
		Node<K, V> e;
		if ((e = getNode(hash(key), key)) != null) {
			V oldValue = e.value;
			e.value = value;
			afterNodeAccess(e);
			return oldValue;
		}
		return null;
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		if (mappingFunction == null)
			throw new NullPointerException();
		int hash = hash(key);
		Node<K, V>[] tab;
		Node<K, V> first;
		int n, i;
		int binCount = 0;
		MyTreeNode<K, V> t = null;
		Node<K, V> old = null;
		if (size > threshold || (tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length;
		if ((first = tab[i = (n - 1) & hash]) != null) {
			if (first instanceof MyTreeNode)
				old = (t = (MyTreeNode<K, V>) first).getTreeNode(hash, key);
			else {
				Node<K, V> e = first;
				K k;
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						old = e;
						break;
					}
					++binCount;
				} while ((e = e.next) != null);
			}
			V oldValue;
			if (old != null && (oldValue = old.value) != null) {
				afterNodeAccess(old);
				return oldValue;
			}
		}
		V v = mappingFunction.apply(key);
		if (v == null) {
			return null;
		} else if (old != null) {
			old.value = v;
			afterNodeAccess(old);
			return v;
		} else if (t != null)
			t.putTreeVal(this, tab, hash, key, v);
		else {
			tab[i] = newNode(hash, key, v, first);
			if (binCount >= TREEIFY_THRESHOLD - 1)
				treeifyBin(tab, hash);
		}
		++modCount;
		++size;
		afterNodeInsertion(true);
		return v;
	}

	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		if (remappingFunction == null)
			throw new NullPointerException();
		Node<K, V> e;
		V oldValue;
		int hash = hash(key);
		if ((e = getNode(hash, key)) != null && (oldValue = e.value) != null) {
			V v = remappingFunction.apply(key, oldValue);
			if (v != null) {
				e.value = v;
				afterNodeAccess(e);
				return v;
			} else
				removeNode(hash, key, null, false, true);
		}
		return null;
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		if (remappingFunction == null)
			throw new NullPointerException();
		int hash = hash(key);
		Node<K, V>[] tab;
		Node<K, V> first;
		int n, i;
		int binCount = 0;
		MyTreeNode<K, V> t = null;
		Node<K, V> old = null;
		if (size > threshold || (tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length;
		if ((first = tab[i = (n - 1) & hash]) != null) {
			if (first instanceof MyTreeNode)
				old = (t = (MyTreeNode<K, V>) first).getTreeNode(hash, key);
			else {
				Node<K, V> e = first;
				K k;
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						old = e;
						break;
					}
					++binCount;
				} while ((e = e.next) != null);
			}
		}
		V oldValue = (old == null) ? null : old.value;
		V v = remappingFunction.apply(key, oldValue);
		if (old != null) {
			if (v != null) {
				old.value = v;
				afterNodeAccess(old);
			} else
				removeNode(hash, key, null, false, true);
		} else if (v != null) {
			if (t != null)
				t.putTreeVal(this, tab, hash, key, v);
			else {
				tab[i] = newNode(hash, key, v, first);
				if (binCount >= TREEIFY_THRESHOLD - 1)
					treeifyBin(tab, hash);
			}
			++modCount;
			++size;
			afterNodeInsertion(true);
		}
		return v;
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		if (value == null)
			throw new NullPointerException();
		if (remappingFunction == null)
			throw new NullPointerException();
		int hash = hash(key);
		Node<K, V>[] tab;
		Node<K, V> first;
		int n, i;
		int binCount = 0;
		MyTreeNode<K, V> t = null;
		Node<K, V> old = null;
		if (size > threshold || (tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length;
		if ((first = tab[i = (n - 1) & hash]) != null) {
			if (first instanceof MyTreeNode)
				old = (t = (MyTreeNode<K, V>) first).getTreeNode(hash, key);
			else {
				Node<K, V> e = first;
				K k;
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						old = e;
						break;
					}
					++binCount;
				} while ((e = e.next) != null);
			}
		}
		if (old != null) {
			V v;
			if (old.value != null)
				v = remappingFunction.apply(old.value, value);
			else
				v = value;
			if (v != null) {
				old.value = v;
				afterNodeAccess(old);
			} else
				removeNode(hash, key, null, false, true);
			return v;
		}
		if (value != null) {
			if (t != null)
				t.putTreeVal(this, tab, hash, key, value);
			else {
				tab[i] = newNode(hash, key, value, first);
				if (binCount >= TREEIFY_THRESHOLD - 1)
					treeifyBin(tab, hash);
			}
			++modCount;
			++size;
			afterNodeInsertion(true);
		}
		return value;
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		Node<K, V>[] tab;
		if (action == null)
			throw new NullPointerException();
		if (size > 0 && (tab = table) != null) {
			int mc = modCount;
			for (int i = 0; i < tab.length; ++i) {
				for (Node<K, V> e = tab[i]; e != null; e = e.next)
					action.accept(e.key, e.value);
			}
			if (modCount != mc)
				throw new ConcurrentModificationException();
		}
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Node<K, V>[] tab;
		if (function == null)
			throw new NullPointerException();
		if (size > 0 && (tab = table) != null) {
			int mc = modCount;
			for (int i = 0; i < tab.length; ++i) {
				for (Node<K, V> e = tab[i]; e != null; e = e.next) {
					e.value = function.apply(e.key, e.value);
				}
			}
			if (modCount != mc)
				throw new ConcurrentModificationException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		HashMap<K, V> result;
		try {
			result = (HashMap<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}
		result.reinitialize();
		result.putMapEntries(this, false);
		return result;
	}

	// These methods are also used when serializing HashSets
	final float loadFactor() {
		return loadFactor;
	}

	final int capacity() {
		return (table != null) ? table.length : (threshold > 0) ? threshold : DEFAULT_INITIAL_CAPACITY;
	}

	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		int buckets = capacity();
		// Write out the threshold, loadfactor, and any hidden stuff
		s.defaultWriteObject();
		s.writeInt(buckets);
		s.writeInt(size);
		internalWriteEntries(s);
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		// Read in the threshold (ignored), loadfactor, and any hidden stuff
		s.defaultReadObject();
		reinitialize();
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new InvalidObjectException("Illegal load factor: " + loadFactor);
		s.readInt(); // Read and ignore number of buckets
		int mappings = s.readInt(); // Read number of mappings (size)
		if (mappings < 0)
			throw new InvalidObjectException("Illegal mappings count: " + mappings);
		else if (mappings > 0) { // (if zero, use defaults)
			// Size the table using given load factor only if within
			// range of 0.25...4.0
			float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f);
			float fc = (float) mappings / lf + 1.0f;
			int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ? DEFAULT_INITIAL_CAPACITY
					: (fc >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : tableSizeFor((int) fc));
			float ft = (float) cap * lf;
			threshold = ((cap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY) ? (int) ft : Integer.MAX_VALUE);

			// Check Map.Entry[].class since it's the nearest public type to
			// what we're actually creating.
			SharedSecrets.getJavaOISAccess().checkArray(s, MyMapEntry[].class, cap);
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Node<K, V>[] tab = (Node<K, V>[]) new Node[cap];
			table = tab;

			// Read the keys and values, and put the mappings in the HashMap
			for (int i = 0; i < mappings; i++) {
				@SuppressWarnings("unchecked")
				K key = (K) s.readObject();
				@SuppressWarnings("unchecked")
				V value = (V) s.readObject();
				putVal(hash(key), key, value, false, false);
			}
		}
	}
	
	

	Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
		return new Node<>(hash, key, value, next);
	}

	public static class MyTreeNode<K, V> extends MyLinkedHashMap.Entry<K, V> {
		MyTreeNode<K, V> parent;
		MyTreeNode<K, V> left;
		MyTreeNode<K, V> right;
		MyTreeNode<K, V> prev;
		boolean red;

		MyTreeNode(int hash, K key, V value, Node<K, V> next) {
			super(hash, key, value, next);
		}

		final MyTreeNode<K, V> root() {
			for (MyTreeNode<K, V> r = this, p;;) {
				if ((p = r.parent) == null) {
					return r;
				}
				r = p;
			}
		}

		static <K, V> void moveRootToFront(Node<K, V>[] tab, MyTreeNode<K, V> root) {
			int n;
			if (root != null && tab != null && (n = tab.length) > 0) {
				int index = (n - 1) & root.hash;
				MyTreeNode<K, V> first = (MyTreeNode<K, V>) tab[index];
				if (root != first) {
					Node<K, V> rn;
					tab[index] = root;
					MyTreeNode<K, V> rp = root.prev;
					if ((rn = root.next) != null) {
						((MyTreeNode<K, V>) rn).prev = rp;
					}
					if (rp != null) {
						rp.next = rn;
					}
					if (first != null) {
						first.prev = root;
					}
					root.next = first;
					root.prev = null;
				}
				assert checkInvariants(root);
			}
		}

		final MyTreeNode<K, V> find(int h, Object k, Class<?> kc) {
			MyTreeNode<K, V> p = this;
			do {
				int ph, dir;
				K pk;
				MyTreeNode<K, V> pl = p.left, pr = p.right, q;
				if ((ph = p.hash) > h)
					p = pl;
				else if (ph < h)
					p = pr;
				else if ((pk = p.key) == k || (k != null && k.equals(pk)))
					return p;
				else if (pl == null)
					p = pr;
				else if (pr == null)
					p = pl;
				else if ((kc != null || (kc = comparableClassFor(k)) != null)
						&& (dir = compareComparables(kc, k, pk)) != 0)
					p = (dir < 0) ? pl : pr;
				else if ((q = pr.find(h, k, kc)) != null)
					return q;
				else
					p = pl;
			} while (p != null);
			return null;
		}

		final MyTreeNode<K, V> getTreeNode(int h, Object k) {
			return ((parent != null) ? root() : this).find(h, k, null);
		}

		static int tieBreakOrder(Object a, Object b) {
			int d;
			if (a == null || b == null || (d = a.getClass().getName().compareTo(b.getClass().getName())) == 0) {
				d = (System.identityHashCode(a) <= System.identityHashCode(b) ? -1 : 1);
			}
			return d;
		}

		final void treeify(Node<K, V>[] tab) {
			MyTreeNode<K, V> root = null;
			for (MyTreeNode<K, V> x = this, next; x != null; x = next) {
				next = (MyTreeNode<K, V>) x.next;
				x.left = x.right = null;
				if (root == null) {
					x.parent = null;
					x.red = false;
					root = x;
				} else {
					K k = x.key;
					int h = x.hash;
					Class<?> kc = null;
					for (MyTreeNode<K, V> p = root;;) {
						int dir, ph;
						K pk = p.key;
						if ((ph = p.hash) > h) {
							dir = -1;
						} else if (ph < h) {
							dir = 1;
						} else if ((kc == null && (kc = comparableClassFor(k)) == null)
								|| (dir = compareComparables(kc, k, pk)) == 0) {
							dir = tieBreakOrder(k, pk);
						}

						MyTreeNode<K, V> xp = p;
						if ((p = (dir <= 0) ? p.left : p.left) == null) {
							x.parent = xp;
							if (dir <= 0) {
								xp.left = x;
							} else {
								xp.right = x;
							}
							root = balanceInsertion(root, x);
						}
					}
				}
			}
		}

		final Node<K, V> untreeify(MyHashMap<K, V> map) {
			Node<K, V> hd = null, tl = null;
			for (Node<K, V> q = this; q != null; q = q.next) {
				Node<K, V> p = map.replacementNode(q, null);
				if (tl == null) {
					hd = p;
				} else {
					tl.next = p;
				}
				tl = p;
			}
			return hd;
		}

		final MyTreeNode<K, V> putTreeVal(MyHashMap<K, V> map, Node<K, V>[] tab, int h, K k, V v) {
			Class<?> kc = null;
			boolean searched = false;
			MyTreeNode<K, V> root = (parent != null) ? root() : this;
			for (MyTreeNode<K, V> p = root;;) {
				int dir, ph;
				K pk;
				if ((ph = p.hash) > h)
					dir = -1;
				else if (ph < h)
					dir = 1;
				else if ((pk = p.key) == k || (k != null && k.equals(pk)))
					return p;
				else if ((kc == null && (kc = comparableClassFor(k)) == null)
						|| (dir = compareComparables(kc, k, pk)) == 0) {
					if (!searched) {
						MyTreeNode<K, V> q, ch;
						searched = true;
						if (((ch = p.left) != null && (q = ch.find(h, k, kc)) != null)
								|| ((ch = p.right) != null && (q = ch.find(h, k, kc)) != null))
							return q;
					}
					dir = tieBreakOrder(k, pk);
				}

				MyTreeNode<K, V> xp = p;
				if ((p = (dir <= 0) ? p.left : p.right) == null) {
					Node<K, V> xpn = xp.next;
					MyTreeNode<K, V> x = map.newTreeNode(h, k, v, xpn);
					if (dir <= 0)
						xp.left = x;
					else
						xp.right = x;
					xp.next = x;
					x.parent = x.prev = xp;
					if (xpn != null)
						((MyTreeNode<K, V>) xpn).prev = x;
					moveRootToFront(tab, balanceInsertion(root, x));
					return null;
				}
			}
		}

		final void removeTreeNode(MyHashMap<K, V> map, Node<K, V>[] tab, boolean movable) {
			int n;
			if (tab == null || (n = tab.length) == 0)
				return;
			int index = (n - 1) & hash;
			MyTreeNode<K, V> first = (MyTreeNode<K, V>) tab[index], root = first, rl;
			MyTreeNode<K, V> succ = (MyTreeNode<K, V>) next, pred = prev;
			if (pred == null)
				tab[index] = first = succ;
			else
				pred.next = succ;
			if (succ != null)
				succ.prev = pred;
			if (first == null)
				return;
			if (root.parent != null)
				root = root.root();
			if (root == null || root.right == null || (rl = root.left) == null || rl.left == null) {
				tab[index] = first.untreeify(map); // too small
				return;
			}
			MyTreeNode<K, V> p = this, pl = left, pr = right, replacement;
			if (pl != null && pr != null) {
				MyTreeNode<K, V> s = pr, sl;
				while ((sl = s.left) != null) // find successor
					s = sl;
				boolean c = s.red;
				s.red = p.red;
				p.red = c; // swap colors
				MyTreeNode<K, V> sr = s.right;
				MyTreeNode<K, V> pp = p.parent;
				if (s == pr) { // p was s's direct parent
					p.parent = s;
					s.right = p;
				} else {
					MyTreeNode<K, V> sp = s.parent;
					if ((p.parent = sp) != null) {
						if (s == sp.left)
							sp.left = p;
						else
							sp.right = p;
					}
					if ((s.right = pr) != null)
						pr.parent = s;
				}
				p.left = null;
				if ((p.right = sr) != null)
					sr.parent = p;
				if ((s.left = pl) != null)
					pl.parent = s;
				if ((s.parent = pp) == null)
					root = s;
				else if (p == pp.left)
					pp.left = s;
				else
					pp.right = s;
				if (sr != null)
					replacement = sr;
				else
					replacement = p;
			} else if (pl != null)
				replacement = pl;
			else if (pr != null)
				replacement = pr;
			else
				replacement = p;
			if (replacement != p) {
				MyTreeNode<K, V> pp = replacement.parent = p.parent;
				if (pp == null)
					root = replacement;
				else if (p == pp.left)
					pp.left = replacement;
				else
					pp.right = replacement;
				p.left = p.right = p.parent = null;
			}

			MyTreeNode<K, V> r = p.red ? root : balanceDeletion(root, replacement);

			if (replacement == p) { // detach
				MyTreeNode<K, V> pp = p.parent;
				p.parent = null;
				if (pp != null) {
					if (p == pp.left)
						pp.left = null;
					else if (p == pp.right)
						pp.right = null;
				}
			}
			if (movable)
				moveRootToFront(tab, r);
		}

		final void split(MyHashMap<K, V> map, Node<K, V>[] tab, int index, int bit) {
			MyTreeNode<K, V> b = this;
			// Relink into lo and hi lists, preserving order
			MyTreeNode<K, V> loHead = null, loTail = null;
			MyTreeNode<K, V> hiHead = null, hiTail = null;
			int lc = 0, hc = 0;
			for (MyTreeNode<K, V> e = b, next; e != null; e = next) {
				next = (MyTreeNode<K, V>) e.next;
				e.next = null;
				if ((e.hash & bit) == 0) {
					if ((e.prev = loTail) == null)
						loHead = e;
					else
						loTail.next = e;
					loTail = e;
					++lc;
				} else {
					if ((e.prev = hiTail) == null)
						hiHead = e;
					else
						hiTail.next = e;
					hiTail = e;
					++hc;
				}
			}

			if (loHead != null) {
				if (lc <= UNTREEIFY_THRESHOLD)
					tab[index] = loHead.untreeify(map);
				else {
					tab[index] = loHead;
					if (hiHead != null) // (else is already treeified)
						loHead.treeify(tab);
				}
			}
			if (hiHead != null) {
				if (hc <= UNTREEIFY_THRESHOLD)
					tab[index + bit] = hiHead.untreeify(map);
				else {
					tab[index + bit] = hiHead;
					if (loHead != null)
						hiHead.treeify(tab);
				}
			}
		}

		static <K, V> MyTreeNode<K, V> rotateLeft(MyTreeNode<K, V> root, MyTreeNode<K, V> p) {
			MyTreeNode<K, V> r, pp, rl;
			if (p != null && (r = p.right) != null) {
				if ((rl = p.right = r.left) != null)
					rl.parent = p;
				if ((pp = r.parent = p.parent) == null)
					(root = r).red = false;
				else if (pp.left == p)
					pp.left = r;
				else
					pp.right = r;
				r.left = p;
				p.parent = r;
			}
			return root;
		}

		static <K, V> MyTreeNode<K, V> rotateRight(MyTreeNode<K, V> root, MyTreeNode<K, V> p) {
			MyTreeNode<K, V> l, pp, lr;
			if (p != null && (l = p.left) != null) {
				if ((lr = p.left = l.right) != null)
					lr.parent = p;
				if ((pp = l.parent = p.parent) == null)
					(root = l).red = false;
				else if (pp.right == p)
					pp.right = l;
				else
					pp.left = l;
				l.right = p;
				p.parent = l;
			}
			return root;
		}

		static <K, V> MyTreeNode<K, V> balanceInsertion(MyTreeNode<K, V> root, MyTreeNode<K, V> x) {
			x.red = true;
			for (MyTreeNode<K, V> xp, xpp, xppl, xppr;;) {
				if ((xp = x.parent) == null) {
					x.red = false;
					return x;
				} else if (!xp.red || (xpp = xp.parent) == null)
					return root;
				if (xp == (xppl = xpp.left)) {
					if ((xppr = xpp.right) != null && xppr.red) {
						xppr.red = false;
						xp.red = false;
						xpp.red = true;
						x = xpp;
					} else {
						if (x == xp.right) {
							root = rotateLeft(root, x = xp);
							xpp = (xp = x.parent) == null ? null : xp.parent;
						}
						if (xp != null) {
							xp.red = false;
							if (xpp != null) {
								xpp.red = true;
								root = rotateRight(root, xpp);
							}
						}
					}
				} else {
					if (xppl != null && xppl.red) {
						xppl.red = false;
						xp.red = false;
						xpp.red = true;
						x = xpp;
					} else {
						if (x == xp.left) {
							root = rotateRight(root, x = xp);
							xpp = (xp = x.parent) == null ? null : xp.parent;
						}
						if (xp != null) {
							xp.red = false;
							if (xpp != null) {
								xpp.red = true;
								root = rotateLeft(root, xpp);
							}
						}
					}
				}
			}
		}

		static <K, V> MyTreeNode<K, V> balanceDeletion(MyTreeNode<K, V> root, MyTreeNode<K, V> x) {
			for (MyTreeNode<K, V> xp, xpl, xpr;;) {
				if (x == null || x == root)
					return root;
				else if ((xp = x.parent) == null) {
					x.red = false;
					return x;
				} else if (x.red) {
					x.red = false;
					return root;
				} else if ((xpl = xp.left) == x) {
					if ((xpr = xp.right) != null && xpr.red) {
						xpr.red = false;
						xp.red = true;
						root = rotateLeft(root, xp);
						xpr = (xp = x.parent) == null ? null : xp.right;
					}
					if (xpr == null)
						x = xp;
					else {
						MyTreeNode<K, V> sl = xpr.left, sr = xpr.right;
						if ((sr == null || !sr.red) && (sl == null || !sl.red)) {
							xpr.red = true;
							x = xp;
						} else {
							if (sr == null || !sr.red) {
								if (sl != null)
									sl.red = false;
								xpr.red = true;
								root = rotateRight(root, xpr);
								xpr = (xp = x.parent) == null ? null : xp.right;
							}
							if (xpr != null) {
								xpr.red = (xp == null) ? false : xp.red;
								if ((sr = xpr.right) != null)
									sr.red = false;
							}
							if (xp != null) {
								xp.red = false;
								root = rotateLeft(root, xp);
							}
							x = root;
						}
					}
				} else { // symmetric
					if (xpl != null && xpl.red) {
						xpl.red = false;
						xp.red = true;
						root = rotateRight(root, xp);
						xpl = (xp = x.parent) == null ? null : xp.left;
					}
					if (xpl == null)
						x = xp;
					else {
						MyTreeNode<K, V> sl = xpl.left, sr = xpl.right;
						if ((sl == null || !sl.red) && (sr == null || !sr.red)) {
							xpl.red = true;
							x = xp;
						} else {
							if (sl == null || !sl.red) {
								if (sr != null)
									sr.red = false;
								xpl.red = true;
								root = rotateLeft(root, xpl);
								xpl = (xp = x.parent) == null ? null : xp.left;
							}
							if (xpl != null) {
								xpl.red = (xp == null) ? false : xp.red;
								if ((sl = xpl.left) != null)
									sl.red = false;
							}
							if (xp != null) {
								xp.red = false;
								root = rotateRight(root, xp);
							}
							x = root;
						}
					}
				}
			}
		}

		static <K, V> boolean checkInvariants(MyTreeNode<K, V> t) {
			MyTreeNode<K, V> tp = t.parent, tl = t.left, tr = t.right, tb = t.prev, tn = (MyTreeNode<K, V>) t.next;
			if (tb != null && tb.next != t)
				return false;
			if (tn != null && tn.prev != t)
				return false;
			if (tp != null && t != tp.left && t != tp.right)
				return false;
			if (tl != null && (tl.parent != t || tl.hash > t.hash))
				return false;
			if (tr != null && (tr.parent != t || tr.hash < t.hash))
				return false;
			if (t.red && tl != null && tl.red && tr != null && tr.red)
				return false;
			if (tl != null && !checkInvariants(tl))
				return false;
			if (tr != null && !checkInvariants(tr))
				return false;
			return true;
		}

	}
}
