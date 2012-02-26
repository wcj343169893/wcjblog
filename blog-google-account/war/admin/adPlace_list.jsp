<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.choujone.blog.entity.AdPlace"%>
<%@page import="com.google.choujone.blog.dao.AdPlaceDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/jquery.js"></script>
<title>广告列表</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			管理广告
			<a href="">添加广告位</a>
			<a href="">广告位管理</a>
			<a href="">添加广告</a>
			<a href="">广告</a>
			<a href="">更新广告</a>
	</div>
		<%
	AdPlaceDao adPlaceDao = new AdPlaceDao();
	int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	Pages pages=new Pages();
	pages.setPageNo(p);
	List<AdPlace> adPlaces = adPlaceDao.getAdPlaceListByPage(pages);
	%>
	<div class="main-title">
		所有广告(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" value="删除" onclick="deletes('/adPlace')">
		</span>
		<span class="tools-right">
			共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
			<%if(p > 1){ %>
				<a href="/admin/adPlace_list.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
			<%} %>
			<%if(p < pages.getPageTotal()){ %>
				<a href="/admin/adPlace_list.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
			<%} %>
		</span>
	</div>

	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox" id="input_check_all" onclick="allCheckFlag(this)"/></th>
				<th width="300px">广告位名称</th>
				<th width="300px">默认代码</th>
				<th>创建时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<%if(adPlaces!= null && adPlaces.size()>0){
				for(int i=0;i<adPlaces.size();i++){
					AdPlace adPlace=adPlaces.get(i); 
				%>
			<tr>
				<td class="vito-content-check"><input type="checkbox" id="rid_<%=adPlace.getId() %>" class="input_check_single" onclick="singleDeleteFlag(this)" name="deleteFlag" value="<%=adPlace.getId() %>"/></td>
				<td class="vito-title">&nbsp;&nbsp;<a href="/adPlace?id=<%=adPlace.getId() %>&op=modify" title='修改   "<%=adPlace.getName() %>"'>[ 编辑 ]</a> &nbsp;  <a href="/adPlace?id=<%=adPlace.getId() %>" target="_blank" title='查看  "<%=adPlace.getName() %>"'><%=adPlace.getName() %></a></td>
				<td><%=adPlace.getCode() %> </td>
				<td><%=adPlace.getSdTime() %> </td>
				<td><%=adPlace.getIsVisible() == 0 ? "发布" : "隐藏" %></td>
				<td>
				<% 
					if(adPlace.getIsVisible() == 0){
				%>
					<a href="/ad?id=<%=adPlace.getId() %>&op=delete&isVisible=1">隐藏</a>
				<%}else{ %>
					<a href="/ad?id=<%=adPlace.getId() %>&op=delete&isVisible=0">发布</a>
				<%} %>
				</td>
			</tr>
			<%}} %>
		</table>
	</div>
	
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>