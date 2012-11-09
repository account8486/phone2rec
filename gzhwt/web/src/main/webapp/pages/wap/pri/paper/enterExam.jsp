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
		<p align="center" style="font-size: 15px;font-weight:bolder;color:#CC9900">标题：${linkPaper.title }</p>
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<div style="border:1px solid skyblue;"  align="center">
		<form id="questForm" action="${ctx}/wap/pri/paper/question_processPager.action"  method="post">
			<input type="hidden" value="${linkPaper.id}" name="paperId"/>
			<input type="hidden" value="wap" name="flag"/>
		<div style="text-align: center;color:#CC9900;font-weight: bolder;margin: 10px;">
		匿名：<input type="radio" name="state" value="1" checked="checked"/>
		<span style="padding-left: 15px;">
		署名：<input type="radio" name="state" value="2"/></span>
		
		</div>
		<c:forEach var="quest" items="${pager.pageRecords}" varStatus="status">
		<div>
			<div style="margin: 10px;color:#CC9900;font-weight: bolder;">${status.count}:${quest.question }</div>
			<!-- 简答题 -->
			<c:if test="${quest.type==3}">
				<div class="quest " type="textarea" style="padding: 5px;">
					<span  style="margin-right: 10px;margin-left: 15px;">
					<textarea rows="2" style="width: 150px;"  name="contents" ></textarea>
					<input type="hidden" value="${quest.id }"  name="quests"/>
					</span>
			 	</div>
			</c:if>
		
		
		
			 <!-- 单选题 -->
			<c:if test="${quest.type==1}">
			 <div class="quest">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 5px;">
							<span style="margin-right: 10px;margin-left: 15px;">
							<input type="radio" value="${item.id }" name="checks_${quest.id}"/>
							<span>${item.content }</span>
							</span>
						</div>
				</c:forEach>
				<input type="hidden" value="${quest.id}"  name="singles"/>
			 </div>
			 </c:if>
			  <!-- 多选题 -->
			 <c:if test="${quest.type==2}">
			 <div class="quest">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 5px;">
							<span style="margin-right: 10px;margin-left: 15px;">
							<input type="checkbox" value="${item.id }" name="checks"/>
							<span>${item.content }</span>
							</span>
						</div>
				</c:forEach>
			 </div>
			 </c:if>
			 
			 
		</div>
		<c:if test="${not status.last}">
		<hr color="silver"  size="1"></hr>
		</c:if>
		</c:forEach>
		<div class="page_form_sub" style="text-align:center;margin: 10px">
       		<input type="submit"" value="提交问卷"/>  
    	</div> 
    	</form>
		</div>
		</c:when>
		</c:choose>
		
		<div class="clear"></div>
    	
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>

