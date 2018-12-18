package com.exmaple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leetCode.Solution;

@SpringBootApplication
public class ExmapleApplication {

	public static void main(String[] args) {
		Solution.main(args);
		SpringApplication.run(ExmapleApplication.class, args);
	}

}

