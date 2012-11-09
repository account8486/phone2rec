<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统配置</title>
	${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>  
  <script type="text/javascript">
	$(document).ready(function() {
	    $('.easyui-tabs').tabs({  
			onSelect:function(title){  
				var tab = $(this).tabs('getTab', title); 
				var href = tab.attr('link');
				if (href) {
					location.href = href;
					showLoading(title);
					return false;
				}
			}  
		});  
	});
  
   function doSbFrm(){
	   showLoading("正在更新中......");
	   $("#updateFrm").submit();
   }
  </script>
</head>
<body>
	<div class="mainbox">
		<div class="page_title"><h3>系统配置 -- 密码找回</h3></div>
	</div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="密码找回" style="padding:10px;">
			<form id="updateFrm" action="${ctx}/admin/pri/dict/updateDataDictConfig.action"  method="POST">
				<input type="radio" name="dataValue" value="D" <c:if test="${dataDictConfig.dataValue eq 'D' }">checked="checked"</c:if>>&nbsp;&nbsp;重新生成密码发送
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="dataValue" value="S" <c:if test="${dataDictConfig.dataValue eq 'S' }">checked="checked"</c:if>>&nbsp;&nbsp;维持原有密码发送
				<input type="hidden" name="dataDictConfigId" id="dataDictConfigId" value="${dataDictConfig.id}" maxlength="20"/>
				<div class="page_form_sub">
			 		<a href="#" onclick="doSbFrm();" id="updateBtn" class="btn_common btn_true">保存 </a> <font color="red"><s:fielderror/></font> 
				</div>
			</form>
		</div>
		<%--
		<div title="客户端登录背景" link="${ctx}/admin/pri/config/systemConfig.action" style="padding:10px;"></div>
		 --%>
	</div>   
</body>
</html>