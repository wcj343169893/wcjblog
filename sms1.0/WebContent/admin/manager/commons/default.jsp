<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="incc" value="1" scope="request"></c:set>
<c:if test="${requestScope.inc==null}">
	<jsp:forward page="/admin2/manager/commons/error404.jsp"></jsp:forward>
</c:if>
<head>
	<title>师恩教育管理信息系统</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<link href="/admin/manager/css/styles.css" type="text/css" rel="stylesheet"/>
	<link href="/admin/manager/css/inc_commons.css" type="text/css" rel="stylesheet"/>
	<link rel="shortcut icon" href="/favorite.ico" />
</head>
<c:if test='${inc=="/admin/manager/html/index.jsp"}'>
<frameset rows="80,*" cols="*" frameborder="no" border="0" framespacing="0" >
  <frame src="/admin/manager/commons/inc_top.jsp" name="topFrame" scrolling="no" id="topFrame" title="topFrame" />
  <frameset cols="213,*" frameborder="no" border="0" framespacing="0">
    <frame src="/admin/manager/commons/inc_left.jsp" name="leftFrame" scrolling="no" id="leftFrame" title="leftFrame" />
    <frame src="${requestScope.inc}" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
</frameset>
</c:if>
<c:if test='${inc!="/admin/manager/html/index.jsp"}'>
	<jsp:include flush="true" page="${requestScope.inc}"></jsp:include>
	<jsp:include flush="true" page="/admin/manager/commons/inc_foot.jsp"></jsp:include>
</c:if>
</html>
