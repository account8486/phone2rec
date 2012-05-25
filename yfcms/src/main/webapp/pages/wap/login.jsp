<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
	<meta http-equiv="Cache-control" content="no-cache" />
	<title>安徽电信会议云平台</title>
	<link href="${ctx }/css/wap.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="logo">
		<div class="w240">
	    	<p>
	        	<a href="${ctx}/wap/pri/base/logout.action">退出</a>
	        </p>
	    </div>
	</div>
	
	<div class="main">
	<form id="loginForm" action="${ctx}/wap/base/login.action" method="post">
		<div style="margin-left:20px;">
		    <br/>
		    <div class="article">
		   	手机号码：<br/>
		   	<input type="text" id="mobile" name="u.mobile" value="${u.mobile}"/>
		   	</div>
		   	
		   	<div class="article">
		   	密码：<br/>
		   	<input type="password" id="password" name="u.password" value="${u.password }" maxlength="20"/>
		   	</div>
		   	
		   	<div class="article">
		   	<font color="red">${errMsg }</font>
		   	</div>
		   	
		   	<div class="article" style="margin-top:10px;">
		   		<input style="margin-left:20px;" type="submit" value="&nbsp;登&nbsp;录&nbsp;"/>&nbsp;
		   		<a href="${ctx }/pages/wap/getPwd.jsp" style="text-decoration:underline;">获取密码</a>
		   	</div>
		   	<br/>
	   	</div>
	</form>
	</div>
	
	<div class="footer">
		版权所有 © 中国电信安徽公司 2011
	</div>

</body>
</html>