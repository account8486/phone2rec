<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>司机管理</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${util_js}
</head>
<body>
	<div class="page_title">
		<h3>司机管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addForm" action="#" method="post">
		<input   type="hidden" id="vehicleDriver.id" name="vehicleDriver.id"  value="${vehicleDriver.id}" />
		<c:set var="meetingId" value="${empty param.meetingId ? vehicleDriver.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="vehicleDriver.meetingId" name="vehicleDriver.meetingId"  value="${meetingId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>姓名：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="vehicleDriver_name" tabindex="1" name="vehicleDriver.name"   value="${vehicleDriver.name}" maxlength="30"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>手机号：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="vehicleDriver_mobile" tabindex="2" name="vehicleDriver.mobile"   value="${vehicleDriver.mobile}" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">&nbsp;性别：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="vehicleDriver_gender" name="vehicleDriver.gender" tabindex="3">
						<option value="0" ${0 eq vehicleDriver.gender ? 'selected':'' }>男</option>
						<option value="1" ${1 eq vehicleDriver.gender ? 'selected':'' }>女</option>
					</select>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">年龄：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="vehicleDriver_age" tabindex="4" name="vehicleDriver.age"  value="${vehicleDriver.age}" maxlength="3" onkeyup="value=value.replace(/[^\d]/g,'')"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<textarea id="comments" name="vehicleDriver.comments" class="medium half" >${vehicleDriver.comments}</textarea>
	                <small>最多200个字,还可以输入<span id="remain">200</span>个字</small>
					<font id="tip_role" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	    	<div style="margin-bottom: 10px; margin-top: 10px;" align="left">
	       		 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:returnList('${meetingId }');" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	        </div>
	    </div>
	</form>
	</div>
	<script type="text/javascript">
		
		$("#comments").calcWordNum({maxNumber:200,targetid:"remain"});

		function validate()
		{    
		     var vehicleDriverName=$.trim($("#vehicleDriver_name").val());
		     if(vehicleDriverName.length == 0){
		     	alert("请输入司机姓名。");
		     	return false;
		     }
		     
		    var mobileVal=$("#vehicleDriver_mobile").val();
		    if(mobileVal.length == 0){
		    	alert("请输入手机号码。");
		    	return false;
		    }
		    if(mobileVal.length!=11){
		    	alert("手机号码长度必须为11位。");
		    	return false;
		    }
	    	if(mobileVal.substr(0,1)!='1'){
		    	alert("手机号码必须是以1开头的11位数字。");
	    	    return false;
	    	}
			return true;
		}

		function save(){
			if(validate()){
				submit();
				document.getElementById("submitBtn").disabled = "disabled";
			}
		}

		function submit(){
			var url = '';
			if('${vehicleDriver.id}'==''){
				url= '${ctx}/admin/pri/journey/addVehicleDriver.action';
			}else{
				url= '${ctx}/admin/pri/journey/modifyVehicleDriver.action';				
			}
			
			var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
			$('#addForm').ajaxSubmit(options);
		}
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){				
				returnList(data.retdata.meetingId);
			}
			document.getElementById("submitBtn").disabled = "enabled";
		}
		
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/journey/listVehicleDriver.action?meetingId="+meetingId;
		}
		
</script>
</body>
</html>