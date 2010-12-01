<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.dao.FriendsDao"%>
<%@page import="com.google.choujone.blog.entity.Friends"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友情链接</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			管理朋友(<a href="javascript:void(0);" onclick="showOrHideDiv('friends-new')">新建</a>) 
	</div>
			<%
				Friends	fri=(Friends)request.getAttribute("friends");
				if(fri!=null){
			%>
			<div class="friends-new">
			<form action="/friends" method="post">
				<input type="hidden" name="op" value="modify">
				<input type="hidden" name="id" value="<%=fri.getId() %>">
				修改：姓名:<input name="name" value="<%=fri.getName() %>">&nbsp;&nbsp; 链接:<input name="url" value="<%=fri.getUrl() %>"> &nbsp;&nbsp;描述:<input name="description" value="<%=fri.getDescription() %>"> <input type="submit" value="保存">
			</form>
			</div>
			<%} %>
			<div class="friends-new" id="friends-new" style="display: none;">
			<form action="/friends" method="post">
				<input type="hidden" name="op" value="add">
				新增：姓名:<input name="name"> &nbsp;&nbsp;链接:<input name="url"> &nbsp;&nbsp;描述:<input name="description"> <input type="submit" value="保存">
			</form>
			</div>
		<%
		FriendsDao friendsDao=new FriendsDao();
		int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
		Pages pages=new Pages();
		pages.setPageNo(p);
		List<Friends> friendsList=friendsDao.getFriendsByPage(pages);
	%>
	<div class="main-title">
		所有朋友(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" onclick="alert('删除中....')" value="删除">
		</span>
		<span class="tools-right">
			共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
			<%if(p > 1){ %>
				<a href="/admin/friends_list.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
			<%} %>
			<%if(p < pages.getPageTotal()){ %>
				<a href="/admin/friends_list.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
			<%} %>
		</span>
	</div>

	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox"/></th>
				<th width="100px;">姓名</th>
				<th>链接</th>
				<th>描述</th>
				<th>创建时间 </th>
			</tr>
			<%if(friendsList!= null && friendsList.size()>0){
				for(int i=0;i<friendsList.size();i++){
				Friends friends=friendsList.get(i); %>
			<tr>
				<td class="vito-content-check"><input type="checkbox" value="<%=friends.getId() %>"/></td>
				<td class="vito-title"> <a href="/friends?op=modify&id=<%=friends.getId() %>">[修改]</a> <%=friends.getName() %> </td>
				<td><a href="<%=friends.getUrl() %>" title='查看  "<%=friends.getName() %>"' target="_bank"><%=friends.getUrl() %></a> </td>
				<td><%=friends.getDescription()%></td>
				<td><%=friends.getSdTime() %> </td>
			</tr>
			<%}} %>
		</table>
	</div>
	
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>