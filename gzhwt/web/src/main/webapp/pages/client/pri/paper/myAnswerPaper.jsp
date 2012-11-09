<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>会务通平台</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <!--浏览器特性-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--多终端icon-->
    <link href="favicon.ico" type="image/x-icon" rel="Bookmark"/>
    <link href="favicon.ico" type="image/x-icon" rel="shortcut icon"/>
    <link href="res/logo_apple.png" rel="apple-touch-icon"/>
    ${main_css}
    ${jquery_js }
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
 	 
</head>
<body>
          <div class="cont" style="min-height: 200px;">
            <div style="margin: 5px;">
						
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
					<textarea rows="2" style="width: 90%;height: 75px;" name="contents" >${myMap[quest.id]}</textarea>
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
      </div>
</body>
</html>