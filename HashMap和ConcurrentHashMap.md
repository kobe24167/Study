#  Java_1.8 中的HashMap和ConcurrentHashMap的区别
没完呢
##  更多的常量：

```java
	/**
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * The value should be at least 4 * TREEIFY_THRESHOLD to avoid
     * conflicts between resizing and treeification thresholds.
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * Minimum number of rebinnings per transfer step. Ranges are
     * subdivided to allow multiple resizer threads.  This value
     * serves as a lower bound to avoid resizers encountering
     * excessive memory contention.  The value should be at least
     * DEFAULT_CAPACITY.
     */
    private static final int MIN_TRANSFER_STRIDE = 16;

    /**
     * The number of bits used for generation stamp in sizeCtl.
     * Must be at least 6 for 32bit arrays.
     */
    private static int RESIZE_STAMP_BITS = 16;

    /**
     * The maximum number of threads that can help resize.
     * Must fit in 32 - RESIZE_STAMP_BITS bits.
     */
    private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;

    /**
     * The bit shift for recording size stamp in sizeCtl.
     */
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
	
	static final int MOVED     = -1; // hash for forwarding nodes
	
	/** Number of CPUS, to place bounds on some sizings */
    static final int NCPU = Runtime.getRuntime().availableProcessors();
```

##  初始化较为简单，先需要设置sizeCtl为-1，为了防止多线程操作重复初始化
```java
		// 初始化要靠sizeCtl锁一下
        if ((sc = sizeCtl) < 0)
            Thread.yield(); // lost initialization race; just spin
        // CAS 一下，将 sizeCtl 设置为 -1，代表抢到了锁
        else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
		}
```

##  扩容 总是2的N次方
	
	扩容是将原来数组里的元素分批，分成多个任务包，多次transfer到新的数组里
	任务包中移动node的多少取决于数组大小和NCPU
	stride = (NCPU > 1) ? (n >>> 3) / NCPU : n;
	任务包最少有16个node，因为数组最少16个node，
	在put、get、remove、treeifyBin时帮助进行transfer
	
	* transferIndex属性
	扩容索引，表示已经分配给扩容线程的table数组索引位置。主要用来协调多个线程，并发安全地
	获取迁移任务（hash桶）。
	
	
	具体请看[链接](https://blog.csdn.net/varyall/article/details/81283231)[链接2](http://www.importnew.com/28263.html)
##  concurrencyLevel 并发数， JDK_7 最多可以同时支持 16 个线程并发写，JDK_8

## put过程
## get过程

##  数据结构：
	数组
	链表
	红黑树
##  key不能为空
```java
	if (key == null || value == null) throw new NullPointerException();
```
##  hash不同，使用再hash减少hash冲突
```java
	int hash = spread(key.hashCode());
	
	static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
```
##  用到的锁，JDK7以前是分段锁，JDK8之后使用的是unsafe
随处可以看到U, 大量使用了U.compareAndSwapXXX的方法，这个方法是利用一个CAS算法实现无锁化的修改值的操作，他可以大大降低锁代理的性能消耗。这个算法的基本思想就是不断地去比较当前内存中的变量值与你指定的一个变量值是否相等，如果相等，则接受你指定的修改的值，否则拒绝你的操作。因为当前线程中的值已经不是最新的值，你的修改很可能会覆盖掉其他线程修改的结果。这一点与乐观锁，SVN的思想是比较类似的。

unsafe代码块控制了一些属性的修改工作，比如最常用的SIZECTL 。在这一版本的concurrentHashMap中，大量应用来的CAS方法进行变量、属性的修改工作。利用CAS进行无锁操作，可以大大提高性能。
```java
	/**
     * Stripped-down version of helper class used in previous version,
     * declared for the sake of serialization compatibility
     */
    static class Segment<K,V> extends ReentrantLock implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;
        Segment(float lf) { this.loadFactor = lf; }
    }
	
	synchronized (f) {}
	
	volatile
```

##  延伸学习的内容
  * volatile和synchronized
  * volatile和synchronized场景
