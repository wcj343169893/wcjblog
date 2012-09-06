<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@page import="com.snssly.sms.commons.Config"%>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/notice_update,css/jquery.treeview,css/screen,css/styles-autocomplate" />
<c:set scope="request" var="script" value="js/common,js/jquery132,DatePicker/WdatePicker,js/message,js/jquery.cookie,js/jquery.treeview,js/jqueryautocomplete" />
<c:set scope="request" var="meta_title" value="新消息"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION) %>" var="login_user"></c:set>
<c:set value="<%=this.getServletContext().getAttribute(Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT) %>" var="roles"></c:set>
<script type="text/javascript">
	$(document).ready(function(){
		$('#query').focus();
		$(".filetree").treeview();
		$('.linkMan-title').bind('click',function(){
			$( this ).next('.filetree')
				.slideToggle();	
			}).eq(0).trigger('click');
	});
	 jQuery(function() {
		    var onAutocompleteSelect = function(value, data) {
		    //  $('#selection').html('<img src="\/global\/flags\/small\/' + data + '.png" alt="" \/> ' + value);
		      //alert("value:"+value+ "  data:"+data);
		      var mobile=value.substring(value.indexOf('(')+1,value.indexOf(')'));
		      var name=value.substring(0,value.indexOf('('));
		     // alert("name:"+name+"   mobile:"+mobile);
		      divAddContext(data,name,mobile);
		    }
		    var options_mobile = {
		      serviceUrl: 'message_findUserByMobile.html',
		      width: 300,
		      delimiter: /(,|;)\s*/,
		      onSelect: onAutocompleteSelect,
		      deferRequestBy: 0, //miliseconds
		      params: { country: 'Yes'},
		      noCache: false //set to true, to disable caching
		    };
		  var  lkm_onload = $('#query').autocomplete(options_mobile);
		});
	</script>
	<c:if test="${eid >0}">
		<script type="text/javascript">
		$(document).ready(function(){
				findAllScore(${login_user.cid});
				findByEidCid(${eid },${login_user.cid});
		});
		</script>
	</c:if>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">新消息</h1>
    		收件人：请在通讯录中选择<br />
    		主题：信息的标题<br>
    		内容：信息的主要信息
    		<input type="hidden"" id="eid" value="${eid }">
    		<ul class="message_type_list">
	    		<c:forEach items="${messageTypeList}" var="messageType" varStatus="vs">
	    		<c:forEach items="${messageType.ridList}" var="mtrl">
	    			<c:if test="${login_user.rid == mtrl }">
		    			<li>
		    				<input type="radio" value="${messageType.id }" name="mt" <c:if test="${tid==messageType.id}">checked</c:if> id="tid_${vs.count }" onclick="to('message_updateInit-${messageType.id}-${mid}-${function }-0.html');"/>
		    				<label for="tid_${vs.count }">${messageType.name }</label>
		    			</li>
	    			</c:if>
	    		</c:forEach>
	    		</c:forEach>
    		</ul>
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <div class="update_message_items">
    	<div class="update_item_name">&nbsp;</div>
    	<div class="update_item_input">&nbsp;</div>
    	<div class="update_item_remarks">&nbsp;</div>
    </div>
   	<form action="message_add.html" method="post" id="inputForm" onsubmit="return mysub(this)">
   		<input type="hidden" value="${message.id }" name="id">
   		<input type="hidden" value="" name="userIds" id="userIds">
   		<input type="hidden" value="1" name="isSend" id="isSend">
   		<input type="hidden" value="${tid }" name="tid" id="tid">
   		<input type="hidden" value="${message.replyId }" name="replyId" id="replyId">
		<input type="hidden" name="formName" value="entity.Message" />
   		 <div class="update_message_items">
	    	<div class="update_item_name">收件人</div>
	    	<div class="update_item_input">
	    		<div id="message_linkman" title="请输入姓名或者电话,也可以在右边通讯录中选择联系人">&nbsp;
	    			<c:forEach items="${slList }" var="sl" varStatus="vs">
	    				<c:if test="${sl.isParent==0}">
		    				<div style="z-index: ${vs.count }" id="${sl.uid }">
		    				<strong>${sl.nikeName }</strong><em>&lt;${sl.mobile }&gt;</em><span class="del" onclick="delLinkMan(this)">[X]</span><span>;</span>
		    				</div>
	    				</c:if>
	    			</c:forEach>
	    			<input type="text" name="q" id="query" class="textbox" autocomplete="off">
	    		</div>
	    		<div>
		    		<div id="linkManList" >
		    			<div class="role_list bg" title="请选择联系人">
		    				通讯录
		    			</div>
		    			<div id="linkmans">
	    				</div>
	    				<!-- 网站管理员 -->
	    				<div class="linkMan-title bg" title="展开分组">网站管理员</div>
						<ul id="leaderList" class="filetree treeview-famfamfam">
							<c:forEach items="${adminWebUserList}" var="user">
								<c:if test="${login_user.id != user.id }">
									<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>"${user.nikeName }"(${user.mobile })</span></li>
								</c:if>
							</c:forEach>
						</ul>
	    				<!-- 住校管理员 -->
	    				<div class="linkMan-title bg" title="展开分组">住校管理员</div>
						<ul id="leader2List" class="filetree treeview-famfamfam">
							<c:forEach items="${adminUserList}" var="user">
								<c:if test="${login_user.id != user.id }">
									<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>"${user.nikeName }"(${user.mobile })</span></li>
								</c:if>
							</c:forEach>
						</ul>
	    				<c:choose>
	    					<c:when test="${login_user.rid==5}">
		    				<!-- 领导查看到的教师列表 -->
	    					<div class="linkMan-title bg" title="展开分组">老师们</div>
		    				<ul id="teacherList" class="filetree treeview-famfamfam">
				    			<c:forEach items="${gradeList}" var="grade">
				    					<li class="closed">
				    						<c:set value="${fn:length(grade.groupsList) }" var="groupsLength"></c:set>
				    						<span>
				    						${grade.name }(${groupsLength })
				    						</span>
				    						<c:if test="${groupsLength>0}">
					    						<ul>
						    						<c:forEach items="${grade.groupsList}" var="groups">
						    							<li class="closed">
						    								<span class="user_group">
							    								${groups.name }
							    								<c:set value="" var="linkmans"></c:set>
							    								<c:set value="${fn:length(groups.userList) }" var="userLength"></c:set>
							    								<c:forEach items="${groups.userList}" var="user">
							    									<c:set value="${linkmans}${user.id}:${user.nikeName }:${user.mobile }," var="linkmans"></c:set>
							    								</c:forEach>
							    								<a href="javascript:void(0)" onclick="checkAll('${linkmans}')" title="添加所有联系人">(${userLength })</a> 
						    								</span>
						    								<c:if test="${userLength>0}">
							    								<ul>
							    									<c:forEach items="${groups.userList}" var="user">
								    									<c:if test="${login_user.id != user.id }">
								    										<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>${user.cname}<br/> ${user.nikeName }(${user.mobile })</span></li>
								    									</c:if>
							    									</c:forEach>
							    								</ul>
						    								</c:if>
						    							</li>
						    						</c:forEach>
					    						</ul>
				    						</c:if>
				    					</li>
				    			</c:forEach>
		    				</ul>
	    					</c:when>
	    					<c:when test="${login_user.rid==2}">
	    					<!-- 校领导 -->
	    					<div class="linkMan-title bg" title="展开分组">校领导</div>
  								<ul id="leaderList" class="filetree treeview-famfamfam">
  									<c:forEach items="${leaderList}" var="user">
  										<c:if test="${login_user.id != user.id }">
	  										<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>"${user.nikeName }"(${user.mobile })</span></li>
  										</c:if>
  									</c:forEach>
  								</ul>
		    				<!-- 本班学生 -->
		    				<div class="linkMan-title bg" title="展开分组">我的学生</div>
		    				<ul id="studentList" class="filetree treeview-famfamfam">
	    						<c:forEach items="${groupsList}" var="groups">
	    							<li class="closed">
	    								<span class="user_group">
		    								${groups.name }
		    								<c:set value="" var="linkmans"></c:set>
		    								<c:set value="${fn:length(groups.userList) }" var="userLength"></c:set>
		    								<c:forEach items="${groups.userList}" var="user">
		    									<c:set value="${linkmans}${user.id}:${user.nikeName }:${user.mobile }," var="linkmans"></c:set>
		    								</c:forEach>
		    								<a href="javascript:void(0)" onclick="checkAll('${linkmans}')" title="添加所有联系人">(${userLength })</a> 
	    								</span>
	    								<c:if test="${userLength>0}">
		    								<ul>
		    									<c:forEach items="${groups.userList}" var="user">
			    									<c:if test="${login_user.id != user.id }">
			    										<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>"${user.nikeName }"(${user.mobile })</span></li>
			    									</c:if>
		    									</c:forEach>
		    								</ul>
	    								</c:if>
	    							</li>
	    						</c:forEach>
	   						</ul>
	    					</c:when>
	    					<c:when test="${login_user.rid==3 || login_user.rid==4}">
	    					<!-- 我们的老师 -->
	    					<div class="linkMan-title bg" title="展开分组">我们的老师</div>
   								<ul id="teacherList" class="filetree treeview-famfamfam">
   									<c:forEach items="${teacherList}" var="user">
	   									<c:if test="${login_user.id != user.id }">
	   										<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>"${user.nikeName }"(${user.mobile })</span></li>
	   									</c:if>
   									</c:forEach>
   								</ul>
	    					</c:when>
	    					<c:otherwise>
	    						<!-- 校领导 -->
		    					<div class="linkMan-title bg" title="展开分组">校领导</div>
  								<ul id="leaderList" class="filetree treeview-famfamfam">
  									<c:forEach items="${leaderList}" var="user">
  										<c:if test="${login_user.id != user.id }">
	  										<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>"${user.nikeName }"(${user.mobile })</span></li>
  										</c:if>
  									</c:forEach>
  								</ul>
  								<!-- 查看到的教师列表 -->
		    					<div class="linkMan-title bg" title="展开分组">老师们</div>
			    				<ul id="teacherList" class="filetree treeview-famfamfam">
					    			<c:forEach items="${gradeList}" var="grade">
				    					<li class="closed">
				    						<c:set value="${fn:length(grade.groupsList) }" var="groupsLength"></c:set>
				    						<span>
				    						${grade.name }(${groupsLength })
				    						</span>
				    						<c:if test="${groupsLength>0}">
					    						<ul>
						    						<c:forEach items="${grade.groupsList}" var="groups">
						    							<li class="closed">
						    								<span class="user_group">
							    								${groups.name }
							    								<c:set value="" var="linkmans"></c:set>
							    								<c:set value="${fn:length(groups.userList) }" var="userLength"></c:set>
							    								<c:forEach items="${groups.userList}" var="user">
							    									<c:set value="${linkmans}${user.id}:${user.nikeName }:${user.mobile }," var="linkmans"></c:set>
							    								</c:forEach>
							    								<a href="javascript:void(0)" onclick="checkAll('${linkmans}')" title="添加所有联系人">(${userLength })</a> 
						    								</span>
						    								<c:if test="${userLength>0}">
							    								<ul>
							    									<c:forEach items="${groups.userList}" var="user">
							    										<c:if test="${login_user.id != user.id }">
								    										<li class="u"><span class="user" onclick="divAddContext(${user.id},'${user.nikeName }','${user.mobile }')" title='"${user.nikeName }"${user.mobile }'>${user.cname}<br/> ${user.nikeName }(${user.mobile })</span></li>
							    										</c:if>
							    									</c:forEach>
							    								</ul>
						    								</c:if>
						    							</li>
						    						</c:forEach>
					    						</ul>
				    						</c:if>
				    					</li>
					    			</c:forEach>
			    				</ul>
	    					</c:otherwise>
	    				</c:choose>
		    		</div>
	    		</div>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
   		 <div class="update_message_items">
	    	<div class="update_item_name">主题</div>
	    	<div class="update_item_input"><input style="height: 20px;width: 85%" name="title" id="title" value="${message.title }"/></div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
   		 <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">
	    	<c:choose>
	    		<c:when test="${tid==2}">
			    	<input type="button" value="家庭作业" onclick="findAllSubject(${login_user.cid})"/>
	    		</c:when>
	    		<c:when test="${tid==3}">
			    	<input type="button" value="考试成绩" onclick="findAllScore(${login_user.cid})"/>&nbsp;以下为只读内容，无法修改
	    		</c:when>
	    		<c:when test="${tid==4}">
			    	<input type="button" value="评语模板" onclick="getTemplate(${tid})"/>
	    		</c:when>
	    		<c:when test="${tid==5}">
			    	<input type="button" value="祝福模板" onclick="getTemplate(${tid})"/>
	    		</c:when>
	    		<c:otherwise>
	    			<span id="contentLength">请输入信息内容</span>
	    		</c:otherwise>
	    	</c:choose>
	    	<div id="buildBox">
	    	</div>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
		</div>	    
   		 <div class="update_message_items">
	    	<div class="update_item_name">内容</div>
	    	<div class="update_item_input"><textarea rows="10" cols="100%" style="width: 85%" name="content" id="content" <c:if test="${tid==3}">readonly="readonly"</c:if> onchange="checkLength()" onkeyup="checkLength()">${message.content }</textarea></div>
	    	<div class="update_item_remarks">&nbsp;</div>
	    </div>
	   	 <div class="update_message_items">
	    	<div class="update_item_name">使用短信签名</div>
	    	<div class="update_item_input">
		    	<input type="checkbox" name="isSignature" value="1"/>
		    	<input type="text" value="${login_user.nikeName }" readonly="readonly"/>
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
	    <c:if test="${login_user.rid==2}">
	    	<!-- 教师特权 -->
		   	 <div class="update_message_items">
		    	<div class="update_item_name">短信接收对象</div>
		    	<div class="update_item_input">
			    	<input type="checkbox" id="toParent" name="toUser" checked="checked" value="0"><label for="toParent">家长</label>&nbsp;
			    	<input type="checkbox" id="toChild" name="toUser" value="1"><label for="toChild">学生</label>  【说明：发送给家长的短信会默认加上学生姓名】
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
	   	 </c:if>
	   	 <c:if test="${tid==3}">
	   	 <!-- 成绩 -->
		   	 <div class="update_message_items">
		    	<div class="update_item_name">总分</div>
		    	<div class="update_item_input">
			    	<input type='radio' name='total' id='totalYes' checked value='1'/><label for='totalYes'>是</label>&nbsp;
			    	<input type='radio' name='total' id='totalNo' value='0'/><label for='totalNo'>否</label>
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
		   	 <div class="update_message_items">
		    	<div class="update_item_name">年级名次</div>
		    	<div class="update_item_input">
			    	<input type='radio' name='rank' id='rankYes' checked value='1'/><label for='rankYes'>是</label>&nbsp;
			    	<input type='radio' name='rank' id='rankNo' value='0'/><label for='rankNo'>否</label>
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
		   	 <div class="update_message_items">
		    	<div class="update_item_name">班级名次</div>
		    	<div class="update_item_input">
		    		<input type='radio' name='description' id='descriptionYes' checked  value='1'/><label for='descriptionYes'>是</label>&nbsp;
					<input type='radio' name='description' id='descriptionNo'  value='0'/><label for='descriptionNo'>否</label>
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
		   	 <div class="update_message_items">
		    	<div class="update_item_name">平均分</div>
		    	<div class="update_item_input">
		    		<input type='radio' name='avgs' id='avgsYes' checked value='1'/><label for='avgsYes'>是</label>&nbsp;
					<input type='radio' name='avgs' id='avgsNo' value='0'/><label for='avgsNo'>否</label>
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
		   	 <div class="update_message_items">
		    	<div class="update_item_name">零分成绩</div>
		    	<div class="update_item_input">
		    		<input type='radio' name='zero' id='zeroYes' checked value='1'/><label for='zeroYes'>是</label>&nbsp;
					<input type='radio' name='zero' id='zeroNo' value='0'/><label for='zeroNo'>否</label>【说明：选择是，则会发送分数为零的成绩】
		    	</div>
		    	<div class="update_item_remarks">&nbsp;</div>
		   	 </div>
	   	 </c:if>
	   	 <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">
		    	<input type="checkbox" name="isMessage" id="isMessage" value="1"/><label for="isMessage">发送短信</label>&nbsp;
		    <!-- 
		     	<input type="checkbox" name="isLeaveMessage" value="1" id="isLeaveMessage"/><label for="isLeaveMessage">私人留言</label>
		     -->
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
	   	 <div class="update_message_items">
	    	<div class="update_item_name">定时发送</div>
	    	<div class="update_item_input">
		    	<input type="text" name="st" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> 默认为及时发送 
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
	   	 <div class="update_message_items">
	    	<div class="update_item_name">&nbsp;</div>
	    	<div class="update_item_input">
		    	<input type="button" onclick="sub(1)" value="发送" />
		    	<input type="button" onclick="sub(0)" value="保存" />
		    	<input type="button" onclick="to('message_inbox.html')" value="取消" />
	    	</div>
	    	<div class="update_item_remarks">&nbsp;</div>
	   	 </div>
   	</form>
</div>
<div id="msg" class="messageBox">
	<div class="message" id="message"></div>
	<div class="message-button" id="message-button"></div>
</div>