<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta http-equiv="Cache-control" content="no-cache" />
<title>安徽电信会议云平台</title>
<link href="${ctx}/css/wap.css" rel="stylesheet" type="text/css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/css/gift.css" />
<c:set var="meeting_list_url" value="<a href='${ctx}/wap/pri/meeting/getAttendMeetingList.action'>会议列表</a>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<style type="text/css">
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{margin: 0;padding: 0;}table{border-collapse: collapse;border-spacing: 0;}th{text-align: inherit;}iframe{display: block;}fieldset,img{border: 0;}address,caption,cite,code,dfn,em,strong,th,var{font-style: normal;font-weight: normal;}ol,ul{list-style: none;}aption,th{text-align: left;}h1,h2,h3,h4,h5,h6{font-size: 100%;font-weight: normal;}q: before,q: after{content: '';}abbr,acronym{border: 0;font-variant: normal;}sup{vertical-align: text-top;}sub{vertical-align: text-bottom;}input,textarea,select{font-family: inherit;font-size: inherit;font-weight: inherit;}input,textarea,select{*font-size: 100%;}legend{color: #000;}: focus{outline: 0;}html{overflow-y: scroll;overflow-x: auto;color: #000;background: #fff;}
body,div,table,form,p,select,input,textarea,option,a { font-family: Verdana, Arial, sans-serif; line-height: 1.5; font-size: 20px; color: #1480b5;}
body { text-align:center; background:#fff;}
div,table { text-align:left;}

a { text-decoration:none;}
a:hover { text-decoration:none; color:#39f;}

.huiyi_detail {}
.huiyi_detail h3 { background:url(../../images/cindex/title_bg_2.jpg) top center repeat-x; height:54px; text-align:center;}
.huiyi_detail h3 div.b2 {  background:url(../../images/cindex/title_bg.jpg) top center no-repeat; display:block; height:54px; line-height:54px; font-weight:bold; font-size:22px; text-align:center; overflow:hidden; margin:0 auto;}
.huiyi_detail .time_bg { background:url(../../images/cindex/time_bg.jpg) top center repeat-x; margin:10px;}
.huiyi_detail .time_bg_l {  background:url(../../images/cindex/time_bg_l.jpg) top left no-repeat; height:105px; overflow:hidden;}
.huiyi_detail .time_bg_r {  background:url(../../images/cindex/time_bg_r.jpg) top right no-repeat; height:105px; overflow:hidden; text-align:center; padding-top:15px;}
.huiyi_detail .time_bg_r p { padding-left:30px; height:26px; overflow:hidden; margin:5px 10px; text-align:left; display:inline-block;}
.huiyi_detail .time_bg_r .start { background:url(../../images/cindex/time_ico.png) 0px 0px no-repeat;}
.huiyi_detail .time_bg_r .end { background:url(../../images/cindex/time_ico.png) 0px -26px no-repeat;}
.huiyi_detail hr { margin:0 10px 5px; clear:both; }
.huiyi_detail .cont { margin:0px 10px; color:#333; line-height:2; clear:both;}

.local_area { margin:0px 10px 5px;}
.local_area .label { width:50px; vertical-align:top;}
.local_area .detail { }

.demo_table {}
.demo_table td { text-align:center; line-height:54px; font-size:22px; font-weight:bolder; text-shadow:0px 1px 0px #fff;}

.pagination a
{
	font-size:20px;
}
.comments
{
	font-size:17px;
	color:black;
}

.comments a
{
	font-size:20px;
	color:black;
}

</style>


</head>
<body>
<div class="main" >
	
	<c:if test="${not empty pager.pageRecords &&(pager.hasPreviousPage || pager.hasNextPage)}">
	    <div class="page_bg">
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=1" class="page_btn">首&nbsp;&nbsp;页</a>
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=${pager.currentPage-1}" class="page_btn">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=${pager.currentPage+1}" class="page_btn">下一页</a>
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=${pager.pageCount}" class="page_btn">尾&nbsp;&nbsp;页</a>
	   		</c:if>
	    </div>
	</c:if>
	
	<div>
		<form id="mainForm" action="${ctx}/client/pri/journey/getJourneyList.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        	<input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			<input type="hidden" id="queryForList" class="btn_common btn_true"/>
		</form>
		
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="journeyView" items="${pager.pageRecords}" varStatus="status">
			<div>
			
					<div class="detail">行程名称：${journeyView.name }</div>
		  			 <hr size="1" noshade>
					<div class="comments">司机：${journeyView.driverName }</div>
					<div class="comments">车辆：${journeyView.vehicleName }</div>
					<div class="comments">开始时间：${fn:substring(journeyView.startTime,0,16)}</div>
					<div class="comments">开始地点：${journeyView.departure} </div>
					<div class="comments">结束时间：${fn:substring(journeyView.startTime,0,16)} </div>
					<div class="comments">结束地点：${journeyView.destination} </div>
					<div class="comments">人员:${journeyView.journeyMembers} </div>
			</div>
			<br/>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<tr class="second_title">
                <td colspan="6">&nbsp;&nbsp;暂时没有车辆安排。</td>
            </tr>
		</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${not empty pager.pageRecords &&(pager.hasPreviousPage || pager.hasNextPage)}">
	   	<div class="page_bg">

	   	 
	   		<c:if test="${pager.hasPreviousPage}">
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=1" class="page_btn">首&nbsp;&nbsp;页</a>
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=${pager.currentPage-1}" class="page_btn">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=${pager.currentPage+1}" class="page_btn">下一页</a>
	   			<a href="${ctx}/client/pri/journey/getJourneyList.action?currentPage=${pager.pageCount}" class="page_btn">尾&nbsp;&nbsp;页</a>
	   		</c:if>
	    </div>
	</c:if>
</div>
</body>
</html>