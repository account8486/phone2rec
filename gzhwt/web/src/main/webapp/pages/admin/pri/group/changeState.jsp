<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<div class="popup">
	<div class="popup-header">
		<h2>分组信息公开设置</h2>
	</div>
	<div class="popup-body page_form">
			<fieldset>
				<!-- <legend>模版名称:${param.planName }</legend> -->
				<dl>
		        	<dt>
						<label for="title">用户级别：</label>
					</dt>
		            <dd style="width: 68%">
						<c:set var="isOpen" value="${empty param.isOpen ? isOpen : param.isOpen}"></c:set>
						<input type="checkbox" name="isOpen" value="1" ${fn:contains(isOpen,"1") ? "checked":""} >一级&nbsp;&nbsp;
						<input type="checkbox" name="isOpen" value="2" ${fn:contains(isOpen,"2") ? "checked":""} >二级&nbsp;&nbsp;
						<input type="checkbox" name="isOpen" value="3" ${fn:contains(isOpen,"3") ? "checked":""} >三级&nbsp;&nbsp;
						<input type="checkbox" name="isOpen" value="4" ${fn:contains(isOpen,"4") ? "checked":""} >四级&nbsp;&nbsp;
						<input type="checkbox" name="isOpen" value="5" ${fn:contains(isOpen,"5") ? "checked":""} >五级
						<small>勾选相应级别用户来设置对其公开该分组信息</small>
					</dd>
				</dl>
			</fieldset>
			<div class="page_form_sub">
		    	<div style="margin-bottom: 10px; margin-top: 10px; padding-left: 50px;" align="left">
		       		 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:;" onclick="$.closePopupLayer()" class="btn_common btn_false">取消</a>
		        </div>
		    </div>
	</div>
</div>
<script type="text/javascript">
	function save(){
		var url = "${ctx}/admin/pri/group/updateGroupPlanState.action";
		$.ajax({
			url: url,
			data: {"groupPlan.id":"${param.planId}","groupPlan.isOpen":getCheckBoxValue()},
	        type: 'post',      
	        dataType: 'json',
	        success: callback
		});
	}
	
	function callback(data){
		alert(data.retmsg);
		if(data.retcode == "0"){
			$.closePopupLayer();
			window.location.href = "${ctx}/admin/pri/group/list.action?meetingId=${param.meetingId}";
		}
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
</script>