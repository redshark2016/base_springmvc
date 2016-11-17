package com.redshark.common.page.util;

import com.redshark.common.page.Pagination;

/**
 * 这是一个与线程相关的分页信息类，保存了当前运行线程的分页状态
 * @author jianyue.yan
 * @see Pagination;
 * @version 1.0
 */
public class PageContext extends Pagination
{
	//分页信息的threadLocal类，保存当前线程的分页信息。
	private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();
	
	//是否需要分页的threadLocal类，保存当前线程的是否需要分页的信息
	private static ThreadLocal<Boolean> isNeedPagination = new ThreadLocal<Boolean>();
	
	/**
	 * 返回当前线程的分页信息(信息中没有包含是否需要分页，需要调方法{@link #setIsNeedPagination}进行设置)
	 * @return
	 */
	public static PageContext getContext()
	{
		PageContext ci = context.get();
		if(ci == null)
		{
			ci = new PageContext();
			context.set(ci);
		}
		return ci;
	}
	
	/**
	 * 设置是否需要分页
	 * @param b b为true是表示需要分页
	 */
	public static void setIsNeedPagination(boolean b)
	{
		isNeedPagination.set(b);
	}
	
	/**
	 * 获取是否需要分页
	 * @return 返回true时表示需要分页
	 */
	public static boolean getIsNeedPagination()
	{
		Boolean result = isNeedPagination.get();
		if(result == null)
		{
			result = false;
			isNeedPagination.set(result);
		}
		return result;
	}
	
	/**
	 * 移除当前线程的分页设置。
	 */
	public  static void removeContext()
	{
		isNeedPagination.remove();
		context.remove();
	}
}
