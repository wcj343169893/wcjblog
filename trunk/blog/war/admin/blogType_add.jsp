<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.dao.BlogTypeDao"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.BlogType"%>
<%@page import="com.google.choujone.blog.util.Tools"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章分类</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			新增文章分类
	</div>
	<%
		Long pid=Long.parseLong(request.getParameter("pid")!=null ?request.getParameter("pid") : "-1");
		BlogTypeDao btDao=new BlogTypeDao();
		List<BlogType> blogTypeList=btDao.getBlogTypeList(-1L);
		List<BlogType> blogTypeList_children=null;
	%>
		<div id="container">
			<form action="/blogType" method="get" name="modifyblog">
				<input type="hidden" name="url" value="1">
				<input type="hidden" name="opera" value="add">
				<div class="container-title">
					上级分类：
				</div>
				<select name="pid">
					<option value="0">请选择</option>
					<%if(blogTypeList!=null){ %>
					<%=Tools.blogTypeList2Str(blogTypeList,pid) %>
					<%} %>
				</select>
				<div class="container-title">
					标题：
				</div>
				<input type="text"	name="tname" value=""> 
				<div class="container-content">
					内容：
				</div>
					<input value="" name="info">
				<input type="submit" value="保存">
			</form>
		</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>