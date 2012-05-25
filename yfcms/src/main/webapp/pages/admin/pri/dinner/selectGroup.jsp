<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用餐分组管理</title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${validate_js}
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
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
		<h3>用餐分组管理  -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
	<form id="chooseGroupPlanForm" action="${ctx}/admin/pri/meeting/doChooseDinnerGroupPlan.action" method="post" >
		<input   type="hidden" id="dinner.id" name="dinner.id"  value="${dinner.id}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
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
	    
	    <div class="neidi">&nbsp;</div>
	    
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
			var url = '${ctx}/admin/pri/meeting/doChooseDinnerGroupPlan.action';
			
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
			window.location.href = "${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId=${dinner.meetingId}";
		}

</script>
</body>
</html>