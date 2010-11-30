<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.dao.BlogDao"%>
<%@page import="com.google.choujone.blog.entity.Blog"%>
<%@page import="com.google.choujone.blog.util.Tools"%>
<%@page import="com.google.choujone.blog.dao.ReplyDao"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.Reply"%>
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.common.Operation"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String id=request.getParameter("id");
	if(id!=null && !"".equals(id.trim())){
	BlogDao blogDao=new BlogDao();
	Blog blog=blogDao.getBlogById(Tools.strTolong(id));
	if(blog == null){
		out.println("文章不存在");
		return;
	}
	blogDao.operationBlog(Operation.readTimes,blog);
	Blog preBlog=blogDao.getPreBlog(blog.getId());
	Blog nextBlog=blogDao.getNextBlog(blog.getId());
	User login_user=(User)request.getSession().getAttribute("login_user");//获取登录信息
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>
<title><%=blog_user.getpTitle() %> -- <%=blog.getTitle() %></title>
<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script type="text/javascript" src="/js/content.js"></script>
<script type="text/javascript" src="/js/jquery.js"></script>
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
	<div class="vito-prenext">
	<%if(preBlog!=null){ %>
		<span style="float: left; margin: 0 0 0 20px;"><a class="l" href="/blog_detail.jsp?id=<%=preBlog.getId() %>">&laquo; <%=preBlog.getTitle(20) %></a></span>
	<%} %>
	<%if(nextBlog!=null){ %>
		<span style="float: right; margin: 0 0 0 20px;"><a class="l" href="/blog_detail.jsp?id=<%=nextBlog.getId() %>"><%=nextBlog.getTitle(20) %>&raquo;</a></span>
	<%} %>
	</div>
	<div class="vito-content-title">
		<%=blog.getTitle() %>
	</div>
	<div class="vito-content-date">
		<%=blog.getSdTime() %>
	</div>
	<div class="vito-detail-content-body">
		<%=blog.getContent().getValue() %>
	<br><br>
	<font class="post-tags">
		Tags:<%=blog.getTag() %>
	</font>
	<font class="post-footer">
			发布:<%=blog_user.getName() %> | 
			分类:<%=blog.getTid()%> | 
			评论:<%=blog.getReplyCount() %> | 
			浏览:<%=blog.getCount() %> 
				<%if(login_user!=null){	%> 
				| <a href="/blog?id=<%=blog.getId() %>&op=modify">修改</a> 
			<%} %>
	</font>
	</div>
	<!-- 直接查询评论 -->
			<%
			int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
				ReplyDao replyDao=new ReplyDao();
				Pages pages=new Pages();
				pages.setPageNo(p);
				List<Reply> replyList=	replyDao.getReplyListByBid(blog.getId(),pages);
			%>
	<div
		style="font-family: 微软雅黑, 宋体, Arial, Helvetica, sans-serif; font-size: 14px; color: #ffd247; padding: 0 0 10px 20px;">
		评论列表
	</div>
	<div class="vito-postcommentlist">
	<%if(replyList!= null && replyList.size()>0){ %>
		<div id="comment">
			<%for(int i=0;i<replyList.size();i++){
				Reply reply=replyList.get(i); %>
			<div class="vito-postcommentlist">
				<span class="vito-postcomment-one">
					<span class="vito-postcomment-name" style="color: #8c8c8c">
						<%=(i+1)*pages.getPageNo() %>&nbsp;|&nbsp; <%=reply.getName() %>
						<span style="color: #979797"><%=reply.getSdTime() %> 说</span>
					</span>
					<br><br>
					<span class="vito-postcomment-content">
						<%=reply.getContent() %>
						<%if(reply.getReplyMessage() != null && !"".equals(reply.getReplyMessage().trim())){ %>
						    <blockquote>
								<div class="quote quote3">
									<div class="quote-title">
										choujone 于 <%=reply.getReplyTime() %>回复
									</div>
									<%=reply.getReplyMessage() %>
								</div>
							</blockquote>
							<%} %>
						</span>
					</span>
				<span class="vito-postcomment-reback"> </span>
			</div>
			<%} %>
		</div>
		<div class="vito-prenext">
		共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
				<%if(p > 1){ %>
					<a href="/blog_detail.jsp?p=<%=p-1 %>&id=<%=blog.getId() %>">上一页</a>&nbsp;&nbsp;
				<%} %>
				<%if(p < pages.getPageTotal()){ %>
					<a href="/blog_detail.jsp?p=<%=p + 1 %>&id=<%=blog.getId() %>">下一页</a>&nbsp;&nbsp;
				<%} %>
		</div>
		<%} %>
	</div>
	<div class="vito-contentbd" id="divCommentPost">
		<p class="posttop vito-postcomment-title">
			<a href="javascript:void(0)" onclick="showOrHideDiv('commentDiv')">点击这里 发表评论</a>
		</p>
		<div id="commentDiv" style="display: none">
			<form id="frmSumbit" target="_self" method="post" action="/reply">
				<div class="vito-ct-id">
					<input type="hidden" name="bid" value="<%=blog.getId() %>">
					<input type="hidden" name="op" value="add">
					<input type="text" name="name"
						class="text vito-contentbd-input" value="" size="28"/>
					<label for="inpName">
						署名(*)
					</label>
				</div>
				<div class="vito-ct-id">
					<input type="text" name="email"
						class="text vito-contentbd-input" value="" size="28"/>
					<label for="inpEmail">
						邮箱
					</label>
				</div>
				<div class="vito-ct-id">
					<input type="text" name="url"
						class="text vito-contentbd-input" size="28"/>
					<label for="inpHomePage">
						网站链接
					</label>
				</div>
					<div id="content-div"></div>
				<p>
					<input type="submit" value="提交"/>
				</p>
			</form>
		</div>
		<p class="postbottom">
			◎欢迎参与讨论，请在这里发表您的看法、交流您的观点。
		</p>
	</div>

	
</div>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<%} %>
</html>