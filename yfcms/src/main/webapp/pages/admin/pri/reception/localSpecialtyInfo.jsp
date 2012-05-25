<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="com.wondertek.meeting.common.CkEditorConfigHelper"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
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
	
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
</head>
<body>
	<div class="page_title">
		<h3>添加土特产信息</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/reception/specialty_addLocalSpecialty.action" method="post" enctype="multipart/form-data">
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="meetingSpecialtyId" name="meetingSpecialtyId" value="${meetingSpecialtyId}"/>
	    <input type="hidden" id="localSpecialtyId" name="localSpecialtyId" value="${localSpecialtyId}"/>
	    <input type="hidden" id="actionType" name="actionType" value="${actionType }" />
	    
	     <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
		
	    <fieldset>
	        <legend></legend>
	        
	        <dl>
	        	<dt>
	            	<label for="name"><font color="red"></font>土特产名称：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="name" name="localSpecialty.name"  value="${localSpecialty.name}" maxlength="20" tabindex="1"/>
	            	<br/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	                <small>请输入土特产名称，该名称在同一地区是唯一的。</small>
	            </dd>
	        </dl>
	        
	        <dl id="dlSearchResult" style="display:none;">
	        	<dt>
	            	<label for="name"><font color="red"></font>&nbsp;</label>
	            </dt>
	            <dd>
	            	<div class="result_content">
	            		<ul>
	            			<li class="close_button">&nbsp;<a href="javascript:closeResult()">关闭</a></li>
	            		</ul>
	            		<ul id="searchResult" >
	            		</ul>
	            	</div>
	            </dd>
	        </dl>
	        
	        <dl id="specialtyInfo" style="display:none;">
	        	<dt>
	            	<label for="name"><font color="red"></font>信息描述：</label>
	            </dt>
	            <dd>
	            	<div class="specialty_info">
	            		<img id="specialty_image" src=""></img>
	            		<div id="specialty_memo"></div>
						<div class="clear"></div>
	            	</div>
	            </dd>
	        </dl>
	        
	        <dl id="dlMemo">
	        	<dt>
	            	<label for="title"><font color="red"></font>信息描述：</label>
	            </dt>
	            <dd>
	            	<textarea id="memo" name="localSpecialty.memo" style="width:80%; height:100px; padding:10px;">${localSpecialty.memo }</textarea>
	            	<br/>
	            	<font id="tip_memo" style="line-height:35px" color="red"></font>
	                <small>请输入土特产的信息描述，不能超过1000个字符。</small>
	            </dd>
	        </dl>
	        
	        <dl id="dlImage">
	        	<dt>
	            	<label for="title"><font color="red"></font>图片信息：</label>
	            </dt>
	            <dd>
	            	<c:if test="${actionType eq 'editLocalSpecialty'}">
	            	<div><img style="margin-left:2px;width:180px;height:120px;"src="${serverUrl}${localSpecialty.image }"></img></div>
	            	</c:if>
	            	<input class="half" type="file" id="image" name="localSpecialtyImage"  />
	            	<br/>
	            	<font id="tip_image" style="line-height:35px" color="red"></font>
	                <small>请上传本特产对应的图片信息，建议大小为180X120的png或jpg图片。</small>
	            </dd>
	        </dl>
	        
	    </fieldset>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保存</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	var province = "${meeting.province}"; //会议所在省
	var city = "${meeting.city}"; //会议所在市
	
	$(function() {
		$("#name").focus();
		
		$("#name").keyup(function(event) {
			var name = $("#name").val();
			if($.trim(name) == "") {
				return false;
			}
			
			var url = "${ctx}/admin/pri/reception/specialty_searchLocalSpecialty.action";
			var data = {
				"localSpecialty.name": name,
				"localSpecialty.province": province,
				"localSpecialty.city": city
			};
			$.post(url, data, ajaxSearchCallback);
		});
	});
	
	//ajax查询回
	function ajaxSearchCallback(data) {
		var searchResult = $("#searchResult");
		var dlSearchResult = $("#dlSearchResult");
		searchResult.empty();
		
		if(data.length == 0) {
			dlSearchResult.hide();
			return;
		}
		
		for(var i = 0; i < data.length; i++) {
			var li = "<li class='result'>" + data[i].name 
				+ "<a href='javascript:chooseResult(" + data[i].id 
				+ ")'>选择</a></li>";
			searchResult.append(li);
		}
		dlSearchResult.show();
	}

	//关闭查询结果div
	function closeResult() {
		$("#dlSearchResult").hide();
	}
	
	//选择查询结果项
	function chooseResult(id) {
		$("#dlSearchResult").hide();
		
		var url = "${ctx}/admin/pri/reception/specialty_findSpecialty.action?localSpecialtyId=" + id;
		$.post(url, function(data) {
			$("#localSpecialtyId").val(data.id);
			$("#actionType").val("chooseLocalSpecialty");
			
			$("#name").val(data.name);
			$("#name").attr("disabled", true);
			$("#specialty_memo").html(data.memo);
			
			if($.trim(data.image) == "") {
				$("#specialty_image").hide();
			} else {
				$("#specialty_image").attr("src", "${serverUrl}" + data.image);
				$("#specialty_image").show();
			}
			
			
			$("#specialtyInfo").show();
			$("#dlMemo").hide();
			$("#dlImage").hide();
		})
	}

	//新增校验
	function validate() {
		var result = true;
		var meetingId = $("#meetingId").val();
	    if ($.trim(meetingId) == "") {
	        alert("缺少会议ID参数。");
	        result = false;
	    }
	    
	    //判断是否选择已经存在的特产信息
	    var localSpecialtyId = $("#localSpecialtyId").val();
	    if(! isEmpty(localSpecialtyId)) {
	    	return true;
	    }
	    
	    var memo = $("#memo").val();
	    if (isEmpty(memo) || memo.length > 1000) {
	        $("#tip_memo").html("请输入不超过1000个字符的土特产描述信息。");
	        $("#tip_memo").show();
	        result = false;
	    }
	    
	    return result;
	}
	
	function save(){
		$("[id^='tip_']").hide();
	   	var tmp_bool = validate();
	    if (tmp_bool != true) {
	        return false;
	    }
	    
		$("#mainForm").submit();
		$("#submitBtn").attr("disabled","disabled");
	}
		
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/reception/specialty_show.action?meetingId=${meetingId}";
	}
	
</script>

</body>
</html>