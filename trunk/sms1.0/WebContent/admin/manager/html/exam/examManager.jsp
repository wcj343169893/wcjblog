<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set scope="request" var="style" value="css/perm_module,css/tabs" />
<c:set scope="request" var="script" value="js/common,js/jquery,js/jqueryEventDrag,js/exam" />
<c:set scope="request" var="meta_title" value="考试列表"></c:set>
<jsp:include page="/admin/manager/commons/inc_commons.jsp"></jsp:include>
<script type="text/javascript">
	function deleteEnter(o,id){
		if (confirm("您确定要删除此次考试成绩吗？删除后无法恢复!")){
			o.href="scores_delete-"+id+"-${page}.html";
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
    		<c:if test="${logo==0}"><h1 class="font1">考试列表(全部考试)</h1></c:if>
    		<c:if test="${logo==1}"><h1 class="font1">考试列表(未录入成绩的考试)</h1></c:if>
    		<font class="font3">成绩录入:</font>此次考试，本班成绩的录入<br />
    		<font class="font3">科目:</font>科目为红色，表示成绩未发送,点击即可发送
    	</div>
    	<div class="tabs_150_context_right"></div>
    </div>
    <!-- 列名  -->
	<div class="list_items font1 bg">	
		<div class="list_id">编号</div>	
		<div class="list_title">考试名称</div>
		<div class="list_time">考试时间</div>
		<div class="list_dlong">科目</div>		
		<div class="list_operate">操作</div>
	</div>	
 	<!-- 数据  -->
	<c:forEach items="${examList}" var="exam" varStatus="vs">
		<div class="list_items">
		<form action="exam_update.html" method="post">
			<div class="list_id">${vs.count }<input type="hidden" name="id" id="id" value="${exam.id}" /></div>
			<div class="list_title">${exam.name}</div>
			<div class="list_time">${fn:substring(exam.examTime, 0, 10)}</div>
			<div class="list_dlong">
				<div style="text-align:left">
				<c:forEach items="${exam.subjectsList }" var="subject">
					<c:if test="${empty subject.ecsid }">
						<a href="/web/message/message_updateInit-3-0-1-${exam.id}.html" title="点击发送成绩"><font color="#FF0000">${subject.name},</font></a>
					</c:if>
					<c:if test="${!empty subject.ecsid}">
						${subject.name}
					</c:if>
				</c:forEach>
				</div>		
			</div>	
			<c:if test="${logo==0}">
				<div class="list_operate"><a href="scores_list-${exam.id}.html">录入</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="makeExcel(${exam.id});">导出</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteEnter(this,${exam.id});">删除</a></div>			
			</c:if>
			<c:if test="${logo==1}">
				<div class="list_operate"><a href="scores_list-${exam.id}.html">成绩录入</a></div>			
			</c:if>
			<input type="hidden" name="formName" value="entity.Exam" />
		</form>
		</div>
	</c:forEach>
	<c:if test="${logo==0}">
		<div class="page">
				<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
				<c:if test="${page>0}"><a href="exam_list-${page-1}.html" title="上一页">&lt;Prev</a></c:if>
				<c:if test="${page<6&&page<maxPage&&page>0}">
				<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="exam_list-${s}.html">${s+1}</a></c:forEach>
				</c:if>
				<c:if test="${page>=6&&page<maxPage}">
					<a href="exam_list-0.html">1</a>
					<a href="exam_list-1.html">2</a>...
					<a href="exam_list-${page-3}.html">${page-2}</a>
					<a href="exam_list-${page-2}.html">${page-1}</a>
					<a href="exam_list-${page-1}.html">${page}</a>
				</c:if>
				<span class="current">${page+1}</span>
				<c:if test="${page<maxPage-6&&page>=0}">
					<a href="exam_list-${page+1}.html">${page+2}</a>
					<a href="exam_list-${page+2}.html">${page+3}</a>
					<a href="exam_list-${page+3}.html">${page+4}</a>...
					<a href="exam_list-${maxPage-2}.html">${maxPage-1}</a>
					<a href="exam_list-${maxPage-1}.html">${maxPage}</a>
				</c:if>
				<c:if test="${page>=maxPage-6&&page<maxPage}">
					<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="exam_list-${s}.html">${s+1}</a></c:forEach>
				</c:if>
				<c:if test="${page<maxPage-1&&page>=0}"><a href="exam_list-${page+1}.html">Next&gt;</a></c:if>
				<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
		</div>
	</c:if>
	<c:if test="${logo==1}">
		<div class="page">
				<c:if test="${page<=0}"><span class="disabled">&lt;Prev</span></c:if>
				<c:if test="${page>0}"><a href="exam_listOther-${page-1}.html" title="上一页">&lt;Prev</a></c:if>
				<c:if test="${page<6&&page<maxPage&&page>0}">
				<c:forEach begin="0" end="${page<maxPage?page-1:maxPage-1}" var="s"><a href="exam_listOther-${s}.html">${s+1}</a></c:forEach>
				</c:if>
				<c:if test="${page>=6&&page<maxPage}">
					<a href="exam_listOther-0.html">1</a>
					<a href="exam_listOther-1.html">2</a>...
					<a href="exam_listOther-${page-3}.html">${page-2}</a>
					<a href="exam_listOther-${page-2}.html">${page-1}</a>
					<a href="exam_listOther-${page-1}.html">${page}</a>
				</c:if>
				<span class="current">${page+1}</span>
				<c:if test="${page<maxPage-6&&page>=0}">
					<a href="exam_listOther-${page+1}.html">${page+2}</a>
					<a href="exam_listOther-${page+2}.html">${page+3}</a>
					<a href="exam_listOther-${page+3}.html">${page+4}</a>...
					<a href="exam_listOther-${maxPage-2}.html">${maxPage-1}</a>
					<a href="exam_listOther-${maxPage-1}.html">${maxPage}</a>
				</c:if>
				<c:if test="${page>=maxPage-6&&page<maxPage}">
					<c:forEach begin="${page+1}" end="${maxPage-1}" var="s"><a href="exam_listOther-${s}.html">${s+1}</a></c:forEach>
				</c:if>
				<c:if test="${page<maxPage-1&&page>=0}"><a href="exam_listOther-${page+1}.html">Next&gt;</a></c:if>
				<c:if test="${page>=maxPage-1}"><span class="disabled">Next&gt;</span></c:if>
		</div>
	</c:if>
	<div class="list_bottom_query">
	</div>
</div>
<!-- 遮罩 -->
<div id="show_div_mask">
</div>
<!--显示内容层--> 
<div id="subjects_show_check">
	<div class="subjects_show_check_title bg">请选择下列科目
		
		<div class="subjects_show_check_tool bg">
			<input type="button" value="确定" title="确定" id="subjects_div_ok" alt="0">
			<input type="button" value="取消" title="取消" id="subjects_div_cancel">
		</div>
	</div>
	<div class="subjects_show_check_title">
	<span id="checks"></span>
	<input type="radio" name="way" value="0" checked="checked" onclick="checkRadio(this)"/>本班
	<input type="radio" name="way" value="1" onclick="checkRadio(this)"/>本年级
	<input type="hidden" id="way" value="0"/>
	</div>
	<div id="subjects">
	</div>
</div>
