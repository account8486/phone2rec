<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>酒店管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}	${jmpopups_js}
    <link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css"/>
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>酒店管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="酒店管理" style="padding:10px;">
			<div class="page_tools page_serach">
			<form id="mainForm" action="${ctx}/admin/pri/hotel/list.action">
				<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
		        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
				<table width="98%">
			      <tr>
			        <th>酒店名称：</th>
			        <td><input type="text" id="hotelName" name="hotel.name"
		                                   value="${hotel.name }"/></td>
			        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
			      </tr>
			    </table>
			</form>
			</div>
			
			<div class="page_tools page_toolbar">
				<a href="#" id="addBtn" class="btn_common btn_false" style="margin-left:5px;">添加酒店</a>
				<font color="red">&nbsp;&nbsp;&nbsp;${errMsg }</font>
			</div>
		
			<div>
				<table class="page_datalist">
			    	<thead>
			        	<tr>
			            	<th width="2px" style="border-right:0"></th>
			                <th width="100">酒店名称</th>
			                <th width="180" >地址</th>
		                    <th width="10" colspan="2">操作</th>
			            </tr>
			        </thead>
			        <tbody>
			            <tr>
			                <c:choose>
		                    <c:when test="${not empty pager.pageRecords}">
		                        <c:forEach var="record" items="${pager.pageRecords}" varStatus="status">
		                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
		                                <td></td>
		                                <td>${record.name }</td>
		                                <td>${record.address }</td>
		                                <td width="40px"><a href="${ctx}/admin/pri/hotel/goUpdate.action?id=${record.id}&meetingId=${meetingId}">编辑</a></td>
		                                <td width="40px"><a href="#" onclick="delHotel(${record.id})">删除</a></td>
		                            </tr>
		                        </c:forEach>
		                    </c:when>
		                    <c:otherwise>
		                        <tr class="datarow">
		                            <td colspan="5" align="center"> 没有酒店信息.</td>
		                        </tr>
		                    </c:otherwise>
		                </c:choose>
			            </tr>
			        </tbody>
			    </table>
				<%@ include file="/pages/common/page.jsp" %>
				</div>
			</div>
		<div title="餐厅管理" link="${ctx}/admin/pri/dr/list.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="客房房型管理" link="${ctx}/admin/pri/hrt/list.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="客房管理" link="${ctx}/admin/pri/room/list.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
<script type="text/javascript">
	function delHotel(id)
	{
		if(confirm("您确认要删除该酒店吗？"))
		{
			window.location.href = "${ctx}/admin/pri/hotel/del.action?id="+ id + "&meetingId=" + $("#meetingId").val();
		}
	}
	
	$(document).ready(function(){
		
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
		
		$("#addBtn").click(function(){
			window.location.href = "${ctx}/admin/pri/hotel/goAdd.action?meetingId=" + $("#meetingId").val();
		});
		
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
	
    jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'hotelName':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>