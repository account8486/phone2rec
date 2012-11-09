<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
${admin_css}                                   
${jquery_js}                                                       
${admin_js}                   
</head>
<body>
		
<div class="mainbox">
    <div class="page_title">
    	<h3>查看会场建议结果</h3>
	</div>
	<div class="page_tools page_toolbar">
		<a href="${ctx}/admin/pri/paper/paper_findPaperAll.action?meetingId=${meetingId}"  class="btn_common btn_false" style="margin-left:5px;">调查列表</a>
	</div>
		<p align="center" style="font-size: 15px;font-weight:bolder;color:#CC9900">标题：${linkPaper.title }</p>
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<div style="border:1px solid skyblue;width: 90%;margin-left: 30px;"  align="center">
		<c:forEach var="quest" items="${pager.pageRecords}" varStatus="status">
		<div>
			<div style="margin: 10px;color:#CC9900;font-weight: bolder;">${status.count}:${quest.question }</div>
			<!-- 简答题 -->
			<c:if test="${quest.type==3}">
				<div class="quest message_sub" style="padding: 5px;">
					<span class="ipt" style="margin-right: 10px;margin-left: 15px;">
					<textarea rows="2" cols="30">在这里填写内容</textarea>
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
							<input type="${quest.type==1?"radio":"checkbox" }" value="${item.id }" name="checks"/>
							<span>${item.content }</span>
							</span>
						</div>
				</c:forEach>
			 </div>
			 </c:if>
		</div>
		<hr color="silver"  size="1"></hr>
		</c:forEach>
		</div>
		</c:when>
		</c:choose>
		
		<div class="clear"></div>
</div>
<script type="text/javascript">

</script>
</body>
</html>