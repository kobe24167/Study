package com.aa.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aa.exception.SignUpException;
import com.aa.user.model.User;
import com.aa.user.service.IUserService;

@EnableAutoConfiguration
@RestController
public class UserController {
	
	@Reference(version = "${demo.service.version}")
    private IUserService userService;

	@RequestMapping(value = "/selectUserById")
	@ResponseBody
	public User selectUserById(@RequestParam(name = "id", required = false) String id) throws InterruptedException {
		if (id == null) {
			return null;
		}
		return userService.selectByPrimaryKey(id);
	}
	
	@RequestMapping(value = "/register")
	@ResponseBody
	public User register(@RequestBody User user) {
		return userService.register(user);
	}
}