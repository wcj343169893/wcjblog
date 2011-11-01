<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.Reply"%>
<%@page import="com.google.choujone.blog.dao.ReplyDao"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	User login_user=(User)request.getSession().getAttribute("login_user");//获取登录信息
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>
<title>留言板  --  <%=blog_user.getpTitle()%></title>
<meta name="google-site-verification" content="0YKCfiBLHIYnG9LLMoVWT5MahWg50_rrDxRm9gcmM7k" />
<meta name="keywords" content="<%=blog_user.getBlogKeyword() %>">
<meta name="description" content="<%=blog_user.getBlogDescription() %>">
<%
	if(blog_user.getBlogHead()!=null && !"".equals(blog_user.getBlogHead().trim())){
		out.print(blog_user.getBlogHead());
	}
%>
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
<script type="text/javascript" src="/js/content.js"></script>
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
	<div class="right-title">
		留言板
	</div>
	<div
		style="font-family: 微软雅黑, 宋体, Arial, Helvetica, sans-serif; font-size: 14px; color: #ffd247; padding: 0 0 10px 20px;">
		主人寄语
	</div>
	<div>
		<%=blog_user.getPreMessage().getValue() %>
	</div>
	<%
		int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
		ReplyDao replyDao=new ReplyDao();
		Pages pages=new Pages();
		pages.setPageNo(p);
		List<Reply> replyList= replyDao.getReplyListByBid(-1L,pages);
	%>
	<div class="vito-prenext">
	共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
	<%if(p > 1){ %>
		<a href="/leaveMessage.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
	<%} %>
	<%if(p < pages.getPageTotal()){ %>
		<a href="/leaveMessage.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
	<%} %>
</div>
	<%for(int i=0;i<replyList.size();i++){
		Reply reply=replyList.get(i); %>
	<div class="vito-postcommentlist">
		<span class="vito-postcomment-one">
			<span class="vito-postcomment-name" style="color: #8c8c8c">
				第<%=pages.getRecTotal()-(i+((pages.getPageNo()-1)*pages.getPageSize())) %>楼&nbsp;|&nbsp; <%=reply.getName() %>
				<span style="color: #979797"><%=reply.getSdTime() %> 说</span>
			</span>
			<br><br>
			<span class="vito-postcomment-content">
				<%=reply.getContent() %>
				<%if(reply.getReplyMessage() != null && !"".equals(reply.getReplyMessage().trim())){ %>
				    <blockquote>
						<div class="quote quote3">
							<div class="quote-title">
								<%=blog_user.getName()%> 于 <%=reply.getReplyTime() %>回复
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
		<div class="vito-prenext">
	共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
	<%if(p > 1){ %>
		<a href="/leaveMessage.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
	<%} %>
	<%if(p < pages.getPageTotal()){ %>
		<a href="/leaveMessage.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
	<%} %>
</div>
	<div class="vito-contentbd" id="divCommentPost">
		<p class="posttop vito-postcomment-title">
			<a href="javascript:void(0)" onclick="showOrHideDiv('commentDiv')">点击这里 发表评论</a>
		</p>
		<div id="commentDiv" style="display: none;">
			<form id="frmSumbit" name="frmSumbit" target="_self" method="post" action="/reply">
				<div class="vito-ct-id">
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
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>