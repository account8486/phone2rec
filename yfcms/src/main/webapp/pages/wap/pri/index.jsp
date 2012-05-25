<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<%@ page language="java" import="java.util.List ,com.wondertek.meeting.model.ClientMenu,com.wondertek.meeting.model.Meeting" %>


<div class="main">
	<div class="path">
    	${meeting_list_url}   
    </div>
    
	<%
		List<ClientMenu> menulist = (List<ClientMenu>) request.getSession().getAttribute("_portal_menulist_");
		if(menulist!=null && menulist.size()>0 && menulist.get(0)!=null){
			ClientMenu cm = menulist.get(0);
			Meeting m = (Meeting) request.getSession().getAttribute("_portal_meeting_");
			String meetingId = String.valueOf(m.getId());
			String path = "/meeting/" + cm.getContentUrl() + meetingId + "&menu_id=" + cm.getId()+ "&menu_name=" + java.net.URLEncoder.encode(cm.getName(),"UTF-8");
			response.sendRedirect(path);
			response.flushBuffer();
			return;
		}
	%>	
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>