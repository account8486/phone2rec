<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<div class="main">
	<div class="path">
     ${meeting_list_url} > ${param.menu_name}
    </div>
	
	<br/>
	<div style="margin-left:2px;">
	    <div class="article">
	    ${errMsg }
	    <br/><br/>
	    <a class="btn_blue" style="text-decoration:none;" href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId}">继续订购</a>
	   	</div>
   	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>