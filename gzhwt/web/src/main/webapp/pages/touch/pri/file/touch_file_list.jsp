<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
<%@ page import="com.wondertek.meeting.util.FileOperatorUtil"%>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会议资料</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    <c:choose>
        <c:when test="${not empty meetingFileList}">
        	<ul class="file_list">
             <c:forEach var="meetingFile" items="${meetingFileList}" varStatus="status">
	             <li class="${wd:getFileCssByFilePostfix(meetingFile.filePostfix)}">
	             <div class="tools">
	            <%-- 	<a href="javascript://" class="pre">预览</a>--%>
	            <c:if test="${meetingFile.downloadFlg == 1}">
	            <a class="down" target="_blank" href="${ctx}/portal/meeting/doDownloadFile.action?from=WAP&meetingFileId=${meetingFile.id}">下载 </a>
	            </c:if>
	            </div>
	            
	             <span class="title">${meetingFile.fileName}</span>
				<%--大小：${meetingFile.fileSize}KB<br> --%>	
					文件类型：${meetingFile.filePostfix }
	            </li>
             </c:forEach>
            </ul>
        </c:when>
    </c:choose>  
    </div>
    
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.file_list {}
	ul.file_list li { padding:10px; padding-left:75px; position:relative; border-bottom:1px solid #ccc;}
	ul.file_list li.defaultCss { background:url(${ctx}/images/touch/file/file_default.png) 5px 0px no-repeat;}
	ul.file_list li.doc { background:url(${ctx}/images/touch/file/file_doc.png) 5px 0px no-repeat;}
	ul.file_list li.excel { background:url(${ctx}/images/touch/file/file_excel.png) 5px 0px no-repeat;}
	ul.file_list li.image { background:url(${ctx}/images/touch/file/file_image.png) 5px 0px no-repeat;}
	ul.file_list li.pdf { background:url(${ctx}/images/touch/file/file_pdf.png) 5px 0px no-repeat;}
	ul.file_list li.ppt { background:url(${ctx}/images/touch/file/file_ppt.png) 5px 0px no-repeat;}
	ul.file_list li.txt { background:url(${ctx}/images/touch/file/file_txt.png) 5px 0px no-repeat;}
	ul.file_list li.video { background:url(${ctx}/images/touch/file/file_video.png) 5px 0px no-repeat;}
	ul.file_list li.zip { background:url(${ctx}/images/touch/file/file_zip.png) 5px 0px no-repeat;}
	ul.file_list li span.title { color:#069; display:block; font-weight:bold;}
	ul.file_list li .tools { position:absolute; right:10px; bottom:10px;}
	ul.file_list li .tools a { width:60px;  height:30px; font-weight:bold; display:inline-block; text-align:center; line-height:30px; margin:10px 0px; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; margin-left:10px;}
	ul.file_list li .tools a.pre {    }
	ul.file_list li .tools a.down { }
</style>
