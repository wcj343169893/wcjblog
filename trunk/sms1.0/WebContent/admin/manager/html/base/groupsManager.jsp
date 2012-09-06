<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="" />
<c:set scope="request" var="meta_title" value="分组管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,id){
		if (confirm("您确定要删除此分组吗？删除后无法恢复!")){
			o.href="groups_delete-"+id+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
		
	}
</script>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">分组管理</h1>
    		<font class="font3">添加分组:</font>如您需要添加一个分组,你可以在最下面一行中输入分组信息然后点击"添加"按钮.
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 列名  -->
	<div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<!-- <div class="list_title">分组类型</div> -->
		<div class="list_content">分组名称</div>
		<div class="list_title">角色名称</div>
		<div class="list_operate">操作</div>
	</div>	
 	<!-- 数据  -->
	<c:forEach items="${groupsList}" var="groups">
		<div class="list_items">
		<form action="groups_update.html" method="post">
			<div class="list_id">${groups.id}<input type="hidden" name="id" value="${groups.id}" /></div>
			<!-- 
			 <div class="list_title">
			<select name="tid" id="tid">
	    		<c:forEach items="${groupsTypeList}" var="groupsType">
					<option value="${groupsType.id }" <c:if test="${groupsType.id==groups.tid}">selected</c:if>>${groupsType.name }</option>    	
	    		</c:forEach>
    		</select></div>
			 -->
			
			<div class="list_content"><input name="name" value="${groups.name}" /></div>
			<div class="list_title">
			<select name="rid" id="rid">
	    		<c:forEach items="${roleList}" var="role">
					<option value="${role.id }" <c:if test="${role.id==groups.rid}">selected</c:if>>${role.name }</option>    	
	    		</c:forEach>
    		</select></div>
			<div class="list_operate"><input type="submit" value="修改" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteEnter(this,${groups.id});">删除</a></div>
			<input type="hidden" name="formName" value="entity.Groups" />
		</form>
		</div>
	</c:forEach>
	<!-- 添加项（单独的form） -->
		<div class="list_items">
			<form action="groups_addInit.html" method="post">
				<div class="list_id">&nbsp;</div>
				<!-- 
				<div class="list_title">
				<select name="tid" id="tid">
	    		<c:forEach items="${groupsTypeList}" var="groupsType">
					<option value="${groupsType.id }" <c:if test="${groupsType.id==groups.tid}">selected</c:if>>${groupsType.name }</option>    	
	    		</c:forEach>
    			</select></div>
				 -->
				<div class="list_content"><input name="name" value="" /></div>
				<div class="list_title">
				<select name="rid" id="rid">
	    		<c:forEach items="${roleList}" var="role">
					<option value="${role.id }" <c:if test="${role.id==groups.rid}">selected</c:if>>${role.name }</option>    	
	    		</c:forEach>
    		</select></div>
				<div class="list_operate"><input type="submit" value="添加" />&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<input type="hidden" name="formName" value="entity.Groups" />
			</form>
		</div>
	<div class="page">
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="groups_list-${page-1}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6&&page<maxPage&&page>0}">
			<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="groups_list-${s}.html">${s+1}</a></c:forEach>
			</c:if>
			<c:if test="${page>=6&&page<maxPage}">
				<a href="groups_list-0.html">1</a>
				<a href="groups_list-1.html">2</a>...
				<a href="groups_list-${page-3}.html">${page-2}</a>
				<a href="groups_list-${page-2}.html">${page-1}</a>
				<a href="groups_list-${page-1}.html">${page}</a>
			</c:if>
			<span class="current">${page+1}</span>
			<c:if test="${page<maxPage-6&&page>=0}">
				<a href="groups_list-${page+1}.html">${page+2}</a>
				<a href="groups_list-${page+2}.html">${page+3}</a>
				<a href="groups_list-${page+3}.html">${page+4}</a>...
				<a href="groups_list-${maxPage-2}.html">${maxPage-1}</a>
				<a href="groups_list-${maxPage-1}.html">${maxPage}</a>
			</c:if>
			<c:if test="${page>=maxPage-6&&page<maxPage}">
				<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="groups_list-${s}.html">${s+1}</a></c:forEach>
			</c:if>
			<c:if test="${page<maxPage-1&&page>=0}"><a href="groups_list-${page+1}.html">Next&gt;</a></c:if>
			<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
		</div>
	<div class="list_bottom_query">
	</div>
</div>
