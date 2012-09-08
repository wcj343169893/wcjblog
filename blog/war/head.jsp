<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config"%><%
    UserDao ud=new UserDao();
	User blog_user=  ud.getUserDetail();
	String keys=request.getParameter("kw")!=null?request.getParameter("kw"):blog_user.getBlogKeyword();
	String desc=request.getParameter("desc")!=null?request.getParameter("desc"):blog_user.getBlogDescription();
			if (blog_user != null && blog_user.getBlogHead() != null
					&& !"".equals(blog_user.getBlogHead())) {
				out.print(blog_user.getBlogHead());
}%><%@page import="com.google.choujone.blog.dao.UserDao"%><meta name="google-site-verification" content="0YKCfiBLHIYnG9LLMoVWT5MahWg50_rrDxRm9gcmM7k" />
<meta name="keywords" content="<%=keys %>">
<meta name="description" content="<%=desc %>">
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
<script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<!--[if (lt IE 9)]>
<script type="text/javascript">(function(){var b=("abbr,article,aside,audio,canvas,datalist,details,dialog,eventsource,figure,footer,header,hgroup,mark,menu,meter,nav,output,progress,section,time,video").split(","),a=b.length;while(a--){document.createElement(b[a])}})();</script>
<![endif]-->