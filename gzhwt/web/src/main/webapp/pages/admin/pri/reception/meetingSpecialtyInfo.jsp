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
	${area_js}   
</head>
<body>
	<div class="page_title">
		<h3>发布土特产</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/reception/specialty_save.action" method="post" >
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="meetingSpecialtyId" name="meetingSpecialtyId" value="${meetingSpecialtyId}"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>会议所属区域：</label>
	            </dt>
	            <dd>
	            	<select id="province" style="width:80px;" class="valid"></select>
					<select id="city" style="width:80px;" ></select>
	            	<input type="hidden" id="meetingSpecialty_province" name="meetingSpecialty.province" value="${meetingSpecialty.province}"/>
	            	<input type="hidden" id="meetingSpecialty_city" name="meetingSpecialty.city" value="${meetingSpecialty.city}"/>
	            	<small>土特产信息必须从属于某一个区域</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>信息描述：</label>
	            </dt>
	            <dd>
	            	<textarea id="memo" name="meetingSpecialty.memo" style="width:80%; height:100px; padding:10px;">${meetingSpecialty.memo }</textarea>
	            	<br/>
	            	<font id="tip_memo" style="line-height:35px" color="red"></font>
	                <small>请输入本次会议所发布的土特产信息描述内容，不能超过1000个字符。</small>
	            </dd>
	        </dl>
	        
	        <s:if test="meetingSpecialty.id != null">
	        <dl>
	        	<dt>
	            	<label for="title">有效状态：</label>
	            </dt>
	            <dd>
	            	<label><input type="radio"  name="meetingSpecialty.state"  value="1" ${(meetingSpecialty.id == null || meetingSpecialty.state == 1) ? "checked" : "" }/>有效</label>
	            	<label><input type="radio"  name="meetingSpecialty.state"  value="0" ${meetingSpecialty.state == 0 ? "checked" : "" } style="margin-left:15px;"/>无效</label>
	            	<font id="tip_state" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        </s:if>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保存</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	$(function() {
		$("#memo").focus();
		
		// 初始化省份、城市
		var areas = "";// 存放省份、城市
		function initMeetingArea(proviceCode){
			$.post(
	                "${ctx}/admin/pri/base/getAreas.action",
	                {},
	                function (data, textStatus) {
	                    if (textStatus == "success") {
	                    	areas = data;
	                    	if (data.province == null){
	                			return;
	                		}
	                		
	                		var sel = $("#province");
	                		sel.empty();
	                		$.each(data.province, function(i, item){
	                			var opt = new Option(item.name, item.code);
	                			sel.get(0).options.add(opt);
	                		});
	                		
	                		$("#province").val(proviceCode);
	                		$("#province").change();
	                    }
	                },
	                "json"
	    	);
		}
		
		$("#province").change(function(){
			var provinceCode = $("#province").val();
			var sel = $("#city");
			
			/*
			var str = "";
			$.each(sel.get(0).options, function(i, item){
				str += "insert into meeting_area values('" +item.value + "','" + item.text +"','"+item.value.substring(0,2)+ "');\r\n";
			});
			alert(str);*/
			
			sel.empty();
			$.each(areas[provinceCode], function(i, item){
				var opt = new Option(item.name, item.code);
				sel.get(0).options.add(opt);
			});
			$("#city").val("${meeting.city}");
		});
		
		//根据已有数据获取省市编码
		initMeetingArea("${meeting.province}");
		
		$("#province").attr("disabled", "true");
		$("#city").attr("disabled", "true");
	});

	//新增校验
	function validate() {
		var result = true;
		var meetingId = $("#meetingId").val();
	    if ($.trim(meetingId) == "") {
	        alert("缺少会议ID参数。");
	        result = false;
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
	    
	    var m_province = "${meeting.province}";
	    var m_city = "${meeting.city}";
	    var province = $("#meetingSpecialty_province").val();
	    var city = $("#meetingSpecialty_city").val();
	    if(m_province != province || m_city != city) {
	    	alert("由于当前会议的区域地址已经变更,因此当前已经发布的所有土特产信息项都已经不再有效")
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