<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><%@page import="com.google.choujone.blog.util.Config,com.google.choujone.blog.entity.*"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.common.Operation"%>
<%@page import="com.google.choujone.blog.dao.WeiweiDao"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
UserDao ud=new UserDao();
User blog_user = ud.getUserDetail();
String title=blog_user.getpTitle();
String message="";
if(request.getParameter("weiweiid") != null){
	WeiweiDao wwd = new WeiweiDao();
	Weiwei ww = new Weiwei();
	int no=0;
	try{
		no=Integer.parseInt(request.getParameter("weiweiid").toString());
		message="添加成功，稍后会自动为您签到";
	} catch (Exception e) {
		no=0;
	}
	ww.setNo(no);
	wwd.operationId(Operation.add,ww);
}else if(request.getParameter("weiwei") != null && request.getParameter("weiwei").toString().equals("registration")){
	//cron自动请求
	WeiweiDao wwd = new WeiweiDao();
	wwd.registration();
}
%><title>微微自动签到_<%=title %></title>
<jsp:include page="head.jsp"><jsp:param value="微微网络电话，微微网络电话自动每日签到，自动签到获取话费" name="kw"/><jsp:param value="微微网络电话，每日自动签到送话费，获赠金额还可以用于拨打电话 兑换手机充值卡、彩票、Q币等" name="desc"/></jsp:include>
</head>
<body>
<jsp:include page="top_member.jsp"></jsp:include>
<div class="main">
<!-- 顶部开始 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 顶部结束 -->
<!-- 左边开始 -->
<!-- 左边结束 -->
<div class="left">
	<div class="mod-blogitem mod-item-text">
		<div class="mod-realcontent mod-cs-contentblock" style="top: 0px;">
			<div class="item-head">
			<h2>微微网络电话，每日自动签到送话费，只需要在下面的表单中提交您的id，即可完成签到。</h2>
			</div>
			<div class="item-content cs-contentblock-detailcontent">
				<div class="q-previewbox"></div>
				<div class="q-summary">
					<p>如何得到微微id？</p>
					<p>用google浏览器或者火狐浏览器<strong>登录</strong>微微官方网站<a href="http://www.uwewe.com/" target="_blank">http://www.uwewe.com/</a>，
					然后F12查看元素，找到Cookies，找到Userid键，看到  userid=5264416，这个数字就是微微id，然后填入到下面的输入框中并提交.
					</p>
					<p>&nbsp;</p>
					<div>
						<form action="/wewe.jsp" method="post">
						微微id：<input type="text" name="weiweiid"><input type="submit">
						</form>
					</div>
					<p>&nbsp;<%=message %></p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 右边开始 -->
<jsp:include page="right.jsp" flush="true"></jsp:include>
<!-- 右边结束 -->
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>