package com.redshark.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Title: 工具类</p>
 * <p>Filename: DateUtil.java</p>
 * <p>Description: 时间处理工具</p>
 * <p>Version: 1.0</p>
 * <p>Author: xianlu.lu 2008-11-12</p>
 * <p>Modify:</p>
 */
public class DateUtil {

	/**
	 * 获得当前时间（日期时间型）
	 * 
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Timestamp getDateTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDateTime = simpleDateFormat.format(calendar.getTime());
		return (java.sql.Timestamp.valueOf(strDateTime));
	}

	/**
	 * 获得当前时间（日期时间型）
	 * 
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Timestamp StrToDateTime(String sDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		Timestamp ts = null;
		try {
			ts = new Timestamp(formatter.parse(sDate).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 获得当前时间（日期时间型）
	 * 
	 * @return String
	 */
	public static String getDateTimeString() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDateTime = simpleDateFormat.format(calendar.getTime());
		return strDateTime;
	}

	/**
	 * 获得当前日期
	 * 
	 * @return String
	 */
	public static String getDateString() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDateTime = simpleDateFormat.format(calendar.getTime());
		return strDateTime;
	}

	/**
	 * 获得当前日期（日期型）
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date getDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(cal.getTime());
		return (java.sql.Date.valueOf(strDate));
	}

	/**
	 * 获得当前日期（日期型）
	 * 
	 * @return String
	 */
	public static String getOnlyDateString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String strDate = formatter.format(cal.getTime());
		return strDate;
	}

	/**
	 * 获得当前日期（日期型）
	 * 
	 * @return String
	 */
	public static String getOnlyTimeString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String strDate = formatter.format(cal.getTime());
		return strDate;
	}

	public static String getWeeK() {

		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 获得当前日期前一天日期（日期型）
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date getPreviousDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(java.util.Calendar.DATE, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime = formatter.format(cal.getTime());
		return (java.sql.Date.valueOf(mDateTime));
	}

	/**
	 * 根据当前间时生成不同的问候语（欢迎词）
	 * 
	 * @return String
	 */
	public static String getWelcome() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
		String strTmp = simpleDateFormat.format(calendar.getTime());

		int intHour = 0;

		try {
			intHour = Integer.parseInt(strTmp);
		} catch (Exception e) {
			intHour = -1;
		}

		strTmp = "您好！";
		if (intHour >= 0 && intHour < 7) {
			strTmp = "夜猫子，要注意身体哦！ ";
		} else if (intHour >= 7 && intHour < 10) {
			strTmp = "早上好！ ";
		} else if (intHour >= 10 && intHour < 12) {
			strTmp = "上午好！ ";
		} else if (intHour >= 12 && intHour < 14) {
			strTmp = "中午好，注意休息！";
		} else if (intHour >= 14 && intHour < 18) {
			strTmp = "祝您下午工作愉快！";
		} else if (intHour >= 18 && intHour < 22) {
			strTmp = "加班辛苦了！";
		} else if (intHour >= 22 && intHour < 24) {
			strTmp = "您应该休息了！";
		}

		return strTmp;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public static String getWelcomeDate() {
		Date dateinclude = new Date();// 取时间
		Calendar calendarinclude = new GregorianCalendar();
		calendarinclude.setTime(dateinclude);
		int hours = dateinclude.getHours();
		String halfday = "";
		if (hours > 0 && hours < 12) {
			halfday = "上午";
		} else {
			halfday = "下午";
		}

		dateinclude = calendarinclude.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatterinclude = new SimpleDateFormat("yyyy年MM月dd日");
		String dateString = formatterinclude.format(dateinclude);
		int weekint = calendarinclude.get(Calendar.DAY_OF_WEEK);
		if (weekint == 1) {
			dateString += " 星期日";
		} else if (weekint == 2) {
			dateString += " 星期一";
		} else if (weekint == 3) {
			dateString += " 星期二";
		} else if (weekint == 4) {
			dateString += " 星期三";
		} else if (weekint == 5) {
			dateString += " 星期四";
		} else if (weekint == 6) {
			dateString += " 星期五";
		} else if (weekint == 7) {
			dateString += " 星期六";
		}

		return dateString;
	}

	/**
	 * 判断是否为有效的日期
	 * 
	 * @param String strDate
	 * @return boolean true:有效的 false:无效的
	 */
	public static boolean isValidDate(String strDate) {
		try {
			java.sql.Date.valueOf(strDate);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * 判断是否为有效的日期时间
	 * 
	 * @param String strDateTime
	 * @return boolean true:有效的 false:无效的
	 */
	public static boolean isValidDateTime(String strDateTime) {
		try {
			java.sql.Timestamp.valueOf(strDateTime);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * 获得当前日期（日期型）
	 * 
	 * @return String
	 */
	public static String getUnsignedDateString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String strDate = formatter.format(cal.getTime());
		return strDate;
	}

	/**
	 * 获得当前日期（日期型）
	 * 
	 * @return String
	 */
	public static String getYearString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String strDate = formatter.format(cal.getTime());
		return strDate;
	}

	public static String getYMDDateString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(cal.getTime());
		return strDate;
	}

	public static String dateToStr(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		return strDate;
	}
	
	public static String dateToStr2(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formatter.format(date);
		return strDate;
	}
	public static String TimestampToStr(Timestamp date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		return strDate;
	}
	/**
	 * 获得当前日期 +n天
	 * @param n
	 * @return
	 */
	public static String getDateByN(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal.getTime());
	}
	
	/**
	 * 获得当前时间（日期时间型）
	 * 
	 * @return java.sql.Timestamp
	 */
	public static String timestamp2Str(java.sql.Timestamp ts) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(ts.getTime());
	}
	
	/**
	 * 
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用。<br>
	 * 
	 * @param date
	 *            指定的日期
	 * 
	 * @param format
	 *            日期格式字符串
	 * 
	 * @return String 指定格式的日期字符串.
	 */

	public static String getFormatDateTime(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);

	}
	
	/**
	 * yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getCurrentDateTimesss() {

		return getFormatDateTime(new Date(), "yyyyMMddHHmmssSSS");

	}
}
