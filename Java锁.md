#  Java 中的锁

##  synchronized的四种用法：
  * 修饰方法synchronized void method()
  * 修饰一个代码块synchronized(this) 指定要给某个对象加锁synchronized (object) {}
  * 修饰一个静态的方法synchronized static void method() {}
  * 修饰一个类synchronized(ClassName.class) {}
  