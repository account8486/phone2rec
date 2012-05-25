<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
	<%@ include file="/pages/portal/common/header.jsp" %>
	
	<br/>
	<div class="w960">
	        <div class="cbox">
	        	<div class="title"><h5 id="caption">
	        	${errMsg }
	        	</h5></div>
	        </div>
	        <br/><br/>
	    	&nbsp;&nbsp;&nbsp;<a class="btn_blue" style="text-decoration:none;" href="${ctx}/portal/pri/gift/list.action?meetingId=${meetingId}">继续订购</a>
	        <br/><br/><br/><br/><br/><br/><br/>
	</div>
<%@ include file="/pages/portal/common/footer.html" %>
