<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	out.println("<body>");
	out.println("<div id=\"wrap\" >");
%>
<link href="/admin/manager/css/styles.css" type="text/css"
	rel="stylesheet" />
<link href="/admin/manager/css/inc_commons.css" type="text/css"
	rel="stylesheet" />
<script src="/admin/manager/js/prototype.lite.js"
	type="text/javascript"></script>
<script src="/admin/manager/js/moo.fx.js" type="text/javascript"></script>
<script src="/admin/manager/js/moo.fx.pack.js" type="text/javascript"></script>
<script type="text/javascript">
	<!--
		var st="${style}";
		var s=st.split(",");
		var script="${script}";
		var scr=script.split(",");
		for(var i=0;i<s.length;i++){
			document.write('<l'+'ink rel="st'+'yl'+'es'+'he'+'et" type="te'+'xt/cs'+'s" href="/admin/manager/'+s[i]+'.c'+'ss" />');	
		}
		for(var j=0;j<scr.length;j++){
			document.write('<sc'+'ri'+'pt type="tex'+'t/ja'+'vas'+'cr'+'ipt" sr'+'c="/admin/manager/'+scr[j]+'.j'+'s" ><\/sc'+'ri'+'pt>');
		}
	//-->
	
</script>
<script type="text/javascript">
<!--
//function mysubmit(ids,url){
//			var myform=document.getElementById(ids);
//			myform.submit();
//		}
//-->
</script>
