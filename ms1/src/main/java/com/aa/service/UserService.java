package com.aa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.dao.UserMapper;
import com.aa.model.User;

@Service
public class UserService {
	
	
	@Autowired
	UserMapper userMapper;
	
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
