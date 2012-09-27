<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>栏目管理</title>
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
			
			
			
			$("#all_check").change(function () {
				if (this.checked) {
					$("[name='chanId']").attr("checked", $("#all_check").attr("checked"));
				} else {
					$("[name='chanId']").removeAttr("checked");
				}
			});
	    
			//有一个不选上则全不选
			$('input[type="checkbox"][name="chanId"]').click(function () {
				   var ckall = true;
				   $('input[type="checkbox"][name="chanId"]').each(function (){
					if (!this.checked){ 
						ckall = false;  
						//直接退出循环,不在进行each循环
						return false; 
						}});
				   
				   $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		   });	
			
			
			
		});
		
		
		function batchDelete(){
	        var retString = "";
	        var checks = document.getElementsByName("chanId");
	        if (checks) {
	            for (var i = 0; i < checks.length; i++) {
	                var chkObj = checks[i];
	                if (chkObj.checked)
	                    retString += chkObj.value + ",";
	            }
	        }
	        
	        if(retString==""){
	        	alert("请选择你要删除的栏目！");
	        	return;
	        }
			//alert(retString);
			//alert(meetingId);
			if(confirm("你确定要批量删除栏目吗？")){
				var url="${ctx}/admin/pri/channel/batchDelChans.action?ids="+retString;			
				this.location=url;
			}
			
	    }
		
	    function query() {
	        $('#sbFrm').submit();
	    }
	    
	    
	    function delChannel(id){
	    	 if(confirm("确定删除当前栏目吗？")){
				 var url="${ctx}/admin/pri/channel/delChannel.action?id="+id;
				 this.location=url;
			 }
	    }
	    
	    
	    
    </script>
</head>
<body>
<div class="page_title"><h3></h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	
	    <div title="栏目列表" style="padding:10px;" selected="true" >
		<div class="page_tools">
		<form id="sbFrm" action="${ctx}/admin/pri/channel/channelList.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		</form>
			<a href="#" id="queryForList" onclick="query();"></a>
			
				<table width="80%">
				<tr>
				<th style="width: 100px; ">栏目名称：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px;" id="chanName" name="chanName" value="${chanName}"/></td>
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
					
					<a href="#" onClick="batchDelete()" class="btn_common btn_false">批量删除</a> 
				</td>
				</tr>
			</table>
			
			
		</div>
		
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="10%">
				<input type="checkbox" name="all_check" id="all_check"></input>
				</th>
				<th width="20%">栏目名称</th>
				<th width="20%">描述</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="chan" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
						    <td>
						    <input type="checkbox" name="chanId" value="${chan.id}">
						    </td>
							<td>${chan.chanName }</td>
							<td>${chan.chanDescription }</td>
							<th width="20%">
							<a href="${ctx}/admin/pri/channel/toEditChannel.action?id=${chan.id}">编辑</a>
						    <a href="#"  onClick="delChannel(${chan.id})">删除</a>
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
	
	<div title="新增栏目" link="${ctx}/admin/pri/channel/toAddChannel.action"  style="padding:10px;"></div>
	
</div>
</body>
</html>