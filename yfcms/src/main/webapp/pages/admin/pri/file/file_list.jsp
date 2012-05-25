<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>资料管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js} ${admin_js}
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
        function pageSkip(cpage) {
            $('#currentPage').val(cpage);
            $('#queryForList').click();
        }

        function query() {
            $('#listMeetingFileForm').submit();
        }

        function openUploadWin() {
            var url = "${ctx}/admin/pri/meeting/preAddMeetingFile.action?meetingId=" + $("#meetingId").val();
            window.open(url, "_blank", "height=400,width=800,top=200,left=200");
        }

        function delFile(meetingFileid) {
            var url = "${ctx}/admin/pri/meeting/deleteMeetingFile.action?meetingFileId=" + meetingFileid;
            if(confirm("确认删除吗？")){
	            document.location.href = url;
            }
        }

        function download(meetingFileid) {
            var url = "${ctx}/admin/pri/meeting/doDownloadFile.action?meetingFileId=" + meetingFileid;
            window.open(url, "_blank", "height=200,width=500,top=200,left=200");
        }

        function backToFileList(){
        	var url="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingId}";
        	window.location.href=url;
        }
		function preFile(file_id, meeting_id) {
			var url = "${ctx}/client/base/previewfile.action?id=" + file_id + "&meetingId=" + meeting_id;
			$.ajax({
				dataType   :   "json",
				url        :   url,
				success    :   function(data, resp, XMLHttpRequest) {
					parent.out_preview(data);

				}
			});
		}
    </script>
</head>
<body>

<div>
    <div class=page_title><h3>资料管理  -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="资料管理" style="padding:10px;">
			<!-- 查询FORM -->
			<div class="page_tools page_toolbar" style="text-align:left;"> 
			<form id="listMeetingFileForm" action="${ctx}/admin/pri/meeting/getMeetingFilesPager.action">
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
				<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
				<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
				文件名称：<input type="text" id="meetingFileName" name="meetingFileName" value="${meetingFileName}">
				<a id="queryForList" class="btn_common btn_true" onclick="query();" href="##">查询</a>
			</form>
			</div>

			<!-- 显示内容TABLE -->
			<table class="page_datalist">
			<thead>
				<tr>
				<th width="3%" align="center" style="border-right: 0px"></th>
				<th width="23%" align="center">文件名称</th>
				<th width="12%" align="center">保存名称</th>

				<th width="10%">文件类型</th>
				<th width="5%">排序</th>
				<th width="12%">分类名称</th>
				<th width="10%">上传时间</th>
				<th width="10%">备注</th>
				<th width="25%">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
			<c:when test="${not empty pager.pageRecords}">
			<c:forEach var="meetingFile" items="${pager.pageRecords}" varStatus="status">
			<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
				<td ></td>
				<td align="left"><a href="${ctx}/admin/pri/meeting/selectMeetingFileById.action?filesId=${meetingFile.id}&meetingId=${meetingId}">${meetingFile.fileName}</a></td>
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
				<td align="left">
				<c:choose>
				<c:when test="${not empty meetingFile.comments}">${meetingFile.comments}</c:when>
				<c:otherwise>无</c:otherwise>
				</c:choose>
				</td>

				<td align="center">
				<a href="${ctx}/admin/pri/meeting/selectMeetingFileById.action?filesId=${meetingFile.id}&meetingId=${meetingId}">编辑</a> &nbsp
				<a target="_blank" href="${ctx}/admin/pri/meeting/doDownloadFile.action?meetingFileId=${meetingFile.id} ">下载</a> &nbsp <a href="#" id='delFileLink' onclick="delFile('${meetingFile.id}');">删除</a>
				<c:if test="${meetingFile.state == 1}"><a class="pre" href="#" onclick="preFile('${meetingFile.id}', '${meetingFile.meetingId}');">预览</a></c:if>
				</td>
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
		</div>
		<div title="资料上传" link="${ctx}/admin/pri/meeting/preAddMeetingFile.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="分类管理" link="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="添加分类" link="${ctx}/admin/pri/files/preAddAssort.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>	
</body>
</html>