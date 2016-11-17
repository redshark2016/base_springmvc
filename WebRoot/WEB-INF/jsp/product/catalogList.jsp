<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>
    <title>用户列表</title>
    
	<script type="text/javascript">
	function del(id){
		$.get("<%=basePath%>product/delCatalog?id=" + id,function(data){
			if("success" == data.result){
				alert("删除成功");
				window.location.reload();
			}else{
				alert("删除失败");
			}
		});
	}
	
	function page(pageNo){
		$("#pageNo").val(pageNo);
		$("#pageForm").submit();
	}
	
</script>
  </head>
  
  <body>
    <h6><a href="<%=basePath%>product/toAddCatalog">添加</a></h6>
	<table border="1">
		<tbody>
			<tr>
				<th>名称</th>
				<th>类别描述</th>
				<th>操作</th>
			</tr>
			<c:if test="${!empty list }">
				<c:forEach items="${list}" var="catalog">
					<tr>
						<td>${catalog.catalog_name }</td>
						<td>${catalog.catalog_desc }</td>
						<td>
							<a href="<%=basePath%>product/getCatalogInfo?id=${catalog.id}">编辑</a>
							<a href="javascript:del('${catalog.id }')">删除</a>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<table>
		<tr>
			<td>
			<a  href="javaScript:page(1)">首页</a>
			<c:if test="${null != pagination.pageNo && pagination.pageNo!=1}">
				<a  href="javaScript:page(${pagination.pageNo-1 })">上一页</a>
			</c:if>
			<c:if test="${pagination.totalPages !=pagination.pageNo}">
				<a  href="javaScript:page(${pagination.pageNo+1 })">下一页</a>
			</c:if>
			<a href="javaScript:page(${pagination.totalPages})">尾页</a>
			</td>
		</tr>
	</table>
	<form id="pageForm" name="pageForm" method="post" action="">
		<input type="hidden" name="pageNo" id="pageNo" value="" >
	</form>
	总记录数： ${pagination.totalCount},  当前页数: ${pagination.pageNo},  当前每页记录数: ${pagination.pageSize}
	
  </body>
</html>
