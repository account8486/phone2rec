<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960" style="min-height: 300px">
	<div class="cbox"><div class="title"><h5 id="caption">座位安排</h5></div></div>
		<div style="width: 100%; height: 100%; text-align:center;">
			<img src="${ctx}/portal/pri/meeting/managerseat_getSeatImage.action" alt="红色圈为您的座位安排" width="700" height="500"/>
		</div>

	</div>

<%@ include file="/pages/portal/common/footer.html" %>