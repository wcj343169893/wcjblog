<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao,java.util.List,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.common.Pages,com.google.choujone.blog.entity.User,com.google.choujone.blog.common.Operation,java.text.SimpleDateFormat,java.util.Date,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType,java.util.ArrayList,java.util.Map,java.util.HashMap"%>
<%@page import="com.google.choujone.blog.util.Tools"%>
<%@page import="com.google.choujone.blog.util.Config"%><html>
<head><%
	User user=Config.blog_user;
	User login_user=(User)request.getSession().getAttribute("login_user");
	String title=user!=null ? user.getpTitle():"";
	Long tid=null;
	try{
		 tid=request.getParameter("tid") != null ? Long.valueOf(request.getParameter("tid").toString()) : null;
	}catch(Exception e){
		tid=null;
	}
	
	
%><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相册_<%=title %></title>
	<jsp:include page="../head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="picasa.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="slimbox2.js"></script>
<script type="text/javascript" src="picasa.js"></script>
</head>
<body>
<jsp:include page="../top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="../top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="../left.jsp" flush="true"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
	<div class="right-title">相册</div>
	<div id="navi"><a href="1" style="display:none;"></a></div>
	<div id="items"></div>
</div>
<!-- 右边结束 -->
<jsp:include page="../footer.jsp"></jsp:include>
</div>
</body>
</html>