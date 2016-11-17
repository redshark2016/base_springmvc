package com.redshark.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redshark.core.CoreService;

/**
 * 
 * @Description:微信公众平台接入
 * @author ximing.fu
 * @time:2015年8月20日 下午2:06:28
 *
 */
public class InitwechatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化微信菜单
		System.out.println("======================微信菜单初始化 begin======================");
		try {
			String path_ = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath();
			
			CoreService.createMenu(path_);
		} catch (Exception e) {
			System.out.println("微信菜单初始化异常");
			e.printStackTrace();
		}
		System.out.println("======================微信菜单初始化 end======================");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

}
