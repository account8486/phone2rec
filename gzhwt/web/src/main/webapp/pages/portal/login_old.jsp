<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ page import="com.wondertek.meeting.common.Constants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css" />               
${jquery_js}
<style>
*{
	margin:0;
	padding:0;
}

body{
	background:rgb(15,46,93); 
	
	font-size:12px;
	line-height:100%;
}

a img {
	border:none;
}
.content{
	position:relative;
	margin:0 auto;
	width:940px;
	height:580px;
	background:url(${ctx}/images/bg_web_login.png) no-repeat;
}
input{
	position:absolute;
	background:none;
	border:none;	
}

input.uid{
	top:265px;
	left:515px;
	height:18px;
	line-height:18px;
	width:140px;
}
#tip_mobile{
	position:absolute;
	top:288px;
	left:506px;
	height:18px;
	line-height:18px;
	width:280px;
}

#tip_err_msg{
	position:absolute;
	top:288px;
	left:506px;
	height:18px;
	line-height:18px;
	width:280px;
}
input.psw{
	top:310px;
	left:515px;
	height:18px;
	line-height:18px;
	width:140px;
}
#tip_password{
	position:absolute;
	top:310px;
	left:660px;
	height:18px;
	line-height:18px;
	width:80px;
}

input.randimg{
	top:307px;
	left:515px;
	height:18px;
	line-height:18px;
	width:50px;
}
#tip_meetingId{
	position:absolute;
	top:307px;
	left:600px;
	height:18px;
	line-height:18px;
	width:120px;
}
div.randimg{
	position:absolute;
	top:307px;
	left:585px;
	height:18px;
	line-height:18px;
	width:50px;
	cursor:pointer;
}
div.btn_login{
	position:absolute;
	top:350px;
	left:458px;
	height:18px;
	line-height:18px;
	width:100px;
}
div.get_pwd_div{
	position:absolute;
	top:258px;
	left:660px;
	height:18px;
	line-height:18px;
	width:100px;
}

</style>
</head>
<body>
<div class="content">
<form id="loginForm" action="${ctx}/portal/base/login.action" method="post">

    <div id="div_err_msg">
    	<font id="tip_err_msg" color="red" >${errMsg }</font>
    </div>
    
	<input class="uid" name="u.mobile" id="mobile"/><div class="uid">
	<font id="tip_mobile" style="display: none;" color="red"></font></div>
		<div class="get_pwd_div">
		<a href="#" id="get_pwd_btn"><img src="${ctx}/images/get_pwd_btn.png"/></a>
	</div>
	<input class="psw" name="u.password" id="password" type="password" value="${u.password }" maxlength="20"/>
	<input  name="flag" id="flag" type="hidden" value="${flag}"/>
	<font id="tip_password" style="display: none;" color="red"></font>
	
	<div class="btn_login">
		<a href="#" id="login_btn"><img src="${ctx}/images/login_btn.png"/></a>
	</div>
	

</form>
</div>
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
        $.cookie('cookie_mobile_portal', $("#mobile").val(), { expires: 365 });
        
        var tmp_bool = validateLogin();
        if (tmp_bool != true) {
            return false;
        }

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
                            $("#tip_err_msg").html(data.retmsg);
                            //alert(data.password);
                            $("#tip_err_msg").show();
                        
                    } else {
                        $("#tip_err_msg").html("对不起，获取动态密码失败，请稍候再试！");
                        $("#tip_err_msg").show();
                    }
                    
                    $("#get_pwd_btn").removeAttr("disabled");
               	    $("#flag").val("Y");
                	$("#password").css("background-color","white"); 
                },
                "json"
    	);
    }
    
    $(document).ready(function(){
    	$("#mobile").val($.cookie("cookie_mobile_portal"));
    	//alert(${flag});
    	<c:if test="${empty flag}">
    	 $("#flag").val("N");
    	 $("#password").css("background-color","gray"); 
    	</c:if>
    	
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
    	
    	$("#password").click(
    		function(){
    			//alert($("#password").attr("readonly"));
    			//alert($("#flag").val());
    			if($("#flag").val()=="N"){
    				alert("请获取动态密码！");
    			}
    		}
    	);
        
      	$(function(){
      		document.onkeydown = function(e){
       		var ev = document.all ? window.event : e; 
       		if(ev.keyCode==13) {
       			doLogin();
       		}
      		}
      	}); 
    });
</script>
</body>
</html>