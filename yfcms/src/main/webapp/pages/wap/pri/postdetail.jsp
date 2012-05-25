<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>

<div class="main">
	<div class="path">
    	${meeting_list_url} > <a href="${ctx}/wap/pri/interaction/postView.action?meetingId=${post.meetingId}&menu_id=${param.menu_id}">互动交流</a> > 评论
    </div>
    
    <div class="article">
       	<ul class="message_list">
			<li class="odd">
            	<p>
                    <span class="name">${post.posterName }<c:if test="${not empty meetingpost.posterJob}">${post.posterJob}</c:if> ：</span>
                    <span class="content">${post.content}</span>
                    <c:if test="${not empty post.contentImg}">
						<div class="contentImg"><img src="${post.contentImg}"/></div>
					</c:if>
                </p>
                <p>
                	<span class="time"><fmt:formatDate value="${post.createTime }" type="both" pattern="yyyy年MM月d日 HH:mm:ss"/></span>
                	<span class="comment"> | 浏览(${post.viewCount }) | 评论(${post.commentCount }) | </span>
                    <span class="ctrl"><c:if test="${not empty post.user}"><a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${post.meetingId}&selectuser=${post.posterId}">私信</a> | </c:if><a href="${ctx}/wap/pri/interaction/postView.action?meetingId=${post.meetingId}&menu_id=${param.menu_id}">返回</a></span>
                </p>
           	</li>
       	</ul>
	</div>

	<form id="loginForm" action="${ctx}/wap/pri/interaction/commentAdd.action" method="post">
		<div class="message_sub">
			<p style="color:#999;font-size:12px;">最多可以输入140个字符</p>
	  	    <span class="ipt">
	  	    	<textarea name="content" id="content" rows="3" cols="30"></textarea>
	  	    </span>
	    </div>
		<div class="message_sub">
	  	    <span class="ipt">
	  	    	<input type="submit" value="发表评论"/>
	            <input type="hidden" name="meetingId" id="meetingId" value="${post.meetingId}"/>
	            <input type="hidden" name="postId" id="postId" value="${post.id}"/>
	            <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
	  	    </span>
	    </div>	    
    </form>

	<c:if test="${not empty pager.pageRecords}">
	    <div class="article pagination">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/wap/pri/interaction/postDetail.action?postId=${post.id}&currentPage=1&menu_id=${param.menu_id}">首页</a>
	   			<a href="${ctx}/wap/pri/interaction/postDetail.action?postId=${post.id}&currentPage=${pager.currentPage-1}&menu_id=${param.menu_id}">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/wap/pri/interaction/postDetail.action?postId=${post.id}&currentPage=${pager.currentPage+1}&menu_id=${param.menu_id}">下一页</a>
	   			<a href="${ctx}/wap/pri/interaction/postDetail.action?postId=${post.id}&currentPage=${pager.pageCount}&menu_id=${param.menu_id}">尾页</a>
	   		</c:if>
	    </div>
	</c:if>
	
	<div class="article">
	  	<c:choose>
	        <c:when test="${not empty pager.pageRecords}">
	        	<ul class="message_list">
	            <c:forEach var="meetingcomment" items="${pager.pageRecords}" varStatus="status">
				<li <c:if test="${status.count % 2 eq 0}"> class="odd"</c:if>>
            	<p>
                    <span class="name">
                    <c:if test="${not empty meetingcomment.user}"><a href="${ctx}/wap/pri/message/messagebox.action?meetingId=${meetingId}&selectuser=${meetingcomment.creator}"></c:if>
                    	${meetingcomment.creatorName }
                    <c:if test="${not empty meetingcomment.user}"></a></c:if>
                    <c:if test="${not empty meetingcomment.creatorJob}">${meetingcomment.creatorJob}</c:if> ：
                    </span>
                    <span class="content">${meetingcomment.content}</span>
                </p>
                <p>
                	<span class="time"><fmt:formatDate value="${meetingcomment.time }" type="both" pattern="yyyy年MM月d日 HH:mm:ss"/></span>
                </p>
            	</li>
	            </c:forEach>
	           	</ul>
	        </c:when>
	        <c:otherwise>
	           <span>该信息还没有评论.</span>
	        </c:otherwise>
	    </c:choose>
	</div>
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>