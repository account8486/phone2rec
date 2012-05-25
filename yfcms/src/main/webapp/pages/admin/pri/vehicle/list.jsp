<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆管理</title>
${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title"><h3>车辆管理  -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="车辆管理" style="padding:10px;">
	<div class="page_tools">
		<form id="listForm" action="${ctx}/admin/pri/journey/listVehicle.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
                <input type="hidden" id="queryForList" />
				<a href="javascript:void(0);" class="btn_common btn_false" id="add">添加车辆</a>
        </form>
	</div>
	
	<div>
		<table class="page_datalist">
	    	<thead>
				<tr>
					<th scope="col" width="10%">车辆名称</th>
					<th scope="col" width="10%">车牌号</th>
					<th scope="col" width="10%">容纳人数</th>
					<th scope="col" width="10%">操作</th>
				</tr>	    	
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="vehicle" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td align="left">${vehicle.name }</td>
                            	<td align="left"> ${vehicle.number }</td>
				          		<td align="left">${vehicle.capacity }</td>
				          		<td align="center">
									<a href="javascript:void(0);" class="edit" id="${vehicle.id }">编辑</a>
				          		  &nbsp;<a href="javascript:void(0);" class="delete" id="${vehicle.id }">删除</a>
				          		</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">没有车辆信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
		
		
		</div>
		<div title="司机管理" link="${ctx}/admin/pri/journey/listVehicleDriver.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="行程管理" link="${ctx}/admin/pri/journey/listJourney.action?meetingId=${meetingId}" style="padding:10px;"></div>	
	</div>
	<script type="text/javascript">
	$(document).ready(
		function() {
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
		
			$("#add").click(function() {
				window.location.href = "${ctx}/admin/pri/journey/gotoAddVehicle.action?meetingId=${meetingId}";
				return false;
			});
			
			$("#queryForList").click (function() {$('#listForm').submit();});
			
			$("a.delete").click(
				function() {
					if(confirm("确定要删除该条信息吗?")){
						var id = $(this).attr('id');
						$.post(
								"${ctx}/admin/pri/journey/deleteVehicle.action",
								{"vehicle.id" :　id	},
								function(data, textStatus) {
									alert(data.retmsg);
									if(data.retcode == "0"){
										location.reload();
									}
								}, 
								"json"
						);
					}
					return false;
				}
			);
			
			$("a.edit").click(
				function() {
					var id = $(this).attr('id');
					window.location.href = "${ctx}/admin/pri/journey/gotoModifyVehicle.action?vehicle.id="+id;
					return false;
				}
			);
			
		}
	);
	</script>
</body>
</html>