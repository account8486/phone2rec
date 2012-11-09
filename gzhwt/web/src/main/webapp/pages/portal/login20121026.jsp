<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ page import="com.wondertek.meeting.model.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贵州移动会务通平台登录</title>
${jquery_js}

 <!-- IE识别HTML5标签 -->
    <!--[if IE]>
    <script src="${ctx}/js/html5.js"></script>
    <![endif]-->
    <!-- IE6 PNGfix -->
    <!--[if IE 6]>
    <script src="${ctx}/js/iepng.js" mce_src="${ctx}/js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.content, .login, .logo, .logoimg');
        /* 将 .iepng 改成你应用了透明PNG的CSS选择器 */
    </script>
    <![endif]-->
	<!--[if IE 6]>
	<style type="text/css">
		#wrap {display:table;height:100%}
	</style>
	<![endif]-->
	
<style>
*{
	margin:0;
	padding:0;
}

body{
	font-size:14px;
	text-align:left;
	background: #EBEBEB;
}

.content{
	width:1000px;
	height:580px;
	margin:0 auto;
}

.content .logo{
	width:120px;
	height:54px;
	left:0px;
	top:0px;
	position:absolute;
	z-index: 1000;
}

.content .copyRight{
	width:500px;
	height:50px;
	line-height:50px;
	font-size:16px;
	//font-weight:bold;
	color:#004080;
	left:0px;
	top:0px;
	position:absolute;
	z-index: 1000;
}

.content .title{
	width:500px;
	height:50px;
	line-height:50px;
	font-size:28px;
	font-weight:bold;
	color:#004080;
	left:0px;
	top:0px;
	position:absolute;
	z-index: 1000;
}

.content .login {
	width:380px;
	height:240px;
	margin:0 auto;
	padding:30px 0 0 30px;
	left:0px;
	top:0px;
	position:absolute;
	z-index: 1000;
	background: url(${ctx}/images/portal/login_bg.png) no-repeat;
}

.content .inp {
	padding:4px 10px;
}

.content .inp span {
	color: #ff0000;
	padding-left:5px;
}

.content label {
	color:#004080;
	font-weight:bold;
	font-size:16px;
}

.content input {
	width:107px;
	height:24px;
	line-height:24px;
	padding:2px 8px;
	border:1px solid #004080;
}

.content .button {
	padding:4px 10px;
	margin:10px 0 0 50px;
}

.content span {
	padding-left:140px;
}

.content .btn {
	width:90px;
	height:35px;
	display:inline-block;
	background:url(${ctx}/images/login_btn_back.png) no-repeat;
	text-align:center;
	line-height:35px;
	font-size:16px;
	font-weight:bold;
	color:#fff;
	text-decoration: none;
}

</style>

<SCRIPT type="text/javascript" src="${ctx}/js/util.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${ctx}/js/jquery.cookie.js"></SCRIPT>
<script type="text/javascript">
    // 登录页面校验
    function validateLogin() {
        var mobile = $("#mobile").val();
        var pwd = $("#password").val();

        if (isEmpty(mobile)) {
            $("#tip_mobile").html("请输入手机号码。");
            $("#tip_mobile").show();
            return false;
        }

        var bool = /^1[0-9]{10}$/.test(mobile);
        if (bool == false) {
            $("#tip_mobile").html("手机号码格式不正确。");
            $("#tip_mobile").show();
            return false;
        }

        if (isEmpty(pwd)) {
            $("#tip_password").html("请输入动态密码。");
            $("#tip_password").show();
            return false;
        }
        return true;
    }

    function validateGetPwd() {
        var mobile = $("#mobile").val();

        if (isEmpty(mobile)) {
            $("#tip_mobile").html("请输入手机号码。");
            $("#tip_mobile").show();
            return false;
        }

        var bool = /^1[0-9]{10}$/.test(mobile);
        if (bool == false) {
            $("#tip_mobile").html("手机号码格式不正确。");
            $("#tip_mobile").show();
            return false;
        }

        return true;
    }
    
    function doLogin() {
        $("#tip_mobile").hide();
        $("#tip_password").hide();
        $("#tip_err_msg").hide();
       
        var tmp_bool = validateLogin();
        if (tmp_bool != true) {
            return false;
        }

        $.cookie('cookie_mobile_portal', $("#mobile").val(), { expires: 365 });
        $("#loginForm").submit();
    }
    
    function getPwd() {
        $("#tip_mobile").hide();
        $("#tip_password").hide();
        $("#tip_err_msg").hide();
        
        $("#password").val('');
        var tmp_bool = validateGetPwd();
        if (tmp_bool != true) {
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
                    	$("#login_btn").show();
                        $("#inp_pwd").show();
                        
                        $("#tip_err_msg").html(data.retmsg);
                        //alert("进入会议的验证码是：" + data.password);  //用于测试，因为部分手机收不到短信
                        $("#tip_err_msg").show();
                    } else {
                        $("#tip_err_msg").html("对不起，获取动态密码失败，请稍候再试！");
                        $("#tip_err_msg").show();
                    }
                    
                    $("#get_pwd_btn").removeAttr("disabled");
                },
                "json"
    	);
    }
    
    $(document).ready(function(){
    	$("#mobile").val($.cookie("cookie_mobile_portal"));
    	
    	var errMsg = "${errMsg}";
    	if(errMsg == "") {
    		//$("#inp_pwd").hide();
        	//$("#login_btn").hide();
    	}
    	
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
    	
    	// 获取动态密码
        $("#get_pwd_btn").click(
            function(){
             	getPwd();	
            }
        );
    	
        resize();
        window.onresize = resize;
    });
    
    
    //动态调整位置
    function resize() {
    	//动态调整容器的位置
    	var windowWidth = $(window).width();
    	var windowHeight = $(window).height();
    	var content_margin = (windowHeight-580)/2;
    	//alert(content_margin);
    	//设置中央容器的上部margin
    	$("body").css("padding-top", content_margin);
    	
    	//设置logo的位置
    	var posX = (windowWidth-1000) / 2 + 40;
    	var posY = (windowHeight-580) /2 + 40;
    	$(".logo").css("left", posX);
    	$(".logo").css("top", posY);
    	
    	//设置标题文字的位置
    	var posX = (windowWidth-1000) / 2 + 300;
    	var posY = (windowHeight-580) /2 + 170;
    	$(".title").css("left", posX);
    	$(".title").css("top", posY);
    	
    	//设置button copyRight文字
    	var posX = (windowWidth-1000) / 2 + 300;
    	var posY = (windowHeight-580) /2 + 450;
    	$(".copyRight").css("left", posX);
    	$(".copyRight").css("top", posY);
    	
    	//设置中间输入区域的margin
    	posX = (windowWidth-380) / 2 + 15;
    	posY = (windowHeight-240) /2 + 50;
    	$(".login").css("left", posX);
    	$(".login").css("top", posY);
    }
</script>
</head>
<body>
<%
	//判断是否指定的会议ID，如果指定了会议ID，则查询此会议下是否设置了登录页面背景图，
	String mid = request.getParameter("meetingId");
	if(mid != null) {
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/portal/base/goPortalLogin.action?meeting.id=" + mid);
		return;
	}
%>

	<div id="content" class="content">
		<%-- 如果没有指定登录页背景图片，则使用缺省的images/portal/bg_login.jpg图片--%>
		<c:if test="${not empty loginImageUrl }">
		<img src="${serverUrl}${loginImageUrl}" width="1000" height="580" border="0" />
		</c:if>
		<c:if test="${empty loginImageUrl }">
		<img src="${ctx}/images/portal/bg_login.jpg" width="1000" height="580" border="0" />
		</c:if>
		
		<c:if test="${not empty logoImageUrl }">
		<div class="logo"><img class="logoimg" src="${serverUrl}${logoImageUrl}" width="120" height="54" border="0" /></div>
		</c:if>
		
		<div class="title"><%-- 中国移动贵州公司会务通平台--%></div>
		
		<div class="login">
		<form id="loginForm" action="${ctx}/portal/base/login.action" method="post">
			<c:if test="${not empty meeting.id}">
				<input type="hidden" name="meeting.id" value="${meeting.id}" />
			</c:if>
			
			<div class="inp" style="height:30px;"><span id="tip_err_msg">${errMsg }</span></div>
			<div class="inp">
				<label for="mobile">手机：</label><input type="text" id="mobile" name="u.mobile"/>
				<span id="tip_mobile"></span>
			</div>
			<div id="inp_pwd" class="inp">
				<label for="password">密码：</label><input type="password" id="password" name="u.password"/>
				<span id="tip_password"></span>
			</div>
			<div class="button">
				<a id="login_btn" class="btn" href="#">登&nbsp;&nbsp;录</a>
			<%--<a id="get_pwd_btn" class="btn" href="#">获取密码</a> --%>	
				<a id="get_pwd_btn" class="btn" href="/hyy.apk">下载客户端</a>
			</div>
			
		</form>
		</div>
		
		
	   <div class="copyRight">Copy Right by 中国移动贵州公司信息技术部 技术支持</div>
	</div>
</body>
</html>