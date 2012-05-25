<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会议资料上传  -- ${CURRENT_MEETING}</title>
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
			var uploadTxt=$("#upload").val();
			var assortId=$("#assortId").val();
			if(uploadTxt==''){
			   alert("请选择要上传的文件!");
			   return;
			}
			showLoading("正在上传文件中......");
			$("#upFrm").submit();
		}
	</script>
</head>
<body>
	<div class="mainbox"><div class="page_title"><h3>上传会议资料</h3></div></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="资料管理" link="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="资料上传" selected="true" style="padding:10px;">
			<div class="page_form">
			<form id="upFrm" action="${ctx}/admin/pri/meeting/doFileUpload.action" enctype="multipart/form-data" method="POST" >
				<fieldset>
				<dl>
				<dt class="title"><label for="title">请选择文件上传</label></dt>
				<dd><input name="upload" id="upload" type="file"/><font color="red">&nbsp;系统最大支持100M文件。</font></dd>
				</dl>
				<dl>
				<dt><label for="title">排序</label></dt>
				<dd>
				<input class="half" name="sortCode" id="sortCode" onkeyup="value=value.replace(/[^\d]/g,'')" type="text" maxlength="5"/>
				</dd>
				</dl>
				<dl>
				<dt><label for="title">备注</label></dt>
				<dd>
					<input class="half" name="fileComment" type="text" maxlength="20"/>
					<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}" />
				</dd>
				</dl>
				<dl>
				<dt><label for="title">权限</label></dt>
				<dd>
					一级<input type="checkbox"  name="memberLevel" value="1" />
					二级<input type="checkbox"  name="memberLevel" value="2" />
					三级<input type="checkbox"  name="memberLevel" value="3" />
					四级<input type="checkbox"  name="memberLevel" value="4" />
					五级<input type="checkbox"  name="memberLevel" value="5" />
				</dd>
				</dl>
				<dl>
					<dt><label for="title">支持下载</label></dt>
					<dd>
						<input type="radio" value="1" name="downloadFlg">是
						<input type="radio" checked="checked" value="0" name="downloadFlg">否 
					</dd>
				</dl>
				<dl>
					<dt><label for="title">支持预览</label></dt>
					<dd>
					<input type="radio" value="1" name="previewFlg">是
					<input type="radio" checked="checked" value="0" name="previewFlg">否 
					</dd>
				</dl>

				<!-- 类别选择 -->
				<dl>
				<dt><label for="title">文件夹</label></dt>
				<dd>
				<select id="assortId" name="assortId" style="width:200px;">
				  <option value=""> </option>
				  <c:forEach var="meetingFilesAssort" items="${meetingFilesAssortLst}" varStatus="status">
				  <c:if test="${not empty meetingFilesAssort}">
				  <option value="${meetingFilesAssort.id }">${meetingFilesAssort.assortName }</option>
				  </c:if>
				  </c:forEach>
				</select>
				</dd>
				</dl>
				</fieldset>

				<div class="page_form_sub">
				 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">上传 </a> <font color="red">${retMsg}</font> 
				</div>
			</form>
			</div>		
		</div>
		<div title="分类管理" link="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="添加分类" link="${ctx}/admin/pri/files/preAddAssort.action?meetingId=${meetingId}" style="padding:10px;"></div>			
	</div>
</body>
</html>