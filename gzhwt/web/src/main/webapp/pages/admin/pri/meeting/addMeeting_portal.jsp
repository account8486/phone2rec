<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
${style_css}                                   
${jquery_js}                                 
${jquery_form_js}
${WdatePicker_js}
</head>
<body>
	<div > 
		
			<div>

	<form id="addMeetingForm" action="${ctx}/pages/admin/pri/meeting/addMeeting.action" >
	<input   type="hidden" id="meeting.id" name="meeting.id"  value="${meeting.id}" />
	<s:token />
	<table width="100%"  class="grid">
	<tr >
		<td class=title scope="col" width="20%" > 会议名称</td>
		<td colspan="3" width="80%" align="left">
			<input style="width:98%" type="text" id="meeting_name" name="meeting.name"  value="${meeting.name}" maxlength="100"/>
		</td>
	</tr>
	<tr >
		<td class=title scope="col" width="20%" > 会议类型</td>
		<td colspan="3" width="80%" align="left" >
			<select id="meeting.type" name="meeting.type">
				<option value="1">研讨会</option>
				<option value="2">公司年会</option>
			</select>
		</td>
	</tr>
	<tr >
		<td class=title scope="col" width="20%" > 会议议题</td>
		<td colspan="3" width="80%" align="left" >
			<textarea  style="width:98%" id="meeting.topic" rows="10" name="meeting.topic" maxlength="2000">${meeting.topic}</textarea>
		</td>
	</tr>
	<tr >
		<td class=title scope="col" width="20%" > 会议地点</td>
		<td colspan="3" width="80%" align="left">
			<input style="width:98%" type="text" id="meeting.location" name="meeting.location"  value="${meeting.location}" maxlength="100"/>
		</td>
	</tr>
	<tr >
		<td class=title scope="col" width="20%" > 会议开始时间</td>
		<td  width="30%" align="left" >
			<input id="meeting.startTime" type="text" name="meeting.startTime"  value="${fn:substring(meeting.startTime,0,10)}"  class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
		</td>
		<td class=title scope="col" width="20%" > 会议结束时间</td>
		<td  width="30%" align="left" >
			<input id="meeting.endTime" type="text" name="meeting.endTime"  value="${fn:substring(meeting.endTime,0,10)}"  class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'meeting.startTime\')||\'2020-10-01\'}',maxDate:'2020-10-01'})" readonly="readonly"/>
		</td>
	</tr>
	<tr >
		<td class=title  scope="col" width="20%" > 备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
		<td colspan="3" width="80%" align="left" >
			<textarea  style="width:98%" id="meeting.comments" rows="3" cols="20" name="meeting.comments" maxlength="200">${meeting.comments}</textarea>
		</td>
	</tr>
	<tr >
		<td class=title scope="col" width="20%" bgcolor="#F2F2F2"> 会议状态</td>
		<td colspan="3" width="80%" align="left" >
			<select id="meeting.state" name="meeting.state">
				<option value="1">有效</option>
				<option value="0">无效</option>
			</select>
		</td>
	</tr>

	
</table>
	<input type="button" value="保存" onclick="save();" class="lbtn" id="submitBtn"/>
	&nbsp;&nbsp;

	</form>
	
	 		</div>
		
	</div>
	
<script type="text/javascript">
		function save(){
			//var formData = $('#addMeetingForm').formJsonData(); 
			//alert(formData);
			var name = $('#meeting_name').val();
			name = $.trim(name);
			if(name.length == 0 ){
				alert("请输入会议名称.");
				return false;
			}
			if(name.length > 25){
				alert("会议名称不能超过25个字.");
				return false;
			}			

			submit();
			//document.getElementById("submitBtn").disabled = "disabled";
		}


		function submit(){
			//alert('${meeting.id}');
			var url = '';
			if('${meeting.id}'==''){
				url= '${ctx}/pages/admin/pri/meeting/addMeeting.action';
			}else{
				url= '${ctx}/pages/admin/pri/meeting/modifyMeeting.action';				
			}
			
			var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
			$('#addMeetingForm').ajaxSubmit(options);		
		}
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){
				parent.tb_remove();
				parent.location.href = parent.location.href;
			}
			//document.getElementById("submitBtn").disabled = "enabled";
		}
		
		function returnList(){
			window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=portal&meeting.id="+meetingId;
		}
		
</script>
</body>
</html>