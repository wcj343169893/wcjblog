<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站关闭提示</title>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery.corner.js"></script>
<style type="text/css">
	#error_notice{
		width: 400px;
		height: 300px;
		border: 2px solid #ccc;
		background: #ccc;
		margin: 0 auto;
		margin-top: 100px;
		opacity: 0.8;
		filter: alpha(opacity=80);
		padding: 20px;
	}
	.notice{
		margin-top: 100px;
		background: url("/images/warn.gif") no-repeat;
		padding-left: 50px;
		height: 50px;
		line-height: 50px;
	}
	a{
		text-decoration: none;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#error_notice").corner();
	});
</script>
</head>
<body>
	<div id="error_notice">
		<div class="notice">
			对不起，您访问的网站暂时关闭
		</div>
	</div>
</body>
</html>