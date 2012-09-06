<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="java.util.Random"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<c:set scope="request" var="style" value="css/perm_module,css/notice_update,css/tabs,css/perm_module" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="用户管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center"><br>
   		<div class="update_message_items">
		    <h1 class="font1">${school.name}</h1>
		    <div class="content_url">学校网址：<a href="${school.url}" target="_bank">${school.url}</a></div>
		    <div class="content_createTime">发布时间：${school.createTime}</div>
    	</div>
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
    <div class="update_message_items">
	    <div class="content_content">
	    <c:out value="${school.description}" escapeXml="false"></c:out>
	    </div>
    </div>
</div>