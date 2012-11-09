<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
	<meta http-equiv="Cache-control" content="no-cache" />
	<title>会务通平台</title>
	<link href="${ctx }/css/wap.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="logo">
		<div class="w240">
	    	<p>
	        	<!-- <a href="${ctx}/wap/pri/base/logout.action"></a> -->
	        </p>
	    </div>
	</div>
	
	<div class="main">
	<form id="loginForm" action="${ctx}/touch/base/getPwd.action" method="post">
		<c:if test="${not empty param.meetingId or not empty meeting.id}">
			<input type="hidden" name="meeting.id" value="${not empty param.meetingId ? param.meetingId : meeting.id}" />
		</c:if>
		
		<div style="margin-left:20px;">
		    <br/>
		    <div class="article">
		   	手机号码：<br/>
		   	<input type="text" id="mobile" name="u.mobile" value="${u.mobile}"/>
		   	</div>
		   	
		   	<div class="article">
		   		<font color="red">${errMsg }</font>
		   	</div>
		   	
		   	<div class="article" style="margin-top:10px;">
		   		<input type="submit" value="获取动态密码" style="margin-left:20px;"/>
		   	</div>
		   	<br/>
	   	</div>
	</form>
	</div>
	
	<div class="footer">
		版权所有 © 中国移动通信集团贵州有限公司 2012
	</div>

</body>
</html>