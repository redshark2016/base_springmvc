package com.redshark.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redshark.model.CatalogBean;

/**
 * 
* @ClassName: CatalogDao  
* @Description: 产品类别接口  
* @author jianyue.yan
* @date 2016-9-20 下午05:27:34  
*
 */
@Repository
public interface CatalogDao {
	
	/**
	 *
	* @Title: save  
	* @Description: 保存产品类别
	* @param @param catalog    设定文件  
	* @return void    返回类型  
	* @throws
	 */
	void save(CatalogBean catalog);
	
	/**
	 * 
	* @Title: update  
	* @Description: 更新产品类别
	* @param @param user
	* @param @return    设定文件  
	* @return boolean    返回类型  
	* @throws
	 */
	boolean update(CatalogBean bean);
	
	/**
	 * 
	* @Title: delete  
	* @Description: 删除产品类别
	* @param @param id
	* @param @return    设定文件  
	* @return boolean    返回类型  
	* @throws
	 */
	boolean delete(String id);
	
	/**
	 * 
	* @Title: findById  
	* @Description:根据ID获取类别信息
	* @param @param id
	* @param @return
	* @return CatalogBean
	* @throws
	 */
	CatalogBean findById(String id);
	
	/**
	 * 
	* @Title: findAll  
	* @Description:查询所有分类信息
	* @param @return
	* @return List<CatalogBean>
	* @throws
	 */
	List<CatalogBean> findAll();
	
	/**
	 * 批量删除分类信息
	* @Title: deleteBatch  
	* @Description:
	* @param @param ids
	* @return void
	* @throws
	 */
	public void deleteBatch(String[] ids);
	
}
