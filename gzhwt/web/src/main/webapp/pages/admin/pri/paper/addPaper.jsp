<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
${admin_css}                                   
${jquery_js}                                
${jquery_form_js}                                 
${validate_js}                                
${WdatePicker_js}                           
${admin_js}                   
${area_js}        
${util_js}      
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
    	<s:if test="flag=='edit'">
		<h3>修改调查</h3>
		</s:if>
		<s:else>
		<h3>添加调查</h3>
		</s:else>
	</div>
	<div class="page_form">
	<form id="addPaperForm" action="${ctx}/admin/pri/paper/paper_addPaper.action" >
	<input   type="hidden"  name="meetingId" id="meet"  value="${param.meetingId }" />
	<input   type="hidden"  name="id"  value="${paper.id }" />
	<fieldset>
        <c:if test="${not empty paper.id}">
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>调查状态：</label>
            </dt>
            <dd>
            	<select name="state" ${errMsg!="true"?"disabled":"" }>
            		<option value="1" ${paper.state==1?"selected":"" }>启用</option>
            		<option value="2" ${paper.state==2?"selected":"" }>未启用</option>
            	</select>
            	<c:if test="${paper.state==2 }">
            	<small>至少有1个调查选项才可以启用调查</small>
            	</c:if>
            </dd>
        </dl>
        </c:if>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>调查标题：</label>
            </dt>
            <dd>
            	<textarea style="width:80%" class="medium" id="paper.topic" name="title" maxlength="100">${paper.title }</textarea>
            	<small>请限制在100字以内,您还可以输入<span id="meeting_topic_remain">100</span>字</small>
            </dd>
        </dl>
        
    </fieldset>

    <div class="page_form_sub">
        <a href="javascript:save();"  id="submitBtn" class="btn_common btn_true">保 存</a>   
         <a href="${ctx}/pages/admin/pri/paper/addQuestion.jsp?paperId=${paper.id}&meetingId=${meetingId}" style="display: none;" id="addItem"  class="btn_common btn_true">添加题目</a> 
        <a href="javascript:history.back();"   class="btn_common btn_true">返回</a>    
    </div>

	</form>
	
	</div>
</div>

<script type="text/javascript">

$(function(){
	$("#paper\\.topic").calcWordNum({maxNumber:100,targetid:"meeting_topic_remain"});
	var flag="${flag}"
	if(flag=="edit"){
		$("#addPaperForm").attr("action","${ctx}/admin/pri/paper/paper_updatePaper.action");
		$("#submitBtn").html("修改");
		$("#addItem").show();
	}
});
	
function save(){
	$("#addPaperForm").submit();
}


</script>
</body>
</html>