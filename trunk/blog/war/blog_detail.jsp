<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><%@page import="com.google.choujone.blog.dao.BlogDao,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.util.Tools,com.google.choujone.blog.dao.ReplyDao,com.google.choujone.blog.common.Pages,java.util.List,com.google.choujone.blog.entity.Reply,com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.common.Operation,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><html><%
	if(request.getAttribute("id") == null){
		response.sendRedirect("/blog/"+request.getParameter("id"));
		return;
	}
	String id = request.getParameter("id")!=null ? request.getParameter("id") : (String)request.getAttribute("id");
	boolean isOk=false;
	BlogDao blogDao=null;
	Blog blog=null;
	UserDao ud=new UserDao();
    User blog_user=  ud.getUserDetail();
	if (id != null && !"".equals(id.trim())) {
		isOk=true;
		blogDao = new BlogDao();
		blog = blogDao.getBlogById(Tools.strTolong(id));
		if (blog == null) {
			isOk=false;
		}
	}
	if(isOk) {
			//blogDao.operationBlog(Operation.readTimes, blog);
			//更新阅读时间
			Config.addBlogReadCount(blog.getId());
			Blog preBlog = blogDao.getPreBlog(blog.getId());
			//Blog preBlog = null;
			Blog nextBlog = blogDao.getNextBlog(blog.getId());
			//Blog nextBlog = null;
			//查询所有的分类
			BlogTypeDao btd = new BlogTypeDao();
			BlogType bt = btd.getBlogTypeById(blog.getTid());
			String keywords=blog.getTitle()+","+(bt!=null ? bt.getName(): "默认分类")+","+blog.getTag()+","+blog_user.getpTitle();
			String description=blog.getContent(150,"").getValue().trim();
%><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=blog.getTitle()%>_<%=bt!=null ? bt.getName(): "默认分类"%>_<%=blog_user.getpTitle()%></title><%
	if (blog_user.getBlogHead() != null
					&& !"".equals(blog_user.getBlogHead().trim())) {
				out.print(blog_user.getBlogHead());
			}
%>
<jsp:include page="head.jsp"><jsp:param value="<%=keywords %>" name="kw"/><jsp:param value="<%=description %>" name="desc"/></jsp:include>
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
                      <div class="content-head clearfix">  <div class="content-other-info"> <span><%=blog.getSdTime()%></span> </div>   
                      <h2 class="title content-title"><%=blog.getTitle()%></h2>  </div> 
                      <div id="" class="content text-content clearfix"><%=blog.getContent().getValue()%></div>   
                      <div class="mod-post-info clearfix"> 
                      	<span class="box-tag clearfix"><%
								String tags= blog.getTag();
								if(tags!=null && !"".equals(tags)){
									String [] taglist=tags.split(" ");
									for(String str :taglist){
										if(!"".equals(str.trim())){
							%>
								<a href="/tag.jsp?t=<%=str %>" class="tag"><%=str %></a>
							<% }}}%></span>
	                      <div class="op-box">  
		                      <span class="pv">浏览(<%=blog.getCount()%>)</span> 
		                      <a class="comment-bnt" id="commentBnt" href="javascript:jQuery('#content').focus()">评论<span class="comment-nub hide"></span> </a>   
		                      <%if (Tools.isLogin(request)) {%><a href="/blog?id=<%=blog.getId()%>&op=modify" class="edit-bnt"> 编辑 </a> <%}%>
                      	  </div>
	                  </div>              
          </div>
            <div class="mod-share-detail" id="shareDetail" style="display:none"></div>
            <div class="mod-detail-pager clearfix">
            		<%if (preBlog != null) { %>
                    <div class="detail-nav-pre">
                       <a href="/blog/<%=preBlog.getId()%>" title="上一篇"></a>
                    </div>
                    <%} %>
                    <%if (nextBlog != null) { %>
                    <div class="detail-nav-next">
                        <a href="/blog/<%=nextBlog.getId()%>" title="下一篇"></a>
                    </div>
                    <%} %>
           </div>
                <div id="commentDetail" class="mod-comment-detail clearfix">
                    <div class="comment-title">评论</div>
                    <div class="comment-content" style="display: block; overflow: visible; height: auto; ">
                    <div id="qcmt1910464" class="qcmt-wraper-box-comment">
                    	 <%String errorMsg = (String)request.getSession().getAttribute("errorMsg");if(null!=errorMsg && !"".equals(errorMsg.trim())){ %>
	                   	<div class="fun" id="msg_notice"><%=errorMsg %></div>
	                   	<%request.getSession().setAttribute("errorMsg", "");} %>
	                    <div class="qcmt-input-box clearfix">
		                    <div class="qcmt-input-textarea-box">
		                    	<form id="frmSumbit" target="_self" method="post" onsubmit="return bd_sub();" action="/reply">
		                    		<input type="hidden" name="bid" value="<%=blog.getId()%>">
									<input type="hidden" name="title" value="<%=blog.getTitle()%>">
									<input type="hidden" name="op" value="add">
				                    <div class="qcmt-textarea-wraper">
						                 <%
											String gustName="游客";
											String gustEmail="";
											String gustURL="";
											boolean isCookied=true;
// 											Cookie[] allcookies=request.getCookies();
// 											if(allcookies!=null){
// 												for(int i=0;i<allcookies.length;i++){
// 													Cookie newCookie= allcookies[i];
// 													if(newCookie.getName().equals("gustName")){
// 														gustName=URLDecoder.decode(newCookie.getValue(), "UTF-8");
// 														isCookied=true;
// 														break;
// 													}
// 												}
// 											}
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
									   		<div class="qcmt-comment-name">欢迎回来：<%=gustName %></div>
									   	<%}else{ %>	           
									   		<div class="qcmt-comment-name" id="comment_account"></div>
									   		<script type="text/javascript">
										   		jQuery(function($) {
													getGoogleAccount();
												});                 
									   		</script>         
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
			                    <a class="cmt-seemore cmt-seenext" href="javascript:initReply2(1,<%=blog.getId()%>)">
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
          	<script type="text/javascript">
			jQuery(function($) {
				initReply2(1,<%=blog.getId()%>);
			});
			</script>
     </div>
</div>
</div>
<!-- 左边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</body><%
	}else{
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问的文章不存在_<%=blog_user.getpTitle() %></title>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 右边开始 -->
<div class="mod-blogpage-wraper">
	<div class="mod-text-content mod-post-content">  
		<div class="notices">访问的文章不存在,<a href="/">返回首页</a></div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<%} %>
</html>