package com.redshark.app.constants;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public abstract class AppConstant {
	
	/**
	 * 成功状态码
	 */
	public static final String SUCCESS_FLAG = "0";
	
	/**
	 * 失败状态码
	 */
	public static final String ERROR_FLAG = "1";	
	
	/**
	 * 默认页面数据数量
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;	
	
	/**
	 * 热门事项默认条数
	 */
	public static final int DEFAULT_HOT_SIZE = 5;	
	
	
	/**
	 * APP上传文件路径
	 */
	public static String APP_UPLOAD_PATH = "";	
	
	public static String APP_MANAGER_URL = "";
	
	public static String PARENT_ORG_ID = "";
	
	public static String APP_SUPER_ROLE = "";
	
	/**
	 * 首席代表
	 */
	public static String APP_COSTOM_ROLE = "";
	
	
	static{
		InputStream in = AppConstant.class.getClassLoader().getResourceAsStream("appConfig/config.properties");
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(in,"UTF-8"));
			APP_UPLOAD_PATH =  prop.getProperty("upload_path");
			APP_MANAGER_URL =  prop.getProperty("app_manager_url");
			PARENT_ORG_ID =  prop.getProperty("PARENT_ORG_ID");
			APP_SUPER_ROLE =  prop.getProperty("app_super_role");
			APP_COSTOM_ROLE =  prop.getProperty("app_costom_role");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
