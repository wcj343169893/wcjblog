<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="inc_commons_top_all">
<link href="/admin2/manager/css/styles.css" type="text/css" rel="stylesheet"/>
	<link href="/admin2/manager/css/inc_commons.css" type="text/css" rel="stylesheet"/>
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
  <tr>
    <td width="61%" height="64"><img src="/admin2/manager/images/logo2.png" width="262" height="64"></td>
    <td width="39%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
						<td width="74%" height="38" class="admin_txt">
							管理员：[
							<b class="font3">${admin_info.user}</b>] 您好,上次登录：${admin_info.setTime}
						</td>
						<td width="22%"><a href="/admin2/jsp/adminuser_logout.html" onClick="logout();" target="parent"><img src="/admin2/manager/images/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td height="19" colspan="3">&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>
</div>
