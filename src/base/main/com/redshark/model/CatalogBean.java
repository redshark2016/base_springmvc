package com.redshark.model;


import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: CatalogBean  
* @Description: 分类实体BEAN
* @author jianyue.yan
* @date 2016-9-20 下午05:22:30  
*
 */

public class CatalogBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * ID
	 */
	private String id;
	
	/*
	 * 类别名称
	 */
	private String catalog_name;
	
	/*
	 * 备注
	 */
	private String catalog_desc;
	
	/*
	 * 创建时间
	 */
	private Date create_time;
	
	/*
	 * 排序号
	 */
	private int sort;
	
	private Date last_update_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatalog_name() {
		return catalog_name;
	}

	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}

	public String getCatalog_desc() {
		return catalog_desc;
	}

	public void setCatalog_desc(String catalog_desc) {
		this.catalog_desc = catalog_desc;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	
	
}
