<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
    <div class="path">${meeting_list_url} > ${title}</div>
    <div class="article">
    	<ul class="message_list"><li><div class="contentbox"><span class="content">${message}</span></div></li></ul>
    </div>
    
	<div class="article pagination">
    	<a href="${ctx}${returnUrl}">返回</a>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>