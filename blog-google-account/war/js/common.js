function showOrHideDiv(divId) {
	var commentReplyDiv = document.getElementById(divId);
	commentReplyDiv.style.display = commentReplyDiv.style.display == "none" ? "block"
			: "none";
}
function closeDiv(id){
	$("#"+id).hide();
}
function showDiv(id){
	$("#"+id).show();
}
function to(url) {
	window.location.href = url;
}
function clearCache(url) {
	if (url.indexOf("?") > 0) {
		to(url + "&op=clearCache");
	} else {
		to(url + "?op=clearCache");
	}
}
function allCheckFlag(inputObj) {
	var inputObj = $(inputObj);
	var input_check_single = $(".input_check_single");
	if (inputObj.attr("checked")) {
		$.each(input_check_single, function(n, value) {
			$(value).attr("checked", true);
		});
	} else {
		$.each(input_check_single, function(n, value) {
			$(value).attr("checked", false);
		});
	}
}

function singleDeleteFlag(inputObj) {
	var inputObj = $(inputObj);
	var input_check_single = $("." + inputObj.attr("class"));
	var input_check_all = $("#input_check_all");
	var flag = true;
	$.each(input_check_single, function(n, value) {
		if (!$(value).attr("checked")) {
			flag = false;
		}
	});
	if (flag) {
		input_check_all.attr("checked", true);
	} else {
		input_check_all.attr("checked", false);
	}
}

function deletes(url) {
	var ids = "";
	var inputs = document.getElementsByTagName("input");
	var flag = false;
	var e;
	for ( var i = 0; i < inputs.length; i++) {
		e = inputs[i];
		if (e.type == "checkbox" && e.className == "input_check_single"
				&& e.checked == true) {
			ids += e.value + ",";
			flag = true;
		}
	}
	if (flag) {
		if (url.indexOf("?") > 0) {
			del(url + "&op=delete&ids=" + (ids + "0"));
		} else {
			del(url + "?op=delete&ids=" + (ids + "0"));
		}
	} else {
		alert("请选择删除项");
	}
}
function del(url) {
	if (confirm("是否删除?")) {
		to(url);
	}
}
function del_m(id){
	if(id!=""){
		$("#"+id).remove();
	}else{
		$(".d_menu").remove();
	}
}
function add_m(){
	var no=new Date().getTime();
	$("#td_menu").append("<div id='menu_"+no+"' class='d_menu'><input value='' class='menu_title'/> <input value='' class='menu_url'/> <input value='删除' type='button' onclick=del_m('menu_"+no+"')> </div>");
}
function make_m(){
	var menus="";
	$(".d_menu").each(function(index, domEle){
		var title=$(domEle).children(".menu_title");
		var url=$(domEle).children(".menu_url");
		if(title.val()!="" && url.val()!=""){
			menus=menus+title.val()+","+url.val()+";";
		}
	});
	menus=menus.substring(0,menus.length-1);
	$("#menus").val(menus);
}
function bd_sub(){
	var content=$("#content").val();
	if(content==null || ""==jQuery.trim(content)){
		alert("请输入评论内容");
		return false;
	}
	return true;
}