<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/editor" />
<c:set scope="request" var="script" value="js/jquery,js/common" />
<c:set scope="request" var="meta_title" value="在线用户"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">在线 用户</h1><br/>
    		在线用户（您的IP为：${ip}）<br>
    		在线总人数：${onlineCount}位
			<div class="tabs_150_context_center_li">
			<ul>
				<c:forEach items="${roleList}" var="role">
					<li><a href="online_info-${role.id}.html">${role.name}(${role.count})</a></li>
				</c:forEach>
			</ul>
			</div>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>  
		<div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_title">用户姓名</div>
		<div class="list_content">IP</div>
	</div>	
	<c:forEach items="${onlineList}" var="online" varStatus="vs">
		<div class="list_items">
			<div class="list_id">${vs.count}</div>
		    <div class="list_title">${online.nikeName}</div>
		    <div class="list_content">${online.ip}</div>
		</div>
	</c:forEach>
	<div class="list_bottom_query">
	</div>	
</div>