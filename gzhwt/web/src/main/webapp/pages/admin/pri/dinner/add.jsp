<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理</title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${validate_js}
	${WdatePicker_js}
	${util_js}
</head>
<body>
	<div class="page_title">
		<h3>用餐信息  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addMeetingDinnerForm" action="${ctx}/admin/pri/meeting/addMeetingDinner.action" method="post">
		<input   type="hidden" id="dinner.id" name="dinner.id"  value="${dinner.id}" />
		<c:set var="meetingId" value="${empty param.meetingId ? dinner.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="dinner.meetingId" name="dinner.meetingId"  value="${meetingId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>日&nbsp;&nbsp;期：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="dinner_date" tabindex="1" name="dinner.dinnerDate"  value="${dinner.dinnerDate}" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy年MM月dd日'})" readonly="readonly"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>请选择用餐日期。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>地&nbsp;&nbsp;点：</label>
	            </dt>
	            <dd>
	            	<input  type="text" class="" id="dinner_address" tabindex="2" name="dinner.address"  value="${dinner.address}" maxlength="30"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small>请输入用餐地点。最多50个字。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>用餐时段：</label>
	            </dt>
	            <dd>
	            	<select id="dinner_section" class="inp_1" style="width:15.5%" tabindex="3" name="dinner.section" value="${dinner.section }" style="width:200px;">
						<option value="1" ${dinner.section == 1 ? 'selected' : '' }>早餐</option>
						<option value="2" ${dinner.section == 2 ? 'selected' : '' }>中餐</option>
						<option value="3" ${dinner.section == 3 ? 'selected' : '' }>晚餐</option>
					</select>
	            	
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl style="width:30%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>开始时间：</label>
	            </dt>
	            <dd style="width:48%;float:left;">
	            	<input  type="text" id="startTime" tabindex="4" name="dinner.startTime"  value="${dinner.startTime}" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'HH:mm',startDate:'%H:00:00'})" readonly="readonly"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>请选择用餐开始时间。</small>
					<font id="tip_role" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl style="width:30%;float:left;">
	        	<dt>
	            	<label for="title"><font color="red">* </font>结束时间：</label>
	            </dt>
	            <dd style="width:48%;float:left;">
	            	<input  type="text" id="endTime" tabindex="5" name="dinner.endTime"  value="${dinner.endTime}" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'HH:mm',startDate:'%H:00:00',minDate:'#F{$dp.$D(\'startTime\')||\'2020-10-01\'}'})" readonly="readonly"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>请选择用餐结束时间。</small>
					<font id="tip_role" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>类&nbsp;&nbsp;型：</label>
	            </dt>
	            <dd>
					<select class="inp_1" name="dinner.type" value="${dinner.type }" tabindex="6" style="width:15.5%">
						<c:forEach items="${dinnerTypeList}" var="dinnerType">
							<option value="${dinnerType.id }" ${dinner.type == dinnerType.id ? 'selected' : '' }>${dinnerType.name }</option>
						</c:forEach>
					</select>
					
	                <small>${dinnerTypeIdMap["3"].name }需要分桌，其他选项自选座位。</small>
					<font id="tip_role" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">备&nbsp;&nbsp;注：</label>
	            </dt>
	            <dd>
	            	<input  type="text" class="half" id="dinner_comments" tabindex="7" name="dinner.comments"  value="${dinner.comments}" maxlength="50"/>
	                <small>最多50个字,还可以输入<span id="remain">50</span>个字</small>
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
		
		$("#dinner_comments").calcWordNum({maxNumber:50,targetid:"remain"});

		function validate()
		{    
		     var isSuccess=$("#addMeetingDinnerForm").validate().form(); //.form(); 返回是否验证成功
		     return isSuccess;
		}

		function save(){
			if(validate()){
				submit();
				document.getElementById("submitBtn").disabled = "disabled";
			}
		}

		function submit(){
			//alert('${meeting.id}');
			var url = '';
			if('${dinner.id}'==''){
				url= '${ctx}/admin/pri/meeting/addMeetingDinner.action';
			}else{
				url= '${ctx}/admin/pri/meeting/modifyMeetingDinner.action';				
			}
			
			var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
			$('#addMeetingDinnerForm').ajaxSubmit(options);
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
			window.location.href = "${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId="+meetingId;
		}
		
		$(document).ready(function() {
			$("#addMeetingDinnerForm").validate({
			rules: {
			"dinner.dinnerDate": "required",
			"dinner.section": "required",
			"dinner.address": "required",
			"dinner.startTime": "required",
			"dinner.endTime": "required",
			"dinner.type": "required",
			"dinner.address": {
					required :true,
					maxlength: 30
				}
			},
			messages: {
				"dinner.dinnerDate": "请选择日期",
				"dinner.section": "请选择用餐时段",
				"dinner.address": "请填写用餐地点",
				"dinner.startTime": "请选择开始时间",
				"dinner.endTime": "请选择结束时间",
				"dinner.type": "请选择用餐类型",
				"dinner.address":{
					required :"请输入用餐地址！",
					maxlength: "最多30个字！"
				}
			}
			});
		});
					

</script>
</body>
</html>