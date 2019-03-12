#  Java 中的锁

## 在并发编程中，我们通常会遇到以下三个问题：原子性问题，可见性问题，有序性问题
## 乐观锁
乐观锁是一种乐观思想，即认为读多写少，遇到并发写的可能性低，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，采取在写时先读出当前版本号，然后加锁操作（比较跟上一次的版本号，如果一样则更新），如果失败则要重复读-比较-写的操作。java中的乐观锁基本都是通过CAS操作实现的，CAS是一种更新的原子操作，比较当前值跟传入值是否一样，一样则更新，否则失败。

## 悲观锁
悲观锁是就是悲观思想，即认为写多，遇到并发写的可能性高，每次去拿数据的时候都认为别人会修改，所以每次在读写数据的时候都会上锁，这样别人想读写这个数据就会block直到拿到锁。java中的悲观锁就是Synchronized,AQS框架下的锁则是先尝试cas乐观锁去获取锁，获取不到，才会转换为悲观锁，如RetreenLock。
  
## 锁优化
减少锁的时间，减少锁的粒度

写写例子，尤其是StampedLock，不然会忘

##  synchronized的四种用法：
  * 修饰方法synchronized void method()
  * 修饰一个代码块synchronized(this) 指定要给某个对象加锁synchronized (object) {}
  * 修饰一个静态的方法synchronized static void method() {}
  * 修饰一个类synchronized(ClassName.class) {}
[自己写的例子](https://github.com/kobe24167/Study/blob/master/test/src/com/aa/test/LockerTester.java)

## volatile
  * 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
  * 禁止进行指令重排序，能在一定程度上保证有序性。
  * 不能保证原子性
  * 使用volatile必须具备以下2个条件
	1）对变量的写操作不依赖于当前值
　　2）该变量没有包含在具有其他变量的不变式中

## ReentrantLock和ReentrantReadWriteLock
	ReentrantLock和synchronized性能上差不多
  ReentrantReadWriteLock中的 readLock 和 writeLock 是互斥锁

## StampedLock 是Java8有的
synchronized是在JVM层面上实现的，不但可以通过一些监控工具监控synchronized的锁定，而且在代码执行时出现异常，JVM会自动释放锁定；
ReentrantLock、ReentrantReadWriteLock,、StampedLock都是对象层面的锁定，要保证锁定一定会被释放，就必须将unLock()放到finally{}中；
StampedLock 对吞吐量有巨大的改进，特别是在读线程越来越多的场景下；
StampedLock有一个复杂的API，对于加锁操作，很容易误用其他方法;
当只有少量竞争者的时候，synchronized是一个很好的通用的锁实现;
当线程增长能够预估，ReentrantLock是一个很好的通用的锁实现;
StampedLock 可以说是Lock的一个很好的补充，吞吐量以及性能上的提升足以打动很多人了，但并不是说要替代之前Lock的东西，毕竟他还是有些应用场景的，起码API比StampedLock容易入手
## 关于StampedLock中的一个bug:
StampedLock其内部是通过死循环+CAS操作的方式来修改状态位，在挂起线程时，是通过unsafe.park的方式，而对于中断的线程，unsafe.park会直接返回，而在StampedLock的死循环逻辑中，没有处理中断的逻辑，就会导致阻塞在park上的线程中断后，再次进入循环，直到当前死循环满足退出条件，因此整个过程会使cpu暴涨。
