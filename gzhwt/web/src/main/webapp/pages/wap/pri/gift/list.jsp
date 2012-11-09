<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<div class="main">
	<div class="path">
     ${meeting_list_url} > ${param.menu_name}
    </div>
	
	<c:if test="${not empty pager.pageRecords}">
	    <div class="article pagination">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=1">首页</a>
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=${pager.currentPage-1}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=${pager.currentPage+1}">下一页</a>
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=${pager.pageCount}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>
	
	<div class="path">
		<form id="mainForm" action="${ctx}/wap/pri/gift/list.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        	<input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			<input type="hidden" id="queryForList" class="btn_common btn_true"/>
		</form>
		
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="gift" items="${pager.pageRecords}" varStatus="status">
			<div>
				<div class="pic"><a href="${ctx}/wap/pri/gift/detail.action?meetingId=${meetingId }&id=${gift.id}"><img width="180px" height="177px" src="${serverUrl}${gift.imgUrl }" /></a></div>
				<div class="description">
					<div class="comments"><a href="${ctx}/wap/pri/gift/detail.action?meetingId=${meetingId }&id=${gift.id}">${fn:substring(gift.name, 0, 36) }</a></div>
					<div class="price"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
					<div class="comments">${fn:substring(gift.introduction, 0, 36) }</div>
				</div>
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
	<c:if test="${not empty pager.pageRecords}">
	    <div class="article pagination">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=1">首页</a>
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=${pager.currentPage-1}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=${pager.currentPage+1}">下一页</a>
	   			<a href="${ctx}/wap/pri/gift/list.action?meetingId=${meetingId }&currentPage=${pager.pageCount}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>