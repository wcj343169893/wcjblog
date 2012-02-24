<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.util.CacheSingleton,javax.cache.Cache,java.util.List,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.dao.BlogDao,java.util.Map,java.util.HashMap,com.google.choujone.blog.dao.ReplyDao,com.google.choujone.blog.entity.Reply,com.google.choujone.blog.common.Pages,com.google.choujone.blog.dao.FriendsDao,com.google.choujone.blog.entity.Friends,com.google.choujone.blog.util.CalendarUtil,java.util.Date,com.google.choujone.blog.util.Config,com.google.choujone.blog.entity.User,java.util.ArrayList,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType,com.google.choujone.blog.util.Tools"%><div class="left"><%
	User blog_user= Config.getBlog_user();
	ReplyDao replyDao=new ReplyDao();
	List<Reply> replyList=new ArrayList<Reply>();
	List<Blog> blog_hot = new ArrayList<Blog>();
	BlogDao bd=new BlogDao();
	if(blog_user.getIsInfo()==null || blog_user.getIsInfo()==0){ %>
	<script type="text/javascript">
	$(document).ready(function(){
			alert($(".more a"));
			var preEle=$(".more a").parent().prev();
			$(".more a").toggle(
				function(){
					$(preEle).css("overflow","visible");
					$(preEle).height("auto");
				},function(){
					$(preEle).css("overflow","hidden");
					$(preEle).height("220px");
				});
		});
	</script>
	<div class="vito-left-title">个人资料</div>
	<div class="vito-left-contentul" style="text-align: center;">
		<ul class="vito-right-contentul">
			<li><img alt="" src="/images/myself.jpg" title="<%=blog_user.getDescription() %>"></li>
			<li><%=blog_user.getName() %></li>
			<li><%=blog_user.getCtitle() %></li>
		</ul>
	</div><%}if(blog_user.getNotice()!=null &&!"".equals(blog_user.getNotice().trim())){ %>
		<div class="vito-left-title">博客公告</div>
		<div class="vito-left-contentul" style="text-align: center;">
			<%=blog_user.getNotice() %>
		</div><%} if(blog_user.getIsWeather()==null || blog_user.getIsWeather()==0){%>
		<div class="vito-left-title">天气预报</div>
		<div class="vito-left-contentul">
		<iframe src="http://m.weather.com.cn/m/pn12/weather.htm?id=101040100T" width="230" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
		</div><%}if(blog_user.getIsCalendars()==null || blog_user.getIsCalendars()==0){%>
	<div class="vito-left-title">日历</div>
	<div class="vito-left-contentul">
		<div id="calendars">
		</div>
	<script type="text/javascript">
		function checkCal(year,month){
			$.post("/calendars", {"year":year, "month":month},function(data){
					//$("#calendars").html(data);
					document.getElementById("calendars").innerHTML=data;
				});
			}
		checkCal('','');
	</script>
	</div><%} if(blog_user.getIsType()==null || blog_user.getIsType()==0){ %>
	<div class="vito-left-title">文章分类</div>
		<script type="text/javascript" src="/vertical/js/mootools-yui-compressed.js"></script>
		<script type="text/javascript" src="/vertical/js/MenuMatic_0.68.3.js"></script>
		<link rel="stylesheet" href="/vertical/css/MenuMatic.css" type="text/css" media="screen" charset="utf-8" />
		<!--[if lt IE 7]>
			<link rel="stylesheet" href="/vertical/css/MenuMatic-ie6.css" type="text/css" media="screen" charset="utf-8" />
		<![endif]-->
	<div class="vito-left-contentul">
		<%
			BlogTypeDao btd=new BlogTypeDao();
			List<BlogType> btList=	btd.getBlogTypeList();
		%>
		<ul id="nav">
			<%if(btList!=null){ %>
				<%=Tools.blogTypeList2Str(btList) %>
			<%} %>
		</ul>
	</div>
	<script type="text/javascript">
		window.addEvent('domready', function() {			
			var myMenu = new MenuMatic({ orientation:'vertical' });			
		});	
	</script><%} if(blog_user.getIsTags()==null || blog_user.getIsTags()==0){ %>
	<div class="vito-left-title">TAGS</div>
	<div class="vito-left-contentul">
		<div class="tags"><%Map<String, Integer> tagsMap =bd.getTags();for(String s : tagsMap.keySet()){ %><a href="javascript:void(0)" style="font-size: <%=tagsMap.get(s)+12 %>px"><%=s %>(<%=tagsMap.get(s) %>)</a><%} %></div>
		<div class="more"><a href="javascript:void(0)" title="更多" >更多</a></div>
	</div><%}if(blog_user.getIsHotBlog()==null || blog_user.getIsHotBlog()==0){blog_hot=bd.getBlogList_hot(8);%>	
	<div class="vito-left-title">热门文章</div>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%if(blog_hot!= null){ for(int i=0;i<blog_hot.size();i++){ 
				Blog b=blog_hot.get(i);
			%><li title="<%=b.getSdTime() %>"><a href="/blog?id=<%=b.getId() %>"><%=b.getTitle() %></a></li><%}} %>
		</ul>
	</div><%}if(blog_user.getIsNewReply()==null || blog_user.getIsNewReply()==0){%>
	<div class="vito-left-title">最新留言</div><%
		replyList=replyDao.getReplyList(8);
	%>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%if(replyList!= null){ for(int i=0;i<replyList.size();i++){ 
					Reply r=replyList.get(i);
			%><li title="post by <%=r.getName() %>  <%=r.getSdTime() %>"><a href="/blog?id=<%=r.getBid() %>"><%=r.getContent() %></a></li><%}} %>
		</ul>
	</div><%}if(blog_user.getIsLeaveMessage()==null || blog_user.getIsLeaveMessage()==0){%>
	<div class="vito-left-title">最新留言</div><%replyList=replyDao.getReplyList(-1L,new Pages(8)); %>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul"><%if(replyList!= null){ for(int i=0;i<replyList.size();i++){ 
					Reply r=replyList.get(i);
			%><li title="post by <%=r.getName() %>  <%=r.getSdTime() %>"><a href="/leaveMessage.jsp"><%=r.getContent() %></a></li><%}} %>
		</ul>
	</div><%}if(blog_user.getIsStatistics()==null || blog_user.getIsStatistics()==0){%>
	<div class="vito-left-title">站点统计</div>
	<div class="vito-left-contentul"><%	Map<String, Integer> counts= bd.getCount();%>
		<ul class="vito-right-contentul">
			<li>文章总数：<%=counts.get("blogcount") %></li>
			<li>评论总数：<%=counts.get("replycount") %></li>
			<li>浏览总数：<%=counts.get("scancount") %></li>
<!--			<li>留言总数：<%=counts.get("messagecount") %></li>-->
		</ul>
	</div><%} if(blog_user.getIsFriends()==null || blog_user.getIsFriends()==0){%>
	<div class="vito-left-title">友情链接</div><%
		FriendsDao fd=new FriendsDao();
		List<Friends> friendsList=fd.getFriendsByPage(new Pages());
	%>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%
				for(int i=0;i<friendsList.size();i++){
					Friends f=friendsList.get(i);
			%><li title="<%=f.getDescription() %>"><a href="<%=f.getUrl() %>" target="_bank" title="<%=f.getDescription() %>"><%=f.getName() %></a></li><%} %>
		</ul>
	</div><%} %>
</div>
