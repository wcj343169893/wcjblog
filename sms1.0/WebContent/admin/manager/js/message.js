var isSend=false;
function mysub(ob){
	// 判断是否选择联系人,并提取联系人
	var linkmans="";
	var message_linkman=document.getElementById("message_linkman");
	var childNotes = message_linkman.childNodes;
	var isHaveLinkMan=false;
	var count=1;
	var length=childNotes.length;
	for ( var i = 0; i < length; i++) {
		var cn = childNotes[i];
		if(parseInt(cn.id)){
			linkmans+=cn.id+",";
			isHaveLinkMan=true;
		}
	}
	if(!isHaveLinkMan){
		$("#message").html("请选择联系人");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg')\"/>");
		$("#msg").fadeIn("slow");
		isSend=false;
		setTimeout("hiddeDiv(\"msg\")",5000);
	}else if($("#title").val()==""){
		$("#message").html("主题为空，是否继续发送?");
		$("#message-button").html("<input value=\"继续\" type=\"button\" onclick=\"sub()\"/><input value=\"取消\" type=\"button\" onclick=\"hiddeDiv('msg')\"/>");
		$("#msg").fadeIn("slow");
		isSend=false;
	}else if($("#content").val()==""){
		$("#message").html("内容为空，是否继续发送?");
		$("#message-button").html("<input value=\"继续\" type=\"button\" onclick=\"sub()\"/><input value=\"取消\" type=\"button\" onclick=\"hiddeDiv('msg')\"/>");
		$("#msg").fadeIn("slow");
		isSend=false;
	}else if($("#content").val()!="" && $("#content").val().length>58 && $("#tid").val()!=3){
		$("#message").html("内容已经超过一条信息<br/>是否继续发送?");
		$("#message-button").html("<input value=\"继续\" type=\"button\" onclick=\"sub()\"/><input value=\"取消\" type=\"button\" onclick=\"hiddeDiv('msg')\"/>");
		$("#msg").fadeIn("slow");
		isSend=false;
	}else{
		$("#userIds").val(linkmans.substring(0, linkmans.length-1));
		isSend=true;
	}
	return isSend;
	// 判断主题，内容是否为空
}
function checkLength(){
	var length=$("#content").val().length;
	var te="请输入信息内容";
	if(length>0){
		te="友情提示:58个字作为一条短信发送,已输入"+length+"个字，将以"+(Math.floor((length-1)/58)+1)+" 条短信发送";
	}
	$("#contentLength").html(te);
}
function mysubQuickReply(ob,notice){
	if($("#txtQuickReplyContentread_2").val()!=null 
			&& $("#txtQuickReplyContentread_2").val()!="" 
				&& $("#txtQuickReplyContentread_2").val()!=notice){
		return true;
	}
	$("#message").html("内容为空，请输入内容");
	// $("#message-button").html("<input value=\"继续\" type=\"button\"
	// onclick=\"sub()\"/><input value=\"取消\" type=\"button\"
	// onclick=\"hiddeDiv('msg')\"/>");
	$("#msg").fadeIn("slow");
	setTimeout("hiddeDiv(\"msg\")",5000);
	return false;
}
function sub(ty){
	var form=document.getElementById("inputForm");
	if(ty== 1 || ty == 0 ){
		isSend=true;
		$("#isSend").val(ty);
		if(mysub(form)){
			form.submit();
		}
	}else{
		var linkmans="";
		var message_linkman=document.getElementById("message_linkman");
		var childNotes = message_linkman.childNodes;
		var isHaveLinkMan=false;
		var count=1;
		var length=childNotes.length;
		for ( var i = 0; i < length; i++) {
			var cn = childNotes[i];
			if(parseInt(cn.id)){
				linkmans+=cn.id+",";
			}
		}
		$("#userIds").val(linkmans.substring(0, linkmans.length-1));
		form.submit();
	}
}