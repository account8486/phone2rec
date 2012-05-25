<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>电信会议云</title>
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	${main_css}
	${style_css}                                   
	${jquery_js}
	${jquery_form_js}
</head>
<body>
	
	<c:if test="${empty param.from}">
	<%@ include file="/pages/portal/common/header.jsp" %>
	</c:if>
	
	<div class="w960">
	    <div class="cbody">
	        <div class="cbox">
	        	<div class="title"><h5 id="caption">${param.menu_name}</h5></div>
	        </div>
		<div class="cbox">	
			<div class="cbox">		
				<div class="second_title">
					<h5>密码由4-20个数字组成。</h5>
				</div>
				<div class="cont"><p><span class="wordbreak"><c:out escapeXml="true" value="${meeting.topic}"/></span></p></div>
			</div>
			<div style="width:500px;magin-left:100px">
				<form id="modifyPwdForm" action="${ctx}/portal/pri/person/modifyPwd.action">
					<table class="grid">
						<tr class="odd">
							<td width="100" ><span class="srd"> 当前密码：</span></td>
						  	<td width="200"><span class="left">
								<input class="psw" name="oldPass" id="oldPass" type="password" maxlength="20" value="${oldPass}"/>
								</span>
							</td>
						</tr>
						<tr>
							<td ><span class="srd"> 新密码：</span></td>
						  	<td width="200"><span class="right">
								<input class="psw" name="pass" id="pass" type="password" maxlength="20" value="${pass}"/>
								</span>
							</td>
						</tr>
						<tr class="odd">
							<td ><span class="srd"> 确认新密码：</span></td>
						  	<td width="200"><span class="right">
								<input class="psw" name="passAg" id="passAg" type="password" maxlength="20" value="${passAg}"/>
								</span>
							</td>
						</tr>
		      		</table>
					
					<div class="neidi">
						<font id="tip_err_msg" style="line-height:35px;" color="red">
							<s:fielderror><s:param>errMsg</s:param></s:fielderror>
						</font>
						<br/>
					</div>
					
					<div class="neidi">
						<input type="button" value="保存" class="lbtn" id="modifyPwdBtn" onclick="modifyPwd();"/>
					</div>
					<br/>
				</form>
			</div>			
		</div>
		</div>
	</div>
<%@ include file="/pages/portal/common/footer.html" %>
</body>
<SCRIPT type="text/javascript" src="${ctx}/js/util.js"></SCRIPT>
<script type="text/javascript">
	// 修改密码校验
	function validateModifyPwd() {
		var oldPass = $("#oldPass").val();
		
		if(isEmpty(oldPass))
		{
			 $("#tip_err_msg").html("请输入当前密码。");
		     $("#tip_err_msg").show();
		     return false;
		}
		
		var bool = checkPortalPwd(oldPass);
	    if (bool == false) {
	        $("#tip_err_msg").html("当前密码长度或格式不正确。");
	        $("#tip_err_msg").show();
	        return false;
	    }
	    
	    var pass = $("#pass").val();
	    
	    if(isEmpty(pass))
		{
			 $("#tip_err_msg").html("请输入新密码。");
		     $("#tip_err_msg").show();
		     return false;
		}
	    
	    var bool = checkPortalPwd(pass);
	    if (bool == false) {
	        $("#tip_err_msg").html("新密码长度或格式不正确。");
	        $("#tip_err_msg").show();
	        return false;
	    }
	    
	    var passAg = $("#passAg").val();
	    
	    if(isEmpty(passAg))
		{
			 $("#tip_err_msg").html("请输入确认新密码。");
		     $("#tip_err_msg").show();
		     return false;
		}
	    
	    if(passAg != pass)
	    {
	    	$("#tip_err_msg").html("两次输入的新密码不一致。");
	        $("#tip_err_msg").show();
	        return false;
	    }
	    
	    if( pass == oldPass)
	    {
	    	$("#tip_err_msg").html("新密码跟当前密码一样，请输入不同于当前密码的新密码。");
	        $("#tip_err_msg").show();
	        return false;
	    }
	    
        return true;
	}
	
	function modifyPwd(){
		$("#tip_err_msg").hide();
       	var tmp_bool = validateModifyPwd();
        if (tmp_bool != true) {
            return false;
        }
        
		$("#modifyPwdBtn").attr("disabled","disabled");
		$("#modifyPwdForm").submit();
	}
</script>
</html>