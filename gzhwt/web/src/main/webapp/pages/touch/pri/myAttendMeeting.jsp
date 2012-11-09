<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>贵州移动会务通</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<!--移动设备特性-->
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="format-detection" content="telephone=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="${ctx}/images/touch/apple-touch-icon-precomposed.png"/>
<link rel="apple-touch-startup-image" href="${ctx}/images/touch/apple-touch-startup-ipad.png"/>
<link rel="apple-touch-startup-image" href="${ctx}/images/touch/apple-touch-startup.png"/>
<!-- 主样式表 -->
<link href="${ctx}/css/touch/main.css" rel="stylesheet" type="text/css">
<!-- jQuery 1.7.2 和 jQuery UI 1.8.18 -->
<script type="text/javascript" src="${ctx}/js/touch/jquery-1.7.2.min.js"></script>
<!-- JS -->
<script type="text/javascript" src="${ctx}/js/touch/ready.js"></script>
<script type="text/javascript" src="${ctx}/js/touch//function.js"></script>
</head>
<body>

<header>
	<h3>我的会议列表</h3>
</header>

<div>
    
    <div class="tab_c" style="display:block;">
        <div class="boxa">
            <div class="tabc" style="display:block; margin:0px;">
            	<ul class="cal_list">
            		<c:choose>
					<c:when test="${not empty meetingList}">
					<c:forEach var="meeting" items="${meetingList}">
                	<li>
                    	<a class="enter" href="${ctx}/pages/touch/pri/index.jsp?meetingId=${meeting.id}">
                            <p><span class="name">${meeting.name}</span></p>
                            <p><span class="time">
                            <c:out value="${fn:substring(meeting.topic, 0, 40)}" escapeXml="true" />
			            	<c:if test="${fn:length(meeting.topic)>40}">
			            		<c:out value="..."></c:out>
			            	</c:if>
			            	</span></p>
                        </a>
                        
                        
                       <c:if test="${not empty meeting.meetingAgendas}">
                
                       <p><a href="#" onclick="expandAgenda(${meeting.id})">查看议程</a></p>
                       <div style="display:none;" id="agendaDiv_${meeting.id}">
                       <c:choose>
                       <c:when test="${not empty meeting.meetingAgendas }">
                       <c:forEach var="agenda" items="${meeting.meetingAgendas}">
                           &nbsp;&nbsp;${agenda.topic} <br>
                       </c:forEach>
                       </c:when>
                       <c:otherwise>
                       </c:otherwise>
                       </c:choose>
                       </div>
                       </c:if> 
                       
                    </li>
                    </c:forEach>
                    </c:when>
	            	<c:otherwise>
		        	<li>            	
		                <p>&nbsp;您还没有可以访问的活动</p>
		            </li>
		            	</c:otherwise>
					</c:choose>
                </ul>
            </div>
        </div>
    </div>
</div>

<footer>
    <p class="copyright">
    	版权所有 © 中国移动通信集团贵州有限公司 2012
    </p>
</footer>

<style type="text/css">
	/*这里是页面私有样式*/
	.home_pic { width:300px; height:100px; margin:10px auto;}
	.home_pic img { width:300px; height:100px; }
	.mtbslide-pagination {
	height:28px;
	width:300px;
	margin:0 auto; margin-bottom:10px;
}
.mtbslide-pagination .pg-btn {
	width:58px;
	height:26px;
	line-height:26px;
	overflow:hidden;
	border:1px solid #c4c4c4;
	text-indent:-9999px;
	background:url(${ctx}/images/touch/icon-list-link.png) no-repeat center center;
}
.mtbslide-pagination .pg-btn a {
	display:block;
	width:58px;
	height:26px;
	text-align:center;
}
.mtbslide-pagination .prev {
	float:left;
	-webkit-transform:rotate(180deg);
}
.mtbslide-pagination .next {
	float:right;
}
.mtbslide-pagination ul {
	margin-top:8px;
}
.mtbslide-pagination .trigs {
	float:left;
	width:180px;
	text-align:center;
}
.mtbslide-pagination .trigs li {
	display:inline-block;
	text-indent:-9999px;
	margin-right:10px;
	width:10px;
	height:10px;
	-webkit-border-radius:5px;
	background:#a9a9a9;
}
.mtbslide-pagination .trigs li.cur {
	background:#069;
}
.enter {
	 background: url("${ctx}/images/touch/icon-list-link.png") no-repeat scroll right center transparent;
	 margin-right:10px;
}
</style>

<script>
	/*这里是页面私有脚本*/
	
	function expandAgenda(meetingId){
		$("#agendaDiv_"+meetingId).toggle();
	}
	
</script>

</body>
</html>