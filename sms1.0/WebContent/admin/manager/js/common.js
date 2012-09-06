//跳转到新页面
function to(url) {
	window.location.href = url;
}
// 返回
function back() {
	history.back();
}
//学校，年级联动
function changeSchool(list){
	var sid=$("#schoolId").val();//学校下拉框值
	var gid=$("#grade_id").val();//年级编号
	if(sid!=0){
		$.post("grade_listBySid.html", {
			"sid" : sid,
			"datas" : Math.random()
		}, function(data) {
			var grade = document.getElementById("gradeId");
			grade.options.length = 0;
			if(list){
				var option = new Option("全部", 0);
				grade.options.add(option);
			}
			var cs = data.split(",");
			for ( var i = 0; i < cs.length; i++) {
				var c = cs[i].split(":");
				if(c[0]>0){
					var option = new Option(c[1], c[0]);
					if (c[0] == gid) {
						option.selected = true;
					}
					grade.options.add(option);
				}
			}
			if(!list){
				changeGrade();
			}
		});
		var clazzs = document.getElementById("clazz");
		if(list){
			clazzs.options.length = 0;
			var option = new Option("全部", 0);
			clazzs.options.add(option);
		}else{
			//clazzs.options.length = 0;
		}
	}else{
		var grade = document.getElementById("gradeId");
		var clazzs = document.getElementById("clazz");
		grade.options.length = 0;
		clazzs.options.length = 0;
		var option = new Option("全部", 0);
		clazzs.options.add(option);
		grade.options.add(option);
	}
}

function changeGrade() {
	var cid = $("#clazzid").val();
	if($("#gradeId").val()){
	$.post("clazz_listByGid.html", {
		"gid" : $("#gradeId").val(),
		"datas" : Math.random()
	}, function(data) {
		var clazzs = document.getElementById("clazz");
		clazzs.options.length = 0;
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			var c = cs[i].split(":");
			if(c[0]>0){
				var option = new Option(c[1], c[0]);
				if (c[0] == cid) {
					option.selected = true;
				}
				clazzs.options.add(option);
			}
		}
	});
	}
}
function changeClassByGrade() {
	var gradeId = $("#gradeId").val();
	var cid = $("#cid").val();
	if (gradeId != 0) {
		$.post("clazz_listByGid.html", {
			"gid" : $("#gradeId").val(),
			"datas" : Math.random()
		}, function(data) {
			var clazzs = document.getElementById("clazz");
			clazzs.options.length = 0;
			var cs = data.split(",");
			var option = new Option("全部", 0);
			clazzs.options.add(option);
			for ( var i = 0; i < cs.length; i++) {
				if (cs[i]) {
					var c = cs[i].split(":");
					option = new Option(c[1], c[0]);
					if (c[0] == cid) {
						option.selected = true;
					}
					clazzs.options.add(option);
				}
			}
		});
	} else {
		var clazzs = document.getElementById("clazz");
		clazzs.options.length = 0;
		var option = new Option("全部", 0);
		clazzs.options.add(option);
	}
}
function changeClassByGrade2() {
	var gradeId = $("#gradeId").val();
	var cid = $("#cid").val();
	if (gradeId != 0) {
		$.post("teacher_listByGid.html", {
			"gid" : $("#gradeId").val(),
			"datas" : Math.random()
		}, function(data) {
			var clazzs = document.getElementById("clazz");
			clazzs.options.length = 0;
			var cs = data.split(",");
			var option = new Option("全部", 0);
			clazzs.options.add(option);
			for ( var i = 0; i < cs.length; i++) {
				if (cs[i]) {
					var c = cs[i].split(":");
					option = new Option(c[1], c[0]);
					if (c[0] == cid) {
						option.selected = true;
					}
					clazzs.options.add(option);
				}
			}
		});
	} else {
		var clazzs = document.getElementById("clazz");
		clazzs.options.length = 0;
		var option = new Option("全部", 0);
		clazzs.options.add(option);
	}
}
function changeRole() {
	var gid = $("#groupsid").val();

	$.post("groups_listByRid.html", {
		"rid" : $("#rid").val(),
		"datas" : Math.random()
	}, function(data) {
		var group = document.getElementById("groups");
		group.options.length = 0;
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i]) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				if (c[0] == gid) {
					option.selected = true;
				}
				group.options.add(option);
			}
		}
	});
	if ($("#rid").val() == 3 || $("#rid").val() == 2) {
		slideDown('showgrade');
	} else {
		slideUp('showgrade');
	}
	if ($("#rid").val() == 3) {
		slideDown('showsnumber');
	} else {
		slideUp('showsnumber');
	}
}
function changeGroupsByRole() {
	var rid = $("#rid").val();
	var gid = $("#gid").val();
	// 在这里判断一下
	if (rid != 0) {
		$.post("groups_listByRid.html", {
			"rid" : $("#rid").val(),
			"datas" : Math.random()
		}, function(data) {
			var group = document.getElementById("groups");
			group.options.length = 0;
			var cs = data.split(",");
			var option = new Option("全部", 0);
			group.options.add(option);
			for ( var i = 0; i < cs.length; i++) {
				var c = cs[i].split(":");
				option = new Option(c[1], c[0]);
				if (c[0] == gid) {
					option.selected = true;
				}
				group.options.add(option);
			}
		});
	} else {
		var group = document.getElementById("groups");
		group.options.length = 0;
		var option = new Option("全部", 0);
		group.options.add(option);
	}
}
// 修改教师的信息
function changeGradeInTeacher() {
	var cid = $("#clazzid").val();
	$.post("teacher_listByGid.html", {
		"gid" : $("#gradeId").val(),
		"datas" : Math.random()
	}, function(data) {
		var clazzs = document.getElementById("clazz");
		clazzs.options.length = 0;
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			var c = cs[i].split(":");
			var option = new Option(c[1], c[0]);
			if (c[0] == cid) {
				option.selected = true;
			}
			clazzs.options.add(option);
		}
	});
}

// 学校与年级的联动
function changeGradeBySchool() {
	$.post("grade_listBySid.html", {
		"sid" : $("#school").val(),
		"datas" : Math.random()
	}, function(data) {
		// $("#clazz").html(data);
			var grades = document.getElementById("grade");
			grades.options.length = 0;
			var cs = data.split(",");
			for ( var i = 0; i < cs.length; i++) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				grades.options.add(option);
			}
		});
}
// 学校与年级的联动
function changeGradeBySchool(sid, gid, gradeId) {
	$.post("grade_listBySid.html", {
		"sid" : $("#" + sid).val(),
		"datas" : Math.random()
	}, function(data) {
		// $("#clazz").html(data);
			var grades = document.getElementById(gid);
			grades.options.length = 0;
			var cs = data.split(",");
			for ( var i = 0; i < cs.length; i++) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				if (gradeId == Number(c[0])) {
					option.selected = true;
				}
				grades.options.add(option);
			}
		});
}
function changeGBS(sid, gid, id) {
	$.post("grade_listBySid.html", {
		"sid" : $("#" + sid).val(),
		"datas" : Math.random()
	}, function(data) {
		var grades = document.getElementById(gid);
		grades.options.length = 0;
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			var c = cs[i].split(":");
			var option = new Option(c[1], c[0]);
			if (id == c[0]) {
				option.selected = true;
			}
			grades.options.add(option);
		}
	});
}
// 删除页面元素
function delLinkMan(ob) {
	ob.parentNode.parentNode.removeChild(ob.parentNode);
}
// 显示，隐藏div
function showDiv(id) {
	var div = $("#" + id);
	if (div.css("display") == "none") {
		$("#" + id).fadeIn("slow");
		// $("#" + id).show("slow");
	} else {
		$("#" + id).fadeOut("slow");
		// $("#" + id).hide("slow");
	}
}
// 隐藏div
function hiddeDiv(id) {
	$("#" + id).fadeOut("slow");
}
// 显示
function show(id) {
	$("#" + id).fadeIn("slow");
}
// 显示
function slideDown(id) {
	$("#" + id).slideDown("slow");
}
// 隐藏
function slideUp(id) {
	$("#" + id).slideUp("slow");
}
// 新增添加联系人
function divAddContext(id, name, mobile) {
	var message_linkman = document.getElementById("message_linkman");
	var lkm_onload = document.getElementById("query");
	if (lkm_onload) {
		lkm_onload.value="";
	}
	var childNotes = message_linkman.childNodes;
	for ( var i = 0; i < childNotes.length; i++) {
		var cn = childNotes[i];
		if (cn.id == id) {
			lkm_onload.focus();
			return;
		}
	}
	if (lkm_onload) {
		// 移除空白输入框<input id="lkm_onload">
		message_linkman.removeChild(lkm_onload);
	}
	var div_linkMan = document.createElement("div");
	div_linkMan.id = id;
	var name_linkman = document.createElement("strong");
	name_linkman.innerHTML = "\"" + name + "\" ";
	div_linkMan.appendChild(name_linkman);
	var mobile_linkman = document.createElement("em");
	mobile_linkman.innerHTML = "<" + mobile + ">";
	div_linkMan.appendChild(mobile_linkman);
	var del = document.createElement("span");
	del.innerHTML = "[X]";
	del.className = "del";
	del.onclick = function() {
		delLinkMan(this);
		lkm_onload.focus();
	};
	div_linkMan.appendChild(del);
	var fenhao = document.createElement("span");
	fenhao.innerHTML = ";";
	div_linkMan.appendChild(fenhao);
	message_linkman.appendChild(div_linkMan);
	// 添加空白输入框
	if (lkm_onload) {
		message_linkman.appendChild(lkm_onload);
		// 设置为lkm_onload.focus();
		lkm_onload.focus();
	}
}
// 动态加载用户信息
function changeUserByRid(rid) {
	$.post("user_listByRid.html", {
		"rid" : rid,
		"datas" : Math.random()
	}, function(data) {
		var linkMans = document.getElementById("linkmans");
		linkMans.innerHTML = "";
		if (data != null && "" != data) {
			var cs = data.split(",");
			var str = "<input type='button' value='全选' onclick='checkAll(\""
					+ data + "\")'/> <ul>";
			for ( var i = 0; i < cs.length; i++) {
				var c = cs[i].split(":");
				str += "<li onclick=divAddContext(" + c[0] + ",'" + c[1]
						+ "','" + c[2] + "') title=\"'" + c[1] + "' &lt;"
						+ c[2] + "&gt;\">" + c[1] + "</li>";
			}
			str += "</ul>";
			linkMans.innerHTML = str;
		} else {
			linkMans.innerHTML = "暂无数据";
		}
	});
}
// 批量增加联系人
function checkAll(str) {
	if (str != null && "" != str) {
		var cs = str.split(",");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i]) {
				var c = cs[i].split(":");
				divAddContext(c[0], c[1], c[2]);
			}
		}
	}
}
// 更新家长信息
function update(puid, name, relationship, mobile, upid) {
	var inputForm = document.getElementById("inputForm");
	var inputs = inputForm.elements;
	for ( var i = 0; i < inputs.length; i++) {
		var inp = inputs[i];
		if (inp.name == "puid") {
			inp.value = puid;
		} else if (inp.name == "nikeName") {
			inp.value = name;
		} else if (inp.name == "relationship") {
			inp.value = relationship;
		} else if (inp.name == "mobile") {
			inp.value = mobile;
		} else if (inp.name == "upid") {
			inp.value = upid;
		}
	}
}
// 根据mobile更新家长信息
function updateParent(puid, name,rid) {
	var inputForm = document.getElementById("inputForm");
	var inputs = inputForm.elements;
	for ( var i = 0; i < inputs.length; i++) {
		var inp = inputs[i];
		if (inp.name == "puid") {
			inp.value = puid;
		} else if (inp.name == "nikeName") {
			inp.value = name;
		} else if (inp.name == "rid") {
			inp.value = rid;
		}
	}
}
// 成绩统计，科目分段查询
function subject() {
	var str = "";
	$.post("scores_subject.html", {
		"eid" : $("#eid").val(),
		"datas" : Math.random()
	}, function(data) {
		var es = data.split("_");
		for ( var i = 0; i < (es.length - 1) / 6; i++) {
			str += "<div class='list_items'><div class='list_id'>" + (i + 1)
					+ "</div>" + "<div class='list_title'>" + es[(6 * i)]
					+ "</div>" + "<div class='list_id'>"
					+ Number("0" + es[(6 * i + 1)]).toFixed(2) + "</div>"
					+ "<div class='list_id'>" + es[(6 * i + 2)] + "</div>"
					+ "<div class='list_id'>" + es[(6 * i + 3)] + "</div>"
					+ "<div class='list_id'>" + es[(6 * i + 4)] + "</div>"
					+ "<div class='list_id'>" + es[(6 * i + 5)]
					+ "</div></div>";
		}
		$("#content").empty();
		$("#content").append(str);
	});
	$("#title").css("display", "block");
}
// 成绩统计，总分分段查询
function total() {
	var str = "";
	if ($("#start").val() != "" && $("#end").val() != "") {
		$.post("scores_total.html", {
			"eid" : $("#eid").val(),
			"start" : $("#start").val(),
			"end" : $("#end").val(),
			"inter" : $("#inter").val(),
			"datas" : Math.random()
		}, function(data) {
			var es = data.split("_");
			for ( var i = 0; i < (es.length - 1) / 2; i++) {
				str += "<div class='list_items'><div class='list_id'>"
						+ (i + 1) + "</div>" + "<div class='list_title'>"
						+ es[(2 * i)] + "</div>" + "<div class='list_id'>"
						+ es[(2 * i + 1)] + "</div></div>";
			}
			$("#content").empty();
			$("#content").append(str);
		});
		$("#title").css("display", "block");
	} else {
		//alert("请输入分段开始和分段结束");
		$("#message").html("请输入分段开始和分段结束");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
	}
}
// 添加考试信息
function clears(eid) {
	if (!$("#c_" + eid).attr("checked")) {
		$("#total_" + eid).val("100");		
	}
}
function mysubmit() {
	var inputform = document.getElementById("inputform");
	var name = document.getElementById("name");
	var examTime = document.getElementById("examTime");
	var checkBoxes = inputform.elements;	
	if($("#name").val() != "" && $("#examTime").val() != ""){		
		var v = "";
		for ( var i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].type == "checkbox") {
				var cb = checkBoxes[i];
				if (cb.checked) {
					v += cb.value + "_" + $("#total_" + cb.value).val();
					v += ",";
				}
			}
		}
		if(v == ""){			
			//alert("请选择考试科目");
			$("#message").html("请选择考试科目");
			$("#msg").fadeIn("slow");
			timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		}else{
			v += "0";
			$("#subjects").val(v);
			inputform.submit();			
		}
	}else{
		//alert("请输入考试名称、考试时间");	
		$("#message").html("请输入考试名称和考试时间");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
	}
}
$ = function(o) {
	return document.getElementById(o);
}
// 成绩录入
function mysubmits() {
	var inputform = $("#inputform");
	var input = $(":text");
	var str = "";
	for ( var i = 0; i < input.length ; i++) {
		str += input[i].name + input[i].value + ",";
	}
	$("#examScores").val(str.substring(0, str.length - 1));
	inputform.submit();
}
// 成绩分析，按总分
function totalAnalysis() {
	var str = "";
	var way = $("#way").val();
	if ($("#uid").val() > 0) {
		$.post("scores_totalAnalysis.html", {
			"cid" : $("#cid").val(),
			"uid" : $("#uid").val(),
			"way" : $("#way").val(),
			"start" : $("#start").val(),
			"end" : $("#end").val(),
			"datas" : Math.random()
		}, function(data) {
			var es = data.split("_");
			var col = 3; // 页面上显示的列数
				var xname = new Array();
				var xscore = new Array();
				for ( var i = 0; i < (es.length - 1) / col; i++) {
					str += "<div class='list_items'><div class='list_id'>"
							+ (i + 1) + "</div>" + "<div class='list_title'>"
							+ es[(col * i)] + "</div>"
							+ "<div class='list_time'>"
							+ es[(col * i + 1)].substring(0, 10) + "</div>"
							+ "<div class='list_id'>" + es[(col * i + 2)]
							+ "</div></div>";
					xname[i] = es[(col * i)];
					xscore[i] = Number(es[(col * i + 2)]);
				}
				$("#content").empty();
				$("#container").empty();
				$("#content").append(str);
				if (way == 0) {
					analysis('container', "总分分析", "总分数", xname, "考试名称", "考试分数",
							"成绩", xscore, "分");
				} else if (way == 1) {
					analysis('container', "总分分析", "名次", xname, "考试名称", "考试名次",
							"名次", xscore, "名");
				}
			});
		$("#title").css("display", "block");
	} else {
		//alert("请选择学生");
		$("#message").html("请选择学生");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
	}
}
/**
 * @param id
 *            页面上显示图片的编号container
 * @param title_text
 *            主标题
 * @param subtitle_text
 *            副标题
 * @param xAxis_categories
 *            x轴上的提示文字【数组】 例如：'考试名称', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul',
 *            'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
 * @param xAxis_text
 *            例如:'Month'
 * @param yAxis_text
 *            例如：'总分/名次'
 * @param series_name
 *            例如：'总分/名次'
 * @param series_data
 *            例如：[ 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
 *            13.9, 9.6 ]
 * @param unit
 *            单位
 * @return
 */
function analysis(id, title_text, subtitle_text, xAxis_categories, xAxis_text,
		yAxis_text, series_name, series_data, unit) {
	var chart = new Highcharts.Chart( {
		chart : {
			renderTo : id,
			defaultSeriesType : 'line',
			margin : [ 50, 150, 60, 80 ]
		},
		title : {
			text : title_text,
			style : {
				margin : '10px 100px 0 0' // center it
		}
		},
		subtitle : {
			text : subtitle_text,
			style : {
				margin : '0 100px 0 0' // center it
		}
		},
		xAxis : {
			categories : xAxis_categories,
			title : {
				text : xAxis_text
			}
		},
		yAxis : {
			title : {
				text : yAxis_text
			}
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' + this.x + ': '
						+ this.y + unit;
			}
		},
		legend : {
			layout : 'vertical',
			style : {
				left : 'auto',
				bottom : 'auto',
				right : '10px',
				top : '100px'
			}
		},
		series : [ {
			name : series_name,
			data : series_data
		// dataURL: 'tokyo.json'//如果需要进入查看详细信息，则启用此连接
		} ]
	});
}
// 成绩分析，按学科
function subjectAnalysis() {
	var str = "";
	var way = $("#way").val();
	// 判断学生!=0
	if ($("#uid").val() > 0 && $("#sid").val() > 0) {
		$.post("scores_subjectAnalysis.html", {
			"cid" : $("#cid").val(),
			"uid" : $("#uid").val(),
			"sid" : $("#sid").val(),
			"way" : $("#way").val(),
			"start" : $("#start").val(),
			"end" : $("#end").val(),
			"datas" : Math.random()
		}, function(data) {
			var es = data.split("_");
			var col = 3; // 页面上显示的列数
				var xname = new Array();
				var xscore = new Array();
				for ( var i = 0; i < (es.length - 1) / col; i++) {
					str += "<div class='list_items'><div class='list_id'>"
							+ (i + 1) + "</div>" + "<div class='list_title'>"
							+ es[(col * i)] + "</div>"
							+ "<div class='list_time'>"
							+ es[(col * i + 1)].substring(0, 10) + "</div>"
							+ "<div class='list_id'>" + es[(col * i + 2)]
							+ "</div></div>";
					xname[i] = es[(col * i)];
					xscore[i] = Number(es[(col * i + 2)]);
				}
				$("#content").empty();
				$("#container").empty();
				$("#content").append(str);
				if (way == 0) {
					analysis('container', "各科分析", "科目分数", xname, "考试名称",
							"科目分数", "成绩", xscore, "分");
				} else if (way == 1) {
					analysis('container', "各科分析", "科目名次", xname, "考试名称",
							"科目名次", "名次", xscore, "名");
				}
			});
		$("#title").css("display", "block");
	} else {
		//alert("请选择学生和科目");
		$("#message").html("请选择学生和科目");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
	}

}
// 成绩分析，选择单选框
function checkRadio(obj) {
	$("#way").val(obj.value);
}
// 查询本班的所有科目
function findAllSubject(cid) {
	if ($("#buildBox").css("display") == "none") {
		$
				.post(
						"message_subject.html",
						{
							"cid" : cid,
							"datas" : Math.random()
						},
						function(data) {
							var str = "<div class='buildBox_title'> 如果不输入，则不计入短信中&nbsp;"
									+ "<input type='button' value='生成短信' id='generateWork'/>&nbsp;"
									+ "<input type='button' value='关闭' onclick='hiddeDiv(\"buildBox\")'/>"
									+ "</div>";
							var subjects = data.split(",");
							for ( var i = 0; i < subjects.length; i++) {
								if (subjects[i]) {
									var subject = subjects[i].split(":");
									str += "<div class='homeworks'><label>";
									str += subject[1]
											+ "</label>:<input type='text' class='homeworks_value' title='"
											+ subject[1] + "' name='"
											+ subject[0] + "'/>";
									str += "</div>";
								}
							}
							// buildBox中填入数据
							$("#buildBox").html(str);
							$("#buildBox").css( {
								"width" : "650px"
							});
							// 生成作业
							$("#generateWork")
									.click(
											function() {
												var str = "";
												$(".homeworks_value")
														.each(
																function(index,
																		domEle) {
																	if ($(
																			domEle)
																			.val()) {
																		$(
																				domEle)
																				.css(
																						"backgroundColor",
																						"yellow");
																		str += $(
																				domEle)
																				.attr(
																						"title")
																				+ ":"
																				+ $(
																						domEle)
																						.val()
																				+ ",";
																	}
																});
												$("#content").val(str);
												showDiv('buildBox');
											});
						});
	}
	showDiv('buildBox');
}

// 查询考试
function findAllScore(cid) {
	if ($("#buildBox").css("display") == "none") {
		$
				.post(
						"message_score.html",
						{
							"cid" : cid,
							"datas" : Math.random()
						},
						function(data) {
							var str = "<div class='buildBox_title'>&nbsp;"
									+ "<input type='button' value='生成短信' id='generateScore'/>&nbsp;"
									+ "<input type='button' value='关闭' onclick='hiddeDiv(\"buildBox\")'/>"
									+ "</div>";
							var exams = data.split(",");
							str += "<div class='exams'><select name='exam' id='exam' onchange='findByEidCid(this.value,\""
									+ cid + "\")'>";
							str += "<option value='0'>请选择考试</option>";
							for ( var i = 0; i < exams.length; i++) {
								if (exams[i]) {
									var exam = exams[i].split(":");
									if ($("#eid").val() == exam[0]) {
										str += "<option value='" + exam[0]
												+ "' selected>" + exam[1]
												+ "</option>";
									} else {
										str += "<option value='" + exam[0]
												+ "'>" + exam[1] + "</option>";
									}
								}
							}
							str += "</select></div>";
							str += "<div class='subjects' id='subjects'></div>";
							// buildBox中填入数据
							$("#buildBox").html(str);
							$("#buildBox").css( {
								"width" : "300px"
							});
						});
	}
	showDiv('buildBox');
}
// 查询考试科目
function findByEidCid(eid, cid) {
	if (eid > 0) {
		$
				.post(
						"message_subject.html",
						{
							"cid" : cid,
							"eid" : eid,
							"datas" : Math.random()
						},
						function(data) {
							var subjects = data.split(",");
							var str = "";
							for ( var i = 0; i < subjects.length; i++) {
								if (subjects[i]) {
									var subject = subjects[i].split(":");
									str += "<input type='checkbox' value='"
											+ subject[0] + "' id='subject_"
											+ subject[0] + "' title='"
											+ subject[1]
											+ "'/> <label for='subject_"
											+ subject[0] + "'>" + subject[1]
											+ "</label>";
								}
							}
							$("#subjects").html(str);
							$("#generateScore")
									.click(
											function() {
												var checkboxs = $("#subjects :checkbox:checked");// 得到选中的科目
												var title_str = $(
														"#exam option:selected")
														.text();
												var content_str = "exam"
														+ $("#exam").val()
														+ "/exam";
												var flag = false;
												for ( var i = 0; i < checkboxs.length; i++) {
													title_str += "《"
															+ checkboxs[i].title
															+ "》";
													content_str += "subject"
															+ checkboxs[i].value
															+ "/subject";
													flag = true;
												}
												if (flag) {
													$("#title").val(title_str);
													$("#content").val(
															content_str);
													showDiv('buildBox');
												} else {
													$("#message").html("请选择科目");
													$("#message-button")
															.html(
																	"<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
													$("#msg").fadeIn("slow");
													timeoutId=setTimeout(
															"hiddeDiv(\"msg\")",
															5000);
												}
												// 添加多次考试
												// $("#title").val($("#title").val()+$("#exam
												// option:selected").text() +
												// "---"+ $("#subs
												// option:selected").text()+"
												// ");
												// $("#content").append("[exam]["
												// + $("#exam").val() + "]["+
												// $("#subs").val() +
												// "][/exam]");
												// $("#title").val($("#exam
												// option:selected").text() +
												// "---"+
												// $("#subs
												// option:selected").text()+"
												// ");
												// $("#content").val("exam" +
												// $("#exam").val() + ""+
												// $("#subs").val() + "/exam");

											});
						});
	} else {
		$("#subjects").html("");
	}
}
// 查询模板
function getTemplate(tid) {
	if ($("#buildBox").css("display") == "none") {
		$
				.post(
						"message_template.html",
						{
							"tid" : tid,
							"datas" : Math.random()
						},
						function(data) {
							var templates = data.split("template:=");
							var str = "<div class='buildBox_title'>&nbsp;"
									+ "直接点击就可以添加到内容中&nbsp;"
									+ "<input type='button' value='关闭' onclick='hiddeDiv(\"buildBox\")'/>"
									+ "</div>";
							for ( var i = 0; i < templates.length; i++) {
								if (templates[i]) {
									str += "<div class='template'>"
											+ templates[i] + "</div>";
								}
							}
							$("#buildBox").html(str);
							$("#buildBox").css( {
								"width" : "650px"
							});
							$(".template").click(function() {
								$("#content").append(this.innerHTML);
								this.style.backgroundColor = "white";
								// showDiv('buildBox');
								});
						});
	}
	showDiv('buildBox');
}
// 获取学生生日列表
function getStudentBirthdayList() {
	alert("test");
	var str = "";
	$.post("index_getStudent.html", {
		"datas" : Math.random()
	}, function(data) {
		var br1 = data.split(",");
		var col = 2;
		var length = br1.length;
		for ( var i = 0; i < br1.length; i++) {
			var br2 = br1[i].split("_");
			str += "<div class='list_items'>";
			for ( var j = 0; j < br2.length; j++) {
				str += "<div class='list_id'>" + br2[(col * j)] + "</div>"
						+ "<div class='list_operate'>" + br2[(col * j + 1)]
						+ "</div>";
			}
			str += "</div>";
		}
		// str +="<div class="">本月共有"+length+"个同学过生日</div>";
			$("#content").append(str);
		});
}
// 获取所有教师生日列表
function getAllTeacherBirthdayList() {
	var str = "";
	$.post("index_getAllTeacher.html", {
		"datas" : Math.random()
	}, function(data) {
		var br1 = data.split(",");
		var col = 2;
		var length = br1.length;
		for ( var i = 0; i < br1.length; i++) {
			var br2 = br1[i].split("_");
			str += "<div class='list_items'>";
			for ( var j = 0; j < br2.length; j++) {
				str += "<div class='list_id'>" + br2[(col * j)] + "</div>"
						+ "<div class='list_operate'>" + br2[(col * j + 1)]
						+ "</div>";
			}
			str += "</div>";
		}
		// str +="<div class="">本月共有"+length+"个老师过生日</div>";
			$("#content").append(str);
		});
}
// 获取本班教师生日列表
function getTeacherBirthdayList() {
	var str = "";
	$.post("index_getTeacher.html", {
		"datas" : Math.random()
	}, function(data) {
		var br1 = data.split(",");
		var col = 2;
		for ( var i = 0; i < br1.length; i++) {
			var br2 = br1[i].split("_");
			for ( var j = 0; j < br2.length; j++) {
				str += "<div class='list_items'><div class='list_id'>"
						+ br2[(col * j)] + "</div>"
						+ "<div class='list_operate'>" + br2[(col * j + 1)]
						+ "</div>";
			}
		}
		// str +="<div class="">本月共有"+length+"个老师过生日</div>";
			$("#content").append(str);
		});
}

// 获取领导生日列表
function getLeaderBirthdayList() {
	var str = "";
	$.post("index_getLeader.html", {
		"datas" : Math.random()
	}, function(data) {
		var br1 = data.split(",");
		var col = 2;
		for ( var i = 0; i < br1.length; i++) {
			var br2 = br1[i].split("_");
			for ( var j = 0; j < br2.length; j++) {
				str += "<div class='list_items'><div class='list_id'>"
						+ br2[(col * j)] + "</div>"
						+ "<div class='list_operate'>" + br2[(col * j + 1)]
						+ "</div>";
			}
		}
		// str +="<div class="">本月共有"+length+"个领导过生日";
			$("#content").append(str);
		});
}
// 获得信息列表
function getMessageList() {
	alert(133);
	$.post("/web/admin/desktop_getMessage.html", {
		"datas" : Math.random()
	}, function(data) {
		alert(data);
		var str = "";
		var ms1 = data.split(",");
		var col = 4;
		var length = ms1.lenght;
		for ( var i = 0; i < ms1.length; i++) {
			var ms2 = ms1[i].split("_");
			for ( var j = 0; j < ms2.length; j++) {
				str += "<div class='list_items' title='" + ms2[(col * j + 2)]
						+ ms2[(col * j + 3)] + "'><div class='list_operate'>"
						+ ms2[(col * j)] + "</div>"
						+ "<div class='list_operate'>" + ms2[(col * j + 1)]
						+ "</div>";
			}
		}
		// str +="<div class="">你有"+length+"条未读信息</div>";
			$("#content").append(str);
		});
}
function showKE() {
	KE.show( {
		id : 'content',
		imageUploadJson : "/admin/manager/kindeditor/jsp/upload_json.jsp",
		allowFileManager : true,
		afterCreate : function(id) {
			KE.event.ctrl(document, 13, function() {
				KE.util.setData(id);
				document.forms['school_form'].submit();
			});
			KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
				KE.util.setData(id);
				document.forms['school_form'].submit();
			});
		}
	});
}
// 领导成绩分析的年级班级联动
function changeClazzByGrade() {
	$.post("scores_listByGid.html", {
		"gid" : $("#gradeId").val(),
		"datas" : Math.random()
	}, function(data) {
		var clazz = document.getElementById("cid");
		clazz.options.length = 0;
		var option = new Option("请选择", 0);
		clazz.options.add(option);
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i]) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				clazz.options.add(option);
			}
		}
	});
}

// 检查父母用户
function checkParents(mobile) {
	if (mobile.length == 11) {
		$.get("parents_checkUser.html",	{
							"mobile" : mobile,// 传参数
							"datas" : Math.random()
						},
						function(data) {
							$("#isAddTo").val("false");
							if (data) {
								var str = "<div>"
										+ "<span class='list_bottom_query'>你可以从下表中选择用户，如果没有你要添加的用户，请确认号码是否正确：</span>"
										+ "<div class='list_items bg'>"
										+ "<div class='list_id'></div>"
										+ "<div class='list_title'>家长</div>"
										+ "<div class='list_title'>子女</div>"
										+ "<div class='list_title'>角色</div>"
										+ "</div>";
								var cu1 = data.split(",");
								var length = cu1.length;
								for ( var i = 0; i < cu1.length - 1; i++) {
									var cu2 = cu1[i].split("_");
									if (cu2[i]) {
										str += "<div class='list_items' onclick='updateParent(\""+ cu2[0]+ "\",\""+ cu2[1]+ "\",\""+cu2[3]+ "\");abcde()'>";
										str += "<div class='list_id'></div>"
												+ "<div class='list_title'>"+ cu2[1]+ "</div>";
										if (cu2[2] != "" && cu2[2] != "null") {
											str += "<div class='list_title'>"+ cu2[2] + "</div>";
										} else {
											str += "<div class='list_title'>无</div>";
										}
										str += "<div class='list_title'>"+cu2[4]+"</div>";
										str += "</div>";
									}
								}
								str += "</div>";
								$("#mobile_parent").html(str);
								$("#isAddTo").val("true");
							}
						});
	}else{
		$("#mobile_parent").html("");
	}

}
function abcde(){
	$("#isAddTo").val("false");
}
//检查教师用户
function checkTeacher(obj) {
	if (obj.value.length == 11 && obj.value!=obj.title) {
		$.get("teacher_checkTeacher.html",	{
							"mobile" : obj.value,// 传参数
							"datas" : Math.random()
						},
						function(data) {
							if(!isNaN(obj.value)){
								if (data) {
									var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>此号码已被使用，请重新输入号码！</span></div>";
								}else{
									var str = "<div><input type='hidden' name='cu' value='0' id='cu'/><span class='search_name'>默认为登录账号！</span></div>";
								}
							}else{
								var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确！</span></div>";
							}
							$("#mobile_teacher").html(str);
						});
	}else if(obj.value.length >11 ){
		$("#mobile_self").html("<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确，请重新输入号码！</span></div>");
	}else{
		$("#mobile_teacher").html("");
	}
}
//检查学生用户
function checkStudent(obj) {
	if (obj.value.length == 11 && obj.value!=obj.title) {
		$.get("student_checkStudent.html",	{
							"mobile" : obj.value,// 传参数
							"datas" : Math.random()
						},
						function(data) {
							if(!isNaN(obj.value)){
								if (data) {
									var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>此号码已被使用，请重新输入号码！</span></div>";
								}else{
									var str = "<div><input type='hidden' name='cu' value='0' id='cu'/><span class='search_name'>默认为登录账号！</span></div>";
								}
							}else{
								var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确！</span></div>";
							}
							$("#mobile_student").html(str);
						});
	}else if(obj.value.length >11 ){
		$("#mobile_self").html("<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确，请重新输入号码！</span></div>");
	}else{
		$("#mobile_student").html("");
	}
}

//检查用户
function checkUser(obj) {
	if (obj.value.length == 11 && obj.value!=obj.title) {
		$.get("user_checkUser.html",	{
							"mobile" : obj.value,// 传参数
							"datas" : Math.random()
						},
						function(data) {
							if(!isNaN(obj.value)){
								if (data) {
									var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>此号码已被使用，请重新输入号码！</span></div>";
								}else{
									var str = "<div><input type='hidden' name='cu' value='0' id='cu'/><span class='search_name'>默认为登录账号！</span></div>";
								}
							}else{
								var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确！</span></div>";
							}
							$("#mobile_user").html(str);
						});
	}else{
		$("#mobile_user").html("");
	}
}
//修改个人信息时，根据mobile检查用户
function checkSelf(obj) {
	if (obj.value.length == 11 && obj.value!=obj.title ) {
		$.get("self_checkSelf.html",	{
							"mobile" : obj.value,// 传参数
							"datas" : Math.random()
						},
						function(data) {
							if(!isNaN(obj.value)){
								if (data) {
									var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>此号码已被使用，请重新输入号码！</span></div>";
								}else{
									var str = "<div><input type='hidden' name='cu' value='0' id='cu'/><span class='search_name'>默认为登录账号！</span></div>";
								}
							}else{
								var str = "<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确！</span></div>";
							}
							$("#mobile_self").html(str);
						});
	}else if(obj.value.length >11 ){
		$("#mobile_self").html("<div><input type='hidden' name='cu' value='1' id='cu' /><span class='search_key'>号码格式不正确，请重新输入号码！</span></div>");
	}else{
		$("#mobile_self").html("");
	}
}
//提交时，检查
function checkSub(obj){
	var nikeName=document.getElementById("nikeName");
	if(!nikeName.value){
		$("#message").html("请填写姓名");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#rid").val()!= 3){
		if($("#mobile").val()=="" ){
			$("#message").html("请输入电话号码");
			$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
			$("#msg").fadeIn("slow");
			timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
			return false;
		}else if($("#mobile").val()=="" || $("#cu").val()== 1 || $("#mobile").val().length != 11){
			$("#message").html("请输入正确的电话号码");
			$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
			$("#msg").fadeIn("slow");
			timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
			return false;
		}
	}
	return true;
}
//学生提交时，检查
function checkStuSub(obj){
	var nikeName=document.getElementById("nikeName");
	if(!nikeName.value){
		$("#message").html("请填写姓名");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;	}
	//	else if($("#mobile").val()=="" ){
//		$("#message").html("请输入电话号码");
//		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"showDiv('msg')\"/>");
//		$("#msg").fadeIn("slow");
//		setTimeout("hiddeDiv(\"msg\")",5000);
//		return false;
//	}else if($("#mobile").val()=="" || $("#cu").val()== 1 || $("#mobile").val().length != 11){
//		$("#message").html("请输入正确的电话号码");
//		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"showDiv('msg')\"/>");
//		$("#msg").fadeIn("slow");
//		setTimeout("hiddeDiv(\"msg\")",5000);
//		return false;
//	}
	return true;
}
var timeoutId;
//父母新增提交时，检查
function checkParSub(obj){
	var nikeName=document.getElementById("nikeName");
	if($("#mobile").val()=="" ){
		$("#message").html("请输入电话号码");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId= setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#mobile").val()=="" || $("#mobile").val().length != 11){
		$("#message").html("请输入正确的电话号码");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#relationship").val()=="" ){
		$("#message").html("请输入关系");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if(!nikeName.value){
		$("#message").html("请填写姓名");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#rid").val()== 3  ){
		$("#message").html("此用户为学生，不能添加");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#isAddTo").val()=="true"){
		$("#message").html("不允许添加");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}
	return true;
}
//关闭定时器
function clearTimeouts(){
	if(timeoutId){
		clearTimeout(timeoutId);
	}
}
//修改个人信息时，检查
function checkSelfSub(obj){
	var nikeName=document.getElementById("nikeName");
	if(!nikeName.value){
		$("#message").html("请填写姓名");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#mobile").val()==""){
		$("#message").html("请输入电话号码");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#mobile").val()=="" || $("#cu").val()== 1 || $("#mobile").val().length != 11){
		$("#message").html("请输入正确的电话号码");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}else if($("#password").val()!= $("#password1").val()){
		$("#message").html("两次密码不相同");
		$("#message-button").html("<input value=\"确定\" type=\"button\" onclick=\"hiddeDiv('msg');clearTimeouts()\"/>");
		$("#msg").fadeIn("slow");
		timeoutId=setTimeout("hiddeDiv(\"msg\")",5000);
		return false;
	}
	return true;
}

// 领导成绩分析，年级中的各班排名
function leadAnalysis() {
	var str = "";
	$.post("scores_leadAnalysis.html", {
		"gid" : $("#gid").val(),
		"start" : $("#start").val(),
		"end" : $("#end").val(),
		"datas" : Math.random()
	}, function(data) {
		var es = data.split("+");
		var examList = es[1];

		str = "<div  class='list_items font1 bg' id='title'>"
				+ "<div  class='list_id'>编号</div>"
				+ "<div  class='list_title'>考试名称</div>"
				+ "<div  class='list_time_date'>考试时间</div>"
		var clazz = es[0].split("_");
		for ( var i = 0; i < clazz.length / 2 - 1; i++) {
			str += "<div  class='list_id'>" + clazz[2 * i + 1] + "</div>";
		}
		str += "</div>";
		var examList = examList.split(",");
		for ( var i = 0; i < examList.length - 1; i++) {
			var mix = examList[i];
			var mix = mix.split("_");
			str += "<div class='list_items'>" + "<div class='list_id'>"
					+ (i + 1) + "</div>" + "<div class='list_title'>" + mix[0]
					+ "</div>" + "<div class='list_time_date'>"
					+ mix[1].substring(0, 10) + "</div>";
			for ( var j = 2; j < mix.length - 1; j++) {
				str += "<div class='list_id'>" + mix[j] + "</div>";
			}
			str += "</div>";
		}
		$("#content").empty();
		$("#content").append(str);
	});
	$("#title").css("display", "block");
}
// 班级和学生的联动
function changeUserByClazz() {
	$.post("scores_listByCid.html", {
		"cid" : $("#cid").val(),
		"datas" : Math.random()
	}, function(data) {
		var user = document.getElementById("uid");
		user.options.length = 0;
		var option = new Option("请选择", 0);
		user.options.add(option);
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i]) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				user.options.add(option);
			}
		}
	});
}
// 年级和科目的联动
function changeSubjectByGrade() {
	$.post("scores_listSubjectByGrade.html", {
		"gid" : $("#gradeId").val(),
		"datas" : Math.random()
	}, function(data) {
		var subject = document.getElementById("sid");
		subject.options.length = 0;
		var option = new Option("请选择", 0);
		subject.options.add(option);
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i]) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				subject.options.add(option);
			}
		}
	});
}
// 全选/全不选
function checkall() {
	$("#checkAllMessage").click(function() {
		$(".message_checkbox").each(function(index, domEle) {
			if ($("#checkAllMessage").attr("checked")) {
				$(domEle).attr("checked", true);
			} else {
				$(domEle).attr("checked", false);
			}
		});
	});
}
// 删除消息
function deleteMessage() {
	// 判断是否选择
	var isCheck = false;
	var mids = "";
	$(".message_checkbox").each(function(index, domEle) {
		if ($(domEle).attr("checked")) {
			mids += "-" + $(domEle).val();
			isCheck = true;
		}
	});
	if (!isCheck) {
		show("message_notice");
		$("#message_notice").text("未选择任何信息");
		timeoutId=setTimeout("hiddeDiv(\"message_notice\")", 5000);
	} else {
		// alert("message_delete-"+$("#checkAllMessage").val()+mids+".html");
		to("message_delete-" + $("#checkAllMessage").val() + mids + ".html");
	}
}
// 恢复消息
function recoveryMessage() {
	// 判断是否选择
	var isCheck = false;
	var mids = "";
	$(".message_checkbox").each(function(index, domEle) {
		if ($(domEle).attr("checked")) {
			mids += "-" + $(domEle).val();
			isCheck = true;
		}
	});
	if (!isCheck) {
		show("message_notice");
		$("#message_notice").text("未选择任何信息");
		timeoutId=setTimeout("hiddeDiv(\"message_notice\")", 5000);
	} else {
		// alert("message_delete-"+$("#checkAllMessage").val()+mids+".html");
		to("message_recovery" + mids + ".html");
	}
}
// 查询是否有新消息
function haveMessage() {

	$
			.ajax( {
				url : '/web/message/message_have.html',
				type : 'GET',
				dataType : 'xml',
				timeout : 1000,
				error : function() {
					$("#newMessage_count").html('0');
					$('#newMessage').html("暂无未读信息");
				},
				success : function(data) {
					var id = "";
					var title = "";
					var nikeName = "";
					var mobile = "";
					var sendTime = "";
					var count=0;
					$(data)
							.find("rightdiv")
							.find("message")
							.each(
									function(index) {
										id = $(this).find("id").text();
										title = $(this).find("title").text();
										nikeName = $(this).find("nikeName")
												.text();
										mobile = $(this).find("mobile").text();
										sendTime = $(this).find("sendTime")
												.text();
										$(
												'<div class="birthday-div-body"><div title="号码：'
														+ mobile
														+ '&nbsp;&nbsp;发送时间：'
														+ sendTime
														+ '"><div><a href="/web/message/message_detail-'
														+ id + '.html">'
														+ title
														+ '</a></div><div>发件人：'
														+ nikeName
														+ '</div></div></div>')
												.appendTo('#newMessage');
										count++;
									});
					$("#newMessage_count").html(count);
				}
			});
}


//考试成绩管理，总分
function sumScores(o){		
	var sum = 0;
	$("."+o.className).each(function(index, domEle) {
		sum += Number($(domEle).val());
	});
	$("#"+o.className+"_sum").val(sum);
}
//excel，成绩导出   examManager.jsp
function makeExcel(eid){
	$("#show_div_mask").css("display","block");
	$("#subjects_show_check").css("display","block");
    makeExcelSubject(eid);
}
//导出成绩
function makeExcelSubject(eid){
	$("#subjects_div_ok").attr("alt",eid);
	var str = "";
	$("#subjects").empty();
	$.post("scores_listSubjectExcel.html", {
		"eid" : eid,
		"datas" : Math.random()
	}, function(data) {
		var subject = data.split(",");
		$("#checks").html("<input type='checkbox' id='checkAllMessage' value=''/>全选/全不选");
		for(var i=0;i<subject.length-1;i++){
			var subject_item = subject[i].split(":");
			str += "<div class='subjects_item' id='"+subject_item[0]+"'>"+subject_item[1]+"</div>";					
		}
		$("#subjects").append(str);			
		 $(".subjects_item").each(function(index, domEle) {
	        	$(domEle).click(function(){
	        		if($(domEle).attr("class")=="subjects_item"){
		        		$(domEle).attr("class","subjects_item_checked");
	        		}else{
	        			$(domEle).attr("class","subjects_item");
	        		}
            	});
			});
		 $("#checkAllMessage").click(function(){
			 if($("#checkAllMessage").attr("checked")){
				 $(".subjects_item").each(function(index, domEle) {
					 $(domEle).attr("class","subjects_item_checked");
				 });
			 }else{
				 $(".subjects_item_checked").each(function(index, domEle) {
					 $(domEle).attr("class","subjects_item");
				 });
			 }
		 });
	});
}

//学生和科目的联动
function changeSubjectByUser() {
	$.post("scores_listSubjectByUser.html", {
		"uid" : $("#uid").val(),
		"datas" : Math.random()
	}, function(data) {
		var subject = document.getElementById("sid");
		subject.options.length = 0;
		var option = new Option("请选择", 0);
		subject.options.add(option);
		var cs = data.split(",");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i]) {
				var c = cs[i].split(":");
				var option = new Option(c[1], c[0]);
				subject.options.add(option);
			}
		}
	});
}

