<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
</head>
<body>
	<!-- 列表功能 -->

	
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
					<th scope="col" width="15%">日期</th>
					<th scope="col" width="10%">时间</th>
					<th scope="col" width="10%">人员</th>
					<th scope="col" width="35%">议题</th>
					<th scope="col" width="10%">地点</th>
					
					
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

    	

	
	
	<script type="text/javascript">
	
	$(document).ready(
			
		function() {
			
			$("#add_agenda_div").hide();
			
			$("#toAddAgendaBtn").click(function(){
				
				setAgendaDateAndTime();
				$("#add_agenda_div").slideToggle();
			});
			
			
			$("#agendaAddBtn").click(
					
					function() {
				        if (isEmpty($("#topic").val())) {
				            $("#tip_topic").html("请输入标题。");
				            $("#tip_topic").show();
				            return false;
				        }
				        
				        if (isEmpty($("#date").val())) {
				            $("#tip_date").html("请选择日期。");
				            $("#tip_date").show();
				            return false;
				        }
				        
				        if (isEmpty($("#startTime").val())) {
				            $("#tip_start").html("请选择开始时间。");
				            $("#tip_start").show();
				            return false;
				        }
				        
				        if (isEmpty($("#endTime").val())) {
				            $("#tip_end").html("请选择结束时间。");
				            $("#tip_end").show();
				            return false;
				        }
						
						$.post(
							"${ctx}/admin/pri/agenda/agendaSave.action",
							{
								"id" :　$("#id").val(),
								"meetingId" : $("#meetid").val(),
								"host" : $("#host").val(),
								"topic" : $("#topic").val(),
								"date" : $("#date").val(),
								"startTime" : $("#startTime").val(),
								"endTime" : $("#endTime").val(),
								"location": $("#location").val(),
								"description" : $("#description").val(),
								"attendee": $("#recieverIds").val()
							},
							
							function(data, textStatus) {
								if (textStatus == "success") {
									alert("保存成功!");
									if (data.result) {
										location.href = "${ctx}/admin/pri/agenda/agendaList.action?meetingId=" + $("#meetid").val();
									} 
								}
							}, 
							"json"
					);
						return false;
				}
				
				
				
				
				
				//$("#add_agenda_div").slideToggle();
	);
			
			$("#agendaAddCancelBtn").click(function(){
				$("#add_agenda_div").slideToggle();
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