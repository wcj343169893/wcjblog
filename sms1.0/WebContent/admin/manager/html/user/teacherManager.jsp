<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/styles" />
<c:set scope="request" var="script" value="js/common,js/jquery" />
<c:set scope="request" var="meta_title" value="用户管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,id){
		if (confirm("您确定要删除此用户吗？")){
			o.href="teacher_delete-"+id+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
	}
	$(document).ready(function() {
		 changeClassByGrade2();
	    });
</script>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">教师列表</h1>
    		
    		您可以在这里输入搜索数据:共查询出<span style="color: red;">${maxCount}</span>条数据.点击标题，可按升降序排列。
    		<form action="teacher_list.html" method="post">
    			性别：
				<select name="sex">
					<option value="2">全部</option>
					<option value="0" <c:if test="${sex==0}">selected</c:if>>男</option>
					<option value="1" <c:if test="${sex==1}">selected</c:if>>女</option>
				</select>&nbsp;&nbsp;			
    			分组：  					
    				<select name="gid" id="groups">
					<option value="0">全部</option>
	    				<c:forEach items="${groupsList}" var="groups">
							<option value="${groups.id}" <c:if test="${groups.id == gid}">selected</c:if>>${groups.name }</option>    	
	    				</c:forEach>
    				</select>
    			年级：
				<select name="gradeId" onchange="changeClassByGrade2()" id="gradeId">
					<option value="0">全部</option>
	    			<c:forEach items="${gradeList}" var="grade">
						<option value="${grade.id}" <c:if test="${grade.id == gradeId}">selected</c:if>>${grade.name }</option>    	
	    			</c:forEach>
    			</select>
   				班级：
    			<select name="cid" id="clazz">
					<option value="0">无</option>
				</select>
    			<input name="ws" value=""/>
    			<input type="submit" value="搜索吧" />
    		</form>
    		<input type="button" value="添加" onclick="to('teacher_updateInit.html')"/>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items font1 bg">
	   <div class="list_id"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-0-${order=='0' && sort=='asc'?'desc':'asc'}-${ws}.html">编号<font style= 'font-family:webdings'>${order=="0" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_time_date"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-1-${order=='1' && sort=='asc'?'desc':'asc'}-${ws}.html">教师姓名<font style= 'font-family:webdings'>${order=="1" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-2-${order=='2' && sort=='asc'?'desc':'asc'}-${ws}.html">性别<font style= 'font-family:webdings'>${order=="2" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_operate"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-3-${order=='3' && sort=='asc'?'desc':'asc'}-${ws}.html">班级<font style= 'font-family:webdings'>${order=="3" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-4-${order=='4' && sort=='asc'?'desc':'asc'}-${ws}.html">分组<font style= 'font-family:webdings'>${order=="4" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_time"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-5-${order=='5' && sort=='asc'?'desc':'asc'}-${ws}.html">身份证编号<font style= 'font-family:webdings'>${order=="5" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_time_date"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-6-${order=='6' && sort=='asc'?'desc':'asc'}-${ws}.html">生日<font style= 'font-family:webdings'>${order=="6" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-7-${order=='7' && sort=='asc'?'desc':'asc'}-${ws}.html">籍贯<font style= 'font-family:webdings'>${order=="7" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	    <div class="list_operate"><a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-8-${order=='8' && sort=='asc'?'desc':'asc'}-${ws}.html">手机号码<font style= 'font-family:webdings'>${order=="8" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id">操作</div>
	</div>
	<c:forEach items="${teacherList}" var="teacher" varStatus="vs">
		<div class="list_items">
			<form action="teacher_update.html" method="post">
		<div class="list_id">${vs.count}</div>
	    <div class="list_time_date"><a href='teacher_updateInit-${teacher.id}.html'>${teacher.nikeName}</a></div>
	    <div class="list_id">${empty teacher.sex || teacher.sex == 0 ?"男":"女"}</div>	    
	    <div class="list_operate">${teacher.gdname}${teacher.cname}</div>
	    <div class="list_id">${teacher.gname}</div>
	    <div class="list_time">${teacher.sid}</div> 
	    <div class="list_time_date">${teacher.brithday}</div>
	    <div class="list_id">${teacher.birthplace}</div>
	    <div class="list_operate">${teacher.mobile}</div>
	    <div class="list_id"><a href="javascript:void();" onclick="deleteEnter(this,${teacher.id});">删除</a></div>
			</form>
		</div>
	</c:forEach>
	<div class="page">
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="teacher_list-${page-1}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6&&page<maxPage&&page>0}">
			<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="teacher_list-${s}.-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page>=6&&page<maxPage}">
			<a href="teacher_list-0-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">1</a>
			<a href="teacher_list-1-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">2</a>...
			<a href="teacher_list-${page-3}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page-2}</a>
			<a href="teacher_list-${page-2}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page-1}</a>
			<a href="teacher_list-${page-1}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page}</a>
		</c:if>
		<span class="current">${page+1}</span>
		<c:if test="${page<maxPage-6&&page>=0}">
			<a href="teacher_list-${page+1}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page+2}</a>
			<a href="teacher_list-${page+2}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page+3}</a>
			<a href="teacher_list-${page+3}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${page+4}</a>...
			<a href="teacher_list-${maxPage-2}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${maxPage-1}</a>
			<a href="teacher_list-${maxPage-1}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${maxPage}</a>
		</c:if>
		<c:if test="${page>=maxPage-6&&page<maxPage}">
			<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="teacher_list-${s}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page<maxPage-1&&page>=0}"><a href="teacher_list-${page+1}-${sex}-${gid}-${gradeId}-${cid}-${order}-${sort}-${ws}.html">Next&gt;</a></c:if>
		<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
	</div>
</div>