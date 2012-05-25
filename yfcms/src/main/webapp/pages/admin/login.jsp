<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会议云平台管理系统</title>
<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
${style_css}                                   
${jquery_js}
<style>
	*{
		margin:0;
		padding:0;
	}
	
	body{
		background:rgb(15,61,93); 
		
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
		background:url(${ctx}/images/bg_adm_login.png) no-repeat;
	}
	input{
		position:absolute;
		background:none;
		border:none;	
	}
	
	input.uid{
		top:240px;
		left:515px;
		height:18px;
		line-height:18px;
		width:140px;
	}
	#tip_mobile{
		position:absolute;
		top:240px;
		left:660px;
		height:18px;
		line-height:18px;
		width:80px;
	}
	
	#tip_err_msg{
		position:absolute;
		top:354px;
		left:520px;
		height:18px;
		line-height:18px;
		width:160px;
	}
	input.psw{
		top:273px;
		left:515px;
		height:18px;
		line-height:18px;
		width:140px;
	}
	#tip_password{
		position:absolute;
		top:273px;
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
		width:70px;
	}
	#img_checkCode{
		position:absolute;
		top:307px;
		left:590px;
	}
	#tip_checkCode{
		position:absolute;
		top:310px;
		left:652px;
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
		left:440px;
		height:18px;
		line-height:18px;
		width:50px;
	}
</style>
</head>
<body>
<div class="content">
<form>
    <div id="div_err_msg">
    	<font id="tip_err_msg" style="display: none;" color="red"></font>
    </div>
	<input class="uid" name="mobile" id="mobile"/><div class="uid"><font id="tip_mobile" style="display: none;" color="red"></font></div>
	<input class="psw" name="password" id="password" type="password" maxlength="20"/><font id="tip_password" style="display: none;" color="red"></font>
	<input class="randimg" name="checkCode" id="checkCode" />
	<img id="img_checkCode" style="cursor:pointer" align="top" onclick="this.src='${ctx}/admin/base/checkCode.action?a='+Math.random();"/>
	<font id="tip_checkCode" style="display: none;" color="red"></font>
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
            $("#tip_mobile").html("请输入用户名。");
            $("#tip_mobile").show();
            return false;
        }

        var bool = /^[a-zA-Z0-9_\.]{4,20}$/.test(mobile);
        if (bool == false) {
            $("#tip_mobile").html("请输入4到20位用户名，可以使用字母，数字下划线。");
            $("#tip_mobile").show();
            return false;
        }
        
        if (isEmpty(pwd)) {
            $("#tip_password").html("请输入密码。");
            $("#tip_password").show();
            return false;
        }

        var result = /^.{6,20}$/.test(pwd);
        if (result == false) {
            $("#tip_password").html("请输入6到20位长度密码。");
            $("#tip_password").show();
            return false;
        }
        
        var checkCode = $("#checkCode").val();

        if (isEmpty(checkCode)) {
            $("#tip_checkCode").html("请输入验证码。");
            $("#tip_checkCode").show();
            return false;
        }

        return true;
    }

    function doLogin() {
        $("#tip_err_msg").hide();
        $("#tip_mobile").hide();
        $("#tip_password").hide();
        $("#tip_checkCode").hide();
        var tmp_bool = validateLogin();
        if (tmp_bool != true) {
            return false;
        }

        $.post(
                "${ctx}/admin/base/login.action",
                {
                    "mobile":$("#mobile").val(),
                    "password":$("#password").val(),
                    "checkCode":$("#checkCode").val()
                },
                function (data, textStatus) {
                    if (textStatus == "success") {
                        if (data.result) {
                        	$.cookie('cookie_mobile', $("#mobile").val(), { expires: 365 });
                            window.location.href = "${ctx}/pages/admin/pri/index.jsp";
                        }
                        else {
                            $("#tip_err_msg").html(data.message);
                            $("#tip_err_msg").show();
                        }
                    } else {
                        $("#tip_err_msg").html("对不起，登录失败，请稍候再试！");
                        $("#tip_err_msg").show();
                    }
                },
                "json"
    	);
    }
        
    $(document).ready(function(){
    	
    	$("#mobile").val($.cookie("cookie_mobile"));
    	
    	var ran = Math.random();
    	var queryStr = "${ctx}/admin/base/checkCode.action?a=" + ran; 
    	$("#img_checkCode").attr("src", queryStr);
    	$("#img_checkCode").attr("border", 1);
    	
    	//表单提交，并作循环验证
        $("#login_btn").click(
        	function(){
         	doLogin();	
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