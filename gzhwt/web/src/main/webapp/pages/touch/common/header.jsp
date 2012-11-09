<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<title>贵州移动会务通</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<!--移动设备特性-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
	<meta name="format-detection" content="telephone=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<link rel="apple-touch-icon" href="${ctx}/images/touch/apple-touch-icon-precomposed.png"/>
	<link rel="apple-touch-startup-image" href="${ctx}/images/touch/apple-touch-startup-ipad.png"/>
	<link rel="apple-touch-startup-image" href="${ctx}/images/touch/apple-touch-startup.png"/>
	<!-- 主样式表 -->
	<link href="${ctx}/css/touch/main.css" rel="stylesheet" type="text/css">
	<!-- jQuery 1.7.2 和 jQuery UI 1.8.18 -->
	<script type="text/javascript" src="${ctx}/js/touch/jquery-1.7.2.min.js"></script>
	<!-- JS -->
	<script type="text/javascript" src="${ctx}/js/touch/ready.js"></script>
	<script type="text/javascript" src="${ctx}/js/touch/function.js"></script>
</head>
<body>

<header>
	<h3 id="header_meeting_name">${wd:limit(meeting.name, 17)}</h3>
    <!-- <div class="weather">
        <span class="info">合肥<s>16℃~6℃</s></span>
        <img src="${ctx}/images/touch/weather01.png">
    </div> -->
</header>

<div>
