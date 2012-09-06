<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery" />
<c:set scope="request" var="meta_title" value="总分分段统计"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">总分分段统计</h1>
    		<br/>
    		<a href="scores_statistics-0.html">科目分段统计</a>&nbsp;&nbsp;&nbsp;<font class="font3">总分分段统计</font>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 选择考试名字 -->
    <!-- 设置分段条件 -->
    <span id="input" style="display:block;">
   		<div class="list_bottom_query">
   			考试名称：<select name="eid" id="eid">
   						<c:forEach items="${examList}" var="exam">
   							<option value="${exam.id}">${exam.name}</option>
   						</c:forEach>
   					 </select>    		
   		</div>    	
		<div class="list_bottom_query">
			分段起始：<input type="text" name="start" id="start" value="" onkeypress="if(event.keyCode<48 || event.keyCode>57)event.returnValue=false;" />
			请输入统计最低分数
		</div>	
		<div class="list_bottom_query">
			分段结束：<input type="text" name="end" id="end" value="" onkeypress="if(event.keyCode<48 || event.keyCode>57)event.returnValue=false;" />
			请输入统计最高分数
		</div>
		<div class="list_bottom_query">
			分段间隔：<input type="text" name="inter" id="inter" value="" onkeypress="if(event.keyCode<48 || event.keyCode>57)event.returnValue=false;" />
			请输入统计的分段间隔
		</div>  
		<div class="list_bottom_query">
			<input type="button" onclick="total()" value="查询" />	
		</div>	
   	</span>
    <!-- 列名  -->
	<div class="list_items font1 bg" style="display:none" id="title">
		<div class="list_id">编号</div>
		<div class="list_title">分数段</div>
		<div class="list_id">人数</div>
	</div>	
 	<!-- 数据  -->
 	<span id="content">	 
 	</span>
	<div class="list_bottom_query">
	</div>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>