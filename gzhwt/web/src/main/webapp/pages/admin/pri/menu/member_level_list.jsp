<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资料管理</title>
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
    <div class=page_title><h3>菜单管理  -- ${CURRENT_MEETING}</h3></div>

	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="系统菜单" link="${ctx}/admin/pri/basemenu/getBaseMenuPages.action?meetingId=${param.meetingId}" style="padding:10px;"></div>
		<div title="定制菜单" link="${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=3&meetingId=${param.meetingId}" style="padding:10px;"></div>
		<div title="权限配置" style="padding:10px;" selected="true">
            <!-- 显示内容TABLE -->
            <table class=page_datalist>
                <thead>
                <tr>
					<td>会员级别</td>
					<td>操作</td>
                </tr>
                </thead>
			<c:forEach begin="1" end="5" step="1" varStatus="status" >
				<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
					<td>${status.count} 级</td>
					<td>
					<a href="${ctx}/admin/pri/menu/getBaseMenu.action?meetingId=${param.meetingId}&memberLevel=${status.count}">编辑</a>
					</td>       
				</tr>
			</c:forEach>
            </table>
		</div>
		<div title="访问统计" link="${ctx}/admin/pri/statistics/menuStatics.action?meetingId=${param.meetingId}" style="padding:10px;"></div>
	</div>
</body>
</html>