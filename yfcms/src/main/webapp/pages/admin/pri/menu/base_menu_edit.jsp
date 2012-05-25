<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统基础菜单编辑</title> ${admin_css} ${jquery_js} ${jquery_form_js} ${validate_js} ${WdatePicker_js}
<script>
	function sbUpdateFrm() {
		$("#updateFrm").submit();
	}
</script>
</head>
<body>
	<div class="page_title">
		<h3>菜单管理：${clientMenu.description} -- ${CURRENT_MEETING}</h3>
	</div>
	<div class="page_form">
		<form id="updateFrm"
			action="${ctx}/admin/pri/basemenu/updateBaseMenuById.action"
			method="post" enctype="multipart/form-data" >
			<input type="hidden" name="id" id="id" value="${clientMenu.id}" />

			<fieldset>
				<legend>基本属性</legend>
				<dl>
					<dt>
						<label for="title">访问类型：</label>
					</dt>
					<dd>
						<label ><wd:translate type="terminal_type" value="${clientMenu.terminalType}"></wd:translate></label>
					</dd>
				</dl>

				<dl>
					<dt>
						<label for="title">菜单名称：</label>
					</dt>
					<dd>
						<input class="half" maxlength="6" align="left" type="text"
							name="name" id="name" value="${clientMenu.name}" /> <small>此处可以修改菜单的显示名称</small>
					</dd>
				</dl>

				<dl>
					<dt>
						<label for="title">菜单描述：</label>
					</dt>
					<dd>
						<input class="half" maxlength="30" align="left" type="text"
							name="description" id="description"
							value="${clientMenu.description}" />

					</dd>
				</dl>

				<!-- 只针对手机客户端有效  -->
				<c:if test="${clientMenu.terminalType eq 'CLIENT'}">
				<dl>
					<dt>
						<label for="title">菜单图标：</label>
					</dt>
					<dd>
						<c:if test="${not empty clientMenu.imgUrl}">
		            		<img id="content_icon" src="${clientMenu.imgUrl }" width="48px" height="48px"/>
		            	</c:if>
						<input type="file" name="icon" value="${clientMenu.imgUrl}" /> <font
							id="tip_mobile" style="line-height: 35px" color="red">${errorMsg}</font> <small>图标尺寸为：96 x 96，
							大小不超过30KB。图片格式只能为gif, jpeg, png或jpg!。</small>
					</dd>
				</dl>
				</c:if>
				
				<dl>
					<dt>
						<label for="title">菜单类型：</label>
					</dt>
					<dd>
					<label><wd:translate type="menu_type" value="${clientMenu.type}"></wd:translate></label>
					</dd>
				</dl>
				
				<dl>
					<dt>
						<label for="title">链接URL：</label>
					</dt>
					<dd>
						<label>${clientMenu.contentUrl}</label>
					</dd>
				</dl>
				<!-- 
				<dl>
					<dt>
						<label for="title">是否有效：</label>
					</dt>
					<dd>
						<input class="half" align="left" type="text" disabled="disabled"
							value="${clientMenu.state}" />
					</dd>
				</dl>
				-->
			</fieldset>

			<c:if test="${meetingModuleTitleList !=null && fn:length(meetingModuleTitleList)>0}">
		    <fieldset>
		        <legend>子页面标题</legend>
		        <dl>
		        	<dt>
		            	&nbsp;
		            </dt>
		            <dd>
		            	<c:forEach var="subtitle" items="${meetingModuleTitleList}">
		            	<input type="text" class="half"  name="meetingModuleTitle" value="${subtitle.titleName}" maxlength="64" tabindex="6"></input>
		            	<input type="hidden" class="half"  name="meetingModuleTitleId" value="${subtitle.id}" maxlength="64" tabindex="6"></input>
		            	<small>变量值：${subtitle.keyName}</small>
		            	</c:forEach>
		            </dd>
		        </dl>
		    </fieldset>
			</c:if>

			<div class="page_form_sub">
				<a href="#" onclick="sbUpdateFrm();" id="addUserBtn"
					class="btn_common btn_true">保 存</a> <a
					href="${ctx}/admin/pri/basemenu/getBaseMenuPages.action?meetingId=${clientMenu.meetingId}"
					id="retUserListBtn" class="btn_common btn_false">返回列表</a>
			</div>
		</form>
	</div>
</body>
</html>