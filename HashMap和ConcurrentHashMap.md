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
```
##  concurrencyLevel
##  数据结构：
	数组
	链表
	红黑树
##  key不能为空
```java
	if (key == null || value == null) throw new NullPointerException();
```
##  hash
```java
	int hash = spread(key.hashCode());
	
	static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
```
##  同步
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
```

##  延伸学习的内容
