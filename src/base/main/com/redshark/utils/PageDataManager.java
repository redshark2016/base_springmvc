package com.redshark.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.redshark.common.page.Filtration;
import com.redshark.common.page.PageData;
import com.redshark.common.page.Pagination;

/**
 * 用作处理分页信息的公共类
 * @author zhengxing
 * @see PageData; Filtration;Pagination;Compositor
 * @version 1.0
 * 
 */
public class PageDataManager 
{
	public static String NUM_PERPAGE = "numPerpage";//每页数据记录条数
	
	public static String STARTLINE = "startline";//从第几条开始查询
	
	public static String ORDER_CONDITION = "orderConditon";//排序条件
	
	public static String SEARCH_CONITION_SINGLE = "singleCondition";//包含单个查询条件的查询条件 的列表
	
	public static String SEARCH_CONITION_MULTI = "multiCondition";//包含多个查询条件的 查询条件 的列表
	
	/**
	 * 获取pageDate中的参数，包含分页，查询条件，排序条件，不建议使用该方法。
	 * @param pageData
	 * @return
	 */
	@Deprecated
	public static HashMap<String, Object> pagaDataToMap(PageData<?> pageData)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();//返回的查询条件
		getPageInfo(pageData.getPagination(), resultMap);//获取分页信息
		getOrderCondition(pageData, resultMap);//获取排序信息
		getSearcherCondition(pageData.getFiltrations(), resultMap);//获取查询条件
		return resultMap;
	}
	
	/**
	 * 将分页数据信息保存至查询条件中
	 * @param pageination
	 * @param map
	 */
	private static void getPageInfo(Pagination pageination, HashMap<String, Object> map)
	{
		//如果pageination为空，不处理。
		if(null == pageination)
		{
			return;
		}
		
		int numPerpage = pageination.getPageSize();
		//每页数据量小于等于零，不处理。
		if(numPerpage <= 0)
		{
			return;
		}
		
		int pageNo = pageination.getPageNo() <= 1 ? 1 : pageination.getPageNo();
		//都不为空且大于0，则查询第pageNo页,numPerpage条。
		int startLine = (pageNo - 1) * numPerpage;
		map.put(NUM_PERPAGE, numPerpage);
		map.put(STARTLINE, startLine);
	}
	
	/**
	 * 获取排序条件
	 * @param pageData
	 * @param map
	 */
	private static void getOrderCondition(PageData<?> pageData, HashMap<String, Object> map)
	{
		//当排序条件为空时返回
		if(null == pageData.getCompositor())
		{
			return;
		}
		//把排序条件放入列表中，以待支持对字段排序。
		List<Object> list = new ArrayList<Object>();//排序列表，从最开始排起
		list.add(pageData.getCompositor());
		map.put(ORDER_CONDITION, list);
	}
	
	/**
	 * 获取查询条件，其中单个filtration中包含多个查询条件的作为一个列表。filtration中只包含单个查询条件的作为一个列表。
	 * @param list
	 * @param map
	 */
	private static void getSearcherCondition(List<Filtration> list, HashMap<String, Object> map)
	{
		//当查询条件为空时返回。
		if(null == list || list.size() == 0)
		{
			return;
		}
		
		ArrayList<Filtration> singleCondition = null;//单个查询条件的filtration列表
		ArrayList<Filtration> multiCondition = null;//多个查询条件的filtration列表
		
		for(Filtration filtration: list)
		{
			//当该查询条件为空时返回
			if(filtration == null || filtration.getFieldNames() == null 
					|| filtration.getFieldNames().length <= 0 || filtration.getFieldValue() == null)
			{
				continue;
			}
			//当该查询条件包含多个条件时，放入multiCondition中
			if(filtration.getFieldNames().length > 1)
			{
				if(null == multiCondition)
				{
					multiCondition = new ArrayList<Filtration>(3);
					map.put(SEARCH_CONITION_MULTI, multiCondition);//当有初始化multiCondition时，把他加入map中
				}
				multiCondition.add(filtration);
			}
			//当该查询条件包含多个查询条件时，放入singleCondition中
			else
			{
				if(null == singleCondition)
				{
					singleCondition = new ArrayList<Filtration>(3);
					map.put(SEARCH_CONITION_SINGLE, singleCondition);//当有初始化singleCondition时，把他加入map中
				}
				singleCondition.add(filtration);
			}
		}
	}
	
	/**
	 * 获取除分页以外的查询条件与排序条件
	 * @param pageData
	 * @return
	 */
	public static HashMap<String, Object> condtionToMap(PageData<?> pageData)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();//返回的查询条件
		getSearcherCondition(pageData.getFiltrations(), resultMap);
		getOrderCondition(pageData, resultMap);//获取排序信息
		return resultMap;
	}
	
	/**/
	@SuppressWarnings("unused")
    private static void getSearcherConditionDetail(List<Filtration> list, HashMap<String, Object> map)
	{
		//当查询条件为空时返回。
		if(null == list || list.size() == 0)
		{
			return;
		}
		
		for(Filtration filtration: list)
		{
			//当该查询条件为空时返回
			if(filtration == null || filtration.getFieldNames() == null 
					|| filtration.getFieldNames().length <= 0 || filtration.getFieldValue() == null)
			{
				continue;
			}
			//当该查询条件包含多个条件时，放入multiCondition中
			if(filtration.getFieldNames().length > 1)
			{
				
			}
			//当该查询条件包含多个查询条件时，放入singleCondition中
			else
			{
				//singleCondition.add(filtration);
			}
		}
	}
}
