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
	<div class="page_title">
		<h3>编辑客房房型</h3>
	</div>
	<div class="page_form">
	
	<form id="mainForm" action="${ctx}/admin/pri/hrt/update.action" method="post" enctype="multipart/form-data"}>
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="hotelRoomTypeId" name="hotelRoomType.id" value="${hotelRoomType.id }"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>酒店名称：</label>
	            </dt>
	            <dd>
	            	<font style="line-height:35px">${hotelRoomType.hotel.name }</font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>客房房型名称：</label>
	            </dt>
	            <dd>
	            	<font style="line-height:35px">${hotelRoomType.name }</font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">面积：</label>
	            </dt>
	            <dd>
	            	<input style="width:60px;" type="text" id="area" name="hotelRoomType.area"  value="${hotelRoomType.area}" maxlength="6"/>平方米
	            	<font id="tip_area" style="line-height:35px" color="red"></font>
	                <small>面积单位为平方米，可为3位整数（如100），也可以为小数（3位整数+最多2位小数，如22.50）。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">可住人数：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="capability" name="hotelRoomType.capability">
						<option value="1" <c:if test="${1 eq hotelRoomType.capability }"> selected </c:if>>1</option>
						<option value="2" <c:if test="${2 eq hotelRoomType.capability }"> selected </c:if>>2</option>
					</select>
	            	<font id="tip_capability" style="line-height:35px" color="red"></font>
	                <small>请选择可住人数。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">床宽：</label>
	            </dt>
	            <dd>
	            	<input style="width:60px;" type="text" id="bedWidth" name="hotelRoomType.bedWidth"  value="${hotelRoomType.bedWidth}" maxlength="4"/>米
	            	<font id="tip_bedWidth" style="line-height:35px" color="red"></font>
	                <small>床宽单位为米，可为1位整数（如2），也可以为小数（1位整数+最多2位小数，如1.80）。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">附加信息：</label>
	            </dt>
	            <dd>
	            	<textarea id="additionalInfo" class="medium" name="hotelRoomType.additionalInfo" rows="3">${hotelRoomType.additionalInfo }</textarea>
	            	<small>附加信息最多1024个字符。<span id="additionalInfo_remain">1024</span>个字符。</small>
	            	<font id="tip_additionalInfo" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">客房设施：</label>
	            </dt>
	            <dd>
	            	<textarea id="facilities" class="medium" name="hotelRoomType.facilities" rows="3">${hotelRoomType.facilities }</textarea>
	            	<small>客房设施最多512个字符。<span id="facilities_remain">512</span>个字符。</small>
	            	<font id="tip_facilities" style="line-height:35px" color="red"></font>
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
	
    	var area = $("#area").val();
	    if (!isEmpty(area)&& !(/^\d{1,3}(\.\d{1,2})?$/.test(area))) {
	        $("#tip_area").html("请输入格式正确的面积。");
	        $("#tip_area").show();
	        result = false;
	    }
	    
	    var capability = $("#capability").val();
	    if (!isEmpty(capability)&& !(/^\d{1,2}$/.test(capability))) {
	        $("#tip_capability").html("请输入2位以内整型可住人数。");
	        $("#tip_capability").show();
	        result = false;
	    }
	    
	 	var bedWidth = $("#bedWidth").val();
	    if (!isEmpty(bedWidth)&& !(/^\d{1}(\.\d{1,2})?$/.test(bedWidth))) {
	    	$("#tip_bedWidth").html("请输入格式正确的床宽。");
	        $("#tip_bedWidth").show();
	        result = false;
	    }
	    
	    return result;
	}
	
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/hrt/list.action?meetingId=${meetingId}";
	}
	
	$("#additionalInfo").calcWordNum({maxNumber:1024,targetid:"additionalInfo_remain"});
	$("#facilities").calcWordNum({maxNumber:512,targetid:"facilities_remain"});
</script>

</body>
</html>