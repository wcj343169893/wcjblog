<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set scope="request" var="style" value="css/perm_module,,css/notice_update,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="领导成绩分析-年级分析"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
     	<div class="tabs_150_context_center">
     	   	<div class="tabs_150_context_center">
    		<h1 class="font1">领导成绩分析</h1><br/>
    		   	<font class="font3">班级名次</font>&nbsp;&nbsp;&nbsp;    		
    			<a href="scores_analysis-0.html">学生总分</a>&nbsp;&nbsp;&nbsp;
    			<a href="scores_analysis-1.html">学生各科成绩</a>
    		</div>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 选择学生及统计方式 -->
    <div class="list_items font1 bg" style="text-align:left">请选择</div>   
		 <div class="list_bottom_query">
		 		起始时间：<input type="text" name="start" id="start" value="${start}" onClick="WdatePicker()"/><br/>	
		 </div> 
		 <div class="list_bottom_query">
		    	结束时间：<input type="text" name="end" id=end value="${end}" onClick="WdatePicker()"/><br/>			    	
		 </div> 
		 <div class="list_bottom_query">
		    	请选择年级：<select name="gid" id="gid">
								<c:forEach items="${gradeList}" var="grade">
									<option value="${grade.id}" <c:if test="${grade.id==gid}">selected="selected"</c:if>>${grade.name}</option>
								</c:forEach>
							 </select> 
				<input type="button" onclick="leadAnalysis()" value="开始查询" />	      			
		 </div> 
 	<!-- 数据  -->
 	<div id="content" style="width: 1024px;">	 
	</div> 
 	<!-- 数据  -->
	<c:forEach items="${examList}" var="exam" varStatus="vs">
		<div class="list_items">
			<div class="list_id">${vs.count}</div>
			<div class="list_title">${exam.name}</div>
			<div class="list_time_date">${fn:substring(exam.examTime, 0, 10)}</div> 	
			<c:set  value="${fn:length(exam.examRowNumList)+1}" var="examRowNumListLength"></c:set>
			<c:forEach items="${clazzList}" var="clazz">		
				<c:set  value="${examRowNumListLength}" var="rank"></c:set>
				<c:forEach items="${exam.examRowNumList}" var="examRowNum" varStatus="vs">
					<c:if test="${clazz.id==examRowNum.cid}">
						<c:set value="${vs.count}" var="rank"></c:set>
					</c:if>
				</c:forEach>	
					<div class="list_id">
						${rank}
					</div>
			</c:forEach>	
		</div>
	</c:forEach>
	<div class="list_bottom_query">
	</div>
<script type="text/javascript">

</script>
</div>
