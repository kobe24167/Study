package com.la;

import java.util.Date;

import org.apache.dubbo.spring.boot.demo.consumer.DemoService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

@EnableAutoConfiguration
@RestController
public class UserController {

  @RequestMapping("/api")
  public String printDate(@RequestHeader(name = "user-name", required = false) String username) {
    if (username != null) {
      return new Date().toString() + " " + username;
    }
    return new Date().toString();
  }
  
  @Reference(version = "${demo.service.version}", url = "${demo.service.url}")
  private DemoService demoService;
  
  @RequestMapping("/dubboTest")
  public String dubboTest(@RequestHeader(name = "user-name", required = false) String username) {
	  return demoService.sayHello("mercyblitz");
  }
}
