<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
${jquery_js}                          
${jquery_form_js}  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>
</head>

<body>
<div style="left:1000px">

<a href="${ctx}/pri/admin/getSsoSystemList.action" target="mainFrame">参数配置</a> <br>
<a href="${ctx}/pages/admin/pri/manager/change_data_source.jsp" target="mainFrame">切换数据源</a> <br>

</div>

</body>
</html>
