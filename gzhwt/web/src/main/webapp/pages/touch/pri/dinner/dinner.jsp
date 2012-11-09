<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">餐饮安排</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	<div class="time_change">
    		<c:if test="${preIdx>=0}">
        		<a href="${ctx}/touch/pri/meeting/showDinner.action?meetingId=${meetingId}&index=${preIdx}"  class="prev">上一页</a>
        	</c:if>
            ${view.date}
            <c:if test="${nextIdx<lastIdx}">
            	<a href="${ctx}/touch/pri/meeting/showDinner.action?meetingId=${meetingId}&index=${nextIdx}"  class="next">下一页</a>
            </c:if>
        </div>
        <div class="cont">
        	<c:choose>
	        <c:when test="${not empty view.info}">
        	<ul class="yc_list">
        		<c:forEach var="info" items="${view.info}" varStatus="status">
            	<li class="${'1' eq info.scetion ? 't01': '2' eq info.scetion ? 't02':'t03' }">
                	时间：${info.time }<br>
					地点：${info.address}<br>
					类型：${info.type }<br>
					桌位：
					<c:if test="${'自选座位' eq info.dinnerTable or '指定座位' eq info.dinnerTable}">
	                	<c:if test="${empty info.groupPlanId || info.groupPlanId < 1}">${info.dinnerTable}</c:if>
                          	<c:if test="${not empty info.groupPlanId && info.groupPlanId > 0}">
                          		<a href="${ctx}/touch/pri/meeting/showAllGroupInfo.action?groupPlan.id=${info.groupPlanId }&meetingId=${meetingId}&menu_id=${param.menu_id}" >${info.dinnerTable}</a>
                          	</c:if>
	                </c:if>
	                <c:if test="${'自选座位' ne info.dinnerTable and '指定座位' ne info.dinnerTable}"><a href="${ctx}/touch/pri/meeting/showGroupInfo.action?meetingId=${meetingId}&dinner.id=${info.dinnerId }&menu_id=${param.menu_id}">${info.dinnerTable}</a></c:if>
                </li>
                </c:forEach>
            </ul>
            </c:when>
	        <c:otherwise>
	        	<ul class="yc_list">
            	<li>
                	<br/>没有餐饮信息。
                </li>
            	</ul>
	        </c:otherwise>
	    </c:choose>
        </div>
    </div>
    
<%@ include file="/pages/touch/common/footer.jsp" %>

<style type="text/css">
	/*这里是页面私有样式*/
	ul.yc_list {}
	ul.yc_list li { height:100px; line-height:23px; padding-left:60px; border-bottom:1px solid #ccc;}
	ul.yc_list li.t01 { background:#f2f2f2 url(${ctx}/images/touch/yc01.png) 10px 0px no-repeat;}
	ul.yc_list li.t02 { background:#fff url(${ctx}/images/touch//yc02.png) 10px 0px no-repeat;}
	ul.yc_list li.t03 { background:#f2f2f2 url(${ctx}/images/touch//yc03.png) 10px 0px no-repeat;}
</style>

<script>
	/*这里是页面私有脚本*/
</script>