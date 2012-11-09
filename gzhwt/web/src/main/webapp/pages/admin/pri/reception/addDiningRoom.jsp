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
	${jmpopups_js}
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
		<h3>添加餐厅</h3>
	</div>
	<div class="page_form">
	<c:choose>
	<c:when test="${not empty hotelList}">
	
	<form id="mainForm" action="${ctx}/admin/pri/dr/add.action" method="post" enctype="multipart/form-data"}>
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="diningRoomId" name="diningRoom.id" value=""/>
	    <input type="hidden" id="actionType" name="actionType" value=""/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>酒店：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="hotelId" name="diningRoom.hotel.id">
						<c:forEach var="record" items="${hotelList}" varStatus="status">
							<option value="${record.id }">${record.name }</option>
						</c:forEach>
					</select>
	            	<font id="tip_hotelId" style="line-height:35px" color="red"></font>
	                <small>请选择酒店。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>餐厅名称：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="name" name="diningRoom.name" value="${diningRoom.name}" maxlength="64"/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	                <small>餐厅名称最多64个字符。</small>
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
	            	<label for="title">营业时间：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="businessHours" name="diningRoom.businessHours"  value="${diningRoom.businessHours}" maxlength="64"/>
	            	<font id="tip_businessHours" style="line-height:35px" color="red"></font>
	                <small>营业时间最多64个字符。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">餐桌个数：</label>
	            </dt>
	            <dd>
	            	<input style="width:50px;" type="text" id="tableCnt" name="diningRoom.tableCnt"  value="${diningRoom.tableCnt}" maxlength="5"/>
	            	<font id="tip_tableCnt" style="line-height:35px" color="red"></font>
	                <small>请输入5位以内整型餐桌个数。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">最大就餐人数：</label>
	            </dt>
	            <dd>
	            	<input style="width:60px;" type="text" id="capability" name="diningRoom.capability"  value="${diningRoom.capability}" maxlength="6"/>
	            	<font id="tip_capability" style="line-height:35px" color="red"></font>
	                <small>请输入6位以内整型最大就餐人数。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">楼层：</label>
	            </dt>
	            <dd>
	            	<input style="width:100px;" type="text" id="floor" name="diningRoom.floor"  value="${diningRoom.floor}" maxlength="10"/>
	            	<font id="tip_floor" style="line-height:35px" color="red"></font>
	                <small>楼层最多10个字符。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">附加信息：</label>
	            </dt>
	            <dd>
	            	<textarea id="additionalInfo" class="medium" name="diningRoom.additionalInfo" rows="3">${diningRoom.additionalInfo }</textarea>
	            	<small>附加信息最多1024个字符。<span id="additionalInfo_remain">1024</span>个字符。</small>
	            	<font id="tip_additionalInfo" style="line-height:35px" color="red"></font>
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
	</c:when>
	<c:otherwise><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;您还没有添加酒店，请先到
		<a href="${ctx}/admin/pri/hotel/list.action?meetingId=${meetingId}">酒店管理</a>
		下添加酒店后，再添加餐厅。
	</c:otherwise>
	</c:choose>
	</div>
<script type="text/javascript">
	$(function() {// 查询餐厅是否已经存在
		$("#name").focus();
		
		$("#name").blur(function(event) {
			var name = $("#name").val();
			if($.trim(name) == "") {
				return false;
			}
			
			// 根据名称模糊查询已经存在的餐厅列表
			var url = "${ctx}/admin/pri/dr/listByName.action";
			var data = {
				"diningRoom.hotel.id": $("#hotelId").val(),	
				"diningRoom.name": name
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
		
		var url = "${ctx}/admin/pri/dr/findById.action?diningRoom.id=" + id;
		$.get(url, function(data) {
			$("#diningRoomId").val(data.id);
			$("#actionType").val("choose");
			
			// 以只读方式展示餐厅信息
			$("#hotelId").attr("disabled", true);
			$("#name").val(data.name).attr("disabled", true);
			$("#businessHours").val(data.businessHours).attr("disabled", true);
			$("#tableCnt").val(data.tableCnt).attr("disabled", true);
			$("#capability").val(data.capability).attr("disabled", true);
			$("#floor").val(data.floor).attr("disabled", true);
			$("#additionalInfo").val(data.additionalInfo).attr("disabled", true);
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
		
		var hotelId = $("#hotelId").val();
		
	    if (isEmpty(hotelId)) {
	        $("#tip_hotelId").html("请选择酒店。");
	        $("#tip_hotelId").show();
	        result = false;
	    }
	    
	    var name = $("#name").val();
	
	    if (isEmpty(name)) {
	        $("#tip_name").html("请输入餐厅名称。");
	        $("#tip_name").show();
	        result = false;
	    }
	
	    var tableCnt = $("#tableCnt").val();
		
	    if (!isEmpty(tableCnt)&& ! (/^\d{1,5}$/.test(tableCnt))) {
	        $("#tip_tableCnt").html("请输入5位以内整型餐桌个数。");
	        $("#tip_tableCnt").show();
	        result = false;
	    }
	    
	    var capability = $("#capability").val();
		
	    if (!isEmpty(capability)&& !(/^\d{1,6}$/.test(capability))) {
	        $("#tip_capability").html("请输入6位以内整型最大就餐人数。");
	        $("#tip_capability").show();
	        result = false;
	    }
	    
	    return result;
	}
	
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/dr/list.action?meetingId=${meetingId}";
	}
	
	$("#additionalInfo").calcWordNum({maxNumber:1024,targetid:"additionalInfo_remain"});
</script>

</body>
</html>