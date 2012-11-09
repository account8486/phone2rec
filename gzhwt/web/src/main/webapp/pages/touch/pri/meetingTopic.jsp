<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
    <ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会议介绍</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
        <div class="boxa">
            <div class="cont">
           
            	时间：${fn:substring(meeting.startTime,0,10)} ～ ${fn:substring(meeting.endTime,0,10)}</br>
               	地点：${meeting.location}</br>
              	内容：${wd:convertToHtmlLine(meeting.topic) }
              
            </div>
        </div>
    </div>
    
<%@ include file="/pages/touch/common/footer.jsp" %>