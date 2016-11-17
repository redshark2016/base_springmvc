package com.redshark.app.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redshark.app.constants.AppConstant;
import com.redshark.app.model.AppReturnData;
import com.redshark.app.utils.AppReturnDataUtil;
import com.redshark.common.page.util.PageContext;
import com.redshark.model.CatalogBean;
import com.redshark.service.CatalogService;
import com.redshark.utils.StringUtil;

/**
 * 
* @ClassName: UserController  
* @Description: 商品相关控制类
* @author jianyue.yan
* @date 2016-9-20 下午11:08:32  
*
 */
@Controller
@RequestMapping("/api")
public class AppProductController {

	@Autowired
	private CatalogService catalogService;
	
	/**
	 * 获取所有类别列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCatalogList")
	public AppReturnData  getCatalogList(HttpServletRequest request, String pageNo,String pageSize){
		PageContext.setIsNeedPagination(true);
		PageContext page = PageContext.getContext();  
	    if(StringUtil.isBlank(pageNo)){  
	    	page.setPageNo(1);  
		 } else{  
	          page.setPageNo(Integer.parseInt(pageNo));  
		 }  
	    if(!StringUtil.isBlank(pageSize)){
	    	page.setPageSize(Integer.parseInt(pageSize));
	    }
		AppReturnData returnData  = new AppReturnData();
	    try{
			List<CatalogBean> list = catalogService.findAll();
			returnData.setData(list);
			returnData.setMessage("查询成功");
			returnData.setStatus(AppConstant.SUCCESS_FLAG);
	    }catch(Exception e){
	    	AppReturnDataUtil.error(returnData, "数据库查询出错");
	    }
		return returnData;
	}
	
	/**
	 * 根据id查询单个类别
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCatalogInfo")
	public AppReturnData getCatalogInfo(String id,HttpServletRequest request){
		AppReturnData returnData  = new AppReturnData();
		try{
			returnData.setData(catalogService.findById(id));
			returnData.setMessage("查询成功");
			returnData.setStatus(AppConstant.SUCCESS_FLAG);
		}catch(Exception e){
			AppReturnDataUtil.error(returnData, "数据库查询出错");
		}
			return returnData;
	}
	/**
	 * 删除类别
	 * @param id
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/delCatalog")
	public AppReturnData delCatalog(String id,HttpServletRequest request,HttpServletResponse response){
		AppReturnData returnData  = new AppReturnData();
		try{
			returnData.setData(catalogService.delete(id));
			returnData.setMessage("删除成功");
			returnData.setStatus(AppConstant.SUCCESS_FLAG);
		}catch(Exception e){
			AppReturnDataUtil.error(returnData, "操作数据库出错");
		}
			return returnData;
	}
}
