<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>会议云</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <!--浏览器特性-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--多终端icon-->
    <link href="favicon.ico" type="image/x-icon" rel="Bookmark"/>
    <link href="favicon.ico" type="image/x-icon" rel="shortcut icon"/>
    <link href="res/logo_apple.png" rel="apple-touch-icon"/>
    ${main_css}
    <link href="${cssdir}/index.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/layout.css" rel="stylesheet" type="text/css" />
    ${jquery_js}
    ${util_js}
    <!-- My JS -->
    <script type="text/javascript" src="${ctx}/js/portal.js"></script>
    <!-- 主样式表 -->

    <!-- IE识别HTML5标签 -->
    <!--[if IE]>
    <script src="${ctx}/js/html5.js"></script>
    <![endif]-->
    <!-- IE6 PNGfix -->
    <!--[if IE 6]>
    <script src="${ctx}/js/iepng.js" mce_src="${ctx}/js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.iepng');
        /* 将 .iepng 改成你应用了透明PNG的CSS选择器 */
    </script>
    <![endif]-->
	<!--[if IE 6]>
	<style type="text/css">
		#wrap {display:table;height:100%}
	</style>
	<![endif]-->
</head>
<body>

<div id="wrap">
	<div id="main" class="clearfix">

<div class="header">
    <div class="w960">
    	<div style="float: left; width: 110px; margin-top: 20px;">
    		<%-- 显示定制的logo信息 --%>
            <s:if test="#session.pageCustomMeetingTypeId != null">
            	<img src="${ctx}/portal/pri/custom/showLogoImage.action?meetingTypeId=${sessionScope.pageCustomMeetingTypeId}" width="108" height="53" border="0"/>
            </s:if>
    	</div>
    	<div class="title">
            <h3 id="title_meeting_name" title="${meeting.name}" >${fn:escapeXml(meeting.name)}</h3>
        </div>
        <div class="detail">
            时间：<fmt:formatDate value="${meeting.startTime }" type="both" pattern="yyyy年MM月d日"/> - <fmt:formatDate value="${meeting.endTime }" type="both" pattern="yyyy年MM月d日"/><br/>
            地点：${meeting.location}
        </div>
    </div>
</div>

<div class="nav" style="height:10px;">
</div>

<script type="text/javascript">

    $(document).ready(function() {
    	//alert($("#title_meeting_name").html().length);
    	//处理标题过长的问题，当标题长度大于22时进行换行并调整样式
    	
    	if($("#title_meeting_name").html().length>24){
    		$("#title_meeting_name").html($("#title_meeting_name").html().substring(0,24)+"<br>"+$("#title_meeting_name").html().substring(24));
    		$("#title_meeting_name").addClass("wordbreak");
    	}
    });
</script>
