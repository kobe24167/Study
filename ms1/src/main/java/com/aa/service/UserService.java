package com.aa.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aa.dao.UserMapper;
import com.aa.model.User;

@Service
public class UserService {
	
	@Resource
	UserMapper userMapper;
	
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
