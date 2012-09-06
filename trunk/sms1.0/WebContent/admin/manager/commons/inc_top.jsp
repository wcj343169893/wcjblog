<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.snssly.sms.commons.Config"%><link href="/admin/manager/css/styles.css" type="text/css" rel="stylesheet"/>
<link href="/admin/manager/css/inc_commons.css" type="text/css" rel="stylesheet"/>
<div class="inc_commons_top_all">
<!-- 
	<div class="inc_commons_top_all_menu">
    	<ul>
		 <c:forEach items="${modules}" var="mds">
		    	<c:if test="${mds.root==0}">
		    	<li>
		    		<a href="javascript:void(0)">${mds.name}</a>
		    	</li>
		    	</c:if>
	    	</c:forEach>
    	</ul>
	</div>
 -->
 <c:set value="<%=request.getSession().getAttribute(Config.LOGIN_SESSION) %>" var="login_user"></c:set>
<fmt:formatDate value="${login_user.lastLoginTime}" var="llt" pattern="yyyy-MM-dd HH:mm:ss"/>
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
  <tr>
    <td width="61%" height="64">
   <div id="logo">
		<img src="/admin/manager/images/logo.png" width="40" height="40"  />
		<div class="font">师恩教育管理信息系统</div>
	</div>
   	</td>
    <td width="39%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
						<td width="74%" height="38" class="admin_txt">
							${login_user.rname }：[
							<b class="font3"><a href="/web/self/self_info.html" target="mainFrame">${login_user.nikeName}</a></b>] 您好,上次登录：${llt}
						</td>
						<td width="22%" class="admin_button">
							<a href="/web/adminuser_logout.html" onClick="logout();" target="_parent">
							<img src="/admin/manager/images/out.gif" alt="安全退出" width="46" height="20" border="0"></a>
						</td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td height="19" colspan="3">&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>
</div>
