<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><%@page import="com.google.choujone.blog.dao.BlogDao,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.util.Tools,com.google.choujone.blog.dao.ReplyDao,com.google.choujone.blog.common.Pages,java.util.List,com.google.choujone.blog.entity.Reply,com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.common.Operation,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.util.MyCache"%><html><%
	String id = request.getParameter("id")!=null ? request.getParameter("id") : (String)request.getAttribute("id");
	boolean isOk=false;
	BlogDao blogDao=null;
	Blog blog=null;
	UserDao ud=new UserDao();
    User blog_user=  ud.getUserDetail();
	if (id != null && !"".equals(id.trim())) {
		isOk=true;
		blogDao = new BlogDao();
		blog = blogDao.getBlogById(Tools.strTolong(id));
		if (blog == null) {
			isOk=false;
		}
	} 
	if(isOk) {
			//blogDao.operationBlog(Operation.readTimes, blog);
			//更新阅读时间
			Config.addBlogReadCount(blog.getId());
			//Blog preBlog = blogDao.getPreBlog(blog.getId());
			Blog preBlog = null;
			//Blog nextBlog = blogDao.getNextBlog(blog.getId());
			Blog nextBlog = null;
			//查询所有的分类
			BlogTypeDao btd = new BlogTypeDao();
			BlogType bt = btd.getBlogTypeById(blog.getTid());
		 	
%><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=blog.getTitle()%>_<%=bt!=null ? bt.getName(): "默认分类"%>_<%=blog_user.getpTitle()%></title><%
	if (blog_user.getBlogHead() != null
					&& !"".equals(blog_user.getBlogHead().trim())) {
				out.print(blog_user.getBlogHead());
			}
%>	<jsp:include page="head.jsp"></jsp:include>
<script type="text/javascript" src="/js/content.js"></script>
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
	<div class="right-title">文章</div>
	<div class="vito-content-detail">
	<div class="vito-prenext"><%
		if (preBlog != null) {
	%><span style="float: left; margin: 0 0 0 20px;"><a class="l" href="/blog/<%=preBlog.getId()%>">&laquo; <%=preBlog.getTitle(20)%></a></span><%
 	}
 %><%
 	if (nextBlog != null) {
 %><span style="float: right; margin: 0 0 0 20px;"><a class="l" href="/blog/<%=nextBlog.getId()%>"><%=nextBlog.getTitle(20)%>&raquo;</a></span><%
 	}
		String url_host="";
		if(blog_user.getUrl().indexOf("http://") != -1){url_host=blog_user.getUrl();}else{url_host="http://"+blog_user.getUrl();}
	%></div>
	<div class="vito-content-title" id="vito-content-title"><a href="<%=url_host %>/blog/<%=id %>" title="<%=blog.getTitle()%>"><%=blog.getTitle()%></a></div>
	<div class="vito-content-date"><%=blog.getSdTime()%></div>
	<div class="vito-detail-content-body"><%=blog.getContent().getValue()%><br><br>本文地址:<a href="<%=url_host %>/blog/<%=id %>" title="<%=blog.getTitle()%>"><%=url_host %>/blog/<%=id %></a><br>
	<font class="post-tags">Tags:<%=blog.getTag()%></font>
	<font class="post-footer">发布:<%=blog_user.getName()%>|分类:<%=bt!=null ? bt.getName():"默认分类"%>|评论:<%=blog.getReplyCount()%>|浏览:<%=blog.getCount()%><%
		if (Tools.isLogin(request)) {
	%>|<a href="/blog?id=<%=blog.getId()%>&op=modify">修改</a><%
		}
	%></font>
	</div>
	<!-- ajax 动态查询评论  -->
	<div
		style="font-family: 微软雅黑, 宋体, Arial, Helvetica, sans-serif; font-size: 14px; color: #ffd247; padding: 0 0 10px 20px;">评论列表</div>
	<div class="vito-postcommentlist" id="reply_comment">
		<a href="javascript:initReply(1,<%=blog.getId()%>)">加载评论</a>
	</div>
	<script type="text/javascript">
		jQuery(function($) {
			//initReply(1,<%=blog.getId()%>);
		});
	</script>
	<div class="vito-contentbd" id="divCommentPost">
		<p class="posttop vito-postcomment-title">
<!--		&nbsp;性能优化中，暂时关闭评论,如有疑问请到<a href="/leaveMessage.jsp">网站留言板</a>。-->
			<a href="javascript:void(0)" onclick="showOrHideDiv('commentDiv');">点击这里 发表评论</a>
		</p>
		<div id="commentDiv" style="display: none">
			<form id="frmSumbit" target="_self" method="post" action="/reply" onsubmit="return bd_sub();">
				<div class="vito-ct-id">
					<input type="hidden" name="bid" value="<%=blog.getId()%>">
					<input type="hidden" name="title" value="<%=blog.getTitle()%>">
					<input type="hidden" name="op" value="add">
					<%
						String gustName="游客";
						String gustEmail="";
						String gustURL="";
				   	%>
					<input type="text" name="name" id="comment_name" class="text vito-contentbd-input" value="<%=gustName %>" size="28" style="color: gray;"
					onclick="if(this.value=='<%=gustName %>'){this.value='';this.style.color='';}" 
					onblur="if(this.value==''){this.value='<%=gustName %>';this.style.color='gray';}"/>
					<label for="comment_name">署名(*)</label>
					
				</div>
				<div class="vito-ct-id">
					<input type="text" name="email" id="inpEmail"
						class="text vito-contentbd-input" value="<%=gustEmail %>" size="28"/>
					<label for="inpEmail">邮箱</label>
				</div>
				<div class="vito-ct-id">
					<input type="text" name="url" id="inpHomePage" value="<%=gustURL %>"
						class="text vito-contentbd-input" size="28"/>
					<label for="inpHomePage">网站链接</label>
				</div>
					<div id="content-div"></div>
				<p>
					<input type="submit" value="提交"/>
				</p>
			</form>
		</div>
		<p class="postbottom">◎欢迎参与讨论，请在这里发表您的看法、交流您的观点。</p>
	</div>
</div>
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body><%
	}else{
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问的文章不存在_<%=blog_user.getpTitle() %></title>
	<jsp:include page="head.jsp"></jsp:include>
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
<div class="right-title">文章</div>
	<div class="notices">访问的文章不存在,<a href="/">返回首页</a></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<%} %>
</html>