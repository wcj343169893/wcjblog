<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set scope="request" var="style" value="css/perm_module,,css/notice_update,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="添加考试"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">添加考试</h1>
    		<font class="font3">添加考试信息:</font>填写考试名称和考试的开始日期<br />
    		<font class="font3">添加考试科目信息:</font>选择此次考试的科目，总分可自行修改	<br/><br/>			
    		<font class="font3">请在提交前，仔细确认</font>			
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
 	<form action="exam_add.html" method="post" id="inputform">
 	<input type="hidden" value="${eid}" name="eid">
    <!-- 第一步 -->
    <div class="list_bottom_query"><span style="float:left">填写考试信息  </span></div>   
   	<div class="list_bottom_query">考试名称：<input type="text" size="20" name="name" id="name" value="${exam.name }"/>(例：期末考试)</div>
   	<c:if test="${eid==0}">
   		<div class="list_bottom_query">考试时间：<input type="text" name="examTime" id="examTime" onClick="WdatePicker()" value="${examTime}"/></div>
   	</c:if>
   	<c:if test="${eid!=0}">
   		<div class="list_bottom_query">考试时间：<input type="text" name="examTime" id="examTime" onClick="WdatePicker()" value="${fn:substring(exam.examTime, 0, 10)}"/></div>
 	</c:if>
 	<!-- 第二步 --> 
 	 <div class="list_bottom_query"><span style="float:left">设置考试科目信息 </span></div>   
    <!-- 列名  -->
	<div class="list_items font1 bg">
		<div class="list_id">选择</div>
		<div class="list_title">科目名称</div>
		<div class="list_id">总分</div>
	</div>		
 	<!-- 数据  -->	
 		<c:forEach items="${subjectList}" var="subject">
	 		<c:if test="${eid==0}">
				<div class="list_items">			
					<div class="list_id"><input type="checkbox" name="checked" value="${subject.id}" id="c_${subject.id}" onclick="clears(${subject.id})"/></div>
					<div class="list_title">${subject.name }</div>
					<div class="list_id"><input type="text" size="4" name="total" id="total_${subject.id}" value="100" maxlength="4" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 )event.returnValue=false;"/></div>
				</div>
			</c:if>
			<!-- 修改考试信息 -->
			<c:set value="<div class='list_id'><input type='checkbox' name='checked' value='${subject.id}'  id='c_${subject.id}' onclick='clears(${subject.id})'/></div>" var="logo"></c:set>
 			<c:if test="${eid!=0}">
	 			<c:forEach items="${subjects_checked}" var="subjects_checked">
		 			 <c:if test="${subjects_checked.id==subject.id }">
			 			<c:set value="<div class='list_id'><input type='checkbox' name='checked' checked='checked' value='${subject.id}'  id='c_${subject.id}' onclick='clears(${subject.id})'/></div>" var="logo"></c:set>
		 			 </c:if>
				</c:forEach>	
					<div class="list_items">			
						${logo}
						<div class="list_title">${subject.name }</div>
						<div class="list_id"><input type="text" size="4" name="total" id="total_${subject.id}" value="100" maxlength="4" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 )event.returnValue=false;"/></div>
					</div>
			</c:if>
		</c:forEach>			
		<div class="list_bottom_query" >
		<input type="button" onclick="mysubmit()" value="提交" />(请在提交前，仔细确认)  <input type="reset" value="重置" /></div>			
		<input type="hidden" name="subjects" id="subjects" value="" />		
	</form>
	<div class="list_bottom_query">
	</div>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>