package com.aa.ms3.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aa.ms3.dao.UserDao;
import com.aa.ms3.model.User;

@Service
public class UserService {
	
	@Resource
	UserDao userMapper;
	
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
