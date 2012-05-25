<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问统计</title>
${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>	
<script type="text/javascript">
	$(document).ready(function(){
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
	<div class="page_title">
		<h3>访问统计  -- ${CURRENT_MEETING}</h3>
	</div>

	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="会议菜单" link="${ctx}/admin/pri/basemenu/getBaseMenuPages.action?meetingId=${param.meetingId}" style="padding:10px;"></div>
		<div title="自定义菜单" link="${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=3&meetingId=${param.meetingId}" style="padding:10px;"></div>
		<div title="菜单配置" link="${ctx}/pages/admin/pri/menu/member_level_list.jsp?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="菜单访问统计" selected="true" style="padding:10px;">
		<div>
			<table class="page_datalist">
				<thead>
					<tr>
						<th scope="col" width="20%">模块名称</th>
						<th scope="col" width="20%">访问方式</th>
						<th scope="col" width="20%">访问次数</th>
					</tr>	    	
				</thead>
				<tbody>
					<tr>
						 <c:choose>
						<c:when test="${not empty resultList}">
							<c:forEach var="data" items="${resultList}" varStatus="status">
								<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
									<td align="left">${data.menuName }</td>
									<td align="left"> 
										<c:if test="${data.terminalType eq 'CLIENT'}">客户端</c:if>
										<c:if test="${data.terminalType ne 'CLIENT'}">${data.terminalType}</c:if>
									</td>
									<td align="left">${data.count }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="datarow">
								<td colspan="10" align="center">没有访问统计信息.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tr>
				</tbody>
			</table>
			<%@ include file="/pages/common/page.jsp" %>
		</div>
		</div>
	</div>
</body>
</html>