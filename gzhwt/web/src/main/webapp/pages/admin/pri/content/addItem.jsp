<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
</head>
<body>
<script type="text/javascript">
	if('${retMsg}'.length > 0){
		alert('${retMsg}');
	}
	
	if('${retCode}' == "0"){
		self.close();
		window.opener.addIdIntoItems("${content.id}","${content.contentTitle}");
		window.opener.refreshItems();
	}
</script>
<c:set var="meetingId" value="${empty param.meetingId ? content.meetingId : param.meetingId}"></c:set>
<c:set var="contentType" value="4"></c:set>
<c:set var="contentName" value="条目"></c:set>
<c:set var="readonly" value="${empty content.id ? false : true}"></c:set>

	<div class="page_title">
		<h3>${contentName}信息编辑填写</h3>
	</div>
	<div class="page_form">
	<form id="addMeetingContentForm" action="${ctx}/admin/pri/meeting/addContentItem.action" method="post" ${contentType == 3 ? 'enctype="multipart/form-data"':''}>
		<input   type="hidden" id="content.id" name="content.id"  value="${content.id}" />
		<input   type="hidden" id="content.meetingId" name="content.meetingId"  value="${meetingId}" />
		<input   type="hidden" id="content.contentType" name="content.contentType"  value="${contentType}" />
		<input   type="hidden" id="content.parentId" name="content.parentId"  value="${parentId}" />
		<s:token />
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>${contentName}名称：</label>
	            </dt>
	            <dd>
	            	<input  type="text" id="content_title" name="content.contentTitle" class="half medium" value="${content.contentTitle}" maxlength="20"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small>${contentName}名称最多20个字。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>排序码：</label>
	            </dt>
	            <dd>
	            	<input  type="text" id="content_sortNo" name="content.sortNo" class="half medium" onkeyup="value=value.replace(/[^\d]/g,'')" value="${content.sortNo}" maxlength="3"/>
	            	<font id="tip_mobile" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>${contentName}介绍：</label>
	            </dt>
	            <dd>
	            	<textarea  style="width:98%" id="editor"  name="content.content" >${content.content}</textarea>
		            <small>内容最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>　<a href="javascript:self.close();" class="btn_common btn_false">取消</a>
	    </div>
	    <br />
	    <br />
	    <br />
	</form>
	</div>
<script type="text/javascript">
		function save(){
			//var formData = $('#addMeetingForm').formJsonData(); 
			//alert(formData);
			var name = $('#content_title').val();
			name = $.trim(name);
			if(name.length == 0 ){
				alert("请输入${contentName}名称.");
				return false;
			}
			if(name.length > 20){
				alert("${contentName}名称不能超过20个字.");
				return false;
			}
			
			var sortNo = $('#content_sortNo').val();
			sortNo = $.trim(sortNo);
			if(sortNo.length == 0 ){
				alert("请输入排序码.");
				return false;
			}
			
			var editor = CKEDITOR.instances['editor'].getData();
			if(editor.length == 0){
				alert("请填写内容");
				return false;
			}
			if(editor.length > 1000000){
				alert("内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。");
				return false;
			}			

			submit();
			document.getElementById("submitBtn").disabled = "disabled";
		}

		function submit(){
			var url = '';
			if('${content.id}'==''){
				url= '${ctx}/admin/pri/meeting/addContentItem.action';
			}else{
				url= '${ctx}/admin/pri/meeting/modifyContentItem.action';				
			}
			
			$('#addMeetingContentForm').attr('action',url).submit();
		}
		
</script>
<ckeditor:replace replace="editor" basePath="${ctx}/ckeditor/" config="<%=CkEditorConfigHelper.createConfig()%>"/>
</body>
</html>