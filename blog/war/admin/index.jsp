<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>
<title><%=blog_user.getpTitle()%> -- 后台管理</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
		<div class="vito-middle">
			<div align="center" style="margin-top: 200px;"><h1>这里放几个特效</h1></div>
		</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>