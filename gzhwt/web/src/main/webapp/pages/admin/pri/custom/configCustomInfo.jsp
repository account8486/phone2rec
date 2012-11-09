<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>页面属性定制</title>
	${admin_css}                                   
	${jquery_js}
	${jquery_form_js}
	${validate_js}
	${util_js}
	
<script type="text/javascript">

function save(){
	if($("organId").val() == "") {
		alert("没有选择当前的组织机构。");
		return false;
	}
	
    var logoImageName = $('#logoImage').val();
    
    //检查文件类型
    var logoExt = logoImageName.split(".")[1];
    if(logoExt && logoExt.toLowerCase() != "gif" && logoExt.toLowerCase() != "png"){
    	alert("logo图片必须是gif或png格式");
     	return;
    }
    
	$('#pageCustomForm').submit();
	$("#submitBtn").attr("disabled", true);
}

function deleteLogo(organId) {
	if(confirm("确定删除此logo图片吗？")) {
		var url = "${ctx}/admin/pri/custom/deletePageCustomLogo.action?organId=" + organId;
		$.post(url, function(data) {
			if(data.result) {
				alert("logo图片删除成功。");
				$("#logoDiv").hide();
			} else {
				alert("logo图片删除失败。");
			}
		});
	}
}

</script>
</head>
<body>

	<div class="page_title">
		<h3>页面信息定制</h3>
	</div>
	<div class="page_form">
	<form id="pageCustomForm" action="${ctx}/admin/pri/custom/savePageCustomInfo.action" method="post" enctype="multipart/form-data">
		<input type="hidden" id="organId" name="organId"  value="${organId}" />
	    <fieldset>
	        <legend>页面Logo信息</legend>

	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">* </font>logo图片：</label>
	            </dt>
	            <dd>
	            	<input  type="file" id="logoImage" tabindex="4" name="logoImage" />
	            	<font id="tip_logoImage" style="line-height:35px" color="red"></font>
	    			<small>请上传用于页面头部的logo图片，必须是背景透明的gif或png格式图片，最佳尺寸为108x53像素</small>
	    			<s:if test="pageCustomMap['logo_url'] != null">
	    			<div id="logoDiv" class="logo">
	    				<img src="${ctx}/admin/pri/custom/showPageCustomLogo.action?organId=${organId}" width="108" height="53" border="0"/>
	    				<small>已经设定的logo图片，点此<a href="javascript:deleteLogo(${organId})">删除</a></small>
	    			</div>
	    			</s:if>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${message }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	    	<div style="margin-bottom: 10px; margin-top: 10px;" align="left">
	       		 <a href="#" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>
	       		 <a href="${ctx }/pages/admin/pri/org/listAndTree.jsp" id="submitBtn" class="btn_common btn_true">返 回</a>
	        </div>
	    </div>
	</form>
	</div>
	
</body>
</html>