<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.UserDao,com.google.choujone.blog.entity.User,java.util.List,com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.dao.FriendsDao"%>
<%@page import="com.google.choujone.blog.entity.Friends"%>
<%@page import="com.google.choujone.blog.common.Pages"%><html><%
	UserDao ud=new UserDao();
    User blog_user=  ud.getUserDetail();
   List<String> ftList=Config.getFtList();
   FriendsDao fd=new FriendsDao();
   List<Friends> friendList=fd.getFriendsByPage(new Pages(1000));
%><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友情链接_<%=blog_user.getpTitle()%></title><%
	if (blog_user.getBlogHead() != null
					&& !"".equals(blog_user.getBlogHead().trim())) {
				out.print(blog_user.getBlogHead());
			}
%>	<jsp:include page="head.jsp"></jsp:include>
<script type="text/javascript" src="/js/content.js"></script>
<style type="text/css">
	#linkpage{margin:0;padding:0 0 10px 0px;}
	#linkpage ul{margin:0;}
	#linkpage h2{margin-bottom:10px;line-height:25px;color:#1b232a;font-size:19px;font-weight:700;text-shadow:1px 1px 2px #999;}
	#linkpage li{float:left;display:block;margin:0 0 10px;padding:0;width:100%;list-style-type:none;}
	#linkpage li ul li{float:left;border:1px solid #FEE7F1;margin:2px 1px;width:24.3%;height:23px;list-style:none;line-height:23px;text-align:center;}
	#linkpage li ul li a{display:block;border:1px solid #FEE7F1;font-size:1em;text-decoration:none;border-radius:3px;}
	#linkpage li ul li a:hover{background:#E89DBC;border-color:#E89DBC;color:#fff;text-shadow:0 1px 0 #E89DBC;}
</style>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<jsp:include page="left.jsp"></jsp:include>
<!-- 左边结束 -->
<!-- 右边开始 -->
<div class="right">
	<div class="right-title" style="border-top: 1px solid;">友情链接</div>
	<ul id="linkpage">
		<%for(int i=0;i<ftList.size();i++){ %>
			<li id="linkcat-346" class="linkcat"><h2><%=ftList.get(i) %></h2>
				<ul class="xoxo blogroll">
				<%for(Friends f:friendList){ %>
					<%if(f.getTid()!=null&&f.getTid().equals(i)){ %>
					<li><a href="<%=f.getUrl() %>" rel="<%=f.getDescription() %>" target="_blank"><%=f.getName() %></a></li>
					<%} %>
				<%} %>
				</ul>
			</li>
		<%} %>
	</ul>
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>