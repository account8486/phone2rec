<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/portal/common/headerNoMenu.jsp" %>

<div class="w960">
	
    <div class="cbody" style="width:900px;">
        <div class="cbox">
        	<div class="title"><h5 id="caption">${param.menu_name}</h5></div>
        </div>
	<div class="cbox">		
		<div class="second_title">
			<h5>请输入您的个人信息</h5>
		</div>
		<div class="cont">
			<p style="margin:10px;">
				<label class="wordbreak">手机：
				<input type="text" name="attendee.mobile" id="mobile" maxlength="11" />
				</label>
				<span style="color:red;">*</span><span id="err_mobile" class="err"></span>
			</p>			
			<p style="margin:10px;">
				<label class="wordbreak">姓名：
				<input type="text" name="attendee.name" id="name" maxlength="10" />
				</label>
				<span style="color:red;">*</span><span id="err_name" class="err"></span>
			</p>
			<p style="margin:10px;">
				<label class="wordbreak">性别：
				<input type="radio" name="attendee.gender" value="0" checked="checked"/>男
				</label>
				<label>
				<input type="radio" name="attendee.gender" value="1" />女
				</label>
			</p>
			<p style="margin:10px;">
				<label class="wordbreak">职务：
				<input type="text" name="attendee.meetingMember.job" id="job" maxlength="30" />
				</label>
			</p>			
			<p style="margin:10px;">
				<label class="wordbreak">城市：
				<input type="text" name="attendee.meetingMember.city" id="city" maxlength="5" />
				</label>
			</p>
			<p style="margin:10px;">
				<label class="wordbreak">邮箱：
				<input type="text" name="attendee.meetingMember.mailbox" id="mailbox" maxlength="50" />
				</label>
				<span style="color:red;">*</span><span id="err_mailbox" class="err"></span>
			</p>
			
			<p style="margin:10px;">
				<input type="button" value="提交" onclick="applyMeeting()" style="width:75px; height:27px; line-height:27px; text-align:center;" />
				<input type="button" value="返回" onclick="history.back()" style="width:75px; height:27px; line-height:27px; text-align:center;" />
			</p>
		</div>
	</div>
    </div>
	</form>
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
$(document).ready(function() {
	$("span.wordbreak").each(function(){
		$(this).html($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
	});

	$("p").css("margin-left","24px");
	
	//给错误提示信息加上样式 
	$("span.err").css("color", "red");
});

function showError(errId, msg) {
	$("#" + errId).html(msg);
	$("#" + errId).show();
}

function validate() {
	$("span.err").hide(); //隐藏消息提示
	var mobile = $("#mobile").val();
	var name = $("#name").val();
	var mailbox = $("#mailbox").val();
	
	if(! /^1\d{10}$/.test(mobile)) {
		showError("err_mobile", "请输入正确格式的11位的手机号码");
		return false;
	}
	
	if($.trim(name) == "") {
		showError("err_name", "姓名不能为空");
		return false;
	}
	
	if(! /^\w+@\w+.\w+$/.test(mailbox)) {
		showError("err_mailbox", "请输入正确格式的邮箱地址");
		return false;
	}
	return true;
}

function applyMeeting() {
	if(validate()) {
		var url = "${ctx}/meeting/base/applyMeeting.action";
		var param = {
			"_meeting_id_": "${meeting.id}",
			"attendee.mobile": $("#mobile").val(),
			"attendee.name": $("#name").val(),
			"attendee.gender": $("input[name='attendee.gender']:checked").val(),
			"attendee.meetingMember.job": $("#job").val(),
			"attendee.meetingMember.city": $("#city").val(),
			"attendee.meetingMember.mailbox": $("#mailbox").val()
		};
		$.post(url, param, applyMeetingCallback);
	}	
}

function applyMeetingCallback(data) {
	if(data.result == 0) {
		alert(data.message);
		return;
	}
	
	alert(data.message);
	window.location.href = "${ctx}/pages/portal/login.jsp";
}
</script>