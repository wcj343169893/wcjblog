<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><%@page import="com.google.choujone.blog.util.Config,com.google.choujone.blog.entity.*"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head>
<link rel="stylesheet" href="/css/search.css" type="text/css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
UserDao ud=new UserDao();
User blog_user = ud.getUserDetail();
String title=blog_user.getpTitle();
%><title>搜索_<%=title %></title>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<!-- 左边结束 -->
<div class="right" style="height: 440px; background-color: white;">
			<div id="cse" style="width: 90%;">Loading</div>
			<script src="http://www.google.com/jsapi" type="text/javascript"></script>
			<script type="text/javascript">
			  google.load('search', '1', {language : 'zh-CN'});
			  google.setOnLoadCallback(function() {
			    var customSearchControl = new google.search.CustomSearchControl('018191268328157601197:kgczkzxwed8');
			    customSearchControl.setResultSetSize(google.search.Search.SMALL_RESULTSET);
			    var options = new google.search.DrawOptions();
			    options.setAutoComplete(true);
			    customSearchControl.draw('cse', options);
			  }, true);
			</script>
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>