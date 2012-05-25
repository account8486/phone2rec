<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>左侧导航菜单</title>
${admin_css}
${jquery_js}
</head>
<body >
<div class="main" >
	<div class="left_nav">
	   	<dl>
        	<dt><h5>会议管理</h5></dt>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo&meeting.id=${param.meeting_id}" >
				<span class="nav">会议基本信息</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/agenda/agendaList.action?meetingId=${param.meeting_id}" >
				<span class="nav">会议议程</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?returnType=portal_addmeeting&meeting.id=${param.meeting_id}" >
				<span class="nav">会议用户管理</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${param.meeting_id}" >
				<span class="nav">会议资料管理</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId=${param.meeting_id}" >
				<span class="nav">用餐管理</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=2&meetingId=${param.meeting_id}" >
				<span class="nav">酒店住宿管理</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/meeting/listMeetingContent.action?contentType=1&meetingId=${param.meeting_id}" >
				<span class="nav">景点管理</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${param.meeting_id}" >
				<span class="nav">临时加入审批</span>
			</a></dd>
			
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/message/getMeetingMember.action?meetingId=${param.meeting_id}" >
				<span class="nav">短信发送</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/message/getMessageList.action?meetingId=${param.meeting_id}" >
				<span class="nav">短信列表</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/menu/member_level_list.jsp?meetingId=${param.meeting_id}">
				<span class="nav">会议菜单配置</span>
			</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/interaction/postList.action?meetingId=${param.meeting_id}" >
				<span class="nav">互动交流</span>
			</a></dd>
			<dd><a target = "mainFrame" href="${ctx}/admin/pri/sign/list.action" >
				<span class="nav">签到管理</span>
			</a></dd>
        </dl>
        <c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
        <dl>
        	<dt><h5>系统管理</h5></dt>
            <dd><a target = "mainFrame" href="${ctx}/pages/admin/pri/org/listAndTree.jsp" >
					<span class="nav">组织机构管理</span>
				</a></dd>
            <dd><a target = "mainFrame" href="${ctx}/admin/pri/user/list.action" >
					<span class="nav">用户管理</span>
				</a></dd>
        </dl>
        </c:if>
    </div>    
</div>   
    

<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	
});
                                          

//]]></script>
</body>
</html>
