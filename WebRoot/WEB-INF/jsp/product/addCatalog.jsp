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
    
    <title>添加类别</title>
    
	<script type="text/javascript">
	function addInfo(){
		var form = document.forms[0];
		form.action = "<%=basePath%>product/addCatalog";
		form.method="post";
		form.submit();
	}
</script>

  </head>
  
  <body>
    <h1>添加类别</h1>
	<form action="" name="catalogForm">
		类别名称：<input type="text" name="catalog_name">
		类别描述：<input type="text" name="catalog_desc">
		排序号：<input type="text" name="sort">
		<input type="button" value="添加" onclick="addInfo()">
	</form>
  </body>
</html>
