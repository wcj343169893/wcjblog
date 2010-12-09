<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="left_all">
<link href="/admin2/manager/css/styles.css" type="text/css" rel="stylesheet"/>
	<link href="/admin2/manager/css/inc_commons.css" type="text/css" rel="stylesheet"/>
		<script src="/admin2/manager/js/prototype.lite.js" type="text/javascript"></script>
	<script src="/admin2/manager/js/moo.fx.js" type="text/javascript"></script>
	<script src="/admin2/manager/js/moo.fx.pack.js" type="text/javascript"></script>
<table width="100%" height="280" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB">
  <tr>
    <td width="182" valign="top">
    <div id="container">
    <c:forEach items="${modules}" var="mds">
    	<c:if test="${mds.root==0}"><h1 class="type"><a href="javascript:void(0)">${mds.title}</a></h1><div class="content"><ul class="MM">
    	<c:forEach items="${modules}" var="mde"><c:if test="${mde.root==mds.mid}"><li><a href="/admin2/jsp/html/${mds.path}/${mde.path}" title="${mde.title }" target="mainFrame">${mde.title}</a></li></c:if>
    	</c:forEach></ul></div></c:if></c:forEach>
        <script type="text/javascript">
		var contents = document.getElementsByClassName('content');
		var toggles = document.getElementsByClassName('type');
	
		var myAccordion = new fx.Accordion(
			toggles, contents, {opacity: true, duration: 300}
		);
		myAccordion.showThisHideOpen(contents[2]);
	</script>
        </td>
  </tr>
</table>
</div>