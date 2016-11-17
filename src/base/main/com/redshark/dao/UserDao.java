package com.redshark.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redshark.model.User;
/**
 * 
 * @author Administrator
 *
 */
@Repository
public interface UserDao {

	void save(User user);
	
	boolean update(User user);
	
	boolean delete(String id);
	
	User findById(String id);
	
	List<User> findAll();
	
	public void deleteBatch(String[] ids);
	
	@SuppressWarnings("unchecked")
	public List<User> getPageUserByPagination(HashMap map);

	/**
	 * 
	* @Title: getUserInfo  
	* @Description:根据用户名、密码查询用户
	* @param @param user
	* @param @return
	* @return User
	* @throws
	 */
	public User getUserInfo(User user);
	
}
