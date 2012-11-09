<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<c:if test="${not empty errMsg }">
		<div style="color:red;">${errMsg }</div>
	</c:if>
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">  
    <h5>${voteBase.title }</h5>  
		<form action="${ctx}/wap/pri/vote/vote_processVote.action?flag=wap&menu_id=${param.menu_id}" id="processVote" method="post">
			<c:forEach items="${list }" var="item" varStatus="sta">
				<p style="padding: 5px">
				<input type="${voteBase.type==1?"radio":"checkbox" }" ${sta.first==true?"checked":"" } value="${item.id }" name="checks"/>
				<span>${item.content }</span>
				</p>
			</c:forEach>
			<input type="hidden" value="${voteBase.id}" name="voteId"/>
			<input type="hidden" value="${voteBase.meeting.id}" name="meetingId"/>
			<input type="submit"" value="投票"/>
		</form>	
    	<p>
    	<a href="${ctx}/wap/pri/vote/vote_findEnableVote.action?menu_id=${param.menu_id}&meetingId=${voteBase.meeting.id}"  >投票列表</a>
    	
    	</p>
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>
