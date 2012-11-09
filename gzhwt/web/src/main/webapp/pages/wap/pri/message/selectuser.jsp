<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
   	<!-- Bread crumbs -->
   	<div class="path">${meeting_list_url} > ${param.menu_name}  > 选择收信人 </div>
   	<div class="article">
		<form id="listUserForm" action="${ctx}/wap/pri/message/selectUser.action">
			<div class="message_sub">
		  	    <span class="search">
		  	    	<span class="phone">手机号码：</span><input type="text" id="user_mobile" name="mobile" value="${mobile}"/>                      
	            	<span class="name">姓 名：</span><input type="text" id="user_name" name="username" value="${username}"/>
		  	    	<input type="submit" value="查询"/>
		  	    	<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}">返回</a>
		            <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
		            <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
		  	    </span>
		    </div>
		</form>
   	</div>
   	
   	<!-- pagination -->
   	<c:if test="${not empty pager.pageRecords}">
	    <div class="article pagination">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/wap/pri/message/selectUser.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
	   			<a href="${ctx}/wap/pri/message/selectUser.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/wap/pri/message/selectUser.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
	   			<a href="${ctx}/wap/pri/message/selectUser.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>

    <!-- addressbox -->
    <div class="article">
	<c:choose>
	<c:when test="${not empty pager.pageRecords}">
	<ul class="message_list">
	<c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
		<li <c:if test="${status.count % 2 eq 0}"> class="odd"</c:if>>
           	<p style="text-align:center;">
           		<span class="ctrl">
           			<a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&selectuser=${user.id}&menu_id=${param.menu_id}">${user.name}</a>
           		</span>
                <span class="name"><c:if test="${not empty user.meetingMember.job && user.meetingMember.jobIsDisplay eq 1}">${user.meetingMember.job}</c:if></span>
                <span class="name">手机：<c:choose><c:when test="${user.meetingMember.mobileIsDisplay eq 1}">${user.mobile }</c:when><c:otherwise>未公开</c:otherwise></c:choose></span>
            </p>
       </li>		
	</c:forEach>
	</ul>
	</c:when>
	<c:otherwise>
		<span class="content">${param.menu_name}没有内容.</span>
	</c:otherwise>	
	</c:choose>
	</div>

	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>