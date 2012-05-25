<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安徽电信会议云平台</title>
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
		<h3>修改投票项</h3>
		</s:if>
		<s:else>
		<h3>添加投票项</h3>
		</s:else>
	</div>
	<div class="page_form">
	<form id="addVoteForm" action="${ctx}/admin/pri/vote/voteItem_addVoteItem.action" >
	<input   type="hidden"  name="voteId"  value="${param.voteId}" />
	<input   type="hidden"  name="id"  value="<s:property  value='#item.id'/>" />
	<fieldset>
        <dl>
        	<dt>
            	<label for="title"><font color="red">* </font>投票项内容：</label>
            </dt>
            <dd>
            	<textarea style="width:80%" class="medium" id="vote.topic" name="content" maxlength="100"><s:property  value="#item.content"/></textarea>
            	<small>请限制在100字以内,您还可以输入<span id="meeting_topic_remain">100</span>字</small>
            </dd>
        </dl>
        
    </fieldset>

    <div class="page_form_sub">
        <a href="javascript:save();"  id="submitBtn" class="btn_common btn_true">保 存</a>    
        <a href="javascript:history.back();"   class="btn_common btn_true">返回</a>    
    </div>

	</form>
	
	</div>
</div>
	
<script type="text/javascript">

$(function(){
	$("#vote\\.topic").calcWordNum({maxNumber:100,targetid:"meeting_topic_remain"});
	var flag="${flag}";
	if(flag=="edit"){
		$("#addVoteForm").attr("action","${ctx}/admin/pri/vote/voteItem_updateVoteItem.action");
		$("#submitBtn").html("修改");
	}
});

function save(){
	var title=$("#vote\\.topic").val();
	if(title==null||title==""){
		alert("请输入投票项内容");
		return ;
	}
	$("#addVoteForm").submit();
}


</script>
</body>
</html>