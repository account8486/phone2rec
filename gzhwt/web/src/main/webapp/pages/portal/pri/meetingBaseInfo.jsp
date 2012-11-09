<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/portal/common/header.jsp" %>


<div class="w960">

<div class="cbody">
    <div class="cbox">
        <div class="title"><h5>会议主题</h5></div>
        <div class="cont">
        <%--${wd:convertToHtmlLine(meeting.topic)} --%>
        ${meeting.topic}
        </div>
    </div>
    
<!-- 参考议程详情页面的在首页展现议程开始 -->
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

    <div class="cbox">
	<div class="title">
        	<h5>议程安排</h5>
        	<a href="${ctx }/portal/pri/agenda/agendaList.action?meetingId=${meetingId }&menu_id=2992&menu_name=会议议程" class="more">更多>></a>
    </div></div>
  	<div class="agenda">
       	<ul class="content_tab content_tab_date">
       		<c:forEach var="view" items="${agendaListContent}" varStatus="status">
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
   	<c:forEach var="view" items="${agendaListContent}" varStatus="status">
    	<table id="info_${status.index }" class="content_table tableInfo content_table_date" style="${today eq fn:substringBefore(view.date," ") ? '' : 'display:none;'}">
    		<thead>
					<tr style="text-align:center">
                    	<th width="20%" style="text-align:center">时间</th>
                        <th width="35%" style="text-align:center">主题</th>
                        <th width="20%" style="text-align:center">发言人</th>
                        <th width="20%" style="text-align:center">地点</th>
                    </tr>
                </thead>
            <tbody>

         	<c:forEach var="meetingagenda" items="${view.agendaViews}" varStatus="status">

         	<c:if test="${status.first}">
         		<tr><td colspan="4"><b>${view.date}</b></td></tr>
         		<tr><td colspan="4"><b>
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
         		<td width="25%">
         			<p><span class="name">${meetingagenda.startTime} 至 ${meetingagenda.endTime}</span></p>
         		</td>
                 <td><span class="hl">${meetingagenda.topic }</span></td>
                 <td><span class="hl">${meetingagenda.hostName }</span></td>
                 <td>${meetingagenda.location }</td>
           	</tr>
          </c:forEach>
            </tbody>
        </table>
    </c:forEach>
   </div>
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
    
  <!-- 参考议程详情页面的在首页展现议程结束 -->  
    
    <!-- 议程安排  //20120711由上面一段议程代码替换  -->
   <%--  <c:if test="${not empty agendaDateView }">
    <div class="cbox">
        <div class="title">
        	<h5>议程安排<span class="time">(${agendaDateView.date })</span></h5>
        	<a href="${ctx }/portal/pri/agenda/agendaList.action?meetingId=${meetingId }&menu_id=2729" class="more">更多>></a>
        </div>
        <div class="cont">
			<table class="data_list">
            	<thead>
					<tr>
                    	<th width="20%">时间</th>
                        <th width="35%">主题</th>
                        <th width="20%">人员</th>
                        <th width="20%">地点</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="agenda" items="${agendaDateView.agendaViews}" varStatus="status" end="9">
                	<tr>
                    	<td>${agenda.startTime }-${agenda.endTime }</td>
                        <td><span class="hl">${agenda.topic }</span></td>
                        <td><span class="hl">${agenda.hostName }</span></td>
                        <td>${agenda.location }</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </c:if> --%>
    
    <!-- 资料下载 -->
    <c:if test="${not empty fileList }">
    <div class="cbox">
        <div class="title">
        	<h5>资料下载(${totalFileCount })</h5>
        	<a href="${ctx }/portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=${meetingId }&menu_id=3270" class="more">更多>></a>
        </div>
        <div class="cont">
			<table class="data_list">
            	<thead>
                	<tr style="text-align:center">
                    	<th width="40%" style="text-align:center;font-size:12px">文件名称</th>
                        <th width="15%" style="text-align:center;font-size:12px">文件类型</th>
                        <th width="15%" style="text-align:center;font-size:12px">资料分类</th>
                        <th width="20%" style="text-align:center;font-size:12px">上传时间</th>
                        <th width="10%" style="text-align:center;font-size:12px">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${fileList }" var="file" end="4">
                	<tr>
                    	<td title="${file.fileName }">${wd:limit(file.fileName,30) }</td>
                        <td>
                        	<c:if test="${fileType[fn:toLowerCase(file.filePostfix)] != null }"><img src="${ctx}/${fileType[fn:toLowerCase(file.filePostfix)][0] }" width="16" height="16" border="0" title="${file.filePostfix}"><span>(${fileType[fn:toLowerCase(file.filePostfix)][1] })</span></c:if>
                        	<c:if test="${fileType[fn:toLowerCase(file.filePostfix)] == null }"><img src="${ctx}/${fileType['other'][0] }" width="16" height="16" border="0" title="${file.filePostfix}"><span>(${fileType['other'][1] })</span></c:if>
                        </td>
                        <td>${file.assortName }</td>
                        <td><fmt:formatDate value="${file.createTime}" pattern="M月d日 H:m"/></td>
                        <td>
                        <c:if test="${file.downloadFlg == 1}">
                        <a target="_blank" href="${ctx}/portal/meeting/doDownloadFile.action?meetingFileId=${file.id} " class="down">下载</a>
                        </c:if>
                        </td>
                    </tr>
                </c:forEach>    
                </tbody>
            </table>
        </div>
    </div>
    </c:if>
    
</div>
    
    <div class="cright">
    	<!-- 欢迎信息 -->
    	<div class="cbox">
	        <div class="user_info">
            	<span class="date">
                	${dateStr }<s>${weekStr }</s>
                </span>
                <span class="welc">
                	<b>${SESSION_USER.name}</b>，欢迎您参加本次会议
                </span>
            </div>
	   </div>
   
    	<!-- 会议新闻 -->
    	<c:if test="${not empty newsList }">
    	<div class="cbox">
        	<div class="title">
        		<h5>会议新闻</h5>
        		<a href="${ctx }/portal/pri/news/show.action?meetingId=${meetingId }&menu_id=3100" class="more">更多>></a>
        	</div>
            <div class="cont" style="margin:0px; background:#d2dee7; padding:0px;">
            	
			<div id="focus">
				<ul>
				<c:forEach items="${newsList}" var="news">
					<li>
		            	<a href="${ctx }/portal/pri/news/detail.action?meetingId=${meetingId}&id=${news.id}&menu_id=${param.menu_id }&menu_name=${param.menu_name}" target="_blank"><img src="${serverUrl }${news.thumbnailImage}" width="250" height="183"/></a>
					</li>
				</c:forEach>
				</ul>
			</div>
            </div>
        </div>
        </c:if>
        
        <!-- 用餐安排 -->
        <c:if test="${not empty dinnerDateView }">
        <div class="cbox">
        	<div class="title">
        		<h5>用餐安排<span class="time">(${dinnerDateView.date })</span></h5>
        		<a href="${ctx }/portal/pri/meeting/getDinnerInfo.action?meetingId=${meetingId }&menu_id=2732" class="more">更多>></a>
        	</div>
            <div class="cont">
            	<ul class="yongcan_list">
            		<c:forEach var="info" items="${dinnerDateView.info}" >
                	<li>
                    	<p class="tit">${'1' eq info.scetion ? '早': '2' eq info.scetion ? '中':'晚' }餐：</p>
                        <p class="cot">
                        		时间：${info.time }<br/>
                            	地点：${info.address }<br/>
                            	类型：${info.type }
                        </p>
                    </li>
                   	</c:forEach>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
        </c:if>
        
        
         <div class="cbox">
        	<div class="title"><h5>会务信息</h5></div>
            <div class="cont">
           	          会议地点：${_portal_meeting_.location}<br />
            	会议时间：<br />
            	<fmt:formatDate value="${_portal_meeting_.startTime }" type="both" pattern="yyyy年MM月d日"/> - <fmt:formatDate value="${_portal_meeting_.endTime }" type="both" pattern="yyyy年MM月d日"/><br />
            	
            	会务人员：<br />
            	${businessPersonnel}
            	
            </div>
        </div>
        
        
        
        <!-- 天气预报 -->
        <div class="cbox">
        	<div class="title"><h5>天气预报</h5></div>
            <div class="cont">
            <%-- 中国天气网插件
            	<c:choose >
            	<c:when test="${meeting.city eq '0101'|| meeting.city eq '0201'|| meeting.city eq '0301'|| meeting.city eq '0401'}">
            		<iframe  src="http://m.weather.com.cn/n/pn12/weather.htm?id=101${meeting.city}00T" width="100%" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            	</c:when>
            	<c:otherwise>
            		<iframe  src="http://m.weather.com.cn/n/pn12/weather.htm?id=101${meeting.city}01T" width="100%" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            	</c:otherwise>
            	</c:choose>
             --%>
             <%--
             <iframe src="${ctx }/portal/meeting/queryWeather.action?meeting.city=${fn:substring(meeting.city, 2, 10) }" width="168" height="50" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            --%>
            
            <%-- 
            <iframe name="weather_inc" src="http://cache.xixik.com.cn/2/guiyang/" width="260" height="70" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
			--%>
			
			 <iframe name="weather_inc" src="http://tianqi.xixik.com/cframe/2" width="260" height="70" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	
            </div>
        </div>
        
        
       
        
        
        <!-- 会务单位 -->
        <div class="cbox">
        	<div class="title"><h5>会务单位</h5></div>
            <div class="cont">
            	主办单位：${meeting.host }<br>
            	<c:if test="${not empty meeting.organizer}">
            	<c:forEach var="orgzer" items="${wd:orgsToList(meeting.organizer) }" varStatus="status">
            		<span style="${status.first ?'':'padding-left:70px;'}"><c:if test="${status.first }">承办单位：</c:if>${orgzer.organizer }<br/></span>
            	</c:forEach>
            	</c:if>
            	<%--
				服务电话：0551-1234567<br>
				邮箱：haoying@wondertek.com.cn
				 --%>
            </div>
        </div>
		
		<!-- Android客户端下载 -->
		
		<%-- 
		 <div class="cbox">
            <a href="${ctx}/portal/pri/interaction/postList.action?meetingId=${meeting.id}&menu_id=4819&menu_name=互动交流">
            <img src="${ctx}/images/touch/download_yjjy.png" title="互动交流" />
            </a>
        </div> 
		--%>
        <div class="cbox">
            <a href="/hyy.apk" class="download"></a>
        </div>
        
<%@ include file="/pages/portal/common/footer.html" %>

<script type="text/javascript" src="${ctx }/js/newsImage.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//alert("${meeting.province}${meeting.city}");
	//alert($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
	$("span.wordbreak").each(function(){
		//alert($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
		$(this).html($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
	});

	$("p").not(".a1").not(".a2").css("margin-left","24px");
	

	//footer_relocate();
	
	/*
	$("#meeting_type").attr("value","${meeting.meetingType.name}");
	$("#type").html($("#meeting_type option:selected").text());
	*/
});
</script>