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
		<h3>发放卡牌</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="卡牌信息" link="${ctx }/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="卡牌发放" link="${ctx}/admin/pri/meeting/card_queryOwner.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="自动发卡" selected="true" style="padding:10px;"></div>
		<%--
		<div title="触发任务管理" link="${ctx}/admin/pri/meeting/card_taskConfigReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		--%>
	</div>
	
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/meeting/card_autoIssue.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <c:if test="${userCount-count==0}">
	    	<span style="margin-left: 20px;font-size: 15px;font-weight: bold;color:RGB(150,200,252);">(所有参会人员都已经发卡,不能再次发卡)</span>
	    </c:if>
		<c:if test="${userCount-count>0}">
		    <span style="margin-left: 20px;font-size: 15px;font-weight: bold;color:RGB(150,200,252);">(共有[${userCount}]个参会人员,已发卡[${count }]人,还需发卡[${userCount-count}]人)</span>
		</c:if>
	    <fieldset>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>标签ID：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="uid" name="card.uid"  value="${card.uid}" maxlength="50" tabindex="1"/>
	            	<font id="tip_uid" style="line-height:35px" color="red"></font>
	                <small>请输入标签ID，标签ID为6位数字组合。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">自动发卡</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function() {
		$("#uid").focus();
		
		var count="${userCount-count}";
		if(count==0||count=='0'){
			$("#submitBtn").hide();
		}
		
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
	    var uid = $("#uid").val();
	    if (isEmpty(uid)) {
	        $("#tip_uid").html("请输入标签ID。");
	        $("#tip_uid").show();
	        result = false;
	    }
	    
	    if(! /^\d{6}$/.test(uid)) {
	    	$("#tip_uid").html("标签ID必须是6位数字组合。");
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