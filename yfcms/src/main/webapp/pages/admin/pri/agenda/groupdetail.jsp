<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>

<style>
LI.members {display: inline;font-size:13px;}
.title_back {display: inline-block;float: right; margin-top: -30px; margin-right: 10px;}
</style>
<c:set var="group" value="${group}"/>
<c:set var="userList" value="${members}"/>
<c:set var="listSize" value="${fn:length(userList)}"></c:set>

<div class="w960">
    <div class="cbox"><div class="title"><h5 id="caption">会议议程 - 分组名单</h5>
        <span class="title_back">
        	<a href="${ctx}/portal/pri/agenda/agendaList.action?meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">返回议程</a>
        </span>
    </div></div>
	
	<div class="cont" style="min-height: 200px;">
	<table class="content_table tableInfo">
    	<thead>
        	<tr>
            	<th colspan="4">组名：
            	<c:if test="${'0' eq group.isOpen }">${group.groupName }</c:if>
	        	<c:if test="${'1' eq group.isOpen }">
	            	<a href="${ctx}/portal/pri/agenda/groupPlan.action?groupPlan.id=${group.planId }&meetingId=${meetingId}&menu_id=${param.menu_id}">${group.groupName }</a>
	        	</c:if>
            	&nbsp;&nbsp;&nbsp;总计:${listSize }人</th>
            </tr>
        </thead>
        <tbody>
        	<c:if test="${not empty group.detail}"><tr><td>${group.detail}</td></tr></c:if> 
        	<tr class="odd"><td><ul>
            <c:forEach var="user" items="${userList}" varStatus="status">
               	<li class="members" <c:if test="${user.id == userId}">style="color:red; font-weight:bold;"</c:if>>${user.name}<c:if test="${not empty user.job && user.isShowJob eq '1'}">${user.job}</c:if>;</li>
            </c:forEach>
            </ul></td></tr>
        </tbody>
    </table>
    </div>
</div>

<%@ include file="/pages/portal/common/footer.html" %>   