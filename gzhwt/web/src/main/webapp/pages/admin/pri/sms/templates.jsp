<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.wondertek.meeting.model.MeetingFiles" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议短信管理</title>
    ${admin_css}  ${jquery_js} ${admin_js} ${jmpopups_js} ${util_js} 
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>    
	<script>
		$(document).ready(function() {
		    $('.easyui-tabs').tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs('getTab', title); 
					var href = tab.attr('link');
					if (href) {
						showLoading(title);
						location.href = href;
						return false;
					}
				}  
			}); 
		});
	</script>
</head>
<body>
    <!-- title -->
	<div class="mainbox"><div class=page_title><h3>短信管理  -- ${CURRENT_MEETING}</h3></div></div>

	<!-- 标签 -->
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="短信列表" style="padding:10px;"></div>
		<div title="发送短信" link="${ctx}/admin/pri/message/preSendMessage.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="短信模版" style="padding:10px;"></div>
	</div>
</body>
</html>