<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.choujone.blog.entity.User"%>
<%@page import="java.util.List"%>
<%@page import="com.google.choujone.blog.util.Config"%>
<%@page import="com.google.choujone.blog.entity.Menu"%>
<%@page import="com.google.choujone.blog.dao.UserDao"%>
<%@page import="com.google.choujone.blog.util.Tools"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	UserDao ud=new UserDao();
	User user=  ud.getUserDetail();
%>
<title><%=user.getpTitle() %> -- 博客设置   / 系统设置</title>
<script type="text/javascript" charset="utf-8" src="/kindeditor/kindeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/ui-lightness/jquery-ui.css" />
<script>
	KE.show({
		id : 'preMessage',
		resizeMode : 1,
		imageUploadJson : "/kindeditor/jsp/upload.jsp",
		fileManagerJson : '/kindeditor/jsp/file_manager.jsp',
		allowFileManager : true,
		allowPreviewEmoticons : true,
		items : [
		'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
		'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
		'insertunorderedlist', '|', 'emoticons', 'image', 'link']
	});
	
</script>
</head>
<body>
<div class="main">
	<jsp:include page="/admin/menu.jsp"></jsp:include>	
	<script type="text/javascript" charset="utf-8" src="/js/jquery-ui.js"></script>
	<div class="address">
			博客设置   / 系统设置
	</div>
	<div id="container">
		<form action="/user" method="post" onsubmit="make_m()">
			<input type="hidden" name="op" value="modify">
			<div>博客设置</div>
			<table cellpadding="0" cellspacing="0" class="setting">
				<tr>
					<td class="title">网站状态</td>
					<td>
						<input type="radio" name="closeweb" value="0" <%=user.getCloseweb()==null ||user.getCloseweb().equals(0) ?"checked":"" %> checked="checked" id="isCloseN" style="width: 20px;"><label for="isCloseN">开启</label>
						<input type="radio" name="closeweb" value="1" <%=user.getCloseweb()!=null&&user.getCloseweb().equals(1) ?"checked":"" %>  id="isCloseY" style="width: 20px;"><label for="isCloseY">关闭</label>
					</td>
				</tr>
				<tr>
					<td class="title">博客标题</td>
					<td>
						<input type="text" name="pTitle" value="<%=user.getpTitle() %>">
					</td>
				</tr>
				<tr>
					<td class="title">博客子标题</td>
					<td>
						<input type="text" name="ctitle" value="<%=user.getCtitle() %>"><br>
						关于这个博客的简单介绍
					</td>
				</tr>
				<tr>
					<td class="title">博客访问地址</td>
					<td>
						<input type="text" name="url" value="<%=user.getUrl() %>">
					</td>
				</tr>
				<tr>
					<td class="title">博客风格</td>
					<td>
						<%List<String> styleList=Config.getStyle_urls(); 
						%>
						<select name="style">
						<%for(String s :styleList){ %>
							<option value="<%=s %>" <% if(s.equals(user.getStyle())){out.print("selected");} %>><%=s %></option>
						<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">公告</td>
					<td>
						<textarea rows="10" cols="70" name="notice"><%=user.getNotice() %></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">电子邮件</td>
					<td>
						<input type="text" name="email" value="<%=user.getEmail() %>">
					</td>
				</tr>
				<tr>
					<td class="title">生日</td>
					<td>
						<input type="text" name="brithday" value="<%=user.getBrithday() %>">
					</td>
				</tr>
				<tr>
					<td class="title">地址</td>
					<td>
						<input type="text" name="address" value="<%=user.getAddress() %>">
					</td>
				</tr>
				<tr>
					<td class="title">自我描述</td>
					<td>
						<textarea rows="10" cols="70" name="description"><%=user.getDescription() %></textarea>
					</td>
				</tr>
			</table>
			<br>
			<table class="preMessage">
				<tr>
					<td class="title">留言寄语
					</td>
					<td class="message"><textarea rows="10" cols="70" name="preMessage" id="preMessage"><%=user.getPreMessage().getValue() %></textarea>
					</td>
				</tr>
			</table>
			<br>
			<div>博客系统设置</div>
			<table cellpadding="0" cellspacing="0" class="blogsetting">
				<tr>
					<td class="title">
						上传图片
					</td>
					<td>
						<input type="radio" name="isUpload" value="0" id="isUploadYes" <%=user.getIsUpload()==null || user.getIsUpload()==0?"checked":"" %>><label for="isUploadYes">允许</label>
						<input type="radio" name="isUpload" value="1" id="isUploadNo" <%=user.getIsUpload()!=null && user.getIsUpload()==1?"checked":"" %>><label for="isUploadNo">禁止</label>
					</td>
					<td class="title">
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td class="title">
						天气
					</td>
					<td>
						<input type="radio" name="isWeather" value="0" id="isWeatherYes" <%=user.getIsWeather()==null || user.getIsWeather()==0?"checked":"" %>><label for="isWeatherYes">显示</label>
						<input type="radio" name="isWeather" value="1" id="isWeatherNo" <%=user.getIsWeather()!=null && user.getIsWeather()==1?"checked":"" %>><label for="isWeatherNo">隐藏</label>
					</td>
					<td class="title">
						日历
					</td>
					<td>
						<input type="radio" name="isCalendars" value="0" id="isCalendarsYes" <%=user.getIsCalendars()==null || user.getIsCalendars()==0?"checked":"" %>><label for="isCalendarsYes">显示</label>
						<input type="radio" name="isCalendars" value="1" id="isCalendarsNo" <%=user.getIsCalendars()!=null && user.getIsCalendars()==1?"checked":"" %>><label for="isCalendarsNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						热门文章
					</td>
					<td>
						<input type="radio" name="isHotBlog" value="0" id="isHotBlogYes" <%=user.getIsHotBlog()==null || user.getIsHotBlog()==0?"checked":"" %>><label for="isHotBlogYes">显示</label>
						<input type="radio" name="isHotBlog" value="1" id="isHotBlogNo" <%=user.getIsHotBlog()!=null && user.getIsHotBlog()==1?"checked":"" %>><label for="isHotBlogNo">隐藏</label>
					</td>
					<td class="title">
						最新评论
					</td>
					<td>
						<input type="radio" name="isNewReply" value="0" id="isNewReplyYes" <%=user.getIsNewReply()==null || user.getIsNewReply()==0?"checked":"" %>><label for="isNewReplyYes">显示</label>
						<input type="radio" name="isNewReply" value="1" id="isNewReplyNo" <%=user.getIsNewReply()!=null && user.getIsNewReply()==1?"checked":"" %>><label for="isNewReplyNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						留言
					</td>
					<td>
						<input type="radio" name="isLeaveMessage" value="0" id="isLeaveMessageYes" <%=user.getIsLeaveMessage()==null || user.getIsLeaveMessage()==0?"checked":"" %>><label for="isLeaveMessageYes">显示</label>
						<input type="radio" name="isLeaveMessage" value="1" id="isLeaveMessageNo" <%=user.getIsLeaveMessage()!=null && user.getIsLeaveMessage()==1?"checked":"" %>><label for="isLeaveMessageNo">隐藏</label>
					</td>
					<td class="title">
						统计
					</td>
					<td>
						<input type="radio" name="isStatistics" value="0" id="isStatisticsYes" <%=user.getIsStatistics()==null || user.getIsStatistics()==0?"checked":"" %>><label for="isStatisticsYes">显示</label>
						<input type="radio" name="isStatistics" value="1" id="isStatisticsNo" <%=user.getIsStatistics()!=null && user.getIsStatistics()==1?"checked":"" %>><label for="isStatisticsNo">隐藏</label>
						<a href="javascript:void(0)" id="ip_btn" class="spider_btn ui-state-default ui-corner-all"><span class="ui-icon ui-icon-newwin"></span>ip地址</a>
					</td>
				</tr>
				<tr>
					<td class="title">
						友情链接
					</td>
					<td>
						<input type="radio" name="isFriends" value="0" id="isFriendsYes" <%=user.getIsFriends()==null || user.getIsFriends()==0?"checked":"" %>><label for="isFriendsYes">显示</label>
						<input type="radio" name="isFriends" value="1" id="isFriendsNo" <%=user.getIsFriends()!=null && user.getIsFriends()==1?"checked":"" %>><label for="isFriendsNo">隐藏</label>
					</td>
					<td class="title">
						个人资料
					</td>
					<td>
						<input type="radio" name="isInfo" value="0" id="isInfoYes" <%=user.getIsInfo()==null || user.getIsInfo()==0?"checked":"" %>><label for="isInfoYes">显示</label>
						<input type="radio" name="isInfo" value="1" id="isInfoNo" <%=user.getIsInfo()!=null && user.getIsInfo()==1?"checked":"" %>><label for="isInfoNo">隐藏</label>
						<a href="javascript:void(0)" id="use_avatar" class="spider_btn ui-state-default ui-corner-all"><span class="ui-icon ui-icon-newwin"></span>编辑头像</a>
					</td>
				</tr>
				<tr>
					<td class="title">
						TAGS
					</td>
					<td>
						<input type="radio" name="isTags" value="0" id="isTagsYes" <%=user.getIsTags()==null || user.getIsTags()==0?"checked":"" %>><label for="isTagsYes">显示</label>
						<input type="radio" name="isTags" value="1" id="isTagsNo" <%=user.getIsTags()!=null && user.getIsTags()==1?"checked":"" %>><label for="isTagsNo">隐藏</label>
					</td>
					<td class="title">
						文章分类
					</td>
					<td>
						<input type="radio" name="isType" value="0" id="isTypeYes" <%=user.getIsType()==null || user.getIsType()==0?"checked":"" %>><label for="isTypeYes">显示</label>
						<input type="radio" name="isType" value="1" id="isTypeNo" <%=user.getIsType()!=null && user.getIsType()==1?"checked":"" %>><label for="isTypeNo">隐藏</label>
					</td>
				</tr>
				<tr>
					<td class="title">
						网站导航
					</td>
					<td colspan="3" id="td_menu">
						<input value="<%=user.getMenu()!=null?user.getMenu():"" %>" name="blogMenu" size="100" id="menus" type="hidden"/>
					<%
						List<Menu> menus=Tools.split(user.getMenu(), ";", ",");
					%>
						<input value="新增" type="button" onclick="add_m()">
						<input value="全部删除" type="button" onclick="del_m('')">
						<input value="转换" type="hidden" onclick="make_m()">
						<%
						int index=1;
						for (Menu m : menus) {%>
						<div id="menu_<%=index %>" class="d_menu">
							<input value="<%=m.getTitle() %>" class="menu_title">
							<input value="<%=m.getUrl() %>" class="menu_url">
							<input value="删除" type="button" onclick="del_m('menu_<%=index %>')">
						</div>
						<%index++;} %>
					</td>
				</tr>
				<tr>
					<td class="title">
						KEYWORDS
					</td>
					<td colspan="3">
						<input name="blogKeyword" value="<%=user.getBlogKeyword() %>">
					</td>
				</tr>
				<tr>
					<td class="title">
						DESCRIPTION
					</td>
					<td colspan="3">
						<textarea rows="10" cols="70" name="blogDescription"><%=user.getBlogDescription()%></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						顶部代码
					</td>
					<td colspan="3">
						<textarea rows="10" cols="70" name="blogHead"><%=user.getBlogHead() %></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						底部代码声明及统计
					</td>
					<td colspan="3">
						<textarea rows="10" cols="70" name="blogFoot"><%=user.getBlogFoot() %></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						评论代码
					</td>
					<td colspan="3">
						<textarea rows="10" cols="70" name="commentCode"><%=user.getCommentCode() %></textarea>
					</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<td class="title">用户名</td>
					<td>
						<input type="text" name="name" value="<%=user.getName() %>">
					</td>
				</tr>
				<tr>
					<td class="title">密码</td>
					<td>
						<input type="password" name="password" value="<%=user.getPassword() %>">
					</td>
				</tr>
			</table>
		<div class="tools-left">
			<input type="submit" value="保存">
			<input type="reset" value="重置">
		</div>
		</form>
	</div>
	<div id="ip_address" title="禁止留言ip">
		<div>
			<label for="ip_input">ip地址</label><input type="text" id="ip_input"/><input type="button" id="ip_save" value="保存">
		</div>
		<ul id="ip_address_list"></ul>
	</div>
	<script type="text/javascript">
	$(function() {
		
		$("#user_header").dialog({
			autoOpen : false,
			width : 560
			});
		$("#use_camera").click(function() {
			useCamera();
			hideLoading();
		});
		$("#use_avatar").click(function() {
			$('#user_header').dialog('open');
			$("#avatar_editor").html("");
			hideLoading();
		});
		
		ip.init();
	});
	var ip={
			dialogId:"ip_address",
			listId:"ip_address_list",
			inputId:"ip_input",
			inputSaveBtnId:"ip_save",
			inputDeleteBtnClass:"ip_delete",
			openId:"ip_btn",
			url:"/ip",
			save:function(address){
				$.ajax({  
		            url : ip.url,  
		            type : "get",  
		            data : {"address" : address,"opera":"add"},  
		            cache : false,  
		            dataType : "json",  
		            success:function(data){
		            	if(data){
		                	ip.initData();
		            	}
		            }  
		        });
			},
			init:function(){
				//绑定弹出框
				$("#"+ip.dialogId).dialog({
					autoOpen : false,
					width : 560,
					modal: true,
			        buttons: {
			        	Cancel: function() {
			                    $( this ).dialog( "close" );
			                }
			            }
				});
				//绑定点击事件
				$("#"+ip.openId).click(function() {
					$('#'+ip.dialogId).dialog('open');
				});
				//新增事件
				$("#"+ip.inputSaveBtnId).click(function() {
					var address=$("#"+ip.inputId).val();
					if(address){
						//判断是否已经增加
						$list=$("#"+ip.listId);
						var address_list=$list.data("address");
						var flag=true;
						$.each(address_list,function(index,addr){
							if(addr==address){
								flag=false;
							}
						});
						if(flag){ip.save(address);}
					}
				});
				ip.initData();
			},
			initData:function(){
				//加载数据
				$.ajax({  
                    url : ip.url,  
                    type : "get",  
                    cache : false,  
                    dataType : "json",  
                    success:function(data){
                    	if(data){
                    		$list=$("#"+ip.listId);
                    		$list.html("");
                    		$list.data("address",data);
							$.each(data,function(index,address){
								$("<li id='ip_id_"+index+"'><span>"+address+"</span></li>").append($("<a href='javascript:;'>删除</a>").click(function(){
									$.ajax({  
					                    url : ip.url,  
					                    type : "get",  
					                    data : {"id" : index,"opera":"delete"},  
					                    cache : false,  
					                    dataType : "json",  
					                    success:function(data){
					                    	if(data){
						                    	ip.initData();
					                    	}
					                    }  
					                });
								})).appendTo($list);
							});                 		
                    	}
                    }  
                });
			}
	};
		//允许上传的图片类型
		var extensions = 'jpg,jpeg,gif,png';
		//保存缩略图的地址.
		var saveUrl = '';//保存缩略图的处理地址
		//保存摄象头白摄图片的地址.
		var cameraPostUrl = '';//保存摄像头拍摄的处理地址
		//头像编辑器flash的地址.
		var editorFlaPath = '/flash/AvatarEditor.swf';
		function useCamera()
		{
			var content = '<embed height="464" width="514" ';
			content +='flashvars="type=camera';
			content +='&postUrl='+cameraPostUrl+'?&radom=1';
			content += '&saveUrl='+saveUrl+'?radom=1" ';
			content +='pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" ';
			content +='allowscriptaccess="always" quality="high" ';
			content +='src="'+editorFlaPath+'"/>';
			$("#avatar_editor").html(content);
		}
		function buildAvatarEditor(pic_id,pic_path,post_type)
		{
			var content = '<embed height="464" width="514"'; 
			content+='flashvars="type='+post_type;
			content+='&photoUrl='+pic_path;
			content+='&photoId='+pic_id;
			content+='&postUrl='+cameraPostUrl+'?&radom=1';
			content+='&saveUrl='+saveUrl+'?radom=1"';
			content+=' pluginspage="http://www.macromedia.com/go/getflashplayer"';
			content+=' type="application/x-shockwave-flash"';
			content+=' allowscriptaccess="always" quality="high" src="'+editorFlaPath+'"/>';
			$("#avatar_editor").html(content);
		}
			/**
			  * 提供给FLASH的接口 ： 没有摄像头时的回调方法
			  */
			 function noCamera(){
				 alert("俺没有camare ：）");
			 }
					
			/**
			 * 提供给FLASH的接口：编辑头像保存成功后的回调方法
			 */
			function avatarSaved(){
				alert('保存成功，哈哈');
				//window.location.href = '/';
			}
			
			 /**
			  * 提供给FLASH的接口：编辑头像保存失败的回调方法, msg 是失败信息，可以不返回给用户, 仅作调试使用.
			  */
			 function avatarError(msg){
				 alert("上传失败了呀，哈哈");
			 }

			 function checkFile()
			 {
				 var path = document.getElementById('Filedata').value;
				 var ext = getExt(path);
				 var re = new RegExp("(^|\\s|,)" + ext + "($|\\s|,)", "ig");
				  if(extensions != '' && (re.exec(extensions) == null || ext == '')) {
				 alert('对不起，只能上传jpg,jpeg,gif,png类型的图片');
				 return false;
				 }
				 showLoading();
				 return true;
			 }

			 function getExt(path) {
				return path.lastIndexOf('.') == -1 ? '' : path.substr(path.lastIndexOf('.') + 1, path.length).toLowerCase();
			}
              function	showLoading()
			  {
				  $("#loading_gif").show();
			  }
			  function hideLoading()
			  {
				 $("#loading_gif").hide();
			  }
			
	
	</script>

	<div id="user_header" title="用户头像">
		<div style="padding:10px 0;color:#666;">
		你最好上传一张真人照片证明你是地球人，也可以  
		<a href="javascript:void(0)" id="use_camera" class="spider_btn ui-state-default ui-corner-all"><span class="ui-icon ui-icon-newwin"></span>使用摄像头</a>
		</div>
		<form enctype="multipart/form-data" method="post" name="upform" target="upload_target" action="upload.jsp" onsubmit="return checkFile()">
			<input type="file" name="Filedata" id="Filedata"/>
			<input style="margin-right:20px;" type="submit" name="" value="上传形象照"  />
			<br>
			<br>
			<div style="display: none;text-align: center;" id="loading_gif"><img src="/images/loading.gif" align="middle" /></div>
		</form>
		<iframe src="about:blank" name="upload_target" style="display:none;"></iframe>
		<div id="avatar_editor"></div>
	</div>
	<jsp:include page="/admin/bottom.jsp"></jsp:include>
</div>
</body>
</html>