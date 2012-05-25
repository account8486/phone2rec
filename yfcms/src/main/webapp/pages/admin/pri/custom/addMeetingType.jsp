<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理</title>
	${admin_css}                                   
	${jquery_js}
	<link type="text/css" rel="stylesheet" href="${ctx}/css/theme.css" />
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
	<script type="text/javascript">
		// 新增校验
		function validate() {
	        var name = $("#name").val();
	        if (isEmpty(name)) {
	            $("#tip_name").html("请输入会议类型名称。");
	            $("#tip_name").show();
	            return false;
	        }
	
	        var themeId = $(":radio[checked]").val();
	        if($.trim(themeId) == "") {
	        	$("#tip_themeId").html("请选择一种主题皮肤。");
	            $("#tip_themeId").show();
	            return false;
	        }
	        
	        var logoImageName = $('#logoImage').val();
	        //检查文件类型
	        var logoExt = logoImageName.split(".")[1];
	        if(logoExt && logoExt.toLowerCase() != "gif" && logoExt.toLowerCase() != "png"){
	        	$("#tip_logoImage").html("logo图片必须是gif或png格式");
	        	$("#tip_logoImage").show();
	         	return;
	        }
	        
			var description = $("#description").val();
		    if (!isEmpty(description) && description.length > 100) {
		    	$("#tip_description").html("描述信息不能超过100个字符。");
		        $("#tip_description").show();
		        return false;
		    }
	        
	        return true;
		}
		
		function save(){
			$("[id^='tip_']").hide();
	       	var tmp_bool = validate();
	        if (! validate()) {
	            return false;
	        }
	        
			$("#addMeetingTypeForm").submit();
			$("#saveBtn").attr("disabled","disabled");
		}
		
		function deleteLogo(meetingTypeId) {
			if(confirm("确定删除此logo图片吗？")) {
				var url = "${ctx}/admin/pri/custom/meetingType_deleteLogoImage.action?meetingTypeId=" + meetingTypeId;
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
	<s:if test="meetingType.id != null">
		<h3>编辑会议类型信息</h3>
	</s:if>
	<s:else>
		<h3>定义新会议类型</h3>
	</s:else>
	</div>
	
	<s:if test="meetingType.id != null">
	<div class="page_tools page_toolbar">
			<a type="button" class="btn_common btn_false"
				href="${ctx}/admin/pri/custom/meetingType_listMenu.action?meetingTypeId=${meetingType.id}">菜单定制</a>
	</div>
	</s:if>
		
	<div class="page_form">
	<form id="addMeetingTypeForm" action="${ctx}/admin/pri/custom/meetingType_save.action" method="post" enctype="multipart/form-data">
	    <input type="hidden" name="meetingTypeId"  value="${meetingType.id}" />
	    
	    <fieldset>
	        <legend>会议类型信息</legend>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>类型名称：</label>
	            </dt>
	            <dd>
	            	<input type="text" class="half" id="name" name="meetingType.name" value="${meetingType.name }" maxlength="64" tabindex="6"></input>
	            	<br/>
	            	<font id="tip_name" style="line-height:35px" color="red"></font>
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>主题皮肤：</label>
	            </dt>
	            <dd>
	            <%-- 
	            	<select class="inp_1" id="themeId" name="meetingType.pageTheme.id" tabindex="7">
	            	<c:forEach items="${pageThemeList }" var="theme">
	            		<option value="${theme.id }" ${meetingType.pageTheme.id == theme.id ? "selected" : "" }>${theme.title }</option>
					</c:forEach>
					</select>
					<br/>
					<font id="tip_themeId" style="line-height:35px" color="red"></font>
				--%>	
					<div class="container">
						<s:iterator value="pageThemeList" var="theme">
						<div class="theme">
							<div class="pic"><img src="${ctx}/css/${theme.thumbnailName}" /></div>
							<div class="description">
								<div class="title">
									<label>
										<input type="radio" name="meetingType.pageTheme.id" value="${theme.id }" ${(meetingType.pageTheme.id == null && theme.id==1) ||meetingType.pageTheme.id==theme.id ? "checked" : "" } />&nbsp;&nbsp;${theme.title}
									</label>
								</div>
								<div class="comments">${theme.description}</div>
							</div>
						</div>
						</s:iterator>	
					
					<div class="clear"></div>
					<font id="tip_themeId" style="line-height:35px" color="red"></font>	
	            </dd>
	        </dl>
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>logo图片：</label>
	            </dt>
	            <dd>
	            	<input  type="file" id="logoImage" tabindex="4" name="logoImage" />
	            	<br/>
	            	<font id="tip_logoImage" style="line-height:35px" color="red"></font>
	    			<small>请上传用于页面头部的logo图片，必须是背景透明的gif或png格式图片，最佳尺寸为108x53像素</small>
	    			<s:if test="meetingType.logoImage != null && meetingType.logoImage.length() > 0">
	    			<div id="logoDiv" class="logo">
	    				<img src="${ctx}/admin/pri/custom/meetingType_showLogoImage.action?meetingTypeId=${meetingType.id}" width="108" height="53" border="0"/>
	    				<small>已经设定的logo图片，点此<a href="javascript:deleteLogo(${meetingType.id})">删除</a></small>
	    			</div>
	    			</s:if>
	            </dd>
	        </dl>
	        	        
	        <dl>
	        	<dt>
	            	<label for="title">描述信息：</label>
	            </dt>
	            <dd>
	            	<textarea class="medium" id="description" name="meetingType.description" rows="5" tabindex="11">${meetingType.description }</textarea>
	            	<br/>
	            	<font id="tip_description" style="line-height:35px" color="red"></font>
	            	<small>备注不能超过100个字符。</small>
	            </dd>
	        </dl>
	    </fieldset>
	    
	    <div class="neidi">
			<font id="tip_err_msg" style="margin-left:110px;line-height:35px" color="red">
			${errMsg }</font>
			<br/>
		</div>
	    
	    <div class="page_form_sub">
	        <a href="#" onclick="save();" id="saveBtn" class="btn_common btn_true">保存</a>　
	        <a href="${ctx }/admin/pri/custom/meetingType_list.action" id="retUserListBtn" class="btn_common btn_false">返回</a>
	    </div>
	</form>
	</div>
</body>
</html>