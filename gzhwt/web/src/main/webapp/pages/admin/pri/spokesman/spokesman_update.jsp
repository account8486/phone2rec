<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>编辑发言人</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}         
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
<script>

$(document).ready(function() {
	$("#gender").val(${spokesman.gender});
});


</script>
</head>
<body>
	<div class="page_title">
		<h3>添加发言人  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="page_form">
	<form id="addSpokesmanForm" action="${ctx}/admin/pri/spokesman/updateSpokesMan.action" method="post">
	<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <fieldset>
	        <legend>基本信息</legend>
	        <input type="hidden"  id="id" name="id" value="${spokesman.id }" ></input>	
	          <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>姓&nbsp;&nbsp;&nbsp;名：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="name" name="name" value="${spokesman.name }" tabindex="2" maxlength="30"></input>	            	
	            </dd>
	        </dl>	
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>手机号码：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" onkeyup="value=value.replace(/[^\d]/g,'')" id="mobile" name="mobile" value="${spokesman.mobile}" <c:if test="${not empty member}">readonly="readonly"</c:if> tabindex="1" maxlength="11"></input>
	                <small>请输入11位手机号码</small>
	            </dd>
	        </dl>	
	        
	       <dl>
	        	<dt>
	            	<label for="title">性&nbsp;&nbsp;&nbsp;别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="gender" name="gender" tabindex="5">
						<option value="0">男</option>
						<option value="1">女</option>
						<option value=""></option>
					</select>
	            </dd>
	        </dl>	
	        

	   
	    </fieldset>
	    <div class="neidi">&nbsp;</div>
	    <div class="page_form_sub">
	        <a href="#" onclick="add();" id="addUserBtn" class="btn_common btn_true">保 存</a>　
	        <a href="${ctx}/admin/pri/spokesman/getSpokesManLst.action?meetingId=${meetingId}" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	   
	</form>
	</div>
	<script type="text/javascript">
		// 新增校验
		
		$().ready(function() {
			
			$("#addSpokesmanForm").validate({
				onkeyup: false,
				rules: {
					"mobile": {
						required :false,
						minlength: 11,
						maxlength: 11,
						digits: true,
						isMobile:true
					},
					"name": "required"
				},
				messages: {
					"mobile": "请输入以数字1开头的11位手机号码！",	
					"name": "请输入发言人姓名！"
				}
			});
			
		});
		
		jQuery.validator.addMethod("isMobile", function(value, element) {
		    var mobileVal=$("#mobile").val();
		    if(mobileVal!=''){
		    	if(mobileVal.substr(0,1)!='1'){
		    		  return false;
		    	}
		    }
			return true;
		}, "手机号码首字母必须为数字1");
		
		function add(){	
			var name=$("#name").val();
			if(name==''){
				alert("请输入发言人姓名！");
				return false;
			}
			
			$("#addSpokesmanForm").submit();
			//$("#addUserBtn").attr("disabled","disabled");
		}
		
		
		
	</script>	
</body>
</html>