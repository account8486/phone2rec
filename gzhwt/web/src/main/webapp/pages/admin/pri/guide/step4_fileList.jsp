<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>资料管理</title>
    ${admin_css} 
    ${jquery_js}
    ${jmpopups_js}
    ${util_js} 
    ${admin_js}
</head>
<body>


			<!-- 显示内容TABLE -->
			<table class="page_datalist">
			<thead>
				<tr>
				
				<th width="15%" align="center">文件名称</th>
				<th width="10%" align="center">保存名称</th>

				<th width="10%">文件类型</th>
				<th width="3%">排序</th>
				<th width="10%">分类名称</th>
				<th width="8%">上传时间</th>
		
				
				</tr>
			</thead>
			<tbody>
			<c:choose>
			<c:when test="${not empty pager.pageRecords}">
			<c:forEach var="meetingFile" items="${pager.pageRecords}" varStatus="status">
			<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
			
				<td align="left">${meetingFile.fileName}</td>
				<td align="left">${meetingFile.fileSaveName }</td>
				<!-- 文件类型 -->
				<td align="left">
				<c:choose>
				<c:when test="${meetingFile.filePostfix=='JPG'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/jpg.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='DOC'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/word.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='BMP'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/bmp.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='GIF'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/gif.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='JPG'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/jpg.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='PNG'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/jpg.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='XLS'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/excel.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='TXT'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/txt.gif"></c:when>
				<c:when test="${meetingFile.filePostfix=='PDF'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
					title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/pdf.gif"></c:when>
				<c:otherwise>
					<img width="16" height="16" alt="${meetingFile.filePostfix}" title="${meetingFile.filePostfix}" src="${ctx}/images/file/filetype/other.gif">
				</c:otherwise>
				</c:choose>
				</td>

				<td align="left">${meetingFile.sortCode }</td>
				<td align="left">${meetingFile.assortName }</td>
				<td align="left"><fmt:formatDate value="${meetingFile.createTime}" type="both" pattern="MM月d日 HH:mm"/></td>
				
			</tr>
			</c:forEach>
			</c:when>
			<c:otherwise>
			<tr class="datarow"><td colspan="10" align="center">无会议资料</td></tr>
			</c:otherwise>
			</c:choose>
			</tbody>
			</table>
			<div><%@ include file="/pages/common/page.jsp" %></div>

</body>
</html>