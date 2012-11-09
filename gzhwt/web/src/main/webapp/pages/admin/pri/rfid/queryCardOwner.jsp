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
	
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	
	${util_js}
</head>
<body>
	<div class="page_title">
		<h3>搜索参会人员信息</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="卡牌信息" link="${ctx }/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="卡牌发放" selected="true" style="padding:10px;"></div>
		<%--
		<div title="触发任务管理" link="${ctx}/admin/pri/meeting/card_taskConfigReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		--%>
	</div>
	
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/meeting/card_queryOwner.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <fieldset>
	        <legend>根据参会人员姓名或手机号码搜索参会人员信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>姓名：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="username" name="issueCard.owner.name"  value="${issueCard.owner.name}" maxlength="50" tabindex="1"/>
	            	<font id="tip_username" style="line-height:35px" color="red"></font>
	                <small>根据姓名搜索参会人员。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>手机：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="mobile" name="issueCard.owner.mobile"  value="${issueCard.owner.mobile}" maxlength="50" tabindex="1"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>根据手机号码搜索参会人员。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">搜索</a>
	        <%-- 
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
	        --%>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function() {
		$("#username").focus();
		
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
	});

	//新增校验
	function validate() {
		var result = true;
	    var username = $("#username").val();
	    var mobile = $("#mobile").val();
	    if (isEmpty(username) && isEmpty(mobile)) {
	        $("#tip_err_msg").html("搜索条件的参会人员姓名和手机不能同时为空。");
	        $("#tip_err_msg").show();
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