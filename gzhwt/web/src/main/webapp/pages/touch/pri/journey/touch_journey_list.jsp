<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>

<style type="text/css">
	/*这里是页面私有样式*/
	ul.contact_list {}
	ul.contact_list li { position:relative; padding:10px; border-bottom:1px solid #ccc;}
	ul.contact_list li.even { background:#f2f2f2;}
	ul.contact_list li a.detail { position:absolute; width:32px; height:32px; background:url(res/list_call.png) 0px 0px no-repeat; right:10px; top:15px;}
	ul.contact_list li span { display:block;}
	ul.contact_list li span.name { font-weight:bold; font-size:16px; color:#069;}
	ul.contact_list li span.tit { color:#666;}
</style>


	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">行程安排</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
      <div class="tab_c" style="display:block;">
      
	<div>
		<form id="mainForm" action="${ctx}/touch/pri/journey/getJourneyList.action">
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        	<input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			<input type="hidden" id="queryForList" class="btn_common btn_true"/>
		</form>
		
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="journeyView" items="${pager.pageRecords}" varStatus="status">
			<div>
			
					<div style="font-size:17px;color:black;">行程名称：${journeyView.name}</div>
		  			 <hr size="1" noshade>
					<div class="comments">司机：${journeyView.driverName }</div>
					<div class="comments">车辆：${journeyView.vehicleName }</div>
					<div class="comments">开始时间：${fn:substring(journeyView.startTime,0,16)}</div>
					<div class="comments">开始地点：${journeyView.departure} </div>
					<div class="comments">结束时间：${fn:substring(journeyView.endTime,0,16)} </div>
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
	   			<a href="${ctx}/touch/pri/journey/getJourneyList.action?meetingId=${meetingId}&currentPage=1" class="page_btn">首&nbsp;&nbsp;页</a>
	   			<a href="${ctx}/touch/pri/journey/getJourneyList.action?meetingId=${meetingId}&currentPage=${pager.currentPage-1}" class="page_btn">上一页</a>
	   		</c:if>
	   		<c:if test="${pager.hasNextPage}">
	   			<a href="${ctx}/touch/pri/journey/getJourneyList.action?meetingId=${meetingId}&currentPage=${pager.currentPage+1}" class="page_btn">下一页</a>
	   			<a href="${ctx}/touch/pri/journey/getJourneyList.action?meetingId=${meetingId}&currentPage=${pager.pageCount}" class="page_btn">尾&nbsp;&nbsp;页</a>
	   		</c:if>
	    </div>
	</c:if>

	</div>
	
	<%@ include file="/pages/touch/common/footer.jsp" %>