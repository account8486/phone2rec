<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>行程管理</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${util_js} ${WdatePicker_js}
</head>
<body>
	<div class="page_title">
		<h3>行程管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addForm" action="#" method="post">
		<input   type="hidden" id="journey.id" name="journey.id"  value="${journey.id}" />
		<c:set var="meetingId" value="${empty param.meetingId ? journey.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="journey.meetingId" name="journey.meetingId"  value="${meetingId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>行程名称：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="journey_name" tabindex="1" name="journey.name"   value="${journey.name}" maxlength="30"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>车辆：</label>
	            </dt>
	            <dd>
	            	<select id="vehicleId" name="journey.vehicleId" value="${journey.vehicleId }">
	            		<option value="-1">请选择</option>
	            		<c:forEach items="${vehicleList}" var="vehicle">
	            			<option value="${vehicle.id }" ${journey.vehicleId eq vehicle.id ? 'selected':'' }>${vehicle.name }</option>
	            		</c:forEach>
	            	</select>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>司机：</label>
	            </dt>
	            <dd>
	            	<select id="driverId" name="journey.driverId"  value="${journey.driverId }">
	            		<option value="-1">请选择</option>
	            		<c:forEach items="${driverList}" var="driver">
	            			<option value="${driver.id }" ${journey.driverId eq driver.id ? 'selected':'' }>${driver.name }</option>
	            		</c:forEach>
	            	</select>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">&nbsp;出发时间：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="startTime" tabindex="2" name="startTime"  value="<fmt:formatDate value='${journey.startTime}' pattern='yyyy-MM-dd HH:mm'/>" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d %H:00:00'})" readonly="readonly"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">出发地点：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="journey_departure" tabindex="3" name="journey.departure"  value="${journey.departure}" maxlength="30" />
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">&nbsp;到达时间：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="journey_endTime" tabindex="4" name="endTime"  value="<fmt:formatDate value='${journey.endTime}' pattern='yyyy-MM-dd HH:mm'/>" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d %H:00:00'})" readonly="readonly"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">目的地：</label>
	            </dt>
	            <dd>
	            	<input  type="text"  id="journey_departure" tabindex="5" name="journey.destination"  value="${journey.destination}" maxlength="30" />
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">乘坐人：</label>
	            </dt>
	             <dd>
                	<textarea name="recieverMobiles" id="recieverMobiles"  readonly="readonly" disabled="disabled" style="width:400px;height:58px">${recieverMobiles}</textarea>
                    <input type="hidden" name="recieverIds" id="recieverIds" value="${recieverIds}">
                    <a href="#" onclick="selectUsers();" id="addUserBtn" class="btn_common btn_false">选择成员</a>
                </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<textarea id="comments" name="journey.comments" class="medium half" >${journey.comments}</textarea>
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
		     var journeyName=$.trim($("#journey_name").val());
		     if(journeyName.length == 0){
		     	alert("请输入行程名称。");
		     	return false;
		     }
		     var vehicle=$.trim($("#vehicleId").val());
		     if(vehicle == '-1'){
		     	alert("请选择车辆。");
		     	return false;
		     }
		     var driver=$.trim($("#driverId").val());
		     if(driver == '-1'){
		     	alert("请选择司机。");
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
			if('${journey.id}'==''){
				url= '${ctx}/admin/pri/journey/addJourney.action';
			}else{
				url= '${ctx}/admin/pri/journey/modifyJourney.action';				
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
			window.location.href = "${ctx}/admin/pri/journey/listJourney.action?meetingId="+meetingId;
		}
		
		function selectUsers(){
			var recieverIds=$("#recieverIds").val();
			var meetingId=document.getElementById("journey.meetingId").value;
			//
			//alert(recieverIds);
			//alert(meetingId);
			
			var url="${ctx}/admin/pri/message/getMeetingMember.action?meetingId="+meetingId+"&recieverIds="+recieverIds;
			window.open(url,'','width=800,height=500,left=200,top=200,scrollbars=yes,location=no');
		}
		
</script>
</body>
</html>