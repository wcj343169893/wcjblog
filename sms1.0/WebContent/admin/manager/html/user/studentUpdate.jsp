<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Random"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<c:set scope="request" var="style" value="css/perm_module,css/notice_update,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="用户管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<%
	Random random=new Random();
	Integer _form_session_id = random.nextInt();
	List<Integer> _form_session=(List<Integer>)request.getSession().getAttribute("_form_session");
	if(_form_session == null){
		_form_session=new ArrayList();
	}
	if(!_form_session.contains(_form_session_id)){
	_form_session.add(_form_session_id);}
	request.getSession().setAttribute("_form_session",_form_session);
%>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">学生详细信息</h1>
    		学生      的所有内容都为必填项。
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
   	 <div class="update_items">
    	<div class="update_item_name">&nbsp;</div>
    	<div class="update_item_input">&nbsp;</div>
    	<div class="update_item_remarks">&nbsp;</div>
    </div>
	<form action="student_update.html" method="post" onsubmit="return checkStuSub(this)">
	<input type="hidden" name="_form_session_id" value="<%=_form_session_id %>"> 
    <div class="update_items">
    	<div class="update_item_name">学号：</div>
    	<div class="update_item_input"><input type="text" name="snumber" value="${student.snumber}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
	<div class="update_items">
    	<div class="update_item_name">姓名：</div>
    	<div class="update_item_input"><input type="text" name="nikeName" id="nikeName" value="${student.nikeName}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">性别：</div>
    	<div class="update_item_input">
    	<input type="radio" name="sex" value="0" id="sexNo" <c:if test="${empty student.sex || student.sex == 0}">checked</c:if>>
				<label for="sexNo">男</label>
		<input type="radio" name="sex" value="1" id="sexYes"	<c:if test="${student.sex == 1}">checked</c:if>>
				<label for="sexYes">女</label></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">班级：</div>
    	<div class="update_item_input">
    	<select name="cid" id="cid">
	    	<c:forEach items="${clazzList}" var="clazz">
				<option value="${clazz.id }" <c:if test="${clazz.id==user.cid}">selected</c:if>>${clazz.gname}${clazz.name }</option>    	
	    	</c:forEach>
    	</select>
    	</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">分组：</div>
    	<div class="update_item_input">
    	<select name="gid" id="gid">
	    	<c:forEach items="${groupsList}" var="groups">
				<option value="${groups.id }" <c:if test="${groups.id==student.gid}">selected</c:if>>${groups.name }</option>    	
	    	</c:forEach>
    	</select></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">身份证编号：</div>
    	<div class="update_item_input"><input type="text" name="sid" maxlength=18 onkeyup="value=value.replace(/[\W]/g,'')"onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" onkeydown="if(event.keyCode==13)event.keyCode=9" value="${student.sid}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">生日：</div>
    	<div class="update_item_input"><input type="text" name="brithday" value="${empty student.brithday ? now:student.brithday}" onClick="WdatePicker()"/></div>
    	<div class="update_item_remarks"><c:if test="${student.brithday == null}">默认为当前日期&nbsp;&nbsp;</c:if>时间格式：	YYYY-MM-DD</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">籍贯：</div>
    	<div class="update_item_input"><input type="text" name="birthplace" value="${student.birthplace}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">手机号码：</div>
    	<div class="update_item_input"><input type="text" name="mobile" id="mobile"  onkeypress="if(event.keyCode<48 || event.keyCode>57) event.returnValue=false;" onkeyup="value=value.replace(/[^\d]/g,'');checkStudent(this) "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength=11 title="${student.mobile}" value="${student.mobile}"/></div>
    	<div class="update_item_remarks" id="mobile_student"> </div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name"></div>
    	<div class="update_item_input"><input type="submit" name="" value="确定" />
    	<c:if test="${empty student.id}"><input type="reset" value="重置" /></c:if></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
	   <input type="hidden" name="formName" value="entity.User" />
	    <input type="hidden" name="id" value="${student.id}" />
	    <input type="hidden" name="gradeId" value="${student.gradeId}" />
	    <input type="hidden" name="rid" id="rid" value="${student.rid}" />
	   </form>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>