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

	public static void main(String[] args) {
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
}
