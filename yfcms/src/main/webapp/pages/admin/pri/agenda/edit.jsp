<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>安徽电信会议云平台</title>
${style_css} ${admin_css} ${jquery_js} ${WdatePicker_js} ${jmpopups_js} ${util_js}
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
<div class="page_title"><h3>会议议程 -- ${CURRENT_MEETING}</h3></div>

<c:choose>
	<c:when test="${empty meetingId}">
		<c:set var="meetingId" value="${agenda.meetingId}"/>
		<c:set var="title" value="编辑"/>
	</c:when>   
	<c:otherwise>
		<c:set var="title" value="新增"/>
	</c:otherwise>
</c:choose>

<div class="easyui-tabs" border="false" style="padding:10px;">
	<div title="议程管理" link="${ctx}/admin/pri/agenda/agendaList.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="议程编辑" selected="true" style="padding:10px;">
		<div class="page_form">
			<fieldset><legend>${title}</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>标题：</label>
	            </dt>
	            <dd><input id="topic" type="text" class="textInput" 
							value="${agenda.topic}" size="32" maxlength="128" />
					<font id="tip_topic" style="line-height:35px" color="red"></font>
				</dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">人员：</label>
	            </dt>
	            <dd>
	            	<input id="host" type="text" class="textInput" 
							value="${agenda.host}" size="32" maxlength="30"/>
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
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>开始时间：</label>
	            </dt>
	            <dd>
					<input id="startTime"
							type="text" name="startTime" class="Wdate"
							value="${agenda.startTime}"
							onfocus="WdatePicker({dateFmt:'HH:mm'})"
							readonly="readonly" />
					<font id="tip_start" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>结束时间：</label>
	            </dt>
	            <dd>
	  				<input id="endTime" type="text"
							name="endTime" class="Wdate"
							value="${agenda.endTime}"
							onfocus="WdatePicker({isShowClear:false,dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startTime\')||\'2020-10-01\'}'})"
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
					<a href="#" id="agendaAdd" class="btn_common btn_true">保存</a>
					<input type="hidden" value="${agenda.id}" id="id" />
					<input type="hidden" value="${meetingId}" id="meetid" />
				</div>
			</div>
		</div>
	</div>
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
			$("#description").bind("keyup", function(event){
				var maxLength = 512;
				if(event.which == 8){// backspace, skip content length check.
					$("#text_limit").html(maxLength-$(this).val().length);
					return true;
				}
				
				if($(this).val().length > maxLength){
					$(this).val($(this).val().substring(0,maxLength));
					$("#text_limit").html(0);
					// scroll to bottom
					$(this).scrollTop(99999) 
					$(this).scrollTop($(this).scrollTop()*12)					
					return false;
				}else{
					$("#text_limit").html(maxLength - $(this).val().length);
				}
			});
			
			$("#agendaAdd").click(
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
							"description" : $("#description").val()
						},
						function(data, textStatus) {
							if (textStatus == "success") {
								if (data.result) {
									location.href = "${ctx}/admin/pri/agenda/agendaList.action?meetingId=" + $("#meetid").val();
								} 
							}
						}, 
						"json"
				);
					return false;
			});
		});
</script>
</body>
</html>