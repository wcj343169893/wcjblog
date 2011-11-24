<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.common.Operation"%><link href="/css/admin-style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/js/common.js"></script>
<%
	UserDao userDao=new UserDao();
	User user=userDao.getUserDetail();
	if(user==null){
		userDao.operationUser(Operation.add,null);
		user=userDao.getUserDetail();
	}
%>
<div class="top">
	<div class="bord"> 
		<a title="后台管理" href="/admin/">控制面板</a>
		<a href="/user">[注销]</a>
	</div>
	 <div class="headings">
      	<h1><a href="/" ><%=user.getpTitle() %></a></h1>
      	<h2><%=user.getCtitle() %></h2>
      	<div class="clear">
      	</div>
    </div>
	<div class="menu">
		<ul>
			<li>
				<a href="/admin/blog_add.jsp">写文章</a>
			</li>
			<li>
				<a href="/admin/blog_list.jsp">文章</a>
			</li>
			<li>
				<a href="/admin/blogType_list.jsp">文章分类</a>
			</li>
			<li>
				<a href="/admin/friends_list.jsp">友情链接</a>
			</li>
			<li>
				<a href="/admin/reply_list.jsp">评论</a>
			</li>
		
			<li>
				<a href="/admin/file_list.jsp">文件</a>
			</li>
			<li>
				<a href="/admin/setting.jsp">设置</a>
			</li>
			<li>
				<a href="">清理所有缓存</a>
			</li>
			<li>
				<a href="/admin/adPlace_list.jsp">广告</a>
			</li>
		</ul>
	</div>
</div>
