<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/portal/common/headerNoMenu.jsp" %>

<div class="w960">


    <div class="cbody">
        <div class="cbox">
        	<div class="title"><h5 id="caption">${meeting.name}</h5></div>
        </div>
	<div class="cbox">		
		<!-- 
		<div>
			<h3>会议类型</h3>
			<p><span id="type"></span></p>
		</div>
		 -->
		<div class="second_title">
			<h5>会议主题</h5>
		</div>
		<div class="cont">
			<p><span class="wordbreak"></span></p>			
			<div class="cont">${wd:convertToHtmlLine(meeting.topic) }</div>
		</div>
	</div>
	<div class="cbox">
		<div class="second_title">
			<h5>会议地点</h5>
		</div>
		<div class="cont"><p><span >${meeting.location}</span></p></div>
	</div>
	<div class="cbox">
		<div class="second_title">
			<h5>会议开始时间</h5>
		</div>
		<div class="cont"><p><span>${fn:substring(meeting.startTime,0,16)}</span></p></div>
	</div>
	<div class="cbox">	
		<div class="second_title">
			<h5>会议结束时间</h5>
		</div>
		<div class="cont"><p><span>${fn:substring(meeting.endTime,0,16)}</span></p></div>
	</div>
    <c:if test="${meeting.joinTime !='' && meeting.joinTime != null}">
	<div class="cbox">	
		<div class="second_title">
			<h5>报到时间</h5>
		</div>
		<div class="cont"><p><span>${meeting.joinTime}</span></p></div>
	</div>
	</c:if>

    </div>
    <div class="cright">
 		<div class="cbox">
        	<div class="title"><h5>参会通道</h5></div>
        	<div class="login">
        	<form id="loginForm" action="${ctx}/base/quickEnterMeeting.action" method="post">
        		<input type="hidden" name="meeting.id" value="${meeting.id}" />
        		<input type="hidden" name="returnType" value="portal" />
        		<input type="hidden" name="from" value="portal" />
        		
        		<span style="color:red;">${errMsg }</span>
        		<div class="cont">
        			<label>手机号码：<input type="text" id="mobile" name="attendee.mobile" value="${attendee.mobile }" maxlength="11" style="width:120px;"/></label>
        		</div>
        		<div class="cont">
        			<label>参会密码：<input type="password" id="password" name="attendee.password" maxlength="20" style="width:120px;"/></label>
        		</div>
        		<div class="cont">
        			<input type="button" value="进入会议" onclick="login()" style="font-weight:bold; width:80px;height:30px;background:#2392E1" />
        			<input type="button" id="get_pwd_btn" value="获取密码" onclick="getPwd()" style="font-weight:bold; width:80px;height:30px;background:#2392E1" />
        			<a style="font-style:italic;" href="${ctx}/base/applyMeetingReq.action?meeting.id=${meeting.id}&menu_name=会议报名">现在报名</a>
        		</div>
        	</form>
        	</div>
        </div>
        
        <div class="cbox">
        	<div class="title"><h5>会议公告</h5></div>
            <div class="cont">
            	${meeting.notice}
            </div>
        </div>

        <div class="cbox">
        	<div class="title"><h5>天气预报</h5></div>
            <div class="cont">
            <%-- 中国天气网插件
            	<c:choose >
            	<c:when test="${meeting.city eq '0101'|| meeting.city eq '0201'|| meeting.city eq '0301'|| meeting.city eq '0401'}">
            		<iframe  src="http://m.weather.com.cn/n/pn12/weather.htm?id=101${meeting.city}00T" width="100%" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            	</c:when>
            	<c:otherwise>
            		<iframe  src="http://m.weather.com.cn/n/pn12/weather.htm?id=101${meeting.city}01T" width="100%" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            	</c:otherwise>
            	</c:choose>
             --%>
             
             <iframe src="http://www.265.com/weather/${fn:substring(meeting.city, 2, 10) }.htm" width="168" height="50" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            </div>
        </div>
        
        <div class="cbox">
        	<div class="title"><h5>会务单位</h5></div>
            <div class="cont">
            	主办单位：${meeting.host }<br>
            	<c:if test="${not empty meeting.organizer}">
            	<c:forEach var="orgzer" items="${wd:orgsToList(meeting.organizer) }" varStatus="status">
            		<span style="${status.first ?'':'padding-left:70px;'}"><c:if test="${status.first }">承办单位：</c:if>${orgzer.organizer }<br/></span>
            	</c:forEach>
            	</c:if>
            </div>
        </div>
        
        <c:if test="${not empty meeting.serviceNumber }">
        <div class="cbox">
        	<div class="title"><h5>总机电话</h5></div>
            <div class="cont">
            	<p class="tel">${meeting.serviceNumber}</p>
            </div>
        </div>
        </c:if>
        
        <%--  贵州移动会务通不需要客户端下载功能
        <div class="cbox">
        	<a href="/hyy.apk" class="download"></a>
        </div>
		--%>
    </div>
	
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
$(document).ready(function() {
	$("span.wordbreak").each(function(){
		$(this).html($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
	});

	$("p").css("margin-left","24px");
	
});

function validateLogin() {
    var mobile = $("#mobile").val();
    var pwd = $("#password").val();

    if (isEmpty(mobile)) {
        alert("请输入11位的手机号码。");
        return false;
    }

    var bool = /^1[0-9]{10}$/.test(mobile);
    if (!bool) {
    	alert("手机号码长度或格式不正确。");
        return false;
    }

    if (isEmpty(pwd)) {
    	alert("请输入参会密码。");
        return false;
    }
    return true;
}

function login() {
	var tmp_bool = validateLogin();
    if (tmp_bool) {
    	$("#loginForm").submit();
    }
}

function validateGetPwd() {
    var mobile = $("#mobile").val();
    if (isEmpty(mobile)) {
        alert("请输入11位的手机号码。");
        return false;
    }

    var bool = /^1[0-9]{10}$/.test(mobile);
    if (!bool) {
        alert("手机号码长度或格式不正确。");
        return false;
    }

    return true;
}

function getPwd() {
    var tmp_bool = validateGetPwd();
    if (! tmp_bool) {
        return false;
    }

    $("#get_pwd_btn").attr("disabled","disabled");
    $.post(
            "${ctx}/portal/base/getPwd.action",
            {
                "username":$("#mobile").val()
            },
            function (data, textStatus) {
                if (textStatus == "success") {
                    alert(data.retmsg);
                } else {
                    alert("对不起，获取密码失败，请稍候再试！");
                }
                
                $("#get_pwd_btn").removeAttr("disabled");
            },
            "json"
	);
}
</script>