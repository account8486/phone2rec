<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>
    <div class="article">  
    <h5><span>投票标题：</span><span style="color:blue">${voteBase.title }</span></h5>  
		
		<div id="lookVote">
			<c:forEach var="item" items="${list}" varStatus="status">
				<p style="padding:5px;">
				<span>投票选项${status.index+1}:${item.content }</span>
				<span>(投票人数：${item.count })</span><p>
			</c:forEach>
		</div>
		 
    	<p>
    		<a href="${ctx}/wap/pri/vote/vote_findEnableVote.action?meetingId=${voteBase.meeting.id}" class="vote" >投票列表</a>
    	</p>
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>
