<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>车辆管理</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${util_js}
</head>
<body>
	<div class="page_title">
		<h3>车辆管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addForm" action="#" method="post">
		<input   type="hidden" id="vehicle.id" name="vehicle.id"  value="${vehicle.id}" />
		<c:set var="meetingId" value="${empty param.meetingId ? vehicle.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="vehicle.meetingId" name="vehicle.meetingId"  value="${meetingId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>车辆名称：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="vehicle_name" tabindex="1" name="vehicle.name"   value="${vehicle.name}" maxlength="30"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">&nbsp;车牌号：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="vehicle_number" tabindex="2" name="vehicle.number"  value="${vehicle.number}" maxlength="20"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">容纳人数：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="vehicle_number" tabindex="3" name="vehicle.capacity"  value="${vehicle.capacity}" maxlength="4" onkeyup="value=value.replace(/[^\d]/g,'')"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<textarea id="comments" name="vehicle.comments" class="medium half" >${vehicle.comments}</textarea>
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
		     var vehicleName=$.trim($("#vehicle_name").val());
		     if(vehicleName.length == 0){
		     	alert("请输入车辆名称。");
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
			if('${vehicle.id}'==''){
				url= '${ctx}/admin/pri/journey/addVehicle.action';
			}else{
				url= '${ctx}/admin/pri/journey/modifyVehicle.action';				
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
			window.location.href = "${ctx}/admin/pri/journey/listVehicle.action?meetingId="+meetingId;
		}
		
</script>
</body>
</html>