<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
</head>
<body>
	<div class="page_title">
		<h3>卡牌挂失</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/meeting/card_lost.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="cardId" name="issueCard.id" value="${issueCard.id}"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>挂失原因：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="lossReason" name="issueCard.lossReason"  value="${issueCard.lossReason}" maxlength="50" tabindex="1"/>
	            	<font id="tip_lossReason" style="line-height:35px" color="red"></font>
	                <small>请输入卡牌挂失原因。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function() {
		$("#uid").focus();
	});

	//新增校验
	function validate() {
		var result = true;
	    var lossReason = $("#lossReason").val();
	    if (isEmpty(lossReason)) {
	        $("#tip_lossReason").html("请输入卡牌挂失原因。");
	        $("#tip_lossReason").show();
	        result = false;
	    }
	    
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