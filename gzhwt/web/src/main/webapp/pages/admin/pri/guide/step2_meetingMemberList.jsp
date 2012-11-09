<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>


<div class="easyui-tabs" border="false" style="padding:10px;">	
	<div title="用户列表" style="padding:10px;">
		<div class="page_tools">
		<table class="page_datalist">
		
		<thead>
			<tr>
				<th width="10%">姓名</th>
				
				<th width="15%">手机号码 </th>
				<th width="10%">性别</th>
				<th width="15%">职务</th>
				<th width="10%">邮箱</th>
		
			</tr>
		</thead>
		
		<tbody>
			<tr> 
				<c:choose>
				<c:when test="${not empty pager.pageRecords}">
					<c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
						<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
							<td>${user.name }</td>
							<td>${user.mobile }</td>
							 <td>${user.gender == 0 ? "男":(user.gender == 1 ? "女":"保密") }</td>
							<td>${user.meetingMember.bookJob }</td>
							<td>${user.meetingMember.mailbox}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="datarow"><td colspan="11" align="center"> 无用户信息.</td></tr>
				</c:otherwise>
			</c:choose>
			</tr>
		</tbody>
	</table>
	<%@ include file="/pages/common/page.jsp" %> 
	</div>
	</div>
	</div>
	
</body>
</html>