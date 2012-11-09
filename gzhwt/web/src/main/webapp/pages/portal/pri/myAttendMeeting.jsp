<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>会务通</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<!--浏览器特性-->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<!--多终端icon-->
	<link href="favicon.ico" type="image/x-icon" rel="Bookmark" />
	<link href="favicon.ico" type="image/x-icon" rel="shortcut icon" />
	<link href="res/logo_apple.png" rel="apple-touch-icon" />
	${main_css}
	${style_css}
    <link href="${ctx}/css/layout.css" rel="stylesheet" type="text/css" />
    ${jquery_js}  
    ${jmpopups_js}      
    ${util_js}    
    <script type="text/javascript" src="${ctx}/js/portal.js"></script>
    
	<!-- IE识别HTML5标签 -->
	<!--[if IE]>
	<script src="${ctx}/js/html5.js"></script>
	<![endif]-->
	<!-- IE6 PNGfix -->
	<!--[if IE 6]>
	<script src="${ctx}/js/iepng.js" mce_src="${ctx}/js/DD_belatedPNG.js"></script>
	<script type="text/javascript">
		DD_belatedPNG.fix('.iepng');   /* 将 .iepng 改成你应用了透明PNG的CSS选择器 */
	</script>
	<![endif]-->   
	<!--[if IE 6]>
	<style type="text/css">
		#wrap {display:table;height:100%}
	</style>
	<![endif]-->
</head>
<body>
<div id="wrap">
	<div id="main" class="clearfix">

	<div class="header">
	    <div class="w960">
	    	<div style="float: left; width: 110px; margin-top: 20px;"></div>
	    	<ul class="title">
            <li style="margin-top:5px;padding-left:110px;"><h3>会务通平台</h3></li>
        </ul>
	        <div class="detail"></div>
	    </div>
	</div>
	
	<div class="nav">
		<div class="w960">
	    	<ul id="menu" class="big" >
	    		<li class="act"  ><a href="javascript://">我的会议</a></li>
	        </ul>
	        <ul class="small">
	            <li id="exit"><a href="javascript://" class="i06 iepng">退出系统</a></li>
	        </ul>
	    </div>
	</div>
	
	<div class="w960">

	    	<div class="cbox">
	        	<div class="title" ><h5 id="caption">${meeting.name}</h5></div>
	            <div class="cont">          
	            
	            <table class="content_table">
	                <thead>
	                   <tr >	                   
	                   	<th width="80">编号</th>
	                    <th width="200">名称</th>
	                    <th>主题</th>
	                    <th width="180">地点</th>
	                    <th width="120">开始时间</th>
	                    <th width="60" colspan="1">操作</th>
	                </tr>
	                </thead>                
	             	<tbody>
	                <c:choose>
	                    <c:when test="${not empty meetingList}">
	                    
	                        <c:forEach var="meeting" items="${meetingList}" varStatus="status">
	                        <c:if test="${status.count==1&&status.last}">
	                        	<c:set var="enterFlag" value="true" />
	                        	<c:set var="uniqueMeeting" value="${meeting.id}" />
							</c:if>
	                            <tr>
	                            
	                                <td align="left">${meeting.id}</td>
	                                <td align="left"><a href="#" onclick="forwardReq('${meeting.id}');">${meeting.name}</a></td>
	                                <td align="left">
	                                	<span class="wordbreak">
	                                		<c:out value="${fn:substring(meeting.topic, 0, 100)}" escapeXml="true" />
	                                		<c:if test="${fn:length(meeting.topic)>100}">
							            		<c:out value="..."></c:out>
							            	</c:if>
	                                	</span>
	                                </td>
	                                <td align="left">${meeting.location}</td>
	                                <td align="left"><fmt:formatDate value="${meeting.startTime}"
	                                                                 type="both"
	                                                                 pattern="yyyy年MM月d日"/></td>
	                                <td align="center"><a class="btn_blue" href="#" onclick="forwardReq('${meeting.id}');">进&nbsp;&nbsp;&nbsp;入</a></td>
	                               
	                            </tr>
	                        </c:forEach>
	                    </c:when>
	                    <c:otherwise>
	                        <tr class="datarow">
	                            <td colspan="10" align="center">
	                               您当前没有可以访问的活动列表.
	                            </td>
	                        </tr>
	                    </c:otherwise>
	                </c:choose>
	                </tbody>
	            </table>
		
	            </div>
	        </div>     
	       	    
	</div>
	</div>

<%@ include file="/pages/portal/common/footer.html" %>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		//alert("${enterFlag}");
		//若只有一条会议，则直接进入
		if("true"=="${enterFlag}"){
			forwardReq("${uniqueMeeting}");
		}
		$("#exit").bind("click", function(){
			window.location="${ctx}/portal/pri/base/logout.action";
			return false;
		});
	});

    function query(){
		$('#listMeetingForm').submit();
	}

	function forwardReq(meetingId){
		//window.location.href = url;
		showLoading("正在进入，请稍候...");
		window.location.href = "${ctx}/portal/pri/meeting/getMeetingById.action?returnType=portal&from=portal&meeting.id="+meetingId;
	}

</script>
</body>
</html>