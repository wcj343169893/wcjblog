<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/styles" />
<c:set scope="request" var="script" value="js/common" />
<c:set scope="request" var="meta_title" value="用户管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">教师生日列表</h1>
    		  点击用户，则显示相应的班级，职务。
    		<h3 class="search_name">今天是：${time}&nbsp;&nbsp;${week}</h3>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <div class="birthday-div">
		<c:forEach items="${birthdayList}" var="birthday" varStatus="vs">
			<c:if test="${fn:length(birthday)!=0}">
			<div class="birthday-div-content">
			    <div class="birthday-div-title"><span class="search_key">${vs.count }</span>月过生日</div>
					  <c:forEach items="${birthday}" var="bt">
						<div class="birthday-div-body" title="${bt.gdname}${bt.cname}&nbsp;&nbsp;${bt.gname}">
				  			<div>${bt.nikeName }</div>
				   			<div><fmt:formatDate value="${bt.brithday}" pattern="M-dd"></fmt:formatDate></div>
						</div>
					  </c:forEach>
					</div>
				</c:if>
		</c:forEach>
	   </div>
</div>