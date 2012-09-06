<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.snssly.sms.commons.Config"%><c:set scope="request" var="style" value="css/perm_module,css/tabs,css/notice_update" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker,js/message" />
<c:set scope="request" var="meta_title" value="消息"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION) %>" var="login_user"></c:set>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<div class="message_title">${message.title }</div>
    		 <div class="update_items">
	    		<div class="update_item_name">发送者:</div>
		    	<div class="update_item_input">
			    		<a href="user_updateInit-${message.uid }.html">${message.nikeName }&lt;${message.mobile }&gt;</a>
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		    </div>
    		<div class="update_items">
		    	<div class="update_item_name">时间:</div>
			    	<div class="update_item_input">
				    	${message.createTime }
			    	</div>
		    	<div class="update_item_remarks">
				</div>
	    	</div>
    		<div class="update_message_items">
		    	<div class="update_item_name">&nbsp;</div>
			    	<div class="update_item_input">
			    		<input type="button" onclick="to('message_list.html');" value="返回" />
				    	<input type="button" onclick="to('message_updateMessage-${message.id}-1.html')"  value="回复" />
				    	<input type="button" onclick="to('message_updateMessage-${message.id}-3.html')"  value="回复全部" />
				    	<input type="button" onclick="to('message_updateMessage-${message.id}-2.html')"  value="转发" />
				    	<input type="button" onclick="to('message_delMessage-${message.id}.html');" value="删除" />
			    	</div>
		    	<div class="update_item_remarks">
				</div>
	    	</div>
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
	    	<div class="update_item_name">收件人:</div>
	    	<div class="update_item_input">
	    		<div id="message_linkman_detail">
	    			<c:forEach items="${slList }" var="sl" varStatus="vs">
	    				<div style="z-index: ${vs.count }" id="${sl.uid }" title="${sl.nikeName }&lt;${sl.mobile }&gt;">
	    					<strong>${sl.nikeName }</strong><em>&lt;${sl.mobile }&gt;</em><span>;</span>
	    				</div>
	    			</c:forEach>
	    		</div>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
	    <div class="update_message_items">
	     	<div class="dividing_line"></div>
	    </div>

   		 <div class="update_message_items">
	    	<div class="update_item_name">内容:</div>
	    	<div class="update_item_input">${message.content }</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
	    <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">&nbsp;</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
	    <div class="update_message_items">
	     	<div class="dividing_line"></div>
	    </div>

	   	 <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">
		    	<input type="button" onclick="to('message_list.html');" value="返回" />
		    	<input type="button" onclick="to('message_updateMessage-${message.id}-1.html')"  value="回复" />
		    	<input type="button" onclick="to('message_updateMessage-${message.id}-3.html')"  value="回复全部" />
		    	<input type="button" onclick="to('message_updateMessage-${message.id}-2.html')"  value="转发" />
		    	<input type="button" onclick="to('message_delMessage-${message.id}.html');" value="删除" />
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
   	</form>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>