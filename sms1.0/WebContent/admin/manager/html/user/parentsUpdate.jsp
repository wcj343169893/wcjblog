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
<script type="text/javascript">
	function deleteEnter(o,puid,upid,cuid){
		if (confirm("您确定要删除此用户吗？")){
			o.href="parents_delete-"+puid+"-"+upid+"-"+cuid+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
		
	}
</script>
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
    		<h1 class="font1">家长详细信息</h1>
    	<span class='search_name'>${clazz.gname}${clazz.name}&nbsp;&nbsp;</span><span class='search_key'>${student.nikeName}</span><br />
    	家长      的所有内容都为必填项。<br />手机号码默认为登录账号。
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
   	 <div class="list_items font1 bg">
   	   <div class="list_id">编号</div>
	   <div class="list_title">手机号码</div>
	   <div class="list_title">关系</div>
	   <div class="list_title">家长姓名</div>
	   <div class="list_title">操作</div>
	</div>
	<c:forEach items="${parentsList}" var="parents" varStatus="vs">
		<div class="list_items">
			<form action="parents_update.html" method="post">
		<div class="list_id">${vs.count}</div>
	   	<div class="list_title">${parents.mobile}</div>
	    <div class="list_title">${parents.relationship}</div>
	    <div class="list_title">${parents.nikeName}</div>
	    <div class="list_title"><a href="javascript:void(0);" onclick="update('${parents.puid}','${parents.nikeName}','${parents.relationship}','${parents.mobile}','${parents.upid}');">修改</a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteEnter(this,${parents.puid},${parents.upid},${parents.cuid});">删除</a></div>
			<input type="hidden" name="cuid" value="${parents.cuid }"/>
			<input type="hidden" name="puid" value="${parents.puid }"/>
			<input type="hidden" name="id" value="${parents.upid}"/>
			<input type="hidden" name="gid" value="${parents.gid}"/>
			<input type="hidden" name="gradeId" value="${clazz.gid}"/>
			<input type="hidden" name="cid" value="${clazz.id}"/>
			</form>
		</div>
	</c:forEach>
	<div class="list_items font1">
   	   <div></div>
	</div>
	<!-- 添加项（单独的form） -->
		<div class="list_items">
			<form action="parents_update.html" method="post" id="inputForm" onsubmit="return checkParSub(this)">
				<input type="hidden" name="_form_session_id" value="<%=_form_session_id %>"> 
				<div class="list_id"><input type="hidden" value="" name="id" /></div>
				<div class="list_title"><input type="text" name="mobile" value="" id="mobile" maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'');checkParents(this.value) "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" onkeypress="if(event.keyCode<48 || event.keyCode>57) event.returnValue=false;"/></div>
				<div class="list_title"><input type="text" name="relationship" id="relationship" value=""/></div>
				<div class="list_title"><input type="text" name="nikeName" id="nikeName" value=""/></div>
				<div class="list_title"><input type="submit" value="保存" /><input type="reset" value="重置" /></div>
				<input type="hidden" name="formName" value="entity.User" />
				<input type="hidden" name="cuid" value="${student.id }"/>
				<input type="hidden" name="upid" value=""/>
				<input type="hidden" name="puid" value=""/>
				<input type="hidden" name="rid" id="rid" value=""/>
				<input type="hidden" id="isAddTo" value="false"/>
			</form>
		</div>
	<div id="mobile_parent" style="width:800px;"></div>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>
