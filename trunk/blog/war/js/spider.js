function tlist() {
	$.post("/blogType", {
		"t" : $("#t").val(),
		"opera" : "lists",
		"isOption" : "0"
	}, function(data) {
		$("#typelist").html(data);
	});
}
// 测试列表填写是否正确
function test_list() {
	showDiv("test_result");
	$("#test_result .result_content").html(
			"<div class='loading'><img src='/images/loading.gif'/></div>");
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
	showDiv("test_result");
	$("#test_result .result_content").html(
			"<div class='loading'><img src='/images/loading.gif'/></div>");
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
// 保存采集信息
function begin() {
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