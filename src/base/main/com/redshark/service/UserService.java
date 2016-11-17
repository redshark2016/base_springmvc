package com.redshark.service;

import java.util.List;

import com.redshark.common.page.PageData;
import com.redshark.model.User;


public interface UserService {
	void save(User user);
	boolean update(User user);
	boolean delete(String id);
	User findById(String id);
	List<User> findAll();
	
	List<User> getPageUserByPagination(PageData<User> pageData);
	
	
	User getUserInfo(String user_name, String password);
}

