<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会务通平台</title>
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	${main_css}
	${style_css}                                   
	${jquery_js}
	${jquery_form_js}
	${util_js}
	<style type="text/css">
		.message_sub { display:block; height:70px;}
		.message_sub span.ipt { width:85%; height:70px; overflow:hidden; float:left;}
		.message_sub span.focus { background-position:0px 0px;}
		.message_sub span.ipt textarea { resize: none; margin:5px 10px; border:0px; width:80%; height:60px; border: 1px solid #CCCCCC; color:#ccc; font-size:14px;}
		.message_sub span.focus textarea { color:#333;}
	</style>
</head>
<body>
	
	<c:if test="${empty param.from}">
	<%@ include file="/pages/portal/common/header.jsp" %>
	</c:if>
	
	<div class="w960">
        <div class="cbox">
        	<div class="title">
        	<h5 id="caption">会场建议
        	</h5>
        	</div>
        </div>
	</div>
	
	<div class="container w960" style="min-height:300px;">
		
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
				<div class="quest message_sub" type="textarea" style="padding: 5px;">
					<span class="ipt" style="margin-right: 10px;margin-left: 15px;">
					<textarea rows="2" cols="30" name="contents" >${myMap[quest.id]}</textarea>
					</span>
			 	</div>
			</c:if>
			<!-- 单选题-->
			<c:if test="${quest.type==1}">
			 <div class="quest">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 5px;">
							<span style="margin-right: 10px;margin-left: 15px;">
							<input type="radio" ${myMap[quest.id]==item.id?"checked":"" } name="checks_${quest.id}"/>
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
<%@ include file="/pages/portal/common/footer.html" %>
</body>
</html>