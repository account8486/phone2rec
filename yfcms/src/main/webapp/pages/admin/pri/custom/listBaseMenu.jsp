<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wondertek.meeting.model.MeetingFiles"%>
<%@ include file="../../../../pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统基础菜单列表</title> 
${admin_css} ${jquery_js} ${util_js} ${admin_js}
<script>
	function initMenu(meetingTypeId) {
		if(confirm("当前会议类型尚未关联基础菜单，确定为当前会议类型生成关联菜单吗？")) {
			location.href = "${ctx}/admin/pri/custom/meetingType_initMenu.action?meetingTypeId=" + meetingTypeId;
		}
	}
	
	function pageSkip(cpage) {
		$('#currentPage').val(cpage);
		$('#queryForList').click();
	}

	function query() {
		$('#queryFrm').submit();
	}

	function editBaseMenu(baseMenuId) {
		var url = "${ctx}/admin/pri/custom/meetingType_editMenuReq.action?baseMenuId=" + baseMenuId;
		document.location.href = url;
	}
</script>
</head>
<body>
	<div>
		<div class=page_title>
			<h3>会议类型[${meetingType.name}]的基础菜单定制</h3>
		</div>

		<div class="page_tools page_toolbar">
		<c:if test="${pager.total == 0}">
			<a type="button" class="btn_common btn_false"
				href="javascript:initMenu(${meetingTypeId})">生成菜单</a>
		</c:if>
			
			<a type="button" class="btn_common btn_false"
				href="${ctx}/admin/pri/custom/meetingType_addReq.action?meetingTypeId=${meetingTypeId}">返回</a>
		</div>

		<!-- 查询FORM -->
		<form id="queryFrm"
			action="${ctx}/admin/pri/custom/meetingType_listMenu.action">
			<input type="hidden" id="totalPage" name="totalPage"
				value="${pager.pageCount}" /> <input type="hidden"
				name="currentPage" id="currentPage" value="${pager.currentPage}" />
			<input type="hidden" name="meetingTypeId" id="meetingTypeId"
				value="${meetingTypeId}" />

			<div class="page_tools page_toolbar page_serach"
				style="text-align: left;">
				菜单名称：<input type="text" id="name" name="baseMenu.name" value="${baseMenu.name}">
				菜单描述：<input type="text" id="description" name="baseMenu.description"
					value="${baseMenu.description}"> 
				访问类型：<wd:select name="baseMenu.terminalType" type="terminal_type"
					nullLabel="--全部--" nullOption="true"
					selectedValue="${baseMenu.terminalType}"></wd:select>

				<a id="queryForList" class="btn_common btn_true" onclick="query();"
					href="##">查询</a>
			</div>
		</form>
	</div>

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
					<c:forEach var="menu" items="${pager.pageRecords}"
						varStatus="status">
						<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
							<td align="left"></td>
							<td align="left">${menu.name }</td>
							<td align="left">${menu.description}</td>
							<td align="left">
							<wd:translate type="terminal_type" value="${menu.terminalType}"></wd:translate>
							</td>
							<td align="left"><fmt:formatDate
									value="${menu.modifyTime}" type="both"
									pattern="MM月d日 HH:mm" /></td>
							<td align="center"><a class="pre" href="#"
								onclick="editBaseMenu('${menu.id }')">编辑</a></td>
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

	<div>
		<%@ include file="/pages/common/page.jsp"%>
	</div>

</body>
</html>