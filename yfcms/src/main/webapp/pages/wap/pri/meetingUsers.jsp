<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">    
		<form id="listUserForm" action="${ctx}/wap/pri/meeting/getMeetingUsers.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meeting.id" name="meeting.id" value="${meeting.id}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table class="content_table">
		      <tr>
		        <td>
		        	手机：
		        	<input type="text" style="width: 100px; " id="user_mobile" name="mobile" value="${mobile}"/>
	            </td>		       
		      </tr>
		      <tr>		      
	            <td >姓名：
	            	<input type="text" style="width: 100px; " id="user_name" name="username"
	                                   value="${username}"/>
	            </td>
		      </tr>
		    </table>
		    <div><input style="margin-left:20px;" type="submit" value="搜 索"/></div>
		</form>
				
		<c:if test="${not empty pager.pageRecords}">
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=1&menu_id=${param.menu_id}">首页</a>
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
		   		</c:if>
		    </div>
		</c:if>
	
		<ul class="meeting_list">
			<c:forEach var="member" items="${pager.pageRecords}" varStatus="status">
        	<li>
            	<h5>${member.name}
            	
            	</h5>
                <p>
                	手机：<c:choose>
		                     <c:when test="${member.meetingMember.mobileIsDisplay eq 1}">
		                     	${member.mobile }
		                     </c:when>
		                     <c:otherwise>
		                         	未公开
							</c:otherwise>
                    	</c:choose>
                </p>
                <p>
                	职位：<c:choose>
		                     <c:when test="${member.meetingMember.jobIsDisplay eq 1}">
		                     	${member.meetingMember.bookJob }
		                     </c:when>
		                     <c:otherwise>
		                         	未公开
							</c:otherwise>
                    	</c:choose>
                </p>
                <p>单位：${member.meetingMember.department}</p>
                <p >房间号：
                    <c:choose>
                        <c:when test="${member.meetingMember.roomNumberIsDisplay eq 1}">
                        	${member.meetingMember.roomNumber }
                        </c:when>
                        <c:otherwise>
                            	未公开
						</c:otherwise>
                    </c:choose>
                </p>
                <p>${member.meetingMember.mailbox}</p>
                <p><a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meeting.id}&selectuser=${member.id}&menu_id=${param.menu_id}" >发送私信</a></p>
            </li>
			</c:forEach>
        </ul>
    	<c:if test="${not empty pager.pageRecords}">
		    <div class="article pagination">
		   		<c:if test="${pager.hasPreviousPage}">
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=1&menu_id=${param.menu_id}">首页</a>
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
		   		</c:if>
		   		<c:if test="${pager.hasNextPage}">
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
		   			<a href="${ctx}/wap/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
		   		</c:if>
		    </div>
		</c:if>    
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>