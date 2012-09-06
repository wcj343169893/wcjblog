<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.snssly.sms.commons.Config"%><c:set scope="request" var="style" value="css/perm_module,css/tabs,css/notice_update" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker,js/message" />
<c:set scope="request" var="meta_title" value="消息"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION) %>" var="login_user"></c:set>
<c:set value="<%=this.getServletContext().getAttribute(Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT) %>" var="roles"></c:set>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">消息</h1><br>
    		收件人：选择通讯录选择需要发送的用户<br />
    		主题：信息的标题（选填）<br>
    		内容：信息的主要信息（必填）
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <div class="update_message_items">
    	<div class="update_item_name">&nbsp;</div>
    	<div class="update_item_input">&nbsp;</div>
    	<div class="update_item_remarks">&nbsp;</div>
    </div>
   	<form action="message_updateMessage.html" method="post" id="inputForm" onsubmit="return mysub(this)">
   		<input type="hidden" value="${message.id }" name="id">
   		<input type="hidden" value="" name="userIds" id="userIds">
   		<input type="hidden" value="1" name="isSend" id="isSend">
   		 <div class="update_message_items">
	    	<div class="update_item_name">收件人</div>
	    	<div class="update_item_input">
	    		<div id="message_linkman">&nbsp;
	    			<c:forEach items="${slList }" var="sl" varStatus="vs">
	    				<div style="z-index: ${vs.count }" id="${sl.uid }">
	    				<strong>${sl.nikeName }</strong><em>&lt;${sl.mobile }&gt;</em><span class="del" onclick="delLinkMan(this)">[X]</span><span>;</span>
	    				</div>
	    			</c:forEach>
	    		</div>
	    		<div>
		    		<div id="linkManList" >
		    			<div class="role_list">
			    			<c:forEach items="${roles}" var="role">
			    				<a href="javascript:void(0)" onclick="changeUserByRid('${role.id}')" title="${role.remarks }">${role.name }</a>
			    			</c:forEach>
		    			</div>
		    			<div id="linkmans">这里有很多联系人哦,<br>快选择上面的角色吧</div>
		    		</div>
	    		</div>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
   		 <div class="update_message_items">
	    	<div class="update_item_name">发送类型</div>
	    	<div class="update_item_input">
	    		<select name="mt">
	    			<c:forEach items="${messageTypeList}" var="mt">
	    				<option value="${mt.id}">${mt.name }</option>
	    			</c:forEach>
	    		</select>
			</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
   		 <div class="update_message_items">
	    	<div class="update_item_name">主题</div>
	    	<div class="update_item_input"><input style="height: 20px;width: 85%" name="title" id="title" value="${message.title }"/></div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
   		 <div class="update_message_items">
	    	<div class="update_item_name">内容</div>
	    	<div class="update_item_input"><textarea rows="10" cols="100%" style="width: 85%" name="content" id="content">${message.content }</textarea> </div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
	   	 <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">
		    	<input type="button" onclick="sub(1)" value="发送" />
		    	<input type="button" onclick="sub(0)" value="存为草稿" />
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
   	</form>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>