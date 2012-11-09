<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>

    	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">分组信息</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
   		 </ul>

	   <div class="tab_c" style="display:block;">
	   
		<c:forEach items="${groupDetailMap}" var="entry">  
		   <c:set var="group" value="${entry.key}"/>
		   <c:set var="members" value="${entry.value}"/>
	        <c:if test="${not empty group}">
	        	<ul class="message_list">
	        	<li>
	        		<b>组名：</b>${group.groupName }
	        		&nbsp;&nbsp;&nbsp;<b>总计:</b>${fn:length(members)}人
	        	</li>
	        	<c:if test="${not empty group.detail }"><li>${group.detail }</li></c:if>
	        	<li>
	            <c:forEach var="user" items="${members}" varStatus="status">
	            	<span class="name" <c:if test="${user.id == userId}">style="color:red; font-weight:bold;"</c:if>>
	            		${user.name }<c:if test="${'1' eq user.isShowJob}"><c:if test="${not empty user.job}">${user.job}</c:if></c:if>;
	            	</span>
	            </c:forEach>
	            </li>
	           	</ul>
	           	<br>
	        </c:if>
		</c:forEach>
		<a href="${ctx}/touch/pri/agenda/agendaList.action?meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">返回议程</a>
	</div>
	
	
<%@ include file="/pages/touch/common/footer.jsp" %>