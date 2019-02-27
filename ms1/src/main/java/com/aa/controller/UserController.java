package com.aa.controller;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aa.model.User;
import com.aa.service.UserService;

@EnableAutoConfiguration
@RestController
public class UserController {
	
	@Resource
	UserService userService;

	@RequestMapping(value = "/getUserById")
	@ResponseBody
	public User getUserById(@RequestHeader(name = "id", required = false) Integer id) {
		return userService.selectByPrimaryKey(id);
	}
}
