<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ page import="com.wondertek.meeting.common.Constants" %>

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
        DD_belatedPNG.fix('.content, .right');
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
}

.content{
	width:100%;
	height:100%;
	background:url(${ctx}/images/login_right.png) no-repeat bottom right;
}

.content .right {
	padding:350px 0 0 450px;
	background:url(${ctx}/images/login_left.png) no-repeat -10px 0px;
}

.content .inp {
	padding:4px 10px;
}

.content .inp span {
	color: #ff0000;
	padding-left:10px;
}

.content label {
	color:#004080;
	font-weight:bold;
	font-size:16px;
}

.content input {
	width:167px;
	height:24px;
	line-height:24px;
	padding:2px 8px;
	border:1px solid #004080;
}

.content .button {
	padding:4px 10px;
	margin-left:50px;
}

.content .btn {
	width:100px;
	height:40px;
	display:inline-block;
	background:url(${ctx}/images/login_btn_back.png) no-repeat;
	text-align:center;
	line-height:40px;
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
            $("#tip_mobile").html("手机号码长度或格式不正确。");
            $("#tip_mobile").show();
            return false;
        }

        if (isEmpty(pwd)) {
            $("#tip_password").html("请输入您接收到的动态密码。");
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
            $("#tip_mobile").html("手机号码长度或格式不正确。");
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
                        //alert(data.password);
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
    		$("#inp_pwd").hide();
        	$("#login_btn").hide();
    	}
    	
    	//表单提交，并作循环验证
        $("#login_btn").click(
        	function(){
         		doLogin();	
        	}
        );
    	
    	// 获取动态密码
        $("#get_pwd_btn").click(
            function(){
             	getPwd();	
            }
        );
    	
    	//动态调整容器的高度
    	var windowHeight = $(window).height();
    	$(".content").css("height", windowHeight);
    });
</script>
</head>
<body>
	<div class="content">
		<div class="right">
		<form id="loginForm" action="${ctx}/portal/base/login.action" method="post">
			<div class="inp" style="height:30px;"><span id="tip_err_msg">${errMsg }</span></div>
			<div class="inp">
				<label for="mobile">手机：</label><input type="text" id="mobile" name="u.mobile"/><span id="tip_mobile"></span>
			</div>
			<div id="inp_pwd" class="inp">
				<label for="password">密码：</label><input type="password" id="password" name="u.password"/><span id="tip_password"></span>
			</div>
			<div class="button">
				<a id="login_btn" class="btn" href="#">登&nbsp;&nbsp;录</a>
				<a id="get_pwd_btn" class="btn" href="#">获取密码</a>
			</div>
		</form>
		</div>
	</div>
</body>
</html>