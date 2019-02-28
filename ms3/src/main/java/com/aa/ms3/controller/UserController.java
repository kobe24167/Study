package com.aa.ms3.controller;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aa.ms3.model.User;
import com.aa.ms3.service.UserService;

@EnableAutoConfiguration
@RestController
public class UserController {
	
	@Resource
	UserService userService;

	@RequestMapping(value = "/getUserById")
	@ResponseBody
	public User getUserById(@RequestParam(name = "id", required = false) Integer id) {
		User user = userService.selectByPrimaryKey(id);
		return user;
	}
}
