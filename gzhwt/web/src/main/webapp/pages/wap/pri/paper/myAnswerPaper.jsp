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
		
		
		<div style="border:1px solid skyblue;"  align="center">
		
		<div style="text-align: center;color:skyblue;font-weight: bolder;margin: 10px;">
			答卷人：${sessionScope.SESSION_USER.name}		
		</div>
		<c:forEach var="quest" items="${pager.pageRecords}" varStatus="status">
		<div>
			<div style="margin: 10px;color:#CC9900;font-weight: bolder;">${status.count}:${quest.question }</div>
			<!-- 简答题 -->
			<c:if test="${quest.type==3}">
				<div class="quest"  style="padding: 5px;">
					<span class="ipt" style="margin-right: 10px;margin-left: 15px;">
					<textarea rows="2" name="contents" >${myMap[quest.id]}</textarea>
					</span>
			 	</div>
			</c:if>
			<!-- 单选题-->
			<c:if test="${quest.type==1}">
			 <div class="quest">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 5px;">
							<span style="margin-right: 10px;margin-left: 15px;">
							<input type="${quest.type==1?"radio":"checkbox" }" ${myMap[quest.id]==item.id?"checked":"" } name="checks_${quest.id}"/>
							<span>${item.content }</span>
							</span>
						</div>
				</c:forEach>
			 </div>
			 </c:if>
			 
			 <!-- 多选题-->
			<c:if test="${quest.type==2}">
			 <div class="quest">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 5px;">
							<span style="margin-right: 10px;margin-left: 15px;">
							<input type="${quest.type==1?"radio":"checkbox" }" ${myMap[item.id-10000]==item.id?"checked":"" } />
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
		</div>
		<div class="clear"></div>	
    </div>
    
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>

