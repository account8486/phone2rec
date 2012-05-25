<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理</title>
	<link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	${style_css}                                   
	${jquery_js}
	<script type='text/javascript' src='${ctx}/js/jquery.form.js'></script>
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
</head>
<body>
<div style="width:98%"> 
	<div class="mainbox">
		<div id="main_content">
			<form id="addUserForm" action="${ctx}/admin/pri/user/add.action">
			<input   type="hidden" id="from" name="from"  value="portal" />
				<table class="grid">
					<tr>
						<td class="title" width="30%"><span class="srd"> 用户名（手机号码）：</span></td>
					  	<td width="70%"><span class="right">
							<input type="text" style="width:90%" name="user.mobile" id="mobile" tabindex="1"  value="${user.mobile }" maxlength="20" size="20"></input>
							</span>
						</td>
					</tr>
					<tr>
					  	<td class="title"  >姓  名：</td>
					  	<td >
					  		<span class="right">
								<input type="text" style="width:90%" id="user.name" name="user.name" tabindex="3"  value="${user.name }" maxlength="20" size="20"></input>
							</span>
					  	</td>
					</tr>
					<tr>
						<td class="title"  > 组  织：</td>
					  	<td >
					  		<input type="hidden" id="user.org.id" name="user.org.id" value="${_organization_.id}" ></input>
					  		<span><font>${_organization_.name}</font></span>
						</td>
					</tr>
					<tr>
					  	<td class="title"  >职  位：</td>
					  	<td >
					  		<span class="right">
								<input type="text" name="user.job" style="width:90%" tabindex="1"  value="${user.job }" maxlength="20" size="20"></input>
							</span>
					  	</td>
					</tr>
					<tr>
						<td class="title"  >邮  箱：</td>
					  	<td >
					  		<span class="right">
								<input type="text" name="user.mailbox" style="width:90%" tabindex="1"  value="${user.mailbox }" maxlength="20" size="20"></input>
							</span>
						</td>
					</tr>
					<tr>
					  	<td class="title"  >地  址：</td>
					  	<td width="100">
					  		<span class="right">
								<input type="text" name="user.address"  style="width:90%" tabindex="1"  value="${user.address }" maxlength="20" size="20"></input>
							</span>
					  	</td>
					</tr>
					<tr>
						<td class="title"  >备   注：</td>
					  	<td >
					  		<span class="right">
								<textarea name="user.comments" rows="3" style="width:90%" tabindex="5" maxlength="100">${user.comments }</textarea>					
							</span> 
						</td>
					</tr>
	      		</table>
				
				<div class="neidi">
					<font id="tip_err_msg" style="line-height:35px" color="red"></font>
					<br/>
				</div>
				
				<div class="neidi">
					<input type="button" value="保存" class="lbtn" id="addUserBtn" onclick="addUser();"/>
				</div>
				<br/>
				<br/>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	// 新增校验
	function validateAdd() {
        var mobile = $("#mobile").val();

        if (isEmpty(mobile)) {
            $("#tip_err_msg").html("请输入用户名。");
            $("#tip_err_msg").show();
            return false;
        }

        var bool = /^1[0-9]{10}$/.test(mobile);
        if (bool == false) {
            $("#tip_err_msg").html("用户名请输入11位手机号码。");
            $("#tip_err_msg").show();
            return false;
        }
        
        return true;
	}
	
	function addUser(){
		$("#tip_err_msg").hide();
       	var tmp_bool = validateAdd();
        if (tmp_bool != true) {
            return false;
        }
        
		$("#addUserForm").ajaxSubmit(function(data){
			alert(data.retmsg);
			if(data.retcode == "0"){			
				window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?returnType=portal_addmeeting&meeting.id=${meeting_id}";
			}
		});
	}
	
</script>
</body>
</html>