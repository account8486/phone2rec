<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/portal/common/header.jsp" %>

<div class="w960">


    <div class="cbody">
        <div class="cbox">
        	<div class="title"><h5 id="caption">${param.menu_name}</h5></div>
        </div>
	<div class="cbox">		
		<!-- 
		<div>
			<h3>会议类型</h3>
			<p><span id="type"></span></p>
		</div>
		 -->
		<div class="second_title">
			<h5>会议议题</h5>
		</div>
		<div class="cont">
			<p><span class="wordbreak"></span></p>			
			<textarea style="display:none;" id="meeting.topic" name="meeting.topic" maxlength="2000">
				<c:out escapeXml="true" value="${meeting.topic}"/>
			</textarea>
		</div>
	</div>
	<div class="cbox">
		<div class="second_title">
			<h5>会议地点</h5>
		</div>
		<div class="cont"><p><span >${meeting.location}</span></p></div>
	</div>
	<div class="cbox">
		<div class="second_title">
			<h5>会议开始时间</h5>
		</div>
		<div class="cont"><p><span>${fn:substring(meeting.startTime,0,16)}</span></p></div>
	</div>
	<div class="cbox">	
		<div class="second_title">
			<h5>会议结束时间</h5>
		</div>
		<div class="cont"><p><span>${fn:substring(meeting.endTime,0,16)}</span></p></div>
	</div>
    <c:if test="${meeting.joinTime !='' && meeting.joinTime != null}">
	<div class="cbox">	
		<div class="second_title">
			<h5>报到时间</h5>
		</div>
		<div class="cont"><p><span>${meeting.joinTime}</span></p></div>
	</div>
	</c:if>
	<div class="cbox">	

		<div style="display:none;">
			<select id="meeting_type" name="meeting.type">
				<option value="0">普通会议</option>
				<option value="1">研讨会</option>
				<option value="2">公司年会</option>
			</select>
		</div>
	</div>
    </div>
    <div class="cright">
    <!-- 
    	<div class="cbox">
        	<div class="title"><h5>总机电话</h5></div>
            <div class="cont">
            	<p class="tel">${meeting.serviceNumber}</p>
            </div>
        </div>
 	-->
        <div class="cbox">
        	<div class="title"><h5>会议公告</h5></div>
            <div class="cont">
            	${meeting.notice}
            </div>
        </div>

        <div class="cbox">
        	<div class="title"><h5>天气预报</h5></div>
            <div class="cont">
            	<c:choose >
            	<c:when test="${meeting.city eq '0101'|| meeting.city eq '0201'|| meeting.city eq '0301'|| meeting.city eq '0401'}">
            		<iframe  src="http://m.weather.com.cn/n/pn12/weather.htm?id=101${meeting.city}00T" width="100%" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            	</c:when>
            	<c:otherwise>
            		<iframe  src="http://m.weather.com.cn/n/pn12/weather.htm?id=101${meeting.city}01T" width="100%" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
            	</c:otherwise>
            	</c:choose>
            	
            </div>
        </div>
        
        <div class="cbox">
        	<a href="/hyy.apk" class="download"></a>
        </div>

    </div>
	
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
$(document).ready(function() {
	//alert("${meeting.province}${meeting.city}");
	//alert($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
	$("span.wordbreak").each(function(){
		//alert($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
		$(this).html($("#meeting\\.topic").val().replace(/\n/g,"<br/>"));
	});

	$("p").css("margin-left","24px");
	

	//footer_relocate();
	
	/*
	$("#meeting_type").attr("value","${meeting.meetingType.name}");
	$("#type").html($("#meeting_type option:selected").text());
	*/
});
</script>