#  Java_1.8 中的HashMap
面试被问到很多次HashMap，面试后自己也是看了好多次，但总是没理解，这样好了，自己写下HashMap，从细节上理解它
[MyHashMap](https://github.com/kobe24167/Study/blob/master/test/src/com/aa/myMap/MyHashMap.java)
##  重要的常量：
	DEFAULT_INITIAL_CAPACITY 初始容量，默认16
	DEFAULT_LOAD_FACTOR 加载因子，默认0.75
	MAXIMUM_CAPACITY 最大容量是int的最大值，因为hashcode是int
	TREEIFY_THRESHOLD 链转成红黑树的阈值
##  数据结构：
	数组
	链表
	红黑树
##  put过程
	![put过程](https://github.com/kobe24167/Study/blob/master/images/HashMap_put.jpg)
## get过程
##  key可以为空的,hash值为0，所以放到第0位上
```java
	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
	
	//put
	if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
		e = p;
	}
```
##  扩容方法: n << 1,最多容纳Int.max个数组
	插入第12个，超出阈值（16*0.75），开始扩容
	扩容过程
	![扩容过程](https://github.com/kobe24167/Study/blob/master/images/HashMap_resize.jpg)
	init过程
	![init过程](https://github.com/kobe24167/Study/blob/master/images/HashMap_init.jpg)
##  加载因子大小问题
	越大，链表或树上的节点越多，越难查找
	越小，数组空的越多
##  迭代器iterator， Spliterator(多线程的情况下迭代同一个 HashMap)
##  红黑树左旋右旋，红黑树平衡
##  transient的使用
```java
	transient Node<K, V>[] table;
	transient Set<MyMapEntry<K, V>> entrySet;
```
	加了transient表示这些变量不会被序列化，但实际上HashMap是可以被序列化的，使用了以下方法，TODO Java序列化
```java
	private void writeObject(java.io.ObjectOutputStream s)
        throws IOException {
        int buckets = capacity();
        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();
        s.writeInt(buckets);
        s.writeInt(size);
        internalWriteEntries(s);
    }

    /**
     * Reconstitute the {@code HashMap} instance from a stream (i.e.,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        // Read in the threshold (ignored), loadfactor, and any hidden stuff
        s.defaultReadObject();
        reinitialize();
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new InvalidObjectException("Illegal load factor: " +
                                             loadFactor);
        s.readInt();                // Read and ignore number of buckets
        int mappings = s.readInt(); // Read number of mappings (size)
        if (mappings < 0)
            throw new InvalidObjectException("Illegal mappings count: " +
                                             mappings);
        else if (mappings > 0) { // (if zero, use defaults)
            // Size the table using given load factor only if within
            // range of 0.25...4.0
            float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f);
            float fc = (float)mappings / lf + 1.0f;
            int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ?
                       DEFAULT_INITIAL_CAPACITY :
                       (fc >= MAXIMUM_CAPACITY) ?
                       MAXIMUM_CAPACITY :
                       tableSizeFor((int)fc));
            float ft = (float)cap * lf;
            threshold = ((cap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY) ?
                         (int)ft : Integer.MAX_VALUE);

            // Check Map.Entry[].class since it's the nearest public type to
            // what we're actually creating.
            SharedSecrets.getJavaOISAccess().checkArray(s, Map.Entry[].class, cap);
            @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] tab = (Node<K,V>[])new Node[cap];
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
```
##  与LinkedHashMap的关系,有些不懂为什么要这样做，应该是为了新的红黑树，在LinkedHashMap也可以使用这个TreeNode
```java
	// code in HashMap
	static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }
	}
	// code in LinkedHashMap
	static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
	// code in HashMap
	static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
	}
```
	
##  延伸学习的内容
  * Java是如何序列化
  * 红黑树
