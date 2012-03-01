function tlist() {
	$.post("/blogType", {
		"t" : $("#t").val(),
		"opera" : "lists",
		"isOption" : "0"
	}, function(data) {
		$("#typelist").html(data);
	});
}
function showResult() {
	$('#test_result').dialog('open');
	$("#test_result .result_content").html(
			"<div class='loading'><img src='/images/loading.gif'/></div>");
}
// 测试列表填写是否正确
function test_list() {
	$.get("/spider", {
		"opera" : "testList",
		"web_host" : getById("web_host"),
		"web_charSet" : getById("web_charSet"),
		"web_list_url" : getById("web_list_url"),
		"web_list_begin" : getById("web_list_begin"),
		"web_list_end" : getById("web_list_end")
	}, function(data) {
		$("#test_result .result_content").html(data);
	});
}

// 测试内容页是否填写正确
function test_content() {
	$.get("/spider", {
		"opera" : "testContent",
		"web_host" : getById("web_host"),
		"web_charSet" : getById("web_charSet"),
		"web_list_url" : getById("web_list_url"),
		"web_list_begin" : getById("web_list_begin"),
		"web_list_end" : getById("web_list_end"),
		"web_content_title" : getById("web_content_title"),
		"web_content_begin" : getById("web_content_begin"),
		"web_content_end" : getById("web_content_end"),
		"clear_content_reg" : getTag()
	}, function(data) {
		$("#test_result .result_content").html(data);
	});
}
function start_spider(id){
	
}
// 保存采集信息
function begin() {
	$('#test_result').dialog('open');
	$("#test_result .result_content").html(
			"<div class='loading'><img src='/images/loading.gif'/></div>");
	$.get("/spider", {
		"opera" : "add",
		"name" : getById("spider_name"),
		"web_host" : getById("web_host"),
		"web_charSet" : getById("web_charSet"),
		"web_list_url" : getById("web_list_url"),
		"web_list_begin" : getById("web_list_begin"),
		"web_list_end" : getById("web_list_end"),
		"web_content_title" : getById("web_content_title"),
		"web_content_begin" : getById("web_content_begin"),
		"web_content_end" : getById("web_content_end"),
		"spider_start" : getById("spider_start"),
		"tids" : getById("tids"),
		"clear_content_reg" : getTag()
	}, function(data) {
		$("#test_result .result_content").html(data);
	});
}
// 根据name属性，取值
function getById(id) {
	return $("#" + id).val();
}
function getTag() {
	var reg = "";
	$("#web_content .web_content_tags:checked").each(function() {
		reg += $(this).val() + ",";
	});
	return reg.substring(0, reg.length - 1);
}
$(function() {
	tlist();
	$(".tabs").tabs();
	// Dialog
	var name = $("#spider_name"), spider_start = $("#spider_start"), web_host = $("#web_host"), web_charSet = $("#web_charSet"), web_list_url = $("#web_list_url"), web_list_begin = $("#web_list_begin"), web_list_end = $("#web_list_end"),

	web_content_title = $("#web_content_title"), web_content_begin = $("#web_content_begin"), web_content_end = $("#web_content_end"), password = $("#password"), allFields = $(
			[]).add(name).add(spider_start).add(web_host).add(web_charSet).add(
			web_list_url).add(web_list_begin).add(web_list_end).add(
			web_content_title).add(web_content_begin).add(web_content_end), tips = $(".validateTips");
	tips_html = tips.html();
	function updateTips(t) {
		tips.text(t).addClass("ui-state-highlight");
		setTimeout(function() {
			tips.removeClass("ui-state-highlight", 1500);
		}, 500);
	}

	function checkLength(o, n, min, max) {
		if (o.val().length > max || o.val().length < min) {
			o.addClass("ui-state-error");
			updateTips("" + n + "的长度 必须在 " + min + " 和 " + max + "之间");
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp(o, regexp, n) {
		if (!(regexp.test(o.val()))) {
			o.addClass("ui-state-error");
			updateTips(n);
			return false;
		} else {
			return true;
		}
	}
	$('#spider_setting')
			.dialog(
					{
						autoOpen : false,
						width : 600,
						buttons : {
							"保存任务" : function() {
								// 检查选项是否都合法
							var bValid = true;
							allFields.removeClass("ui-state-error");

							bValid = bValid && checkLength(name, "名称", 3, 16);
							bValid = bValid
									&& checkLength(spider_start, "运行时间", 6, 80);
							bValid = bValid
									&& checkLength(web_host, "网址", 5, 80);
							bValid = bValid
									&& checkLength(web_list_url, "列表页地址", 5, 80);
							bValid = bValid
									&& checkLength(web_list_begin, "列表页开始位置",
											5, 200);
							bValid = bValid
									&& checkLength(web_list_end, "列表页结束位置", 5,
											200);

							bValid = bValid
									&& checkRegexp(
											web_host,
											/(?:https|http|ftp|rtsp|mms):\/\/[^\/]/,
											"网址格式错误");
							bValid = bValid
									&& checkRegexp(
											web_list_url,
											/(?:https|http|ftp|rtsp|mms):\/\/.+\/[\w]+\.[\w]+/,
											"网址格式错误");

							bValid = bValid
									&& checkLength(web_content_title, "内容标题",
											5, 80);
							bValid = bValid
									&& checkLength(web_content_begin,
											"内容页开始位置", 5, 200);
							bValid = bValid
									&& checkLength(web_content_end, "内容页结束位置",
											5, 200);

							if (bValid) {
								begin();
								$(this).dialog("close");
							}
						},
						"取消任务" : function() {
							$(this).dialog("close");
						}
						}
					});
	$('#test_result').dialog( {
		autoOpen : false,
		width : 500
	});

	$('#newSpider').click(function() {
		$('#spider_setting').dialog('open');
		allFields.removeClass("ui-state-error");
		updateTips(tips_html);
		return false;
	});
	$('#test_list_btn').click(function() {
		// 验证是否合法
			var bValid = true;
			allFields.removeClass("ui-state-error");

			bValid = bValid && checkLength(web_content_title, "内容标题", 5, 80);
			bValid = bValid
					&& checkLength(web_content_begin, "内容页开始位置", 5, 200);
			bValid = bValid && checkLength(web_content_end, "内容页结束位置", 5, 200);

			bValid = bValid
					&& checkRegexp(web_host,
							/(?:https|http|ftp|rtsp|mms):\/\/[^\/]/, "网址格式错误");
			bValid = bValid
					&& checkRegexp(web_list_url,
							/(?:https|http|ftp|rtsp|mms):\/\/.+\/[\w]+\.[\w]+/,
							"网址格式错误");
			if (bValid) {
				showResult();
				test_list();
			}
			return false;
		});
	$('#test_content_btn').click(function() {
		// 验证是否合法
			var bValid = true;
			allFields.removeClass("ui-state-error");

			bValid = bValid && checkLength(web_content_title, "内容标题", 5, 80);
			bValid = bValid
					&& checkLength(web_content_begin, "内容页开始位置", 5, 200);
			bValid = bValid && checkLength(web_content_end, "内容页结束位置", 5, 200);
			if (bValid) {
				showResult();
				test_content();
			}
			return false;
		});
	// 时间
	Calendar.setup( {
		inputField : "spider_start",
		trigger : "spider_start",
		onSelect : function() {
			this.hide()
		},
		showTime : 12,
		dateFormat : "%Y-%m-%d %H:%M"
	});
	//给开始按钮添加事件
	$(".start_spider").each(function(){	
		$(this).click(function() {
			showResult();
		$.get("/spider", {
				"opera" : "start","id":$(this).attr("name")},function(data){
					$("#test_result .result_content").html(data);
				});
		});
	});
});