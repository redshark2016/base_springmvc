package com.redshark.utils;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>Title: 工具类</p>
 * <p>Filename: StringUtil.java</p>
 * <p>Description: 字符串处理工具</p>
 * <p>Version: 1.0</p>
 * <p>Author: xianlu.lu 2008-11-12</p>
 * <p>Modify: </p>
 */
public class StringUtil {

	/**
	 * 检查字符串是否为数字字符串 返回值：true为是数字，false为不是数字
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		int i, j;
		String strTemp = "0123456789";

		if (str == null || str.length() == 0) {
			return false;
		}

		for (i = 0; i < str.length(); i++) {
			j = strTemp.indexOf(str.charAt(i));
			if (j == -1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 功能描述：是否为空白,包括null和""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0 || "null".equals(str);
	}

	/**
	 * 检查电子邮件合法性 true表示合法 false表示非法
	 * 
	 * @param String email
	 * @return boolean
	 */
	public static boolean checkEmailIsValid(String email) {

		boolean returnResult = false;

		if (email == null || email.equals("")) {
			returnResult = false;
		}

		for (int i = 1; i < email.length(); i++) {
			char s = email.charAt(i);
			if (s == '@') {
				returnResult = true;
				break;
			}
		}

		return returnResult;
	}

	/**
	 * 判断字符是否为空
	 * 
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("") || param.equals("null")) ? true : false;
	}

	/**
	 * 过滤掉为null的字符串
	 * 
	 * @param: String param
	 * @return: String
	 */
	public static String deNull(String param) {
		if (nullOrBlank(param))
			return "";
		return param.trim();
	}
	
	public static String deNull(Object param) {
		if (param == null)
			return "";
		return param.toString().trim();
	}
	
	
	/**
	 * uuid
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	public static void main(String[] args) {
		//System.out.println(getUUID());
		String str = "测试一下中文是否乱码，test is chn!";
		String ret = null;
		ret = new BASE64Encoder().encode(str.getBytes());
		System.out.println("加密前:"+str+" 加密后:"+ret);
		str = ret;
		try {
			ret = new String(new BASE64Decoder().decodeBuffer(str));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("解密前:"+str+" 解密后:"+ret);
		
	}
	
	/**
	 * 汉子转拼音
	 * @param name
	 * @return
	 */
	public static String HanyuToPinyin(String name) {
		name = deNull(name);
		String pinyinName = "";
		try {
			char[] nameChar = name.toCharArray();
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			for (int i = 0; i < nameChar.length; i++) {
				if (nameChar[i] > 128) {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} else {
					pinyinName += nameChar[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("汉子转拼音异常："+e.getMessage());
			return name;
		}
		return pinyinName;
	}
	
	/**
	 * 获取uuid字符
	 * @return
	 */
	public static String createUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	public static String getFromUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 解析获取html中的text文本内容
	 * @param html
	 * @return
	 */
	public static String getTextByhtml(String html) {
		try {
			Document doc = Jsoup.parse(html);
			return deNull(doc.body().text());
		} catch (Exception e) {
			return html;
		}
		
	}
	
	/**
	 * 产生一定长度的随机数
	 * 
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String creatRandom(int length) throws Exception {
		StringBuffer re = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			re.append(""+random.nextInt(10));
		}
		return re.toString();
	}
	

}
