<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="/pages/portal/common/header.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="today" value="${now}" pattern="yyyy年M月d日"/>

       <div class="w960">
           <div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
            
            <div class="agenda">
	          	<ul class="content_tab content_tab_date">
	          		<c:forEach var="dinnerInfo" items="${dinnerInfoList}" varStatus="status">
	          		 <c:if test="${status.count  < 6}">
	          		 
	          		 	<li class="${fn:startsWith(dinnerInfo.date, today) ? 'act' : ''}"><a class="adate" id="${status.index }"  href="javascript://">
	                        	<c:set var="dinnerDate" value='${dinnerInfo.date }'/>
	                        	${fn:substringBefore(dinnerDate, " ") }
	                     </a></li>
	          		 
	                  </c:if>
					<c:if test="${status.count  == 6}">
	                    <li class="more">
	                    <ul>
	                </c:if>
	                <c:if test="${status.count  >= 6}">
	                	<li class="${fn:startsWith(dinnerInfo.date, today) ? 'act' : ''}"><a class="adate" id="${status.index }"  href="javascript://">
	                        	<c:set var="dinnerDate" value='${dinnerInfo.date }'/>
	                        	${fn:substringBefore(dinnerDate, " ") }
	                     </a></li>
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
            	<c:forEach var="dinnerInfo" items="${dinnerInfoList}" varStatus="status">
	            	<table id="info_${status.index }" class="content_table tableInfo" style="${fn:startsWith(dinnerInfo.date, today) ? '' : 'display:none'}">
	                	<thead>
	                		<tr>
			        			<th colspan=6>${dinnerInfo.date}</th>
			        		</tr>
	                    	<tr>
	                            <!-- <th>日期</th>  -->
	                            <th width="30">时段</th>
	                            <th >时间</th>
	                            <th >地点</th>
	                            <th >类型</th>
	                            <th >桌位</th>
	                            <th >备注</th>
	                        </tr>
	                    </thead>
	                    <tbody>
			               	<c:forEach var="info" items="${dinnerInfo.info}" >
	                        <tr>
	                            <!-- <td>${info.dinnerDate }</td>  -->
	                            <td>${'1' eq info.scetion ? '早': '2' eq info.scetion ? '中':'晚' }餐</td>
	                            <td>${info.time }</td>
	                            <td>${info.address }</td>
	                            <td>${info.type }</td>
	                            <td>
	                            
	                            <c:if test="${'自选座位' eq info.dinnerTable or '指定座位' eq info.dinnerTable}">
	                            	
	                            	<c:if test="${empty info.groupPlanId || info.groupPlanId < 1}">${info.dinnerTable}</c:if>
	                            	<c:if test="${not empty info.groupPlanId && info.groupPlanId > 0}">
	                            		<a href="${ctx}/portal/pri/meeting/showAllGroupInfo.action?groupPlan.id=${info.groupPlanId }&meetingId=${meetingId}&menu_id=${param.menu_id}" >${info.dinnerTable}</a>
	                            	</c:if>
	                            </c:if>
			                	<c:if test="${'自选座位' ne info.dinnerTable and '指定座位' ne info.dinnerTable}"><a href="${ctx}/portal/pri/meeting/showGroupInfo.action?meetingId=${meetingId}&dinner.id=${info.dinnerId }&menu_id=${param.menu_id}">${info.dinnerTable}</a></c:if>
	                            
	                            </td>
	                            <td>${info.comments }</td>
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
	
	//内容tab切换act
	$(".content_tab li").not('.more').find('a').click(function () {
		$(".content_tab li").removeClass("act");
		$(this).parent().addClass("act");
		$(".tableInfo").css('display','none');
		var id = $(this).attr('id');
		$('#info_'+id).css('display','');
	});
	
	
	//导航
	$(".agenda .content_tab li.more a").hover(
	  function () {
		$(this).parent().find('ul').fadeIn();
	  },
	  function () {
		$(this).parent().find('ul').hide();
	  }
	);
	
	$(".agenda .content_tab li.more ul").hover(
	  function () {
		$(this).show();
	  },
	  function () {
		$(this).hide();
	  }
	);
	
	//如果没有默认的ACT样式，自动为第一个添加act class
	if($('.agenda').find(".act").length == 0){
		$('.agenda').find("li").first().addClass("act");
		$('.cont').find('.tableInfo').first().css('display','');
	}
	
	
});
</script>
