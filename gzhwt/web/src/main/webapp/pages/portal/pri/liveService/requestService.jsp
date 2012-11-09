<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>

<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption"></h5></div></div>

	<div class="">
		<input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
		<input type="hidden" name="menu_name" id="menu_name" value="${param.menu_name}"/>	
		
		<div><textarea id="message" style="width:80%;height:50px; padding:10px; font-size:14; font-weight:bold;"></textarea></div>
		<div style="padding:10px; color:#aaa;">请输入您的需要的服务信息，请求信息会提交给会务人员来处理。</div>
		
		<br/>
		<div>
			<a href="javascript:doRequest()" id="postAdd" class="btn_blue" style="margin-bottom:5px;">请求服务</a>
		</div>
	</div>

       
</div>
<%@ include file="/pages/portal/common/footer.html" %>
	
<script type="text/javascript">

function tea() {
	$("#message").val("请续茶");
}

function doRequest() {
	if ($("#message").val() == "" ) {
		alert("请输入您的服务请求。");
		return;
	}
	
	var url = "${ctx}/portal/pri/meeting/liveService_submitRequest.action";
	var data = {
			"meetingId":"${meetingId}", 
			"clientType": "portal",
			"liveService.requester.id": "${SESSION_USER.id}",
			"liveService.serviceName": $("#message").val()
		};
	$.post(url, data, function(ret) {
		if(ret.result == "true") {
			alert("服务请求信息成功发送到会务中心，请稍候。");
			$("#message").val("");
		}
	});
}
	
	
</script>