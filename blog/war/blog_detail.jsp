<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><%@page import="com.google.choujone.blog.dao.BlogDao,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.util.Tools,com.google.choujone.blog.dao.ReplyDao,com.google.choujone.blog.common.Pages,java.util.List,com.google.choujone.blog.entity.Reply,com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.common.Operation,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%><html><%
	String id = request.getParameter("id")!=null ? request.getParameter("id") : (String)request.getAttribute("id");
	boolean isOk=false;
	BlogDao blogDao=null;
	Blog blog=null;
	if (id != null && !"".equals(id.trim())) {
		isOk=true;
		blogDao = new BlogDao();
		blog = blogDao.getBlogById(Tools.strTolong(id));
		if (blog == null) {
			isOk=false;
		}
	} 
	if(isOk) {
			blogDao.operationBlog(Operation.readTimes, blog);
			//Blog preBlog = blogDao.getPreBlog(blog.getId());
			Blog preBlog = null;
			//Blog nextBlog = blogDao.getNextBlog(blog.getId());
			Blog nextBlog = null;
			//查询所有的分类
			BlogTypeDao btd = new BlogTypeDao();
			BlogType bt = btd.getBlogTypeById(blog.getTid());
			User blog_user =Config.getBlog_user();
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
	<!-- 直接查询评论 -->
			<%
				int p = request.getParameter("p") != null ? Integer
								.parseInt(request.getParameter("p").toString()) : 1;
						ReplyDao replyDao = new ReplyDao();
						Pages pages = new Pages();
						pages.setPageNo(p);
						List<Reply> replyList = replyDao.getReplyListByBid(blog
								.getId(), pages);
			%>
	<div
		style="font-family: 微软雅黑, 宋体, Arial, Helvetica, sans-serif; font-size: 14px; color: #ffd247; padding: 0 0 10px 20px;">评论列表</div>
	<div class="vito-postcommentlist">
	<%
		if (replyList != null && replyList.size() > 0) {
	%>
		<div id="comment">
			<%
				for (int i = 0; i < replyList.size(); i++) {
								Reply reply = replyList.get(i);
			%>
			<div class="vito-postcommentlist">
				<span class="vito-postcomment-one">
					<span class="vito-postcomment-name" style="color: #8c8c8c">
						<%
							int louceng=(pages.getPageNo()-1)*pages.getPageSize()+i+1;
							if (louceng == 1) {out.print("沙发");} 
							else if (louceng == 2) {out.print("板凳");} 
							else if (louceng == 3) {out.print("平地");
							} else {out.print("第" + louceng+ "楼");}
						
						String url=reply.getUrl()!=null && !"".equals(reply.getUrl().trim()) ?reply.getUrl(): blog_user.getUrl();
						url=url.indexOf("http://")!=-1?url:"http://"+url;
						%>|<a href="<%=url %>"><%=reply.getName()%></a>
						<span style="color: #979797"><%=reply.getSdTime()%>说</span>
					</span>
					<br><br>
					<span class="vito-postcomment-content">
						<%=reply.getContent() %>
						<%
							if (reply.getReplyMessage() != null
													&& !"".equals(reply.getReplyMessage()
															.trim())) {
						%>
						    <blockquote>
								<div class="quote quote3">
									<div class="quote-title"><%=blog_user.getName()%>于 <%=reply.getReplyTime()%>回复</div>
									<%=reply.getReplyMessage()%>
								</div>
							</blockquote>
							<%
								}
							%>
						</span>
					</span>
				<span class="vito-postcomment-reback"> </span>
			</div>
			<%
				}
			%>
		</div>
		<br>
		<div class="vito-prenext">共<%=pages.getRecTotal()%>第<%=pages.getPageNo()%>/<%=pages.getPageTotal()%>页<%
			if (p > 1) {
		%><a href="/blog?p=<%=p - 1%>&id=<%=blog.getId()%>">上一页</a><%
			}
		%><%
			if (p < pages.getPageTotal()) {
		%><a href="/blog?p=<%=p + 1%>&id=<%=blog.getId()%>">下一页</a><%
			}
		%></div>
		<%
			} else {
		%>
		<div class="vito-prenext">还没有人发表评论<a href="javascript:void(0)" onclick="showOrHideDiv('commentDiv');$('#comment_name').focus();">来坐第一个沙发</a></div>
		<%
			}
		%>
	</div>
	<div class="vito-contentbd" id="divCommentPost">
		<p class="posttop vito-postcomment-title">
			<a href="javascript:void(0)" onclick="showOrHideDiv('commentDiv');$('#comment_name').focus();">点击这里 发表评论</a>
		</p>
		<div id="commentDiv" style="display: none">
			<form id="frmSumbit" target="_self" method="post" action="/reply" onsubmit="return bd_sub()">
				<div class="vito-ct-id">
					<input type="hidden" name="bid" value="<%=blog.getId()%>">
					<input type="hidden" name="p" value="<%=p %>">
					<input type="hidden" name="op" value="add">
					<%
				     	UserService userService = UserServiceFactory.getUserService();
						String gustName="游客";
						String gustEmail="";
						String gustURL="";
				     	if (userService.isUserLoggedIn()){
				     		gustName=userService.getCurrentUser().getNickname();
				     		gustEmail=userService.getCurrentUser().getEmail();
				     		gustURL=userService.getCurrentUser().getAuthDomain();
				     	}
				   	%>
					<input type="text" name="name" id="comment_name" class="text vito-contentbd-input" value="<%=gustName %>" size="28" style="color: gray;"
					onclick="if(this.value=='<%=gustName %>'){this.value='';this.style.color='';}" 
					onblur="if(this.value==''){this.value='<%=gustName %>';this.style.color='gray';}"/>
					<label for="comment_name">署名(*)</label><%if (!userService.isUserLoggedIn()){%><a href="<%=userService.createLoginURL("/blog?id="+id)%>" >google账号登陆</a><%} %>
					
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
<title>访问的文章不存在</title>
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