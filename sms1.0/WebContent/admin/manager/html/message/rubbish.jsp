<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.snssly.sms.commons.Config"%><c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery" />
<c:set scope="request" var="meta_title" value="垃圾箱"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION) %>" var="login_user"></c:set>
<div class="context_right_all">
<script type="text/javascript">
	$(document).ready(function(){
		checkall();
	});
</script>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">垃圾箱</h1><br>
    		共查询出<span style="color: red;">${maxCount}</span>条数据.
    		<br><br>
    		<div class="tabs_toolBar">
    			<input type="button" value="彻底删除" onclick="deleteMessage()">
    			<input type="button" value="恢复" onclick="recoveryMessage()">
    			<span id="message_notice"></span>
    		</div>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items_message font1 bg">
		<div class="list_message_id"><input type="checkbox" title="全选" id="checkAllMessage" value="3"></div>
	 	<div class="list_message_ico"></div>
	 	<div class="list_message_nikeName">发件人</div>
	   	<div class="list_message_content">主题</div>
	   	<div class="list_message_time">发送时间</div>
	</div>
	<c:forEach items="${messageList}" var="message">
		<div class="list_items_message">
			<form action="message_update.html" method="post">
				<div class="list_message_id"><input type="checkbox" value="${message.id}" class="message_checkbox"></div>
				 <div class="list_message_ico"></div>
				 <div class="list_message_nikeName">${message.nikeName }</div>
			    <div class="list_message_content">${message.title}</div>
			    <div class="list_message_time">${message.createTime}</div>
			</form>
		</div>
	</c:forEach>
	<div class="page">
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="message_rubbish-${page-1}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6&&page<maxPage&&page>0}">
			<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="message_rubbish-${s}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page>=6&&page<maxPage}">
			<a href="message_rubbish-0.html">1</a>
			<a href="message_rubbish-1.html">2</a>...
			<a href="message_rubbish-${page-3}.html">${page-2}</a>
			<a href="message_rubbish-${page-2}.html">${page-1}</a>
			<a href="message_rubbish-${page-1}.html">${page}</a>
		</c:if>
		<span class="current">${page+1}</span>
		<c:if test="${page<maxPage-6&&page>=0}">
			<a href="message_rubbish-${page+1}.html">${page+2}</a>
			<a href="message_rubbish-${page+2}.html">${page+3}</a>
			<a href="message_rubbish-${page+3}.html">${page+4}</a>...
			<a href="message_rubbish-${maxPage-2}.html">${maxPage-1}</a>
			<a href="message_rubbish-${maxPage-1}.html">${maxPage}</a>
		</c:if>
		<c:if test="${page>=maxPage-6&&page<maxPage}">
			<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="message_rubbish-${s}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page<maxPage-1&&page>=0}"><a href="message_rubbish-${page+1}.html">Next&gt;</a></c:if>
		<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
	</div>
</div>
</div>
