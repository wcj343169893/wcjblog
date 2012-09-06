$(document).ready(function(){
		$('#subjects_show_check').bind('drag',function( event ){
            $( this ).css({
                    top: event.offsetY,
                    left: event.offsetX
                    });
            }); 
	    $("#subjects_div_ok").click(function(){
	        var ids=$("#subjects_div_ok").attr("alt")+"";
	        var flag=false;
	    	$("#subjects .subjects_item_checked").each(function(index,domEle){
	            	if($(domEle).attr("id")){
	        			ids+="-"+$(domEle).attr("id");
	        			flag=true;
	                }
	        	});
        	if(flag){
	        	$.ajax( {
	        		url : '/web/base/grade_subject.html',
	        		type : 'GET',
	        		data:'sids='+ids,
	        		dataType : 'text',
	        		timeout : 1000,
	        		error : function() {
	        			$('#subjects').html("<div>信息错误</div>");
	        		},
	        		success : function(data) {
	        			//$('#subjects').html("<div>"+data+"</div>");
	        			$("#message").html(data);
	            		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"showDiv('msg')\"/>");
	            		$("#msg").fadeIn("slow");
	            		setTimeout("hiddeDiv(\"msg\")",5000);
	        			hiddesubjects();
	            	}});
           	}else{
           		$("#message").html("请选择科目");
        		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"showDiv('msg')\"/>");
        		$("#msg").fadeIn("slow");
        		setTimeout("hiddeDiv(\"msg\")",5000);
            }
	     });
	    $("#subjects_div_cancel").click(function(){
	    	hiddesubjects();
	     });
	     function hiddesubjects(){
	    		$("#show_div_mask").css("display","none");
				$("#subjects_show_check").css("display","none");
	     }
	});
function showSubject(id,name){
	$("#gradeName_span").html(name);
	$("#show_div_mask").css("display","block");
	$("#subjects_show_check").css("display","block");
	$("#subjects_div_ok").attr("alt",id);
	$.ajax( {
	url : '/web/base/grade_subject.html',
	type : 'GET',
	data:'gid='+id,
	dataType : 'xml',
	timeout : 1000,
	error : function() {
		$('#subjects').html("<div>信息加载错误</div>");
	},
	success : function(data) {
		$('#subjects').html("");
		var id = "";
		var name = "";
		var isChecked = "false";
		$(data).find("subjectsList").find("subjects").each(
				function(index) {
					id = $(this).find("id").text();
					name = $(this).find("title").text();
					isChecked = $(this).find("isChecked").text();
					if(isChecked=='true'){
						$('<div class="subjects_item_checked" title="'+name+'" id="'+id+'">'+name+'</div>').appendTo('#subjects');
					}else{
						$('<div class="subjects_item" title="'+name+'" id="'+id+'">'+name+'</div>').appendTo('#subjects');
					}
				});
		 $(".subjects_item").each(function(index, domEle) {
	        	$(domEle).click(function(){
	        		if($(domEle).attr("class")=="subjects_item_checked"){
	        			$(domEle).attr("class","subjects_item");
	        		}else{
	        			$(domEle).attr("class","subjects_item_checked");
	        		}
	            	});
			});
		 $(".subjects_item_checked").each(function(index, domEle) {
			 $(domEle).click(function(){
				 if($(domEle).attr("class")=="subjects_item_checked"){
					 $(domEle).attr("class","subjects_item");
				 }else{
					 $(domEle).attr("class","subjects_item_checked");
				 }
			 });
		 });
	}
	});
}