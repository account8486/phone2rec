<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理</title>
	${admin_css}                                   
	${jquery_js}
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function(){
			$("#roleId").val(${user.roleId});
		})
	
		
		// 新增校验
		function validateAdd() {
	        var password = $("#password").val();
	
	        if (isEmpty(password)) {
	            $("#tip_password").html("请输入密码。");
	            $("#tip_password").show();
	            return false;
	        }
	        
	        var result = /^.{6,20}$/.test(password);
	        if (result == false) {
	        	$("#tip_password").html("请输入6到20位长度密码。");
	            $("#tip_password").show();
	            return false;
	        }
	        
	        var name = $("#name").val();
	
	        if (isEmpty(name)) {
	            $("#tip_name").html("请输入姓名。");
	            $("#tip_name").show();
	            return false;
	        }
	        
	        var org = $("#org").val();
	        
	        var role = $("#role").val();
			/**
	        if (isEmpty(role)) {
	            $("#tip_role").html("请选择角色。");
	            $("#tip_role").show();
	            return false;
	        }
			*/
	        

	        
			var comments = $("#comments").val();
			
		    if (!isEmpty(comments)) {
		    	if(comments.length > 400) {
		    		$("#tip_comments").html("备注不能超过400个字符。");
		        	$("#tip_comments").show();
		        	return false;
		    	}
		    }
		    
	        return true;
		}
		
		function updateUser(){
			$("[id^='tip_']").hide();
	       	var tmp_bool = validateAdd();
	        if (tmp_bool != true) {
	            return false;
	        }
	        
			$("#updateUserForm").submit();
			$("#updateUserBtn").attr("disabled","disabled");
		}
		
		function retUserList()
		{
			//window.location.href = "${ctx}/pages/admin/pri/user/listAndTree.jsp";
			window.location.href = "${ctx}/admin/pri/user/list.action";
		}
	</script>
</head>
<body>
	<div class="page_title">
		<h3>用户编辑</h3>
	</div>
	<div class="page_form">
	<form id="updateUserForm" action="${ctx}/admin/pri/user/update.action">
		<input type="hidden" id="user.id" name="user.id" value="${user.id}"/>
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>用户名：</label>
	            </dt>
	            <dd><font style="line-height:35px">${user.mobile }</font></dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>密&nbsp;&nbsp;&nbsp;码：</label>
	            </dt>
	            <dd>
	            	<input type="password" class="quart" id="password" name="user.password" value="${user.password }" tabindex="1" maxlength="11"></input>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small>请输入6到20位长度密码。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>姓&nbsp;&nbsp;&nbsp;名：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="quart" id="name" name="user.name" value="${user.name }" tabindex="2" maxlength="30"></input>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>组&nbsp;&nbsp;&nbsp;织：</label>
	            </dt>
	            <dd>
					<font style="line-height:35px">${user.org.name }</font>
			    	<input type="hidden" id="org" name="user.org.id" value="${user.org.id}"/>
			    	<input type="hidden" id="organId" name="organId" value="${user.org.id}"/>
	            </dd>
	        </dl>
	       
	       
	         <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>角&nbsp;&nbsp;&nbsp;色：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="roleId" name="user.roleId" tabindex="5">
						<c:forEach var="role" items="${securityRolesList}" varStatus="status">
							<option value="${role.id }">${role.roleName }</option>
						</c:forEach>
					</select>
					<font id="tip_role" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	        
	    </fieldset>
	    <fieldset>
	        <legend>其他信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title">职&nbsp;&nbsp;&nbsp;位：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="quart" name="user.job" value="${user.job }" maxlength="64" tabindex="5"></input>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">性&nbsp;&nbsp;&nbsp;别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="user.gender" name="user.gender" tabindex="6">
						<option value="0" <c:if test="${user.gender eq 0 }"> selected </c:if>>男</option>
						<option value="1" <c:if test="${user.gender eq 1 }"> selected </c:if>>女</option>
					</select>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">状&nbsp;&nbsp;&nbsp;态：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="user.state" name="user.state" tabindex="7">
						<option value="0" <c:if test="${user.state eq 0 }"> selected </c:if>>无效</option>
						<option value="1" <c:if test="${user.state eq 1 }"> selected </c:if>>有效</option>
			    	</select>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">电&nbsp;&nbsp;&nbsp;话：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="quart" name="user.tel" value="${user.tel }" maxlength="64" tabindex="8"></input>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">邮&nbsp;&nbsp;&nbsp;箱：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="quart" name="user.mailbox" value="${user.mailbox }" maxlength="50" tabindex="9"></input>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">地&nbsp;&nbsp;&nbsp;址：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" name="user.address" value="${user.address }" maxlength="200" tabindex="10"></input>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<textarea class="medium" id="comments" name="user.comments" rows="5" tabindex="11">${user.comments }</textarea>
	            	<font id="tip_comments" style="line-height:35px" color="red"></font>
	            	<small>备注不能超过400个字符。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="updateUser();" id="updateUserBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:retUserList();" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
</body>
</html>