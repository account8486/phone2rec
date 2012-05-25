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
		<h3>发放卡牌</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/meeting/card_issue.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="issueCard.owner.id" name="issueCard.owner.id" value="${issueCard.owner.id}"/>
	    <fieldset>
	        <legend>扫描卡牌标签进行卡牌发放操作</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>标签UID：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="uid" name="card.uid"  value="${card.uid}" maxlength="50" tabindex="1"/>
	            	<font id="tip_uid" style="line-height:35px" color="red"></font>
	                <small>请使用RFID读卡器扫描标签UID。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">发卡</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
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
	    var uid = $("#uid").val();
	    if (isEmpty(uid)) {
	        $("#tip_uid").html("请使用RFID读卡器扫描标签UID。");
	        $("#tip_uid").show();
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
		window.location.href = "${ctx}/admin/pri/meeting/card_queryOwner.action?meetingId=${meetingId}&issueCard.owner.mobile=${issueCard.owner.mobile}";
	}
	
</script>

</body>
</html>