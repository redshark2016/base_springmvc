package com.redshark.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redshark.model.User;
import com.redshark.service.UserService;
import com.redshark.utils.StringUtil;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/loginPage")
	public String loginPage(HttpServletRequest request){
		return "login";
	}
	
	/**
	 * 
	* @Title: login  
	* @Description:用户登录
	* @param @param request
	* @param @param user
	* @param @return
	* @return String
	* @throws
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request,String user_name,String password,String verify){
		String msg = "";
		if(null == request.getSession().getAttribute("user")){
			User user = userService.getUserInfo(user_name,password);
			if(null==user){
				msg = "用户名或密码错误！";
				request.setAttribute("msg", msg);
			}else{
				request.getSession().setAttribute("user", user);
			}
		}
		if(StringUtil.isBlank(msg)){
			return "index";
		}else{
			return "login";
		}
	}
	
}
