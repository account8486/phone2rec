<%@ page language="java" import="java.util.*,bean.*,dao.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="common.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link href="${appPath}/images/common.css" rel="stylesheet"
			type="text/css" />
		<title>湿度监管</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<style>
html,body,h3,td {
	color: #fff;
	font-family: '宋体', Arial, Helvetica;
	font-weight: bold;
}

/* body { background:#033d3d; }   border-left:1px solid #039bb4;*/
body {
	background: #000;
	padding: 0.5em 1em 0.5em 1em;
}

.title {
	color: #fff;
	font-size: 24px;
	padding-left: 2em;
	line-height: 1em;
}

.main {
	border: 4px solid #360;
	padding: 0.5em 1em 0.5em 0em;
}

.item h3 {
	font-weight: normal;
	color: #fff;
	font-size: 22px;
}

td {
	text-align: center;
}

td img {
	padding: 1% 0;
}

td.tar {
	width: 10.5em;
	text-align: right;
	font-size: 14px;
}

td.tar div {
	padding-right: 1em;
}

tr.area td {
	text-align: center;
}

tr.area td div {
	font-size: 14px;
	width: 1em;
	margin: 0 auto;
	padding-top: 0em;
}

td.bg_cross {
	background: url(${appPath}/images/bg_cross.gif) 50% 50% no-repeat;
}

.box {
	background: #000;
	position: relative;
	width: 1383px;
}

.box .rc-tp,.box .rc-bt {
	position: relative;
	display: block;
	height: 15px;
	overflow: hidden;
}

.box .rc-tp b,.box .rc-bt b {
	float: right;
	width: 15px;
	height: 15px;
}

.box .rc-tp {
	margin-bottom: -15px;
}

.box .rc-bt {
	margin-top: -15px;
}

.box .rc-tp {
	background: url(${appPath}/images/bg_lt.gif) no-repeat;
}

.box .rc-tp b {
	background: url(${appPath}/images/bg_rt.gif) no-repeat;
}

.box .rc-bt {
	background: url(${appPath}/images/bg_lb.gif) no-repeat;
}

.box .rc-bt b {
	background: url(${appPath}/images/bg_rb.gif) no-repeat;
}

#tip {
	position: absolute;
	color: #333;
	display: none;
}

#tip s {
	display: block;
	position: absolute;
	bottom: -20px;
	left: 10px;
	width: 0;
	height: 0;
	line-height: 0;
	font-size: 0;
	border-color: #FFF transparent transparent;
	border-style: solid dashed dashed;
	border-width: 10px;
}

#tip .t_box {
	position: relative;
	background-color: #CCC;
	bottom: 0;
	right: 0;
}

#tip .t_box div {
	position: relative;
	background-color: #FFF;
	background: #FFF;
	top: 0;
	left: 0;
}

#tip .t_box .cont {
	font-size: 18px;
	padding: 0.5em;
	white-space: nowrap;
}

.bubble {
	width: 34px;
	height: 17px;
}

#left {
	width: 100px;
}
</style>
		<script type="text/javascript" src="${appPath}/images/jquery.min.js"></script>
	</head>
	<body>

		<div>
			<table>
				<tr>
					<td white=44px>
						地点
					</td>
					<c:forEach items="${sdlist}" var="sd">
					
						<td white=34px>
							${sd.agentname}${sd.devname}${sd.ch}
						</td>
						
					</c:forEach>

				</tr>
				<tr>
					<td white=44px>
						湿度
					</td>
						<c:forEach items="${sdlist}" var="sd">
							<c:if test="${sd.flag eq '0'}">
								<td>
									<img src="images/green34.gif" />
								</td>
							</c:if>
							<c:if test="${sd.flag eq '1'}">
								<td>
									<img src="images/red34.gif" />
								</td>
							</c:if>
							<c:if test="${sd.flag eq '3'}">
								<td>
									<img src="images/blue34.gif" />
								</td>
							</c:if>
						</c:forEach>
				</tr>
			</table>

		</div>
	</body>

</html>
