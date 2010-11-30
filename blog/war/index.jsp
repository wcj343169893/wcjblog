<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.Blog"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.common.Operation"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao userDao=new UserDao();
	User user=userDao.getUserDetail();
	User login_user=(User)request.getSession().getAttribute("login_user");
	String title=user.getpTitle();
	userDao.closePM();
%>
<title><%=title %></title>
<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
</head>
<body>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="left.jsp"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
<div style="font-family: 微软雅黑, 宋体, Arial, Helvetica, sans-serif; font-size: 14px; color: #ffd247; padding: 0 0 10px 20px;">
		文章列表
	</div>
<%
	BlogDao blogDao = new BlogDao();
	int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	Pages pages=new Pages();
	pages.setPageNo(p);
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
	List<Blog> blogs = blogDao.getBlogListByPage(pages);
%>
<div class="vito-prenext">
	共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
	<%if(p > 1){ %>
		<a href="/index.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
	<%} %>
	<%if(p < pages.getPageTotal()){ %>
		<a href="/index.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
	<%} %>
</div>
<%
for(int i=0;i<blogs.size();i++){
	Blog blog=blogs.get(i);
	if(blog.getIsVisible() == 0 ){
%>
<div class="vito-content">
	<div class="vito-content-title">
		<a href="/blog_detail.jsp?id=<%=blog.getId() %>">
			<%=blog.getTitle() %>
		</a>
	</div>
	<div class="vito-content-date">
		<%=blog.getSdTime() %>
	</div>
	<div class="vito-content-body">
		<%=blog.getContent(50).getValue() %>
		<br><br>
		<font class="post-tags">
			Tags:<%=blog.getTag() %>
		</font>
		<font class="post-footer">
			发布:<%=user.getName() %> | 
			分类:<%=blog.getTid()%> | 
			评论:<%=blog.getReplyCount() %> | 
			浏览:<%=blog.getCount() %>
			<%if(login_user!=null){	%> 
				| <a href="/blog?id=<%=blog.getId() %>&op=modify">修改</a> 
			<%} %>
		</font>
	</div>
</div><br>
<%}	
} %>
<div class="vito-prenext">
	共<%=pages.getRecTotal() %>  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页
	<%if(p > 1){ %>
		<a href="/index.jsp?p=<%=p-1 %>">上一页</a>
	<%} %>
	<%if(p < pages.getPageTotal()){ %>
		<a href="/index.jsp?p=<%=p + 1 %>">下一页</a>
	<%} %>
</div>

<%blogDao.closePM();//关闭查询链接 %>
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>