/**
 * 
 */
$(document).ready(function() {
	$('#subjects_show_check').bind('drag', function(event) {
		$(this).css({
			top : event.offsetY,
			left : event.offsetX
		});
	});
	$("#subjects_div_ok").click(function() {
		var ids=$("#subjects_div_ok").attr("alt")+"-"+$("#way").val();
		var flag=false;
		$(".subjects_item_checked").each(function(index,domEle){
			if($(domEle).attr("id")){
    			ids+="-"+$(domEle).attr("id");
    			flag=true;
            }
		});
		if(flag){
			//alert("连接数据库，查询一下");
			to("scores_makeExcel-"+ids+".html");
			setTimeout("hiddeDiv(\"show_div_mask\")",5000);
			setTimeout("hiddeDiv(\"subjects_show_check\")",5000);
		}else{
			alert("没有选择任何东西");
		}
	});
	$("#subjects_div_cancel").click(function() {
		$("#show_div_mask").css("display", "none");
		$("#subjects_show_check").css("display", "none");
	});
});