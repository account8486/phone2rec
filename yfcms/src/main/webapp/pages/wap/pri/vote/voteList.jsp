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
		<ul class="meeting_list">
			<c:forEach var="vote" items="${list}" varStatus="status">
        	<li>
            	<h5>${vote.title}
            	
            	</h5>
                <p>
                	参与投票人数：${vote.count }
                </p>
                <p>
                <a href="${ctx}/wap/pri/vote/voteItem_findItemByVoteId.action?flag=wap&voteId=${vote.id }&menu_id=${param.menu_id}" >参与投票</a>
               
                <c:if test="${vote.count>0}">
                <a href="${ctx}/wap/pri/vote/vote_lookVoteResult.action?flag=wap&voteId=${vote.id }&menu_id=${param.menu_id}" >查看结果</a>
               	</c:if>
              
                </p>
            </li>
			</c:forEach>
        </ul>
    	
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>

