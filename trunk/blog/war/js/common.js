function showOrHideDiv(divId){
	var commentReplyDiv = document.getElementById(divId);
	commentReplyDiv.style.display = commentReplyDiv.style.display=="none"?"block":"none";
}
function to(url){
	window.location.href=url;
}
function allCheckFlag(inputObj){
	var inputObj = $(inputObj);
	var input_check_single = $(".input_check_single");
	if(inputObj.attr("checked")){
		$.each(input_check_single,function(n,value) {
			$(value).attr("checked",true);
	    });
	}else{
		$.each(input_check_single,function(n,value) {
			$(value).attr("checked",false);
	    });
	}
}

function singleDeleteFlag(inputObj){
	var inputObj = $(inputObj);
	var input_check_single = $("." + inputObj.attr("class"));
	var input_check_all = $("#input_check_all");
	var flag = true;
	$.each(input_check_single,function(n,value) {
		if(!$(value).attr("checked")) {
			flag = false;
		}
    });
	if(flag){
		input_check_all.attr("checked",true);
	}else{
		input_check_all.attr("checked",false);
	}
}

function deletes(url){
	var ids="";
	var inputs=document.getElementsByTagName("input");
	var flag=false;
	var e;
	for(var i=0;i<inputs.length;i++){
		e=inputs[i];
		if(e.type=="checkbox" && e.className=="input_check_single" && e.checked==true){
			ids+=e.value+",";
			flag=true;
		}
	}
	if(flag){
		if(url.indexOf("?") > 0){
			del(url+"&op=delete&ids="+(ids+"0"));
		}else{
			del(url+"?op=delete&ids="+(ids+"0"));
		}
	}else{
		alert("请选择删除项");
	}
}
function del(url){
	if(confirm("是否删除?")){
		to(url);
	}
}
