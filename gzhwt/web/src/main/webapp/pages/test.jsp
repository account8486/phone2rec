<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通管理asdfasdfasd</title>
<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
${style_css}                                   
${jquery_js}

</head>
<body>
<div class="content">
<form>
   result:${pageContext.request.contextPath}
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