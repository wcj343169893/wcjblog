<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONSerializer"%>
<%@page import="net.sf.json.JSON"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录成功</title>
<style type="text/css">
	.userinfo{
		text-align: center;
	}
</style>
</head>
<body>
<%
try{
	String result=(String)request.getAttribute("result");
	int n_index=result.indexOf("nickname");
	int p_index=result.indexOf("figureurl_1");
	String nikeName=result.substring(n_index+"nickname\":\"".length(),result.indexOf(",",n_index)-1);
	String figureurl=result.substring(p_index+"figureurl_1\":\"".length(),result.indexOf(",",p_index));
%>

<div class="userinfo">
<div class="info">欢迎登录:  <img src="<%=figureurl %>" alt="<%=nikeName %>"/><strong><%=nikeName %></strong>&nbsp;</div>
</div>
<%}catch(Exception e){out.print("登录失败");} %>
</body>
</html>