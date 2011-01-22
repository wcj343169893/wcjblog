<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.entity.*"%><html>
<head>
<link rel="stylesheet" href="/css/search.css" type="text/css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
UserDao userDao=new UserDao();
User user=userDao.getUserDetail();
User login_user=(User)request.getSession().getAttribute("login_user");
String title=user.getpTitle();
%>
<title>搜索 -- <%=title %></title>
<meta name="google-site-verification" content="0YKCfiBLHIYnG9LLMoVWT5MahWg50_rrDxRm9gcmM7k" />
<meta name="keywords" content="<%=user.getBlogKeyword() %>">
<meta name="description" content="<%=user.getBlogDescription() %>">
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-20148773-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body>
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