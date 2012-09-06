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
    	<div class="search_key">如果首次登录，请及时修改个人信息！</div>
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
   	 <div class="update_items">
    	<div class="update_item_name">&nbsp;</div>
    	<div class="update_item_input">&nbsp;</div>
    	<div class="update_item_remarks">&nbsp;</div>
    </div>
	<form action="self_update.html" method="post" onsubmit="return checkSelfSub(this)">
	<input type="hidden" name="_form_session_id" value="<%=_form_session_id %>"> 
	<c:if test="${user.rid == 3}">
   	 <div class="update_items">
    	<div class="update_item_name">学号：</div>
    	<div class="update_item_input"><input type="text" name="snumber" value="${user.snumber}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
	<div class="update_items">
    	<div class="update_item_name">姓名：</div>
    	<div class="update_item_input"><input type="text" name="nikeName" id="nikeName" value="${user.nikeName}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">密码：</div>
    	<div class="update_item_input"><input type="password" name="password" id="password" value="" /></div>
    	<div class="update_item_remarks">默认为手机号码后六位，如果不输入，则不会修改密码</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">再次输入：</div>
    	<div class="update_item_input"><input type="password" name="password1" id="password1" value="" /></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">性别：</div>
    	<div class="update_item_input">
    	<input type="radio" name="sex" value="0" id="sexNo" <c:if test="${empty user.sex || user.sex == 0}">checked</c:if>>
				<label for="sexNo">男</label>
		<input type="radio" name="sex" value="1" id="sexYes"	<c:if test="${user.sex == 1}">checked</c:if>>
				<label for="sexYes">女</label></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <c:if test="${user.rid!=1 && user.rid!=5 && user.rid !=4}">
   	 <div class="update_items">
    	<div class="update_item_name">年级：</div>
    	<div class="update_item_input">${user.gdname }${user.cname }</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
   	 <div class="update_items">
    	<div class="update_item_name">角色&nbsp;&nbsp;分组：</div>
    	<div class="update_item_input">${user.rname}&nbsp;&nbsp;${user.gname }</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">生日：</div>
    	<div class="update_item_input"><input type="text" name="brithday" value="${empty user.brithday ? now:user.brithday}" onClick="WdatePicker()"/></div>
    	<div class="update_item_remarks"><c:if test="${user.brithday == null}">默认为当前日期&nbsp;&nbsp;</c:if>时间格式：YYYY-MM-DD</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">身份证编号：</div>
    	<div class="update_item_input"><input type="text" name="sid" maxlength=18 onkeyup="value=value.replace(/[\W]/g,'')"onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" onkeydown="if(event.keyCode==13)event.keyCode=9" value="${user.sid}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">籍贯：</div>
    	<div class="update_item_input"><input type="text" name="birthplace" value="${user.birthplace}"/></div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">手机号码：</div>
    	<div class="update_item_input"><input type="text" name="mobile" id="mobile" onkeypress="if(event.keyCode<48 || event.keyCode>57) event.returnValue=false;" onkeyup="value=value.replace(/[^\d]/g,'');checkSelf(this)" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" title="${user.mobile}" value="${user.mobile}"/></div>
    	<div class="update_item_remarks" id="mobile_self"></div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name"></div>
    	<div class="update_item_input"><input type="submit" name="" value="保存" /></div>
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