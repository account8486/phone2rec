<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<ul class="nav_icon">

<c:if test="${not empty menuList}">
<c:forEach var="menu" items="${menuList}" varStatus="status" >
 <li>
        <a href="${ctx}/${menu.contentUrl}${not empty param.meetingId ? param.meetingId : meeting.id}&menu_id=${menu.id}&menu_name=${menu.name}">
            <img src="${serverUrl}${menu.imgUrl}"/>
            <span>${menu.name}</span>
        </a>
</li>


</c:forEach>
</c:if>

</ul>