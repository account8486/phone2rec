<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>

	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会议议程</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    

    <div class="tab_c" style="display:block;">
    
    <c:forEach var="view" items="${views}" varStatus="status">
   
         <%--每一天 --%>
         <ul class="yicheng_list" id="yicheng_list_${status.count}" <c:if test="${status.count >1 }"> style="display:none" </c:if> >
         	
    <%--切换 --%>
    	<div class="tab" style="display:block;">
    	<div class="time_change">
    		<c:if test="${!status.first}"> 
        	<a href="javascript://" onClick="showPreDiv(${status.count})"  class="prev">上一页</a>
        	</c:if>
           ${view.date}
           	<c:if test="${!status.last}"> 
            <a href="javascript://" onClick="showNxtDiv(${status.count})"  class="next">下一页</a>
            </c:if>
            </div>
    </div>

	<div class="cont">
	
        <c:set var="flagAgenda" value="0"/>
         	<c:forEach var="meetingagenda" items="${view.agendaViews}" varStatus="status">
         		<c:set var="flagAgenda" value="${status}"/>

         		
           		 <c:if test="${status.first}">
         			<c:set var="hour" value="${fn:substringBefore(meetingagenda.startTime,':')}"/> 
         			
         			<c:if test="${hour lt '12'}">
         			<c:set var="ampm_first" value="上午"/>
         			</c:if>
         			
         			<c:if test="${hour lt '18' && hour ge '12'}">
         			<c:set var="ampm_first" value="下午"/>
         			</c:if>
         			
         			<c:if test="${hour ge '18'}">
         			<c:set var="ampm_first" value="晚上"/>
         			</c:if>    
         		 <p class="title">${ampm_first}</p>
         		</c:if>
         	
	         	<c:if test="${!status.first}">
	       			<c:set var="hour" value="${fn:substringBefore(meetingagenda.startTime,':')}"/> 
	       			<c:if test="${hour lt '12'}"><c:set var="ampm" value="上午"/></c:if>
	       			<c:if test="${hour lt '18' && hour ge '12'}"><c:set var="ampm" value="下午"/></c:if>
	       			<c:if test="${hour ge '18'}"><c:set var="ampm" value="晚上"/></c:if>
	         		<c:if test="${ampm != ampm_first}">
	         			<c:set var="ampm_first" >${ampm}</c:set>
		          <p class="title">${ampm}</p>
	         	</c:if>
         		</c:if>
         		
         		<li   <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>  >
                <div  <c:if test="${status.count % 2 eq 0}"> class="time hl"</c:if> <c:if test="${status.count % 2 ne 0}"> class="time"</c:if> >
                    <span class="start">${meetingagenda.startTime }</span>
                    <span class="end">  ${meetingagenda.endTime }  </span>
                </div>
                
                <div class="info">
                    <c:if test="${not empty meetingagenda.hostName}">
         				<p>人员：${meetingagenda.hostName }</p>
         			</c:if>
         			<c:if test="${not empty meetingagenda.location }">
	                  <p>地点：${meetingagenda.location}</p>
	                </c:if>
         	  		<p>议程：${meetingagenda.topic }<p>
	                <c:if test="${not empty meetingagenda.attendeeNames }">
	                <p>参议人员：
	                ${fn:substring(meetingagenda.attendeeNames, 0, fn:length(meetingagenda.attendeeNames)-1)}
	                </p>
	                </c:if>
	                
	               <c:if test="${meetingagenda.hasGroup}">
                     		<span class="ctrl"><a href="${ctx}/touch/pri/agenda/group.action?meetingId=${meetingagenda.meetingId}&menu_id=${param.menu_id}&agendaId=${meetingagenda.id}" class="group"><p style="text-align:right;">分组详情</p></a></span>
                     	</c:if>
               			<c:if test="${!meetingagenda.hasGroup && not empty meetingagenda.groupPlanId}">
               				<span class="ctrl"><a href="${ctx}/touch/pri/agenda/groupPlan.action?groupPlan.id=${meetingagenda.groupPlanId}&meetingId=${meetingagenda.meetingId}&menu_id=${param.menu_id}&agendaId=${meetingagenda.id}" class="group"><p style="text-align:right;">分组详情</p></a></span>
               			</c:if>     
		            
                    </div>
            </li>
            
             </c:forEach>
               <%--每一天 End--%>
             </ul>
    		</c:forEach>
        </div>

<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	.cont { margin:10px auto; clear:both;}
	ul.yicheng_list {}
	ul.yicheng_list p.title { background:url(${ctx}/images/touch/meeting_item_prompt.png) top center repeat-x; height:35px; line-height:35px; text-align:center; color:#fff; font-weight:bold;}
	ul.yicheng_list li { overflow:auto; padding:10px; border-bottom:1px solid #ccc;}
	ul.yicheng_list li.even { background:#f2f2f2;}
	ul.yicheng_list li .time { width:72px; height:76px; background:url(${ctx}/images/touch/meeting_icon_n.png) 0px 0px no-repeat; float:left; margin-right:10px; position:relative;}
	ul.yicheng_list li .time.hl { background:url(${ctx}/images/touch/meeting_icon_a.png) 0px 0px no-repeat;}
	ul.yicheng_list li .time span { text-align:center; width:72px; color:#fff; font-weight:bold; position:absolute;}
	ul.yicheng_list li .time span.start { top:1px; }
	ul.yicheng_list li .time span.end { bottom:7px; }
</style>

<script type="text/javascript">
function showNxtDiv(divNum){
	var nexPageNum=divNum+1;
	
	$("#yicheng_list_"+divNum+"").css("display","none");
	$("#yicheng_list_"+nexPageNum+"").show();
}

function showPreDiv(divNum){
	var prePageNum=divNum-1;
	$("#yicheng_list_"+divNum+"").css("display","none");
	$("#yicheng_list_"+prePageNum+"").show();
}
</script>

</body>
</html>