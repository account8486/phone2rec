<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<style type="text/css">
.date {
	background-color: #c9c999;
}
.article strong{font-style:normal;font-weight:bold;}
</style>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} >  ${content.contentTitle }
    </div>

	<div class="article">
	  	${content.content }
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>