package com.la;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.la.msApi.IUserService;
import com.la.msApi.User;

@Service
public class UserService implements IUserService{

	@Override
	public List<User> getAllUsers() {
		
		List<User> list = new ArrayList<>();
		User user1 = new User();
		user1.setId(1);
		user1.setName("User1");
		
		User user2 = new User();
		user2.setId(2);
		user2.setName("User2");
		
		list.add(user1);
		list.add(user2);
		return list;
	}

}
