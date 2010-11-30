<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加博客</title>
	<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			发布文章 
	</div>
	<script type="text/javascript">
		KE.show({
			id : 'content',
			fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
			afterCreate : function(id) {
				KE.event.ctrl(document, 13, function() {
					KE.util.setData(id);
					document.forms['newblog'].submit();
				});
				KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
					KE.util.setData(id);
					document.forms['newblog'].submit();
				});
			}
		});
	</script>
	<div id="container">
		<form action="/blog" method="post" name="newblog">
			<input type="hidden" name="op" value="add">
			<div class="container-title">
				标题：
			</div>
			<input type="text"	name="title"> 
			<div class="container-content">
				内容：
			</div>
				<textarea rows="20" cols="100" name="content"></textarea>
			<div class="container-type">
				分类：
			</div>
			<input type="text"	name="tid" value="1" > 
			<div class="container-tags">
				关键字：
			</div>
			<input type="text" name="tag"><br>
			<input type="checkbox" value="0" id="isVisible" name="isVisible"><label for="isVisible">发布</label> &nbsp;<input type="submit" value="保存">
		</form>
	</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>