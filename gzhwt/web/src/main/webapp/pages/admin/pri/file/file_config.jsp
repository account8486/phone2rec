<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资料管理  -- ${CURRENT_MEETING}</title>
	${admin_css} ${jquery_js} ${jquery_form_js} ${validate_js} ${WdatePicker_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".easyui-tabs").tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs("getTab", title); 
					var href = tab.attr("link");
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});
			
		});
		function doSbFrm(){
			var viewDisplayType = $(":radio[checked]").val();
			$("#upFrm").submit();
		}
	</script>
</head>
<body>
	<div class="mainbox"><div class="page_title"><h3>资料管理  -- ${CURRENT_MEETING}</h3></div></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="资料管理" link="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="资料上传" link="${ctx}/admin/pri/meeting/preAddMeetingFile.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="分类管理" link="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="添加分类" link="${ctx}/admin/pri/files/preAddAssort.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="配置显示方式" style="padding:10px;" selected="true" >
			<div class="page_form">
			<form id="upFrm" action="${ctx}/admin/pri/meeting/saveViewTypeConfig.action" method="POST" >
				<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}" />
				<input type="hidden" id="meetingId" name="pageCustomId" value="${pageCustomId}" />
				
				<div><span style="color:red;">${errMsg }</span></div>
				<fieldset>
				<dl>
					<dt><label for="title">资料显示方式</label></dt>
					<dd>
						<input type="radio" value="bookshelf" name="viewDisplayType" ${viewDisplayType=='bookshelf' ? 'checked' : ''}>书架
						<input type="radio" value="list" name="viewDisplayType" ${viewDisplayType=='list' ? 'checked' : ''}>列表
						<small>在此配置Web门户中会议资料文件的视图显示方式</small>
					</dd>
				</dl>
				</fieldset>

				<div class="page_form_sub">
				 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">保存 </a> <font color="red">${retMsg}</font> 
				</div>
			</form>
			</div>		
		</div>
					
	</div>
</body>
</html>