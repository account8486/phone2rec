<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.wondertek.meeting.model.MeetingFiles" %>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会议资料上传  -- ${CURRENT_MEETING}</title>

    <link href='${cssdir}/colorbox.css' rel='stylesheet' type='text/css'/>
    
    ${jquery_js}
    
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript">
		function doSbFrm(){
			var uploadTxt=$("#upload").val();
			var assortId=$("#assortId").val();
			if(uploadTxt==''){
			   alert("请选择要上传的文件!");
			   return;
			}
			
			$("#upFrm").submit();
		}
	</script>
</head>

<body>
<div class="w960">
	<div class="cbox"><div class="title"><h5>上传会议资料</h3></div></div>
	
			<div>
			<form id="upFrm" action="${ctx}/portal/pri/file/doFileUpload.action" enctype="multipart/form-data" method="POST" >
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
				<%-- 
				<dl>
					<dt><label for="title">支持预览</label></dt>
					<dd>
					<input type="radio" value="1" name="previewFlg">是
					<input type="radio" checked="checked" value="0" name="previewFlg">否 
					</dd>
				</dl>
				--%>
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
				<a href="#" onclick="doSbFrm();" class="btn_blue">
				上传 </a> <font color="red">${retMsg}</font> 
				</div>
			</form>
			</div>		
		</div>
		
</body>
</html>