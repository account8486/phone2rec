<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件夹  -- ${CURRENT_MEETING}</title>
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
   	   var assortName=$("#assortName").val();
   	   var description=$("#description").val();
	   
   	   if(assortName==''){
   		   alert("请输入分类名称!");
   		   return;
   	   }
	   $("#addFrm").submit();
   }
  </script>
</head>
<body>
	<div class="mainbox"><div class="page_title"><h3>新建文件夹</h3></div></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="资料管理" link="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="资料上传" link="${ctx}/admin/pri/meeting/preAddMeetingFile.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="分类管理" link="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="添加分类" selected="true" style="padding:10px;">
			<div class="page_form">
			<form id="addFrm" action="${ctx}/admin/pri/files/addAssort.action"  method="POST" >
				<fieldset>
					<dl>
					<dt class="title"><label for="title">文件夹名称</label></dt>
					<dd><input class="half" name="assortName" id="assortName" type="text" maxlength="20"/>
					</dd>
					</dl>

					<dl>
					<dt><label for="title">描述</label></dt>
					<dd>
						<input class="half" name="description" id="description" type="text" maxlength="40"/>
						<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}" />
					</dd>
					</dl>
				</fieldset>
				<div class="page_form_sub">
					<a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">添加</a> 
				</div>
			</form>
			</div>
		</div>
	</div>
</body>
</html>