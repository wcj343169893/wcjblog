<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.dao.FriendsDao"%>
<%@page import="com.google.choujone.blog.entity.Friends"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.choujone.blog.util.Config"%><html><%	List<String> ftList=Config.getFtList();%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>友情链接</title>
    <link rel="stylesheet" type="text/css" href="/css/ui-lightness/jquery-ui.css" />
	<script type="text/javascript" src="/js/jquery.js"></script>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<script type="text/javascript" charset="utf-8" src="/js/jquery-ui.js"></script>
	<div class="address">
			管理朋友(<a href="javascript:void(0);" id="c_new_f">新建</a>) 
			<a href="javascript:void(0);" onclick="clearCache('/friends')">清理缓存</a>
	</div>
			<div class="friends-new" id="friends-modify" title="修改链接">
			</div>
			<div class="friends-new" id="friends-new" title="新增链接">
			<form action="/friends" method="post" id="friends-new-form">
				<input type="hidden" name="op" value="add">
				<div><div class="container-title-header">名称:</div><input name="name"></div>
				<div><div class="container-title-header">分类：</div><select name="tid" id="f_t_l"><%for(int i=0;i<ftList.size();i++){%><option value="<%=i %>"><%=ftList.get(i) %></option><%} %></select></div>
				<div><div class="container-title-header">链接地址：</div><input name="url"></div>
				<div><div class="container-title-header">推荐：</div>
				<input name="istop" type="radio" value="0" id="istop_n" checked><label for="istop_n">不推荐</label>
				<input name="istop" type="radio" value="1" id="istop_y"><label for="istop_y">推荐</label>
				</div>
				<div><div class="container-title-header">描述：</div><textarea cols="30" rows="5" name="description"></textarea></div>
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
		所有链接(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" value="删除" onclick="deletes('/friends')">
			<input type="button" value="推荐" onclick="tops('/friends?op=ttop')">
			<input type="button" value="取消推荐" onclick="tops('/friends?op=dtop')">
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
				<th class="vito-content-check"><input type="checkbox" id="input_check_all" onclick="allCheckFlag(this)"/></th>
				<th width="200px">名称</th>
				<th width="200px">链接</th>
				<th width="150px">分类</th>
				<th>描述</th>
				<th width="150px">创建时间 </th>
				<th width="100px">操作 </th>
			</tr>
			<%if(friendsList!= null && friendsList.size()>0){
				for(int i=0;i<friendsList.size();i++){
				Friends friends=friendsList.get(i); %>
			<tr>
				<td class="vito-content-check"><input type="checkbox" id="rid_<%=friends.getId() %>" class="input_check_single" onclick="singleDeleteFlag(this)" name="deleteFlag" value="<%=friends.getId() %>"/></td>
				<td class="vito-title"><%=friends.getName() %> </td>
				<td><a href="<%=friends.getUrl() %>" title='查看  "<%=friends.getName() %>"' target="_bank"><%=friends.getUrl() %></a> </td>
				<td><%for(int j=0;j<ftList.size();j++){if(friends.getTid()!=null && friends.getTid().equals(j)){out.print(ftList.get(j));}}%></td>
				<td><%=friends.getDescription()%></td>
				<td><%=friends.getSdTime() %> </td>
				<td>
				<%if(friends.getIstop()!=null && friends.getIstop().equals(1)){ %>
					<a href="/friends?op=dtop&id=<%=friends.getId() %>">取消推荐</a> 
				<%}else{ %>
					<a href="/friends?op=ttop&id=<%=friends.getId() %>">推荐</a> 
				<%} %>
				<a href="javascript:void(0)" name="<%=friends.getId() %>" class="f_modify">修改</a>
				</td>
			</tr>
			<%}} %>
		</table>
	</div>
	<script type="text/javascript">
	$(function() {
			$("#friends-modify").dialog({
				autoOpen : false,
				width : 440,
				buttons:{
					"保存链接" : function() {
						$("#friends-modify>form").submit();
					},
					"取消":function(){
							$(this).dialog("close");
						}
					}
				});
			$("#friends-new").dialog({
				autoOpen : false,
				width : 440,
				buttons:{
				"保存链接" : function() {
					$("#friends-new>form").submit();
				},
				"取消":function(){
						$(this).dialog("close");
					}
				}
				});
			$("#c_new_f").bind("click",function(){$("#friends-new").dialog('open');});
			$(".f_modify").bind("click", function(){
				$("#friends-modify").dialog('open');
				jQuery.getJSON("/friends?op=modify&id="+$(this).attr("name"),function(data){
					$("#friends-modify").html("");
					$("#friends-modify").append('<form action="/friends" method="post"></form>');
					$(data).each(function(index,domEle){
						$("#friends-modify>form").append('<input type="hidden" name="op" value="modify">');
						$("#friends-modify>form").append('<input type="hidden" name="id" value="'+domEle.id+'">');
						$("#friends-modify>form").append('<div><div class="container-title-header">名称:</div><input name="name"  value="'+domEle.name+'"></div>');
						var types=$("#f_t_l").html();
						$("#friends-modify>form").append('<div><div class="container-title-header">分类:</div><select name="tid" id="f_t_l_m">'+types+'</select></div>');
						$("#f_t_l_m").val(domEle.tid);//选择下拉菜单
						$("#friends-modify>form").append('<div><div class="container-title-header">链接:</div><input name="url"  value="'+domEle.url+'"></div>');
						$("#friends-modify>form").append('<div><div class="container-title-header">推荐:</div><input name="istop" type="radio" value="0" id="istop_n" '+(domEle.istop==0 ? "checked":"")+'><label for="istop_n">不推荐</label><input name="istop" type="radio" value="1" id="istop_y" '+(domEle.istop==1 ? "checked":"")+'><label for="istop_y">推荐</label>');
						$("#friends-modify>form").append('<div><div class="container-title-header">描述:</div><textarea cols="30" rows="5" name="description">'+domEle.description+'</textarea></div>');
					});
					});
				});
		});
	</script>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>