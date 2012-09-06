<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="总分分段统计"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript" src="/admin/manager/js/highcharts.js"></script>
<!--[if IE]>
	<script type="text/javascript" src="/admin/manager/js/excanvas.compiled.js"></script>
<![endif]-->
<c:if test="${rid==5||rid==1}">
	<script type="text/javascript">
	$(document).ready(function(){
		changeClazzByGrade();
		changeUserByClazz();
		});
	</script>
</c:if>
<c:if test="${rid==2}">
	<script type="text/javascript">
	$(document).ready(function(){
		changeUserByClazz();
		});
	</script>
</c:if>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">成绩总分分析</h1><br/>  
    		<c:if test="${rid==5||rid==1}">
    		   	<a href="scores_analysis-2.html">班级名次</a>&nbsp;&nbsp;&nbsp; 
    		 </c:if>   		
    			<font class="font3">学生总分</font>&nbsp;&nbsp;&nbsp;
    			<a href="scores_analysis-1.html">学生各科成绩</a>	
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 选择学生及统计方式 -->
    <div class="list_items font1 bg" style="text-align:left">请选择学生及统计方式</div>
		       	<c:choose>
    				<c:when test="${rid==5||rid==1}">   
    				<div class="list_bottom_query"> 		
			  			年级：<select name="gid" id="gradeId" onchange="changeClazzByGrade()">
										<c:forEach items="${gradeList}" var="grade">
											<option value="${grade.id}">${grade.name}</option>
										</c:forEach>
									 </select>  
									 </div>
    				</c:when>
    				<c:when test="${rid==2}"> 
    				<div class="list_bottom_query">
			  			年级：<select name="gid" id="gradeId" onchange="changeSubjectByGrade()">
											<option value="${grade.id}">${grade.name}</option>
								</select>      		
					</div>
    				</c:when>
    			</c:choose> 
			    <c:choose>
    				<c:when test="${rid==5||rid==1}">    
		    <div class="list_bottom_query">
					    	班级：<select name="cid" id="cid" onchange="changeUserByClazz()">								
												<option value="0">选择班级</option>
										 </select>  
			</div>
    				</c:when>
    				<c:when test="${rid==2}"> 
		    <div class="list_bottom_query">
					    	班级：<select name="cid" id="cid" onchange="changeUserByClazz()">								
												<option value="${clazz.id}">${clazz.name}</option>
										 </select> 	
			</div>
    				</c:when>
    			</c:choose> 
	   	 	<div class="list_bottom_query">
				<c:choose>
		    		<c:when test="${rid==5||rid==1||rid==2}">
							学生姓名：<select name="uid" id="uid">
											<option value="0">请选择</option>
									 </select>   
	
		    		</c:when>
		    		<c:otherwise>
						学生姓名：<select name="uid" id="uid">
									<c:forEach items="${userList}" var="user">
										<option value="${user.id}">${user.nikeName}</option>
									</c:forEach>
								 </select>   
		    		</c:otherwise>
	    		</c:choose>
			</div> 	    		
	<div class="list_bottom_query">统计方式：</div> 	
	<div class="list_bottom_query">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="way" value="0" checked="checked" onclick="checkRadio(this)"/>按成绩：显示某一学生历次考试成绩变化情况；   		
	</div>  
  	<div class="list_bottom_query">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="way" value="1" onclick="checkRadio(this)"/>按名次：显示某一学生在历次考试的班级排名变化情况；   	
		<input type="hidden" value="0" id="way">	
	</div>	
	<div class="list_bottom_query">
		起始时间：<input type="text" name="start" id="start" value="${start}" onClick="WdatePicker()"/>	
	</div> 
	<div class="list_bottom_query">
		结束时间：<input type="text" name="end" id="end" value="${end}" onClick="WdatePicker()"/>  
		<input type="button" onclick="totalAnalysis()" value="开始查询" />	   		
	</div>
	    <!-- 列名  -->
	<div class="list_items font1 bg" style="display:none" id="title">
		<div class="list_id">编号</div>
		<div class="list_title">考试名称</div>
		<div class='list_time'>考试时间</div>
		<div class="list_id">总分/名次</div>
	</div>	
 	<!-- 数据  -->
	 	<div id="content" style="width: 1024px;">	 
		</div>
	 	<div id="container" style="width: 1024px; height: 400px"></div>
	<div class="list_bottom_query">
	</div>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>
