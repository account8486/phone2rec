<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta http-equiv="Cache-control" content="no-cache" />
<title>安徽电信会议云平台</title>
<link href="${ctx}/css/wap.css" rel="stylesheet" type="text/css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<c:set var="meeting_list_url" value="<a href='${ctx}/wap/pri/meeting/getAttendMeetingList.action'>会议列表</a>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.pagination a
{
	font-size:20px;
}
.comments a
{
	font-size:20px;
}
</style>
</head>
<body>
<div class="main" >
	
	<c:if test="${not empty pager.pageRecords &&(pager.hasPreviousPage || pager.hasNextPage)}">
	    <div class="page_bg">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=1" class="page_btn">首&nbsp;&nbsp;页</a>
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=${pager.currentPage-1}" class="page_btn">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=${pager.currentPage+1}" class="page_btn">下一页</a>
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=${pager.pageCount}" class="page_btn">尾&nbsp;&nbsp;页</a>
	   		</c:if>
	    </div>
	</c:if>
	
	<div>
		<form id="mainForm" action="${ctx}/client/pri/gift/list.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        	<input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			<input type="hidden" id="queryForList" class="btn_common btn_true"/>
		</form>
		
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="gift" items="${pager.pageRecords}" varStatus="status">
			<div class="gift">
				<div class="pic" style="float:left;margin-right:10px;width:120px;"><a href="${ctx}/client/pri/gift/detail.action?id=${gift.id}"><img style="width:120px;height:177px;border:0px;" src="${serverUrl}${gift.imgUrl }" onerror="this.src='${ctx}/images/wap/no_pic.png'" /></a></div>
				<div class="description">
					<div class="comments"><a href="${ctx}/client/pri/gift/detail.action?id=${gift.id}">${fn:substring(gift.name, 0, 27) }</a></div>
					<div class="price"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
					<div class="comments">${fn:substring(gift.introduction, 0, 52) }</div>
				</div>
				<div class="clear"></div>
			</div>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<tr class="second_title">
                <td colspan="6">&nbsp;&nbsp;暂时没有可以订购的礼品。</td>
            </tr>
		</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${not empty pager.pageRecords &&(pager.hasPreviousPage || pager.hasNextPage)}">
	   	<div class="page_bg">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=1" class="page_btn">首&nbsp;&nbsp;页</a>
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=${pager.currentPage-1}" class="page_btn">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=${pager.currentPage+1}" class="page_btn">下一页</a>
	   			<a href="${ctx}/client/pri/gift/list.action?currentPage=${pager.pageCount}" class="page_btn">尾&nbsp;&nbsp;页</a>
	   		</c:if>
	    </div>
	</c:if>
</div>
</body>
</html>