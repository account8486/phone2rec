<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
	
	<%@ include file="/pages/portal/common/header.jsp" %>
	
	<div class="w960">
        <div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
	</div>
	
	<div class="container w960" style="min-height:300px;">
		<form id="mainForm" action="${ctx}/portal/pri/gift/list.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        	<input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			<input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="queryForList" class="btn_common btn_true"/>
		</form>
		
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="gift" items="${pager.pageRecords}" varStatus="status">
			<div class="goods">
				<div class="pic"><a href="${ctx}/portal/pri/gift/detail.action?id=${gift.id}&meetingId=${meetingId}&menu_id=${param.menu_id}"><img src="${serverUrl}${gift.imgUrl }" /></a></div>
				<div class="description">
					<div class="title"><a href="${ctx}/portal/pri/gift/detail.action?id=${gift.id}&meetingId=${meetingId}&menu_id=${param.menu_id}">${fn:substring(gift.name, 0, 36) }</a></div>
					<div class="price"><span class="money">￥<fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/></span></div>
					<div class="comments">${fn:substring(gift.introduction, 0, 36) }</div>
				</div>
			</div>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<tr class="datarow">
                <td colspan="6">&nbsp;&nbsp;暂时没有可以订购的礼品。</td>
            </tr>
		</c:otherwise>
		</c:choose>
		
		<div class="clear"></div>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
	$(document).ready(function(){
		// 查询
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
	});
</script>