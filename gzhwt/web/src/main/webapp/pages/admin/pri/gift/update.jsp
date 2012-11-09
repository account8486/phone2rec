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
		<h3>编辑礼品</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/gift/update.action" method="post" enctype="multipart/form-data"}>
		<input type="hidden" name="gift.id" value="${gift.id}"/>
		<input type="hidden" name="meetingId" value="${meetingId}"/>
	    <fieldset>
	        <legend>基本信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>礼品名称：</label>
	            </dt>
	            <dd>
	            	<input class="half" type="text" id="giftName" name="gift.name"  value="${gift.name}" maxlength="64" tabindex="1"/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	                <small>礼品名称最多64个字符。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>价格：</label>
	            </dt>
	            <dd>
	            	<input type="text" style="width: 50px" id="giftPrice" name="gift.price"  value="<fmt:formatNumber value='${gift.price}' pattern='#0.00'/>" maxlength="9" tabindex="2"/>元
	            	<font id="tip_price" style="line-height:35px" color="red"></font>
	                <small>请输入整数位6位以内，小数位2位以内的价格。</small>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>礼品介绍：</label>
	            </dt>
	            <dd>
	            	<textarea id="giftIntroduction" class="medium" name="gift.introduction" rows="5" tabindex="2">${gift.introduction }</textarea>
	            	<small>礼品简介最多512个字符。<span id="giftIntroduction_remain">512</span>个字符。</small>
	            	<font id="tip_introduction" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>礼品图片：</label>
	            </dt>
	            <dd>
	            	<div><img style="margin-left:2px;width:180px;height:140px;"src="${serverUrl}${gift.imgUrl }"></img></div>
	            	<input  type="file" id="img" name="img" tabindex="3"/>
	            	<font id="tip_img" style="line-height:35px" color="red"></font>
	                <small></small>
	            </dd>
	        </dl>
	        
	        <!-- 
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>详细描述：</label>
	            </dt>
	            <dd>
	            	<textarea  style="width:98%" id="editor"  name="gift.description" >${gift.description}</textarea>
		            <font id="tip_description" style="line-height:35px" color="red"></font>
		            <small>内容最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。</small>
	            </dd>
	        </dl>
	         -->
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>
	        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回列表</a>
	    </div>
	</form>
	</div>
<script type="text/javascript">
	//新增校验
	function validateAdd() {
		var result = true;
	    var giftName = $("#giftName").val();
	
	    if (isEmpty(giftName)) {
	        $("#tip_name").html("请输入礼品名称。");
	        $("#tip_name").show();
	        result = false;
	    }
	    
	    var giftPrice = $("#giftPrice").val();
		
	    if (isEmpty(giftPrice)) {
	        $("#tip_price").html("请输入礼品价格。");
	        $("#tip_price").show();
	        result = false;
	    }else{
	    
		    var bool = /^((\d{1,6}\.\d{0,2})|(\d{1,6}))$/.test(giftPrice);
		    if(!bool){
		        $("#tip_price").html("请输入整数位6位以内，小数位2位以内的礼品价格。");
		        $("#tip_price").show();
		        result = false;
		    }
	    }
	    
	    var giftIntroduction = $("#giftIntroduction").val();
	
	    if (isEmpty(giftIntroduction)) {
	        $("#tip_introduction").html("请输入礼品简介。");
	        $("#tip_introduction").show();
	        result = false;
	    }
	    
	    /**
	    var editor = CKEDITOR.instances['editor'].getData();
		if(editor.length == 0){
			$("#tip_description").html("请输入礼品描述。");
        	$("#tip_description").show();
			return false;
		}
		if(editor.length > 1000000){
			$("#tip_description").html("内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具,否则有可能保存失败。");
        	$("#tip_description").show();
			return false;
		}	
		*/
		
	    return result;
	}
	
	function save(){
		$("[id^='tip_']").hide();
	   	var tmp_bool = validateAdd();
	    if (tmp_bool != true) {
	        return false;
	    }
	    
		$("#mainForm").submit();
		$("#submitBtn").attr("disabled","disabled");
	}
		
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/gift/list.action?meetingId=${meetingId}";
	}
	
	$("#giftIntroduction").calcWordNum({maxNumber:512,targetid:"giftIntroduction_remain"});
</script>
<!-- <ckeditor:replace replace="editor" basePath="${ctx}/ckeditor/" config="<%=CkEditorConfigHelper.createConfig()%>"/> -->
</body>
</html>