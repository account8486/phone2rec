<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>客房管理</title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
</head>
<body>
	<div class="page_title">
		<h3>添加客房</h3>
	</div>
	<div class="page_form">
	<c:choose>
	<c:when test="${not empty hotelRoomTypeList}">
	
	<form id="mainForm" action="${ctx}/admin/pri/room/add.action" method="post" enctype="multipart/form-data"}>
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>客房类型：</label>
	            </dt>
	            <dd>
	            	<select class="inp_1" id="hotelRoomTypeId" name="hotelRoom.hotelRoomType.id">
						<c:forEach var="record" items="${hotelRoomTypeList}" varStatus="status">
							<option value="${record.id }">${record.hotel.name }_${record.name }</option>
						</c:forEach>
					</select>
	            	<font id="tip_hotelRoomTypeId" style="line-height:35px" color="red"></font>
	                <small>请选择客房类型。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>房间号：</label>
	            </dt>
	            <dd>
	            	<input style="width:60px;" type="text" id="roomNo" name="hotelRoom.roomNo" value="${hotelRoom.roomNo}" maxlength="20"/>
	            	<font id="tip_roomNo" style="line-height:35px" color="red"></font>
	                <small>房间号最多20个字符。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title">楼层：</label>
	            </dt>
	            <dd>
	            	<input style="width:60px;" type="text" id="floor" name="hotelRoom.floor" value="${hotelRoom.floor}" maxlength="3"/>
	            	<font id="tip_floor" style="line-height:35px" color="red"></font>
	                <small>请输入整数。</small>
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
		&nbsp;&nbsp;&nbsp;&nbsp;您还没有添加客房类型，请先到
		<a href="${ctx}/admin/pri/hrt/list.action?meetingId=${meetingId}">客房类型管理</a>
		下添加客房类型后，再添加客房。
	</c:otherwise>
	</c:choose>
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
		
		var hotelRoomTypeId = $("#hotelRoomTypeId").val();
	    if (isEmpty(hotelRoomTypeId)) {
	        $("#tip_hotelRoomTypeId").html("请选择客房类型。");
	        $("#tip_hotelRoomTypeId").show();
	        result = false;
	    }
	    
	    var roomNo = $("#roomNo").val();
	    if (isEmpty(roomNo)) {
	        $("#tip_roomNo").html("请输入房间号。");
	        $("#tip_roomNo").show();
	        result = false;
	    }
	    
	    var floor = $("#floor").val();
	    if (!isEmpty(floor) && !(/^\d{1,3}$/.test(floor))) {
		        $("#tip_floor").html("楼层请输入整数。");
		        $("#tip_floor").show();
		        result = false;
	    }
	    
	    return result;
	}
	
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/room/list.action?meetingId=${meetingId}";
	}
</script>
</body>
</html>