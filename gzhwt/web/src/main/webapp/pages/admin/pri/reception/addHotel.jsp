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
		<h3>添加酒店</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/hotel/add.action" method="post" enctype="multipart/form-data"}>
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="province" name="hotel.province" value="${meeting.province}"/>
	    <input type="hidden" id="city" name="hotel.city" value="${meeting.city}"/>
	    <input type="hidden" id="hotelId" name="hotel.id" value=""/>
	    <input type="hidden" id="actionType" name="actionType" value=""/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>酒店名称：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="name" name="hotel.name"  value="${hotel.name}" maxlength="64"/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	                <small>酒店名称最多64个字符。</small>
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
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>地址：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="address" name="hotel.address"  value="${hotel.address}" maxlength="256"/>
	            	<font id="tip_address" style="line-height:35px" color="red"></font>
	                <small>地址最多256个字符。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">联系方式：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="linking" name="hotel.linking"  value="${hotel.linking}" maxlength="64"/>
	            	<font id="tip_linking" style="line-height:35px" color="red"></font>
	                <small>联系方式最多64个字符。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">基本信息：</label>
	            </dt>
	            <dd>
	            	<textarea id="baseInfo" class="medium" name="hotel.baseInfo" rows="3">${hotel.baseInfo }</textarea>
	            	<small>基本信息最多1024个字符。<span id="baseInfo_remain">1024</span>个字符。</small>
	            	<font id="tip_baseInfo" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">酒店简介：</label>
	            </dt>
	            <dd>
	            	<textarea id="introduction" class="medium" name="hotel.introduction" rows="3">${hotel.introduction }</textarea>
	            	<small>酒店简介最多1024个字符。<span id="introduction_remain">1024</span>个字符。</small>
	            	<font id="tip_introduction" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">周边信息：</label>
	            </dt>
	            <dd>
	            	<textarea id="peripheralInfo" class="medium" name="hotel.peripheralInfo" rows="3">${hotel.peripheralInfo }</textarea>
	            	<small>周边信息最多1024个字符。<span id="peripheralInfo_remain">1024</span>个字符。</small>
	            	<font id="tip_peripheralInfo" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">附加信息：</label>
	            </dt>
	            <dd>
	            	<textarea id="additionalInfo" class="medium" name="hotel.additionalInfo" rows="3">${hotel.additionalInfo }</textarea>
	            	<small>附加信息最多1024个字符。<span id="additionalInfo_remain">1024</span>个字符。</small>
	            	<font id="tip_additionalInfo" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">服务项目：</label>
	            </dt>
	            <dd>
	            	<textarea id="service" class="medium" name="hotel.service" rows="3">${hotel.service }</textarea>
	            	<small>服务项目最多512个字符。<span id="service_remain">512</span>个字符。</small>
	            	<font id="tip_service" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">客房设施：</label>
	            </dt>
	            <dd>
	            	<textarea id="facilities" class="medium" name="hotel.facilities" rows="3">${hotel.facilities }</textarea>
	            	<small>客房设施最多512个字符。<span id="facilities_remain">512</span>个字符。</small>
	            	<font id="tip_facilities" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>酒店图片：</label><br/>
	            	<font id="addImgBtn" style="cursor:pointer" color="blue">增加图片&nbsp;&nbsp;&nbsp; </font>
	            </dt>
	            <dd>
	            	<table id="images">
		            	<tr><td colspan="4"><font style="line-height:35px" color="orange">建议至少上传一张图片。</font>
		            		<font id="tip_images" style="line-height:35px" color="red"></font></td></tr>
		            	<tr>
		            		<td>标题：<input type="text" name="imgTitle" style="width: 200px"/></td>
		            		<td>排序：<input type="text" name="imgSort" style="width: 30px"/></td>
		            		<td><input type="file" name="img"/></td>
		            		<td><a href="#" onclick="$(this).parent().parent().remove()">删除</a></td>
		            	</tr>
	            	</table>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>
	        <a href="javascript:returnList();" id="retBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function() {
		$("#name").focus();
		
		$("#name").blur(function(event) {
			var name = $("#name").val();
			if($.trim(name) == "") {
				return false;
			}
			
			// 根据名称模糊查询已经存在的酒店列表
			var url = "${ctx}/admin/pri/hotel/listByName.action";
			var data = {
				"hotel.name": name,
				"hotel.province": $("#province").val(),
				"hotel.city": $("#city").val()
			};
			$.post(url, data, ajaxSearchCallback);
		});
	});
	
	//ajax查询回调
	function ajaxSearchCallback(data) {
		var searchResult = $("#searchResult");
		var dlSearchResult = $("#dlSearchResult");
		
		searchResult.empty();
		if(data.length == 0) {
			dlSearchResult.hide();
			return;
		}
		
		for(var i = 0; i < data.length; i++) {
			var li = "<li class='result'>" + data[i][1] 
				+ "<a href='javascript:chooseResult(" + data[i][0]
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
		
		var url = "${ctx}/admin/pri/hotel/findById.action?hotel.id=" + id;
		$.get(url, function(data) {
			$("#hotelId").val(data.id);
			$("#actionType").val("chooseHotel");
			
			// 以只读方式展示酒店信息
			$("#name").val(data.name).attr("disabled", true);
			$("#address").val(data.address).attr("disabled", true);
			$("#linking").val(data.linking).attr("disabled", true);
			$("#baseInfo").val(data.baseInfo).attr("disabled", true);
			$("#introduction").val(data.introduction).attr("disabled", true);
			$("#peripheralInfo").val(data.peripheralInfo).attr("disabled", true);
			$("#additionalInfo").val(data.additionalInfo).attr("disabled", true);
			$("#service").val(data.service).attr("disabled", true);
			$("#facilities").val(data.facilities).attr("disabled", true);
			
			$("#addImgBtn").hide();
			
			var imagesContent = "";
			for(var i = 0; i < data.images.length; i++) {
				imagesContent += '<tr><td><div style="margin-left:2px;width:500px;height:190px;">        		<img style="margin-left:2px;width:180px;height:140px;"src="${serverUrl}' + data.images[i].address + '"></img><br/>        		标题：' + data.images[i].title + '<br/>        		排序：'+ data.images[i].sort +'    		</div></td></tr>';
			}
			$("#images").html(imagesContent);
		})
	}
	
	// 保存
	function save(){
		$("[id^='tip_']").hide();
	   	var tmp_bool = validateAdd();
	    if (tmp_bool != true) {
	        return false;
	    }
	    
		$("#mainForm").submit();
		$("#submitBtn").attr("disabled","disabled");
	}
	
	//新增校验
	function validateAdd() {
		var result = true;
	    var name = $("#name").val();
	
	    if (isEmpty(name)) {
	        $("#tip_name").html("请输入酒店名称。");
	        $("#tip_name").show();
	        result = false;
	    }
	    
	    var address = $("#address").val();
		
	    if (isEmpty(address)) {
	        $("#tip_address").html("请输入酒店地址。");
	        $("#tip_address").show();
	        result = false;
	    }
	    
	    // 酒店图片标题
	    $("input[name='imgTitle']").each(function(){
	    	if(isEmpty($(this).val())) {
	    		$("#tip_images").html("请输入图片标题。");
	        	$("#tip_images").show();
	        	result = false;
	        	return result;// 这里的return false 作用仅仅是退出循环，程序还将执行下面的其他check。
	    	}
		});
	    	
    	// 酒店图片排序
	    $("input[name='imgSort']").each(function(){
	    	var order = $(this).val();
	    	if(isEmpty(order)) {
	    		$("#tip_images").html("请输入图片排序。");
	        	$("#tip_images").show();
	        	result = false;
	        	return result;
	    	} else  {
	    		var bool = /^(\d{1,3})$/.test(order);
	 		    if(!bool){
	 		        $("#tip_images").html("请输入3位以内整数型排序码。");
	 		        $("#tip_images").show();
	 		       	result = false;
		        	return result;
	 		    }
	    	}
		});
	    	
    	// 酒店图片
	    $("input[name='img']").each(function(){
	    	var img = $(this).val();
	    	if (isEmpty(img)) {
	     		$("#tip_images").html("请选择酒店图片。");
	         	$("#tip_images").show();
	         	result = false;
	         	return false;
	 	    }else if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(img.toLowerCase())) {
	 			$("#tip_images").html("图片类型必须是.gif,jpeg,jpg,png中的一种，请重新选择。");
	         	$("#tip_images").show();
	         	result = false;
	         	return result;
	 	    }
		});
	
	    return result;
	}
	
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/hotel/list.action?meetingId=${meetingId}";
	}
	
	$("#baseInfo").calcWordNum({maxNumber:1024,targetid:"baseInfo_remain"});
	$("#introduction").calcWordNum({maxNumber:1024,targetid:"introduction_remain"});
	$("#peripheralInfo").calcWordNum({maxNumber:1024,targetid:"peripheralInfo_remain"});
	$("#additionalInfo").calcWordNum({maxNumber:1024,targetid:"additionalInfo_remain"});
	$("#service").calcWordNum({maxNumber:512,targetid:"service_remain"});
	$("#facilities").calcWordNum({maxNumber:512,targetid:"facilities_remain"});
	
	$(document).ready(function(){
		$("#addImgBtn").click(function(){
			// 增加图片输入项
			$("#images").append('<tr> <td>标题：<input type="text" name="imgTitle" style="width: 200px"/></td>        		<td>排序：<input type="text" name="imgSort" style="width: 30px" name="gift.price"/></td>        		<td><input  type="file" name="img"/></td>        		<td><a href="#" onclick="$(this).parent().parent().remove()">删除</a></td>        	</tr>');
			$(window).scrollTop(1000);
		});
	});
</script>

</body>
</html>