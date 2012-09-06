<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="left_all">
  <c:forEach items="${modules}" var="mds">
    	<c:if test="${mds.root==0}"><h1 class="type"><a href="javascript:void(0)">${mds.name}</a></h1><div class="content">
    	<ul>
	    	<c:forEach items="${modules}" var="mde">
	    		<c:if test="${mde.root==mds.id}">
	    			<li><a href="/web/html/${mds.url}/${mde.url}" title="${mde.name }" target="mainFrame">${mde.name}</a></li>
	   			</c:if>
	    	</c:forEach>
    	</ul></div>
    	</c:if>
    </c:forEach>
</div>