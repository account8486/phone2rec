<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/theme.css" />
${admin_css}                                   
${jquery_js}                                
${admin_js}                   
${util_js}      

<style type="text/css">
	.servicebox {
		margin:10px 0;
		padding:10px;
		height:300px;
		overflow: auto;
	}
	
	.service {
		padding:10px;
		margin:10px;
		border:1px dashed gray;
	}
	
	.msg1 {
		font-size:18px;
		font-weight:bold;
		padding:5px 10px;
	}
	
	.msg2 {
		font-size:16px;
		text-align:right;
		border-top:none;
		padding: 5px 10px;
	}
	
	.clear {
		float:none;
		clear:both;
	}
</style>
</head>
<body>
		
<div class="mainbox">
	<div class="page_title" style="display:block"><h3>会场服务  -- ${CURRENT_MEETING}</h3></div>
   	
    <div class="servicebox">
    <%-- 
		<div class="service">
			<h1 class="msg1">请求续茶</h1>
			<h2 class="msg2">--张国敬于2012-04-23 17:30:23请求服务</h2>
			<div class="clear"></div>
		</div>
	--%>	
	</div>
	
	<div class="page_form_sub">
        <a href="##" onclick="clearAll();" class="btn_common btn_true">清空</a>    
    </div>
</div>
	
<script type="text/javascript">
	var timer; //计时器
	var meetingId = "${meetingId}";
	
	$(function() {
		//动态计算页面的高度
		$(".servicebox").css("height", $("body").outerHeight() - 20);
		
		timer = setInterval(seekServiceInfo, 2000); //2秒钟执行一次
	});
	
	//检索是否有服务请求信息
	function seekServiceInfo() {
		var url = "${ctx}/admin/pri/meeting/liveService_seek.action?meetingId=" 
				+ meetingId + "&time=" + new Date().getTime();
		$.get(url, function(data) {
			if(data.result == "true") {
				var div = "<div class='service' id='" + data.serviceInfo.id + "'>"
						+ "  <h1 class='msg1'>(" + data.serviceInfo.id + ") " + data.serviceInfo.serviceName + "</h1>"
						+ "  <h2 class='msg2'>--" + data.serviceInfo.requester.name + "于" 
							+ data.serviceInfo.requestTime + "请求服务&nbsp&nbsp;<a href='#' onclick='hideService("
							+ data.serviceInfo.id + ")'>删除</a></h2>"
						+ "  <div class='clear'></div>"
						+ "</div>";
				$(".servicebox").append(div);
				var top = $(".servicebox").scrollTop();
				$(".servicebox").scrollTop(top + 100);
			}
		});
	}
	
	//清空内容
	function clearAll() {
		if(confirm("确定清除所有的服务请求信息吗？")) {
			$(".servicebox").empty();
		}
	}
	
	//隐藏
	function hideService(id) {
		if(confirm("确定删除当前选择的服务请求信息吗？")) {
			$("#" + id).hide();
		}
	}
		
</script>
</body>
</html>