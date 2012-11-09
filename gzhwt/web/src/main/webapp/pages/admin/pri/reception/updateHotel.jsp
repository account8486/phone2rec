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
		<h3>编辑酒店</h3>
	</div>
	<div class="page_form">
	<form id="mainForm" action="${ctx}/admin/pri/hotel/update.action" method="post" enctype="multipart/form-data"}>
	    <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
	    <input type="hidden" id="hotelId" name="hotel.id" value="${hotel.id}"/>
	    <fieldset>
	        <legend></legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>酒店名称：</label>
	            </dt>
	            <dd>
	            	<font style="line-height:35px">${hotel.name }</font>
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
	            	<a href="#" onclick="addImg();">增加图片&nbsp;&nbsp;&nbsp; </a>
	            </dt>
	            <dd>
	            	<table id="images">
		            	<tr><td><font id="tip_images" style="line-height:35px" color="red"></font></td></tr>
		            	<c:choose>
		            	<c:when test="${not empty hotel.images}">
                        <c:forEach var="record" items="${hotel.images}" varStatus="status">
                            <tr><td><div id="_imgdiv_${record.id }" style="margin-left:2px;width:500px;height:250px;">
                            	<input type="hidden" name="oldImgUrl" value="${record.address }"/>
			            		<img style="margin-left:2px;width:180px;height:140px;"src="${serverUrl}${record.address }"></img><br/>
			            		标题：${record.title }<br/>
			            		排序：${record.sort }
			            		<a href="#" onclick="return delImage(${hotel.id},${record.id })">删除</a>
		            		</div></td></tr>
                        </c:forEach>
                    	</c:when>
                    	</c:choose>
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

	//删除图片
	function delImage(hotelId, hotelImageId) {
		if(confirm("确定删除这张图片吗？")) {
			var url = "${ctx}/admin/pri/hotel/deleteImage.action?hotelImageId=" + hotelImageId + "&hotel.id=" + hotelId;
			$.post(url, function(data) {
				if("ok" == data) {
					$("#_imgdiv_" + hotelImageId).hide();
					alert("图片删除成功。");
				}
			})
		}
		return false;
	}
	
	// 增加图片输入项
	function addImg(){
		$("#images").append('<tr><td><div style="margin-left:2px;width:500px;height:100px;">        		<input style="width: 280px"type="file" name="img"/><br/>        		标题：<input type="text" name="imgTitle" value="${record.title }" style="width: 239px"/><br/>        		排序：<input type="text" name="imgSort" value="${record.sort }" style="width: 30px"/>        		<a href="#" onclick="$(this).parent().parent().remove()">删除</a>    		</div></td></tr>');
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
	    	if (!isEmpty(img) && !/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(img.toLowerCase())) {
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
</script>

</body>
</html>