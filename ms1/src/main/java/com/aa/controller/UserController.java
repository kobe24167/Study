package com.aa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aa.model.User;
import com.aa.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/getUserById")
	@ResponseBody
	public User getUserById(@RequestParam(name = "id", required = false) Integer id) {
		return userService.selectByPrimaryKey(id);
	}
}
