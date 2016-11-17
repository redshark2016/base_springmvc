<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<!-- Jquery -->
	<script src="<%=basePath%>/js/jquery-1.11.1.min.js"></script>
    <title>用户列表</title>
    
	<script type="text/javascript">
	function del(id){
		$.get("<%=basePath%>admin/user/delUser?id=" + id,function(data){
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
    <h6><a href="<%=basePath%>admin/user/toAddUser">添加用户</a></h6>
	<table border="1">
		<tbody>
			<tr>
				<th>姓名</th>
				<th>身份证号</th>
				<th>操作</th>
			</tr>
			<c:if test="${!empty userList }">
				<c:forEach items="${userList}" var="user">
					<tr>
						<td>${user.user_name }</td>
						<td>${user.idcard }</td>
						<td>
							<a href="<%=basePath%>admin/user/getUser?id=${user.id}">编辑</a>
							<a href="javascript:del('${user.id }')">删除</a>
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
