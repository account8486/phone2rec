<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > ${param.menu_name}
    </div>

	<form id="loginForm" action="${ctx}/wap/pri/interaction/postAdd.action" method="post">
		<div class="message_sub">
			<p style="color:#999;font-size:12px;">最多可以输入140个字符</p>
	  	    <span class="ipt">
	  	    	<textarea name="content" id="content" rows="3" cols="30"></textarea>
	  	    </span>
	    </div>
		<div class="message_sub">
	  	    <span class="ipt">
	  	    	<input type="submit" value="发布信息"/>
	            <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
	            <input type="hidden" name="meetingId" id="meetingId" value="${meetingId}"/>
	  	    </span>
	    </div>	    
    </form>

	<c:if test="${not empty pager.pageRecords}">
	    <div class="article pagination">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/wap/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=1&menu_id=${param.menu_id}">首页</a>
	   			<a href="${ctx}/wap/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/wap/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
	   			<a href="${ctx}/wap/pri/interaction/postView.action?meetingId=${meetingId}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>
	
	<div class="article">
	  	<c:choose>
	        <c:when test="${not empty pager.pageRecords}">
	        	<ul class="message_list">
	            <c:forEach var="meetingpost" items="${pager.pageRecords}" varStatus="status">
				<li <c:if test="${status.count % 2 eq 0}"> class="odd"</c:if>>
            	<p>
                    <span class="name">
                    <c:if test="${not empty meetingpost.user}"><a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&selectuser=${meetingpost.posterId}">
                    </c:if>${meetingpost.posterName }<c:if test="${not empty meetingpost.user}"></a></c:if><c:if test="${not empty meetingpost.posterJob}">(${meetingpost.posterJob})</c:if> ：
                    </span>
                    <span class="content">${meetingpost.content}</span>
                    <c:if test="${not empty meetingpost.contentImg}">
						<div class="contentImg"><img src="${meetingpost.contentImg}"/></div>
					</c:if>
                </p>
                <p>
                	<span class="time"><fmt:formatDate value="${meetingpost.createTime }" type="both" pattern="yyyy年MM月d日 HH:mm:ss"/></span>
                	<span class="comment"> | 浏览(${meetingpost.viewCount }) | 评论(${meetingpost.commentCount }) | </span>
					<c:if test="${not empty meetingpost.videourl}">
						<span class="ctrl"><a href="${ctx}/wap/pri/interaction/downloadVideo.action?link=${meetingpost.videourl}" target="_blank">视频下载</a> | </span>
					</c:if>
                    <span class="ctrl"><a href="${ctx}/wap/pri/interaction/postDetail.action?meetingId=${meetingId}&postId=${meetingpost.id }&menu_id=${param.menu_id}">详情</a></span>
                </p>
            	</li>
	            </c:forEach>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	           <span>该会议没有互动交流信息.</span>
	        </c:otherwise>
	    </c:choose>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>