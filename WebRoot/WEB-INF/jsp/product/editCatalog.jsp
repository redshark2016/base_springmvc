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
    
    <title>编辑</title>
    
	<script type="text/javascript">
	function updateInfo(){
		var form = document.forms[0];
		form.action = "<%=basePath%>product/updateCatalog";
		form.method="post";
		form.submit();
	}
</script>

  </head>
  
  <body>
    <h1>修改类别</h1>
	<form action="" name="catalogForm">
		<input type="hidden" name="id" value="${catalog.id }"/>
		产品类别：<input type="text" name="catalog_name" value="${catalog.catalog_name }"/>
		类别描述：<input type="text" name="catalog_desc" value="${catalog.catalog_desc}"/>
		排序号：<input type="text" name="sort" value="${catalog.sort}"/>
		<input type="button" value="编辑" onclick="updateInfo()"/>
	</form>
  </body>
  
</html>
