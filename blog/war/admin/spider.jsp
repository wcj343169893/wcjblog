<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.common.Pages"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采集内容</title>
<script type="text/javascript" charset="utf-8" src="/js/jquery.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/jquery.tabs.css" />
	<link rel="stylesheet" type="text/css" href="/jscal/css/jscal2.css" />
    <link rel="stylesheet" type="text/css" href="/jscal/css/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="/jscal/css/steel/steel.css" />
</head>
<body>
<%
	int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
	Pages pages=new Pages();
	pages.setPageNo(p); 
%>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<script type="text/javascript" charset="utf-8" src="/js/jquery.move.js"></script>
	<script type="text/javascript" charset="utf-8" src="/js/jquery.tabs.js"></script>
 	<script src="/jscal/js/jscal2.js"></script>
    <script src="/js/spider.js"></script>
    <script src="/jscal/js/lang/en.js"></script>
	<div class="address">
			采集中心(<a href="javascript:void(0)" onclick="showDiv('container')">新建任务</a>)
	</div>
	<div class="main-title">
		所有任务(<%=pages.getRecTotal() %>)
	</div>
	<div class="tools">
		<span class="tools-left">
			<input type="button" value="删除" onclick="deletes('/spider')">
		</span>
		<span class="tools-right">
			共<%=pages.getRecTotal() %>&nbsp;&nbsp;  第<%=pages.getPageNo() %>/<%=pages.getPageTotal() %> 页&nbsp;&nbsp;
			<%if(p > 1){ %>
				<a href="/admin/blog_list.jsp?p=<%=p-1 %>">上一页</a>&nbsp;&nbsp;
			<%} %>
			<%if(p < pages.getPageTotal()){ %>
				<a href="/admin/blog_list.jsp?p=<%=p + 1 %>">下一页</a>&nbsp;&nbsp;
			<%} %>
		</span>
	</div>
	<div class="vito-content">	
		<table cellpadding="0" cellspacing="0">
			<tr>
				<th class="vito-content-check"><input type="checkbox" id="input_check_all" onclick="allCheckFlag(this)"/></th>
				<th width="300px">标题</th>
				<th width="300px">采集网站/地址</th>
				<th>文章分类</th>
				<th>定时时间</th>
				<th>采集总数/采集次数</th>
			</tr>
		</table>
	</div>
	<div id="container" class="spider">
		<div class="spider_title">新增采集任务 <a href="javascript:void(0)" onclick="closeDiv('container')" class="f_r">关闭</a></div>
		 <div class="tabs">
		    <ul>
		        <li><a href="#web_list"><span>列表</span></a></li>
		        <li><a href="#web_content"><span>内容</span></a></li>
		    </ul>
		<div id="web_list">
			<div class="container-title">
				<div class="container-title-header">网址：</div><input type="text" name="web_host" id="web_host" value="http://www.xiaodiao.com/"> 
				<select name="web_charSet" id="web_charSet">
					<option value="utf-8">utf-8</option>
					<option value="gbk">gbk</option>
					<option value="gb2312">gb2312</option>
					<option value="ISO8859-1">ISO8859-1</option>
				</select>
			</div>
			<div class="container-title">
				<div class="container-title-header">列表页地址：</div><input type="text"	name="web_list_url" id="web_list_url" value="http://www.xiaodiao.com/html/gndy/dyzz/index.html"> 
			</div>
			<div class="container-title">
				<div class="container-title-header">列表页开始位置：</div><textarea rows="5" cols="20" name="web_list_begin" id="web_list_begin"><div class="co_content8"></textarea>
			</div>
			<div class="container-title">
				<div class="container-title-header">列表页结束位置：</div><textarea rows="5" cols="20" name="web_list_end" id="web_list_end">align="center" bgcolor="#F4FAE2"</textarea>
				<input type="button" value="测试" onclick="test_list()">
			</div>
		</div>
		<div id="web_content">
			<div class="container-title">
				<div class="container-title-header">内容标题：</div><input type="text" name="web_content_title" id="web_content_title" value="<h1>(.*)</h1>">(正则) 
			</div>
			<div class="container-title">
				<div class="container-title-header">保留标签：</div>
				<input type="checkbox" value="p,/p" id="tag_p" checked class="web_content_tags"><label for="tag_p">P</label>
				<input type="checkbox" value="span,/span" id="tag_span" checked class="web_content_tags"><label for="tag_span">SPAN</label>
				<input type="checkbox" value="img" id="tag_img" checked class="web_content_tags"><label for="tag_img">IMG</label>
				<input type="checkbox" value="div,/div" id="tag_div" class="web_content_tags"><label for="tag_div">DIV</label>
			</div>
			<div class="container-title">
				<div class="container-title-header">内容开始位置：</div><textarea rows="5" cols="20" name="web_content_begin" id="web_content_begin"><div id="Zoom"></textarea>
			</div>
			<div class="container-title">
				<div class="container-title-header">内容结束位置：</div><textarea rows="5" cols="20" name="web_content_end" id="web_content_end">安装软件后,点击即可下载,谢谢大家支持，欢迎每天来</textarea>
				<input type="button" value="测试" onclick="test_content()">
			</div>
			<div class="container-title">
				<div class="container-title-header">运行时间：</div><input type="text" name="spider_start" id="spider_start"><input type="button" id="spider_start_btn" value="..."/>
			</div>
			<div class="container-title">
				<div class="container-title-header">文章分类：</div><div id="typelist"></div> 
			</div>
			<div class="container-title">
				<input type="button" value="采集" onclick="begin()">
			</div>
		</div>
		</div>
	</div>
	<div id="test_result">
		<div class="result_title" ondblclick="closeDiv('test_result')" title="双击关闭" >测试结果 <a href="javascript:void(0)" onclick="closeDiv('test_result')" class="f_r">关闭</a></div>
		<div class="result_content"><div class='loading'><img src='/images/loading.gif'/></div></div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			tlist();
		 	$(".tabs").tabs();
			$("#container").draggable();
			$("#test_result").draggable();
			//spider_title //当按下鼠标左键，激活拖拽功能
			//result_title
			 Calendar.setup({
			        inputField : "spider_start",
			        trigger    : "spider_start_btn",
			        onSelect   : function() { this.hide() },
			        showTime   : 12,
			        dateFormat : "%Y-%m-%d %H:%M"
			      });
		});
	</script>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>