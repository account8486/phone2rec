<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<div class="main">
	<div class="path">
     ${meeting_list_url} > ${param.menu_name}
    </div>
    
	<form id="mainForm" action="${ctx}/wap/pri/meeting/signIn.action" method="post">
		<input type="hidden" name="meetingId" value="${param.meetingId }"/>
		<div style="margin-left:20px;">
			<div class="article">
		   		<font color="red">${errMsg }</font>
		   	</div>
		   	
		    <div class="article">
		   	手机号码：<br/>
		   	<input type="text" id="mobile" name="mobile" value="${mobile}"/>
		   	</div>
		   	
		   	<div class="article">
		   	签到码：<br/>
		   	<input type="text" id="signCode" name="signIn.signCode" value="${signIn.signCode }"/>
		   	</div>
		   	
		   	<div class="article" style="margin-top:10px;">
		   		<input style="margin-left:60px;" type="submit" value="&nbsp;签 到&nbsp;"/>&nbsp;
		   	</div>
	   	</div>
	</form>
	
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>