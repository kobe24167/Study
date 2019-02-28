package com.aa.ms3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.aa.ms3.dao")
@ComponentScan(basePackages = {"com.aa.ms3"})

public class Ms3Application {

	public static void main(String[] args) {
		SpringApplication.run(Ms3Application.class, args);
	}

}
