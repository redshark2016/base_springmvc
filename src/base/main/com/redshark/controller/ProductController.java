package com.redshark.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redshark.common.page.util.PageContext;
import com.redshark.model.CatalogBean;
import com.redshark.service.CatalogService;

/**
 * 
* @ClassName: UserController  
* @Description: 商品相关控制类
* @author jianyue.yan
* @date 2016-9-20 下午11:08:32  
*
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController {

	@Autowired
	private CatalogService catalogService;
	
	/**
	 * 获取所有类别列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCatalogList")
	public String getCatalogList(HttpServletRequest request, String pageNo){
		PageContext.setIsNeedPagination(true);
		PageContext page = PageContext.getContext();  
	    if(null == pageNo){  
	    	page.setPageNo(1);  
		 } else{  
	          page.setPageNo(Integer.parseInt(pageNo));  
		 }  
		List<CatalogBean> list = catalogService.findAll();
		page.setTotalPages(page.getTotalPage());
		request.setAttribute("list", list);
		request.setAttribute("pagination", page);
		return "/product/catalogList";
	}
	
	/**
	 * 跳转到添加类别界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddCatalog")
	public String toAddCatalog(HttpServletRequest request){
		return "/product/addCatalog";
	}
	/**
	 * 添加类别并重定向
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/addCatalog")
	public String addCatalog(CatalogBean bean,HttpServletRequest request){
		catalogService.save(bean);
		return "redirect:/product/getCatalogList";
	}
	
	/**
	 *编辑类别
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(CatalogBean bean,HttpServletRequest request){
		if(catalogService.update(bean)){
			bean = catalogService.findById(bean.getId());
			request.setAttribute("user", bean);
			return "redirect:/product/getCatalogList";
		}else{
			return "/error";
		}
	}
	/**
	 * 根据id查询单个类别
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCatalogInfo")
	public String getCatalogInfo(String id,HttpServletRequest request){
		
		request.setAttribute("catalog", catalogService.findById(id));
		return "/product/editCatalog";
	}
	/**
	 * 删除类别
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delCatalog")
	public void delCatalog(String id,HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"error\"}";
		
		if(catalogService.delete(id)){
			result = "{\"result\":\"success\"}";
		}
		
		response.setContentType("application/json");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
