<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>角色管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".easyui-tabs").tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs("getTab", title); 
					var href = tab.attr("link");
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});	
		});
		
		
	    function query() {
	        $('#sbFrm').submit();
	    }
	    
	    
	    function delRole(roleId){
	    	 if(confirm("确定删除当前角色吗？")){
				 var url="${ctx}/admin/pri/security/deleteRole.action?roleId="+roleId;
				 this.location=url;
			 }
	    }
	    
    </script>
</head>
<body>
<div class="page_title"><h3></h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	
	<div title="角色列表" style="padding:10px;" selected="true" >
		<div class="page_tools">
		<form id="sbFrm" action="${ctx}/admin/pri/security/list.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		</form>
			<a href="#" id="queryForList" onclick="query();"></a>
		</div>
		
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="10%">序号 </th>
				<th width="20%">角色名称</th>
				<th width="20%">角色描述</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="role" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
							<td>${(pager.currentPage-1) * pager.pageSize + status.index + 1}</td>
							<td>${role.roleName }</td>
							<td>${role.roleDescription }</td>
							<th width="20%">
							<a href="${ctx}/admin/pri/security/toEditRole.action?roleId=${role.id}">编辑</a>
						    <a href="#"  onClick="delRole(${role.id})">删除</a>
							</th>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无角色信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
		</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>
	
	<div title="新增角色" link="${ctx}/admin/pri/security/toAddRole.action"  style="padding:10px;"></div>
	
</div>
</body>
</html>