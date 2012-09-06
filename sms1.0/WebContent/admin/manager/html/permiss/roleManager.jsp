<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.snssly.sms.commons.Config"%><c:set scope="request" var="style" value="css/perm_role,css/tabs" />
<c:set scope="request" var="script" value="" />
<c:set scope="request" var="meta_title" value="角色管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">角色管理</h1>
    		<font class="font3">一个管理员可以拥有多个角色，一个角色可以管理多个模块。方便实用！</font><br>
    		<font class="font3">允许访问的模块:</font>管理员能通过此角色来访问的模块,可以从"<font class="font3">所有管理模块</font>"
    		中选择一个或多个模块然后点击"<img src="/admin/manager/images/site_page_16.png">"
    		添加.点击"提交"将修改后的数据提交到数据库.
    		一个"大"模块包含其所有"小"模块,如果添加一个"大"模块,那么此角色将可以访问其中的"小"模块.<br>
    		<font class="font3">添加角色:</font>如您需要添加一个角色,你可以在最下面一行中输入角色名称然后点击"添加"按钮.
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <c:set value="<%=this.getServletContext().getAttribute(Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT) %>" var="roles"></c:set>
    <c:set value="<%=this.getServletContext().getAttribute(Config.LOGIN_MODULE_LIST_SERVLET_CONTEXT) %>" var="modules"></c:set>
    <c:set value="<%=this.getServletContext().getAttribute(Config.LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT) %>" var="roleModules"></c:set>
    <c:forEach items="${roles}" var="role">
    <div class="role_items">
    	<div class="role_title">
    		<form action="role_updateRole.html" method="post">
    			角色名称：<input name="name" value="${role.name}" class="font3"/>&nbsp;&nbsp;&nbsp;
    			<input type="hidden" name="formName" value="entity.Role" />
    			<input type="hidden" name="id" value="${role.id}" />
    			<input type="submit" value="修改" />&nbsp;&nbsp;&nbsp;<a href="role_deleteRole-${role.id}.html" title="删除">删除</a>
    		</form>
    	</div>
    	<div class="role_context">
    		<div class="role_context_left">
    			所有管理模块<br>
    			<select multiple="multiple" id="allModule_${role.id}">
    				<c:forEach items="${modules}" var="mds">
    					<option value="${mds.id}">${mds.root==0?"大":"小"}:${mds.name}</option>
    				</c:forEach>
    			</select>
    		</div>
    		<div class="role_context_center">
    			<div><a href="javascript:void(0);" onclick="addModule('allModule_${role.id}','Modules_${role.id}');" class="a_right" title="添加"></a></div>
    			<div><a href="javascript:void(0);" onclick="removeModule('Modules_${role.id}');" class="a_left" title="删除"></a></div>
    			<div><a href="javascript:void(0);" onclick="subAction(this,'${role.id}');" title="提交修改后的内容">提交</a></div>
    		</div>
    		<div class="role_context_right">
    		允许访问的模块<br>
    		<select multiple="multiple" id="Modules_${role.id}">
    			<c:forEach items="${roleModules}" var="rms">
    				<c:if test="${rms.rid==role.id}">
    				<c:forEach items="${modules}" var="mds">
	    				<c:if test="${mds.id==rms.mid}">
	    					<option value="${mds.id}">${mds.root==0?"大":"小"}:${mds.name}</option>
	    				</c:if>
    				</c:forEach>
    				</c:if>
    			</c:forEach>
    			</select>
    		</div>
    	</div>
    </div>
</c:forEach>
<div class="add_role_items">
	<form action="role_addRole.html" method="post">
    	角色名称：<input name="name" />&nbsp;&nbsp;&nbsp;
    	<input type="submit" value="添加" />&nbsp;&nbsp;&nbsp;
    	<input type="hidden" name="formName" value="entity.Role" />
    </form>
</div>
<script type="text/javascript">
	function addModule(aid,mid){
		var obja=$(aid);
		var objm=$(mid);
		if(obja.selectedIndex<0)return false;
		var o = obja.options[obja.selectedIndex].value;
		var t = obja.options[obja.selectedIndex].text;
		var isr = false;
		for(var i=0;i<objm.length;i++){
			if(objm.options[i].value==o){
				objm.options[i].defaultSelected=true;
				isr = true;
			}else{
				objm.options[i].defaultSelected=false;
			}
		}
		if(!isr)objm.options.add(new Option(t,o));
	}
	
	function removeModule(mid){
		var objm=$(mid);
		if(objm.selectedIndex<0)return false;
		objm.remove(objm.selectedIndex);
	}
	
	function subAction(o,mid){
		var objm=$("Modules_"+mid);
		if(!confirm("您确定要修改？")){
			return false;
		}
		var href="role_updateRM-"+mid;
		for(var i=0;i<objm.length;i++){
			href+="-"+objm.options[i].value;
		}
		href+=".html";
		o.href=href;
		return true;
	}
	$=function(o){
		return document.getElementById(o);
	}
</script>
</div>
