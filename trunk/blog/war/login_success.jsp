<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.User"%><%@page import="com.google.appengine.api.users.UserService"%><%@page import="com.google.appengine.api.users.UserServiceFactory"%><%@page import="com.google.choujone.blog.util.Config"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	User blog_user=Config.blog_user;
%>
<title>登录_<%=blog_user.getpTitle()%></title>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="left.jsp"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
	<div class="right-title">
		登录后台 <% if(request.getAttribute("error")!= null){out.print(request.getAttribute("error"));} %>
	</div>
	<div class="login">
	 	<%
	     	UserService userService = UserServiceFactory.getUserService();
	     	if (userService.isUserLoggedIn()) {
	   %>
	      欢迎 <%if(userService.isUserAdmin()){out.print("管理员");}%><%= userService.getCurrentUser().getNickname() %>!<a href="<%=userService.createLogoutURL(request.getRequestURI())%>">退出</a>
	   <%}%>
	 <script type="text/javascript" 
	 src="http://qzs.qq.com/qzone/openapi/qc.js">
	</script>
	<script type="text/javascript">
	//获取openid
	QC.Login.getMe(function(openId, accesToken, backData)
	{
		$("#qq_login").html("您的openid是："+openId+";\r\n accessToken是："+ accesToken);
		QC.JSON("https://graph.qq.com/user/get_user_info",{"oauth_consumer_key":"100237063","openid":openId,"accessToken":accesToken},function(data){
			alert(data);
			});
	});
	</script>
	<span id="qq_login"></span>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>