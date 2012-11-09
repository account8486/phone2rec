<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会务通平台</title>
	${admin_css}                                   
	${jquery_js}                                
	${jquery_form_js}                                 
	${admin_js}                   
	${util_js} 
	<style type="text/css">
	.result_content {
		border:1px dashed #000000; 
		padding:4px 10px 10px 10px; 
		width:420px; 
		background:#FFFFBF; 
		overflow:auto;
	}
	
	.result_content .close_button {
		margin-bottom:5px; 
	}
	
	.result_content .close_button a {
		color:#ff0000;
		float: right;
	}
	
	.result_content .close_button a:hover {
		background:#C0C0C0;
	}
	
	.result_content .result {
		margin-bottom:2px; 
		border-bottom: 1px dotted #C5C5C5;
	}
	
	.result_content .result a {
		float: right;
	}
	
	.result_content .result a:hover {
		background:#C0C0C0;
	}
	
	.specialty_info {
		width:90%;
		border:1px dashed #000000;
		margin-top:10px;
	}
	
	.specialty_info img {
		width:180px;
		height:120px;
		float:right;
		margin:10px;
	}
	
	.specialty_info #specialty_memo {
		padding:10px;
		
	}
	
	.clear {
		float: none;
		clear: both;
	}
	</style>
</head>
<body>
	<div class="page_title">
		<h3>嘉宾详细信息</h3>
	</div>
	<div class="page_form">
	
	    
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>姓名：</label>
	            </dt>
	            <dd>
	            	<input class="half" disabled="disabled"  type="text" id="name" name="name"  value="${guest.name}" maxlength="20"/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        
	          <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>头像：</label>
	            </dt>
	            <dd>
	            	<img id="img_headUrl" name="img_headUrl" style="width:200px;height:200px;" 
	            	<c:if test="${not empty guest.headUrl }"> src="${guest.headUrl}" </c:if>  src="${ctx}/images/head/head_default.png"  ></img>
	            </dd>
	        </dl>
	        
	       
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>头衔：</label>
	            </dt>
	            <dd>
	            	<input class="half" disabled="disabled" type="text" id="rank" name="rank"  value="${guest.rank}" maxlength="50"/>
	            	<font id="tip_rank" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">简介：</label>
	            </dt>
	            <dd>
	            	<textarea  class="medium" disabled="disabled" id="guest.topic" name="about" rows="3">${guest.about }</textarea>
	            	<font id="tip_about" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    
	    <div class="page_form_sub">
	        <a href="javascript:history.back();" id="retBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</div>
<script type="text/javascript">
	$(function(){
		$("input,textarea").css("color","skyblue");
	})
</script>
</body>
</html>