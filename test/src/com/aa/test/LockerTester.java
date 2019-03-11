package com.aa.test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class LockerTester {
	private static int count;

	public synchronized static void add() {
		count++;
		System.out.println("count: " + count);
	}

	public static void counter() {
		ExecutorService service = Executors.newCachedThreadPool();

		class Counter implements Runnable {
			@Override
			public void run() {
				add();
			}
		}
		Counter c = new Counter();
		service.execute(c);
		service.execute(c);
		service.execute(c);
		service.execute(c);
		service.execute(c);
	}

	public static Map<String, String> taskMap = new LinkedHashMap<>();
	public static Map<String, String> taskResultMap = new LinkedHashMap<>();
	private static int resultCount;

	public static Map<String, String> successMap = new LinkedHashMap<>();
	public static Map<String, String> failedMap = new LinkedHashMap<>();

	private volatile static int successCount;
	private volatile static int failedCount;

	public synchronized static void updateResult(String taskId, String result) {
		System.out.println(taskId + ": " + result);
		resultCount++;
		taskResultMap.put(taskId, result);
		if (resultCount == taskMap.size()) {
			System.out.println("Success: " + successCount);
			System.out.println("SuccessMap: " + successMap.size());
			System.out.println("Failed: " + failedCount);
			System.out.println("FailedMap: " + failedMap.size());
			System.out.println("All Done");
		}
	}

	public static void synchronizedTester() {
		for (int i = 0; i < 50; i++) {
			taskMap.put("taskId " + i, "comand " + i);
		}

//		ExecutorService service = Executors.newCachedThreadPool();

		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(500);

		class Task implements Runnable {
			String taskId;
			String command;

			public Task(String taskId, String command) {
				this.taskId = taskId;
				this.command = command;
			}

			@Override
			public void run() {
				int result = (int) (1 & System.currentTimeMillis());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (result == 1) {
					successCount++;
					synchronized (successMap) {
						successMap.put(taskId, "Success");
					}
				} else {
					failedCount++;
					synchronized (failedMap) {
						failedMap.put(taskId, "Failed");
					}
				}
				System.out.println("Run task: " + command);
				updateResult(taskId, result == 1 ? "Success" : "Failed");
			}
		}

		ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 1, TimeUnit.SECONDS, queue);

		Set<Entry<String, String>> entries = taskMap.entrySet();
		Iterator<Entry<String, String>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			Task task = new Task(next.getKey(), next.getValue());
			executor.execute(task);
		}
		executor.shutdown();
		System.out.println("resultCount: " + resultCount);
	}

	public static void main(String[] args) {
		testMethod();
	}

	private static Lock lock = new ReentrantLock();
	private static Condition condition = lock.newCondition();

	public static void testMethod() {
		Task1 task = new Task1(lock, condition);
		Thread thread1 = new Thread(task);
		thread1.start();
		try {
			lock.lock();
			System.out.println("开始wait");
			condition.await();
			for (int i = 0; i < 5; i++) {
				System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
			}
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

	public static void reentrantReadWriteLockTester() {
		reentrantReadWriteLock.writeLock().lock();
		reentrantReadWriteLock.writeLock().newCondition();
		reentrantReadWriteLock.writeLock().unlock();
		
		//readLock 和 writeLock 是互斥锁
		reentrantReadWriteLock.readLock().lock();
		reentrantReadWriteLock.readLock().unlock();

	}

	private static StampedLock stampedLock = new StampedLock();

	public static void stampedLockTester() {
		long stamp;
		//乐观读锁
		stamp = stampedLock.tryOptimisticRead(); //获得一个乐观读锁
		if (!stampedLock.validate(stamp)) { //检查发出乐观读锁后同时是否有其他写锁发生？
			stamp = stampedLock.readLock(); //如果没有，我们再次获得一个读悲观锁
			stampedLock.unlockRead(stamp); //解锁
		}
		
		// 这里可以使用乐观读锁替换
        stamp = stampedLock.readLock();
        try {
            // 如果当前点在原点则移动
            while (true) {
                // 尝试将获取的读锁升级为写锁
                long ws = stampedLock.tryConvertToWriteLock(stamp);
                // 升级成功，则更新票据，并设置坐标值，然后退出循环
                if (ws != 0L) {
                    stamp = ws;
                }
                else {
                    // 读锁升级写锁失败则释放读锁，显示获取独占写锁，然后循环重试
                	stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        } finally {
        	stampedLock.unlock(stamp);
        }
		
	}

}

class Task1 implements Runnable {

	Lock lock;
	Condition condition;

	public Task1(Lock lock, Condition condition) {
		this.lock = lock;
		this.condition = condition;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.lock();
		System.out.println("开始signal");
		condition.signal();
		lock.unlock();
	}
}
