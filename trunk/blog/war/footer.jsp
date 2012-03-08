<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><div class="footer"><%
    UserDao ud=new UserDao();
    User blog_user=  ud.getUserDetail();
	%>
	<div class="content">Powered By choujone 版权所有.Some Rights Reserved <%=blog_user.getBlogFoot() %></div>
</div>
<div class="clear"></div>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script>
(function() {
	var $backToTopTxt = "返回顶部";
	var $backToTopEle = $('<div class="backToTop"></div>').appendTo($("body"))
		.html("<a onclick='javascript:void(0)' class='cursor' id='go_top'></a>	").attr("title", $backToTopTxt).click(function() {
			$("html, body").animate({ scrollTop: 0 }, 120);
	}), $backToTopFun = function() {
		var st = $(document).scrollTop(), winh = $(window).height();
		(st > 0)? $backToTopEle.show(): $backToTopEle.hide();	
		//IE6下的定位
		if (!window.XMLHttpRequest) {
			$backToTopEle.css("top", st + winh - 166);	
		}
		var winw=$(window).width();
		if(winw>980){
			$backToTopEle.css("left",961+(winw-980)/2);
		}else{
			$backToTopEle.hide();
		}
	};
	$(window).bind("scroll", $backToTopFun);
	$(function() { $backToTopFun(); });
})();
</script>