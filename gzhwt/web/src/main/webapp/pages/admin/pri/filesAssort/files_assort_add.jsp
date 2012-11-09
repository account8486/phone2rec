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
	<div class="mainbox"><div class="page_title"><h3>资料管理  -- ${CURRENT_MEETING}</h3></div></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="资料管理" link="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="资料上传" link="${ctx}/admin/pri/meeting/preAddMeetingFile.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="分类管理" link="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="添加分类" selected="true" style="padding:10px;">
			<div class="page_form">
			<form id="addFrm" action="${ctx}/admin/pri/files/addAssort.action"  enctype="multipart/form-data" method="POST" >
			     <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}" />
				<fieldset>
					<dl>
					<dt class="title"><label for="title">文件夹名称</label></dt>
					<dd><input class="half" name="assortName" id="assortName" type="text" maxlength="20"/>
					</dd>
					</dl>
					
				<dl>
					<dt class="title"><label for="title">属于图片文件夹</label></dt>
				   <dd>
					<select name="isImgFold" id="isImgFold" style="width:300px;">
					<option value="1">是</option>
					<option value="0">否</option>
					</select>
				   </dd>
				</dl>
				
				<dl>
					<dt class="title"><label for="title">上报单位</label></dt>
					<dd><input class="half" name="applyDepartment" id="applyDepartment" type="text" maxlength="20"/>
					</dd>
					</dl>
					
					<dl>
					<dt class="title"><label for="title">图片封面</label></dt>
					<dd><input class="half" name="pageImg" id="pageImg" type="file" maxlength="20"/>
					</dd>
					</dl>
					
					<dl>
					<dt><label for="title">描述</label></dt>
					<dd>
						<textarea name="description" id="description" rows="1000" cols="5000"></textarea>
					</dd>
					</dl>
					
				</fieldset>
				<div class="page_form_sub">
					<a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">添加</a> 
				</div>
			</form>
			</div>
		</div>
		<div title="显示方式" link="${ctx}/admin/pri/meeting/goViewTypeConfig.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
</body>
</html>