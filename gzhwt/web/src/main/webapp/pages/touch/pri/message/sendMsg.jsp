<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">私信</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="margin:10px; display:block;">
       	<p style="display:block">
     			<a href="javascript://" class="recipient login_btn1">收信人</a>
     			<input type="hidden" name="userIds" id="userIds"  value="${userIds}">
     			
     			<c:choose>
			<c:when test="${not empty users}">
			<c:forEach var="user" items="${users}" varStatus="status">
				<span id="recipient" style="margin-left:5px;line-height:38px;">${user.name}<c:if test="${not empty user.job && user.isShowJob eq '1'}">${user.job}</c:if></span>
			</c:forEach>
     			</c:when>
     			<c:otherwise>
     				<span id="recipient" style="margin-left:5px; line-height:38px;">请选择收信人</span>
     			</c:otherwise>
     			</c:choose>
     		</p>
     		<fieldset style="margin-top:10px;" ><textarea id="content" rows="6" cols="40" name="content">${content }</textarea></fieldset>
     		<p style="color:#999;font-size:12px;width:80%;">目前可以输入<span id="text_limit">512</span>个字符</p>
		<a href="javascript://" class="send login_btn1" style="margin-top:5px;" >发送</a><br/><br/>
		<input type="hidden" value="${not empty param.meetingId ? param.meetingId : meeting.id}" id="meetingId" />
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
<script src="${ctx }/js/util.js"></script>
<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
		
		//初始化时进行
		var maxLength = 512;
		$("#text_limit").html(maxLength-$("#content").val().length);
		
	});
	
	$("a.recipient").click(function() {
		//此处把userId回传回去
		
		location.href = "${ctx}/touch/pri/msg/selectUser.action?meetingId="+$("#meetingId").val()+"&userIds="+$("#userIds").val()+"&content="+$("#content").val();
		return false;
	});
	
	
	$("a.send").click(function() {
		if (isEmpty($("#content").val())) {
            alert("请输入内容。");
            return false;
        }
        if ($("#recipient").text() == "请选择收信人") {
            alert("请选择收信人。");
            return false;
        }
        var menu_id = "";
        $.post(
			"${ctx}/touch/pri/msg/send.action",
			{"message" : $("#content").val(),"meetingId": "${meetingId}","userId" : "${selectuser}"},
			function(data, textStatus) {
				if (textStatus == "success" && data.retcode == 0) {
					location.href = "${ctx}/touch/pri/msg/listMessageUsers.action?meetingId=${meetingId}&menu_id=" + menu_id + "&select=outbox";
					return false;
				}
			}, 
			"json"
		);
	});
	
	$("#content").bind("keyup", function(event){
		var maxLength = 512;
		if(event.which == 8){// backspace, skip content length check.
			$("#text_limit").html(maxLength-$(this).val().length);
			return true;
		}
		
		if($(this).val().length > maxLength){
			$(this).val($(this).val().substring(0,maxLength));
			$("#text_limit").html(0);
			// scroll to bottom
			$(this).scrollTop(99999) 
			$(this).scrollTop($(this).scrollTop()*12)					
			return false;
		}else{
			$("#text_limit").html(maxLength - $(this).val().length);
		}
	});
</script>