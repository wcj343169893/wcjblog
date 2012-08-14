<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.dao.BlogDao,java.util.List,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.common.Pages,com.google.choujone.blog.entity.User,com.google.choujone.blog.common.Operation,java.text.SimpleDateFormat,java.util.Date,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType,java.util.ArrayList,java.util.Map,java.util.HashMap,com.google.choujone.blog.util.Tools,com.google.choujone.blog.dao.UserDao"%><html>
<head><%
UserDao ud=new UserDao();
User blog_user=  ud.getUserDetail();
	String title=blog_user!=null ? blog_user.getpTitle():"";
	Long tid=null;
	try{
		 tid=request.getParameter("tid") != null ? Long.valueOf(request.getParameter("tid").toString()) : null;
	}catch(Exception e){
		tid=null;
	}
	
	//查询所有的分类
	BlogTypeDao btd=new BlogTypeDao();
	List<BlogType> blogTypeList = new ArrayList<BlogType>();
	blogTypeList=btd.getBlogTypeList();
	Map<Long,String> typeMaps=new HashMap<Long,String>();
	
	for(int i=0;i<blogTypeList.size();i++){
		typeMaps.put(blogTypeList.get(i).getId(),blogTypeList.get(i).getName());
		if(tid!=null && blogTypeList.get(i).getId().equals(tid)){
			title=blogTypeList.get(i).getName()+"_"+title;
		}
	}
%><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=title %></title>
<jsp:include page="head.jsp"></jsp:include>
<script type="text/javascript" src="/js/jquery.masonry.js"></script>
<script type="text/javascript" src="/js/jquery.infinitescroll.js"></script>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<script type="text/javascript">
$(function(){
         var speed = 1000;
         $("#mainbox").masonry({
            singleMode: true,
           // columnWidth: 242,
            itemSelector: '.mod-blogitem',
            animate: false,
            animationOptions: {
                duration: 500,
                easing: 'linear',
                queue: false
            }
        });
        $("#mainbox").infinitescroll({
            navSelector : '#page_nav', // selector for the paged navigation
            nextSelector : '#page_nav a', // selector for the NEXT link (to page 2)
            itemSelector : '.mod-blogitem', // selector for all items you'll retrieve
            loadingImg : '/images/loading.gif',
            donetext : '已经到最后一页了',
            debug: false,
            errorCallback: function() {
            // fade out the error message after 2 seconds
            $('#infscr-loading').animate({opacity: .8},2000).fadeOut('normal');
            }},
            // call masonry as a callback.
            function( newElements ) { $(this).masonry({ appendedContent: $(newElements) }); }
        );
     });
</script>
<div class="left" id="mainbox"><%
	BlogDao blogDao = new BlogDao();
	Integer p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	
	Pages pages=new Pages();
	pages.setPageNo(p);
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
	List<Blog> blogs = blogDao.getBlogListByPage(pages,tid);
if(blogs!=null && blogs.size()>0){
for(int i=0;i<blogs.size();i++){
	Blog blog=blogs.get(i);
	if(blog.getIsVisible() == 0 ){
		String link="/blog/"+blog.getId();
%>
<article class="mod-blogitem mod-item-text">
	<div class="box-postdate"><div class="q-day"><%=Tools.changeTime(Tools.changeTime(blog.getSdTime()),"dd")%></div><div class="q-month-year"><%=Tools.changeTime(Tools.changeTime(blog.getSdTime()),"MM/yyyy")%></div></div>
	<div class="mod-realcontent mod-cs-contentblock">
		<div class="item-head">
			<a href="<%=link %>" class="a-incontent a-title cs-contentblock-hoverlink" target="_blank"><%=blog.getTitle() %></a>
		</div>
		<div class="item-content cs-contentblock-detailcontent">
			<div class="q-previewbox"></div>
			<div class="q-summary">
				<%=blog.getContent(200).getValue().trim() %>
			</div>
		</div>
		<div class="item-foot clearfix">
			<span class="box-act">
				<a href="<%=link %>" class="a-act a-readall">阅读全文</a>
				<a href="<%=link %>#commentDetail" class="a-act a-reply" >评论<span class="comment-count">(<%=blog.getReplyCount() %>)</span></a><%if(Tools.isLogin(request)){	%>
					<a href="/blog?id=<%=blog.getId() %>&op=modify" class="a-act a-modifyblog">编辑</a>
				<%} %>
			</span>
			<span class="box-tag"><%
				String tags= blog.getTag();
				if(tags!=null && !"".equals(tags)){
					String [] taglist=tags.split(" ");
					for(String str :taglist){
						if(!"".equals(str.trim())){
			%>
				<a href="/tag.jsp?t=<%=str %>" class="a-tag" target="_blank"><span class="q-tag"><%=str %></span></a>
			<% }}}%></span>
		</div>
		<div class="blog-cmt-wraper"></div>
	</div>
</article>
<%}	} }else{%><div style="margin: 50px;">Sorry 暂无内容 <a href="/" class="a-incontent a-title cs-contentblock-hoverlink" target="_blank">回到首页</a></div><%} %>
</div>
<div id="page_nav" class="hide"><%=pages.getPageNos(tid) %></div>
<!-- 左边结束 -->
<!-- 右边开始 -->
<jsp:include page="right.jsp" flush="true"></jsp:include>
<!-- 右边结束 -->
<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>