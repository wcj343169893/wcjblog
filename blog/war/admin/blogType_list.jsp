<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.dao.BlogTypeDao"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.BlogType"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章分类列表</title>
<script type="text/javascript" src="/js/jquery.js"></script>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			管理分类（<a href="blogType_add.jsp">新增分类</a>）
	</div>
	<%
		Long parentId=Long.parseLong(request.getParameter("pid")!=null ?request.getParameter("pid") : "0");
		BlogTypeDao btDao=new BlogTypeDao();
		List<BlogType> blogTypeList=btDao.getBlogTypeList(parentId);
		BlogType blogType=null;
		if(parentId>0){
			blogType=btDao.getBlogTypeById(parentId);
		}
	%>
	<div class="main-title">
			<%if(blogType!=null) {%>
				<%=blogType.getName() %>
			<%}else{ %>
				所有分类
			<%} %>
	</div>
	<div class="tools">
		<span class="tools-left">
		</span>
		<span class="tools-right">
		<%if(blogType!=null) {%>
				<a href="/admin/blogType_list.jsp?pid=<%=blogType.getParentId() %>">返回上一级</a>
			<%}else{ %>
			<%} %>
		</span>
	</div>
	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox" id="input_check_all" onclick="allCheckFlag(this)"/></th>
				<th>上级id</th>
				<th width="300px">分类名</th>
				<th width="300px">操作</th>
			</tr>
			<% if(blogTypeList!=null && blogTypeList.size()>0){ %>
				<%for(BlogType bt : blogTypeList){ %>
					<tr>
						<td class="vito-content-check"><input type="checkbox" id="rid_<%=bt.getId() %>" class="input_check_single" onclick="singleDeleteFlag(this)" name="deleteFlag" value="<%=bt.getId() %>"/></td>
						<td><%=bt.getParentId() %></td>
						<td><%=bt.getName() %></td>
						<td>
							<a href="/admin/blogType_list.jsp?pid=<%=bt.getId() %>" class="child" title="子分类">&nbsp;</a>   
							<a href="/admin/blogType_add.jsp?pid=<%=bt.getId() %>" class="newChild" title="添加子分类">&nbsp;</a>   
							<a href="/admin/blogType_update.jsp?id=<%=bt.getId() %>" class="edit" title="修改">&nbsp;</a> 
							<a href="/blogType?t=<%=bt.getId() %>&url=1" class="delete" title="删除">&nbsp;</a> 
						</td>
					</tr>
				<%} %>	
			<%} %>
		</table>
	</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>