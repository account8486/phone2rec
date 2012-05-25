<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="requestUri" value="${pageContext.request.requestURI}" />
<c:set var="queryString" value="${pageContext.request.queryString}" />
<div class="nav">
	<ul>
		<c:forEach var="menu" items="${_portal_menulist_}" varStatus="status">
			<c:set var="menu_str" value="menu_id=${menu.id}" />
			<li <c:if test='${fn:contains(queryString,menu_str)}'>class="act"</c:if>><a href="${ctx}/${menu.contentUrl}${_portal_meeting_.id}&menu_id=${menu.id}&menu_name=${menu.name}">${menu.name}</a></li>
    	</c:forEach>
    	<li>&nbsp;</li>
    </ul>
</div>