<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
    <ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">签到码</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
         <div class="boxa">
            <div style="width: 150px; height:150px; margin: 10px auto;">
            	<img alt="qrcode" width="150px;" height="150px;" src="${ctx}/touch/pri/meeting/queryQRCode.action?meetingId=${param.meetingId}">
            </div>
        </div>
    </div>
    
<%@ include file="/pages/touch/common/footer.jsp" %>
<script>
/*$(function(){
	var ran = Math.random();
	var queryStr = "${ctx}/touch/pri/meeting/queryQRCode.action?meetingId=${param.meetingId}&a=" + ran; 
	$("#qrcode").attr("src", queryStr);	
});*/
</script>