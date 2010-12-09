<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="incc" value="1" scope="request"></c:set>
<c:if test="${requestScope.inc==null}">
	<jsp:forward page="/web/common/error404.jsp"></jsp:forward>
</c:if>
<head>
	<title>系统${requestScope.inc}</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
</head>
<c:if test='${inc=="/web/commons/default.jsp"}'>
<frameset rows="59,*" cols="*" frameborder="no" border="0" framespacing="0" ><br />
  <frame src="/web/common/inc_top.jsp" name="topFrame" scrolling="no" id="topFrame" title="topFrame" />
  <frameset cols="213,*" frameborder="no" border="0" framespacing="0">
    <frame src="/web/common/inc_left.jsp" name="leftFrame" scrolling="no" id="leftFrame" title="leftFrame" />
    <frame src="${requestScope.inc}" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
</frameset>
</c:if>
<c:if test='${inc!="/web/commons/default.jsp"}'>
	<jsp:include flush="true" page="${requestScope.inc}"></jsp:include>
	<jsp:include flush="true" page="/common/footer.jsp"></jsp:include>
</c:if>
</html>
