<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
${jquery_js}                          
${jquery_form_js}  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>内容展示</title>
</head>

<frameset rows="80,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${ctx}/pages/admin/pri/frames/topFrame.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame">
  <frameset cols="150,*" frameborder="yes" border="0.5" framespacing="1">
    <frame src="${ctx}/pages/admin/pri/frames/leftFrame.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame">
    <frame src="${ctx}/pri/admin/getSsoSystemList.action" name="mainFrame" id="mainFrame" title="mainFrame">
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes></html>
