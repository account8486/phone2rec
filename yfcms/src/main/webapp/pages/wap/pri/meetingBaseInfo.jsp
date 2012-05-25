<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    
    <div class="article">
    	　　${meeting.topic}
    </div>
    
    <div class="article">
		<ul class="meeting_list">
        	<li>
            	<h5>会议地点</h5>
                <p>${meeting.location}</p>
            </li>
        	<li>
            	<h5>开始时间</h5>
                <p>${fn:substring(meeting.startTime,0,16)}</p>
            </li>
        	<li>
            	<h5>结束时间</h5>
                <p>${fn:substring(meeting.endTime,0,16)}</p>
            </li>
        	<li>
            	<h5>通知公告</h5>
                <p>${meeting.notice}</p>
            </li>    
            <c:if test="${meeting.joinTime !='' && meeting.joinTime != null}">
        	<li>
            	<h5>报到时间</h5>
                <p>${meeting.joinTime}</p>
            </li>      
            </c:if>       
        	<li>
            	<h5>Android客户端下载</h5>
                <p><a href="/hyy.apk" class="download">点此下载</a></p>
            </li>
        </ul>
    </div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>