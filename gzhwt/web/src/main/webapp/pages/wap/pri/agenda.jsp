<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>

    <c:if test="${not empty view.agendaViews}">
    <div class="article date">
    	<c:if test="${preIdx>=0}">
    		<a href="${ctx}/wap/pri/agenda/view.action?meetingId=${meetingId}&index=${preIdx}&menu_id=${param.menu_id}">前一天</a>
    	</c:if> 
    	&nbsp;&nbsp;<strong>${view.date}</strong>&nbsp;&nbsp;
    	<c:if test="${nextIdx<lastIdx}">
    		<a href="${ctx}/wap/pri/agenda/view.action?meetingId=${meetingId}&index=${nextIdx}&menu_id=${param.menu_id}">后一天</a>
    	</c:if>
    </div>
    </c:if>

	<div class="article">
	  	<c:choose>
	        <c:when test="${not empty view.agendaViews}">
	        	<ul class="message_list">
	            <c:forEach var="meetingagenda" items="${view.agendaViews}" varStatus="status">
		        	<li <c:if test="${status.count % 2 eq 0}"> class="odd"</c:if>>
	            	<p style="text-align:left;">
	            		<span class="name"><b>时间</b>：${meetingagenda.startTime } - ${meetingagenda.endTime }</span>
	            		<span class="name">&nbsp;&nbsp;<b>标题</b>：${meetingagenda.topic }</span>
	            		<c:if test="${not empty meetingagenda.hostName }">
	                    	<span class="name">&nbsp;&nbsp;<b>人员</b>：${meetingagenda.hostName }</span>
	                    </c:if>
	                    <c:if test="${not empty meetingagenda.location }">
	                    	<span class="name">&nbsp;&nbsp;<b>地点</b>：${meetingagenda.location }</span>
	                    </c:if>
	                    
	                   <c:if test="${not empty meetingagenda.attendeeNames }">
	                   	<p style="line-height:2em;"><span class="name">参议人员：${meetingagenda.attendeeNames }</span></p>
	                  </c:if>
	                  
			            <div class="contentbox" style="margin-bottom:2px;"><span class="content">${meetingagenda.description}</span></div>
			            <c:if test="${meetingagenda.hasGroup}">
                     		<span class="ctrl"><a href="${ctx}/wap/pri/agenda/group.action?meetingId=${meetingagenda.meetingId}&menu_id=${param.menu_id}&agendaId=${meetingagenda.id}" class="group">分组详情</a></span>
                     	</c:if>
               			<c:if test="${!meetingagenda.hasGroup && not empty meetingagenda.groupPlanId}">
               				<span class="ctrl"><a href="${ctx}/wap/pri/agenda/groupPlan.action?groupPlan.id=${meetingagenda.groupPlanId}&meetingId=${meetingagenda.meetingId}&menu_id=${param.menu_id}&agendaId=${meetingagenda.id}" class="group">分组详情</a></span>
               			</c:if>                     	
	                </p>
	                </li>
	            </c:forEach>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	           <span>没有会议议程信息.</span>
	        </c:otherwise>
	    </c:choose>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>