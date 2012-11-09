<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<c:forEach var="post" items="${list}" varStatus="status">
	<li postId="${post.id }">
            	<div class="t"></div>
                <div class="m">
                    <span class="cont"><a href="javascript:void">  
                    <c:if test="${post.user.meetingMember.memberLevel eq 1}">
                    ${post.posterName }
                   </c:if>
                    <c:if test="${post.user.meetingMember.memberLevel eq 2}">
                                                   参会代表
                    </c:if></a>：${wd:convertToHtmlLine(post.content) }</span>
                    <span class="other">
                    	<s><fmt:formatDate value="${post.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>发表</S>
                        <a href="javascript://">浏览[${post.viewCount }]</a><a href="javascript:void">评论[${post.commentCount }]</a>
                    </span>
                </div>
                <div class="e"></div>                
    </li>
</c:forEach>

