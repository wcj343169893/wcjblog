<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao,java.util.List,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.common.Pages,com.google.choujone.blog.entity.User,com.google.choujone.blog.common.Operation,java.text.SimpleDateFormat,java.util.Date,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType,java.util.ArrayList,java.util.Map,java.util.HashMap"%>
<%@page import="com.google.choujone.blog.util.Tools"%>
<%@page import="com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head><%
	UserDao ud=new UserDao();
	User blog_user=  ud.getUserDetail();
	String title=blog_user!=null ? blog_user.getpTitle():"";
%><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相册_<%=title %></title>
	<jsp:include page="../head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="picasa.css" />
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="slimbox2.js"></script>
<script type="text/javascript" src="picasa.js"></script>
</head>
<body>
<jsp:include page="../top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="../top.jsp"></jsp:include>
<!-- 顶部结束 -->
<div class="mod-text-content mod-post-content">
	<div id="navi"><a href="1" style="display:none;"></a></div>
	<div id="items"></div>
</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>