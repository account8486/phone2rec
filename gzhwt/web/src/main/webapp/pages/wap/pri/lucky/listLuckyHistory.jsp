<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">    
		<form id="listUserForm" action="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" name="meetingId"  value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table class="content_table">
		      <tr>		      
	            <td >手机号码：
	            	<input type="text" style="width: 100px; " id="mobile" name="mobile"
	                                   value="${mobile}"/>
	                <input style="margin-left:10px;" type="submit" value="搜 索"/>
	            </td>
		      </tr>
		    </table>
		</form>
				
		<c:if test="${not empty pager.pageRecords}">
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
		   		</c:if>
		    </div>
		</c:if>
	
		<ul class="meeting_list">
			<c:forEach var="lucky" items="${pager.pageRecords}" varStatus="status">
        	<li>
            	<h5>${lucky.user.name}
            	
            	</h5>
                <p>
                	手机号：${lucky.user.mobile }
                </p>
                <p style="margin-top: 10px;">
                	奖项：${lucky.lucky.name}
                </p>
                <p style="margin-top: 10px;">
                	奖品：${lucky.lucky.award}
                </p>
            </li>
			</c:forEach>
        </ul>
    	<c:if test="${not empty pager.pageRecords}">
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
		   			<a href="${ctx}/wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
		   		</c:if>
		    </div>
		</c:if>    
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>