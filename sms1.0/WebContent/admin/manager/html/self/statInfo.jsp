<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/editor" />
<c:set scope="request" var="script" value="DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="统计信息"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">短信统计</h1><br/>
	    	<form action="stat_info-1.html" method="post">
		    	日期：<input type="text" name="start" value="${start}" onClick="WdatePicker()"/>	
		    	至<input type="text" name="end" value="${end}" onClick="WdatePicker()"/> 
		    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input type="submit" value="统计">
	    	</form>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_time_date">姓名</div>
		<div class="list_operate">接收信息数</div>
		<c:forEach items="${typeList}" var="type">
			<div class="list_menu">${type.name}成功数</div>
		</c:forEach>
	</div>	
	<c:forEach items="${mixedList}" var="mix" varStatus="vs">
		<div class="list_items">
			<div class="list_id">${vs.count}</div>
			<div class="list_time_date">${mix.name}</div>
			<div class="list_operate">${mix.sendCount}</div>
			<c:forEach items="${mix.messageTypeList}" var="messageType">
				<div class="list_menu">${messageType.count}</div>
			</c:forEach>
		</div>	
	</c:forEach>
	<div class="list_bottom_query">
	</div>	
</div>