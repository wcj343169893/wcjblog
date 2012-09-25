<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%UserDao ud=new UserDao();
	User blog_user=  ud.getUserDetail();
%>
<title>登录_<%=blog_user.getpTitle()%></title>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body onload="">
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<div class="left masoned login">
<article class="mod-blogitem mod-item-text">
	<div class="mod-realcontent mod-cs-contentblock">
		<div class="item-head">
			<a href="javascript:;" class="a-incontent a-title cs-contentblock-hoverlink" target="_blank">登录</a>
		</div>
		<div class="item-content cs-contentblock-detailcontent">
			<div class="q-previewbox"></div>
			<div class="q-summary">
				<div class="login">
				<div><% if(request.getAttribute("error")!= null){out.print(request.getAttribute("error"));} %></div>
				<form action="/user" method="post" id="loginform" name="loginform">
				<table cellpadding="0" cellspacing="0" class="login_table">
					<tr>
						<td class="l_title">用户名：</td><td><input type="text" value="" name="name" size="20" maxlength="20"></td>
					</tr>
					<tr>
						<td class="l_title">密码：</td><td><input type="password" name="password" size="20" maxlength="20"></td>
					</tr>
					<tr>
						<td>&nbsp;</td><td><input type="submit" value="登录" class="btn btn-info"></td>
					</tr>
					<tr>
				</table>
				</form>
				</div>
			</div>
		</div>
		<div class="item-foot clearfix">
		</div>
		<div class="blog-cmt-wraper"></div>
	</div>
</article>
</div>
<!-- 左边结束 -->
<!-- 右边开始 -->
<jsp:include page="right.jsp"></jsp:include>
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>