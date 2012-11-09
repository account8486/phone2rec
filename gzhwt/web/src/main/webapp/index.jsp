<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<body>
<%
	String userAgent = request.getHeader("user-agent");
	String queryString = request.getQueryString();
	queryString = queryString == null ? "" : "?" + queryString;
	
	System.out.println("userAgent:" + userAgent);
	System.out.println("queryString:" + queryString);
	if (userAgent.indexOf("Windows NT") != -1 || userAgent.indexOf("Macintosh") != -1) {
		response.sendRedirect(request.getContextPath() + "/pages/portal/login.jsp" + queryString);
	} else {
		if (userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iOS") != -1
				|| userAgent.indexOf("iPad") != -1 || userAgent.indexOf("iPod") != -1
				|| userAgent.indexOf("Android") != -1 || userAgent.indexOf("Windows Phone") != -1
				|| userAgent.indexOf("MQQBrowser") != -1 || userAgent.indexOf("Linux") != -1) {
			response.sendRedirect(request.getContextPath() + "/pages/touch/login.jsp" + queryString);
		} else if (userAgent.indexOf("SymbianOS") != -1
				|| userAgent.indexOf("BlackBerry") != -1) {
			response.sendRedirect(request.getContextPath() + "/pages/wap/login.jsp" + queryString);
		} else {
			response.sendRedirect(request.getContextPath() + "/pages/portal/login.jsp" + queryString);
		}
	}
%>
<script type="text/javascript">
	//该页面为间接跳转页面，如果访问时没有指定portal或wap前缀，则根据访问浏览器所在的平台，自动跳转到portal或wap页面
	//added by zhangguojing at 2012/3/26
	/*
	var userAgent = navigator.userAgent;
	// 桌面
	if(userAgent.match(/Windows NT/i) || userAgent.match(/Macintosh/i)) {
		window.location = "${ctx}/pages/portal/login.jsp";
	} else {
		if(userAgent.match(/iPhone/i) || userAgent.match(/iPad/i) || userAgent.match(/iPod/i)
				|| userAgent.match(/Android/i) || userAgent.match(/Windows Phone/i) || userAgent.match(/MQQBrowser/i)) {
	    	window.location = "${ctx}/pages/touch/login.jsp";
		} else if(userAgent.match(/UCWEB/i) || userAgent.match(/SymbianOS/i)
				|| userAgent.match(/BlackBerry/i)){
			window.location = "${ctx}/pages/wap/login.jsp";
		} else {
			window.location = "${ctx}/pages/portal/login.jsp";
		}
	}*/
</script>
</body>
</html>
