<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page import="com.snssly.sms.commons.Config"%>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common" />
<c:set scope="request" var="meta_title" value="消息发送成功"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION) %>" var="login_user"></c:set>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">操作成功</h1>
    		<a href="javascript:void(0)" onclick="to('message_updateInit.html')">返回再写一封</a>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
</div>