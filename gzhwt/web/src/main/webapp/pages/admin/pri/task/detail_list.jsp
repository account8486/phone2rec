<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>任务管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript" src="${ctx}/js/ajax-public.js"></script>
	
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
					$("[name='userId']").attr("checked", $("#all_check").attr("checked"));
				} else {
					$("[name='userId']").removeAttr("checked");
				}
			});
        
			//有一个不选上则全不选
			$('input[type="checkbox"][name="userId"]').click(function () {
				   var ckall = true;
				   $('input[type="checkbox"][name="userId"]').each(function (){
					if (!this.checked){ 
						ckall = false;  
						//直接退出循环,不在进行each循环
						return false; 
						}});
				   
				   $('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		   });			
		});
		
		
		function showTrByName(nameId){
			var trNameId="tr_"+nameId;
			//alert(nameId);
			$("tr[name='"+trNameId+"']").slideToggle();
			
			var plusUrl="${ctx}/images/navigate_plus.png";
			var minusUrl="${ctx}/images/navigate_minus.png";

			//此处进行判断
			//如果为+号  则变成—号
			if($("#img_"+nameId+"").attr("src").indexOf("navigate_plus.png")>0){
				$("#img_"+nameId+"").attr("src",minusUrl);
			}else{
				$("#img_"+nameId+"").attr("src",plusUrl);
			}
			
			
			//$("#img_47").attr("src",minusUrl);
			/**
			
			//img_
			*/
			
		}
    </script>
</head>

<body>

<div class="page_title"><h3>任务管理  -- ${CURRENT_MEETING}</h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">	
	    <div title="任务列表" style="padding:10px;">
		<div class="page_tools">
		<form id="listUserForm" action="${ctx}/admin/pri/task/getDetailListPager.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="80%">
				<tr>
				<th style="width: 120px; ">主任务名称：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px; " id="taskDetailName" name="taskDetailName" value="${taskDetailName}"/></td>
				<td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
				</td>
				</tr>
			</table>
		</form>
		</div>
		
		<div class="page_tools page_toolbar">
	<a class="btn_common btn_false"  href="${ctx}/admin/pri/task/toAddDetail.action?meetingId=${meetingId}&taskId=${taskId}">添加任务</a>
</div>
		
		<div>
		<table class="page_datalist">
		<thead>
			<tr>
				<th width="3%" style="border-right: 0"></th>
				<th width="10%">名称</th>
				<th width="18%">负责人</th>
				<th width="10%">状态</th>
				<th width="12%">开始时间</th>
				<th width="12%">结束时间</th>
				<th width="12%">内容</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		
		<tbody id="Tbody">
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="detail" items="${pager.pageRecords}" varStatus="status">
						<tr>
							<td><a href="#" onClick="showTrByName(${detail.id})">
							<c:if test="${not empty detail.sonMeetingTaskDetailList }">
							<img id="img_${detail.id}" src="${ctx}/images/navigate_plus.png"/>
							</c:if>
							</a></td>
							<td><a href="##" onclick="edit(${detail.id})"><font size="3px">${detail.detailName}</font></a></td>
							<td>${fn:substring(detail.chargeName, 0, fn:length(detail.chargeName)-1)}</td>
					 	<td>
							<c:if test="${detail.status eq '0'}"> 未开始</c:if>
							<c:if test="${detail.status eq '1'}"> 开始</c:if>
						    <c:if test="${detail.status eq '2'}"> 进行中</c:if>
						 	<c:if test="${detail.status eq '3'}"> 部分完成</c:if>
							<c:if test="${detail.status eq '4'}"> 完成</c:if>
						</td>
							
						<td> ${fn:substring(detail.executeStartTime,0,16)}</td>
						<td> ${fn:substring(detail.executeEndTime,0,16)}</td>
						<td>${detail.detailDescription}</td>
						<td><a href="##" onclick="edit(${detail.id})">编辑</a>&nbsp;<a href="##" onclick="del(${detail.id})">删除</a>
						 <%-- <a href="##" onClick="getTaskDetailListJson(${meetingId},${detail.id},tr_${detail.id});" >查看子任务</a> --%>	
							<c:if test="${0 eq detail.parentId}">
							<a  href="${ctx}/admin/pri/task/toAddDetail.action?meetingId=${meetingId}&parentId=${detail.id}">添加子任务</a>
						 	<%-- 	<a href="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${meetingId}&parentId=${detail.id}">查看子任务</a>
						 	--%>
						 	</c:if>
						</td>
						</tr>
						
						<!-- 展示子任务START -->
						<c:choose>
						<c:when test="${not empty detail.sonMeetingTaskDetailList }">
						<div style="display:none">
						
						<c:forEach var="sondetail" items="${detail.sonMeetingTaskDetailList}" varStatus="status">
						<tr name="tr_${detail.id}"  class="even" style="display:none">
							<td></td>
							<td><a href="##" onclick="edit(${sondetail.id})">${sondetail.detailName}</a></td>
							<td> ${fn:substring(sondetail.chargeName, 0, fn:length(sondetail.chargeName)-1)} </td>
					 	<td>
							<c:if test="${sondetail.status eq '0'}"> 未开始</c:if>
							<c:if test="${sondetail.status eq '1'}"> 开始</c:if>
						    <c:if test="${sondetail.status eq '2'}"> 进行中</c:if>
						 	<c:if test="${sondetail.status eq '3'}"> 部分完成</c:if>
							<c:if test="${sondetail.status eq '4'}"> 完成</c:if>
						</td>
						<td> ${fn:substring(sondetail.executeStartTime,0,16)}</td>
						<td> ${fn:substring(sondetail.executeEndTime,0,16)}</td>
						<td> ${sondetail.detailDescription}</td>
						<td>
							<a href="##" onclick="edit(${sondetail.id})">编辑</a>
								&nbsp;
							<a href="##" onclick="del(${sondetail.id})">删除</a>
					    </td>
					 </tr>
					</c:forEach>
					
					</div>
				    </c:when>
				    </c:choose> 
				    
				    <!-- 展示子任务EDN-->
				    
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无任务信息.</td></tr>
				</c:otherwise>
			</c:choose>
		
	</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>

	<div title="我的任务" link="${ctx}/admin/pri/task/getDetailListPager.action?meetingId=${meetingId}&myTaskFlag=1" style="padding:10px;"></div>
	
</div>



<script type="text/javascript">
	function del(id)
	{
		if(confirm("您确认进行删除操作？"))
		{
			var url  = "/admin/pri/task/deleteDetail.action?id="+id;
			//alert(url);
			ajaxRequest(url,finishDel,false);
			location.reload();
		}
	}
	
	function finishDel(data){
		alert(data.retMsg);
	}
	
	function edit(id){
		window.location.href = "${ctx}/admin/pri/task/toUpdateDetail.action?id="+id;
	}
	
    function forwardReq(pagePath) {
        window.location.href = pagePath;
    }

    function query() {
        $('#listUserForm').submit();
    }
    
    function getTaskDetailListJson(meetingId,parentId,obj){
    	alert(obj);
    	var url="/admin/pri/task/getDetailListJson.action?meetingId="+meetingId+"&parentId="+parentId;
    	ajaxRequest(url,getListJson,false);
        function getListJson(data){
        	var tmp="<tr><td>asdfasdfasdfasdfasdfasd</td><td></td><td></td><td></td><td></td><td></td></tr>";
        	document.getElementById("Tbody").append(tmp);
        	alert(tmp);
        }
    }
    

    
</script>


</body>
</html>

