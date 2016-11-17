package com.redshark.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redshark.dao.UserDao;
import com.redshark.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring-common.xml")
public class UserTest{

	@Autowired
	private UserDao userMapper;
	
	@Test
	public void testAdd(){
		User user = new User();
		for (int i=0;i<100000;i++){
			user.setUser_name("test"+i);
			user.setPassword("123456");
			user.setActive_flag("1");
			userMapper.save(user);
		}
	}
	
	@Test
	public void testFindAll(){
		List<User> findAllList = userMapper.findAll();
		System.out.println(findAllList.size());
	}
	
	@Test
	public void testFindById(){
		User user = userMapper.findById(2+"");
		System.out.println(user.getId());
		System.out.println(user.getUser_name());
	}


	@Test
	public void testUpdate(){
		
		User user = new User();
		user.setUser_name("test2");
		user.setIdcard("33333");
		user.setId("5");
		userMapper.update(user);
	}
	
	@Test
	public void testDelete(){
		userMapper.delete(1+"");
	}
}
