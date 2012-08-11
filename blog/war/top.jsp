<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.util.Tools,java.util.Map,com.google.choujone.blog.entity.Menu"%>
<%@page import="java.util.List"%><%@page import="com.google.choujone.blog.dao.UserDao"%><%
	UserDao ud=new UserDao();
	User blog_user = ud.getUserDetail();
%>
<section class="mod-topspaceinfo mod-cs-header"><div class="head-topbar"></div><div class="container"><h1><a class="space-name cs-header-spacename" href="<%=blog_user.getUrl() %>"><%=blog_user.getCtitle() %></a>	</h1><p class="space-description cs-header-spacesummary"></p></div><div class="head-footer"></div></section>