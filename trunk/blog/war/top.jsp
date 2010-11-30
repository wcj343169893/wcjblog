<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.UserDao"%><link href="/css/style.css" type="text/css" rel="stylesheet" />
<%@page import="com.google.choujone.blog.entity.User"%><script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/jquery.js"></script>
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>
<div class="top">
	<div class="top-title">
		<a href="/"><%=blog_user.getpTitle()%></a>
		<%if(blog_user.getCtitle()!= null  && !"" .equals(blog_user.getCtitle().trim())){ %> 
			| <%=blog_user.getCtitle() %>  
		<%} %>
	</div>
	<div class="top-menu">
		<ul>
			<li>
				<a href="/">主页</a>
			</li>
			<li>
				<a>搜索</a>
			</li>
			<li>
				<a href="/leaveMessage.jsp">网站留言</a>
			</li>
			<li>
				<a href="/admin">后台管理</a>
			</li>
		</ul>
	</div>
</div>
