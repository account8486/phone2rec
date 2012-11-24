<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="common.*"%>
<%
String appPath = request.getContextPath();
request.setAttribute("appPath", appPath);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主监管</title>
<style type="text/css">
body { margin:0; padding:0; width:100%;  background:red;}
</style>
</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><iframe src="appMonitor.jsp" frameborder="0" scrolling="no" width="100%" height="270px"></iframe></td>
	</tr>
	<tr>
		<td><iframe src="deskMonitor.jsp" frameborder="0" scrolling="no" width="100%" height="190px"></iframe></td>
	</tr>
	<tr>
		<td><iframe src="netMonitor.jsp" frameborder="0" scrolling="no" width="100%" height="200px"></iframe></td>
	</tr>
	<tr>
		<td><iframe src="secuMonitor.jsp" frameborder="0" scrolling="no" width="100%" height="240px"></iframe></td>
	</tr>	
</table>

</body>
</html>