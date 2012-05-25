<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<div class="main">

	<div class="title">
		<div class="w240">
	    	我的列表
	    </div>
	</div>

    <div class="article">
		<ul class="meeting_list">
			<c:choose>
				<c:when test="${not empty meetingList}">
					 <c:forEach var="meeting" items="${meetingList}">
        	<li>
            	<h5>${meeting.name}</h5>
                <p>
	                <c:out value="${fn:substring(meeting.topic, 0, 40)}" escapeXml="true" />
	            	<c:if test="${fn:length(meeting.topic)>40}">
	            		<c:out value="..."></c:out>
	            	</c:if>
            	</p>
                <span><a href="${ctx}/wap/pri/meeting/getMeetingById.action?returnType=wap_index&meeting.id=${meeting.id}">进入>></a></span>
            </li>
					</c:forEach>
				</c:when>
            	<c:otherwise>
        	<li>            	
                <p>您还没有可以访问的活动</p>
            </li>
            	</c:otherwise>
			</c:choose>

        </ul>
    </div>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>