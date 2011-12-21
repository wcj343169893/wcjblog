<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>

<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>
<title>登录_<%=blog_user.getpTitle()%></title>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body onload="document.forms.loginform.name.focus()">
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="left.jsp"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
	<div class="right-title">
		登录后台 <% if(request.getAttribute("error")!= null){out.print(request.getAttribute("error"));} %>
	</div>
	<div class="login">
	 	<%
	     	UserService userService = UserServiceFactory.getUserService();
	     	if (!userService.isUserLoggedIn()) {
	   %>
		<form action="/user" method="post" id="loginform" name="loginform">
			用户名：<input type="text" value="" name="name" size="20" maxlength="20"><br><br>
			密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" size="20" maxlength="20"><br><br>
			<div align="center"><input type="submit" value="登录"></div> 
		</form>
	      <a href="<%=userService.createLoginURL(request.getRequestURI())%>" >google账号登陆</a>
	   <% 
	     } else { 
	     %>
	      欢迎 <%if(userService.isUserAdmin()){out.print("管理员");}%><%= userService.getCurrentUser().getNickname() %>!<br/><br/><a href="<%=userService.createLogoutURL(request.getRequestURI())%>">退出</a>
	   <%
	   }
	   %>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>