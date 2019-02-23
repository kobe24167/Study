package com.la;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.la.msApi.User;

@RestController
public class Ms1Controller {
	@Autowired
	RestTemplate restTemplate;
	@RequestMapping("/api")
	public String printDate(@RequestHeader(name = "user-name", required = false) String username) {
		return restTemplate.getForObject("http://localhost:8002/ms2/api", String.class);
	}
	
	@Autowired
	UserConsumerService userService;
	
	@RequestMapping("/getAllUser")
	public List<User> getAllUser(@RequestHeader(name = "user-name", required = false) String username) {
		return userService.getAllUser();
	}
}
