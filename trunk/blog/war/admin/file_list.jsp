<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.Blog"%>
<%@page import="com.google.choujone.blog.dao.BlogTypeDao"%>
<%@page import="com.google.choujone.blog.entity.BlogType"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.choujone.blog.dao.DataFileDao"%>
<%@page import="com.google.choujone.blog.entity.DataFile"%>
<%@page import="com.google.choujone.blog.util.Tools"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/jquery.js"></script>
<title>文件列表</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			管理文件 
	</div>
	<%
		DataFileDao dataFileDao=new DataFileDao();
		int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
		Pages pages=new Pages();
		pages.setPageNo(p);
		//查询所有的分类
		List<DataFile> files=dataFileDao.getDataFileListByPage(pages);
	%>
	<div class="main-title">
		所有文件(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" value="删除" onclick="">
		</span>
		<span class="tools-right">
			共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
			<%if(p > 1){ %>
				<a href="/admin/file_list.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
			<%} %>
			<%if(p < pages.getPageTotal()){ %>
				<a href="/admin/file_list.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
			<%} %>
		</span>
	</div>

	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox" id="input_check_all" onclick="allCheckFlag(this)"/></th>
				<th>图片</th>
				<th width="300px">filename</th>
				<th>size</th>
				<th>postDate</th>
				<th>description</th>
				<th>isShow</th>
				<th >操作</th>
			</tr>
			<%if(files!= null && files.size()>0){
				for(int i=0;i<files.size();i++){
				DataFile file=files.get(i); %>
			<tr>
				<td class="vito-content-check"><input type="checkbox" id="rid_<%=file.getId() %>" class="input_check_single" onclick="singleDeleteFlag(this)" name="deleteFlag" value="<%=file.getId() %>"/></td>
				<td>
					<img alt="" src="/file/<%=file.getId() %>_<%=file.getFilename() %>" height="100px;" width="100px">
				</td>
				<td><%=file.getFilename() %></td>
				<td><%=file.getSize()/1024 %>KB</td>
				<td><%=Tools.changeTime(file.getPostDate()) %></td>
				<td><%=file.getDescription() %></td>
				<td><%=file.getIsShow() %></td>
				<td></td>
			</tr>
			<%}} %>
		</table>
	</div>
	<%
		dataFileDao.closePM();
	%>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>