<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
    
    <div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>

    <div>

        <!-- 显示内容TABLE -->
        <table>
          
            <tr>
                <th  align="center">文件列表</th>
            </tr>
       

            <c:choose>
                <c:when test="${not empty pager.pageRecords}">
                    <c:forEach var="meetingFile" items="${pager.pageRecords}" varStatus="status">
                        <tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>


                            <td align="left">
                            <c:choose>
                            <c:when test="${meetingFile.downloadFlg == 1}">
                            <a target="_blank" href="${ctx}/portal/meeting/doDownloadFile.action?from=WAP&meetingFileId=${meetingFile.id} ">${meetingFile.fileName}</a>
                            </c:when>
                			<c:otherwise>
                				${meetingFile.fileName}
                			</c:otherwise>
                			</c:choose>
                            </td>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="datarow">
                        <td colspan="10" align="center">
                            此会议无会议资料
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>

    </div>
</div>
<div id='image_group'></div>

<%@ include file="/pages/portal/common/footer.html" %>
</body>

</html>