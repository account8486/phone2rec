<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会场服务</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block; margin-top:10px;">      
		<input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
		<input type="hidden" name="menu_name" id="menu_name" value="${param.menu_name}"/>	
		
		<fieldset style="margin-top:10px;" ><textarea id="message" rows="3" cols="40" placeholder="请输入您的需要的服务信息，请求信息会提交给会务人员来处理。"></textarea></fieldset>
		
		<p style="margin:0px auto; text-align:center; overflow:auto; padding:10px;">
			<a href="javascript:doRequest()" id="postAdd" class="login_btn1">请求服务</a>
		</p>
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.msg_list {}
	ul.msg_list li { padding:10px; border-bottom:1px solid #ccc;}
	ul.msg_list li.even { background:#f2f2f2;}
	ul.msg_list li a { display:block;}
	ul.msg_list li span.time { float:right; color:#999;}
	ul.msg_list li span.name { color:#069; font-weight:bold;}
	a.login_btn { width:48%;  height:38px; display:block; float:left; line-height:38px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
	a.login_btn1 { width:100px;  height:32px; display:block; float:left; line-height:32px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
	a.login_btn2 { width:48%;  height:40px; display:block; float:right; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#333; background:url(${ctx}/images/touch/box_t_bg.png) top center repeat-x; border-left:1px solid #ccc;  border-right:1px solid #ccc;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});
	
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
				"clientType": "touch",
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