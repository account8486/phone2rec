<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/theme.css" />
${admin_css}                                   
${jquery_js}                                
</head>
<body>
		
<div class="mainbox">
	<div class="page_title" style="display:block"><h3>会议主题及界面定制  -- ${CURRENT_MEETING}</h3></div>
	
	</div>
	<div class="page_form">
	<form id="addForm" action="${ctx}/admin/pri/custom/meeting_saveViewConfig.action" enctype="multipart/form-data" method="post">
	<input   type="hidden" id="meetingId" name="meetingId"  value="${meetingId}" />
	
	<fieldset>
        <legend></legend>
        
        <dl>
	        	<dt>
	            	<label for="title"><font color="red">*</font>主题皮肤：</label>
	            </dt>
	            <dd>
					<div class="container">
						<s:iterator value="pageThemeList" var="theme">
						<div class="theme">
							<div class="pic"><img src="${ctx}/css/${theme.thumbnailName}" width="160" height="100"/></div>
							<div class="description">
								<div class="title">
									<label>
										<input type="radio" name="pageThemeId" value="${theme.id }" ${(meeting.pageTheme == null && theme.id==1) || meeting.pageTheme.id==theme.id ? "checked" : "" } />&nbsp;&nbsp;${theme.title}
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
	            	<label for="title"><font color="red"></font>登录背景图片：</label>
	            </dt>
	            <dd>
	            	<input  type="file" id="loginImage" tabindex="4" name="loginImage" />
	            	<br/>
	            	<font id="tip_loginImage" style="line-height:35px" color="red"></font>
	    			<small>请上传用于Web门户登录页面的背景图片，建议尺寸为1000x580像素</small>
	    			<s:if test="loginImage != null">
	    			<div id="loginImageDiv" >
	    				<img src="${serverUrl}${loginImage }" width="240" height="150" border="0"/>
	    				<small>已经设定的登录页面背景图片，点此<a href="javascript:deleteImage(${meetingId}, 'loginImage')">删除</a></small>
	    			</div>
	    			</s:if>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>门户Logo图片：</label>
	            </dt>
	            <dd>
	            	<input  type="file" id="logoImage" tabindex="4" name="logoImage" />
	            	<br/>
	            	<font id="tip_logoImage" style="line-height:35px" color="red"></font>
	    			<small>请上传用于页面头部的logo图片，必须是背景透明的gif或png格式图片，建议尺寸为120x54像素</small>
	    			<s:if test="logoImage != null">
	    			<div id="logoImageDiv" class="logo">
	    				<img src="${serverUrl}${logoImage }" width="120" height="54" border="0"/>
	    				<small>已经设定的登录页面背景图片，点此<a href="javascript:deleteImage(${meetingId}, 'logoImage')">删除</a></small>
	    			</div>
	    			</s:if>
	            </dd>
	        </dl>
	        
	        <dl>
	        	<dt>
	            	<label for="title"><font color="red"></font>门户顶部背景：</label>
	            </dt>
	            <dd>
	            	<input  type="file" id="topImage" tabindex="4" name="topImage" />
	            	<br/>
	            	<font id="tip_topImage" style="line-height:35px" color="red"></font>
	    			<small>请上传用于Web门户页面的顶部背景图片，建议尺寸为960x100像素</small>
	    			<s:if test="topImage != null">
	    			<div id="topImageDiv">
	    				<img src="${serverUrl}${topImage }" width="800" height="80" border="0"/>
	    				<small>已经设定的门户页面顶部背景图片，点此<a href="javascript:deleteImage(${meetingId}, 'topImage')">删除</a></small>
	    			</div>
	    			</s:if>
	            </dd>
	        </dl>
	   
    </fieldset>
    <div class="page_form_sub">
        <a href="##" onclick="save();" id="submitBtn" class="btn_common btn_true">保 存</a>    
    </div>

	</form>
	
	</div>
</div>
	
<script type="text/javascript">

		$(function() {
			var msg = "${errMsg }";
			if(msg != "") {
				alert(msg);
			}
		});

		function save(){
			$("#addForm").submit();
		}

		function deleteImage(meetingId, imageType) {
			if(confirm("确定删除此图片吗？")) {
				var url = "${ctx}/admin/pri/custom/meeting_deleteImage.action?meetingId=" + meetingId + "&imageType=" + imageType;
				$.post(url, function(data) {
					if(data.result) {
						alert("图片删除成功。");
						$("#" + imageType + "Div").hide();
					} else {
						alert("图片删除失败。");
					}
				});
			}
		}
</script>
</body>
</html>