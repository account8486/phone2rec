<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<style>
LI.members {display: inline;font-size:13px;}
.title_back {display: inline-block;float: right; margin-top: -30px; margin-right: 10px;}
</style>
<div class="w960">
    <div class="cbox"><div class="title"><h5 id="caption">会议议程 - 分组信息</h5>
        <span class="title_back">
        	<a href="${ctx}/portal/pri/agenda/agendaList.action?meetingId=${meetingId}&menu_id=${param.menu_id}" class="btn_blue">返回议程</a>
        </span>
    </div></div>
	<div class="cont" style="min-height: 200px;">
		<c:forEach items="${groupDetailMap}" var="entry">  
		   <c:set var="group" value="${entry.key}"/>
		   <c:set var="userList" value="${entry.value}"/>
		   <c:set var="listSize" value="${fn:length(userList)}"></c:set>
			<table class="content_table tableInfo">
		    	<thead>
		        	<tr>
		        		<th width="1px">&nbsp;</th>
		            	<th colspan="4">组名：${group.groupName }&nbsp;&nbsp;&nbsp;总计:${listSize }人</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:if test="${not empty group.detail }">
			        	<tr>
			        		<th width="1px">&nbsp;</th>
			            	<th colspan="4">${group.detail }</th>
			            </tr>
		            </c:if>
                        <tr class="odd"><td width="1px">&nbsp;</td><td><ul>
	                    <c:forEach var="user" items="${userList}" varStatus="status">
	                        <li class="members" <c:if test="${user.id == userId}">style="color:red; font-weight:bold;"</c:if>>${user.name }<c:if test="${'1' eq user.isShowJob}"><c:if test="${not empty user.job}">${user.job }</c:if></c:if>;</li>
	                    </c:forEach>
	                    </ul>
                   </td>
                   </tr>
		        </tbody>
		    </table>
	</c:forEach>
	</div>
</div>
<%@ include file="/pages/portal/common/footer.html" %>   