<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>您访问的页面出错啦</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="/admin/manager/css/styles.css">
	<script type="text/javascript" src="/admin/manager/js/common.js"></script>
	<script type="text/javascript">
		var mi=10;
		function countdown(){
			if(mi>=0){
				document.getElementById("countdownNotice").innerHTML=mi+"秒后，<a href=\"/\" target=\"_parent\">返回主页</a>";
				if(mi==0){
					top.location.href = "/";
				}
				mi--;
			}
			setTimeout("countdown()",1000);
		}
	</script>
  </head>
  <body onload="countdown()">
   	<div class="error-div">
   		<a href="/" target="_parent"><img alt="您访问的页面出错啦" src="/admin/manager/images/500error.jpg"></a>
   		<div id="countdownNotice">10秒后，<a href="/" target="_parent">返回主页</a></div>
   	</div>
  </body>
</html>