<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.dao.UserDao"%><%
    	UserDao userDao = new UserDao();
			User blog_user = userDao.getUserDetail();
			if (blog_user != null && blog_user.getBlogHead() != null
					&& !"".equals(blog_user.getBlogHead())) {
				out.print(blog_user.getBlogHead());
}%>
<meta name="google-site-verification" content="0YKCfiBLHIYnG9LLMoVWT5MahWg50_rrDxRm9gcmM7k" />
<meta name="keywords" content="<%=blog_user != null ? blog_user.getBlogKeyword() : ""%>">
<meta name="description" content="<%=blog_user != null ? blog_user.getBlogDescription() : ""%>">
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
<link href="<%=blog_user.getStyle()%>" type="text/css" rel="stylesheet" />
<link href="/css/jquery.treeview.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/content.js"></script>