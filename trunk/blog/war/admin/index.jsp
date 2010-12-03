<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/3d.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/js/3d.js"></script>
<%
	UserDao userDao=new UserDao();
	User blog_user= userDao.getUserDetail();
%>
<title><%=blog_user.getpTitle()%> -- 后台管理</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
		<div class="vito-middle">
			<div id="screen">
				<div id="command">
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
			{ src: 'duck.jpg', url: 'http://yooyoor.appspot.com/blog_initBlogInfo.action?article.articleId=16', title: '点击进入', color: '#fff' },
			{ src: 'juan.jpg' },
			{ src: 'me.jpg' },
			{ src: 'metoo.jpg' },
			
			{ src: 'mumengmei.jpg' },
			{ src: 'qinqin.gif' },
			{ src: 'tiantian.jpg' },
			{ src: 'xiaoyan.jpg' }
		]
	);
}, 500);
</script>
		</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>