<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>任务管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript" src="${ctx}/js/ajax-public.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".easyui-tabs").tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs("getTab", title); 
					var href = tab.attr("link");
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});
			$("#all_check").change(function () {
				if (this.checked) {
					$("[name='userId']").attr("checked", $("#all_check").attr("checked"));
				} else {
					$("[name='userId']").removeAttr("checked");
				}
			});
        
			//有一个不选上则全不选
			$('input[type="checkbox"][name="userId"]').click(function () {
				   var ckall = true;
				   $('input[type="checkbox"][name="userId"]').each(function (){
					if (!this.checked){ 
						ckall = false;  
						//直接退出循环,不在进行each循环
						return false; 
						}});
				   
				   $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		   });			
		});
    </script>
</head>

<body>

<div class="page_title"><h3>任务管理  -- ${CURRENT_MEETING}</h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	

	    <div title="任务列表" style="padding:10px;">
	    
		<div class="page_tools">
		<form id="listUserForm" action="${ctx}/admin/pri/task/getTaskListPager.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="80%">
				<tr>
				<th style="width: 80px; ">任务名称：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px; " id="taskName" name="taskName" value="${taskName}"/></td>
					
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
				
				</td>
				</tr>
			</table>
		</form>
		</div>
	
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="3%" style="border-right: 0"><input type="checkbox" name="all_check" id="all_check"></input>  </th>
				<th width="20%">名称</th>
				<th width="20%">描述 </th>
				
				<th width="20%">创建时间</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="task" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
							<td><input type="checkbox" name="spokesmanId" value="${task.id}"></input></td>
							<td>${task.taskName}</td>
							<td>${task.taskDescription}</td>
					
							<td> 
							${fn:substring(task.createTime,0,16)}
							</td>
							<td>
								<a href="##" onclick="edit(${task.id})">编辑</a>
								&nbsp;
								<a href="##" onclick="del(${task.id})">删除</a>
									&nbsp;
								<a href="${ctx}/admin/pri/task/getDetailListPager.action?taskId=${task.id}&meetingId=${meetingId}">管理任务明细</a>
								
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无任务信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
	</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>
	<div title="添加任务" link="${ctx}/admin/pri/task/toAddTask.action?meetingId=${meetingId}" style="padding:10px;"></div>
	
</div>



<script type="text/javascript">
	function del(id)
	{
		if(confirm("您确认进行删除操作？"))
		{
			
			var url  = "/admin/pri/task/deleteTask.action?id="+id;
			ajaxRequest(url,finishDel,false);
			location.reload();
		}
	}
	
	function finishDel(data){
		alert(data.retMsg);
	}
	
	function edit(id){
		//alert(id);
		window.location.href = "${ctx}/admin/pri/task/toUpdateTask.action?id="+id;
	}
	
    function forwardReq(pagePath) {
        window.location.href = pagePath;
    }

    function query() {
        $('#listUserForm').submit();
    }
    
 
</script>


</body>
</html>

