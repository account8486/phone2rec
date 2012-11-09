<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>贵州移动会务通</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<!--移动设备特性-->
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="format-detection" content="telephone=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="${ctx}/images/touch/apple-touch-icon-precomposed.png"/>
<link rel="apple-touch-startup-image" href="${ctx}/images/touch/apple-touch-startup-ipad.png"/>
<link rel="apple-touch-startup-image" href="${ctx}/images/touch/apple-touch-startup.png"/>

<!-- 主样式表 -->
<link href="${ctx}/css/touch/main.css" rel="stylesheet" type="text/css">
<!-- jQuery 1.7.2 和 jQuery UI 1.8.18 -->
<script type="text/javascript" src="${ctx}/js/touch/jquery-1.7.2.min.js"></script>
<SCRIPT type="text/javascript" src="${ctx}/js/jquery.cookie.js"></SCRIPT>
<!-- JS -->
<script type="text/javascript" src="${ctx}/js/touch/ready.js"></script>
<script type="text/javascript" src="${ctx}/js/touch/function.js"></script>
<script type="text/javascript" src="${ctx}/js/util.js"></script>
<script type="text/javascript" src="${ctx}/js/ajax-public.js"></script>
</head>
<body>

<header>

	<%--会议名称 ：当传入ID时,需要显示名称，否则不需要显示 --%>
	<h3><span id="meetingTitle"></span></h3>
	<%--
    <div class="weather">
        <span class="info">合肥<s>16℃~6℃</s></span>
        <img src="${ctx}/images/touch/res/weather01.png">
    </div> --%>
</header>

<div>
    
    <%--登录FORM --%>
    <form name="loginForm" id="loginForm" name="loginForm" action="${ctx}/touch/base/login.action" method="POST" >
    <input type="hidden" name="meeting.id" value="${param.meetingId}" />
    
    <div class="tab_c" style="display:block;">
    	<div class="login_form">
        	<p>
            	<span>手机号：</span><input id="mobile" name="u.mobile" type="text" value="${u.mobile }" placeholder="请输入11位手机号码"/>
            	<div id="helpTips"></div>
            </p>
             <p style="margin:0px;">
            <%--	<a href="#" id="get_pwd_btn" class="login_btn2" style="margin:0px;">获取密码</a> --%>
            </p>
            <p>
            	<span>密　码：</span><input id="password" name="u.password" type="password" placeholder="请输入您的密码"/>
            </p>
            <p>
            	<a href="javascript://" id="login_btn" class="login_btn">登录</a>
            </p>
        </div>
    </div>
   </form>
    
</div>

<footer>
	<p class="edition">
    	<a href="javascript://">标准版</a> | <a href="javascript://" class="act">触屏版</a> | <a href="/hyy.apk">客户端下载</a>
    </p>
    <p class="copyright">
    	版权所有 © 中国移动通信集团贵州有限公司
    </p>
</footer>

<style type="text/css">
	/*这里是页面私有样式*/
	.login_form { margin:10px;}
	.login_form p { display:block; clear:both; overflow:auto; margin-bottom:10px;}
	.login_form p span { display:block; font-size:16px; font-weight:bold; line-height:3;}
	.login_form input,.login_form select,.login_form textarea { background:#fff; border:1px solid #b7b7b7; outline:0px; display:inline; height:40px; padding:0px 5px; color:#808080; -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; line-height:1; width:95%; float:left;}
	.login_form textarea { height:120px; width:80%;}
	.login_form input:hover,.login_form select:hover,.login_form textarea:hover { border:1px solid #999; background:#fcfcfc;}
	.login_form input:focus,.login_form select:focus,.login_form textarea:focus { border:1px solid #86c4fe; background:#fff; -webkit-box-shadow:0px 0px 3px #86c4fe; color:#333;}
	a.login_btn { width:95%;  height:40px; display:block; margin:10px 0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
	a.login_btn2 { width:95%;  height:40px; display:block; margin:10px 0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#333; background:url(${ctx}/images/touch/box_t_bg.png) top center repeat-x; border-left:1px solid #ccc;  border-right:1px solid #ccc;}
</style>

<script type="text/javascript">
$(document).ready(function(){
	// 获取动态密码
    $("#get_pwd_btn").click(
        function(){
        	getPwd();	
        }
    );
	
	
 	//表单提交，并作循环验证
    $("#login_btn").click(
    	function(){
     		doLogin();	
    	}
    );
 	
	//回车提交
	$("#password").keypress(function(evt) {
		if(evt.keyCode == 13) {
			doLogin();	
		}
	});
	
	$("#mobile").val($.cookie("cookie_mobile_touch"));
	helpTips();
	expandMeetingInfo();
	
});


function expandMeetingInfo(){
	var meetingId="${param.meetingId}";
	if(!isEmpty(meetingId)){
		var url="/touch/base/getMeetingInfoForTouch.action?meetingId="+meetingId;
		ajaxRequest(url,successCall);
		function successCall(data){
			//alert((data.meetingMap.name).length);
			if((data.meetingMap.name).length>18){
				$("#meetingTitle").html(data.meetingMap.name.substring(0,17)+"...");
			}else{
				$("#meetingTitle").html(data.meetingMap.name);
			}
		}
	}
}

//消息
function helpTips(){
	var errMsg='${errMsg}';
	  if (!isEmpty(errMsg)) {
	       // $("#helpTips").html(errMsg);
	       $("#helpTips").html(errMsg);
	  }
}


function getPwd() {
	$("#helpTips").html("");
    $("#password").val('');
    var tmp_bool = validateGetPwd();
    
    if (tmp_bool != true) {
        return false;
    }
    
    $.post(
            "${ctx}/portal/base/getPwd.action",
            {
                "username":$("#mobile").val()
            },
            function (data, textStatus) {
                if (textStatus == "success") {
                    $("#helpTips").html(data.retmsg);
                    //alert("进入会议的验证码是：" + data.password);  //用于测试，因为部分手机收不到短信
                    $("#password").focus();
                } else {
                   $("#helpTips").html("对不起，获取动态密码失败，请稍候再试！");
                }
            },
            "json"
	);
}


function validateGetPwd() {
    var mobile = $("#mobile").val();
	
    if (isEmpty(mobile)) {
        $("#helpTips").html("请输入手机号码。");
        return false;
    }
    
    var bool = /^1[0-9]{10}$/.test(mobile);
    if (bool == false) {
      	$("#helpTips").html("手机号码格式不正确。");
        return false;
    }

    return true;
}

function doLogin() {
	
    var tmp_bool = validateLogin();
    if (tmp_bool != true) {
        return false;
    }

    $.cookie('cookie_mobile_touch', $("#mobile").val(), { expires: 365 });
    $("#loginForm").submit();
}


// 登录页面校验
function validateLogin() {
    var mobile = $("#mobile").val();
    var pwd = $("#password").val();
    if (isEmpty(mobile)) {
        $("#helpTips").html("请输入手机号码。");
        return false;
    }

    var bool = /^1[0-9]{10}$/.test(mobile);
    if (bool == false) {
    	$("#helpTips").html("手机号码格式不正确。");
        return false;
    }

    if (isEmpty(pwd)) {
        $("#helpTips").html("请输入动态密码。");
       
        return false;
    }
    return true;
}



	
</script>

</body>
</html>