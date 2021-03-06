<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/admin/pri/admin_top.jsp" %>

<html>
<head>
<title>系统列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
${util_js}
${jquery_js}                          
${jquery_form_js}  

<script>
	function query(){
		$("#sbFrm").submit();
	}
	
	$(document).ready(function() {
		
		$(".easyui-tabs").tabs({  
			onSelect:function(title){ 
				alert(title);
				var tab = $(this).tabs("getTab", title); 
				var href = tab.attr("link");
				if (href) {
					location.href = href;
					return false;
				}
			}  
		});
	});
		
		
		
</script>

</head>
<body>

<div class="easyui-tabs" border="false" style="padding:10px;">	
    <div  title="用户列表" style="padding:10px;"></div>
	<div  title="添加用户" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
	<div title="导入用户" link="${ctx}/admin/pri/import/preImportUser.action?meetingId=${meeting.id}" style="padding:10px;"></div>
	<div title="临时加入审批" link="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
</div>



<div>
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
		
		
		
<table width="98%" height="400" border="0" cellspacing="0" cellpadding="0" align=center class="mhjl-style" style="margin:0px;">
  <tr class=tab-add height="40">
    <td>序号</td>
    <td>系统名称</td>
    <td>操作</td>
  </tr>

 <c:if test="${not empty pager}">
 
  <c:set var="rn" value="1" />
  <c:forEach var="ssosys" items="${pager.pageRecords}" varStatus="status">
  <c:choose>
  <c:when test="${status.index%2==0}">
  <tr class=tab-even height="40">
  </c:when>
  <c:otherwise>
   <tr class=tab-add height="40">
  </c:otherwise>
  </c:choose>
  
    <td>
   	<input type="hidden" name="" id="" value="${ssosys[0]}"> 
   	${rn}
    </td>
    <td>${ssosys[1]}</td>
    <td>
    <a href="#" id="configParameter" onClick="doEdit('${ssosys[0]}');">基础配置</a>
    </td>
    
   </tr>
   
   <c:set var="rn" value="${rn+1}"></c:set>
  </c:forEach>
 </c:if>
 
</table>
<%@ include file="/pages/common/page.jsp"  %>


<%@ include file="/pages/admin/pri/admin_bottom.jsp" %>
</body>
</html>

<script type="text/javascript">
function doEdit(appId){
	 //alert(appId);
	 var url="${ctx}/pri/admin/toConfigSsoSystem.action?appId="+appId;
	 window.location.href=url;
}
</script>




