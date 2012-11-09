<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资料管理  -- ${CURRENT_MEETING}</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}   
	${jmpopups_js}      
    ${util_js}   
  <script>
   function doSbFrm(){
	   
	   showLoading("正在更新中......");
	   $("#addFrm").submit();
   }
  </script>
</head>
<body>




	<div class="mainbox">
		<div class="page_title">
		<h3>资料管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
<div class="page_form">
<form id="addFrm" action="${ctx}/admin/pri/meeting/updateMeetingFile.action" enctype="multipart/form-data" method="POST" >

<fieldset>

<dl>
<dt><label for="title">文件名称</label></dt>
<dd>

<input class="half" name="fileName" id="fileName" type="text" readonly="readonly" disabled="disabled" value="${meetingFiles.fileName }" maxlength="20"/>

<input type="hidden" id="meetingId" name="meetingId" value="${meetingFiles.meetingId }" />
<input type="hidden" id="filesId" name="filesId" value="${meetingFiles.id}" />
</dd>
</dl>

<dl>
<dt><label for="title">排序</label></dt>
<dd>
<input class="half" name="sortCode" id="sortCode" onkeyup="value=value.replace(/[^\d]/g,'')" type="text" value="${meetingFiles.sortCode}" maxlength="5"/>
</dd>
</dl>

<dl>
<dt><label for="title">备注</label></dt>
<dd>
<input class="half" name="fileComment" type="text" value="${meetingFiles.comments}" maxlength="20"/>
<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}" />

</dd>
</dl>


<dl>
<dt><label for="title">权限</label></dt>
<dd>
一级<input type="checkbox"  name="memberLevel" value="1" <c:if test="${fn:contains(meetingFiles.fileAccess,1)}"> checked="checked" </c:if>  />
二级<input type="checkbox"  name="memberLevel" value="2" <c:if test="${fn:contains(meetingFiles.fileAccess,2)}"> checked="checked" </c:if>  />
三级<input type="checkbox"  name="memberLevel" value="3" <c:if test="${fn:contains(meetingFiles.fileAccess,3)}"> checked="checked" </c:if>  />
四级<input type="checkbox"  name="memberLevel" value="4" <c:if test="${fn:contains(meetingFiles.fileAccess,4)}"> checked="checked" </c:if>  />
五级<input type="checkbox"  name="memberLevel" value="5" <c:if test="${fn:contains(meetingFiles.fileAccess,5)}"> checked="checked" </c:if>  />
</dd>
</dl>

<dl>
	<dt><label for="title">支持下载</label></dt>
	<dd>
	<input type="radio" <c:if test="${meetingFiles.downloadFlg != null && meetingFiles.downloadFlg eq 1}"> checked="checked" </c:if> value="1" name="downloadFlg">
	是
	<input type="radio" <c:if test="${meetingFiles.downloadFlg == null || meetingFiles.downloadFlg != 1}"> checked="checked" </c:if> value="0" name="downloadFlg">
	否 
	</dd>
</dl>

<%--
<dl>
	<dt><label for="title">支持预览</label></dt>
	<dd>
	<input type="radio" <c:if test="${meetingFiles.previewFlg != null && meetingFiles.previewFlg eq 1}"> checked="checked" </c:if> value="1" name="previewFlg">
	是
	<input type="radio" <c:if test="${meetingFiles.previewFlg == null || meetingFiles.previewFlg != 1}"> checked="checked" </c:if> value="0" name="previewFlg">
	否 
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
  <option value="${meetingFilesAssort.id }" <c:if test="${meetingFilesAssort.id eq meetingFiles.assortId}">selected="selected"</c:if> >${meetingFilesAssort.assortName }</option>
  </c:if>
  </c:forEach>
  
</select>
</dd>
</dl>

<dl>
				<dt class="title"><label for="title">上传资料封面</label></dt>
				<dd><input name="fileCover" id="fileCover" type="file"/><font color="black">&nbsp;建议上传尺寸为139*189大小的图片。</font></dd>
				</dl>
</fieldset>

<div class="page_form_sub">
 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">保存 </a> <font color="red"><s:fielderror/></font> 
 <a href="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}"  id="addUserBtn" class="btn_common btn_false">返回列表</a>
</div>

</form>
</div></div>
</body>
</html>