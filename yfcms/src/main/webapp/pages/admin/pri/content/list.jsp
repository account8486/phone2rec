<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
<%
	String retMsg=(String)request.getAttribute("retMsg");
	if(retMsg!=null&&!"".equals(retMsg)){
%>
	<script type="text/javascript">
	alert('<%=retMsg%>');
	</script>
<%
	}
%>
<c:if test="${contentType == 1}">
	<c:set var="contentName" value="景点"></c:set>
</c:if>
<c:if test="${contentType == 2}">
	<c:set var="contentName" value="酒店"></c:set>
</c:if>
<c:if test="${contentType == 3}">
	<c:set var="contentName" value="菜单"></c:set>
</c:if>
	<div class="page_title"><h3>${contentName }管理  -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="会议菜单" link="${ctx}/admin/pri/basemenu/getBaseMenuPages.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="自定义菜单"  style="padding:10px;" selected="true">
			<div class="page_tools page_toolbar">
				<a href="javascript:void();"  onclick="gotoAdd();return false;" class="btn_common btn_false" width="120px;">添加${contentName }</a>
				<a href="javascript:void();"  onclick="gotoAddList();return false;" class="btn_common btn_false" width="120px;">添加列表</a>
			</div>

			<div>
				<table class="page_datalist">
					<thead>
						<tr>
							<th width="1%">&nbsp;</th>
							<th width="30%">${contentName }名称</th>
							<th width="15%">${contentName }类型</th>
							<th width="15%">更新时间</th>
							<th width="35%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty pager.pageRecords}">
								<c:forEach var="content" items="${pager.pageRecords}" varStatus="status">
									<tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
										<td></td>
										<td align="left">${content.contentTitle }</td>
										<td align="left">${content.isList eq "1" ? "列表":"单条"}</td>
										<td align="left"><fmt:formatDate value="${content.modifyTime}"
																		 type="both"
																		 pattern="MM月d日 HH:mm"/></td>
										<td align="center"><a href="#" onclick="modify('${content.id}');">编辑</a>&nbsp;
										<a href="#" onclick="del('${content.id}');">删除</a>
										<a href="#" onclick="preview('${content.id}');">预览</a></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="datarow">
									<td colspan="10" align="center">
										没有${contentName }信息.
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		<div title="菜单配置" link="${ctx}/pages/admin/pri/menu/member_level_list.jsp?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="菜单访问统计" link="${ctx}/admin/pri/statistics/menuStatics.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("span.wordbreak").each(function(){
			$(this).html($(this).html().replace(/\n/g,"<br/>"));
		});
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

    function query(){
		$('#listMeetingForm').submit();
	}
	function gotoAdd(){
		//window.location.href = url;
		window.location.href = "${ctx}/admin/pri/meeting/gotoAddContent.action?contentType=${contentType}&meetingId=${meetingId}";
	}
	function gotoAddList(){
		window.location.href = "${ctx}/admin/pri/meeting/gotoAddContentList.action?contentType=${contentType}&meetingId=${meetingId}";
	}
	function modify(id){
		//window.location.href = url;
		window.location.href = "${ctx}/admin/pri/meeting/getMeetingContentById.action?returnType=modify&content.id="+id;
	}
	function preview(id){
		//window.location.href = url;
		url = "${ctx}/admin/pri/meeting/getMeetingContentById.action?returnType=preview&content.id="+id;
		window.open(url, "_blank", "location=no,menubar=no,scrollbars=yes,resizable=yes,status=no,height=600,width=800,top=200,left=200");
	}
	function del(id){
		if(confirm("确定要删除选择的信息吗?")){
			doDelete(id);
		}
	}
	
	function doDelete(id){
		var url = '${ctx}/admin/pri/meeting/deleteMeetingContent.action?content.id='+id;
		
		$.getJSON(url, callback);
	}
	//回调函数
	function callback(data){
		alert(data.retmsg);
		if(data.retcode == "0"){
			returnList();
		}
	}
	
	function returnList(){
		window.location.href = "${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=${contentType}&meetingId=${meetingId}";
	}
</script>
</body>
</html>