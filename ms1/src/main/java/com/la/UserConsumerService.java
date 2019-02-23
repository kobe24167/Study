package com.la;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.la.msApi.IUserService;
import com.la.msApi.User;

@Component
public class UserConsumerService {
	
	@Reference
	IUserService userService;
	
	public List<User> getAllUser() {
		return userService.getAllUsers();
	}
}
