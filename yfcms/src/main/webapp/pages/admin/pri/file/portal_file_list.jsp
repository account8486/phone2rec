<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.wondertek.meeting.model.MeetingFiles" %>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>资料管理</title>
    <link href='${cssdir}/colorbox.css' rel='stylesheet' type='text/css'/>
    
    ${jquery_js}
    <script>
    
    function pageSkip(cpage) {
        $('#currentPage').val(cpage);
        $('#queryForList').click();
    }


    function openUploadWin() {
        var url = "${ctx}/pages/admin/pri/file/file_add.jsp?meetingId=" + $("#meetingId").val();
        //alert(url);
        window.open(url, "_blank", "height=300,width=800,top=200,left=200");

    }

    function delFile(meetingFileid) {
        var url = "${ctx}/admin/pri/meeting/deleteMeetingFile.action?meetingFileId=" + meetingFileid;
        //alert(url);
        if(confirm("确认删除吗？")){
            document.location.href = url;
        }

    }

    function download(meetingFileid) {
        var url = "${ctx}/admin/pri/meeting/doDownloadFile.action?meetingFileId=" + meetingFileid;
        window.open(url, "_blank", "height=200,width=500,top=200,left=200");
    }


    function reloadPage() {
        //alert('reload');
        //window.opener.location.reload();
        //parent.location.href = parent.location.href;
    }
    

    </script>

</head>


<body onLoad="reloadPage();">
<script type='text/javascript' src='${ctx}/js/jquery.colorbox.js'></script>
<div class="w960">
<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
    <div>
        <!-- 查询FORM -->
        <form id="listMeetingFileForm" action="${ctx}/portal/pri/meeting/getMeetingFilesPager.action?from=portal">
            <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
            <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
            <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
              <input type="hidden" id="from" name="from" value="portal"/>
			<input type="hidden" id="queryForList" class="btn_common btn_true" onclick="query();"/>
				<table class="content_table">
		      <tr>
		        <td >
		        	文件名：
		        	<input type="text" style="width: 120px; " id="meetingFileName" name="meetingFileName"
	                                   value="${meetingFileName}"/>                      
	            	     资料分类     
   <select id="assortId" name="assortId" style="width:200px;">
  <option value=""> </option>
  <c:forEach var="meetingFilesAssort" items="${meetingFilesAssortLst}" varStatus="status">
  <c:if test="${not empty meetingFilesAssort}"  >
  <option value="${meetingFilesAssort.id }"  <c:if test="${assortId eq meetingFilesAssort.id}">selected="selected"</c:if>  >${meetingFilesAssort.assortName }</option>
  </c:if>
  </c:forEach>
  </select>
  
<a href="#" id="queryForList" onclick="query();" class="btn_blue">搜 索</a> 
	            </td>
		       
		      </tr>
		    </table>
			
        </form>


        <!-- 显示内容TABLE -->
        <table class="content_table tableInfo">
            <thead>
            <tr height="25px">
            
                <th width="" align="center">文件名称</th>

                <th width="80px">文件类型</th>
                <th width="80px">资料分类</th>
                <th width="120px">上传时间</th>
                <th width="70px" colspan="2">操作</th>
            </tr>
            </thead>

            <c:choose>
                <c:when test="${not empty pager.pageRecords}">
                    <c:forEach var="meetingFile" items="${pager.pageRecords}" varStatus="status">
                        <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>


                            <td align="left">${meetingFile.fileName }</td>


                            <!-- 文件类型 -->
                            <td align="left">
                                <c:choose>
                                    <c:when test="${meetingFile.filePostfix=='JPG'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/jpg.gif"></c:when>
                                    <c:when test="${meetingFile.filePostfix=='DOC'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/word.gif"></c:when>
                                    <c:when test="${meetingFile.filePostfix=='BMP'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/bmp.gif"></c:when>
                                    <c:when test="${meetingFile.filePostfix=='GIF'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/gif.gif"></c:when>
                                    <c:when test="${meetingFile.filePostfix=='JPG'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/jpg.gif"></c:when>
                                    <c:when test="${meetingFile.filePostfix=='PNG'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/jpg.gif"></c:when>

                                    <c:when test="${meetingFile.filePostfix=='TXT'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/txt.gif"></c:when>
                                    <c:when test="${meetingFile.filePostfix=='PDF'}"><img width="16" height="16" alt="${meetingFile.filePostfix}"
                                                                                          title="${meetingFile.filePostfix}"
                                                                                          src="${ctx}/images/file/filetype/pdf.gif"></c:when>
                                    <c:otherwise>
                                        <img width="16" height="16" alt="${meetingFile.filePostfix}" title="${meetingFile.filePostfix}"
                                             src="${ctx}/images/file/filetype/other.gif">
                                    </c:otherwise>
                                </c:choose>
                            </td>

							<td align="left">${meetingFile.assortName }</td>
                            <td align="left">
                                <fmt:formatDate value="${meetingFile.createTime}"
                                                type="both"
                                                pattern="MM月d日 HH:mm"/></td>
                            <td align="center">

                                <c:if test="${meetingFile.downloadFlg == 1}"><a target="_blank" href="${ctx}/portal/meeting/doDownloadFile.action?meetingFileId=${meetingFile.id} ">下载 </a></c:if>
                                <c:if test="${meetingFile.state == 1 && meetingFile.previewFlg == 1}"><a class="pre" href="#"
                                                                          onclick="preFile('${meetingFile.id}', '${meetingFile.meetingId}');">预览</a></c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="datarow">
                        <td colspan="10" align="center">
                            无会议资料
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>

<!-- 如果没有资料 不显示分页 -->
<c:if test="${not empty pager.pageRecords&&pager.pageCount>1}">
<div>
    <%@ include file="/pages/common/page.jsp" %>
</div>
</c:if>
    </div>
</div>
<div id='image_group'></div>

<%@ include file="/pages/portal/common/footer.html" %>
<script>
    function preFile(file_id, meeting_id) {
        var url = "${ctx}/client/base/previewfile.action?id=" + file_id + "&meetingId=" + meeting_id;
        $.ajax({
            dataType   :   "json",
            url        :   url,
            success    :   function(data, resp, XMLHttpRequest) {
                if (data.retcode != 0) {
                    alert(data.retmsg);
                    return;
                }
                var ss = "";
                for (var i = 0; i < data.num; i++) {
                    var ts = data.url.substring(0, data.url.length - 5) + i + ".jpg";
                    ss += "<a id='img_pre_" + i + "' class='img_pre'  href='" + ts + "'></a>\n";
                }

                $("#image_group").html(ss);
                $(".img_pre").colorbox({rel:'img_pre', transition:"none"});
                $("#img_pre_0").click();
            }
        });
    }
    
    
    function query() {
        $('#listMeetingFileForm').submit();
    }

</script>

</body>
</html>