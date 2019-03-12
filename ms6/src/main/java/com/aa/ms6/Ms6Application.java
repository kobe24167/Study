package com.aa.ms6;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Ms6Application {

	public static void main(String[] args) {
		SpringApplication.run(Ms6Application.class, args);
	}

	@Autowired
	KafkaSender sender;

	@GetMapping("/")
	String home() {
		doPerformanceTest();
		return "200";
	}

	public boolean isRunning = true;
	public Map<String, User> map = new ConcurrentHashMap<>(1 << 10, 0.75f, 4);
	private int count = 0;
	public static final String BUSINESS_CODE = "50880";
	public static String DATE_CODE = "190312";
	public static final long millisecondOffset = 60 * 60 * 24 * 365 * 10;
	
	public static final int runTime = 1000;

	private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

	public synchronized String getNumber() {
		return String.format("%08d", count++);
	}

	public synchronized String getUserId() {
		reentrantReadWriteLock.readLock().lock();
		// new SimpleDateFormat("yyMMdd").format(new Date())
		String newId = BUSINESS_CODE + DATE_CODE + getNumber();
		reentrantReadWriteLock.readLock().unlock();
		return newId;
	}

	public void updateDateCode() {
		reentrantReadWriteLock.writeLock().lock();
		DATE_CODE = new SimpleDateFormat("yyMMdd").format(new Date());
		reentrantReadWriteLock.writeLock().unlock();
	}

	public static enum USER_TYPE {
		engineer, artist, reception
	}

	public void doPerformanceTest() {
		updateDateCode();
		class Task implements Runnable {

			String type;

			public Task(String type) {
				this.type = type;
			}

			@Override
			public void run() {
				while (isRunning) {
					// 类型
					System.out.println(type);
					// 姓名
					User user = new User(getUserId(), "name", type, (int) (Math.random() * 50));
					System.out.println(user.getId());
					// 生日
					// startDate = "1990-01-01 00:00:00";
					Date startDate = new Date(631123200000l);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date birthdayDate = null;
					BigDecimal birthday = new BigDecimal(startDate.getTime() / 1000)
							.add(new BigDecimal(Math.random()).multiply(new BigDecimal(millisecondOffset)));
					birthdayDate = new Date(birthday.longValue() * 1000);
					System.out.println(sdf.format(birthdayDate));
					// 密码
					try {
						String password = Base64.getEncoder().encodeToString(user.getId().getBytes("UTF-8"));
						System.out.println(password);
						System.out.println(new String(Base64.getDecoder().decode(password.getBytes()), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					map.put(user.getId(), user);
					sender.send(user);
				}
			}
		}

		class Stopper implements Runnable {

			public Stopper() {
			}

			@Override
			public void run() {
				try {
					Thread.sleep(runTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isRunning = false;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("map.size: " + map.size());

			}
		}

		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new Task(USER_TYPE.values()[0].name()));
		service.submit(new Task(USER_TYPE.values()[0].name()));
		service.submit(new Task(USER_TYPE.values()[1].name()));
		service.submit(new Task(USER_TYPE.values()[2].name()));
		service.submit(new Stopper());
	}
}
