package com.redshark.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redshark.core.CoreService;
import com.redshark.utils.SignUtil;
import com.redshark.utils.StringUtil;

/**
 * 
 * @Description:微信公众平台接入
 * @author ximing.fu
 * @time:2015年8月20日 下午2:06:28
 *
 */
public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * doGet 方法的作用正是确认请求是否来自于微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");
		if (!StringUtil.isBlank(flag)) {
			// 跳转到post请求
			doPost(request, response);
		} else {
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			System.out.println("signature:" + signature);
			System.out.println("timestamp:" + timestamp);
			System.out.println("nonce:" + nonce);
			System.out.println("echostr:" + echostr);

			PrintWriter out = response.getWriter();
			// 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				if (StringUtil.isBlank(echostr)) {

				} else {
					out.print(echostr);
				}
			}
			out.close();
			out = null;
		}
	}

	/*
	 * 微信服务器会将用户的请求通过doPost 方法发送给我们 <br>
	 * 
	 * request 中封装了请求相关的所有内容，可以从request 中取出微 信服务器发来的消息；而通过response
	 * 我们可以对接收到的消息进行响应，即发送消息。
	 * 
	 * 从微信公众平台接口消息指南中可以了解到，当用户向公众帐号发消息时，微信服务器会将 消息通过POST
	 * 方式提交给我们在接口配置信息中填写的URL，而我们就需要在URL 所指 向的请求处理类WechatServlet 的doPost
	 * 方法中接收消息、处理消息和响应消息。
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("转发中...");
		// 微信服务器POST 消息时用的是UTF-8 编码，在接收时也要用同样的编码，否则中文会乱码
		request.setCharacterEncoding("UTF-8");
		// 在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8
		response.setCharacterEncoding("UTF-8");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 反馈到微信服务器的内容
		String respContent = request.getParameter("respContent");
		// 页面参数
		/*String flag = request.getParameter("flag");
		String ids = request.getParameter("ids");
		System.out.println(flag+"===="+ids);
		if ("xxfb".equals(flag)) {
			BackwechatService backwechatService = (BackwechatService) SpringContextUtil.getBean("backwechatService");
			try {
				// xxfb : 根据id 封装值
				respContent = backwechatService.packRespcontent(ids);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		respContent = CoreService.processRequest(request);
		System.out.println("返回结果字符串:"+respContent);
		// 将消息的处理结果返回给用户
		PrintWriter out = response.getWriter();
		out.print(respContent);
		out.close();
		out = null;
	}
	
	@Override
	public void init() throws ServletException {
		try {
			// 从wechat.properties加载配置 默认class目录下
			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("wechat.properties");
			Properties p = new Properties();
			p.load(inputStream);
			Iterator<Entry<Object, Object>> it = p.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				if ("projectpath".equals(String.valueOf(entry.getKey()))) {
					CoreService.PROJECT_PATH = String.valueOf(entry.getValue());
				}
			}
		} catch (Exception e) {
			System.out.println("!!!注意：【wechat.properties配置文件加载失败】");
		}
		System.out.println("【wechat.properties配置文件加载成功】"+CoreService.PROJECT_PATH);
	}
	

}
