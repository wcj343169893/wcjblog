<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="java.util.Random"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<c:set scope="request" var="style" value="css/perm_module,css/notice_update,css/tabs,css/perm_module" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="在线支付"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center"><br>
		    <h1 class="font1">在线支付</h1>
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
    <div class="update_message_items">
    	暂无支付方式，如需支付，请联系管理员
    </div>
</div>