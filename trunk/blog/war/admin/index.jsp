<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.dao.DataFileDao"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.DataFile"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="com.google.choujone.blog.util.Tools"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/3d.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/js/3d.js"></script>
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
	DataFileDao dfDao=new DataFileDao();
	int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	Pages pages=new Pages();
	pages.setPageNo(p);
	pages.setPageSize(24);
	List<DataFile> dataFileList=dfDao.getDataFileListByPage(pages);
%>
<title><%=blog_user.getpTitle()%> -- 后台管理</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
		<div class="vito-middle">
			<div id="screen">
				<div id="command">
					<%if(p > 1){ %>
						<a href="/admin/index.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
					<%} %>
					<%if(p < pages.getPageTotal()){ %>
						<a href="/admin/index.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
					<%} %>
					<br>
					可以选择下面的方块来选择图片
					<div id="bar"></div>
				</div>
				<div id="urlInfo"></div>
			</div>
			<script type="text/javascript">
/* ==== start script ==== */
setTimeout(function() {
	m3D.init(
		[ 
			<%for (DataFile df:dataFileList){String fi=Tools.getFileThumb(df.getId()+"_"+df.getFilename());%>
			{ src: '<%=fi%>', url: '', title: '<%=df.getFilename()%>', color: '#fff' },
			<%}%>
		]
	);
}, 500);
</script>
		</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>