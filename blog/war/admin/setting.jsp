<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.util.Config"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao userDao = new UserDao();
	User user=userDao.getUserDetail();
%>
<title><%=user.getpTitle() %> -- 博客设置   / 系统设置</title>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			博客设置   / 系统设置
	</div>
	<div id="container">
		<form action="/user" method="post">
			<input type="hidden" name="op" value="modify">
			<div>博客设置</div>
			<table cellpadding="0" cellspacing="0" class="setting">
				<tr>
					<td class="title">博客标题</td>
					<td>
						<input type="text" name="pTitle" value="<%=user.getpTitle() %>">
					</td>
				</tr>
				<tr>
					<td class="title">博客子标题</td>
					<td>
						<input type="text" name="ctitle" value="<%=user.getCtitle() %>"><br>
						关于这个博客的简单介绍
					</td>
				</tr>
				<tr>
					<td class="title">博客访问地址</td>
					<td>
						<input type="text" name="url" value="<%=user.getUrl() %>">
					</td>
				</tr>
				<tr>
					<td class="title">博客风格</td>
					<td>
						<%List<String> styleList=Config.style_urls; 
						%>
						<select name="style">
						<%for(String s :styleList){ %>
							<option value="<%=s %>" <% if(s.equals(user.getStyle())){out.print("selected");} %>><%=s %></option>
						<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">公告</td>
					<td>
						<textarea rows="10" cols="70" name="notice"><%=user.getNotice() %></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">电子邮件</td>
					<td>
						<input type="text" name="email" value="<%=user.getEmail() %>">
					</td>
				</tr>
				<tr>
					<td class="title">生日</td>
					<td>
						<input type="text" name="brithday" value="<%=user.getBrithday() %>">
					</td>
				</tr>
				<tr>
					<td class="title">地址</td>
					<td>
						<input type="text" name="address" value="<%=user.getAddress() %>">
					</td>
				</tr>
				<tr>
					<td class="title">自我描述</td>
					<td>
						<textarea rows="10" cols="70" name="description"><%=user.getDescription() %></textarea>
					</td>
				</tr>
			</table>
			<br>
			<div>博客系统设置</div>
			<table cellpadding="0" cellspacing="0" class="blogsetting">
				<tr>
					<td class="title">
						天气
					</td>
					<td>
						<input type="radio" name="isWeather" value="0" id="isWeatherYes" <%=user.getIsWeather()==null || user.getIsWeather()==0?"checked":"" %>><label for="isWeatherYes">显示</label>
						<input type="radio" name="isWeather" value="1" id="isWeatherNo" <%=user.getIsWeather()!=null && user.getIsWeather()==1?"checked":"" %>><label for="isWeatherNo">隐藏</label>
					</td>
					<td class="title">
						日历
					</td>
					<td>
						<input type="radio" name="isCalendars" value="0" id="isCalendarsYes" <%=user.getIsCalendars()==null || user.getIsCalendars()==0?"checked":"" %>><label for="isCalendarsYes">显示</label>
						<input type="radio" name="isCalendars" value="1" id="isCalendarsNo" <%=user.getIsCalendars()!=null && user.getIsCalendars()==1?"checked":"" %>><label for="isCalendarsNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						热门文章
					</td>
					<td>
						<input type="radio" name="isHotBlog" value="0" id="isHotBlogYes" <%=user.getIsHotBlog()==null || user.getIsHotBlog()==0?"checked":"" %>><label for="isHotBlogYes">显示</label>
						<input type="radio" name="isHotBlog" value="1" id="isHotBlogNo" <%=user.getIsHotBlog()!=null && user.getIsHotBlog()==1?"checked":"" %>><label for="isHotBlogNo">隐藏</label>
					</td>
					<td class="title">
						最新评论
					</td>
					<td>
						<input type="radio" name="isNewReply" value="0" id="isNewReplyYes" <%=user.getIsNewReply()==null || user.getIsNewReply()==0?"checked":"" %>><label for="isNewReplyYes">显示</label>
						<input type="radio" name="isNewReply" value="1" id="isNewReplyNo" <%=user.getIsNewReply()!=null && user.getIsNewReply()==1?"checked":"" %>><label for="isNewReplyNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						留言
					</td>
					<td>
						<input type="radio" name="isLeaveMessage" value="0" id="isLeaveMessageYes" <%=user.getIsLeaveMessage()==null || user.getIsLeaveMessage()==0?"checked":"" %>><label for="isLeaveMessageYes">显示</label>
						<input type="radio" name="isLeaveMessage" value="1" id="isLeaveMessageNo" <%=user.getIsLeaveMessage()!=null && user.getIsLeaveMessage()==1?"checked":"" %>><label for="isLeaveMessageNo">隐藏</label>
					</td>
					<td class="title">
						统计
					</td>
					<td>
						<input type="radio" name="isStatistics" value="0" id="isStatisticsYes" <%=user.getIsStatistics()==null || user.getIsStatistics()==0?"checked":"" %>><label for="isStatisticsYes">显示</label>
						<input type="radio" name="isStatistics" value="1" id="isStatisticsNo" <%=user.getIsStatistics()!=null && user.getIsStatistics()==1?"checked":"" %>><label for="isStatisticsNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						友情链接
					</td>
					<td>
						<input type="radio" name="isFriends" value="0" id="isFriendsYes" <%=user.getIsFriends()==null || user.getIsFriends()==0?"checked":"" %>><label for="isFriendsYes">显示</label>
						<input type="radio" name="isFriends" value="1" id="isFriendsNo" <%=user.getIsFriends()!=null && user.getIsFriends()==1?"checked":"" %>><label for="isFriendsNo">隐藏</label>
					</td>
					<td class="title">
						个人资料
					</td>
					<td>
						<input type="radio" name="isInfo" value="0" id="isInfoYes" <%=user.getIsInfo()==null || user.getIsInfo()==0?"checked":"" %>><label for="isInfoYes">显示</label>
						<input type="radio" name="isInfo" value="1" id="isInfoNo" <%=user.getIsInfo()!=null && user.getIsInfo()==1?"checked":"" %>><label for="isInfoNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						TAGS
					</td>
					<td>
						<input type="radio" name="isTags" value="0" id="isTagsYes" <%=user.getIsTags()==null || user.getIsTags()==0?"checked":"" %>><label for="isTagsYes">显示</label>
						<input type="radio" name="isTags" value="1" id="isTagsNo" <%=user.getIsTags()!=null && user.getIsTags()==1?"checked":"" %>><label for="isTagsNo">隐藏</label>
					</td>
					<td class="title">
						文章分类
					</td>
					<td>
						<input type="radio" name="isType" value="0" id="isTypeYes" <%=user.getIsType()==null || user.getIsType()==0?"checked":"" %>><label for="isTypeYes">显示</label>
						<input type="radio" name="isType" value="1" id="isTypeNo" <%=user.getIsType()!=null && user.getIsType()==1?"checked":"" %>><label for="isTypeNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						KEYWORDS
					</td>
					<td colspan="3">
						<input name="blogKeyword" value="<%=user.getBlogKeyword() %>">
					</td>
				</tr>
				<tr>
					<td class="title">
						DESCRIPTION
					</td>
					<td colspan="3">
						<textarea rows="10" cols="70" name="blogDescription"><%=user.getBlogDescription() %></textarea>
					</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<td class="title">用户名</td>
					<td>
						<input type="text" name="name" value="<%=user.getName() %>">
					</td>
				</tr>
				<tr>
					<td class="title">密码</td>
					<td>
						<input type="password" name="password" value="<%=user.getPassword() %>">
					</td>
				</tr>
			</table>
		<div class="tools-left">
			<input type="submit" value="保存">
			<input type="reset" value="重置">
		</div>
		</form>
	</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>