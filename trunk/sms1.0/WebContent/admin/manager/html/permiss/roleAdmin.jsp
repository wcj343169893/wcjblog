<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="" />
<c:set scope="request" var="meta_title" value="角色关联"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
<script type="text/javascript">
	function deleteEnter(o,aid){
		if (confirm("您确定要删除此关联吗？")){
			o.href="admin_delAR-"+aid+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
		
	}
</script>
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center" >
    		<h1 class="font1">角色关联[暂时没有用！！！！]</h1>
    		<font class="font3">添加关联:</font>如您需要添加一个关联,你可以在最下面一行中选择帐号数据然后点击"添加"按钮.
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_title">帐号名称</div>
		<div class="list_path">角色名称</div>
		<div class="list_operate">操作</div>
	</div>
	<c:forEach items="${arlist}" var="ad">
		<div class="list_items">
			<div class="list_id">${ad.arid }</div>
			<div class="list_title">
			<c:forEach items="${admins}" var="ads">
				<c:if test="${ads.aid==ad.aid}">${ads.user}</c:if>
			</c:forEach></div>
			<div class="list_path">
			<c:forEach items="${roles}" var="adr">
				<c:if test="${adr.rid==ad.rid}">${adr.title}</c:if>
			</c:forEach></div>
			<div class="list_operate"><a href="javascript:void();" onclick="deleteEnter(this,${ad.arid});">删除</a></div>
		</div>
	</c:forEach>
	<form action="admin_addAR.html" method="post">
	<div class="list_items font1 bg">
		<div class="list_title">帐号名称:</div>
		<div class="list_title"><select name="aid">
			<c:forEach items="${admins}" var="ad">
				<option value="${ad.aid}">${ad.user}</option>
			</c:forEach>
		</select></div>
		<div class="list_path">角色名称：</div>
		<div class="list_title"><select name="rid">
			<c:forEach items="${roles}" var="ad">
				<option value="${ad.rid}">${ad.title}</option>
			</c:forEach>
		</select></div>
		<div class="list_operate"><input type="submit" value="添加" /><input type="hidden" name="formName" value="entity.AdminRole" /></div>
	</div>		
	</form>
</div>
