<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">    
		<form id="listUserForm" action="${ctx}/wap/pri/guest/guest_findAllGuest.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" name="meetingId"  value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table class="content_table">
		      <tr>		      
	            <td >姓名：
	            	<input type="text" style="width: 100px; " id="queryName" name="queryName"
	                                   value="${queryName}"/>
	                <input style="margin-left:10px;" type="submit" value="搜 索"/>
	            </td>
		      </tr>
		    </table>
		</form>
				
		<c:if test="${not empty pager.pageRecords}">
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
		   		</c:if>
		    </div>
		</c:if>
	
		<ul class="meeting_list">
			<c:forEach var="guest" items="${pager.pageRecords}" varStatus="status">
        	<li>
            	<h5>${guest.name}
            	
            	</h5>
                <p>
                	头衔：${guest.rank }
                </p>
                <p style="margin-top: 10px;">
                	简介：${guest.about }
                </p>
            </li>
			</c:forEach>
        </ul>
    	<c:if test="${not empty pager.pageRecords}">
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
		   			<a href="${ctx}/wap/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
		   		</c:if>
		    </div>
		</c:if>    
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>