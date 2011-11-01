<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="footer">
	<%
		UserDao userDao=new UserDao();
		User user=userDao.getUserDetail();
	%>
	<div class="content">Powered By choujone 版权所有.Some Rights Reserved <%=user.getBlogFoot() %></div>
</div>