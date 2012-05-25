<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改密码</title>
	${admin_css}                                   
	${jquery_js}
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
	<script type="text/javascript">
		// 新增校验
		function validateAdd() {
	
	        var oldPass = $("#oldPass").val();
			
			if(isEmpty(oldPass))
			{
				 $("#tip_oldPass").html("请输入当前密码。");
			     $("#tip_oldPass").show();
			     return false;
			}
			
			var bool = /^.{6,20}$/.test(oldPass);
		    if (bool == false) {
		        $("#tip_oldPass").html("当前密码长度或格式不正确。");
		        $("#tip_oldPass").show();
		        return false;
		    }
		    
		    var pass = $("#pass").val();
		    
		    if(isEmpty(pass))
			{
				 $("#tip_pass").html("请输入新密码。");
			     $("#tip_pass").show();
			     return false;
			}
		    
		    var bool = /^.{6,20}$/.test(pass);
		    if (bool == false) {
		        $("#tip_pass").html("新密码长度或格式不正确。");
		        $("#tip_pass").show();
		        return false;
		    }
		    
		    var passAg = $("#passAg").val();
		    
		    if(isEmpty(passAg))
			{
				 $("#tip_passAg").html("请输入确认新密码。");
			     $("#tip_passAg").show();
			     return false;
			}
		    
		    if(passAg != pass)
		    {
		    	$("#tip_passAg").html("两次输入的新密码不一致。");
		        $("#tip_passAg").show();
		        return false;
		    }
		    
		    if( pass == oldPass)
		    {
		    	$("#tip_pass").html("新密码跟当前密码一样，请输入不同于当前密码的新密码。");
		        $("#tip_pass").show();
		        return false;
		    }
		    
	        return true;
		}
		
		function modifyPwd(){
			$("[id^='tip_']").hide();
	       	var tmp_bool = validateAdd();
	        if (tmp_bool != true) {
	            return false;
	        }

	        $.post(
	                "${ctx}/admin/pri/user/modifyPwd.action",
	                {
	                	"oldPass":$("#oldPass").val(),
	                	"pass":$("#pass").val()
	                },
	                function (data, textStatus) {
	                    if (textStatus == "success") {
	                       	$("#tip_err_msg").html(data.message);
	                    	$("#tip_err_msg").show();
	                    } else {
	                        $("#tip_err_msg").html("对不起，修改密码失败，请稍候再试！");
	                        $("#tip_err_msg").show();
	                    }
	                },
	                "json"
	    	);
	    }
	</script>
</head>
<body>
	<div class="page_title">
		<h3>修改密码</h3>
	</div>
	<div class="page_form">
	<form id="modifyPwdForm" action="${ctx}/admin/pri/user/modifyPwd.action" method="post">
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>当&nbsp;前&nbsp;密&nbsp;码：</label>
	            </dt>
	            <dd>
	            	<input type="password" class="quart" id="oldPass" name="oldPass" tabindex="1" maxlength="20"></input>
	            	<font id="tip_oldPass" style="line-height:35px" color="red"></font>
	                <small>请输入当前密码。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码：</label>
	            </dt>
	            <dd>
	            	<input type="password" class="quart" id="pass" name="pass" tabindex="2" maxlength="20"></input>
	            	<font id="tip_pass" style="line-height:35px" color="red"></font>
	                <small>请输入6到20位长度新密码。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>确认新密码：</label>
	            </dt>
	            <dd>
	            	<input type="password" class="quart" id="passAg" name="user.passAg" tabindex="3" maxlength="20"></input>
	            	<font id="tip_passAg" style="line-height:35px" color="red"></font>
	            	<small>请再次输入 新密码。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="modifyPwd();" id="modifyPwdBtn" class="btn_common btn_true">保 存</a>
	    </div>
	</form>
	</div>
</body>
</html>