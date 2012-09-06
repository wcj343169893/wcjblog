<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/styles" />
<c:set scope="request" var="script" value="js/common" />
<c:set scope="request" var="meta_title" value="用户管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">家长列表</h1>
    		<span class='search_name'>${clazz.gname}${clazz.name}&nbsp;&nbsp;</span>
    		您可以在这里输入搜索数据:共查询出<span style="color: red;">${maxCount}</span>条数据。同一学生多个家长信息只显示一条，点击详细显示全部。点击标题，可按升降序排列。
    		<form action="parents_list.html" method="post">
    			性别：
				<select name="sex">
					<option value="2">全部</option>
					<option value="0" <c:if test="${sex==0}">selected</c:if>>男</option>
					<option value="1" <c:if test="${sex==1}">selected</c:if>>女</option>
				</select>&nbsp;&nbsp;
    			<input name="ws" value=""/>
    			<input type="submit" value="搜索吧" />
    		</form>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items font1 bg">
	   <div class="list_id"><a href="parents_list-0-${sex}-1-${order=='1' && sort=='asc'?'desc':'asc'}-${ws}.html">编号<font style= "font-family:webdings">${order=="1" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_operate"><a href="parents_list-0-${sex}-2-${order=='2' && sort=='asc'?'desc':'asc'}-${ws}.html">姓名<font style= "font-family:webdings">${order=="2" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_id"><a href="parents_list-0-${sex}-3-${order=='3' && sort=='asc'?'desc':'asc'}-${ws}.html">关系<font style= "font-family:webdings">${order=="3" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_operate"><a href="parents_list-0-${sex}-4-${order=='4' && sort=='asc'?'desc':'asc'}-${ws}.html">家长姓名<font style= "font-family:webdings">${order=="4" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	   <div class="list_title"><a href="parents_list-0-${sex}-5-${order=='5' && sort=='asc'?'desc':'asc'}-${ws}.html">手机号码<font style= "font-family:webdings">${order=="5" ? (sort=="desc"?"5":"6"):""}</font></a></div>
	</div>
	<c:forEach items="${parentsList}" var="parents" varStatus="vs">
		<div class="list_items">
			<form action="parents_update.html" method="post">
		<div class="list_id">${vs.count}</div>
	    <div class="list_operate"><a href="parents_updateInit-${parents.cuid}.html">${parents.nikeName}</a></div>
	    <div class="list_id">${parents.relationship}</div>
	    <div class="list_operate">${parents.pnikeName}</div>
	   	<div class="list_title">${parents.mobile}</div>
			</form>
		</div>
	</c:forEach>
	<div class="page">
	
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="parents_list-${page-1}-${sex}-${order}-${sort}-${ws}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6 && page<maxPage && page>0}">
		
	        <c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s">
	        
				<a href="parents_list-${s}-${sex}-${order}-${sort}-${ws}.html">${s+1}</a>
				
			</c:forEach>
		</c:if>
		<c:if test="${page>=6&&page<maxPage}">
			<a href="parents_list-0-${sex}-${order}-${sort}-${ws}.html">1</a>
			<a href="parents_list-1-${sex}-${order}-${sort}-${ws}.html">2</a>...
			<a href="parents_list-${page-3}-${sex}-${order}-${sort}-${ws}.html">${page-2}</a>
			<a href="parents_list-${page-2}-${sex}-${order}-${sort}-${ws}.html">${page-1}</a>
			<a href="parents_list-${page-1}-${sex}-${order}-${sort}-${ws}.html">${page}</a>
		</c:if>
		<span class="current">${page+1}</span>
		<c:if test="${page<maxPage-6&&page>=0}">
			<a href="parents_list-${page+1}-${sex}-${order}-${sort}-${ws}.html">${page+2}</a>
			<a href="parents_list-${page+2}-${sex}-${order}-${sort}-${ws}.html">${page+3}</a>
			<a href="parents_list-${page+3}-${sex}-${order}-${sort}-${ws}.html">${page+4}</a>...
			<a href="parents_list-${maxPage-2}-${sex}-${order}-${sort}-${ws}.html">${maxPage-1}</a>
			<a href="parents_list-${maxPage-1}-${sex}-${order}-${sort}-${ws}.html">${maxPage}</a>
		</c:if>
		<c:if test="${page>=maxPage-6&&page<maxPage}">
			<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="parents_list-${s}-${sex}-${order}-${sort}-${ws}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page<maxPage-1&&page>=0}"><a href="parents_list-${page+1}-${sex}-${order}-${sort}-${ws}.html">Next&gt;</a></c:if>
		<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
	</div>
</div>