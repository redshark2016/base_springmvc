<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String basePath = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="chn">
  	<head>
	    <meta charset="utf-8">
	    <title>系统登录</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="description" content="">
	    <meta name="author" content="">

	    <!-- Bootstrap core CSS -->
	    <link href="<%=basePath%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Font Awesome -->
		<link href="<%=basePath%>/css/font-awesome.min.css" rel="stylesheet">
		<!-- ionicons -->
		<link href="<%=basePath%>/css/ionicons.min.css" rel="stylesheet">
		<!-- Simplify -->
		<link href="<%=basePath%>/css/simplify.min.css" rel="stylesheet">
  	</head>

  	<body class="overflow-hidden light-background">
		<div class="wrapper no-navigation preload">
			<div class="sign-in-wrapper">
				<div class="sign-in-inner">
					<div class="login-brand text-center">
						<i class="fa fa-database m-right-xs"></i> <strong class="text-skin">系统登录</strong>
					</div>

					<form method="post" action="<%=basePath%>/login" id="loginForm">
						<div class="form-group m-bottom-md">
							<input type="text" class="form-control" name="user_name" placeholder="用户名">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="password" placeholder="密码">
						</div>

						<div class="form-group">
							<div class="custom-checkbox">
								<input type="checkbox" id="chkRemember">
								<label for="chkRemember"></label>
							</div>
							记住密码
						</div>
						<div style="color: red;">
						        	${msg }
						</div>
						<div class="m-top-md p-top-sm">
							<a href="javaScript:checkLogin()" class="btn btn-success block">登 录</a>
						</div>

					<!-- 	<div class="m-top-md p-top-sm">
							<div class="font-12 text-center m-bottom-xs">
								<a href="#" class="font-12">忘记密码 ?</a>
							</div>
							<div class="font-12 text-center m-bottom-xs">还没有账号?</div>
							<a href="signup.html" class="btn btn-default block">创建一个新账号</a>
						</div> -->
					</form>
				</div><!-- ./sign-in-inner -->
			</div><!-- ./sign-in-wrapper -->
		</div><!-- /wrapper -->

		<a href="" id="scroll-to-top" class="hidden-print"><i class="icon-chevron-up"></i></a>

	    <!-- Le javascript
	    ================================================== -->
	    <!-- Placed at the end of the document so the pages load faster -->
		
		<!-- Jquery -->
		<script src="<%=basePath%>/js/jquery-1.11.1.min.js"></script>
		
		<!-- Bootstrap -->
	    <script src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
		
		<!-- Slimscroll -->
		<script src='<%=basePath%>/js/jquery.slimscroll.min.js'></script>
		
		<!-- Popup Overlay -->
		<script src='<%=basePath%>/js/jquery.popupoverlay.min.js'></script>

		<!-- Modernizr -->
		<script src='<%=basePath%>/js/modernizr.min.js'></script>
		
		<!-- Simplify -->
		<script src="<%=basePath%>/js/simplify/simplify.js"></script>
		<script type="text/javascript">
		function checkLogin(){
			$("#loginForm").submit();
		}
	</script>
  	</body>
</html>
