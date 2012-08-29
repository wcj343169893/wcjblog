<%@page import="com.google.choujone.blog.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.UserDao"%><html><%
	UserDao ud=new UserDao();
    User blog_user=ud.getUserDetail();
%><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>征集文章_<%=blog_user.getpTitle()%></title><%
	if (blog_user.getBlogHead() != null
					&& !"".equals(blog_user.getBlogHead().trim())) {
				out.print(blog_user.getBlogHead());
			}
String key="征集文章 "+""+blog_user.getpTitle();
%>
<jsp:include page="head.jsp"><jsp:param value="<%=key %>" name="kw"/><jsp:param value="为了丰富本博客，故向广大网友征集文章，题材不限，字数不限。" name="desc"/></jsp:include>
<script type="text/javascript" src="/kindeditor/kindeditor.js"></script>
<script type="text/javascript" src="/js/content.js"></script>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<div class="mod-blogpage-wraper">
	<div class="blog-bg-main-repeat hide"></div>
   	<div class="grid-80 mod-blogpage">
       <div class="blog-bg-main hide"></div>
	   <div class="mod-text-content mod-post-content"> 
	   		<h1>征集文章</h1>
	   		<div>我的博客已经无法阻挡你在这里发表文章，只要你觉得有用，就大胆的写出来吧，我期待你的光临。</div>
	   		<div>&nbsp;</div>
	   		<div>要求：
		   		<ul>
		   			<li>文章需要原创</li>
		   			<li>不违法</li>
		   			<li>文章类型最好是与计算机、网络相关</li>
		   		</ul>
	   		</div>
	   		<div>&nbsp;</div>
	   		<div>注意：
	   			<ul>
	   				<li>文章会在审核之后展现出来</li>
	   				<li>不接受灌水</li>
	   				<li>需要GOOGLE账户登录</li>
	   				<li>允许带其他网站链接</li>
	   				<li>测试阶段，每日限制10条</li>
	   			</ul>
	   		</div>
	   		<div>&nbsp;<hr/><br/></div>
	   		<%//判断是否登录 %>	
   		 	<div class="qcmt-wraper-box-comment">
	   		<div class="qcmt-input-box clearfix">
		        <div class="qcmt-input-textarea-box">
		   		<form action="/" method="post" id="guestForm">
		   		 <div class="qcmt-textarea-wraper">
		   			<div class="qcmt-comment-name">
						<label for="blog_name">标题：</label> <input type="text" name="name" id="blog_name" class="text vito-contentbd-input" value="" size="28" style="color: gray;"/>
				   	</div>
		   			<div class="qcmt-comment-name">
						<label for="blog_type">分类：</label> <input type="text" name="type" id="blog_type" class="text vito-contentbd-input" value="" size="28" style="color: gray;"/>
				   	</div>
				   	<div class="qcmt-textarea-minishadow">
				   		<div id="content-div"></div>
	                </div>
	                 <div class="qcmt-sub-bt-box">
	                    <a href="javascript:jQuery('#guestForm').submit();" class="cmt-add button button-save button-cmt clearfix"><span class="button-left">&nbsp;</span>
	                    <span class="button-text">发布</span>	<span class="button-right">&nbsp;</span></a>
                    </div>
	              </div>
		   		</form>
	   		</div>
	   		</div>
	   		</div>
	   </div>
   </div>
</div>
 </div>
<!-- 左边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>