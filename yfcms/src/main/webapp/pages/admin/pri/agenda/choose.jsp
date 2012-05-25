<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>议程分组管理</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${validate_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
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
<div class="page_title"><h3>会议议程 -- ${CURRENT_MEETING}</h3></div>
<div class="easyui-tabs" border="false" style="padding:10px;">
	<div title="议程管理" style="padding:10px;">
		<div class="page_form">
		<form id="chooseGroupPlanForm" action="${ctx}/admin/pri/group/doChooseGroupPlan.action" method="post" >
			<input type="hidden" id="meetingAgenda.id" name="meetingAgenda.id" value="${meetingAgenda.id}" />
			<s:token />
			<fieldset>
				<legend>议程分组</legend>
				<c:if test="${not empty oldGroupPlan}">
					<dl>
						<dt>
							<label for="title"><font color="red">* </font>当前分组模版：</label>
						</dt>
						<dd>
							${oldGroupPlan.planName }
						</dd>
					</dl>
				</c:if>
				<dl>
					<dt>
						<label for="title"><font color="red">* </font>选择模版：</label>
					</dt>
					<dd>
						<select id="choosePlan" class="inp_1" tabindex="1" name="groupPlanId" value="${groupPlanId }">
								<option value="-1" >请选择</option>
							<c:if test="${not empty groupPlanList}">
								<c:forEach var="plan" items="${groupPlanList}">
									<option value="${plan.id }" ${plan.id == oldGroupPlan.id ? 'selected' : '' }>${plan.planName }</option>
								</c:forEach>
							</c:if>
						</select>
						<font id="tip_name" style="line-height:35px" color="red"></font>
					</dd>
				</dl>
			</fieldset>
			
			<div class="page_form_sub">
				<div style="margin-bottom: 10px; margin-top: 10px;" align="left">
					 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返 回</a>
				</div>
			</div>
		</form>
		</div>

	</div>
	<div title="议程编辑" link="${ctx}/admin/pri/agenda/agendaAdd.action?meetingId=${meetingAgenda.meetingId}" style="padding:10px;"></div>
	<div title="议程导入" link="${ctx}/admin/pri/agenda/agendaImport.action?meetingId=${meetingAgenda.meetingId}" style="padding:10px;"></div>
</div>
	<script type="text/javascript">
	
$(document).ready(function() {
	$(".easyui-tabs").tabs({  
		onSelect:function(title){  
			var tab = $(this).tabs("getTab", title); 
			var href = tab.attr("link");
			if (href) {
				location.href = href;
				showLoading(title);
				return false;
			}
		}  
	}); 
});	
		function validate()
		{    
		     var choosePlan = $('#choosePlan').val();
		     if(choosePlan== '-1'){
		     	alert('请选择分组模版');
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
			//alert('${meeting.id}');
			var url = '${ctx}/admin/pri/agenda/doChooseGroupPlan.action';
			
			var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'         
			    };
			$('#chooseGroupPlanForm').ajaxSubmit(options);
		}
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){				
				returnList();
			}
			document.getElementById("submitBtn").disabled = "enabled";
		}
		
		function returnList(){
			window.location.href = "${ctx}/admin/pri/agenda/agendaList.action?meetingId=${meetingAgenda.meetingId}";
		}

</script>
</body>
</html>