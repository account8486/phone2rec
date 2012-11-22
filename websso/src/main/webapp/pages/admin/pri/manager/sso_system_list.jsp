<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
<title>系统列表</title>

${util_js}
${jquery_js}                          
${jquery_form_js}  

<script>
	function query(){
		
		$("#sbFrm").submit();
	}
</script>

</head>
<body>

<div class="page_tools">
		<form id="sbFrm" action="${ctx}/pri/admin/getSsoSystemList.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		
			<a href="#" id="queryForList" onclick="query();"></a>
			
				<table width="80%">
				<tr>
				<th style="width: 100px; ">系统名称：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px;" id="appName" name="appName" value="${appName}"/></td>
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
				</td>
				</tr>
			</table>
			
		</form>
		</div>
		
		
		
<table width="480" height="132" border="1" style="text-align:center;align:center;">
  <tr>
    <td>序号</td>
    <td>系统名称</td>
    <td>操作</td>
  </tr>

 <c:if test="${not empty pager}">
 
  <c:set var="rn" value="1" />
  <c:forEach var="ssosys" items="${pager.pageRecords}" varStatus="status">
    <tr>
    <td>
   	<input type="hidden" name="" id="" value="${ssosys[0]}"> 
   	${rn}
    </td>
    <td>${ssosys[1]}</td>
    <td>
    <a href="#" id="configParameter" onClick="doEdit('${ssosys[0]}');">配置参数</a>
    </td>
    
   </tr>
   
   <c:set var="rn" value="${rn+1}"></c:set>
  </c:forEach>
 </c:if>
 
</table>
<%@ include file="/pages/common/page.jsp"  %>

</body>
</html>

<script type="text/javascript">
function doEdit(appId){
	 //alert(appId);
	 var url="${ctx}/pri/admin/toConfigSsoSystem.action?appId="+appId;
	 window.location.href=url;
}
</script>

