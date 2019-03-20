#  Java_1.8 中的HashMap和HashTable的区别
Hashtable是java一开始发布时就提供的键值映射的数据结构，而HashMap产生于JDK1.2。
以下写的是HashTable相对于HashMap
##  HashTable没有用到红黑树，所以没有红黑树相关的常量、方法、node
##  Hashtable是线程安全的，它的每个方法中都加入了Synchronize方法
##  Hashtable还使用了Enumeration的方式迭代器,没有Spliterator
##  HashTable的key不允许为空，throw new NullPointerException()
##  扩容方法2n+1
```java
	//初始容量11，加载因子0.75
	public Hashtable() {
        this(11, 0.75f);
    }
	//最大容量
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	//扩容方法
		int newCapacity = (oldCapacity << 1) + 1;
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            if (oldCapacity == MAX_ARRAY_SIZE)
                // Keep running with MAX_ARRAY_SIZE buckets
                return;
            newCapacity = MAX_ARRAY_SIZE;
        }
```
##  计算hash值的方法不同
```java
		int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        @SuppressWarnings("unchecked")
        Entry<K,V> entry = (Entry<K,V>)tab[index];
```
##  延伸学习的内容
  * ConcurrentHashMap
