<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx }/js/touch/tbtouch.js"></script>
	<c:if test="${not empty newsList }">
	<div class=" pic-widget-slide-v2" id="J_IndexSlider">
		<div class="common-slide">
			<div class="list">
				<ul style="width: 1200px; left: 0px;" class="wrap">
				<c:forEach items="${newsList}" var="news" varStatus="status">
					<c:if test="${status.count <= 5 }">
						<li style="left: 0px;" class="one-img">
						<a href="${ctx }/touch/pri/news/detail.action?meetingId=${meetingId}&id=${news.id}&menu_id=${param.menu_id}&menu_name=${param.menu_name}"><img alt="${wd:limit(news.title,30) }" src="${serverUrl }${news.thumbnailImage}" height="100" width="300"/></a></li>
					</c:if>
				</c:forEach>
				</ul>
			</div>
			<div class="mtbslide-pagination">
				<div id="prevBtn" class="prev pg-btn"><a>上一页</a></div> 
				<div class="trigs"> 
					<ul>
						<c:forEach items="${newsList}" var="news" varStatus="status">
							<c:if test="${status.count <= 5 }">
								<li id="li${status.count }" <c:if test="${status.count == 1}"> class="cur"</c:if>>${status.count }</li>
							</c:if>
						</c:forEach>
					</ul> 
				</div> 
				<div id="nextBtn" class="next pg-btn"><a>下一页</a></div> 
			</div>
		</div>
	</div>
	</c:if>
	<div class="boxa">
		<div class="tit">
	    	<span class="ti">会议介绍</span>
	    	<c:if test="${fn:length(meeting.topic)>40}">
		    	<a href="${ctx}/touch/pri/meeting/getMeetingById.action?returnType=meeting_topic&meeting.id=${meeting.id}" class="more">更多信息</a>
		    </c:if>
		</div>
		
		
		<div class="cont">
			时间：${fn:substring(meeting.startTime,0,10)} ～ ${fn:substring(meeting.endTime,0,10)}<br>
			地点：${meeting.location}<br>
			内容：${wd:convertToHtmlLine(fn:substring(meeting.topic,0,110)) }
			<c:if test="${fn:length(meeting.topic)>40}">
			   	<c:out value="..."></c:out>
			</c:if>
	    </div>
	    
	     <%--
	    	<div class="cont">
	    	<img alt="" src="${ctx}/images/touch/20120718_meeting_info_half.png" style="width:320px">
	    	</div> 
	      --%>
	     
	</div>
	<div class="boxa">
    	<div class="tit">
            <p class="tab">
            	<a href="javascript://" class="act"><span>议程安排</span></a>
                <a href="javascript://"><span>用餐安排</span></a>
            </p>
        </div>
        <div class="tabc" style="display:block; margin:0px;">
			<ul class="cal_list">
				 <p class="title_yc">${agendaDateView.date}
         		        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx}/touch/pri/agenda/agendaList.action?meetingId=${meeting.id}&menu_id=4840&menu_name=会议议程" class="more">>>更多信息</a>
         		 </p>
         		 
				<c:forEach var="agenda" items="${agendaDateView.agendaViews}" varStatus="status">
		    	<li>
		        	<a>
		                <p><span class="name">${agenda.topic }</span><span class="aur">${agenda.hostName }</span></p>
		                <p><span class="time">${agenda.startTime } - ${agenda.endTime }</span><span class="add">&nbsp;<c:if test="${not empty agenda.location }">(${agenda.location })</c:if></span></p>
		            </a>
		        </li>
		        </c:forEach>
		    </ul>
		</div>
		<div class="tabc">
		
			 <p class="title_yc" style="clear:both">
			 	${dinnerDateView.date}
         		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             		<a href="${ctx}/touch/pri/meeting/showDinner.action?meetingId=${meeting.id}&menu_id=4853&menu_name=餐饮安排"  class="more" style="text-align:right;"> >>更多信息</a>
         		
         	</p>
         	
			<ul class="cal_list">
			
		
         		 
         		 
			<c:forEach var="info" items="${dinnerDateView.info}" varStatus="status">
	    		<li>
	        	<a>
	                <p><span class="name">${'1' eq info.scetion ? '早': '2' eq info.scetion ? '中':'晚' }餐：${info.type }</span><span class="aur">${agenda.hostName }</span></p>
	                <p><span class="time">${info.time }</span><span class="add">&nbsp;(地点：${info.address })</span></p>
	            </a>
	        	</li>
	        </c:forEach>
	    	</ul>
	    </div>
	</div>
	<div class="boxa">
		<div class="tit">
	    	<span class="ti">会议资料</span>
		    <a href="${ctx}/touch/pri/file/getMeetingFilesList.action?meetingId=${not empty param.meetingId ? param.meetingId : meeting.id}" class="more">更多信息</a></div>
	
		<div style="display:block;">
			<c:choose>
		        <c:when test="${not empty fileList}">
		        	<ul class="file_list">
		            <c:forEach var="meetingFile" items="${fileList}" varStatus="status">
			        	<li class="${wd:getFileCssByFilePostfix(meetingFile.filePostfix)}">
			            <div class="tools">
			            <%-- 	<a href="javascript://" class="pre">预览</a>--%>
				            <c:if test="${meetingFile.downloadFlg == 1}">
				            <a class="down" target="_blank" href="${ctx}/portal/meeting/doDownloadFile.action?meetingFileId=${meetingFile.id}">下载 </a>
				            </c:if>
			            </div>
			            
			             <span class="title">${meetingFile.fileName}</span>
							<%--大小：${meetingFile.fileSize} KB<br> --%>
							文件类型：${meetingFile.filePostfix }
			        	</li>
		            </c:forEach>
		            </ul>
		        </c:when>
		    </c:choose>  
	    </div>
	</div>
<script type="text/javascript" src="${ctx}/js/touch/ready.js"></script>
<script>
	$(function(){
		$("#header_meeting_name").html("${wd:limit(meeting.name, 17)}");
	
		/*
		// 图片切换
		var picIndex = 1
		
		$("#nextBtn").click(function(){
			if(picIndex == 5 || picIndex == ${fn:length(newsList)}) {// 最后一幅，则跳转到第一幅
				picIndex = 1;
			}else {
				picIndex += 1;
			}
			changeImg(picIndex);
		});
		
		$("#prevBtn").click(function(){
			if(picIndex == 1) {// 第一幅，则跳转到最后一幅
				picIndex = ${fn:length(newsList)};
				if (picIndex > 5)
				{
					picIndex = 5;
				}
			}else {
				picIndex += -1;
			}
			
			changeImg(picIndex);
		});
		
		function changeImg(picIndex) {
			$(".home_pic").hide();
			var homePicName = "#home_pic_" + picIndex;
			$(homePicName).show();
			
			$(".trigs li").removeClass("cur");
			var liName = "#li" + picIndex;
			$(liName).addClass("cur");
		}*/
	});
	
	$('#J_IndexSlider').slideLayer_v2({
		wrapEl : '.common-slide .list',
		slideEl : '.common-slide .list .wrap',
		childEl : '.common-slide .list li',
		counter : '.common-slide .trigs'
	});
</script>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.file_list {}
	ul.file_list li { padding:10px; padding-left:75px; position:relative; border-bottom:1px solid #ccc;}
	ul.file_list li.defaultCss { background:url(${ctx}/images/touch/file/file_default.png) 5px 0px no-repeat;}
	ul.file_list li.doc { background:url(${ctx}/images/touch/file/file_doc.png) 5px 0px no-repeat;}
	ul.file_list li.excel { background:url(${ctx}/images/touch/file/file_excel.png) 5px 0px no-repeat;}
	ul.file_list li.image { background:url(${ctx}/images/touch/file/file_image.png) 5px 0px no-repeat;}
	ul.file_list li.pdf { background:url(${ctx}/images/touch/file/file_pdf.png) 5px 0px no-repeat;}
	ul.file_list li.ppt { background:url(${ctx}/images/touch/file/file_ppt.png) 5px 0px no-repeat;}
	ul.file_list li.txt { background:url(${ctx}/images/touch/file/file_txt.png) 5px 0px no-repeat;}
	ul.file_list li.video { background:url(${ctx}/images/touch/file/file_video.png) 5px 0px no-repeat;}
	ul.file_list li.zip { background:url(${ctx}/images/touch/file/file_zip.png) 5px 0px no-repeat;}
	ul.file_list li span.title { color:#069; display:block; font-weight:bold;}
	ul.file_list li .tools { position:absolute; right:10px; bottom:10px;}
	ul.file_list li .tools a { width:60px;  height:30px; font-weight:bold; display:inline-block; text-align:center; line-height:30px; margin:10px 0px; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; margin-left:10px;}
	ul.file_list li .tools a.pre {    }
	ul.file_list li .tools a.down { }
	
	
	.title_yc { background:url(${ctx}/images/touch/meeting_item_prompt.png) top center repeat-x; height:35px; line-height:35px; text-align:center; color:#fff; font-weight:bold; width:320px;}
	
	.common-slide {
	    height: 140px;
	    margin: 0 auto;
	    padding: 10px;
	    position: relative;
	    width: 300px;
	}
	.common-slide .list {
	    height: 100px;
	    overflow: hidden;
	    position: relative;
	    width: 300px;
	}
	.common-slide .list ul {
	    position: absolute;
	}
	.common-slide .list li {
	    float: left;
	    height: 100px;
	    left: 0;
	    line-height: 100px;
	    position: relative;
	    width: 300px;
	}
	
	.common-slide .list li.one-img a {
	    height: 100px;
	    width: 300px;
	}
</style>