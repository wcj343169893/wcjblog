<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao,java.util.List,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.common.Pages,com.google.choujone.blog.entity.User,com.google.choujone.blog.common.Operation,java.text.SimpleDateFormat,java.util.Date,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType,java.util.ArrayList,java.util.Map,java.util.HashMap"%>
<%@page import="com.google.choujone.blog.util.Tools"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head><%
UserDao ud=new UserDao();
User blog_user=  ud.getUserDetail();
	String title=blog_user!=null ? blog_user.getpTitle():"";
	Long tid=null;
	try{
		 tid=request.getParameter("tid") != null ? Long.valueOf(request.getParameter("tid").toString()) : null;
	}catch(Exception e){
		tid=null;
	}
	
	//查询所有的分类
	BlogTypeDao btd=new BlogTypeDao();
	List<BlogType> blogTypeList = new ArrayList<BlogType>();
	blogTypeList=btd.getBlogTypeList();
	Map<Long,String> typeMaps=new HashMap<Long,String>();
	
	for(int i=0;i<blogTypeList.size();i++){
		typeMaps.put(blogTypeList.get(i).getId(),blogTypeList.get(i).getName());
		if(tid!=null && blogTypeList.get(i).getId().equals(tid)){
			title=blogTypeList.get(i).getName()+"_"+title;
		}
	}
%><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=title %></title>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="left.jsp" flush="true"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
	<div class="right-title">文章</div><%
	BlogDao blogDao = new BlogDao();
	Integer p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	
	Pages pages=new Pages();
	pages.setPageNo(p);
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
	List<Blog> blogs = blogDao.getBlogListByPage(pages,tid);
%>
<div class="vito-prenext">共<%=pages.getRecTotal() %>条 第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %>页<%if(p > 1){ %>	<a href="/index.jsp?p=<%=p-1 %>&tid=<%=tid %>">上一页</a><%} %><%if(p < pages.getPageTotal()){ %><a href="/index.jsp?p=<%=p + 1 %>&tid=<%=tid %>">下一页</a><%} %></div>
<%
if(blogs!=null && blogs.size()>0){
for(int i=0;i<blogs.size();i++){
	Blog blog=blogs.get(i);
	if(blog.getIsVisible() == 0 ){
		String link=blog_user.getUrl()+"/blog/"+blog.getId();
%><div class="vito-content">
	<div class="vito-content-title">
		<a href="<%=link %>"><%=blog.getTitle() %></a>
	</div>
	<!-- <div class="vito-content-rc"><img alt="<%=link %>" src="http://chart.cli.im/chart?chs=150x150&cht=qr&chl=<%=link %>%0A<%=blog.getTitle() %>" style="border: none" title="<%=blog.getTitle() %>"></div>-->
	<div class="vito-content-date"><%=blog.getSdTime() %></div>
	<div class="vito-content-body"><%=blog.getContent(50).getValue() %>
	<br><br>
		<font class="post-tags">Tags:<%=blog.getTag() %></font>
		<font class="post-footer">发布:<%=blog_user!=null ? blog_user.getName():"" %>|分类:<%=typeMaps.get(blog.getTid())%>|评论:<%=blog.getReplyCount() %>|浏览:<%=blog.getCount() %><%if(Tools.isLogin(request)){	%>|<a href="/blog?id=<%=blog.getId() %>&op=modify">修改</a><%} %></font>
	</div>
</div><br><%}	
} }else{%>
<div class="vito-content">
	<div class="vito-content-title">暂无内容</div>
</div><%} %>
<div class="vito-prenext">共<%=pages.getRecTotal() %>条 第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %>页<%if(p > 1){ %>	<a href="/index.jsp?p=<%=p-1 %>&tid=<%=tid %>">上一页</a><%} %><%if(p < pages.getPageTotal()){ %><a href="/index.jsp?p=<%=p + 1 %>&tid=<%=tid %>">下一页</a><%} %></div><%blogDao.closePM();//关闭查询链接 %>
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>