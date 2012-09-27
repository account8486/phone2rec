<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>菜单管理</title>
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
			
			$("#unitType").val(${unitType});
			$("#unitParentId").val(${unitParentId});
			
			
			$("#all_check").change(function () {
				if (this.checked) {
					$("[name='unitId']").attr("checked", $("#all_check").attr("checked"));
				} else {
					$("[name='unitId']").removeAttr("checked");
				}
			});
	    
			//有一个不选上则全不选
			$('input[type="checkbox"][name="unitId"]').click(function () {
				   var ckall = true;
				   $('input[type="checkbox"][name="unitId"]').each(function (){
					if (!this.checked){ 
						ckall = false;  
						//直接退出循环,不在进行each循环
						return false; 
						}});
				   
				   $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		   });	
			
			
			
		});
		
		
	    function query() {
	        $('#sbFrm').submit();
	    }
	    
	    /**
	    *删除当前菜单
	    */
	    function delUnit(unitId){
	    	 if(confirm("确定删除当前菜单吗？")){
				 var url="${ctx}/admin/pri/unit/deleteUnit.action?id="+unitId;
				 this.location=url;
			 }
	    }
	    
	    function toAddUnit(parentId){
	    	 var url="${ctx}/admin/pri/unit/toAddUnit.action?unitParentId="+parentId;
	    	 this.location=url;
	    }
	    
	    
	    function batchDelete(){
	        var retString = "";
	        var checks = document.getElementsByName("unitId");
	        if (checks) {
	            for (var i = 0; i < checks.length; i++) {
	                var chkObj = checks[i];
	                if (chkObj.checked)
	                    retString += chkObj.value + ",";
	            }
	        }
	        
	        if(retString==""){
	        	alert("请选择你要删除的菜单!");
	        	return;
	        }
			//alert(retString);
			//alert(meetingId);
			if(confirm("你确定要批量删除菜单吗?")){
				var url="${ctx}/admin/pri/unit/batchDelUnits.action?ids="+retString;			
				this.location=url;
			}
			
	    }
	    
    </script>
</head>
<body>
<div class="page_title"><h3></h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	
	<div title="菜单列表" style="padding:10px;" selected="true" >
		<div class="page_tools">
		<form id="sbFrm" action="${ctx}/admin/pri/unit/list.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="80%">
				<tr>
				<th style="width: 100px; ">菜单名称：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px;" id="unitName" name="unitName" value="${unitName}"/></td>
				<th style="width: 50px; ">类型：</th>
				<td style="width: 150px; ">
					<select name="unitType" id="unitType">
					<option value="">未选择</option>
					<option value="0">一级菜单</option>
					<option value="1">二级菜单</option>
					</select>
				</td>				
						
				<th style="width: 80px; ">一级模块：</th>
				<td style="width: 150px; ">
					<select name="unitParentId" id="unitParentId">
					<option value="">未选择</option>
					<%--采用循环的方式来读出数据库中的一级模块(一级菜单)的信息 --%>
					<c:if test="${not empty unitParentList }">
					 <c:forEach var="pUnit" items="${unitParentList}" varStatus="status" >
					 <option value="${pUnit.id}">${pUnit.unitName}</option>
					 </c:forEach>
					</c:if>
					</select>
				</td>
				
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
					
					<a href="#" onClick="batchDelete()" class="btn_common btn_false">批量删除</a> 
				</td>
				</tr>
			</table>
			
		</form>
			<a href="#" id="queryForList" onclick="query();"></a>
		</div>
		
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="10%"><input type="checkbox" name="all_check" id="all_check"></input> </th>
				<th width="15%">菜单名称</th>
				<th width="20%">菜单描述</th>
				<th width="5%">类型</th>
				<th width="20%">URL</th>
				<th width="10%">排序</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="unit" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
							 <td>
							 <c:if test="${unit.unitType ne '0'}">
								<input type="checkbox" name="unitId" value="${unit.id}">
								</input></c:if>
							 </td>
							<td>
							 <c:if test="${unit.unitType ne '0'}">${unit.unitName }</c:if>
							 <c:if test="${unit.unitType eq '0'}"><font style="color:red"> ${unit.unitName }</font></c:if>
							
							</td>
							<td>${unit.unitDescription}</td>
							<td>${unit.unitType}</td>
							<td>${unit.unitUrl}</td>
							<td>${unit.orderCode}</td>
							<th width="20%">
							<a href="${ctx}/admin/pri/unit/toUpdateUnit.action?id=${unit.id}">编辑</a>
							
							<c:if test="${unit.unitType ne '0'}">
						    <a href="#"  onClick="delUnit(${unit.id})">删除</a>
						    </c:if>
						    
						    <c:if test="${unit.unitType eq '0'}">
						     <a href="#"  onClick="toAddUnit(${unit.id})">添加菜单</a>
						    </c:if>
							</th>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无菜单信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
		</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>
	
	
<%--	<div title="新增角色" link="${ctx}/admin/pri/security/toAddRole.action"  style="padding:10px;"></div>
	 --%>
	 
	 
</div>
</body>
</html>