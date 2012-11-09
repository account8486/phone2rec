<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name} 分组详情
    </div>

	<div class="article">
	  	<c:choose>
	        <c:when test="${not empty group}">
	        	<ul class="message_list">
	        	<li>
	        		组名：
	        		<c:if test="${'0' eq group.isOpen }">${group.groupName }</c:if>
	        	<c:if test="${'1' eq group.isOpen }">
	            	<a href="${ctx}/wap/pri/agenda/groupPlan.action?groupPlan.id=${group.planId }&meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">${group.groupName }</a>
	        	</c:if>
	        		&nbsp;&nbsp;&nbsp;总计:${fn:length(members)}人
            		<a href="${ctx}/wap/pri/agenda/view.action?meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">返回议程</a>
	        	</li>
	        	<c:if test="${not empty group.detail}"><li>${group.detail}</li></c:if> 
	        	<li>
	            <c:forEach var="user" items="${members}" varStatus="status">
	            	<span class="name" <c:if test="${user.id == userId}">style="color:red; font-weight:bold;"</c:if>>
	            		${user.name }<c:if test="${not empty user.job && user.isShowJob eq '1'}">${user.job}</c:if>;
	            	</span>
	            </c:forEach>
	            </li>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	           <span>没有${param.menu_name} 详情.</span>
	        </c:otherwise>
	    </c:choose>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>