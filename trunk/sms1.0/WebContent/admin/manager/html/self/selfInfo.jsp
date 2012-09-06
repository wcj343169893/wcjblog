<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Random"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.snssly.sms.commons.Env"%>
<%@page import="com.snssly.sms.entity.User"%>
<%@page import="java.util.Date"%><c:set scope="request" var="style" value="css/perm_module,css/notice_update,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery,DatePicker/WdatePicker" />
<c:set scope="request" var="meta_title" value="我的详细信息"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">我的详细信息</h1>
    		<br/>
    		<div class="search_key">如果首次登录，请及时修改个人信息！</div>
    		<input type="button" value="修改我的信息" onclick="to('self_updateInit.html')">
    	</div>
    	<div class="tabs_150_context_right"></div>
   	</div>
   	 <div class="update_items">
    	<div class="update_item_name">&nbsp;</div>
    	<div class="update_item_input">&nbsp;</div>
    	<div class="update_item_remarks">&nbsp;</div>
    </div>
	<div class="update_items">
    	<div class="update_item_name">姓名：</div>
    	<div class="update_item_input">${user.nikeName}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">性别：</div>
    	<div class="update_item_input">${empty user.sex || user.sex == 0 ?"男":"女"}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <c:if test="${user.rid!=1 && user.rid!=4 && user.rid!=5}">
   	 <div class="update_items">
    	<div class="update_item_name">所在班级：</div>
    	<div class="update_item_input">${user.gdname}${user.cname}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
   	 <c:if test="${user.rid==3}">
   	 <div class="update_items">
    	<div class="update_item_name">学号：</div>
    	<div class="update_item_input">${user.snumber}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
   	 <div class="update_items">
    	<div class="update_item_name">角色&nbsp;&nbsp;分组：</div>
    	<div class="update_item_input">${user.rname}&nbsp;&nbsp;${user.gname }</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">生日：</div>
    	<div class="update_item_input">${user.brithday}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">身份证编号：</div>
    	<div class="update_item_input">${user.sid}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">籍贯：</div>
    	<div class="update_item_input">${user.birthplace}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">手机号码：</div>
    	<div class="update_item_input">${user.mobile}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <div class="update_items">
    	<div class="update_item_name">注册时间：</div>
    	<div class="update_item_input">${user.registTime}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 <c:if test="${user.rid==3 || user.rid==4}">
   	 <div class="update_items">
    	<div class="update_item_name">会员时间：</div>
    	<div class="update_item_input">
    		<c:choose>
    			<c:when test="${empty user.memberEndTime}">
    			永久会员
    			</c:when>
    			<c:otherwise>
    				<%	User user=(User)request.getAttribute("user");
    					boolean flag=Env.compareDate(user.getMemberEndTime(),new Date()); %>
    					<c:set value="<%=flag %>" var="memberEnd"></c:set>
    				<span class="${memberEnd ?"font3":""}"><a href="pay_init.html">${user.memberEndTime }</a></span>  &nbsp; ${memberEnd ?"已到期 ":"" }<a href="pay_init.html">续期</a>
    			</c:otherwise>
    		</c:choose>
    	</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
   	 </c:if>
   	  <div class='update_items'>
    	<div class="update_item_name">最后登录：</div>
    	<div class='update_item_input'>${user.lastLoginTime}</div>
    	<div class="update_item_remarks">&nbsp;</div>
   	 </div>
</div>