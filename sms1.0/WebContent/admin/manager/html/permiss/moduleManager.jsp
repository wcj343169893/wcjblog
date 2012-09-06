<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.snssly.sms.commons.Config"%><c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="" />
<c:set scope="request" var="meta_title" value="模块管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,id){
		if (confirm("您确定要删除此模块吗？\n删除此模块将会删除相关的子模块,以及相关的权限!")){
			o.href="module_delete-"+id+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
		
	}
</script>
<c:set value="<%=this.getServletContext().getAttribute(Config.LOGIN_MODULE_LIST_SERVLET_CONTEXT) %>" var="modules"></c:set>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">模块管理</h1>
    		<font class="font3">所属目录:</font>此模块所在位置,如"角色管理"是"权限管理"中的一个模块,那么就应该是"权限管理"的编号"1".如果此模块是根模块就为"0".<br>
    		<font class="font3">路径:</font>就是点击左边功能后将要显示的页面路径,如果是根模块是一个文件夹的名称.<br>
    		<font class="font3">功能过滤:</font>类似于"路径",不同的是"功能过滤"中的内容不是显示的页面.而是子模块中功能的一个列表,用于权限分配.<br>
    		<font class="font3">添加模块:</font>如您需要添加一个模块,你可以在最下面一行中输入模块数据然后点击"添加"按钮.
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_title">标题</div>
		<div class="list_root">所属目录</div>
		<div class="list_path">路径</div>
		<div class="list_content">功能过滤</div>
		<div class="list_operate">操作</div>
	</div>
	
	<c:if test="${params!=null}">
		<c:set var="begin" value="${params[0]}" scope="page"/>
		<c:set var="end" value="${params[1]}" scope="page"/>
	</c:if>
	<c:if test="${params==null}">
		<c:set var="begin" value="0" scope="page"/>
		<c:set var="end" value="9" scope="page"/>
	</c:if>
	<c:forEach items="${modules}" var="mds" begin="${begin}" end="${end}">
		<div class="list_items">
		<form action="module_update.html" method="post">
			<div class="list_id">${mds.id}<input type="hidden" name="id" value="${mds.id}" /></div>
			<div class="list_title"><input name="name" value="${mds.name}" /></div>
			<div class="list_root"><input name="root" value="${mds.root}" /></div>
			<div class="list_path"><input name="url" value="${mds.url}" /></div>
			<div class="list_content"><input name="menu" value="${mds.menu}" class="input_put_all_div"/></div>
			<div class="list_operate"><input type="submit" value="修改" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteEnter(this,${mds.id});">删除</a></div>
			<input type="hidden" name="formName" value="entity.Module" />
		</form>
		</div>
	</c:forEach>
		<div class="list_items">
		<form action="module_add.html" method="post">
			<div class="list_id">&nbsp;</div>
			<div class="list_title"><input name="name" value="" /></div>
			<div class="list_root"><input name="root" value="" /></div>
			<div class="list_path"><input name="url" value="" /></div>
			<div class="list_content"><input name="menu" value="" class="input_put_all_div"/></div>
			<div class="list_operate"><input type="submit" value="添加" />&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<input type="hidden" name="formName" value="entity.Module" />
		</form>
		</div>
	<c:forEach items="${modules}" varStatus="va">
	<c:if test="${va.last&&va.count<=end}">
	<c:set var="begin" value="${begin-10}" scope="page"/>
		<c:set var="end" value="${end-10}" scope="page"/></c:if></c:forEach>
	<div class="list_bottom_query">
		<div>
			<a class="query_prev" href="moduleManager-${begin-10<=0?0:begin-10}-${end-10<=0?10:end-10}.html">上一页</a>
		</div>
		<div>
			<a class="query_next" href="moduleManager-${begin+10}-${end+10}.html">下一页</a>
		</div>
	</div>
</div>
