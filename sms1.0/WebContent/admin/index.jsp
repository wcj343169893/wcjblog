<%@ page language="java"  pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>师恩教育管理信息系统</title>
<link href="/admin/manager/css/login.css" rel="stylesheet" type="text/css" />
	<meta name="google-site-verification" content="xoBRnJeiPkFaiSVBakju99kmb4h0Xf8dXZ5C2Tw1B0s" />
	<meta name="description" content="师恩教育管理信息系统,家校通,方便家长和教师沟通,让家长更放心自己的子女" />
	<meta name="keywords" content="师恩教育管理信息系统,家校通,中国家校通,重庆家校通" />
<script type="text/javascript">
	function reloadVerifyCode(){  
		var obj=document.getElementById("login_img");
	    obj.src = "<%=request.getContextPath()%>/servlet/ImageServlet?d="+new Date();
	} 
	</script>
	<link rel="shortcut icon" href="/favorite.ico" /> 
<base target="_parent"/>
<script type="text/javascript">
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-19500302-1']);
	  _gaq.push(['_trackPageview']);
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	</script>
	<meta name="alexaVerifyID" content="wYHvAzFWU-0LaO_Y4Kd1SRobBVI" />
</head>
<body onload="document.myform.name.focus();">
<div id="bg">
	<div id="bgtop">
	<div id="logo">
	<img src="/admin/manager/images/logo.png" width="40" height="40"  />
	<div class="font">师恩教育管理信息系统</div>
	</div>
		<div id="bgbtm">
				<div id="content">
					<div id="left"> 
					   <ol>
						<li><p>完善的家校沟通系统</p></li>
						<li><p>强大的考试管理系统</p></li>
					  </ol>
					  <div id="welcome"></div>
					</div>
					
					<div id="line"></div>
				  <div id="right">
				  <form id="myform" name="myform" method="post" action="/web/adminuser_login.html">
				    <table width="270" border="0" class="list">
				    	<tr>
				    		<td colspan="2"><span class="login_txt_bt">${login_error_message }</span></td>
				    	</tr>
                      <tr>
                        <td width="48">登录名：</td>
                        <td width="150">
                            <input type="text" name="name" size="20" value="${login_name }"/>
                       </td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td>密码：</td>
                        <td>
                            <input type="password" name="password" />
                     	</td>
                        <td><img src="/admin/manager/images/luck.gif"/></td>
                      </tr>
                      <tr>
                        <td>验证码：</td>
                        <td colspan="2">
                        		<input class=wenbenkuang name=verifycode type=text value="" maxlength=4 size=4 />
                               <img alt="看不清楚，换一张" title="看不清楚，换一张" src="/servlet/ImageServlet" onclick="reloadVerifyCode();" style="cursor: pointer;" id="login_img"/>
                               <a href="javascript:void(0)" onclick="reloadVerifyCode();">看不清楚?</a>
                        </td>
                      </tr>
                    </table>
				    <table class="button">
					  <tr>
					  <td>
                          <input type="submit"  value="登录" />
					  </td>
					    <td>
                          <input type="button"  value="取消" />
					  </td>
					  </tr>
				    </table>
				      <input type="hidden" name="formName" value="entity.Adminuser" />
				    </form>
                  </div>
					<div style="clear: both;"></div>
				 <div id="btm">Copyright &copy;2010 www.snssly.com</div>
		  </div>
		</div>
  </div>
</div>
</body>
</html>