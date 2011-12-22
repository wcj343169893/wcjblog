<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User"%><%@page import="com.google.choujone.blog.dao.UserDao"%>
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>

<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<div id="nav_wrap">
	<div id="top_member">
			<%
		     	UserService userService = UserServiceFactory.getUserService();
				String  url  =  request.getRequestURI();  
				if(url.indexOf("blog_detail.jsp")!=-1){
					url="/blog";
				}
				if(request.getQueryString()!=null)  { 
					   url+="?"+request.getQueryString(); 
				}
		     	if (!userService.isUserLoggedIn()) {
		   %>
		      	<a href="<%=userService.createLoginURL(url)%>" >Google账号登陆</a>
		   <% 
		     } else { 
		     %>
		   欢迎 <%if(userService.isUserAdmin()){out.print("管理员");}%><%= userService.getCurrentUser().getNickname() %>!  <a href="<%=userService.createLogoutURL(url)%>">退出</a>
		   <%
		   }
		   %>
	</div>
</div>
