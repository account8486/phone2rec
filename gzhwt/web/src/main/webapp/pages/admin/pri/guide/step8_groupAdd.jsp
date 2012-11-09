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

	<div style="width: 75%;float:left;margin-left: 5px;margin-right: 20px;">
	<div class="page_title">
		<h3>分组模版管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form5">
	<form id="addGroupPlanForm" action="#" method="post" enctype="multipart/form-data">
		<input   type="hidden" id="groupPlan.id" name="groupPlan.id"  value="${groupPlan.id}" />
		<input   type="hidden" id="groupPlanIsOpen" name="groupPlan.isOpen"  value="${groupPlan.isOpen}" />
		<c:set var="meetingId" value="${empty param.meetingId ? groupPlan.meetingId : param.meetingId}"></c:set>
		<input   type="hidden" id="groupPlan.meetingId" name="groupPlan.meetingId"  value="${meetingId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>模版名称：</label>
	            </dt>
	            <dd>
	            	<input type="text" id="groupPlanName" tabindex="1" name="groupPlan.planName"  value="${groupPlan.planName}" class="half" maxlength="10"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>请填写分组模版名称。最多10个字</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">模版描述：</label>
	            </dt>
	            <dd>
	            	<input  type="text" class="half" id="groupPlanDesc" tabindex="2" name="groupPlan.planDesc"  value="${groupPlan.planDesc}" maxlength="50"/>
	            	<font id="tip_password" style="line-height:35px" color="red"></font>
	                <small>请描述一下该分组模版。最多50个字,还可以输入<span id="remain">50</span>个字</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>分组类型：</label>
	            </dt>
	            <dd>
	            	<select id="planType" class="inp_1" tabindex="3" name="groupPlan.planType" value="${groupPlan.planType }">
						<option value="-1" ${groupPlan.planType == -1 ? 'selected' : '' }>请选择</option>
						<option value="1" ${groupPlan.planType == 1 ? 'selected' : '' }>议程分组</option>
						<option value="2" ${groupPlan.planType == 2 ? 'selected' : '' }>用餐分组</option>
					</select>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
					<label for="title">用户级别：</label>
				</dt>
	            <dd style="width: 68%">
					<c:set var="isOpen" value="${empty groupPlan.isOpen ? isOpen : groupPlan.isOpen}"></c:set>
					<input type="checkbox" name="isOpen" value="1" ${fn:contains(isOpen,"1") ? "checked":""} >一级&nbsp;&nbsp;
					<input type="checkbox" name="isOpen" value="2" ${fn:contains(isOpen,"2") ? "checked":""} >二级&nbsp;&nbsp;
					<input type="checkbox" name="isOpen" value="3" ${fn:contains(isOpen,"3") ? "checked":""} >三级&nbsp;&nbsp;
					<input type="checkbox" name="isOpen" value="4" ${fn:contains(isOpen,"4") ? "checked":""} >四级&nbsp;&nbsp;
					<input type="checkbox" name="isOpen" value="5" ${fn:contains(isOpen,"5") ? "checked":""} >五级
					
					<input type="checkbox" id="userLevelSelectAll" style="margin-left:40px;" /><label for="userLevelSelectAll">全选</label>
					<small>勾选相应级别用户来设置对其公开该分组信息</small>
				</dd>
			</dl>
			<c:if test="${empty groupPlan.id}">
		        <dl>
		        	<dt>
		            	<label for="title"><font color="red">* </font>分组模版：</label>
		            </dt>
		            <dd>
		            	<input  type="file" id="file" tabindex="4" name="upload" />
		            	<font id="tip_name" style="line-height:35px" color="red"></font>
		    			<div class=crumbs2><a href="${ctx}/xlstemplate/group_import_template.xls">导入模板下载</a></div>
		            </dd>
		        </dl>
			</c:if>
			<c:if test="${not empty groupPlan.id}">
		        <input  type="hidden"  name="groupPlan.importFile"  value="${groupPlan.importFile}" />
			</c:if>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	    	<div style="margin-bottom: 10px; margin-top: 10px;" align="left">
	    		 <a href="${ctx}/admin/pri/meeting/guide_step7.action?meetingId=${empty param.meetingId?groupPlan.meetingId : param.meetingId}" class="btn_common btn_true">上一步</a>
	       		 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　
	       		 <a href="#" onclick="doFinish();" class="btn_common btn_true">结束向导</a>
	        </div>
	    </div>
	</form>
	</div>
	</div>
	
	
	<jsp:include page="guideCommonRight.jsp" />
	<div style="clear: both"></div>
	
	
	
	<script type="text/javascript">
		function doFinish() {
			if(confirm("确定结束此会议向导操作吗？")) {
				location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
			}
		}
		
		$(function() {
			//用户级别选择增加全选功能
			$("#userLevelSelectAll").click(function() {
				var checked = $("#userLevelSelectAll").prop("checked");
				$(":checkbox[name='isOpen']").attr("checked", checked);
			});
		});

		function validate()
		{    
		     var groupPlanName = $('#groupPlanName').val();
		     if(groupPlanName==""){
		     	alert('请填写模版名称');
		     	return false;
		     }
		     var planType = $('#planType').val();
		     if(planType=="-1"){
		     	alert('请选择分组类型');
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
			$("#groupPlanIsOpen").val(getCheckBoxValue());
			
			if('${groupPlan.id}'==''){
				$('#addGroupPlanForm').attr("action","${ctx}/admin/pri/group/add.action");
			}else{
				$('#addGroupPlanForm').attr("action","${ctx}/admin/pri/group/modify.action");
			}
			
			$('#addGroupPlanForm').submit();
		}
		
		function getCheckBoxValue() {
	         var retString = "";
	         var checks = document.getElementsByName("isOpen");
	         if (checks) {
	             for (var i = 0; i < checks.length; i++) {
	                 var chkObj = checks[i];
	                 if (chkObj.checked){
	                     retString += chkObj.value+",";
	                 }
	             }
	             retString = retString.substr(0, retString.length - 1);
	         }
	         return retString;
	    }
		
		function returnList(meetingId){
			window.location.href = "${ctx}/admin/pri/group/list.action?meetingId="+meetingId;
		}
        
        $("#groupPlanDesc").calcWordNum({maxNumber:50,targetid:"remain"});

</script>
</body>
</html>