package com.la.ms1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@RestController
public class Ms1Controller {
	@Autowired
	RestTemplate restTemplate;
	@RequestMapping("/api")
	public String printDate(@RequestHeader(name = "user-name", required = false) String username) {
		return restTemplate.getForObject("http://localhost:8002/ms2/api", String.class);
	}
}
