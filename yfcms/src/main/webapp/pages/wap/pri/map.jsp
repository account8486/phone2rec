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
	<div class="article">
	 <img src="http://api.map.baidu.com/staticimage?center=${meeting.locationXY}&markers=${meeting.locationXY}" >
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>