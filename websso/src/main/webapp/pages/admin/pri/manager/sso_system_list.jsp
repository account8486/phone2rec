<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
<title>系统列表</title>
</head>
<body>

<table width="480" height="132" border="1" align="center">
  <tr>
    <td>序号</td>
    <td>系统名称</td>
    <td>操作</td>
  </tr>

 <c:if test="${not empty pager}">
  <c:forEach var="ssosys" items="${pager.pageRecords}" varStatus="status">
    <tr>
    <td>
   	<input type="hidden" name="" id="" value="${ssosys[0]}"> 
    </td>
    <td>${ssosys[1]}</td>
    <td>
    <a href="#" id="configParameter" onClick="doEdit('${ssosys[0]}');">配置参数</a>
    </td>
    
   </tr>
  </c:forEach>
 </c:if>
 
</table>

<%@ include file="/pages/common/page.jsp" %>


</body>
</html>

<script type="text/javascript">
function doEdit(appId){
	 //alert(appId);
	 var url="${ctx}/pri/admin/toConfigSsoSystem.action?appId="+appId;
	 window.location.href=url;
}
</script>

