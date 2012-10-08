function showOrHideDiv(divId) {
	var commentReplyDiv = document.getElementById(divId);
	commentReplyDiv.style.display = commentReplyDiv.style.display == "none" ? "block"
			: "none";
}
//显示留言区域
function showForm(){
	var form=jQuery("#frmSumbit");
	form.attr("action","/reply");
	var goolge_email=jQuery("#google_account_email"), goolge_nickname=jQuery("#google_account_nickname"),goolge_authDomain=jQuery("#google_account_authDomain");
	if (goolge_nickname.val()) {
		form.find("#comment_name").val(goolge_nickname.val());
	}
	form.find("#inpEmail").val(goolge_email.val());
	form.find("#inpHomePage").val(goolge_authDomain.val());
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
function tops(url) {
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
			to(url + "&ids=" + (ids + "0"));
		} else {
			to(url + "?ids=" + (ids + "0"));
		}
	} else {
		alert("请选择操作项");
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
/**动态加载Google登陆信息**/
function initGoogleAccount(){
	var top_member = jQuery("#top_member");
	var url = window.location.href;
	top_member.html("<img src='/images/loading.gif' height='20'/>");
	jQuery.getJSON("/user?op=getUser&url="+url,function(data){
		jQuery(data).each(function(index,domEle){
//			alert("data"+domEle+" "+ index);
			if(domEle.isUserLoggedIn){
				var content="欢迎";
				if(domEle.isUserAdmin){content+="管理员";}
				content+=domEle.nickname;
				content+=" <a href='"+domEle.logoutUrl+"'>退出</a>";
				content+='<input id="google_account_email" value="'+domEle.email+'" type="hidden"/> <input id="google_account_nickname" value="'+domEle.nickname+'" type="hidden"/>  <input id="google_account_authDomain" value="'+domEle.authDomain+'" type="hidden"/>';
				top_member.html(content);
			}else{
				top_member.html('<a href="'+domEle.loginUrl+'" >Google登陆</a>');
			}
		});
	});
}
function getGoogleAccount(){
	var top_member = jQuery("#comment_account");
	var url = window.location.href;
	top_member.html("<img src='/images/loading.gif' height='20'/>");
	jQuery.getJSON("/user?op=getUser&url="+url,function(data){
		jQuery(data).each(function(index,domEle){
			if(domEle.isUserLoggedIn){
				var content="欢迎";
				if(domEle.isUserAdmin){content+="管理员 ";}
				content+="【"+domEle.nickname+"】";
				content+=" <a href='"+domEle.logoutUrl+"'>退出</a>";
				//content+='<input name="email" value="'+domEle.email+'" type="hidden"/> <input name="name" value="'+domEle.nickname+'" type="hidden"/>  <input name="url" value="'+domEle.authDomain+'" type="hidden"/>';
				top_member.html(content);
				jQuery("#msg_notice").remove();
			}else{
				top_member.html('<a href="'+domEle.loginUrl+'" >Google登陆</a>');
			}
		});
	});
}
/**动态加载评论**/
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
/**动态加载评论，，，格式2**/
function initReply2(p, bid) {
	loading_rc2();
	jQuery.getJSON("/reply?p=" + p + "&bid=" + bid,function(data){
		var content="";
		var isContent=false;
		var count=0;
		jQuery.each(data,function(index,domEle){
			isContent=true;
			count++;
			var url="javascript:;";
			if((domEle.url).indexOf("http") != -1){
				url=domEle.url;
			}
			content+='<div class="cmt-item" style="height: auto; overflow: visible; "><div class="cmt-item-content clearfix"><div class="cmt-content-box"><div class="cmt-content-detail"><a class="cmt-user-name cs-contentblock-link" href="'+url+'" target="_blank">'+domEle.name+'</a> <br/><p>'+domEle.content+'</p></div><div class="cmt-pub-time">'+domEle.sdTime+'</div>';
			if(domEle.replyMessage!=null &&domEle.replyMessage.length>0){
				content+='<blockquote><div class="quote quote3"><div class="quote-title">管理员 于 '+domEle.replyTime+'回复</div>'+domEle.replyMessage+'</div></blockquote>';
			}
			content+='</div></div><div class="cmt-mini-border"></div></div>';
	  });
		if(isContent){
			jQuery(".cmt-seemore .seemore-tip").html("查看更多");
			jQuery(".cmt-seemore").attr("href","javascript:initReply2("+(p+1)+","+bid+")");
		}else{
			if(p>1){
				content="<div align='center' id='comment-notice'>就这些啦</div>";
				jQuery(".cmt-seemore .seemore-tip").html("收起");
				jQuery(".cmt-seemore").toggle(
					  function () {
						  	jQuery("#cmt-list").slideUp("slow");
						  	jQuery(".cmt-seemore .seemore-tip").html("展开");
						  },
						  function () {
						    jQuery("#cmt-list").slideDown("slow");
						    jQuery(".cmt-seemore .seemore-tip").html("收起");
						  }
						);
				
			}else{
				jQuery("#cmt-list").empty();
				content="<div align='center' id='comment-notice'>还没有人发表评论</div>";
			}
		}
		jQuery("#cmt-list").append(content);
		hide_loading();
	});
}
function loading_rc(){
	jQuery("#reply_comment").html("<div align='center'><img src='/images/loading.gif'/></div>");
}
/**显示loading**/
function loading_rc2(text){
	var opts={
			loadingId:"infscr-loading",
			loadingImg:"/images/loading.gif",
			loadingText:"正在加载..."
	};
	var loading = jQuery("#"+opts.loadingId);
	if(loading.length==0){
		loading = jQuery('<div id="'+opts.loadingId+'" style="text-align: center;"><img alt="Loading..." src="'+
          opts.loadingImg+'" /><div>'+(text?text:opts.loadingText)+'</div></div>');    
		jQuery("body").append(loading);
	}
	loading.fadeIn("slow");
}
/**隐藏loading**/
function hide_loading(){
	$("#infscr-loading").fadeOut("slow");
}
function checkWord(){
	if($("#content").val().length >= 500){
		$("#content_notice").html("输入长度超过500字符,请删除!");
		return false;
	}else{
		$("#content_notice").html(" ");
		return true;
	}
}