package com.redshark.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redshark.common.page.PageData;
import com.redshark.dao.UserDao;
import com.redshark.model.User;
import com.redshark.service.UserService;
import com.redshark.utils.PageDataManager;



@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao mapper;

	public boolean delete(String id) {
		
		return mapper.delete(id);
	}

	public List<User> findAll() {
		List<User> findAllList = mapper.findAll();
		return findAllList;
	}

	public User findById(String id) {

		User user = mapper.findById(id);
		
		return user;
	}

	public void save(User user) {

		mapper.save(user);
	}

	public boolean update(User user) {

		return mapper.update(user);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getPageUserByPagination(PageData<User> pageData) 
	{
		
		HashMap map = PageDataManager.condtionToMap(pageData);
		pageData.getPagination().setReadTotalCount(true);
		map.put("pagination", pageData.getPagination());
		
		return mapper.getPageUserByPagination(map);
	}

	@Override
	public User getUserInfo(String user_name, String password) {
		User user = new User();
		user.setUser_name(user_name);
		user.setPassword(password);
		return mapper.getUserInfo(user);
	}

}
