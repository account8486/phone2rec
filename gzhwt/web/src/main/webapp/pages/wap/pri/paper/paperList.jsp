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
			<c:forEach var="paper" items="${list}" varStatus="status">
        	<li>
            	<h5>${paper.title}
            	
            	</h5>
                <p>
                	参与人数：${paper.count }
                </p>
                <p>
                <a href="${ctx}/wap/pri/paper/question_enterExam.action?flag=wap&paperId=${paper.id }&menu_id=${param.menu_id}" >参与调查</a>
                <a href=" ${ctx}/wap/pri/paper/question_findMyAnswerPaper.action?flag=wap&paperId=${paper.id }&menu_id=${param.menu_id}" >我的答卷</a>
              
                </p>
            </li>
			</c:forEach>
        </ul>
    	
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>

