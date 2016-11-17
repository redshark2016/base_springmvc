package com.redshark.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @Description:HttpClient，url调用工具类
 * @author ximing.fu
 * @time:2015年7月20日 上午9:40:19
 *
 */
public class HttpClient {
	private static Logger log = Logger.getLogger(HttpClient.class);

	private DefaultHttpClient client = null;
	private String url = "";
	// 代理信息
	private String hostname = ""; // 代理地址ip：172.16.17.1
	private int port = 80; // 代理地址端口：80
	// 连接配置信息
	private static final int TIMEOUT = 10 * 60 * 1000;// 连接超时时间
	private static final int SO_TIMEOUT = 10 * 60 * 1000;// 数据传输超时

	public HttpClient() {
	}

	public HttpClient(String url) {
		this.url = url;
	}

	public HttpClient(String url, String hostname, int port) {
		this.url = url;
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * 初始化连接
	 */
	public void initHttpClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		cm.setMaxTotal(500);
		cm.setDefaultMaxPerRoute(200);

		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		// params.setParameter(CoreProtocolPNames.USER_AGENT, UA);
		// params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

		client = new DefaultHttpClient(cm, params);
	}

	/**
	 * 从url中获取返回信息
	 * 
	 * @param url
	 * @return
	 */
	public String get() {
		initHttpClient();
		log.info("get from url:" + url);
		HttpGet get = new HttpGet(url);
		HttpEntity entity = null;
		HttpResponse response = null;
		// 响应内容
		StringWriter sw = new StringWriter();
		try {
			// 设置代理
			if (!"".equals(hostname)) {
				HttpHost proxy = new HttpHost(hostname, port);
				client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
						proxy);
			}
			// 发送请求
			response = client.execute(get);
			// cookie信息
			List<Cookie> cookies = ((AbstractHttpClient) client)
					.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
				log.info("cookies is None");
			} else {
				log.info("cookies:");
				for (int i = 0; i < cookies.size(); i++) {
					log.info("- " + cookies.get(i).toString());
				}
			}
			// 响应内容
			entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				IOUtils.copy(is, sw, "utf-8");
				is.close();
				// 关闭 StringWriter 无效。此类中的方法在关闭该流后仍可被调用，而不会产生任何 IOException。
				sw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception get from url:" + url);
		} finally {
			get.abort();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(sw.toString());
		log.info("response:" + sw.toString());
		return sw.toString();
	}

	/**
	 * 发送 Post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public String post(MultipartEntity entity_) {
		log.info("post from url:" + url);
		initHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpEntity entity = null;
		String re = "";
		try {
			
			 /*client.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8);  
	    	   client.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);  
	    	   client.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);  
	    	   client.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,HTTP.UTF_8);  
	           
	           //设置post编码  
	           httppost.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8);  
	           httppost.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);  
	           httppost.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);  
	           httppost.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);  */
			
			// 设置参数
			//httppost.setEntity(new UrlEncodedFormEntity(params));
			// httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httppost.setEntity(entity_);
			//设置报文头  
			httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");  
			// 发送请求
			HttpResponse httpresponse = client.execute(httppost);
			// 获取返回数据
			entity = httpresponse.getEntity();
			re = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httppost.abort();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return re;
	}

	public static void main(String[] args) {
		// BasicConfigurator.configure(); // 加载默认配置
		// PropertyConfigurator.configure("src/main/resources/log4j.properties");
		// // 加载指定配置
		HttpClient client = new HttpClient(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb88c50a23899f877&secret=19a34ea01d43b14a8ac2bfca17c66fdb");
		String r = client.get();
		System.out.println(r);
	}

}
