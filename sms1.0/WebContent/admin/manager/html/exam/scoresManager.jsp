<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery" />
<c:set scope="request" var="meta_title" value="考试成绩管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">成绩录入</h1>
    		<font class="font3">录入成绩:</font>老师所在班级的学生姓名和所选择的考试<br/><br/>
    		<font class="font3">考试名称</font>：${exam.name }<br/>
    		<font class="font3">考试时间：</font>${fn:substring(exam.examTime, 0, 10)}
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 列名  -->
	<div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_menu">学号</div>
		<div class="list_operate">姓名</div>
		<c:forEach items="${subjectList}" var="subject">
			<div class="list_id" name="${subject.esid}">${subject.name}</div>
		</c:forEach>
		<div class="list_id">总分</div>				
	</div>	
 	<!-- 数据  -->
 	<form action="scores_update.html" method="post" id="inputform">
 	<input type="hidden"" value="${eid}" name="eid">
		<c:forEach items="${userList}" var="user" varStatus="vs">
			<div class="list_items">				
				<div class="list_id">${vs.count}</div>
				<div class="list_menu">${user.snumber}</div>
				<div class="list_operate">${user.nikeName}</div>
				<c:set value="0" var="sumscores"></c:set>
				<c:if test="${empty user.esList}">
					<c:forEach items="${subjectList}" var="subject">						
						<div class="list_id">
							<input type="text" class="exam_scores_${user.id}" onblur="sumScores(this)" name="${user.id}_${subject.esid}_" size="4" maxlength="5" value="0" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 )event.returnValue=false;"/>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${!empty user.esList}">
					<c:forEach items="${subjectList}" var="subject">						
						<c:set value="0" var="scores"></c:set>
						<c:forEach items="${user.esList}" var="score">
							<c:if test="${subject.esid==score.examSubId}">								
								<c:set value="${score.scores}" var="scores"></c:set>
								<c:set value="${score.scores+sumscores}" var="sumscores"></c:set>
							</c:if>
						</c:forEach>
						<div class="list_id">
							<input class="exam_scores_${user.id}" onblur="sumScores(this)" name="${user.id}_${subject.esid}_" size="4" maxlength="5" value="${scores}" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 )event.returnValue=false;"/>
						</div>	
					</c:forEach>
				</c:if>		
				<div class="list_id"><input type="button" style="width:60px;" id="exam_scores_${user.id}_sum" value="${sumscores}"/></div>	
		</div>
	</c:forEach>
		<div class="list_bottom_query">
			<input type="button" onclick="mysubmits()" value="提交" /></div>		
			<input type="hidden" name="examScores" id="examScores" value=""/>	
	</form>
	<div class="list_bottom_query">
	</div>	
</div>
