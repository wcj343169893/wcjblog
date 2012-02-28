function showOrHideDiv(divId) {
	var commentReplyDiv = document.getElementById(divId);
	commentReplyDiv.style.display = commentReplyDiv.style.display == "none" ? "block"
			: "none";
}
function closeDiv(id) {
	$("#" + id).hide();
}
function showDiv(id) {
	$("#" + id).show();
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
function del_m(id) {
	if (id != "") {
		$("#" + id).remove();
	} else {
		$(".d_menu").remove();
	}
}
function add_m() {
	var no = new Date().getTime();
	$("#td_menu")
			.append(
					"<div id='menu_"
							+ no
							+ "' class='d_menu'><input value='' class='menu_title'/> <input value='' class='menu_url'/> <input value='删除' type='button' onclick=del_m('menu_"
							+ no + "')> </div>");
}
function make_m() {
	var menus = "";
	$(".d_menu").each(function(index, domEle) {
		var title = $(domEle).children(".menu_title");
		var url = $(domEle).children(".menu_url");
		if (title.val() != "" && url.val() != "") {
			menus = menus + title.val() + "," + url.val() + ";";
		}
	});
	menus = menus.substring(0, menus.length - 1);
	$("#menus").val(menus);
}
function bd_sub() {
	var content = $("#content").val();
	if (content == null || "" == jQuery.trim(content)) {
		alert("请输入评论内容");
		return false;
	}
	return true;
}
// 动态加载评论
function initReply(p, bid) {
	loading_rc();
	jQuery.getJSON("/reply?p=" + p + "&bid=" + bid,function(data){
		var content="";
		var isContent=false;
		var count=0;
		data.each(function(domEle,index){
			isContent=true;
			count++;
			var louceng_str="";
			var louceng=(p-1)*10+index+1;
			if (louceng == 1) {louceng_str="沙发";} 
			else if (louceng == 2) {louceng_str="板凳";} 
			else if (louceng == 3) {louceng_str="平地";
			} else {louceng_str="第" + louceng+ "楼";}
			content+='<div class="vito-postcommentlist"><span class="vito-postcomment-one"><span class="vito-postcomment-name" style="color: #8c8c8c">'+louceng_str+'|<a href="">'+domEle.name+'</a><span style="color: #979797">'+domEle.sdTime+'说</span></span><br><br>';
			content+='<span class="vito-postcomment-content">'+domEle.content;
			
			if(domEle.replyMessage!=null &&!""==domEle.replyMessage){
				content+='<blockquote><div class="quote quote3"><div class="quote-title">管理员 于 '+domEle.replyTime+'回复</div>'+domEle.replyMessage+'</div></blockquote>';
			}
			content+='</span></span><span class="vito-postcomment-reback"> </span></div>';
	  });
		if(isContent){
			content+="<br>";
			content+="<div class='vito-prenext'>";
			if (p>1) {
				content+="<a href='javascript:initReply("+(p-1)+","+bid+")'>上一页</a>";
			}
			if(count>=10){
				content+="<a href='javascript:initReply("+(p+1)+","+bid+")'>下一页</a>";
			}
			content+="</div>";
		}else{
			content="<div align='center'>还没有人发表评论</div>";
		}
		jQuery("#reply_comment").html(content);
	});
}
function loading_rc(){
	jQuery("#reply_comment").html("<div align='center'><img src='/images/loading.gif'/></div>");
}