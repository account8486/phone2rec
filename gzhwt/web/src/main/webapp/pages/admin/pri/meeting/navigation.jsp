<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会务通平台管理</title>
    ${admin_css}
    ${jquery_js}
</head>
<body>
<div >
        <div>
            <div class="page_title">
				<h3>会议编辑导航  -- ${CURRENT_MEETING}</h3>
				
			</div>
            <div>
            <form id="listMeetingForm" action="${ctx}/pages/admin/pri/listMeeting.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                
                <input id="meeting_info" type="button" class=lbtn value="会议信息"  />--&gt;
                <input id="meeting_agenda" type="button" class=lbtn value="会议议程"  />--&gt;
                <input id="meeting_user" type="button" class=lbtn value="会议用户" />--&gt;
                <input id="meeting_doc" type="button" class=lbtn value="会议资料"  />--&gt;
                <input id="meeting_dinner" type="button" class=lbtn value="用餐安排" />--&gt;
                <input id="meeting_hotel" type="button" class=lbtn value="酒店住宿"  />--&gt;
                <input id="meeting_travel" type="button" class=lbtn value="出行服务"  />
            </form>
			</div>
        </div>
</div>


<script type="text/javascript">
	$(document).ready(function() {
	});

	$("#meeting_info").bind("click",{"meeting_id":"${param.meeting_id}"},function(event){
		alert(event.data.meeting_id);
		});//设置文本

	//${ctx}/pages/admin/pri/meeting/getMeetingById.action
</script>
</body>
</html>