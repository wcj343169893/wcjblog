<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%><div class="footer"><%
    UserDao ud=new UserDao();
    User blog_user=  ud.getUserDetail();
	%>
	<div class="content">Powered By choujone 版权所有.Some Rights Reserved <%=blog_user.getBlogFoot() %></div>
</div>
<div class="clear"></div>
<script>
jQuery(document).ready(function(){
	var $backToTopTxt = "返回顶部";
	var $backToTopEle = jQuery('<div class="backToTop"></div>').appendTo(jQuery("body"))
		.html("<a onclick='javascript:void(0)' class='cursor' id='go_top'></a>	").attr("title", $backToTopTxt).click(function() {
			jQuery("html, body").animate({ scrollTop: 0 }, 120);
	}), $backToTopFun = function() {
		var st = jQuery(document).scrollTop(), winh = jQuery(window).height();
		(st > 0)? $backToTopEle.show(): $backToTopEle.hide();	
		//IE6下的定位
		if (!window.XMLHttpRequest) {
			$backToTopEle.css("top", st + winh - 166);	
		}
		var winw=jQuery(window).width();
		if(winw>980){
			$backToTopEle.css("left",961+(winw-980)/2);
		}else{
			$backToTopEle.hide();
		}
	};
	jQuery(window).bind("scroll", $backToTopFun);
	jQuery(function() { $backToTopFun(); });
});
</script>