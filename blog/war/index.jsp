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
<%@page import="java.util.Date"%>
<%@page import="com.google.choujone.blog.dao.BlogTypeDao"%>
<%@page import="com.google.choujone.blog.entity.BlogType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao userDao=new UserDao();
	User user=userDao.getUserDetail();
	User login_user=(User)request.getSession().getAttribute("login_user");
	String title=user!=null ? user.getpTitle():"";
	userDao.closePM();
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
%>
<title><%=title %></title>
<%
	if(user!=null && user.getBlogHead()!=null && !"".equals(user.getBlogHead())){
		out.print(user.getBlogHead());
	}
%>
<meta name="google-site-verification" content="0YKCfiBLHIYnG9LLMoVWT5MahWg50_rrDxRm9gcmM7k" />
<meta name="keywords" content="<%=user!=null ? user.getBlogKeyword():"" %>">
<meta name="description" content="<%=user!=null ? user.getBlogDescription():"" %>">
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-20148773-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
</head>
<body>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="left.jsp" flush="true"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
	<div class="right-title">文章</div>
<%
	BlogDao blogDao = new BlogDao();
	Integer p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	
	Pages pages=new Pages();
	pages.setPageNo(p);
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
	List<Blog> blogs = blogDao.getBlogListByPage(pages,tid);
%>
<div class="vito-prenext">共<%=pages.getRecTotal() %>页 第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %>页<%if(p > 1){ %>	<a href="/index.jsp?p=<%=p-1 %>&tid=<%=tid %>">上一页</a><%} %><%if(p < pages.getPageTotal()){ %><a href="/index.jsp?p=<%=p + 1 %>&tid=<%=tid %>">下一页</a><%} %></div>
<%
if(blogs!=null && blogs.size()>0){
for(int i=0;i<blogs.size();i++){
	Blog blog=blogs.get(i);
	if(blog.getIsVisible() == 0 ){
%>
<div class="vito-content">
	<div class="vito-content-title">
		<a href="/blog?id=<%=blog.getId() %>"><%=blog.getTitle() %></a>
	</div>
	<div class="vito-content-date"><%=blog.getSdTime() %></div>
	<div class="vito-content-body"><%=blog.getContent(50).getValue() %><br><br>
		<font class="post-tags">Tags:<%=blog.getTag() %></font>
		<font class="post-footer">发布:<%=user!=null ? user.getName():"" %>|分类:<%=typeMaps.get(blog.getTid())%>|评论:<%=blog.getReplyCount() %>|浏览:<%=blog.getCount() %><%if(login_user!=null){	%>|<a href="/blog?id=<%=blog.getId() %>&op=modify">修改</a><%} %></font>
	</div>
</div><br>
<%}	
} }else{%>
<div class="vito-content">
	<div class="vito-content-title">暂无内容</div>
</div>
<%} %>
<div class="vito-prenext">共<%=pages.getRecTotal() %>页 第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %>页<%if(p > 1){ %>	<a href="/index.jsp?p=<%=p-1 %>">上一页</a><%} %><%if(p < pages.getPageTotal()){ %><a href="/index.jsp?p=<%=p + 1 %>">下一页</a><%} %></div>

<%blogDao.closePM();//关闭查询链接 %>
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>