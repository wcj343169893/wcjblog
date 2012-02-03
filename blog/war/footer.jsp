<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config"%><div class="footer"><%
		User user=Config.blog_user;;
	%>
	<div class="content">Powered By choujone 版权所有.Some Rights Reserved <%=user.getBlogFoot() %></div>
</div>
<div class="clear"></div>