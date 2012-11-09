<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会务通平台</title>
	${admin_css}                                   
	${jquery_js}                                
	${jquery_form_js}                                 
	${admin_js}                   
	${util_js} 
	${WdatePicker_js}
	<style type="text/css">
	.result_content {
		border:1px dashed #000000; 
		padding:4px 10px 10px 10px; 
		width:420px; 
		background:#FFFFBF; 
		overflow:auto;
	}
	
	.result_content .close_button {
		margin-bottom:5px; 
	}
	
	.result_content .close_button a {
		color:#ff0000;
		float: right;
	}
	
	.result_content .close_button a:hover {
		background:#C0C0C0;
	}
	
	.result_content .result {
		margin-bottom:2px; 
		border-bottom: 1px dotted #C5C5C5;
	}
	
	.result_content .result a {
		float: right;
	}
	
	.result_content .result a:hover {
		background:#C0C0C0;
	}
	
	.specialty_info {
		width:90%;
		border:1px dashed #000000;
		margin-top:10px;
	}
	
	.specialty_info img {
		width:180px;
		height:120px;
		float:right;
		margin:10px;
	}
	
	.specialty_info #specialty_memo {
		padding:10px;
		
	}
	
	.clear {
		float: none;
		clear: both;
	}
	</style>
</head>
<body>
	<div class="page_title">
		<s:if test="flag=='edit'">
		<h3>修改签到事件</h3>
		</s:if>
		<s:else>
		<h3>添加签到事件</h3>
		</s:else>
	</div>
	<div class="page_form">
	<form id="addGuestForm" action="${ctx}/admin/pri/meeting/signEvent_add.action" method="post">
	    <input type="hidden"  name="meetingId" value="${meetingId}"/>
	    <input type="hidden"  name="id" value="${id}"/>
	    
	    <fieldset>
	        <legend></legend>
	        
	         <dl style="width:40%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>事件名称：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="eventName"  name="eventName" value="${signEvent.eventName}"   maxlength="30" style="width: 150px"/>
	            	<font id="tip_eventName" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	         <dl style="width:40%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>签到日期：</label>
	            </dt>
	            <dd>
	            	<input id="signDate" type="text"
							name="signDate" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="${signEvent.signDate}"
							readonly="readonly" />
				<font id="tip_signDate" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	     
	    </fieldset>
	    
	   <fieldset>
	        
	           <dl style="width:40%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>签到开始时间：</label>
	            </dt>
	            <dd>
					<input id="signInBeginTime"
							type="text" name="signInBeginTime" class="Wdate"
							onfocus="WdatePicker({dateFmt:'HH:mm',startDate:'%H:00:00'})"
							value="${signEvent.signInBeginTime}"
							readonly="readonly" />
					<font id="tip_beginTime" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	       
	       
	        <dl style="width:40%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>签到结束时间：</label>
	            </dt>
	            <dd>
					<input id="signInEndTime"
							type="text" name="signInEndTime" class="Wdate"
							onfocus="WdatePicker({dateFmt:'HH:mm',startDate:'%H:00:00',minDate:'#F{$dp.$D(\'signInBeginTime\')||\'2020-10-01\'}'})"
							value="${signEvent.signInEndTime}"
							readonly="readonly" />
					<font id="tip_endTime" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	      
	      
	    </fieldset>
	    
	     <fieldset>
	         <dl style="width:40%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>签退开始时间：</label>
	            </dt>
	            <dd>
					<input id="signOutBeginTime"
							type="text" name="signOutBeginTime" class="Wdate"
							onfocus="WdatePicker({dateFmt:'HH:mm',startDate:'%H:00:00'})"
							value="${signEvent.signOutBeginTime}"
							readonly="readonly" />
					<font id="tip_beginTime_event" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	        <dl style="width:40%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>签退结束时间：</label>
	            </dt>
	            <dd>
					<input id="signOutEndTime"
							type="text" name="signOutEndTime" class="Wdate"
							onfocus="WdatePicker({dateFmt:'HH:mm',startDate:'%H:00:00',minDate:'#F{$dp.$D(\'signOutBeginTime\')||\'2020-10-01\'}'})"
							value="${signEvent.signOutEndTime}"
							readonly="readonly" />
					<font id="tip_endTime_event" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	      
	    </fieldset>
	    
	    <div class="page_form_sub">
	        <a href="#" id="submitBtn" class="btn_common btn_true">保 存</a>
	        <a href="${ctx}/admin/pri/meeting/signEvent_findAll.action?meetingId=${meetingId}" id="retBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function(){
		var flag="${flag}";
		if(flag=="edit"){
			$("#submitBtn").html("修改");
		}
		
		$("#submitBtn").click(function(){
			if(validateAdd()){
				$("#addGuestForm").submit();
			}
		});
	});

	//新增校验
	function validateAdd() {
		var result = false;
		
		 $("[id^='tip_']").hide();
		 
		var eventName=$("#eventName").val();
		if(isEmpty(eventName)){
			 $("#tip_eventName").html("请输入事件名称");
		     $("#tip_eventName").show();
		     return result;   
		}
		
		if(eventName.indexOf(",") != -1 || eventName.indexOf(";") != -1){
			 $("#tip_eventName").html("事件名称中不能含有逗号和分号");
		     $("#tip_eventName").show();
		     return result;   
		}
		
	    var signDate = $("#signDate").val();
	    if (isEmpty(signDate)) {
	        $("#tip_signDate").html("请输入签到日期");
	        $("#tip_signDate").show();
	        return result;   
	    }
		    
		
	    var beginTime = $("#signInBeginTime").val();
		
	    if (isEmpty(beginTime)) {
	        $("#tip_beginTime").html("请输入签到开始时间");
	        $("#tip_beginTime").show();
	        return result;   
	    }
	    var endTime = $("#signInEndTime").val();
	    if (isEmpty(endTime)) {
	        $("#tip_endTime").html("请输入签到结束时间");
	        $("#tip_endTime").show();
	        return result;   
	    }
	    
	 
	    
   		var beginTime_event = $("#signOutBeginTime").val();
		
	    if (isEmpty(beginTime_event)) {
	        $("#tip_beginTime_event").html("请输入签退开始时间");
	        $("#tip_beginTime_event").show();
	        return result;   
	    }
	    var endTime_event = $("#signOutEndTime").val();
	    if (isEmpty(endTime_event)) {
	        $("#tip_endTime_event").html("请输入签退结束时间");
	        $("#tip_endTime_event").show();
	        return result;   
	    }
	    return true;
	}
	
</script>

</body>
</html>