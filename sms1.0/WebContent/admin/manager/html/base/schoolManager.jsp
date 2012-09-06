<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set scope="request" var="style" value="css/perm_module,css/tabs,css/editor,css/notice_update" />
<c:set scope="request" var="script" value="js/common,js/jquery132,DatePicker/WdatePicker,js/message,js/jquery.cookie,js/jquery.treeview,kindeditor/kindeditor" />
<c:set scope="request" var="meta_title" value="学校管理"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,id){
		if (confirm("您确定要删除此学校吗？删除后无法恢复!")){
			o.href="school_delete-"+id+".html";
			return true;
		}else{
			//o.href="#";
			return false;
		}
	}
</script>
<div class="context_right_all">
	<div class="tabs_150_context">
    	<div class="tabs_150_context_left"></div>
    	<div class="tabs_150_context_center">
    		<h1 class="font1">学校管理</h1>
    		<font class="font3">添加学校:</font>	如您需要添加一个学校,你可以点击"添加"按钮,然后输入相关信息保存<br>
    		<font class="font3">取消添加或修改:</font>点击取消即可<br>
   			<input type="button" value="添加" onclick="slideDown('school_info')">
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    	<div class="school_info" id="school_info">
    		<form action="school_add.html" method="post" name="school_form">
				
				<div class="update_message_items">
			    	<div class="update_item_name">学校名称:</div>
			    	<div class="update_item_input"><input type="text" name="name" value="${school.name}"/></div>
			    	<div class="update_item_remarks">&nbsp;</div>
			   	 </div>
				<div class="update_message_items">
			    	<div class="update_item_name">学校主页:</div>
			    	<div class="update_item_input"><input type="text" name="url" value="${school.url}"/></div>
			    	<div class="update_item_remarks">&nbsp;</div>
			   	 </div>
				<div class="update_message_items">
			    	<div class="update_item_name">学校简介:</div>
			    	<div class="update_item_input">
			    		<div id="content-div"></div>
			    		<textarea style="width:700px;height:300px;visibility:hidden;" id="content" name="description">
			    		${school.description}
			    		</textarea>
			    	</div>
			    	<div class="update_item_remarks">&nbsp;</div>
			   	 </div>
			   	 <div class="update_message_items">
			    	<div class="update_item_name">&nbsp;</div>
			    	<div class="update_item_input">
						<input type="submit" value="保存">
						<input type="button" value="取消" onclick="slideUp('school_info')">(提交快捷键: Ctrl + Enter)
					</div>
			    	<div class="update_item_remarks">&nbsp;</div>
			   	 </div>
			   	 <input type="hidden" name="formName" value="entity.School" />
			   	 <input type="hidden" name="id" value="${school.id}" />
    		</form>
    	</div>
    	<c:choose>
    		<c:when test="${!empty school }">
		    	<script type="text/javascript">
				    $(document).ready(function() {
				    	slideDown('school_info');
				    	showKE();
				    });
		   	 </script>
    		</c:when>
    		<c:otherwise>
		    	<script type="text/javascript">
				    $(document).ready(function() {
				    	showKE();
				    });
		   	 	</script>
    		</c:otherwise>
    	</c:choose>
    <!-- 列名  -->
	<div class="list_items font1 bg">
		<div class="list_id">编号</div>
		<div class="list_title">学校名称</div>
		<div class="list_content">学校简介</div>
		<div class="list_path">学校主页</div>
		<div class="list_time">创建时间</div>
		<div class="list_operate">操作</div>
	</div>	
 	<!-- 数据  -->
	<c:forEach items="${schoolList}" var="school" varStatus="vs">
		<div class="list_items">
			<div class="list_id">${vs.count}</div>
			<div class="list_title">${school.name}</div>
			<div class="list_content">
				<c:out value="${school.introduction}" escapeXml="true"></c:out>
			</div>
			<div class="list_path">${school.url}</div>
			<div class="list_time">${school.createTime}</div>
			<div class="list_operate">
				<input type="button" value="修改" onclick="to('school_update-${school.id}.html')"/>&nbsp;
				<a href="javascript:void(0);" onclick="deleteEnter(this,${school.id});">删除</a></div>
		</div>
	</c:forEach>
	<!-- 添加项（单独的form）
		<div class="list_items">
			<form action="school_add.html" method="post">
				<div class="list_id">&nbsp;</div>
				<div class="list_title"><input name="name" value="" /></div>
				<div class="list_content"><input name="description" value="" /></div>
				<div class="list_path"><input name="url" value="" /></div>
				<div class="list_time"></div>
				<div class="list_operate"><input type="submit" value="添加" />&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<input type="hidden" name="formName" value="entity.School" />
			</form>
		</div>
	 -->
	<div class="page">
		<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
		<c:if test="${page>0}"><a href="school_list-${page-1}.html" title="上一页">&lt;Prev</a></c:if>
		<c:if test="${page<6&&page<maxPage&&page>0}">
			<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="school_list-${s}.html">${s+1}</a></c:forEach>
			</c:if>
			<c:if test="${page>=6&&page<maxPage}">
				<a href="school_list-0.html">1</a>
				<a href="school_list-1.html">2</a>...
				<a href="school_list-${page-3}.html">${page-2}</a>
				<a href="school_list-${page-2}.html">${page-1}</a>
				<a href="school_list-${page-1}.html">${page}</a>
			</c:if>
			<span class="current">${page+1}</span>
			<c:if test="${page<maxPage-6&&page>=0}">
				<a href="school_list-${page+1}.html">${page+2}</a>
				<a href="school_list-${page+2}.html">${page+3}</a>
				<a href="school_list-${page+3}.html">${page+4}</a>...
				<a href="school_list-${maxPage-2}.html">${maxPage-1}</a>
				<a href="school_list-${maxPage-1}.html">${maxPage}</a>
			</c:if>
			<c:if test="${page>=maxPage-6&&page<maxPage}">
				<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="school_list-${s}.html">${s+1}</a></c:forEach>
			</c:if>
			<c:if test="${page<maxPage-1&&page>=0}"><a href="school_list-${page+1}.html">Next&gt;</a></c:if>
			<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
		</div>
	<div class="list_bottom_query">
	</div>

</div>
