<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">座位安排</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;width: 100%;height: 100%">
    	<img src="${ctx}/portal/pri/meeting/managerseat_getSeatImage.action?meetingId=<%=request.getParameter("meetingId") %>" alt="红色圈为您的座位安排" width="100%" height="100%"/>
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