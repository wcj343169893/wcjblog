<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery" />
<c:set scope="request" var="meta_title" value="科目分段统计"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">科目分段统计</h1>
    		<font class="font3">科目分段统计</font>&nbsp;&nbsp;&nbsp;<a href="scores_statistics-1.html">总分分段统计</a><br/>
    		<font class="font3">优：</font>大于或等于总分的80%，小于或等于总分.
    		<font class="font3">良：</font>大于或等于总分的60%，小于总分的80%.
    		<font class="font3">中：</font>大于或等于总分的40%，小于总分的60%.
    		<font class="font3">差：</font>小于总分的40%.
		    <!-- 选择考试名字 -->
		  	<div> 请选择考试名称	</div>
		  	<div>
		   			考试名称：<select name="eid" id="eid">
		   						<c:forEach items="${examList}" var="exam">
		   							<option value="${exam.id}">${exam.name}</option>
		   						</c:forEach>
		   					 </select>    		
		   		
		   		<input type="button" onclick="subject()" value="查询" />	   		  	
		   </div>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 列名  -->   
		<div class="list_items font1 bg" style="display:none" id="title">
			<div class="list_id">编号</div>
			<div class="list_title">科目</div>
			<div class="list_id">平均分</div>
			<div class="list_id">优</div>
			<div class="list_id">良</div>
			<div class="list_id">中</div>
			<div class="list_id">差</div>
		</div>		
	 	<!-- 数据  -->
	 	<span id="content">	 
	 	</span>
	<div class="list_bottom_query">
	</div>
</div>
