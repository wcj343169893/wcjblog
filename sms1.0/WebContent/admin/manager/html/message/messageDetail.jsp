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
			    		${message.nikeName }&lt;${message.mobile }&gt;
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
				    	<c:choose>
				    		<c:when test="${message.uid==login_user.id}">
						    	<input type="button"  onclick="to('message_outbox.html');" value="返回" />
						    	<input type="button"  value="重发" onclick="to('message_updateInit-${message.tid }-${message.id}-4.html')"/>
						    	<input type="button"  value="回复全部" onclick="to('message_updateInit-${message.tid }-${message.id}-5.html')"/>
						    	<input type="button" value="转发" onclick="to('message_updateInit-${message.tid }-${message.id}-3.html')"/>
						    	<input type="button" value="删除" onclick="to('message_delete-1-${message.id}.html')"/>
				    		</c:when>
				    		<c:otherwise>
						    	<input type="button" onclick="to('message_inbox.html');" value="返回" />
						    	<input type="button"  value="回复" onclick="to('message_updateInit-${message.tid }-${message.id}-2.html')"/>
						    	<input type="button" value="转发" onclick="to('message_updateInit-${message.tid }-${message.id}-3.html')"/>
						    	<input type="button" value="删除" onclick="to('message_delete-0-${message.id}.html')"/>
				    		</c:otherwise>
				    	</c:choose>
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
   
   		<input type="hidden" value="${message.id }" name="id">
   		<input type="hidden" value="" name="userIds" id="userIds">
   		<input type="hidden" value="1" name="isSend" id="isSend">
   		 <div class="update_message_items">
	    	<div class="update_item_name">收件人:</div>
	    	<div class="update_item_input">
	    		<div id="message_linkman_detail">
	    			<c:forEach items="${slList }" var="sl" varStatus="vs">
	    				<c:if test="${sl.isParent==0}">
		    				<div style="z-index: ${vs.count }" id="${sl.uid }" title="${sl.nikeName }&lt;${sl.mobile }&gt;">
		    					<strong>${sl.nikeName }</strong><em>&lt;${sl.mobile }&gt;</em><span>;</span>
		    				</div>
	    				</c:if>
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
   	<form action="message_add.html" method="post" id="inputForm" onsubmit="return mysubQuickReply(this,'快捷回复给：${message.nikeName }')">
   		<input type="hidden" value="1" name="tid">
		<input type="hidden" name="formName" value="entity.Message" />
		<input type="hidden" value="${message.uid }" name="userIds" id="userIds">
		<input type="hidden" value="回复:${message.title }" name="title" id="title">
		<input type="hidden" value="${message.id }" name="replyId" id="replyId">
   		<input type="hidden" value="1" name="isSend" id="isSend">
   		<c:choose>
   			<c:when test="${empty sl.isReply || sl.isReply==0}">
		      <div class="update_message_items">
		    	<div class="update_item_name">快捷回复:</div>
		    	<div class="update_item_input">
			    	<textarea name="content" class="ipt-t-dft txt-info" cols="100" rows="5" id="txtQuickReplyContentread_2" onclick="if(this.value=='快捷回复给：${message.nikeName }')this.value='';this.className='ipt-t-dft'" onblur="if(this.value=='')this.value='快捷回复给：${message.nikeName }';this.className='ipt-t-dft txt-info'">快捷回复给：${message.nikeName }</textarea>
			    	<input type="submit" value="发送" class="send_button">
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
   			</c:when>
   			<c:otherwise>
		      <div class="update_message_items">
		    	<div class="update_item_name">快捷回复:</div>
		    	<div class="update_item_input">
		    		已回复
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
   			</c:otherwise>
   		</c:choose>
   		 <div class="update_message_items">
	     	<div class="dividing_line"></div>
	    </div>
	   	 <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">
		    	<c:choose>
		    		<c:when test="${message.uid==login_user.id}">
				    	<input type="button"  onclick="to('message_outbox.html');" value="返回" />
				    	<input type="button"  value="重发" onclick="to('message_updateInit-${message.tid }-${message.id}-4.html')"/>
				    	<input type="button"  value="回复全部" onclick="to('message_updateInit-${message.tid }-${message.id}-5.html')"/>
				    	<input type="button" value="转发" onclick="to('message_updateInit-${message.tid }-${message.id}-3.html')"/>
				    	<input type="button" value="删除" onclick="to('message_delete-1-${message.id}.html')"/>
		    		</c:when>
		    		<c:otherwise>
				    	<input type="button" onclick="to('message_inbox.html');" value="返回" />
				    	<input type="button"  value="回复" onclick="to('message_updateInit-${message.tid }-${message.id}-2.html')"/>
				    	<input type="button" value="转发" onclick="to('message_updateInit-${message.tid }-${message.id}-3.html')"/>
				    	<input type="button" value="删除" onclick="to('message_delete-0-${message.id}.html')"/>
		    		
		    		</c:otherwise>
		    	</c:choose>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
   	</form>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>