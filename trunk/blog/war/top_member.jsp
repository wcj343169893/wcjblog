<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@page import="com.google.choujone.blog.entity.User,com.google.choujone.blog.util.Config,com.google.choujone.blog.util.Tools,java.util.Map,com.google.choujone.blog.entity.Menu"%><%@page import="java.util.List"%><%@page import="com.google.choujone.blog.dao.UserDao"%><%UserDao ud=new UserDao();User blog_user = ud.getUserDetail();%><header class="mod-topbar" id="modTopbar">
	<div class="mod-topbar-pseudo-real">
		<div class="wrapper-box clearfix">
			<div class="mod-msg-bubble hide" id="mod-msg-bubble"></div>
			<div class="left-box">
				<a href="<%=blog_user.getUrl()%>" class="logo-box"><span id="baiduSpaceLogo" class="q-logo" title="<%=blog_user.getpTitle()%>"></span></a>
			</div>
			<div class="center-box">
			<ul class="q-menubox">
				<%
					List<Menu> menus=Tools.split(blog_user.getMenu(), ";", ",");
					String uri=request.getRequestURI();
					boolean isSelected=true;
					//确定哪一个被选中
					for (Menu m : menus) {
						if(uri.indexOf(m.getUrl())!=-1 && !m.getUrl().equals("/")){
							m.setSelected(true);
							isSelected=false;
							break;
						}
					}
					for (Menu m : menus) {
				%><li class="q-menuitem"><a href="<%=m.getUrl() %>" <%if(m.isSelected() || isSelected){isSelected=false;%> class="q-selected" <%} %>><%=m.getTitle() %></a></li>
				<%} %>			
			</ul>
			</div>
			<div class="right-box">
				<ul class="q-navbox"><!--<li class="q-navitem q-nav-sp"><span></span></li>-->
					<li class="q-navitem" id="top_member"><a href="javascript:hig();">High 起来</a> </li>
					<li class="q-navitem-menu" id="top_member"><a href="javascript:showmenu();"><img src="/images/menu.png" alt="menu" width="40" height="30"/></a> </li>
				</ul>
			</div>
			<div class="mod-msg-unread-num hide" style="top: 7px; left: 732px; ">
			<div class="left-border"></div>
			<div class="content"></div>
			<div class="right-border"></div>
			</div>
		</div>
	</div>
</header>
<script type="text/javascript">
		jQuery(function($) {
			//initGoogleAccount();
		});
	var ismenushow=false;
	function showmenu(){
		if(!ismenushow){
			$(".center-box").animate({right:0},"slow");
			ismenushow=true;
			//创建一个遮罩层，用来关闭菜单
			var z=$("<div id='menu_shadow'>&nbsp;</div>").click(function(){
				showmenu();
			});
			$("body").append(z);
		}else{
			$(".center-box").animate({right:-161},"slow");
			ismenushow=false;
			$("#menu_shadow").remove();
		}
	}
	
</script>
<script type="text/javascript" src="/js/hi.js"></script>
