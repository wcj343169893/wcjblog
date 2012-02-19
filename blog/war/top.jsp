<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.util.Tools,java.util.Map,com.google.choujone.blog.entity.Menu"%>
<%@page import="java.util.List"%>
<%
	User blog_user=  Config.getBlog_user();
%>
<div class="top">
	<div class="top-title">
		<a href="/" title="<%=blog_user.getCtitle() %>"><%=blog_user.getpTitle()%></a><%if(blog_user.getCtitle()!= null  && !"" .equals(blog_user.getCtitle().trim())){ %><%} %><br/><a href="<%=blog_user.getUrl() %>"><%=blog_user.getUrl() %></a></div>
	<div class="top-menu">
		<ul><%
			List<Menu> menus=Config.getMenus();
			for (Menu m : menus) {%>
			<li><a href="<%=m.getUrl() %>"><%=m.getTitle() %></a>	</li>
		<%} %>
		</ul>
	</div>
</div>