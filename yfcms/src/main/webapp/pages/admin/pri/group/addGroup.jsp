<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>分组计划管理</title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${validate_js}
	${util_js}
</head>
<body>
<%
	String retMsg=(String)request.getAttribute("retMsg");
	if(retMsg!=null&&!"".equals(retMsg)){
%>
	<script type="text/javascript">
	alert('<%=retMsg%>');
	</script>
<%
}
%>

	<div class="page_title">
		<h3>分组模版管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="addGroupPlanDetailForm" action="#" method="post" >
		<input   type="hidden" id="groupPlanDetail.id" name="groupPlanDetail.id"  value="${groupPlanDetail.id}" />
		<c:set var="meetingId" value="${empty param.meetingId ? groupPlanDetail.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="groupPlanDetail.meetingId" name="groupPlanDetail.meetingId"  value="${meetingId}" />
		<c:set var="planId" value="${empty param.planId ? groupPlanDetail.planId : param.planId}"></c:set>
		<input   type="hidden" id="groupPlanDetail.planId" name="groupPlanDetail.planId"  value="${planId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>组名：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="groupName" tabindex="1" name="groupPlanDetail.groupName"  value="${groupPlanDetail.groupName}" class="half" maxlength="50"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>请填写组名。最多50个字</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>排序：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="groupShowIndex" tabindex="2" onkeyup="value=value.replace(/[^\d]/g,'')" name="groupPlanDetail.showIndex"  value="${groupPlanDetail.showIndex}" class="half" maxlength="3"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>请填写该组显示顺序</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">详细内容：</label>
	            </dt>
	            <dd>
	            	<textarea class="medium half" id="groupDetail" rows="8" cols="10"
								name="groupPlanDetail.detail">${groupPlanDetail.detail}</textarea>
					<font id="tip_desc" style="line-height:35px" color="red"></font>
	                <small>目前还可以输入<span id="remain">500</span>个字</small>
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
	       		 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	        </div>
	    </div>
	</form>
	</div>
	<script type="text/javascript">

		function validate()
		{    
		     var groupPlanName = $('#groupName').val();
		     if(groupPlanName==""){
		     	alert('请填写组名');
		     	return false;
		     }
		     var groupShowIndex = $('#groupShowIndex').val();
		     if(groupShowIndex==""){
		     	alert('请指定排序号');
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
			if('${groupPlanDetail.id}'==''){
				$('#addGroupPlanDetailForm').attr("action","${ctx}/admin/pri/group/addGroup.action");
			}else{
				$('#addGroupPlanDetailForm').attr("action","${ctx}/admin/pri/group/modifyGroup.action");
			}
			
			$('#addGroupPlanDetailForm').submit();
		}
		
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/group/listGroup.action?groupPlan.id=${planId}";
		}
        
        $("#groupDetail").calcWordNum({maxNumber:500,targetid:"remain"});

</script>
</body>
</html>