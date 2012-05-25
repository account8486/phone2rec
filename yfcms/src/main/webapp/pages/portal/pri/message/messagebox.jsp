<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>

<div class="w960" style="min-height:300px;">
    <div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
     
  	<div class="second_title_nav">
          	<ul class="content_tab content_tab_message">
       		 	<li><a class="message" id="inbox" href="javascript://">收件箱</a></li>
               	<li><a class="message" id="outbox" href="javascript://">发件箱</a></li>
               	<li><a class="message" id="newmessage" href="javascript://">新私信</a></li>
            </ul>
   </div>    

      	<ul id="info_inbox" class="message_list">
      	<c:choose>
			<c:when test="${not empty inbox}">
			<c:forEach var="message" items="${inbox}" varStatus="status">
   	 		 <li id="${message.id }">
            	<p><span class="name"><b>发信人</b> ：${message.sender.name }<c:if test="${not empty message.sender.job && message.sender.isShowJob eq '1'}">${message.sender.job}</c:if></span></p>
            	<p><span>${message.message}</span></p>
                <p>
                	<span class="time">${message.time}</span>
                    <span class="ctrl"><a class="reply" id="${message.sender.id}" href="javascript://">回复</a> | <a class="delInbox" id="${message.id}" href="javascript://">删除</a></span>
                </p>
            </li>
      		</c:forEach>
			</c:when>
			<c:otherwise>
				<li><p><span>没有收到站内私信.</span></p></li>
			</c:otherwise>
		</c:choose>
        </ul>
        
        <ul id="info_outbox" class="message_list">
      	<c:choose>
			<c:when test="${not empty outbox}">
			<c:forEach var="message" items="${outbox}" varStatus="status">
   	 		 <li id="${message.id }">
            	<p><span class="name"><b>收信人</b> ：
            	<c:if test="${fn:length(message.recipients) > 0}">
            	<c:forEach var="recipient" items="${message.recipients}" varStatus="status">
            		${recipient.name }<c:if test="${not empty recipient.job && recipient.isShowJob eq '1'}">${recipient.job}</c:if>
            		<c:if test="${!status.last}">;&nbsp;</c:if>
            	</c:forEach>
            	</c:if>
            	</span></p>
            	<p><span>${message.message}</span></p>
                <p>
                	<span class="time">${message.time}</span>
                    <span class="ctrl"><a class="delOutbox" id="${message.id}" href="javascript://">删除</a></span>
                </p>
            </li>
      		</c:forEach>
			</c:when>
			<c:otherwise>
				<li><p><span>没有发送站内私信.</span></p></li>
			</c:otherwise>
		</c:choose>
        </ul>
        
        <div id="info_newmessage" class="message_list message_new">
        	<p>
       			<a href="javascript://" class="recipient btn_blue">收信人</a>
       			<c:choose>
				<c:when test="${not empty users}">
					<c:forEach var="user" items="${users}" varStatus="status">
						<span id="recipient">${user.name}<c:if test="${not empty user.job && user.isShowJob eq '1'}">${user.job}</c:if></span>
					</c:forEach>
       			</c:when>
       			<c:otherwise>
       				<span id="recipient">请选择收信人</span>
       			</c:otherwise>
       			</c:choose>
       		</p>
       		<p style="text-align: right;color:#999;font-size:12px;width:80%;">目前可以输入<span id="text_limit">512</span>个字符</p>
       		<fieldset><textarea id="content" rows="6" cols="28" name="content"></textarea></fieldset>
			<a href="javascript://" class="send btn_blue">发送</a>
			<input type="hidden" value="<c:out value="${meetingId}" />" id="meetid" />
        </div>
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
$(document).ready(function() {
	//内容tab切换act
	$(".content_tab li").not('.more').find('a').click(function () {
		$(".content_tab li").removeClass("act");
		$(this).parent().addClass("act");
		$(".message_list").css('display','none');
		var id = $(this).attr('id');
		$('#info_'+id).css('display','');
	});
	
	//odd
	$(".message_list li:odd").addClass("odd");
	
	//如果没有默认的ACT样式，自动为第一个添加act class
	if($('.second_title_nav').find(".act").length == 0){
		$(".message_list").css('display','none');
		if ("${select}" == "outbox") {
			$('.second_title_nav').find("li").first().next().addClass("act");
			$('.message_list').first().next().css('display','');
		} else if ("${select}" == "new") {
			$('.second_title_nav').find("li").last().addClass("act");
			$('.message_list').last().css('display','');
		} else {
			$('.second_title_nav').find("li").first().addClass("act");
			$('.message_list').first().css('display','');
		}
	}
	
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
	
	//删除收件箱
	$("a.delInbox").click (function() {
		var id = $(this).attr('id');
        $.post(
				"${ctx}/portal/pri/message/delete.action",
				{
					"msgId" : id,
					"type" : "inbox"
				},
				function(data, textStatus) {
					if (textStatus == "success") {
						if (data.retcode == 0) {
							$("#info_inbox").find("li[id='"+id+"']").remove();
							$(".message_list li:odd").addClass("odd");
						} else {
							// error
						}
					} else {
						// error
					}
				}, 
				"json"
		);
	});

	//删除发件箱
	$("a.delOutbox").click (function() {
		var id = $(this).attr('id');
        $.post(
				"${ctx}/portal/pri/message/delete.action",
				{
					"msgId" : id,
					"type" : "outbox"
				},
				function(data, textStatus) {
					if (textStatus == "success" && data.retcode == 0) {
						$("#info_outbox").find("li[id='"+id+"']").remove();
						$(".message_list li:odd").addClass("odd");
					}
				}, 
				"json"
		);
	});	
	
	$("a.recipient").click(function() {
		var menu_id = getMenuId();
		location.href = "${ctx}/portal/pri/message/selectUser.action?meetingId=" + $("#meetid").val() + "&menu_id=" + menu_id;
		return false;
	});
	
	$("a.reply").click(function() {
		var menu_id = getMenuId();
		location.href = "${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&select=new&menu_id=" + menu_id + "&selectuser=" + $(this).attr('id');
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
        var menu_id = getMenuId();
        $.post(
			"${ctx}/portal/pri/message/send.action",
			{"message" : $("#content").val(),"meetingId": "${meetingId}","userId" : "${selectuser}"},
			function(data, textStatus) {
				if (textStatus == "success" && data.retcode == 0) {
					location.href = "${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&menu_id=" + menu_id + "&select=outbox";
					return false;
				}
			}, 
			"json"
		);
	});
});
</script>

