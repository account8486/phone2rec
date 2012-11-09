<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.wondertek.meeting.model.MeetingFiles" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请加入会议管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js} ${admin_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
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
		});
	</script>
</head>
<body>    
    <div class=page_title><h3>参会人员管理  -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="用户列表" link="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=${meetingId}&isAdmin=1" style="padding:10px;"></div>
		<div title="批量添加" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=batch_add_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
		<div title="添加用户" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_members&meeting.id=${meetingId}" style="padding:10px;"></div>
		<div title="导入用户" link="${ctx}/admin/pri/import/preImportUser.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="临时加入审批" selected="true" style="padding:10px;">
	        <!-- 显示内容TABLE -->
            <table class=page_datalist>
                <thead>
                <tr>          
                     <th align="center">用户姓名</th>
                     <th>电话号码</th>
                     <th>邮箱</th>
                     <th>城市</th>
                     <th>性别</th>
                     <th>审核状态</th>
                     <th>申请时间</th>
                    <th>操作</th>
                </tr>
                </thead>
               
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="userApply" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
                                <td align="left">${userApply.name}</td>                             
                                <td align="left">${userApply.mobile}</td>
                                <td align="left">${userApply.mailbox}</td>
                                <td align="left">${userApply.city}</td>
                                <td align="left">${userApply.gender eq 0 ?'男':'女'}</td>
                                <td align="left">${userApply.state eq 1 ?'审核成功':'未审核'} </td>
                                <td align="left">
                                <fmt:formatDate value="${userApply.createTime}" type="both"  pattern="MM月d日 HH:mm"/>
                                </td>
                                 <td align="left">
                                 <a href="${ctx}/admin/pri/apply/getEditUserApplly.action?id=${userApply.id}">审核</a>
                                 <a href="${ctx}/admin/pri/apply/deleteUserApply.action?id=${userApply.id}">删除</a>
                                 </td>      
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                        	 无申请用户
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
			<%@ include file="/pages/common/page.jsp" %>
		</div>	
		<div title="发言人管理" link="${ctx}/admin/pri/spokesman/getSpokesManLst.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="嘉宾管理" link="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
</body>
</html>