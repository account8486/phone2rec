<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会议议程</title>
	${admin_css} ${jquery_js} ${jmpopups_js} ${util_js} ${WdatePicker_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
		<script type="text/javascript" src="${ctx}/js/ajax-public.js"></script>
	<style type="text/css">
	.page_form fieldset dl dd {
	    float: left;
	    width: 75%;
	}
	</style>	
		
</head>
<body>
	<div class="page_title"><h3>会议议程 -- ${CURRENT_MEETING}</h3></div>
	
	
	<!-- 议程添加 START-->
	<div id="add_agenda_div" class="easyui-tabs" border="false" style="padding:10px;" >
	
	<div title="议程编辑" selected="true" style="padding:10px;">
		<div class="page_form" >
			<fieldset><legend>${title}</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>议题：</label>
	            </dt>
	            <dd><input id="topic" type="text" class="textInput" 
							value="${agenda.topic}" size="32" maxlength="128" />
					<font id="tip_topic" style="line-height:35px" color="red"></font>
				</dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">主持人：</label>
	            </dt>
	            <dd>
	            	<input id="host" type="text" class="textInput" 
							value="${agenda.host}" size="32" maxlength="30"/>
	            </dd>
	        </dl>

	        
	         <dl>
                <dt><label for="sendAllFlag">参议人员:</label></dt>
                <dd>
                	 <textarea name="recieverMobiles" id="recieverMobiles" readonly="readonly" disabled="disabled" style="width:600px;height:38px"></textarea>
                    <input type="hidden" name="recieverIds" id="recieverIds">
                    <a href="#" onclick="selectUsers();" id="addUserBtn" class="btn_common btn_false">选择成员</a>
                     <small>你当前选择了<font color="red" id="selectNum">0</font>个人员</small>
                    
                </dd>
            </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>日期：</label>
	            </dt>
	            <dd>
	            	<input id="date" type="text"
							name="date" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="${agenda.date}"
							readonly="readonly" />
	            	<font id="tip_date" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl style="width:43%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>开始时间：</label>
	            </dt>
	            <dd style="width:48%;float:left;">
					<input id="startTime"
							type="text" name="startTime" class="Wdate"
							value="${agenda.startTime}"
							onfocus="WdatePicker({dateFmt:'HH:mm',startDate:'%H:00:00'})"
							readonly="readonly" />
					<font id="tip_start" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl style="width:43%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>结束时间：</label>
	            </dt>
	            <dd style="width:48%;float:left;">
	  				<input id="endTime" type="text"
							name="endTime" class="Wdate"
							value="${agenda.endTime}"
							onfocus="WdatePicker({isShowClear:false,dateFmt:'HH:mm',startDate:'%H:00:00',minDate:'#F{$dp.$D(\'startTime\')||\'2020-10-01\'}'})"
							readonly="readonly" />
					<font id="tip_end" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">地点：</label>
	            </dt>
	            <dd><input id="location" type="text" class="textInput" 
							value="${agenda.location}" size="32" maxlength="128" />
				</dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">议程内容：</label>
	            </dt>
	            <dd>
					<textarea class="medium half" id="description" rows="8" cols="10"
								name="description">${agenda.description}</textarea>
					<small>目前可以输入<span id="text_limit">512</span>个字符</small>
					<font id="tip_desc" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>	        
			</fieldset>
			<div class="neidi">
				<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
				${errMsg }</font>
				<br/>
			</div>
			
			<div class="page_form_sub">
				<div style="margin-bottom: 10px; margin-top: 10px;" align="left">
					<a href="#" id="agendaAddBtn" class="btn_common btn_true">保存</a>
					<a href="#" id="agendaAddCancelBtn" class="btn_common btn_true">取消</a>
					<input type="hidden" value="${agenda.id}" id="id" />
					<input type="hidden" value="${meetingId}" id="meetid" />
				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- 议程添加END -->
	
	
	
	<!-- 列表功能 -->
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="议程管理" style="padding:10px;">
		<div>
			<form id="listAgendaForm" action="${ctx}/admin/pri/agenda/agendaList.action">
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
				<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
				<input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
				<input type="hidden" id="queryForList" />
				
			<table width="80%">
				<tr>
				<td>
					<a href="#" id="toAddAgendaBtn"  class="btn_common btn_true">快速添加</a>
				</td>
				</tr>
			</table>
			
			</form>
		</div>
		<div>
		<table class="page_datalist">
	    	<thead>
				<tr>
					<th scope="col" width="10%">日期</th>
					<th scope="col" width="10%">时间</th>
					<th scope="col" width="10%">主持人</th>
					<th scope="col" width="35%">议题</th>
					<th scope="col" width="10%">地点</th>
					<th scope="col" width="10%">分组</th>
					<th scope="col" width="15%">操作</th>
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
    	<div title="批量添加" link="${ctx}/admin/pri/agenda/toBatchAddAgenda.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="议程编辑" link="${ctx}/admin/pri/agenda/agendaAdd.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="议程导入" link="${ctx}/admin/pri/agenda/agendaImport.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
	
	
	<script type="text/javascript">
	
	function selectUsers(){
		var recieverIds=$("#recieverIds").val();
		var url="${ctx}/admin/pri/message/getMeetingMember.action?calSelectNum=true&meetingId="+$("#meetingId").val()+"&recieverIds="+$("#recieverIds").val();
		window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
	}
	
	//统计人数
	function calSelectNum(selectNum){
		//alert(selectNum);
		$("#selectNum").html(selectNum);
	}
	
	
	function setAgendaDateAndTime(){
		//alert("到后台获取议程的最新时间!");
		var url="/admin/pri/agenda/getNewestAgenda.action?meetingId="+${meetingId};
		ajaxRequest(url,getBack);
		function getBack(data){
		   $("#date").val(data.newestAgenda.date);
		   $("#startTime").val(data.newestAgenda.endTime);
		  // $("#date").val(data.newestAgenda.date);
		}
	}
	
	
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