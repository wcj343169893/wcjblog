<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.dao.BlogDao,java.util.Map,com.google.choujone.blog.dao.BlogTypeDao,java.util.List,com.google.choujone.blog.entity.BlogType,com.google.choujone.blog.util.Tools,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.dao.FriendsDao,com.google.choujone.blog.entity.Friends,com.google.choujone.blog.common.Pages"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<%	UserDao ud=new UserDao();
User blog_user = ud.getUserDetail(); %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站地图-<%=blog_user.getpTitle() %></title>
<style type="text/css">
	div{
		font-size: 14px;
	}
	.header{
		text-align: center;
	}
	.footer{
		text-align: center;
	}
</style>
</head><% BlogDao bd=new BlogDao();%>
<body>
<div class="header"><h1>网站地图-<%=blog_user.getpTitle() %></h1></div>
<%if(blog_user.getIsStatistics()==null || blog_user.getIsStatistics()==0){ %>
<div>最新文章</div>
<div><%List<Blog> blogList=bd.getBlogList(); %>
	<ul><%for(Blog blog:blogList){ %>
		<li><a href="/blog?id=<%=blog.getId() %>"><%=blog.getTitle() %></a></li><%} %>
	</ul>
</div>
<div>文章分类</div>
<div>
		<%
			BlogTypeDao btd=new BlogTypeDao();
			List<BlogType> btList=	btd.getBlogTypeList();
		%>
		<ul id="blogType">
			<li title="全部文章">全部文章(<%=bd.getCount(null) %>)</li><%if(btList!=null){ %>
				<%=Tools.blogTypeList2Str(btList) %>
			<%} %>
		</ul>
</div>
<div>最新标签</div>
<div>
<ul><%Map<String, Integer> tagsMap =bd.getTags();for(String s : tagsMap.keySet()){ %>
	<li><a href="javascript:void(0)" style="font-size: <%=tagsMap.get(s)+12 %>px"><%=s %>(<%=tagsMap.get(s) %>)</a></li><%} %></ul>
</div>
<div>友情链接</div>
<div>
<ul class="vito-right-contentul"><%
		FriendsDao fd=new FriendsDao();
		List<Friends> friendsList=fd.getFriendsByPage(new Pages(10000));
	%>
			<%
				for(int i=0;i<friendsList.size();i++){
					Friends f=friendsList.get(i);
			%><li title="<%=f.getDescription() %>"><a href="<%=f.getUrl() %>" target="_bank" title="<%=f.getDescription() %>"><%=f.getName() %></a></li><%} %>
		</ul>
</div>
<%} %>
<div><a href="/">返回首页</a></div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>