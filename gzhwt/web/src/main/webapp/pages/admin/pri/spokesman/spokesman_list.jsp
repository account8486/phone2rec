<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>发言人管理</title>
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

		<div class="page_title"><h3>发言人管理  -- ${CURRENT_MEETING}</h3></div>
		
		<div class="easyui-tabs" border="false" style="padding:10px;">	
			<div title="用户列表" link="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}" style="padding:10px;"></div>
			<div title="批量添加" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=batch_add_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
			<div title="添加用户" style="padding:10px;"></div>
			<div title="导入用户" link="${ctx}/admin/pri/import/preImportUser.action?meetingId=${meeting.id}" style="padding:10px;"></div>
			<div title="临时加入审批" link="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
			<div title="发言人管理" selected="true" style="padding:10px;"></div>
			<div title="嘉宾管理" link="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meeting.id}" style="padding:10px;"></div>
		</div>

	    <div title="发言人列表" style="padding:10px;">
	    
		<div class="page_tools">
		<form id="listUserForm" action="${ctx}/admin/pri/spokesman/getSpokesManLst.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="80%">
				<tr>
				<th style="width: 50px; ">姓 名：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px; " id="spokesManname" name="spokesManname" value="${spokesManname}"/></td>
				
				<th style="width: 100px; ">手机号码：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px; " id="mobile" name="mobile" value="${mobile}"/></td>
				
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
				</td>
				</tr>
			</table>
			<div class="page_tools page_toolbar">
	                 <a class="btn_common btn_false" href="${ctx}/admin/pri/spokesman/toAddSpokesman.action?meetingId=${meetingId}">添加发言人</a>
	             </div>
		</form>
		</div>
	
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="3%" style="border-right: 0"><input type="checkbox" name="all_check" id="all_check"></input>  </th>
				<th width="20%">姓名</th>
				<th width="20%">手机号码 </th>
				<th width="20%">性别</th>
				<th width="20%">创建时间</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="spokesman" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
							<td><input type="checkbox" name="spokesmanId" value="${spokesman.id}"></input></td>
							<td>${spokesman.name}</td>
							<td>${spokesman.mobile}</td>
							<td>
							<c:if test="${spokesman.gender eq 0}">男</c:if>
							<c:if test="${spokesman.gender eq 1}">女</c:if>
							
							</td>
							<td> 
							${fn:substring(spokesman.createTime,0,16)}
							</td>
							<td>
								<a href="##" onclick="edit(${spokesman.id})">编辑</a>
								&nbsp;
								<a href="##" onclick="del(${spokesman.id})">删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无用户信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
	</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>

<script type="text/javascript">
	function del(id)
	{
		if(confirm("您确认进行删除操作？"))
		{
			var url  = "/admin/pri/spokesman/deleteSpokesMan.action?id="+id;
			ajaxRequest(url,finishDel,false);
			location.reload();
		}
	}
	
	function finishDel(data){
		alert(data.retMsg);
	}
	
	function edit(id){
		window.location.href = "${ctx}/admin/pri/spokesman/toUpdateSpokesMan.action?id="+id;
	}
	
    function forwardReq(pagePath) {
        window.location.href = pagePath;
    }

    function query() {
        $('#listUserForm').submit();
    }
    
 
    function exportMeetingUser(){
    	if(confirm('确定要导出会议用户吗？')){
    		 var url="${ctx}/admin/pri/export/exportMeetingUser.action?meetingId=${meeting.id}";
        	 var name=document.getElementById("user.name").value;
        	 var mobile=document.getElementById("user.mobile").value;
        	 url=url+"&username="+name;
        	 url=url+"&mobile="+mobile;
        	 
        	 document.location.href=url;
    	} 
 }
</script>


</body>
</html>

