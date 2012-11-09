<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<style type="text/css">
.date {
	background-color: #c9c999;
}
</style>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} >  ${param.menu_name}
    </div>

    <div class="article date">
    	<c:if test="${preIdx>=0}">
    		<a href="${ctx}/wap/pri/meeting/showDinner.action?meetingId=${meetingId}&index=${preIdx}">前一天</a>
    	</c:if> 
    	&nbsp;&nbsp;<strong>${view.date}</strong>&nbsp;&nbsp;
    	<c:if test="${nextIdx<lastIdx}">
    		<a href="${ctx}/wap/pri/meeting/showDinner.action?meetingId=${meetingId}&index=${nextIdx}">后一天</a>
    	</c:if>
    </div>

	<div class="article">
	  	<c:choose>
	        <c:when test="${not empty view.info}">
	        	<ul class="meeting_list">
	            <c:forEach var="info" items="${view.info}" varStatus="status">
		        	<li>
		            	<h5>${'1' eq info.scetion ? '早': '2' eq info.scetion ? '中':'晚' }餐</h5>
		                <h5>${info.address}</h5>
		            	<p>${info.time }</p>
		                <p>
			                <c:if test="${'自选座位' eq info.dinnerTable or '指定座位' eq info.dinnerTable}">
			                	<c:if test="${empty info.groupPlanId || info.groupPlanId < 1}">${info.dinnerTable}</c:if>
                            	<c:if test="${not empty info.groupPlanId && info.groupPlanId > 0}">
                            		<a href="${ctx}/wap/pri/meeting/showAllGroupInfo.action?groupPlan.id=${info.groupPlanId }&meetingId=${meetingId}&menu_id=${param.menu_id}" >${info.dinnerTable}</a>
                            	</c:if>
			                </c:if>
			                <c:if test="${'自选座位' ne info.dinnerTable and '指定座位' ne info.dinnerTable}"><a href="${ctx}/wap/pri/meeting/showGroupInfo.action?meetingId=${meetingId}&dinner.id=${info.dinnerId }&menu_id=${param.menu_id}">${info.dinnerTable}</a></c:if>
		                </p>
		                
		            	<p>${info.type }</p>
		            	<p>${info.comments }</p>
		            </li>
	            </c:forEach>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	           <span>没有餐饮信息.</span>
	        </c:otherwise>
	    </c:choose>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>