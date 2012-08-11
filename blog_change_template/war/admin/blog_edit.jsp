<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.google.choujone.blog.entity.Blog"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改文章</title>
<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/jquery.js"></script>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<div class="address">
			修改文章 
	</div>
	<% Blog blog=(Blog)request.getAttribute("blog");
	if(blog!=null){ %>
		<script type="text/javascript">
			KE.show({
				id : 'content',
				imageUploadJson : "/kindeditor/jsp/upload.jsp",
				fileManagerJson : '/kindeditor/jsp/file_manager.jsp',
				allowFileManager : true,
				afterCreate : function(id) {
					KE.event.ctrl(document, 13, function() {
						KE.util.setData(id);
						document.forms['modifyblog'].submit();
					});
					KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
						KE.util.setData(id);
						document.forms['modifyblog'].submit();
					});
				}
			});
		</script>
		<div id="container">
			<form action="/blog" method="post" name="modifyblog">
				<input type="hidden" name="op" value="modify">
				<input type="hidden" name="id" value="<%=blog.getId() %>">
				<div class="container-title">
					标题：
				</div>
				<input type="text"	name="title" value="<%=blog.getTitle() %>"> 
				<div class="container-content">
					内容：
				</div>
					<textarea rows="20" cols="100" name="content"><%=blog.getContent().getValue() %></textarea>
				<div class="container-type">
					分类：<input type="hidden" value="<%=blog.getTid()  %>" id="t">
					<span id="addType" style="display: none;">
					<strong>新增：</strong>&nbsp;分类名:<input type="text" id="tname" value="" > 
						简 &nbsp;&nbsp;介:<input type="text" id="info" value="" >
						<input type="button" onclick="tadd()" value="保存">
					</span>
				<span id="modifyType" style="display: none;">
				</span>
				<span id="result"></span>
				</div>
				<div id="typelist">
				</div>
				<div class="container-tags">
					关键字：
				</div>
				<input type="text" name="tag" value="<%=blog.getTag() %>"><br>
				<input type="checkbox" value="0" id="isVisible" name="isVisible" <%=blog.getIsVisible() == 0 ? "checked":"" %>><label for="isVisible">发布</label> &nbsp;<input type="submit" value="保存">
			</form>
		</div>
		<script type="text/javascript">
				function tlist(){
					$.post("/blogType", {"t":$("#t").val(),"opera":"lists" },function(data){
							$("#typelist").html(data);
						});
					}
				function tadd(){
					if($("#tname").val() != ""){
						$.get("/blogType", {"tname":$("#tname").val(),"info":$("#info").val(),"opera":"add"},function(data){
								if(data){
									$("#result").html("新增成功");
									$("#tname").val("");
									$("#info").val("")
								}else{
									$("#result").html("新增失败");
								}
								tlist();
							});
						}else{
							$("#result").html("请输入分类名");
							}
						$("#tname").focus();
					}
				function modifyType(modifyId,tids){
					var modifyDiv = document.getElementById(modifyId);
					modifyDiv.style.display = modifyDiv.style.display=="none"?"block":"none";
					if(modifyDiv.style.display == "block"){
						var ts = document.getElementById(tids);
						$.post("/blogType", {"t":ts.value},function(data){
							$("#modifyType").html(data);
						});
					}
				}
				function tmodify(){
					if($("#modifytname").val() != ""){
						$.get("/blogType", {"t":$("#modifytid").val(),"info":$("#modifyinfo").val(),"tname":$("#modifytname").val(),"opera":"modify"},function(data){
								if(data){
									$("#result").html("修改成功");
								}else{
									$("#result").html("修改失败");
								}
								showOrHideDiv("modifyType");
								tlist();
							});
						}else{
							$("#result").html("请输入分类名");
							}
						$("#tname").focus();
					}
				function deleteType(){
					$.get("/blogType", {"t":$("#tids").val()},function(data){
						if(data){
							$("#result").html("删除成功");
							document.getElementById("addType").style.display="none";
							document.getElementById("modifyType").style.display="none";
						}else{
							$("#result").html("删除失败");
						}
						tlist();
					});
					}
				tlist();
			</script>	
	<%} %>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>