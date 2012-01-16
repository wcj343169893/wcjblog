<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.dao.BlogDao"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.Blog"%>
<%@page import="com.google.choujone.blog.entity.Reply"%>
<%@page import="com.google.choujone.blog.dao.ReplyDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/jquery.js"></script>
<title>评论列表</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
		管理评论
	</div>
		<%
		int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
		ReplyDao replyDao=new ReplyDao();
		BlogDao blogDao=new BlogDao();
		Pages pages=new Pages();
		pages.setPageNo(p);
		List<Reply> replyList= replyDao.getReplyList(pages);
	%>
	<div class="main-title">
		所有评论(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" value="删除" onclick="deletes('/reply')">
		</span>
		<span class="tools-search">
			<input type="text" name="name"><input type="button" value="查询">
		</span>
		<span class="tools-right">
			共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
			<%if(p > 1){ %>
				<a href="/admin/reply_list.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
			<%} %>
			<%if(p < pages.getPageTotal()){ %>
				<a href="/admin/reply_list.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
			<%} %>
		</span>
	</div>
	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox" id="input_check_all" onclick="allCheckFlag(this)"/></th>
				<th>编号</th>
				<th width="200px;">文章标题</th>
				<th>评论者</th>
				<th width="400px;">评论内容</th>
				<th width="300px;">回复内容</th>
				<th>评论时间 </th>
				<th>回复时间</th>
			</tr>
			<%if(replyList!= null && replyList.size()>0){
				a:for(int i=0;i<replyList.size();i++){
				Reply reply=replyList.get(i); 
				Blog b=null;
				if(reply.getBid()>0){
					b=blogDao.getBlogById(reply.getBid());
					if(b==null){
						break a;
					}
				}
				long blogid=-1L;
				%>
			<tr>
				<td class="vito-content-check"><input type="checkbox" id="rid_<%=reply.getId() %>" class="input_check_single" onclick="singleDeleteFlag(this)" name="deleteFlag" value="<%=reply.getId() %>" /></td>
				<td class="vito-title"><label for="rid_<%=reply.getId() %>"><%=reply.getId() %></label></td>
				<td class="vito-title">
				<% if(reply.getBid()>0 && b!=null){blogid=b.getId();%>
					<a href="/blog_detail.jsp?id=<%=b.getId() %>" target="_blank" title='查看  <%=b.getTitle() %>'><%=b.getTitle() %></a>
				<%}else{ %>
					<a href="/leaveMessage.jsp" target="_blank" title='查看  网站留言'>网站留言</a>
				<%} %>
				</td>
				<td><%=reply.getName()%><div class="explain"><table><tr><td>NAME</td><td><%=reply.getName()%></td></tr><tr><td>EMAIL</td><td><%=reply.getEmail() %></td></tr><tr><td>URL</td><td><%=reply.getUrl() %></td></tr><tr><td>其他信息</td><td><%=reply.getVisiter() %></td></tr></table></div>
				</td>
				<td><%=reply.getContent()%></td>
				<td ondblclick="showOrHideDiv('div-msg-<%=i %>')">
					<%=reply.getReplyMessage()%>
					<div id="div-msg-<%=i %>" style="display: none;">
						<input type="text" value="<%=reply.getReplyMessage()%>" id="msg-<%=i %>" onkeydown="responseEnter(event,'<%=reply.getId() %>','<%=blogid %>','<%=p %>','msg-<%=i %>')"><input type="button" onclick="replys('<%=reply.getId() %>','<%=blogid %>','<%=p %>','msg-<%=i %>')" value="回复">
					</div>
				</td>
				<td><%=reply.getSdTime() %> </td>
				<td><%=reply.getReplyTime() %> </td>
			</tr>
			<%}} %>
		</table>
	</div>
	<script type="text/javascript">
		function replys(id,bid,p,msg){
				var message=	document.getElementById(msg).value;
				to("/reply?op=modify&id="+id+"&msg="+message+"&bid="+bid+"&p="+p);
			}
		function responseEnter(e,id,bid,p,msg) {
            var key = window.event ? e.keyCode : e.which;
            if (key == 13) {
            	replys(id,bid,p,msg);
            }
        }
		jQuery(function($) {
				$(".explain").each(function(index, domEle) {
					$(this).parent().hover(
							  function () {
								  $(domEle).show();
							  },
							  function () {
								  $(domEle).hide();
							  }
							);
					});
			});
	</script>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>