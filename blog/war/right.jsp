<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.util.CacheSingleton,javax.cache.Cache,java.util.List,com.google.choujone.blog.entity.Blog,com.google.choujone.blog.dao.BlogDao,java.util.Map,java.util.HashMap,com.google.choujone.blog.dao.ReplyDao,com.google.choujone.blog.entity.Reply,com.google.choujone.blog.common.Pages,com.google.choujone.blog.dao.FriendsDao,com.google.choujone.blog.entity.Friends,com.google.choujone.blog.util.CalendarUtil,java.util.Date,com.google.choujone.blog.util.Config,com.google.choujone.blog.entity.User,java.util.ArrayList,com.google.choujone.blog.dao.BlogTypeDao,com.google.choujone.blog.entity.BlogType,com.google.choujone.blog.util.Tools"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><div class="right"><%
    UserDao ud=new UserDao();
	User blog_user=  ud.getUserDetail();
	ReplyDao replyDao=new ReplyDao();
	List<Reply> replyList=new ArrayList<Reply>();
	List<Blog> blog_hot = new ArrayList<Blog>();
	BlogDao bd=new BlogDao(); %>
<div class="mod-siderbar"><% if(blog_user.getIsType()==null || blog_user.getIsType()==0){ %>
	<div class="mod-taglist"><%
			BlogTypeDao btd=new BlogTypeDao();
			List<BlogType> btList=	btd.getBlogTypeList();
		%>
		<div class="q-title"><h2 class="side-title stat-title">文章分类<span>(<%=btList.size() %>)</span></h2></div>
		<ul class="q-taglist clearfix q-category">
			<%if(btList!=null){ %>
				<%=Tools.blogTypeList2Str(btList) %>
			<%} %>
		</ul>
	</div><%}  if(blog_user.getIsTags()==null || blog_user.getIsTags()==0){ %>
	<div class="mod-taglist tags_more">
		<ul class="q-taglist clearfix">
			<li class="q-tagitem"><span class="q-icon"></span></li><%Map<String, List<Long>> tagsMap =bd.getTags2();for(String s : tagsMap.keySet()){ %><li class="q-tagitem"><a href="tag.jsp?t=<%=s %>" class="a-tagitem cs-sidebar-hoverbglink"><%=s %>(<%=tagsMap.get(s).size() %>)</a></li>
			<%} %></ul>
	</div><%} if(blog_user.getIsFriends()==null || blog_user.getIsFriends()==0){
		FriendsDao fd=new FriendsDao();
		List<Friends> friendsList=fd.getFriendList();
	%>
	<section class="mod-side-item mod-side-stat scrolls">
		<h2 class="side-title stat-title">友情链接</h2>
		<div class="stat-content">
			<%if(friendsList!=null && friendsList.size()>0){
				for(int i=0;i<friendsList.size();i++){
					Friends f=friendsList.get(i);
					if(f.getIstop()!=null && f.getIstop().equals(1)){
			%>
			<div class="stat-desc today-stat"><span class="stat-tip"><a href="<%=f.getUrl() %>" target="_bank" title="<%=f.getDescription() %>"><%=f.getName() %></a></span></div>
			<%}}} %>
		</div>
	</section>
	
	<%}if(blog_user.getIsStatistics()==null || blog_user.getIsStatistics()==0){%>
	<section class="mod-side-item mod-side-stat scrolls">
		<div class="side-mini-border"></div>
		<h2 class="side-title stat-title">访问统计</h2>
		<div class="stat-content"><%	Map<String, Integer> counts= bd.getCount();%>
			<div class="stat-desc today-stat"><span class="stat-tip">总的文章量：</span><em class="stat-num"></em><%=counts.get("blogcount") %></div>
			<div class="stat-desc total-stat "><span class="stat-tip">总的评论量：</span><em class="stat-num"></em><%=counts.get("replycount") %></div>
			<div class="stat-desc total-stat "><span class="stat-tip">总的访问量：</span><em class="stat-num"></em><%=counts.get("scancount") %></div>
		</div>
	</section><%} %>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var m_arr = Array();
	$(".scrolls").each(function(){m_arr.push($(this).position().top);});
	var right_offset=$(".right").offset();
	
  	$(document).scroll(function() {
  		var w_width=$(window).width();
  		if(w_width>800){
	    	var scrolled = $(window).scrollTop();
		 	if(scrolled>right_offset.top-50){
		 		$('.right').css({"left":right_offset.left,"position":"fixed"});
		 		$('.right').css('top',(0-(scrolled*.25))+'px');
		 	}else{
		 		$('.right').css({'position':'',"top":""});
		 	}
	 	}
  });
  	//tag收缩
  	var $tm =$(".tags_more");
  	var tm_height=$tm.height();
  	if(tm_height>300){
  		var more_btn=$("<a href='javascript:;'>更多</a>").toggle(
  			  function () {
  			    $(this).html("收起");
  			  	$tm.animate({"height":tm_height});
  			  },
  			  function () {
  			    $(this).html("更多");
  			  	$tm.animate({"height":"245"});
  			  }
  			).css({"display": "inline-block","float":"right","margin-top":"-1px","margin-right":"0px","background":"#f5f5f5","width":"40px","border-radius":"0px 0px 0px 7px","height":"22px","line-height":"22px","text-align":"center","z-index":"200"});
  		$tm.after(more_btn);
  		$tm.css({"height":"245","overflow":"hidden","padding-bottom":"7px"});
  	}
});
</script>