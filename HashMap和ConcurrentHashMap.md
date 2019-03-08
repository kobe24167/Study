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

##  初始化较为简单，先需要设置sizeCtl为-1，为了防止多线程操作重复初始化
```java
		// 初始化的"功劳"被其他线程"抢去"了
        if ((sc = sizeCtl) < 0)
            Thread.yield(); // lost initialization race; just spin
        // CAS 一下，将 sizeCtl 设置为 -1，代表抢到了锁
        else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
		}
```

##  扩容是原来大小乘2
	加载因子是sc = n - (n >>> 2); // 0.75 * n
	
	
	主要用到了如下的东西，记录是否被迁移
```java
	//ForwardingNode 翻译过来就是正在被迁移的 Node
    //这个构造方法会生成一个Node，key、value 和 next 都为 null，关键是 hash 为 MOVED
    //后面我们会看到，原数组中位置 i 处的节点完成迁移工作后，
    //就会将位置 i 处设置为这个 ForwardingNode，用来告诉其他线程该位置已经处理过了
    //所以它其实相当于是一个标志。
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
	
	//ForwardingNode 保存了扩容后table的地址，在get的时候通过nextTable找到已经迁移的节点，
	//如果新的table中的节点也被迁移了，同样道理，找到ForwardingNode中下一个table的地址，找到节点位置
	static final class ForwardingNode<K,V> extends Node<K,V> {
        final Node<K,V>[] nextTable;
        ForwardingNode(Node<K,V>[] tab) {
            super(MOVED, null, null, null);
            this.nextTable = tab;
        }
	}
```
##  concurrencyLevel 并发数， JDK_7 最多可以同时支持 16 个线程并发写，JDK_8不是

##  数据结构：
	数组
	链表
	红黑树
##  key不能为空
```java
	if (key == null || value == null) throw new NullPointerException();
```
##  hash不同
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
  * volatile
  * 扩容迁移的时候是单线程的循环，可能有多个迁移同时运行，那用n个cpu干嘛
  * 迁移边界是什么
