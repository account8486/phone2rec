<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
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
		<h3>编辑餐厅</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/dr/update.action" method="post" enctype="multipart/form-data"}>
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="diningRoomId" name="diningRoom.id" value="${diningRoom.id}"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title">酒店名称：</label>
	            </dt>
	            <dd>
	            	<font style="line-height:35px">${diningRoom.hotel.name }</font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">餐厅名称：</label>
	            </dt>
	            <dd>
	            	<font style="line-height:35px">${diningRoom.name }</font>
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
	</div>
<script type="text/javascript">
	// 保存
	function save() {
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