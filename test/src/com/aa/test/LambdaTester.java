package com.aa.test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class LambdaTester {
	public static void main(String[] args) {
		Map<String, User> map = new LinkedHashMap<String, User>();
		map.put("1", new User("1", "1", "student", 15));
		map.put("2", new User("2", "2", "student", 23));
		map.put("3", new User("3", "3", "student", 15));
		map.put("4", new User("4", "4", "student", 26));
		map.put("5", new User("5", "5", "student", 25));

		map.put("6", new User("6", "6", "teacher", 60));
		map.put("7", new User("7", "7", "teacher", 70));
		map.put("8", new User("8", "8", "teacher", 80));
		
		
		Predicate<? super Entry<String, User>> ageFilter = (u) -> (u.getValue().getAge()>30);  
		map.entrySet().stream().filter(ageFilter);
		map.entrySet().stream().filter((u) -> (u.getValue().getAge()>30)).forEach((u) -> System.out.println(u.getValue().getName()));
		
		Comparator<Entry<String, User>> comparator = new Comparator<Map.Entry<String,User>>() {
			
			@Override
			public int compare(Entry<String, User> o1, Entry<String, User> o2) {
				if (o1.getValue().getAge() > o2.getValue().getAge()) {
					return -1;
				} else if (o1.getValue().getAge() < o2.getValue().getAge()) {
					return 1;
				} else 
					return 0;
			}
		}; 
		map.entrySet().stream().sorted(comparator).forEach((u) -> System.out.println(u.getValue().getName()));
		
		
		
		map.entrySet().stream().limit(5).forEach((u) -> System.out.println(u.getValue().getName()));
		
		map.entrySet().stream().max(comparator);
//		Iterator<Entry<String, User>> it = map.entrySet().iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next().getValue().getName());
//		}
	}
}
