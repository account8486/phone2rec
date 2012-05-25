<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安徽电信天翼云平台</title>
${admin_css}                                   
${jquery_js}                                
${jquery_form_js}                                 
${validate_js}                                
${WdatePicker_js}                           
${admin_js}                   
${area_js}        
${jmpopups_js}   
${util_js}      
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
    <c:if test="${meeting.id !='' && meeting.id != null}">
		<h3>基本信息编辑  -- ${CURRENT_MEETING}</h3>	
	</c:if>	
    <c:if test="${meeting.id == null}">
		<h3>添加基本信息</h3>	
	</c:if>		
	
	</div>
	<div class="page_form">
	<form id="addMeetingForm" action="${ctx}/pages/admin/pri/meeting/addMeeting.action" >
	<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
	
	<fieldset>
        <legend>基本信息</legend>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</label>
            </dt>
            <dd>
            	<input style="width:80%" type="text" class="half" id="meeting_name" name="meeting.name"  value="${fn:escapeXml(meeting.name)}" maxlength="40"/>
            	<small>请限制在40字以内,您还可以输入<span id="meeting_name_remain">40</span>字</small>
            	<!-- <small>如标题太长，则可输入空格进行换行设置，若标题中存在多个空格，则以第一个空格作为换行标志<span id="meeting_name_remain">40</span>字</small> -->
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</label>
            </dt>
            <dd>
            <c:if test="${meeting.id == null}">
            	<select id="meeting_type" name="meeting.meetingType.id">
            	<s:iterator value="meetingTypeList" var="mType">
            		<option value="${mType.id }" ${meeting.meetingType.id == mType.id ? "selected" : "" }>${mType.name }</option>
            	</s:iterator>
				</select>
            </c:if>
            <c:if test="${meeting.id !='' && meeting.id != null}">
            	<label><wd:custom  type="translate" entity="meeting_type" entityValue="id" entityName="name" selectedValue="${meeting.meetingType.id}" condition="where id=${meeting.meetingType.id}"></wd:custom></label>
            	<input type="hidden" id="meeting_type" name="meeting.meetingType.id" value="${meeting.meetingType.id}"/>
            </c:if>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
            </dt>
            <dd>
            	<textarea style="width:80%" class="medium" id="meeting.topic" name="meeting.topic" maxlength="2000">${fn:escapeXml(meeting.topic)}</textarea>
            	<small>请限制在2000字以内,您还可以输入<span id="meeting_topic_remain">2000</span>字</small>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点：</label>
            </dt>
            <dd>
            	<jsp:include page="/pages/common/area.jsp"></jsp:include>
				<p></p>
            	<input style="width:80%" type="text" id="meeting.location" name="meeting.location"  value="${fn:escapeXml(meeting.location)}" maxlength="30"/>
            	<small>请限制在30字以内,您还可以输入<span id="meeting_location_remain">30</span>字</small>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>开始时间：</label>
            </dt>
            <dd>
            	<input id="startTime" type="text" name="meeting.startTime"  value="${fn:substring(meeting.startTime,0,16)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')||\'2019-12-31 00:00\'}' })" readonly="readonly"/>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>结束时间：</label>
            </dt>
            <dd>
            	<input id="endTime" type="text" name="meeting.endTime"  value="${fn:substring(meeting.endTime,0,16)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')||\'2019-12-31 23:59\'}',maxDate:'2019-12-31 23:59'})" readonly="readonly"/>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title">免费短信条数：</label>
            </dt>
            <dd>
            	<input style="width:20%" id="freeSmsNum" type="text" name="meeting.freeSmsNum"  value="${meeting.freeSmsNum}" maxlength="8" />
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title">总机号码：</label>
            </dt>
            <dd>
            	<c:if test="${meeting.id !='' && meeting.id != null}">
		        	<div style="padding-top: 5px;margin-left: 10px"><font style="color:red;">${meeting.serviceNumber}</font></div>
		        	<input type="hidden" id="service_number" name="meeting.serviceNumber"  value="${meeting.serviceNumber}" />
		        </c:if>
				<c:if test="${meeting.id == null}">
					<input style="width:20%" type="text" id="service_number" name="meeting.serviceNumber"  value="${meeting.serviceNumber}" maxlength="20" />
          	    	<small>请输入正确的总机号码，只能使用数字。</small>
 		        </c:if>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
            </dt>
            <dd >
            	<input type="radio" id="state_valid" name="meeting.state" value="1" checked="checked"/>&nbsp;<label for="state_valid">有效</label> 
            	<input type="radio" id="state_invalid"  name="meeting.state" value="0" />&nbsp; <label for="state_invalid">无效</label> 
            </dd>
        </dl>
    </fieldset>
	<fieldset>
		<legend>其他信息</legend>
	    <dl>
        	<dt>
            	<label for="title">通知公告：</label>
            </dt>
            <dd>
            	<input style="width:80%"  id="meeting.notice" type="text" name="meeting.notice"  value="${fn:escapeXml(meeting.notice)}"  maxlength="200"/>
            	<small>请限制在200字以内,您还可以输入<span id="meeting_notice_remain">200</span>字</small>
            </dd>
        </dl>
	    <dl>
        	<dt>
            	<label for="title">报到时间：</label>
            </dt>
            <dd>
            	<input style="width:80%"  id="meeting.joinTime" type="text" name="meeting.joinTime"  value="${fn:escapeXml(meeting.joinTime)}"  maxlength="200"/>
            	<small>请限制在200字以内,您还可以输入<span id="meeting_jointime_remain">200</span>字</small>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title">访问开始时间：</label>
            </dt>
            <dd>
            	<input id="meeting.accessStartTime" type="text" name="meeting.accessStartTime"  value="${fn:substring(meeting.accessStartTime,0,10)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'meeting.accessEndTime\')||\'2020-10-01\'}'})" readonly="readonly"/>
            </dd>
        </dl>
        <dl>
        	<dt>
            	<label for="title">访问结束时间：</label>
            </dt>
            <dd>
            	<input id="meeting.accessEndTime" type="text" name="meeting.accessEndTime"  value="${fn:substring(meeting.accessEndTime,0,10)}"  class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'meeting.accessStartTime\')||\'2020-10-01\'}',maxDate:'2020-10-01'})" readonly="readonly"/>
            </dd>
        </dl>
	    <dl>
        	<dt>
            	<label for="title"> &nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
            </dt>
            <dd>
            	<textarea  style="width:80%" class="medium" id="meeting.comments" name="meeting.comments" maxlength="200">${fn:escapeXml(meeting.comments)}</textarea>
            	<small>请限制在200字以内,您还可以输入<span id="meeting_comments_remain">200</span>字</small>
            </dd>
        </dl>
    </fieldset>
    <div class="page_form_sub">
        <a href="##" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>    
    </div>

	</form>
	
	</div>
</div>
	
<script type="text/javascript">

$().ready(function() {
	$("#meeting_name").calcWordNum({maxNumber:40,targetid:"meeting_name_remain"});
	$("#meeting\\.topic").calcWordNum({maxNumber:2000,targetid:"meeting_topic_remain"});
	$("#meeting\\.location").calcWordNum({maxNumber:30,targetid:"meeting_location_remain"});
	$("#meeting\\.notice").calcWordNum({maxNumber:200,targetid:"meeting_notice_remain"});
	$("#meeting\\.joinTime").calcWordNum({maxNumber:200,targetid:"meeting_jointime_remain"});
	$("#meeting\\.comments").calcWordNum({maxNumber:200,targetid:"meeting_comments_remain"});
	
	//根据已有数据获取省市编码
	initArea("${meeting.province}","${meeting.city}");
	

	if('${meeting.id}'!=''){
		gotobase();		
		//$("#meeting_state").val(${meeting.state});
	    $("input[name='meeting.state'][value=${meeting.state}]").attr("checked",true);  
	}
	
	jQuery.validator.addMethod("areaValid", function(value, element) {
	    if ( $("#city").val() == "0000" ){
	    	return false;
	    }
		return true;
	}, "请选择省份和城市");
	
	jQuery.validator.addMethod("serviceNumDepend", function(value, element) {
		if($("#service_number").val() == ""){
			return true;
		}else  if ( $("#startTime").val() == "" || $("#endTime").val() =="" || $("#freeSmsNum").val() ==""){
	    	return false;
	    }
		return true;
	}, "请输入开始时间、结束时间和免费短信条数");
	

	$("#addMeetingForm").validate({
		onkeyup: false,
		rules: {
			"meeting.name": "required",
			"meeting.meetingType.id": "required",
			"meeting.topic": {
				required :true,
				maxlength: 2000
			},
			"meeting.city": {
				areaValid:true
			},
			"meeting.location": "required",
			"meeting.startTime": "required",
			"meeting.endTime": "required",
			"meeting.freeSmsNum": {
				maxlength: 8,
				digits: true
			},
			"meeting.serviceNumber": {
				serviceNumDepend:true,
				minlength: 7,
				number: true,
				remote:{
				    type: "POST",
				    dataType:"json",
				    url: "${ctx}/pages/admin/pri/meeting/checkServiceNumber.action",
				    data: {
				    		"meeting.startTime":function(){ return jQuery("#startTime").val();},
				    		"meeting.endTime":function(){ return jQuery("#endTime").val();},
				    		"meeting.freeSmsNum":function(){ return jQuery("#freeSmsNum").val();}
				    	   }				   
				}
			}
		},
		messages: {
			"meeting.name": {
				required :"请输入名称！",
				maxlength:"名称已超过25个字！"
			},
			"meeting.meetingType.id": {
				required :"请选择类型！"
			},
			"meeting.topic": {
				required :"请输入主题！",
				maxlength:"主题内容已超过2000字！"
			},
			"meeting.location": {
				required :"请输入地点！",
				maxlength:"地点已超过30字！"
			},
			"meeting.startTime": "请输入开始时间！",
			"meeting.endTime": "请输入结束时间！",
			"meeting.serviceNumber":{
				required :"请输入总机号码！",
				minlength: "请至少输入7位数字！",
				number: "请至少输入7位数字！",
				remote: "总机号码不存在，请检查！"
			} ,
			"meeting.freeSmsNum":{
				required :"请输入免费发送短信条数！",
				maxlength: "不能超过8位数字！",
				digits: "请输入整数！"
			}
		}
	});
});

		function save(){
			if($("#submitBtn").hasClass("btn_false")){
				alert("您已经添加过本次编辑内容，请不要重复保存！");
				return false;
			}

			submit();
			//document.getElementById("submitBtn").disabled = "disabled";
		}


		function submit(){
			var url = '';
			if('${meeting.id}'==''){
				url= '${ctx}/pages/admin/pri/meeting/addMeeting.action';
			}else{
				url= '${ctx}/pages/admin/pri/meeting/modifyMeeting.action';				
			}
			
			var options = { 
					beforeSubmit: validate,
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
			$('#addMeetingForm').ajaxSubmit(options);		
		}
		//回调函数
		function callback(data){
			hideLoading();
			alert(data.retmsg);
			if(data.retcode=="0"){
				showEditMenu("${ctx}",data.retdata.id);
				gotobase();		
			}else{

				$("#submitBtn").removeClass("btn_false");
				$("#submitBtn").addClass("btn_true");
			}
		}
		
		function validate()
		{    
		     var isSuccess=$("#addMeetingForm").validate().form(); //.form(); 返回是否验证成功
			if(isSuccess){
				if('${meeting.id}'==''){
					$("#submitBtn").removeClass("btn_true");
					$("#submitBtn").addClass("btn_false");
				}
				showLoading("正在保存，请稍候...");
				
			}
		     return isSuccess;
		}
		

</script>
</body>
</html>