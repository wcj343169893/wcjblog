<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.Blog"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章列表</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			管理文章(<a href="/admin/blog_add.jsp">新建</a>) 
	</div>
		<%
	BlogDao blogDao = new BlogDao();
	int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	Pages pages=new Pages();
	pages.setPageNo(p);
	List<Blog> blogs = blogDao.getBlogsByPage(pages);
	%>
	<div class="main-title">
		所有文章(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" value="删除">
		</span>
		<span class="tools-right">
			共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
			<%if(p > 1){ %>
				<a href="/admin/blog_list.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
			<%} %>
			<%if(p < pages.getPageTotal()){ %>
				<a href="/admin/blog_list.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
			<%} %>
		</span>
	</div>

	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox"/></th>
				<th width="300px">标题</th>
				<th width="300px">内容</th>
				<th>回复/查看</th>
				<th>发表时间 </th>
				<th>最后修改时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<%if(blogs!= null && blogs.size()>0){
				for(int i=0;i<blogs.size();i++){
				Blog blog=blogs.get(i); %>
			<tr>
				<td class="vito-content-check"><input type="checkbox"/></td>
				<td class="vito-title">&nbsp;&nbsp;<a href="/blog?id=<%=blog.getId() %>&op=modify" title='修改   "<%=blog.getTitle() %>"'>[ 编辑 ]</a> &nbsp;  <a href="/blog_detail.jsp?id=<%=blog.getId() %>" target="_blank" title='查看  "<%=blog.getTitle() %>"'><%=blog.getTitle() %></a></td>
				<td><%=blog.getContent(10).getValue() %></td>
				<td><%=blog.getReplyCount() %>/<%=blog.getCount() %></td>
				<td><%=blog.getSdTime() %> </td>
				<td><%=blog.getMoTime() %> </td>
				<td><%=blog.getIsVisible() == 0 ? "发布" : "隐藏" %></td>
				<td><a href="/blog?id=<%=blog.getId() %>&op=delete">删除</a></td>
			</tr>
			<%}} %>
		</table>
	</div>
	
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>