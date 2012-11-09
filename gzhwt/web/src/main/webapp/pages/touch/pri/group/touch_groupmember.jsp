<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>

    	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">分组详情</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
   		 </ul>

	   <div class="tab_c" style="display:block;">
	   
	   
	  	<c:choose>
	        <c:when test="${not empty group}">
	        	<ul class="message_list">
	        	<li>
	        		组名：
	        		<c:if test="${'0' eq group.isOpen }">${group.groupName }</c:if>
	        	<c:if test="${'1' eq group.isOpen }">
	            	<a href="${ctx}/touch/pri/agenda/groupPlan.action?groupPlan.id=${group.planId }&meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">${group.groupName }</a>
	        	</c:if>
	        		&nbsp;&nbsp;&nbsp;总计:${fn:length(members)}人
            		
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
	    
	    <br>
	    <a href="${ctx}/touch/pri/agenda/agendaList.action?meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">返回议程</a>
	    
	</div>

	
	
	<%@ include file="/pages/touch/common/footer.jsp" %>