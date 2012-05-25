<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ page import="com.wondertek.meeting.model.User" %>
<%@ page import="com.wondertek.meeting.model.Role" %>
<%@ page import="java.lang.Long" %>
<%@ page import="java.util.List" %>
        
<div id=index class="header">
	<div class="box960">
        <%@ include file="/pages/common/brand_header.jsp" %>
		<div class="title_menu">
			<ul >
                <c:set var="tmp" value=""/>
               	<c:forEach var="role" items="${SESSION_USER.roles}" varStatus="status">
               	<c:if test="${(role.id eq 1 || role.id eq 2 || role.id eq 3) && tmp ne '1' }">
               		<c:set var="tmp" value="1"/>
               		<li><a id="a_listMeeting|getMeetingById" class="selected"  href="${ctx}/pages/admin/pri/listMeeting.action">会议管理</a></li>
                </c:if>
                <c:if test="${(role.id eq 1 || role.id eq 2) && tmpflag ne '1' }">
               		<c:set var="tmpflag" value="1"/>
                	<li><a id="a_org" class=all href="${ctx}/admin/pri/org/list.action">机构管理</a></li>
	                <li><a id="a_user" class=all href="${ctx}/admin/pri/user/list.action">用户管理</a></li>
                </c:if>
                </c:forEach>
                <li><a id="a_getAttendMeetingList" class="selected" href="${ctx}/portal/pri/meeting/getAttendMeetingList.action">我的会议</a></li>
                <li><a id="a_modifyPwd" class="all" href="${ctx}/pages/portal/pri/person/modifyPwd.jsp">修改密码</a></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
    var url = window.location + "";

    var alist = $(".title_menu a");
    alist.removeClass("selected");
    alist.addClass("all");
    alist.each(function() {
	    var exp = this.id.substring(2);
	
	    if (url.search(exp) != -1) {
	          $(this).removeClass("all");
	          $(this).addClass("selected");
	    }
    })
</script>
