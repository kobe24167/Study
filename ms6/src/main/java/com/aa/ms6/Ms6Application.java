package com.aa.ms6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Ms6Application {
	
	@Autowired
	KafkaSender sender;
	
	@GetMapping("/")
	String home() {
        for (int i = 0; i < 3; i++) {
            //调用消息发送类中的消息发送方法
            sender.send();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		return "Spring is here!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Ms6Application.class, args);
	}

}
