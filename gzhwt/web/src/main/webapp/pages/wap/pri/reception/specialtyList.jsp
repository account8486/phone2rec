<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<style type="text/css">
	.non_info {
		padding:10px;
		border: 1px solid #C1C1C1; 
		margin-bottom:30px;
		font-size:16px;
	}
	
	.header_info {
		font-size:16px;
		border:1px solid #C1C1C1;
		margin:20px 0px;
		padding:10px;
		background:#F0F0F0;
	}
	
	.specialty_info {
		border:1px dashed #666;
		margin:20px 0px;
	}
	
	.specialty_info .specialty_image {
		width:180px;
		height:120px;
		float:right;
		margin:10px;
	}
	
	.specialty_info .specialty_name {
		font-weight:bold;
		font-size:20px;
		padding:10px;
	}
	
	.specialty_info .specialty_memo {
		padding:10px;
	}
	
	.clear {
		float: none;
		clear: both;
	}
</style>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">    
		<form id="listUserForm" action="${ctx}/wap/pri/meeting/getMeetingUsers.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			
		</form>
	
		<s:if test="specialty == null || specialty.state == 0">
			<div class="non_info">当前会议尚未发布本地土特产信息。</div>
		</s:if>
		<s:else>
		<ul class="meeting_list">
        	<div class="header_info">${specialty.memo }</div>
            
            <s:iterator var="sp" value="pager.pageRecords">
            <div class="specialty_info">
         		<c:if test="${not empty sp.image}">
         		<img class="specialty_image" src="${serverUrl}${sp.image}"></img>
         		</c:if>
         		<div class="specialty_name"><span>${sp.name }</span></div>
         		<div class="specialty_memo">${sp.memo }</div>
				<div class="clear"></div>
         	</div>
            </s:iterator>
        </ul>
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/reception/specialty_show.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}&menu_name=${param.menu_name}">首页</a>
		   			<a href="${ctx}/wap/pri/reception/specialty_show.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/reception/specialty_show.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">下一页</a>
		   			<a href="${ctx}/wap/pri/reception/specialty_show.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">尾页</a>
		   		</c:if>
		    </div>
		</s:else>    
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>