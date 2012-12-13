<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
${jquery_js}                          
${jquery_form_js}  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style>
*{ margin:0; padding:0}
body{ background:#f1f1f1; font-size:12px; color:#666; font-family:Verdana, Arial, Helvetica, sans-serif;}
.main{ width:819px; height:499px; position:absolute; left:50%; top:50%; margin:-250px 0 0 -410px;}
.head{ height:91px; background:url(${ctx}/images/login/head.jpg)}
.login{ height:312px; width:416px; float:left; background:url(${ctx}/images/login/login.jpg)}
.pic{ float:left; height:312px; width:403px; background:url(${ctx}/images/login/pic.jpg)}
.font{ height:43px; background:url(${ctx}/images/login/font.jpg);clear:both;}
.font ul{ list-style:none; float:left}
.font li{ background:url(${ctx}/images/login/icon.jpg) no-repeat left 10px; height:12px; line-height:16px;padding:5px 5px 0 10px;}
.cr{ height:53px; line-height:53px; text-align:center; font-size:11px; background:url(${ctx}/images/login/cr.jpg)}
table{ border-collapse:collapse; margin:120px 0 0 90px;}
th{ height:45px; text-align:right; padding-right:5px; font-weight:400}
td{ vertical-align:middle;}
input{ vertical-align:middle}
.text{ height:20px; line-height:20px; width:145px; border:1px solid #b8b8b8; background:#fff;}
.btn{ height:29px; width:79px; border:0; font-size:14px; color:#fff; font-weight:700; background:url(${ctx}/images/login/but_dl.jpg)}
a{ color:#416ba7; text-decoration:none;}
a:hover{ text-decoration:underline}
.font h2{ font-size:14px; color:#000; padding-bottom:5px;}
.font p{}
.fo{ float:left;}
.f1{ float:left; padding-left:130px; width:400px;}
</style>
<script>
function doSave(){
        var url = "../login/action_loginIn.do";
         loginform.action=url;
		 loginform.submit();
    }
</script>

</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="loginform" id="loginform" method="post" action="${ctx}/sso/integrateLogin.action">
<div class="main">
	<div class="head"></div>
	<div class="cont">
		<div class="login">
			<table>
				<tr> 
				<font color="#FF0000"><span id="message" class="message"></span></font>
					<th>用户名</th>
					<td><input type="text" name="userName" id="userName"  class="text" ></td>
				</tr>
				<tr>
					<th>密　码</th>
					<td><input type="password" name="password" id="password"  class="text" /> <a href="#"> </a></td>
				</tr>
				<tr>
					<th></th>
					<td><input name="login" type="button" value="" class="btn" onClick="doLogin();"/></td>
				</tr>
			</table>
		</div>
		<div class="pic"></div>
	</div>
	<div class="font">
	<div class="f1">
		
	</div>
		<div class="fo">
			
		</div>
	</div>
	<div class="cr">安徽省电力公司版权所有</div>
</div>
<form>
</body>
</html>




<script>
function doLogin(){
	//alert("login!");
	$("#loginform").submit();
}
</script>