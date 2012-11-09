<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<div style="font-size: 15px;font-weight: bolder;">${question.question }</div>
<div id="lookVote"><br/>
	<c:forEach items="${list }" var="item" varStatus="sta">
		<c:if test="${not empty item.content }">
		<div style="padding: 5px;">${sta.count }、${item.content }     &nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${item.state==2 }">
			(<span style="color:skyblue">${item.user.name }   </span>)  ${fn:substring(item.answerTime,0,16)}
		</c:if>
		<c:if test="${item.state==1 }">
			(<span style="color:skyblue">匿名</span>)
		</c:if>
		</div>
		</c:if>
	</c:forEach>
</div>
		
