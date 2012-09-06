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
    		<h1 class="font1">用户详细信息</h1>
    		用户的所有内容都为必填项。<br />
    		<span class="font3">请在信息显示完后，进行操作！</span>
    		<input type="hidden" id="grade_id" value="${user.gradeId }" />
    		<input type="hidden" id="clazzid" value="${user.cid }" />
    		<input type="hidden" id="groupsid" value="${user.gid }" />
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
   	 <div class="update_items">
    	<div class="update_item_name">&nbsp;</div>
    	<div class="update_item_input">&nbsp;</div>
    	<div class="update_item_remarks">&nbsp;</div>
    </div>
	<form action="user_update.html" method="post" id="inputForm" onsubmit="return checkSub(this)">
	<input type="hidden" name="_form_session_id" value="<%=_form_session_id %>">
	<div class="update_items">
    	<div class="update_item_name">姓名：</div>
    	<div class="update_item_input"><input type="text" name="nikeName" id="nikeName" value="${user.nikeName}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
	<c:if test="${user.id!=null}">
   	<div class="update_items">
    	<div class="update_item_name">登录名：</div>
    	<div class="update_item_input">
    		<input type="text" name="name" value="${user.name}" readonly="readonly"/>
    	</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
   	 <div class="update_items">
    	<div class="update_item_name">性别：</div>
    	<div class="update_item_input">
    	<input type="radio" name="sex" value="0" id="sexNo" <c:if test="${empty user.sex || user.sex == 0}">checked</c:if>>
				<label for="sexNo">男</label>
		<input type="radio" name="sex" value="1" id="sexYes"	<c:if test="${user.sex == 1}">checked</c:if>>
				<label for="sexYes">女</label>
    	</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	  <div class="update_items">
    	<div class="update_item_name">角色：</div>
    	<div class="update_item_input">
    	<select name="rid" onchange="changeRole()" id="rid">
	    	<c:forEach items="${roleList}" var="role">
				<option value="${role.id }" <c:if test="${role.id==user.rid}">selected</c:if>>${role.name }</option>    	
	    	</c:forEach>
    	</select></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <script type="text/javascript">
    	$(document).ready(function(){changeRole();});
    	</script>
   	 <c:if test="${user.gid!=0}">
   	 <div class="update_items">
    	<div class="update_item_name">分组：</div>
    	<div class="update_item_input">
    	<select name="gid" id="groups">
    	</select></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
   	 <div id="showgrade" style="display:  <c:if test="${user.rid !=1 && user.rid !=5}">none;</c:if>">
   	 	<div class="update_items">
   	 		<div class="update_item_name">学校：</div>
	    	<div class="update_item_input">
	    		<select name="schoolId" onchange="changeSchool()" id="schoolId">
	    			<c:forEach items="${schoolList}" var="school">
						<option value="${school.id}">${school.name }</option>    	
		    		</c:forEach>
	    		</select>
	    	</div>
   	 	</div>
	   	 <div class="update_items">
	    	<div class="update_item_name">年级：</div>
	    	<div class="update_item_input">
	    	<select name="gradeId" onchange="changeGrade()" id="gradeId">
	    	</select></div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    	<script type="text/javascript">
	    	$(document).ready(function(){changeSchool();});
	    	</script>
	   	 </div>
	   	 <div class="update_items">
	    	<div class="update_item_name">班级：</div>
	    	<div class="update_item_input">
	    		<select name="cid" id="clazz">
				</select>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
   	 </div>
   	 <div id="showsnumber" style="display:  <c:if test="${user.rid!=2 && user.rid!=1 && user.rid != 5}">none;</c:if>">
   	 <div class="update_items">
    	<div class="update_item_name">学号：</div>
    	<div class="update_item_input"><input type="text" name="snumber" value="${user.snumber}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">生日：</div>
    	<div class="update_item_input"><input type="text" name="brithday" value="${empty user.brithday ? now:user.brithday}" onClick="WdatePicker()"/></div>
    	<div class="update_item_remarks"><c:if test="${user.brithday == null}">默认为当前日期&nbsp;&nbsp;</c:if>时间格式：	YYYY-MM-DD</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">身份证编号：</div>
    	<div class="update_item_input"><input type="text" name="sid" maxlength=18 onkeyup="value=value.replace(/[\W]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" onkeydown="if(event.keyCode==13)event.keyCode=9" value="${user.sid}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">籍贯：</div>
    	<div class="update_item_input"><input type="text" name="birthplace" value="${user.birthplace}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">手机号码：</div>
    	<div class="update_item_input"><input type="text" name="mobile" id="mobile"  onkeypress="if(event.keyCode<48 || event.keyCode>57) event.returnValue=false;" onkeyup="value=value.replace(/[^\d]/g,'');checkUser(this) " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength=11 title="${user.mobile}" value="${user.mobile}"/></div>
    	<div class="update_item_remarks" id="mobile_user"> </div>
   	 </div>
   	  <div class="update_items">
    	<div class="update_item_name">会员时间：</div>
    	<div class="update_item_input"><input type="text" name="memberEndTime" value="${empty user.memberEndTime ? now:user.memberEndTime}" onClick="WdatePicker()"/></div>
    	<div class="update_item_remarks"></div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">是否启用：</div>
    	<div class="update_item_input">
    	<input type="radio" name="isVisible" value="0" id="isVisibleNo" <c:if test="${empty user.isVisible || user.isVisible == 0}">checked</c:if>>
				<label for="isVisibleNo">正常</label>
		<input type="radio" name="isVisible" value="1" id="isVisibleYes"	<c:if test="${user.isVisible == 1}">checked</c:if>>
				<label for="isVisibleYes">禁用</label></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name"></div>
    	<div class="update_item_input"><input type="submit" name="" value="确定" />
    	<c:if test="${empty user.id}"><input type="reset" value="重置" /></c:if></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
	   <input type="hidden" name="formName" value="entity.User" />
	    <input type="hidden" name="id" value="${user.id}" />
	   </form>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>