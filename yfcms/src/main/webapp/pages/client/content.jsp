<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>电信会议云</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <!--浏览器特性-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--多终端icon-->
    <link href="favicon.ico" type="image/x-icon" rel="Bookmark"/>
    <link href="favicon.ico" type="image/x-icon" rel="shortcut icon"/>
    <link href="res/logo_apple.png" rel="apple-touch-icon"/>
    ${main_css}
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
           <!--<div class="cbox">
                  <div class="title"><h5 id="caption">${content.contentTitle }</h5></div>
            </div>

            --><div class="cont" style="min-height: 200px;">
            	${content.content }
            </div>
           	<c:if test="${content.contentType == 4}">
           		<a href="${ctx}/client/pri/meeting/getCustomeMenu.action?returnType=client&content.id=${content.parentId }" onclick="">返回列表</a>
           	</c:if>
<div class="footer">版权所有 © 中国电信安徽公司 2011 </div>
</body>
</html>