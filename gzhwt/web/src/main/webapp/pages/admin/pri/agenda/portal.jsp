<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<style>
<!--
.content_table_date span.name {font-size:12px; color:#666;}
.content_table_date span.ctrl {font-size:12px;}
.content_table_date span.ctrl a {font-size:12px;}
.content_table_date tbody tr.even { background:#EBF2F7;}
.content_table_date tbody tr {background:#FFFFFF;}
.content_table_date tbody td {color:#666; vertical-align: top; padding:2px;}
.content_table_date div.text {color:#666; font-size:12px;margin-top:5px;}
-->
</style>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:setLocale value="zh_CN"/>
<fmt:formatDate var="today" value="${now}" pattern="yyyy年M月d日"/>

<div class="w960">
    <div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>

  	<div class="agenda">
       	<ul class="content_tab content_tab_date">
       		<c:forEach var="view" items="${views}" varStatus="status">
       		 <c:if test="${status.count  < 6}">
       		 	<li <c:if test="${fn:startsWith(view.date, today)}">class="act"</c:if>><a class="adate" id="${status.index }" href="javascript://">${fn:substringBefore(view.date, " ") }</a></li>
               </c:if>
				<c:if test="${status.count  == 6}">
                 <li class="more">
                 <ul>
             </c:if>
             <c:if test="${status.count  >= 6}">
             	<li <c:if test="${fn:startsWith(view.date, today)}">class="act"</c:if>><a class="adate" id="${status.index }" href="javascript://">${fn:substringBefore(view.date, " ") }</a></li>
             </c:if>
             <c:if test="${status.count  >= 6 && status.last}">
                 </ul>
                 <a href="javascript://">更 多<span>︾</span></a>
                 </li>
             </c:if>
       		</c:forEach>
           </ul>
   </div>    

   <div class="cont" style="min-height: 200px;">
   	<c:forEach var="view" items="${views}" varStatus="status">
    	<table id="info_${status.index }" class="content_table tableInfo content_table_date" style="${today eq fn:substringBefore(view.date," ") ? '' : 'display:none;'}">
            <tbody>

         	<c:forEach var="meetingagenda" items="${view.agendaViews}" varStatus="status">

         	<c:if test="${status.first}">
         		<tr><td colspan="2"><b>${view.date}</b></td></tr>
         		<tr><td colspan="2"><b>
         			<!--<fmt:parseDate value="${meetingagenda.startTime}" var="sdate" pattern="HH:mm"/>
         			<fmt:formatDate var="ampm_first" value="${sdate}" pattern="a"/>${ampm_first}-->
         			<c:set var="hour" value="${fn:substringBefore(meetingagenda.startTime,':')}"/> 
         			<c:if test="${hour lt '12'}"><c:set var="ampm_first" value="上午"/></c:if>
         			<c:if test="${hour lt '18' && hour ge '12'}"><c:set var="ampm_first" value="下午"/></c:if>
         			<c:if test="${hour ge '18'}"><c:set var="ampm_first" value="晚上"/></c:if>         			
         			${ampm_first}
         		</b></td></tr>
         	</c:if>
         	<c:if test="${!status.first}">
       			<c:set var="hour" value="${fn:substringBefore(meetingagenda.startTime,':')}"/> 
       			<c:if test="${hour lt '12'}"><c:set var="ampm" value="上午"/></c:if>
       			<c:if test="${hour lt '18' && hour ge '12'}"><c:set var="ampm" value="下午"/></c:if>
       			<c:if test="${hour ge '18'}"><c:set var="ampm" value="晚上"/></c:if>
         		<c:if test="${ampm != ampm_first}">
         			<c:set var="ampm_first" >${ampm}</c:set>
	         		<tr><td colspan="2"><b>${ampm}</b></td></tr>
         		</c:if>
         	</c:if>
         	
         	<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
         		<td width="18%">
         			<p><span class="name">${meetingagenda.startTime} 至 ${meetingagenda.endTime}</span></p>
         		</td>
         		<td width="82%">
         		    <c:if test="${not empty meetingagenda.hostName}">
         				<p style="line-height:2em;"><span class="name">发言人:${meetingagenda.hostName }</span></p>
         			</c:if>
         	  		<p style="line-height:2em;"><span class="name">议程：${meetingagenda.topic }</span></p>

         			<c:if test="${not empty meetingagenda.location }">
	                   	<p style="line-height:2em;"><span class="name">地点：${meetingagenda.location }</span></p>
	                </c:if>
	                
	                <c:if test="${not empty meetingagenda.attendeeNames }">
	                   	<p style="line-height:2em;"><span class="name">参议人员：${meetingagenda.attendeeNames }</span></p>
	                </c:if>
	                <c:if test="${not empty meetingagenda.description }">
	                	<p style="line-height:2em;"><span class="name"><div class="text">${meetingagenda.description }</div></span></p>
	                </c:if>         			
	               	<c:if test="${meetingagenda.hasGroup}">
						<p style="text-align:right;"><span class="ctrl">
		                   <a href="${ctx}/portal/pri/agenda/groupDetail.action?meetingId=${meetingagenda.meetingId}&menu_id=${param.menu_id}&agendaId=${meetingagenda.id}" class="group">分组详情</a>
		                </span></p>
		            </c:if>
	               	<c:if test="${!meetingagenda.hasGroup && not empty meetingagenda.groupPlanId}">
						<p style="text-align:right;"><span class="ctrl">
							<a href="${ctx}/portal/pri/agenda/groupPlan.action?groupPlan.id=${meetingagenda.groupPlanId }&meetingId=${meetingagenda.meetingId}&menu_id=${param.menu_id}">分组详情</a>
		                </span></p>
		            </c:if>
           		</td>
           	</tr>
          </c:forEach>
            </tbody>
        </table>
    </c:forEach>
   </div>
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
$(document).ready(function() {
	//导航
	$(".agenda .content_tab li.more a").hover(
	  function () {$(this).parent().find('ul').fadeIn();},
	  function () {$(this).parent().find('ul').hide();}
	);
	
	$(".agenda .content_tab li.more ul").hover(
	  function () {$(this).show();},
	  function () {$(this).hide();}
	);
	
	//内容tab切换act
	$(".content_tab li").not('.more').find('a').click(function () {
		$(".content_tab li").removeClass("act");
		$(this).parent().addClass("act");
		$(".tableInfo").css('display','none');
		var id = $(this).attr('id');
		$('#info_'+id).css('display','');
	});
	
	
	//如果没有默认的ACT样式，自动为第一个添加act class
	if($('.agenda').find(".act").length == 0){
		$('.agenda').find("li").first().addClass("act");
		$('.cont').find('.tableInfo').first().css('display','');
	}
});
</script>

