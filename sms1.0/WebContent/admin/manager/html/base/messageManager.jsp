<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common" />
<c:set scope="request" var="meta_title" value="消息管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,mid){
		if (confirm("是否删除消息！")){
			o.href="message_delMessage-"+mid+".html";
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
    		<h1 class="font1">消息管理</h1>
    		您可以在这里输入搜索数据:共查询出<span style="color: red;">${maxCount}</span>条数据.
    		<form action="message_list-${page}.html" method="post">
    			主题关键字：<input name="sc" value=""/>
    			选择类型:<select name="ty">
    				<option value="0">不知道</option>
    				<c:forEach items="${messageTypeList}" var="mtl">
    					<option value="${mtl.id }" <c:if test="${ty == mtl.id}">selected</c:if>>${mtl.name }</option>
    				</c:forEach>
    			</select>
    			<input type="submit" value="搜索吧" />
    		</form>
    		<input type="button" value="发送信息" onclick="to('message_updateMessage.html')"/>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
	<div class="list_items font1 bg">
	
	   <div class="list_id">编号</div>
	   <div class="list_time">发件人</div>
	   <div class="list_dlong">主题</div>
	   <div class="list_operate">发送时间</div>
	   <div class="list_id">发送状态</div>
	   <div class="list_id">保存状态</div>
	   <div class="list_operate">操作</div>
	</div>
	<c:forEach items="${messageList}" var="message">
		<div class="list_items">
			<form action="message_update.html" method="post">
				<div class="list_id">${message.id}</div>
			    <div class="list_time"><a href="user_updateInit-${message.uid}.html">${message.nikeName}</a></div>
			    <div class="list_dlong"><a href="message_updateMessage-${message.id }.html">${message.title}</a></div>
			    <div class="list_operate">${message.createTime}</div>
			    <div class="list_id">${message.isSend== 0 ?"草稿":"已发送"}</div>
			    <div class="list_id">${message.isVisible== 0 ?"显示":"删除"}</div>
			    <div class="list_operate">
			    	<c:choose>
			    		<c:when test="${message.isVisible==0}">
					    	<a href="javascript:void();" onclick="deleteEnter(this,${message.id});">删除</a>
			    		</c:when>
			    		<c:otherwise>
					    	<a href="javascript:void();" onclick="to('message_recoveryAdmin-${message.id}.html')">恢复</a>
			    		</c:otherwise>
			    	</c:choose>
			    </div>
			</form>
		</div>
	</c:forEach>
	<div class="page">
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="message_list-${page-1}-${ty}-${sc}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6&&page<maxPage&&page>0}">
			<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s">
				<a href="message_list-${s}-${ty}-${sc}.html">${s+1}</a>
			</c:forEach>
		</c:if>
		<c:if test="${page>=6&&page<maxPage}">
			<a href="message_list-0-${ty}-${sc}.html">1</a>
			<a href="message_list-1-${ty}-${sc}.html">2</a>...
			<a href="message_list-${page-3}-${ty}-${sc}.html">${page-2}</a>
			<a href="message_list-${page-2}-${ty}-${sc}.html">${page-1}</a>
			<a href="message_list-${page-1}-${ty}-${sc}.html">${page}</a>
		</c:if>
		<span class="current">${page+1}</span>
		<c:if test="${page<maxPage-6&&page>=0}">
			<a href="message_list-${page+1}-${ty}-${sc}.html">${page+2}</a>
			<a href="message_list-${page+2}-${ty}-${sc}.html">${page+3}</a>
			<a href="message_list-${page+3}-${ty}-${sc}.html">${page+4}</a>...
			<a href="message_list-${maxPage-2}-${ty}-${sc}.html">${maxPage-1}</a>
			<a href="message_list-${maxPage-1}-${ty}-${sc}.html">${maxPage}</a>
		</c:if>
		<c:if test="${page>=maxPage-6&&page<maxPage}">
			<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="message_list-${s}-${ty}-${sc}.html">${s+1}</a></c:forEach>
		</c:if>
		<c:if test="${page<maxPage-1&&page>=0}"><a href="message_list-${page+1}-${ty}-${sc}.html">Next&gt;</a></c:if>
		<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
	</div>
</div>