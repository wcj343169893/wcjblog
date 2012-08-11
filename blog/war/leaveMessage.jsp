<%@page import="java.net.URLDecoder"%>
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
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<div class="mod-blogpage-wraper">
        <div class="blog-bg-main-repeat hide"></div>
        <div class="grid-80 mod-blogpage">
            <div class="blog-bg-main hide"></div>
               <div id="commentDetail" class="mod-comment-detail clearfix">
                   <div class="comment-title">网站留言</div>
                   <div class="comment-content" style="display: block; overflow: visible; height: auto; ">
                   <div id="qcmt1910464" class="qcmt-wraper-box-comment">
                   	<div class="cmt-border-top"></div>
                    <div class="qcmt-arrow-wraper" style="left:653px;"><div class="qcmt-arrow-border"></div>
	                    <div class="qcmt-arrow-shadow"></div>
	                    <div class="qcmt-arrow"></div>
	                    <div class="qcmt-arrow-inner"></div>
                    </div>
                    <div class="qcmt-input-box clearfix">
	                    <div class="qcmt-input-textarea-box">
	                    	<form id="frmSumbit" target="_self" method="post" onsubmit="return bd_sub();" action="/reply">
								<input type="hidden" name="op" value="add">
								<input type="hidden" name="title" value="网站留言中心">
			                    <div class="qcmt-textarea-wraper">
					                 <%
										String gustName="游客";
										String gustEmail="";
										String gustURL="";
										boolean isCookied=false;
										Cookie[] allcookies=request.getCookies();
										if(allcookies!=null){
											for(int i=0;i<allcookies.length;i++){
												Cookie newCookie= allcookies[i];
												if(newCookie.getName().equals("gustName")){
													gustName=URLDecoder.decode(newCookie.getValue(), "UTF-8");
													isCookied=true;
													break;
												}
											}
										}
								   	%>
								   	<%if(!isCookied){ %>
									   	<div class="qcmt-comment-name">
											<input type="text" name="name" id="comment_name" class="text vito-contentbd-input" value="<%=gustName %>" size="28" style="color: gray;"
											onclick="if(this.value=='<%=gustName %>'){this.value='';this.style.color='';}" 
											onblur="if(this.value==''){this.value='<%=gustName %>';this.style.color='gray';}"/>
											<label for="comment_name">署名(*)</label>
									   	</div>
									   	<div class="qcmt-comment-name">
									   		<input type="text" name="email" id="inpEmail" class="text vito-contentbd-input" value="<%=gustEmail %>" size="28"/>
											<label for="inpEmail">邮箱</label>
									   	</div>				                    
									   	<div class="qcmt-comment-name">
									   		<input type="text" name="url" id="inpHomePage" value="<%=gustURL %>"
												class="text vito-contentbd-input" size="28"/>
											<label for="inpHomePage">网站链接</label>
									   	</div>
								   	<%}else{ %>	                    
								   		<div class="qcmt-comment-name">欢迎回来：<%=gustName %></div>
								   	<%} %>
								   	<div class="qcmt-textarea-minishadow">
				                    	<textarea class="qcmt-textarea-box" id="content" style="overflow: hidden; height: 63px; " name="content"></textarea>
				                    </div>
			                    </div>
		                    </form>
	                    </div>
	                    <div class="qcmt-sub-bt-box">
		                    <a href="javascript:jQuery('#frmSumbit').submit();" class="cmt-add button button-save button-cmt clearfix"><span class="button-left">&nbsp;</span>
		                    <span class="button-text">发布</span>	<span class="button-right">&nbsp;</span></a>
	                    </div>
                    </div>
                    <div class="qcmt-main-wraper">
                    <div class="qcmt-main-box">
                    <div class="qcmt-list-wraper">
                    	<div class="cmt-list" id="cmt-list"></div>
                    </div>
                    <div class="qcmt-footer-wraper">
	                    <div class="qcmt-footer-box clearfix">
		                    <a class="cmt-seemore cmt-seenext" href="javascript:initReply2(1,-1)">
			                    <span class="seemore-arrow hide"></span>
			                    <span class="seemore-tip cs-contentblock-link">查看更多</span>
		                    </a>
		                    <span class="seemore-loading"></span>
	                    </div>
                    </div>
                    </div>
                    </div>
                   </div>
               </div>
         </div>
     </div>
</div>
<script type="text/javascript">
jQuery(function($) {
	initReply2(1,-1);
});
</script>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>