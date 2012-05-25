<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name} >宴会安排
    </div>
	<div class="article" style="min-height: 200px;">
		<c:forEach items="${groupDetailMap}" var="entry">  
		   <c:set var="group" value="${entry.key}"/>
		   <c:set var="members" value="${entry.value}"/>
	        <c:if test="${not empty group}">
	        	<ul class="message_list">
	        	<li>
	        		<b>${group.groupName }</b>
	        		&nbsp;&nbsp;&nbsp;<b>总计:</b>${fn:length(members)}人
	        	</li>
	        	<c:if test="${not empty group.detail }"><li>${group.detail }</li></c:if>
	        	<li>
	            <c:forEach var="user" items="${members}" varStatus="status">
	            	<span class="name" <c:if test="${user.id == userId}">style="color:red; font-weight:bold;"</c:if>>
	            		${user.name }<c:if test="${not empty user.job && user.isShowJob eq '1'}">${user.job}</c:if>;
	            	</span>
	            </c:forEach>
	            </li>
	           	</ul>
	           	<br>
	        </c:if>
		</c:forEach>
	<a href="${ctx}/wap/pri/meeting/showDinner.action?meetingId=${meetingId}&menu_id=${param.menu_id}" >返回</a>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>