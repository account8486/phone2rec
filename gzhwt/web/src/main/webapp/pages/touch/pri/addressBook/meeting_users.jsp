<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>

	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">通讯录</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
        <c:choose>
	            <c:when test="${not empty pager.pageRecords}">
                    <c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
    	  <ul class="contact_list">
            <li  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
            	<a href="tel:${user.mobile}" class="detail"></a>
            	<span class="name">${user.name }</span>
            	<span class="tit">手机:
            	<c:choose>
		                     <c:when test="${user.meetingMember.mobileIsDisplay eq 1}">
		                     	${user.mobile }
		                     </c:when>
		                     <c:otherwise>
		                         	未公开
							</c:otherwise>
                </c:choose> 
                </span>
                
            <c:if test="${not empty user.meetingMember.mailbox}"><span class="tit">邮箱:${user.meetingMember.mailbox}</span></c:if>
            <c:if test="${not empty user.meetingMember.bookJob}">   <span class="tit">职位:${user.meetingMember.bookJob } &nbsp;  </span></c:if>
            <c:if test="${not empty user.meetingMember.department}">     <span class="tit">单位:${user.meetingMember.department }</span></c:if>
            <c:if test="${not empty user.meetingMember.city}">    <span class="tit">城市:${user.meetingMember.city }</span></c:if>
            <c:if test="${not empty user.meetingMember.roomNumber}">    <span class="tit">房间号:${user.meetingMember.roomNumber }</span></c:if>
            </li>
           </ul> 
               </c:forEach>
               <c:set var="meetingId" value="${not empty param.meetingId ? param.meetingId : meeting.id}"/>
               	<c:if test="${not empty pager.pageRecords}">
	    <div class="pager">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a class="down" href="${ctx}/touch/pri/member/getMeetingUsers.action?meeting.id=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
	   			<a class="down"  href="${ctx}/touch/pri/member/getMeetingUsers.action?meeting.id=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a class="down" href="${ctx}/touch/pri/member/getMeetingUsers.action?meeting.id=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
	   			<a class="down" href="${ctx}/touch/pri/member/getMeetingUsers.action?meeting.id=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>
	
               
                </c:when>
         </c:choose> 
    </div>
        
 <%@ include file="/pages/touch/common/footer.jsp" %>


<style type="text/css">
	/*这里是页面私有样式*/
	ul.contact_list {}
	ul.contact_list li { position:relative; padding:10px; border-bottom:1px solid #ccc;}
	ul.contact_list li.even { background:#f2f2f2;}
	ul.contact_list li a.detail { position:absolute; width:32px; height:32px; background:url(${ctx}/images/touch/list_call.png) 0px 0px no-repeat; right:10px; top:15px;}
	ul.contact_list li span { display:block;}
	ul.contact_list li span.name { font-weight:bold; font-size:16px; color:#069;}
	ul.contact_list li span.tit { color:#666;}
</style>
