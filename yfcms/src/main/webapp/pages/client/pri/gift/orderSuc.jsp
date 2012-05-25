<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta http-equiv="Cache-control" content="no-cache" />
<title>安徽电信会议云平台</title>
<link href="${ctx}/css/wap.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<c:set var="meeting_list_url" value="<a href='${ctx}/wap/pri/meeting/getAttendMeetingList.action'>会议列表</a>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<body>
<div class="main">
	
	<br/>
	<div style="margin-left:2px;">
	    <div class="article">
	    ${errMsg }
	    <br/><br/>
	    <a class="btn_blue" style="text-decoration:none;" href="${ctx}/client/pri/gift/list.action">继续订购</a>
	   	</div>
   	</div>
</div>
</body>