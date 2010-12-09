<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.util.Config"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao userDao = new UserDao();
	User user=userDao.getUserDetail();
%>
<title><%=user.getpTitle() %> -- 博客设置   / 系统设置</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			博客设置   / 系统设置
	</div>
	<div id="container">
		<form action="/user" method="post">
			<input type="hidden" name="op" value="modify">
			<table cellpadding="0" cellspacing="0" class="setting">
				<tr>
					<td class="title">博客标题</td>
					<td>
						<input type="text" name="pTitle" value="<%=user.getpTitle() %>">
					</td>
				</tr>
				<tr>
					<td class="title">博客子标题</td>
					<td>
						<input type="text" name="ctitle" value="<%=user.getCtitle() %>"><br>
						关于这个博客的简单介绍
					</td>
				</tr>
				<tr>
					<td class="title">博客访问地址</td>
					<td>
						<input type="text" name="url" value="<%=user.getUrl() %>">
					</td>
				</tr>
				<tr>
					<td class="title">博客风格</td>
					<td>
						<%List<String> styleList=Config.style_urls; 
						%>
						<select name="style">
						<%for(String s :styleList){ %>
							<option value="<%=s %>" <% if(s.equals(user.getStyle())){out.print("selected");} %>><%=s %></option>
						<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">公告</td>
					<td>
						<textarea rows="10" cols="70" name="notice"><%=user.getNotice() %></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">电子邮件</td>
					<td>
						<input type="text" name="email" value="<%=user.getEmail() %>">
					</td>
				</tr>
				<tr>
					<td class="title">生日</td>
					<td>
						<input type="text" name="brithday" value="<%=user.getBrithday() %>">
					</td>
				</tr>
				<tr>
					<td class="title">地址</td>
					<td>
						<input type="text" name="address" value="<%=user.getAddress() %>">
					</td>
				</tr>
				<tr>
					<td class="title">自我描述</td>
					<td>
						<textarea rows="10" cols="70" name="description"><%=user.getDescription() %></textarea>
					</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<td class="title">用户名</td>
					<td>
						<input type="text" name="name" value="<%=user.getName() %>">
					</td>
				</tr>
				<tr>
					<td class="title">密码</td>
					<td>
						<input type="password" name="password" value="<%=user.getPassword() %>">
					</td>
				</tr>
			</table>
		<div class="tools-left">
			<input type="submit" value="保存">
			<input type="reset" value="重置">
		</div>
		</form>
	</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>