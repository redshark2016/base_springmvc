package com.redshark.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redshark.common.page.util.PageContext;
import com.redshark.model.User;
import com.redshark.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 获取所有用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllUser")
	public String getAllUser(HttpServletRequest request, String pageNo){
		/*PageData<User> p = new PageData<User>();
		p.setPageNo(1);
		p.setPageSize(3);
		Compositor compositor = new Compositor("id", Compositor.CompositorType.DESC);
		
		p.setCompositor(compositor);
		Filtration filtration1 = new Filtration("EQS_USER_NAME", "1"); 
		
		ArrayList filtrations = new ArrayList<Filtration>();
		p.setFiltrations(filtrations);
		filtrations.add(filtration1);
		List<User> list = userService.getPageUserByPagination (p);
		//把查询结果赋值给pageData
		p.setResult(list);*/
		PageContext.setIsNeedPagination(true);
		PageContext page = PageContext.getContext();  
		//String pageNo= request.getParameter("pageNo");  
	    if(null == pageNo){  
	    	page.setPageNo(1);  
		 } else{  
	          page.setPageNo(Integer.parseInt(pageNo));  
		 }  
		List<User> findAll = userService.findAll();
		page.setTotalPages(page.getTotalPage());
		request.setAttribute("userList", findAll);
		request.setAttribute("pagination", page);
		return "/user/allUser";
	}
	
	/**
	 * 跳转到添加用户界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddUser")
	public String toAddUser(HttpServletRequest request){
		
		return "/user/addUser";
	}
	/**
	 * 添加用户并重定向
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/addUser")
	public String addUser(User user,HttpServletRequest request){
		user.setActive_flag("1");
		userService.save(user);
		return "redirect:/user/getAllUser";
	}
	
	/**
	 *编辑用户
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(User user,HttpServletRequest request){
		
		
		if(userService.update(user)){
			user = userService.findById(user.getId());
			request.setAttribute("user", user);
			return "redirect:/user/getAllUser";
		}else{
			return "/error";
		}
	}
	/**
	 * 根据id查询单个用户
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUser")
	public String getUser(String id,HttpServletRequest request){
		
		request.setAttribute("user", userService.findById(id));
		return "/user/editUser";
	}
	/**
	 * 删除用户
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delUser")
	public void delUser(String id,HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"error\"}";
		if(userService.delete(id)){
			result = "{\"result\":\"success\"}";
		}
		response.setContentType("application/json");
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
