<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.Reply,com.google.choujone.blog.dao.ReplyDao,com.google.choujone.blog.common.Pages,java.util.List,com.google.choujone.blog.entity.User,com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.util.Config"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
UserDao ud=new UserDao();
User blog_user=  ud.getUserDetail();
%><title>留言板 _<%=blog_user.getpTitle()%></title>
	<jsp:include page="head.jsp"></jsp:include>
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
	<div class="right-title">留言板</div>
	<div style="font-family: 微软雅黑, 宋体, Arial, Helvetica, sans-serif; font-size: 14px; color: #ffd247; padding: 0 0 10px 20px;">主人寄语</div>
	<div><%=blog_user.getPreMessage().getValue() %></div>
	<%
		int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
		ReplyDao replyDao=new ReplyDao();
		Pages pages=new Pages();
		pages.setPageNo(p);
		List<Reply> replyList= replyDao.getReplyListByBid(-1L,pages);
	%>
	<div class="vito-prenext">
	共<%=pages.getRecTotal() %>条  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页
	<%if(p > 1){ %>
		<a href="/leaveMessage.jsp?p=<%=p-1 %>">上一页</a>
	<%} %>
	<%if(p < pages.getPageTotal()){ %>
		<a href="/leaveMessage.jsp?p=<%=p + 1 %>">下一页</a>
	<%} %>
</div>
	<%for(int i=0;i<replyList.size();i++){
		Reply reply=replyList.get(i); %>
	<div class="vito-postcommentlist">
		<span class="vito-postcomment-one">
			<span class="vito-postcomment-name" style="color: #8c8c8c">
				第<%=pages.getRecTotal()-(i+((pages.getPageNo()-1)*pages.getPageSize())) %>楼| <%=reply.getName() %>
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
	共<%=pages.getRecTotal() %>条  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页
	<%if(p > 1){ %>
		<a href="/leaveMessage.jsp?p=<%=p-1 %>">上一页</a>
	<%} %>
	<%if(p < pages.getPageTotal()){ %>
		<a href="/leaveMessage.jsp?p=<%=p + 1 %>">下一页</a>
	<%} %>
</div>
	<div class="vito-contentbd" id="divCommentPost">
		<p class="posttop vito-postcomment-title">
			<a href="javascript:void(0)" onclick="showOrHideDiv('commentDiv');showForm();">点击这里 发表评论</a>
		</p>
		<div id="commentDiv" style="display: none;">
			<form id="frmSumbit" name="frmSumbit" target="_self" method="post" action="#" onsubmit="return bd_sub()">
				<div class="vito-ct-id">
				<%
						String gustName="游客";
						String gustEmail="";
						String gustURL="";
				   	%>
					<input type="hidden" name="op" value="add">
					<input type="hidden" name="title" value="网站留言中心">
					<input type="text" name="name" id="comment_name"
						class="text vito-contentbd-input" value="<%=gustName %>" size="28"/>
					<label for="comment_name">署名(*)</label>
				</div>
				<div class="vito-ct-id">
					<input type="text" name="email" id="inpEmail"
						class="text vito-contentbd-input" value="<%=gustEmail %>" size="28"/>
					<label for="inpEmail">邮箱</label>
				</div>
				<div class="vito-ct-id">
					<input type="text" name="url" value="<%=gustURL %>" id="inpHomePage"
						class="text vito-contentbd-input" size="28"/>
					<label for="inpHomePage">网站链接</label>
				</div>
					<div id="content-div"></div>
				<p><input type="submit" value="提交"/></p>
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