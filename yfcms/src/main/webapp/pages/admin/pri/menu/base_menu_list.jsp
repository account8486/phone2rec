<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wondertek.meeting.model.MeetingFiles"%>
<%@ include file="../../../../pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统基础菜单列表</title> 
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
		$("#queryForList").click (function() {$('#queryFrm').submit();});
	});
	</script>	
</head>
<body>
	<div class=page_title><h3>菜单管理 -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="会议菜单" style="padding:10px;">
			<!-- 查询FORM -->
			<form id="queryFrm"
				action="${ctx}/admin/pri/basemenu/getBaseMenuPages.action">
				<input type="hidden" id="totalPage" name="totalPage"
					value="${pager.pageCount}" /> <input type="hidden"
					name="currentPage" id="currentPage" value="${pager.currentPage}" />
				<input type="hidden" name="meetingId" id="meetingId"
					value="${meetingId}" />

				<div class="page_tools page_toolbar" style="text-align: left;">
					菜单名称：<input type="text" id="name" name="name" value="${name}">
					菜单描述：<input type="text" id="description" name="description"
						value="${description}"> 访问类型：
					<wd:select name="terminalType" type="terminal_type"
						nullLabel="--全部--" nullOption="true"
						selectedValue="${terminalType}"></wd:select>

					<a id="queryForList" class="btn_common btn_true" href="##">查询</a> <a id="resetByOneKey"
						href="${ctx}/admin/pri/basemenu/resetMenuByOneKey.action?meetingId=${meetingId}"
						class="btn_common btn_true">初始化菜单</a>
				</div>
			</form>
			<!-- 显示内容TABLE -->
			<table class="page_datalist">
				<thead>
					<tr>
						<th width="2px" align="center" style="border-right: 0px"></th>
						<th>菜单名称</th>
						<th>菜单描述</th>
						<th>访问类型</th>
						<th>修改时间</th>
						<th width="120px">操作</th>
					</tr>
				</thead>

				<tbody>
					<c:choose>
						<c:when test="${not empty pager.pageRecords}">
							<c:forEach var="clientMenu" items="${pager.pageRecords}"
								varStatus="status">
								<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
									<td align="left"></td>
									<td align="left">${clientMenu.name }</td>
									<td align="left">${clientMenu.description}</td>
									<td align="left">
									<wd:translate type="terminal_type" value="${clientMenu.terminalType}"></wd:translate>
									</td>
									<td align="left"><fmt:formatDate
											value="${clientMenu.modifyTime}" type="both"
											pattern="MM月d日 HH:mm" /></td>
									<td align="center"><a class="pre" href="${ctx}/admin/pri/basemenu/getBaseMenuById.action?id=${clientMenu.id}">编辑</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="datarow">
								<td colspan="10" align="center">无菜单</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div><%@ include file="/pages/common/page.jsp"%></div>
		</div>
		<div title="自定义菜单" link="${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=3&meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="菜单配置" link="${ctx}/pages/admin/pri/menu/member_level_list.jsp?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="菜单访问统计" link="${ctx}/admin/pri/statistics/menuStatics.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
</body>
</html>