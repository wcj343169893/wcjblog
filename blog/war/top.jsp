<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User"%><%@page import="com.google.choujone.blog.dao.UserDao"%>
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%><div class="top">
	<div class="top-title">
		<a href="/" title="<%=blog_user.getCtitle() %>"><%=blog_user.getpTitle()%></a><%if(blog_user.getCtitle()!= null  && !"" .equals(blog_user.getCtitle().trim())){ %><%} %><br/><a href="<%=blog_user.getUrl() %>"><%=blog_user.getUrl() %></a></div>
	<div class="top-menu">
		<ul>
			<li><a href="/">主页</a>	</li>
			<li><a href="/search.jsp">搜索</a></li>
			<li><a href="/leaveMessage.jsp">留言板</a></li>
			<li><a href="/admin">后台管理</a></li>
		</ul>
	</div>
</div>