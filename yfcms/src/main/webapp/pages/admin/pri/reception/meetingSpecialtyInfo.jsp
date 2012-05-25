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
		<h3>发布土特产</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/reception/specialty_save.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="meetingSpecialtyId" name="meetingSpecialtyId" value="${meetingSpecialtyId}"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>信息描述：</label>
	            </dt>
	            <dd>
	            	<textarea id="memo" name="meetingSpecialty.memo" style="width:80%; height:100px; padding:10px;">${meetingSpecialty.memo }</textarea>
	            	<br/>
	            	<font id="tip_memo" style="line-height:35px" color="red"></font>
	                <small>请输入本次会议所发布的土特产信息描述内容，不能超过1000个字符。</small>
	            </dd>
	        </dl>
	        
	        <s:if test="meetingSpecialty.id != null">
	        <dl>
	        	<dt>
	            	<label for="title">有效状态：</label>
	            </dt>
	            <dd>
	            	<label><input type="radio"  name="meetingSpecialty.state"  value="1" ${(meetingSpecialty.id == null || meetingSpecialty.state == 1) ? "checked" : "" }/>有效</label>
	            	<label><input type="radio"  name="meetingSpecialty.state"  value="0" ${meetingSpecialty.state == 0 ? "checked" : "" } style="margin-left:15px;"/>无效</label>
	            	<font id="tip_state" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        </s:if>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保存</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function() {
		$("#memo").focus();
	});

	//新增校验
	function validate() {
		var result = true;
		var meetingId = $("#meetingId").val();
	    if ($.trim(meetingId) == "") {
	        alert("缺少会议ID参数。");
	        result = false;
	    }
	    
	    var memo = $("#memo").val();
	    if (isEmpty(memo) || memo.length > 1000) {
	        $("#tip_memo").html("请输入不超过1000个字符的土特产描述信息。");
	        $("#tip_memo").show();
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
		window.location.href = "${ctx}/admin/pri/reception/specialty_show.action?meetingId=${meetingId}";
	}
	
</script>

</body>
</html>