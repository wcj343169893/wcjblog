<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.util.CacheSingleton"%>
<%@page import="javax.cache.Cache"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.entity.Blog"%>
<%@page import="com.google.choujone.blog.dao.BlogDao"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.google.choujone.blog.dao.ReplyDao"%>
<%@page import="com.google.choujone.blog.entity.Reply"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="com.google.choujone.blog.dao.FriendsDao"%>
<%@page import="com.google.choujone.blog.entity.Friends"%>
<%@page import="com.google.choujone.blog.util.CalendarUtil"%>
<%@page import="java.util.Date"%>
<div class="left">
		<%	
			//CacheSingleton cacheSingleton=CacheSingleton.getInstance();
			//cacheSingleton.init(new HashMap());
			//Cache cache =	cacheSingleton.getCache();
		%>
	<div class="vito-left-title">
		天气预报	<br>
	</div>
	<div class="vito-left-contentul">
	<iframe src="http://m.weather.com.cn/m/pn12/weather.htm?id=101040100T " width="230" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
	</div>
	<div class="vito-left-title">
		日历		
	</div>
	<div class="vito-left-contentul">
		<div id="calendars">
		</div>
	<script type="text/javascript">
		function checkCal(year,month){
			$.post("/calendars", {"year":year, "month":month},function(data){
					$("#calendars").html(data);
				});
			}
		checkCal('','');
	</script>
	
	</div>
	<div class="vito-left-title">
		热门文章	
	</div>
		<%
		List<Blog> blog_hot = null;
		BlogDao bd=new BlogDao();
		if(blog_hot == null){
			blog_hot=bd.getBlogList_hot(8);
			//cache.put("newBlogs",blog_new);
		}
		%>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%if(blog_hot!= null){ for(int i=0;i<blog_hot.size();i++){ 
				Blog b=blog_hot.get(i);
			%>
				<li title="<%=b.getSdTime() %>"><a href="/blog_detail.jsp?id=<%=b.getId() %>"><%=b.getTitle() %></a></li>
			<%}} %>
		</ul>
	</div>
	<div class="vito-left-title">
		最新评论	
	</div>
	<%
		ReplyDao replyDao=new ReplyDao();
		List<Reply> replyList=replyDao.getReplyList(8);
	%>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%if(replyList!= null){ for(int i=0;i<replyList.size();i++){ 
					Reply r=replyList.get(i);
			%>
				<li title="post by <%=r.getName() %>  <%=r.getSdTime() %>"><a href="/blog_detail.jsp?id=<%=r.getBid() %>"><%=r.getContent() %></a></li>
			<%}} %>
		</ul>
	</div>
	<div class="vito-left-title">
		最新留言	
	</div>
	<%replyList=replyDao.getReplyList(-1L,new Pages(8)); %>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%if(replyList!= null){ for(int i=0;i<replyList.size();i++){ 
					Reply r=replyList.get(i);
			%>
				<li title="post by <%=r.getName() %>  <%=r.getSdTime() %>"><a href="/leaveMessage.jsp"><%=r.getContent() %></a></li>
			<%}} %>
		</ul>
	</div>
	<div class="vito-left-title">
		站点统计	
	</div>
	<div class="vito-left-contentul">
	<%	Map<String, Integer> counts = bd.getCount(); %>
		<ul class="vito-right-contentul">
			<li>
				文章总数：<%=counts.get("blogcount") %>
			</li>
			<li>
				评论总数：<%=counts.get("replycount") %>
			</li>
			<li>
				浏览总数：<%=counts.get("scancount") %>
			</li>
			<li>
				留言总数：<%=counts.get("messagecount") %>
			</li>
		</ul>
	</div>
	<div class="vito-left-title">
		友情链接
	</div>
	<%
		FriendsDao fd=new FriendsDao();
		List<Friends> friendsList=fd.getFriendsByPage(new Pages(10));
	%>
	<div class="vito-left-contentul">
		<ul class="vito-right-contentul">
			<%
				for(int i=0;i<friendsList.size();i++){
					Friends f=friendsList.get(i);
			%>
			<li title="<%=f.getDescription() %>">
				<a href="<%=f.getUrl() %>" target="_bank" title="<%=f.getDescription() %>"><%=f.getName() %></a>
			</li>
			<%} %>
		</ul>
	</div>
</div>