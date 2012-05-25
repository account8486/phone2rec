<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会议议程</title>
	${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title"><h3>会议议程 -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="议程管理" style="padding:10px;">
		<div>
			<form id="listAgendaForm" action="${ctx}/admin/pri/agenda/agendaList.action">
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
				<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
				<input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
				<input type="hidden" id="queryForList" />
			</form>
		</div>
		<div>
		<table class="page_datalist">
	    	<thead>
				<tr>
					<th scope="col" width="10%">日期</th>
					<th scope="col" width="10%">时间</th>
					<th scope="col" width="10%">人员</th>
					<th scope="col" width="40%">标题</th>
					<th scope="col" width="10%">地点</th>
					<th scope="col" width="10%">分组</th>
					<th scope="col" width="10%">操作</th>
				</tr>	    	
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="meetingagenda" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td align="left">${meetingagenda.date }</td>
                            	<td align="left"> ${meetingagenda.startTime } - ${meetingagenda.endTime }</td>
				          		<td align="left">${meetingagenda.host }</td>
				          		<td align="left">${meetingagenda.topic }</td>
				          		<td align="left">${meetingagenda.location }</td>
				          		<td align="left">${meetingagenda.description }</td>
				          		<td align="center">
				          		<a href="javascript:void(0);" class="choose" id="${meetingagenda.id }">分组</a>
				          		 &nbsp;<a href="javascript:void(0);" class="edit" id="${meetingagenda.id }">编辑</a>
				          		  &nbsp;<a href="javascript:void(0);" class="delete" id="${meetingagenda.id }">删除</a>
				          		</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">没有会议议程信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
	    <c:if test="${pager.pageCount > 1}">
			<%@ include file="/pages/common/page.jsp" %>
		</c:if>
		</div>		
		</div>
		<div title="议程编辑" link="${ctx}/admin/pri/agenda/agendaAdd.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="议程导入" link="${ctx}/admin/pri/agenda/agendaImport.action?meetingId=${meetingId}" style="padding:10px;"></div>
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
			$("#queryForList").click (function() {$('#listAgendaForm').submit();});
			
			$("a.delete").click(
				function() {
					if(confirm("确定要删除该议程吗?")){
						var id = $(this).attr('id');
						$.post(
								"<c:out value='${ctx}'/>/admin/pri/agenda/agendaDelete.action",
								{"id" :　id	},
								function(data, textStatus) {
									if (textStatus == "success") {
										if (data.result) {
											location.reload();
										} else {
											// error
										}
									} else {
										// error
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
					window.location.href = "<c:out value='${ctx}'/>/admin/pri/agenda/agendaEdit.action?id="+id;
					return false;
				}
			);
			
			$("a.choose").click(
				function() {
					var id = $(this).attr('id');
					window.location.href = "<c:out value='${ctx}'/>/admin/pri/agenda/agendaChoose.action?meetingAgenda.id="+id;
					return false;
				}
			);
		}
	);
	</script>
</body>
</html>