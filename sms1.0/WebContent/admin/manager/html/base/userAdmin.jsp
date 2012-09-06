<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/styles" />
<c:set scope="request" var="script" value="js/common,js/jquery" />
<c:set scope="request" var="meta_title" value="用户管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,id){
		if (confirm("您确定要删除此用户吗？")){
			o.href="user_delete-"+id+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
		
	}
	 $(document).ready(function() {
		 changeGroupsByRole();
		 changeClassByGrade();
	    });
</script>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">会员列表</h1>
    		您可以在这里输入搜索数据:共查询出<span style="color: red;">${maxCount}</span>条数据.点击标题，可按升降序排列。显示阴影的为禁用状态。
    		<input type="hidden" id="gid" value="${gid}" />
    		<input type="hidden" id="cid" value="${cid}" />
    		<form action="user_list.html" method="post">
    			性别：
				<select name="sex">
					<option value="2">全部</option>
					<option value="0" <c:if test="${sex==0}">selected</c:if>>男</option>
					<option value="1" <c:if test="${sex==1}">selected</c:if>>女</option>
				</select>&nbsp;&nbsp;
				角色：
				<select name="rid" onchange="changeGroupsByRole()" id="rid">
					<option value="0">全部</option>
					<c:forEach items="${roleList}" var="role" varStatus="vs">
						<option value="${role.id}" <c:if test="${rid == role.id}">selected</c:if>>${role.name }</option>
					</c:forEach>
				</select>&nbsp;&nbsp;
				
    			分组：  					
    				<select name="gid" id="groups">
					<option value="0">全部</option>
    				</select><br>
    			学校:
    				<select name="schoolId" onchange="changeSchool(1)" id="schoolId">
    					<option value="0">全部</option>
		    			<c:forEach items="${schoolList}" var="school">
							<option value="${school.id}">${school.name }</option>    	
			    		</c:forEach>
	    			</select>
				年级：
				<select name="gradeId" onchange="changeClassByGrade()" id="gradeId">
					<option value="0">全部</option>
    			</select>
   				班级：
    			<select name="cid" id="clazz">
					<option value="0">无</option>
				</select>
    			状态：
    				<select name="isVisible">
						<option value="2">全部</option>
						<option value="0" <c:if test="${isVisible==0}">selected</c:if>>正常</option>
						<option value="1" <c:if test="${isVisible==1}">selected</c:if>>禁用</option>
					</select>
    			<input name="ws" value=""/>
    			<input type="submit" value="搜索吧" />
    		</form>
    		<input type="button" value="添加" onclick="to('user_updateInit.html')"/>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items font1 bg">
	   <div class="list_id"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-id-${order=='id' && sort=='asc'?'desc':'asc'}-${ws}.html">编号<font style= 'font-family:webdings'>${order=="id" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-nikeName-${order=='nikeName' && sort=='asc'?'desc':'asc'}-${ws}.html">姓名<font style= 'font-family:webdings'>${order=="nikeName" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_operate"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-name-${order=='name' && sort=='asc'?'desc':'asc'}-${ws}.html">登录名<font style= 'font-family:webdings'>${order=="name" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-sex-${order=='sex' && sort=='asc'?'desc':'asc'}-${ws}.html">性别<font style= 'font-family:webdings'>${order=="sex" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_operate"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-cid-${order=='cid' && sort=='asc'?'desc':'asc'}-${ws}.html">班级<font style= 'font-family:webdings'>${order=="cid" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-rid-${order=='rid' && sort=='asc'?'desc':'asc'}-${ws}.html">角色<font style= 'font-family:webdings'>${order=="rid" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-gid-${order=='gid' && sort=='asc'?'desc':'asc'}-${ws}.html">分组<font style= 'font-family:webdings'>${order=="gid" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_operate"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-mobile-${order=='mobile' && sort=='asc'?'desc':'asc'}-${ws}.html">手机号码<font style= 'font-family:webdings'>${order=="mobile" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_time_date"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-lastLoginTime-${order=='lastLoginTime' && sort=='asc'?'desc':'asc'}-${ws}.html">最后登录<font style= 'font-family:webdings'>${order=="lastLoginTime" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_time_date"><a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-memberEndTime-${order=='memberEndTime' && sort=='asc'?'desc':'asc'}-${ws}.html">会员时间<font style= 'font-family:webdings'>${order=="memberEndTime" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id">操作</div>
	</div>
	<c:forEach items="${userList}" var="user" varStatus="vs">
		<div class="list_items ${user.isVisible ==1 ?'bg1':""}">
			<form action="user_update.html" method="post">
		<div class="list_id">${vs.count}</div>
	    <div class="list_id"><a href='user_updateInit-${user.id}.html'>${user.nikeName}</a></div>
	    <div class="list_operate"><a href="user_updateInit-${user.id}.html">${user.name}</a></div>
	    <div class="list_id">${empty user.sex || user.sex == 0 ?"男":"女"}</div>
	    <div class="list_operate">${user.gdname}${user.cname}</div>
	    <div class="list_id">${user.rname}</div>
	    <div class="list_id">${user.gname}</div>
	    <div class="list_operate">${user.mobile}</div>
	    <div class="list_time_date">${user.lastLoginTime}</div>
	     <div class="list_time_date">${empty user.memberEndTime ? "永久会员":user.memberEndTime}</div>
	    <div class="list_id"><a href="javascript:void();" onclick="deleteEnter(this,${user.id});">删除</a></div>
			</form>
		</div>
	</c:forEach>
	<div class="page">
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="user_list-${page-1}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6&&page<maxPage&&page>0}">
			<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="user_list-${s}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page>=6&&page<maxPage}">
			<a href="user_list-0-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">1</a>
			<a href="user_list-1-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">2</a>...
			<a href="user_list-${page-3}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page-2}</a>
			<a href="user_list-${page-2}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page-1}</a>
			<a href="user_list-${page-1}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page}</a>
		</c:if>
		<span class="current">${page+1}</span>
		<c:if test="${page<maxPage-6&&page>=0}">
			<a href="user_list-${page+1}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page+2}</a>
			<a href="user_list-${page+2}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page+3}</a>
			<a href="user_list-${page+3}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page+4}</a>...
			<a href="user_list-${maxPage-2}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${maxPage-1}</a>
			<a href="user_list-${maxPage-1}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${maxPage}</a>
		</c:if>
		<c:if test="${page>=maxPage-6&&page<maxPage}">
			<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="user_list-${s}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page<maxPage-1&&page>=0}"><a href="user_list-${page+1}-${sex}-${rid}-${gid}-${isVisible}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">Next&gt;</a></c:if>
		<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
	</div>
</div>