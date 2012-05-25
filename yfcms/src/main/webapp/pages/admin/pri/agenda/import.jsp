<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会议议程</title>
${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
<div class="page_title"><h3>会议议程 -- ${CURRENT_MEETING}</h3></div>

<div class="easyui-tabs" border="false" style="padding:10px;">
	<div title="议程管理" link="${ctx}/admin/pri/agenda/agendaList.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="议程编辑" link="${ctx}/admin/pri/agenda/agendaAdd.action?meetingId=${meetingId}" style="padding:10px;"></div>
	<div title="议程导入" selected="true" style="padding:10px;">
		<div class="page_form">	
		<form id="upFrm" action="${ctx}/admin/pri/agenda/agendaImport.action" enctype="multipart/form-data" method="POST" >
			<fieldset>
				<legend>从电子表格导入议程</legend>
				<dl>
					<dt style="padding-top: 12px;">请选择电子表格</dt>
					<dd><input name="upload" id="upload" type="file" style="color:#666;"/>
						<span id="tip_file" style="display:none; color:red;">*请选择电子表格。</span>
					</dd>
				</dl>
				<dl>
					<dt>电子表格样例</dt>
					<dd>
						<div class=crumbs2><a href="${ctx}/xlstemplate/议程导入模版.xls">点击下载</a></div>
					</dd>
				</dl>
				<dl>
					<dt>&nbsp;</dt>
					<dd><a href="#" id="agendaImport" class="btn_common btn_true">导入</a></dd>
				</dl>
			</fieldset>
			<div class="page_form_sub"> 
				<input type="hidden"" name="meetingId" id="meetingId" value="${meetingId}"/>
				<input type="hidden"" name="action" id="action" value="doImport"/>
			</div>
			<div id="importMsg" style="color:red;margin-left:35px;">${importMsg}</div>
		</form>
		</div>
	</div>
</div>
</body>
</html>
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
	$('#upload').blur(function() {
		$(this).removeClass("btn_false");
		$(this).addClass("btn_true");
	});
	
	$("#agendaImport").click(function(){
		if (isEmpty($("#upload").val())) {
			$("#tip_file").show();
			return false;
		} 
		if ($(this).is('.btn_true')) {
			$(this).removeClass("btn_true");
			$(this).addClass("btn_false");
			$("form").submit();
			return false;
		}
	});
});
</script>