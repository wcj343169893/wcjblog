<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page import="com.snssly.sms.commons.Config"%>
<%@page import="com.snssly.sms.admin.AdminuserAction"%>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/styles" />
<c:set scope="request" var="script" value="js/common,js/jquery132,DatePicker/WdatePicker,js/message,js/jquery.cookie,js/jquery.treeview" />
<c:set scope="request" var="meta_title" value="师恩教育管理信息系统--后台管理首页"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION)%>" var="login_user"></c:set>
<script type="text/javascript">
	$(document).ready(function(){
		haveMessage();
	});
	</script>
<div class="context_right_all">
<div class="search_key"><marquee direction=left height=15 scrollamount=1 scrolldelay=0>如果首次登录，请及时修改个人信息！</marquee></div>
<fieldset> 					
	     <legend><img src="../images/message_box.gif" width="16" height="16" />&nbsp;收件箱</legend> 
<div class="message-div">
	<div class="birthday-div-content">
		<div class="birthday-div-title">你有<span class="search_key" id="newMessage_count"></span>条未读信息</div>
			<div id="newMessage">
			</div>
	</div>
</div>
</fieldset>
<c:if test="${login_user.rid!=1}">
<div class="birthday-div">
<fieldset> 					
	     <legend><img src="../images/birthday.gif" width="18" height="18" />&nbsp;生日</legend> 
<c:choose>
	<c:when test="${login_user.rid==2}">
		<c:if test="${fn:length(studentList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(studentList)}</span>个同学过生日</div>
				<c:forEach items="${studentList}" var="student" varStatus="vs">
			  		<div class="birthday-div-body">
			  			<div>${student.nikeName }</div>
			  			<div><fmt:formatDate value="${student.brithday}" pattern="M-dd"></fmt:formatDate></div>
			  		</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${fn:length(teacherList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(teacherList)}</span>个老师过生日</div>
					<c:forEach items="${teacherList}" var="teacher">
						<div class="birthday-div-body" title="${teacher.gdname}${teacher.cname} &nbsp;&nbsp; ${teacher.gname}">
				  			<div>${teacher.nikeName}</div>
				   			<div><fmt:formatDate value="${teacher.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
					</c:forEach>
			</div>
		</c:if>
		<c:if test="${fn:length(leaderList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(leaderList)}</span>个领导过生日</div>
					<c:forEach items="${leaderList}" var="leader">
						<div class="birthday-div-body" title="${leader.gname}">
			  		 		<div>${leader.nikeName }</div>
			   				<div><fmt:formatDate value="${leader.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
				</c:forEach>
			</div>
		</c:if>
	</c:when>
	<c:when test="${login_user.rid==3}">
			<c:if test="${fn:length(studentList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(studentList)}</span>个同学过生日</div>
				<c:forEach items="${studentList}" var="student" varStatus="vs">
			  		<div class="birthday-div-body">
			  			<div>${student.nikeName }</div>
			  			<div><fmt:formatDate value="${student.brithday}" pattern="M-dd"></fmt:formatDate></div>
			  		</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${fn:length(teacherList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(teacherList)}</span>个老师过生日</div>
					<c:forEach items="${teacherList}" var="teacher">
						<div class="birthday-div-body">
				  			<div>${teacher.nikeName }</div>
				   			<div><fmt:formatDate value="${teacher.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
					</c:forEach>
			</div>
		</c:if>
	</c:when>
	<c:when test="${login_user.rid==4}">
			<c:if test="${fn:length(teacherList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(teacherList)}</span>个老师过生日</div>
					<c:forEach items="${teacherList}" var="teacher">
						<div class="birthday-div-body">
				  			<div>${teacher.nikeName }</div>
				   			<div><fmt:formatDate value="${teacher.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
					</c:forEach>
			</div>
		</c:if>
		<c:if test="${fn:length(leaderList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(leaderList)}</span>个领导过生日</div>
					<c:forEach items="${leaderList}" var="leader">
						<div class="birthday-div-body" title="${leader.gname }">
			  		 		<div>${leader.nikeName }</div>
			   				<div><fmt:formatDate value="${leader.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
				</c:forEach>
			</div>
		</c:if>
	</c:when>
	<c:when test="${login_user.rid==5}">
			<c:if test="${fn:length(teacherList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(teacherList)}</span>个老师过生日</div>
					<c:forEach items="${teacherList}" var="teacher">
						<div class="birthday-div-body" title="${teacher.gdname}${teacher.cname} &nbsp;&nbsp; ${teacher.gname}">
				  			<div>${teacher.nikeName }</div>
				   			<div><fmt:formatDate value="${teacher.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
					</c:forEach>
			</div>
		</c:if>
		<c:if test="${fn:length(leaderList) != 0}">
			<div class="birthday-div-content">
				<div class="birthday-div-title">本月共<span class="search_key">${fn:length(leaderList)}</span>个领导过生日</div>
					<c:forEach items="${leaderList}" var="leader">
						<div class="birthday-div-body" title="${leader.gname }">
			  		 		<div>${leader.nikeName }</div>
			   				<div><fmt:formatDate value="${leader.brithday}" pattern="MM-dd"></fmt:formatDate></div>
						</div>
				</c:forEach>
			</div>
		</c:if>
	</c:when>
</c:choose>
</fieldset>
</div>	
</c:if>								
</div>