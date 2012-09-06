<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="" />
<c:set scope="request" var="meta_title" value="帐号管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
<script type="text/javascript">
	function deleteEnter(o,aid){
		if (confirm("您确定要删除此帐号吗？\n删除此帐号将会删除相关的角色关联!")){
			o.href="admin_deleteAdmin-"+aid+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
		
	}
</script>
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">帐号管理</h1>
    		<font class="font3">添加帐号:</font>如您需要添加一个帐号,你可以在最下面一行中输入帐号数据然后点击"添加"按钮.
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_title">帐号名称</div>
		<div class="list_path">登录时间</div>
		<div class="list_operate">操作</div>
	</div>
	<c:forEach items="${admins}" var="ad">
		<div class="list_items">
			<div class="list_id">${ad.aid }</div>
			<div class="list_title">${ad.user}</div>
			<div class="list_path">${ad.setTime}</div>
			<div class="list_operate"><a href="javascript:void(0);" onclick="deleteEnter(this,${ad.aid});">删除</a></div>
		</div>
	</c:forEach>
	<form action="admin_addAdmin.html" method="post">
	<div class="list_items font1 bg">
		<div class="list_title">帐号名称:</div>
		<div class="list_title"><input name="user" /></div>
		<div class="list_path">帐号密码：</div>
		<div class="list_title"><input name="password" type="password" /></div>
		<div class="list_operate"><input type="submit" value="添加" /><input type="hidden" name="formName" value="entity.Adminuser" /></div>
	</div>		
	</form>
</div>
