<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="left_all">
<link href="/admin/manager/css/styles.css" type="text/css" rel="stylesheet"/>
<link href="/admin/manager/css/menu.css" type="text/css" rel="stylesheet"/>
<script src="/admin/manager/js/jquery.js" type="text/javascript"></script>
<script src="/admin/manager/js/jquery.easing.1.3.js" type="text/javascript"></script>
<script src="/admin/manager/js/menu.js" type="text/javascript"></script>

<div id="main">
  <ul class="container">
     <c:forEach items="${_module_list_session_}" var="mds">
    	<c:if test="${mds.root==0}">
	      <li class="menu">
	          <ul>
			    <li class="button"><a href="#" class="type">${mds.name}<span></span></a></li>
	            <li class="dropdown">
	                <ul>
		                <c:forEach items="${_module_list_session_}" var="mde">
				    		<c:if test="${mde.root==mds.id}">
				    			<li><a href="/web/${mds.url}/${mde.url}" title="${mde.name }" target="mainFrame">${mde.name}</a></li>
				   			</c:if>
			    		</c:forEach>
	                </ul>
				</li>
	          </ul>
	      </li>
      	</c:if>
       </c:forEach>
  </ul>
<div class="clear"></div>
</div>
</div>