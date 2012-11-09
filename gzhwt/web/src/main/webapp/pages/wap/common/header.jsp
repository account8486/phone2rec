<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%-- handle page theme --%>
<c:set var="cssdir" value="${ctx}/css${empty sessionScope.pageThemeName ? '' : sessionScope.pageThemeName}" />

<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta http-equiv="Cache-control" content="no-cache" />
<title>会务通平台</title>
<link href="${cssdir}/wap.css" rel="stylesheet" type="text/css">
<c:set var="meeting_list_url" value="<a href='${ctx}/wap/pri/meeting/getAttendMeetingList.action'>我的会议</a>"/>
</head>
<body>
<div class="logo">
	<%-- 显示定制的logo信息 --%>
            <s:if test="#session.meeting_logo_image_url != null">
            	<img src="${sessionScope.meeting_logo_image_url }" style="float: left; width: 108px; height:35px; border:0; " />
            </s:if>
	<div class="w240">
    	<p>
        	<span>${SESSION_USER.name}</span>，欢迎您！ - <a href="${ctx}/wap/pri/base/logout.action">退出</a>
        </p>
    </div>
</div>
<div class="title">
	<div class="w240">
    	${_portal_meeting_.name}
    </div>
</div>