<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统基础菜单编辑</title> 
${admin_css} 
${jquery_js} 
${jquery_form_js}
${validate_js} 
${WdatePicker_js}


<script>
	function sbUpdateFrm() {
		$("#updateFrm").submit();
	}
</script>

</head>
<body>
	<div class="page_title">
		<h3>云类型：${baseMenu.meetingType.name} -- 基础菜单定制</h3>
	</div>
	<div class="page_form">
		<form id="updateFrm"
			action="${ctx}/admin/pri/custom/meetingType_editMenu.action"
			method="post" enctype="multipart/form-data" >
			<input type="hidden" name="meetingTypeId" id="id" value="${baseMenu.meetingType.id}" />
			<input type="hidden" name="baseMenuId" id="id" value="${baseMenu.id}" />

			<fieldset>
				<legend>基本属性</legend>

				<dl>
					<dt>
						<label for="title">访问类型：</label>
					</dt>
					<dd>
						<label ><wd:translate type="terminal_type" value="${baseMenu.terminalType}"></wd:translate></label>
					</dd>
				</dl>

				<dl>
					<dt>
						<label for="title">菜单名称：</label>
					</dt>
					<dd>
						<input class="half" maxlength="4" align="left" type="text"
							name="baseMenu.name" id="name" value="${baseMenu.name}" /> <small>此处可以修改菜单的显示名称</small>
					</dd>
				</dl>

				<dl>
					<dt>
						<label for="title">菜单描述：</label>
					</dt>
					<dd>
						<input class="half" maxlength="4" align="left" type="text"
							name="baseMenu.description" id="description"
							value="${baseMenu.description}" />

					</dd>
				</dl>

				<!-- 只针对手机客户端有效  -->
				<c:if test="${baseMenu.terminalType eq 'CLIENT'}">
				<dl>
					<dt>
						<label for="title">菜单图标：</label>
					</dt>
					<dd>
						<c:if test="${not empty baseMenu.imgUrl}">
		            		<img id="content_icon" src="${baseMenu.imgUrl }" width="48px" height="48px"/>
		            	</c:if>
						<input type="file" name="icon" value="${baseMenu.imgUrl}" /> <font
							id="tip_mobile" style="line-height: 35px" color="red"></font> 
							<small>图标尺寸为：96 x 96，	大小不超过30KB。图片格式只能为gif, jpeg, png或jpg!。</small>
							<small style="color:red;">${retMsg }</small>
					</dd>
				</dl>
				</c:if>
				
				<dl>
					<dt>
						<label for="title">菜单类型：</label>
					</dt>
					<dd>
					<label><wd:translate type="menu_type" value="${baseMenu.type}"></wd:translate></label>
					</dd>
				</dl>
				
				<!-- 
				<dl>
					<dt>
						<label for="title">是否有效：</label>
					</dt>
					<dd>
						<input class="half" align="left" type="text" disabled="disabled"
							value="${baseMenu.state}" />
					</dd>
				</dl>
				-->
			</fieldset>
			
			<c:if test="${baseModuleTitleList !=null && fn:length(baseModuleTitleList)>0}">
		    <fieldset>
		        <legend>子页面标题</legend>
		        <dl>
		        	<dt>
		            	&nbsp;
		            </dt>
		            <dd>
		            	<c:forEach var="subtitle" items="${baseModuleTitleList}">
		            	<input type="text" class="half"  name="baseModuleTitle" value="${subtitle.titleName}" maxlength="64" tabindex="6"></input>
		            	<input type="hidden" class="half"  name="baseModuleTitleId" value="${subtitle.id}" maxlength="64" tabindex="6"></input>
		            	<small>变量值：${subtitle.keyName}</small>
		            	</c:forEach>
		            </dd>
		        </dl>
		    </fieldset>
			</c:if>
			
			<div class="page_form_sub">
				<a href="#" onclick="sbUpdateFrm();" id="addUserBtn"
					class="btn_common btn_true">保 存</a> <a
					href="${ctx}/admin/pri/custom/meetingType_listMenu.action?meetingTypeId=${baseMenu.meetingType.id}"
					id="retUserListBtn" class="btn_common btn_false">返回</a>
			</div>
		</form>
	</div>

</body>
</html>