<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>


<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">    
		<form id="listForm" action="${ctx}/wap/pri/news/show.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			
		</form>
	
		<s:if test="pager.total == 0">
            <div class="non_info">当前会议尚未发布新闻信息。</div>
        </s:if>
		<s:else>
		<ul class="meeting_list">
            <s:iterator var="entity" value="pager.pageRecords">
            <li>
            	<a href="${ctx }/wap/pri/news/detail.action?meetingId=${meetingId}&id=${entity.id}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">${wd:limit(entity.title,30) }</a>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<span style="font-style: italic;"><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
            </li>
            </s:iterator>
        </ul>
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/news/show.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}&menu_name=${param.menu_name}">首页</a>
		   			<a href="${ctx}/wap/pri/news/show.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/news/show.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">下一页</a>
		   			<a href="${ctx}/wap/pri/news/show.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">尾页</a>
		   		</c:if>
		    </div>
		</s:else>    
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>