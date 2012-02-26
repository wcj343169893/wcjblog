<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.entity.User"%><html>
<%
UserDao userDao=new UserDao();
User user=userDao.getUserDetail();
if(user!=null){
	response.sendRedirect("/index.jsp");
	return;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客安装程序</title>
<link href="/css/install.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div class="main">
<div class="content">
	<div id="container">
		<form action="/user" method="post">
			<input type="hidden" name="op" value="add">
			<div>博客设置</div>
			<br>
			<table>
				<tr>
					<td class="title">用户名</td>
					<td>
						<input type="text" name="name" value="">
					</td>
				</tr>
				<tr>
					<td class="title">密码</td>
					<td>
						<input type="password" name="password" value="">
					</td>
				</tr>
			</table>
		<div class="tools-left">
			<input type="submit" value="保存">
			<input type="reset" value="重置">
		</div>
		</form>
	</div>
</div>
<div class="footer">Powered By choujone 版权所有.Some Rights Reserved</div>
</div>
</body>
</html>