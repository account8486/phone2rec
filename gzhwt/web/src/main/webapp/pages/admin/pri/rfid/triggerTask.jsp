<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>触发任务管理</title>
	
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	${util_js}
</head>
<body>
	<div class="page_title">
		<h3>触发任务管理</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="卡牌信息" link="${ctx }/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="卡牌发放" link="${ctx}/admin/pri/meeting/card_queryOwner.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="触发任务管理" selected="true" style="padding:10px;"></div>
	</div>
	
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/meeting/card_saveTaskConfig.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="triggerTask.id" name="triggerTask.id" value="${triggerTask.id}"/>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:12px;line-height:20px; font-size:14px; font-style:normal; color:red;">
			${errMsg }</font>
		</div>
		
	    <fieldset>
	        <legend>配置RFID触发任务属性</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>RFID触发动作：</label>
	            </dt>
	            <dd>
	            	<label><input type="checkbox" id="signIn" name="triggerTask.signIn"  value="1" ${triggerTask.signIn==1 ? "checked" : "0" } tabindex="1" style="margin:0 10px 0 0;"/>触发时记录签到信息</label>
	            	<font id="tip_triggerNotify" style="line-height:35px" color="red"></font>
	                <small>指定当触发一个RFID标签时是否记录签到信息。</small>
	            </dd>
	        </dl>
	        <div id="otherItems">
	        <%-- 
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>延迟执行时间：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="delayTime" name="triggerTask.delayTime"  value="${empty triggerTask.delayTime ? '0' : triggerTask.delayTime}" maxlength="2" tabindex="1" style="width:60px;"/> &nbsp;&nbsp;(单位：秒)
	            	<font id="tip_delayTime" style="line-height:35px" color="red"></font>
	                <small>指定当触发时延迟执行任务的时间，为0则表示立即执行通知。</small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>&nbsp;</label>
	            </dt>
	            <dd>
	            	<label><input type="checkbox" id="sendMeetingAgenda" name="triggerTask.sendMeetingAgenda"  value="1" ${triggerTask.sendMeetingAgenda==1 ? "checked" : "" } tabindex="1" style="margin:0 10px 0 0;"/>触发时发送会议议程信息</label>
	            	<font id="tip_sendMeetingAgenda" style="line-height:35px" color="red"></font>
	                <small>指定当触发时是否发送会议议程信息。</small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>&nbsp;</label>
	            </dt>
	            <dd>
	            	<label><input type="checkbox" id="sendSMS" name="triggerTask.sendSMS"  value="1" ${triggerTask.sendSMS==1 ? "checked" : "" } tabindex="1" style="margin:0 10px 0 0;"/>触发时发送短信通知</label>
	            	<font id="tip_sendSMS" style="line-height:35px" color="red"></font>
	                <small>指定当触发时是否发送短信通知。</small>
	            </dd>
	        </dl>
	        <dl id="dlSmsTemp">
	        	<dt>
	            	<label for="title"><font color="red">*</font>发送短信模板：</label>
	            </dt>
	            <dd>
	            	<textarea id="smsTemplate" name="triggerTask.smsTemplate" style="width:80%; height:80px;">${triggerTask.smsTemplate }</textarea>
	            	<br/><font id="tip_smsTemplate" style="line-height:35px" color="red"></font>
	                <small>指定当触发时要发送的短信内容模板，其中{xxx}为系统标记符，不可修改，不超过100个字符。</small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>&nbsp;</label>
	            </dt>
	            <dd>
	            	<label><input type="checkbox" id="displayWelcome" name="triggerTask.displayWelcome"  value="1" ${triggerTask.displayWelcome==1 ? "checked" : "" } tabindex="1" style="margin:0 10px 0 0;"/>触发时在显示欢迎信息</label>
	            	<font id="tip_displayWelcome" style="line-height:35px" color="red"></font>
	                <small>指定当触发时是否在电子屏幕上显示欢迎信息。</small>
	            </dd>
	        </dl>
	        --%>
	        
	        </div>
	    </fieldset>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保存</a>
	        <%-- 
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
	        --%>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	
	$(function() {
		var sendSMS = "${triggerTask.sendSMS}"; //是否发送短信
		var sendMeetingAgenda = "${triggerTask.sendMeetingAgenda}"; //是否发送会议议程
		
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
		
		if(sendSMS != "1") {
			$("#dlSmsTemp").hide();
		}
		
		$("#sendSMS").click(function() {
			if($(this).attr("checked")) {
				$("#dlSmsTemp").show();
			} else {
				$("#dlSmsTemp").hide();
			}
		});
	});

	//新增校验
	function validate() {
		var result = true;
		
		/*
		var delayTime = $("#delayTime").val();
		if(! /^\d+$/.test(delayTime)) {
			$("#tip_delayTime").html("任务延迟执行时间必须是一个有效的整数。");
	        $("#tip_delayTime").show();
	        result = false;
		}
		
	    var sendSMSChecked = $("#sendSMS").attr("checked");
	    var smsTemplate = $("#smsTemplate").val();
	    if (sendSMSChecked && isEmpty(smsTemplate)) {
	        $("#tip_smsTemplate").html("触发时要发送的短信内容模板不能为空。");
	        $("#tip_smsTemplate").show();
	        result = false;
	    }
	    if (sendSMSChecked && smsTemplate.length > 100) {
	        $("#tip_smsTemplate").html("触发时要发送的短信内容模板的字数不能超过100个字符。");
	        $("#tip_smsTemplate").show();
	        result = false;
	    }
	    */
	    
	    return result;
	}
	
	function save(){
		$("[id^='tip_']").hide();
	   	var tmp_bool = validate();
	    if (tmp_bool != true) {
	        return false;
	    }
	    
		$("#mainForm").submit();
		$("#submitBtn").attr("disabled","disabled");
	}
		
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}";
	}
	
</script>

</body>
</html>