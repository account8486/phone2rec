<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>安徽电信会议云平台管理</title>
    <link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css"/>
    ${style_css}
    ${main_css}
    <%--${thickbox_css}--%>
    ${jquery_ui_css}
    ${module_js}
    ${jquery_js}
    ${jquery_ui_js}
    ${jquery_myext_js}
    ${thickbox_js}
</head>
<body id=portal_body>
<div class="container">
    <%@ include file="/pages/common/admin_header.jsp" %>

    <div class="mainbox">
        <div>
            <div class=crumbs2>会议名称：${meeting.name}</div>


            <table id="main_table">
                <tbody>
                <tr>
                    <td style="position:relative;padding:0px;">

                        <div style="width: 33%;" id="c1" class="main_containers">
                            <div id="m_meeting_topic" class="module" style="display: block;">
                                <div class="moduleFrame">
                                    <jsp:include page="/pages/admin/pri/meeting/portal_module_header.jsp">
                                        <jsp:param name="_moduleTitle_" value="会议议题"/>
                                    </jsp:include>

                                    <div class="moduleContent">
                                        <p><span class="wordbreak"><c:out value="${fn:escapeXml(fn:substring(meeting.topic, 0, 364))}"/></span></p>
                                        <hr>
                                        <p>会议地点：${meeting.location}</p>
                                        <p>会议开始时间：${fn:substring(meeting.startTime,0,10)}</p>
                                        <p>会议结束时间：${fn:substring(meeting.endTime,0,10)}</p>
									
										<c:if test="${empty from}" > 
                                        <div class="moduleEdit" style="text-align:right">
                                            <p><span>
                                            <a class="thickbox"
                                               href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=${meeting.id}&keepThis=true&TB_iframe=true&height=530&width=646">查看明细</a>
                                               &nbsp;
                                            <a class="thickbox"
                                               href="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=portal_addmeeting&meeting.id=${meeting.id}&keepThis=true&TB_iframe=true&height=530&width=646">编辑</a>
                                               </span>
                                            </p>
                                        </div>
                                  		</c:if>
                                        
                                    </div>

                                </div>
                            </div>

                            <div id="m_meeting_map" class="module" style="display: block;">
                                <div class="moduleFrame">
                                    <jsp:include page="/pages/admin/pri/meeting/portal_module_header.jsp">
                                        <jsp:param name="_moduleTitle_" value="地图导航"/>
                                    </jsp:include>

                                    <div class="moduleContent">
                                        <%@ include file="/pages/portal/pri/portal_map.jsp" %>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div style="width: 67%; float: right; margin-left: 0px;" id="c2" class="main_containers">
                            <div id="m_meeting_agenda" class="module" style="display: block;">
                                <div class="moduleFrame">
									
									<jsp:include page="/pages/admin/pri/meeting/portal_module_header.jsp">
                                        <jsp:param name="_moduleTitle_" value="会议议程"/>
                                    </jsp:include>
								    
									
                                    <div class="moduleContent">
                                    <c:if test="${empty from}">
                                        <iframe frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” 
                                        id="frame_agenda" src="${ctx}/admin/pri/meeting/agendaList.action?meetingId=${meeting.id}&from=portal"
                                                style="width:100%;height:100%"></iframe>
                                    </c:if>
                                    
                                    <c:if test="${not empty from}">
                                        <iframe frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no”  
                                        id="frame_agenda" src="${ctx}/portal/pri/meeting/agendaList.action?meetingId=${meeting.id}&from=portal"
                                                style="width:100%;height:100%"></iframe>
                                    </c:if>
                                       
                                        <c:if test="${empty from}">
                                        <div class="moduleEdit" style="text-align:right">
                                            <p><span><a class="thickbox"
                                                        href="${ctx}/admin/pri/meeting/agendaList.action?meetingId=${meeting.id}&keepThis=true&TB_iframe=true&height=400&width=600">编辑</a></span>
                                            </p>
                                        </div>
                                        </c:if>
                                        
                                    </div>
                                </div>
                            </div>
                             <div id="m_meeting_users" class="module" style="display: block;">
                                <div class="moduleFrame">
                                    <jsp:include page="/pages/admin/pri/meeting/portal_module_header.jsp">
                                        <jsp:param name="_moduleTitle_" value="会议用户"/>
                                    </jsp:include>

 						
                                    <div class="moduleContent">
                                    	<div >
                                    	<table width="100%">
                                        <c:forEach var="member" items="${meeting.memberSet}" varStatus="status">
                                        <c:if test="${status.count  % 4 ==1}">
	                                        <tr class="odd">
	                                    </c:if>
	                                        	<td width="25%">
								    			${member.name}[${member.mobile}]
								    			</td>
								    	<c:if test="${status.count  % 4 ==0}">
	                                        </tr>
	                                    </c:if>
							    		</c:forEach>
							    		</table>
							    		</div>
                                        
                                        <c:if test="${empty from}">
                                        <div class="moduleEdit" style="text-align:right">
                                            <p><span>
												<a class="thickbox"
						                           href="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?returnType=portal_addmeeting&meeting.id=${meeting.id}&keepThis=true&TB_iframe=true&height=430&width=550">编辑</a>
											</span></p>
                                        </div>
                                        </c:if>
                                    </div>
                                    
                                        
                                </div>
                            </div>
                            
							<!-- 互动交流 -->
                            <div id="m_meeting_interaction" class="module" style="display: block;">
                                <div class="moduleFrame">
                                    <jsp:include page="/pages/admin/pri/meeting/portal_module_header.jsp">
                                        <jsp:param name="_moduleTitle_" value="互动交流"/>
                                    </jsp:include>
                                    									
                                    <div class="moduleContent">
                                                                       
                                        <!-- 判断FROM是否为空 -->
                                        <c:choose> 
                                        <c:when test="${not empty from}">
                                        <iframe frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no”   
                                        		id="frame_interaction" src="${ctx}/portal/pri/interaction/postList.action?meetingId=${meeting.id}&from=portal"
                                                style="width:100%;height:100%"></iframe>
                                        </c:when>
                                        <c:otherwise>
                                        <iframe frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” 
                                        		id="frame_interaction" src="${ctx}/admin/pri/interaction/postList.action?meetingId=${meeting.id}&from=portal"
                                                style="width:100%;height:100%"></iframe>
                                            
                                        <div class="moduleEdit" style="text-align:right">
                                            <p><span><a class="thickbox"
                                                        href="${ctx}/admin/pri/interaction/postList.action?meetingId=${meeting.id}&keepThis=true&TB_iframe=true&height=380&width=580">更多发言</a></span>
                                            </p>
                                        </div>
                                        </c:otherwise>
                                        </c:choose>        
                                    </div>
                                </div>
                            </div>
                            <!-- 互动交流 -->
                            
							<div id="m_meeting_file" class="module" style="display: block;">
                                <div class="moduleFrame">
                                    <jsp:include page="/pages/admin/pri/meeting/portal_module_header.jsp">
                                        <jsp:param name="_moduleTitle_" value="会议资料"/>
                                    </jsp:include>
                                    <div class="moduleContent">
                                        <!-- 判断FROM是否为空 -->
                                        <c:choose> 
                                        <c:when test="${not empty from}">
                                         <iframe frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” 
                                        		id="frame_file" src="${ctx}/portal/pri/meeting/getMeetingFilesPager.action?meetingId=${meeting.id}&from=portal"
                                                style="width:100%;height:100%"></iframe>
                                        </c:when>
                                        <c:otherwise>
                                        <iframe frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no”
                                        		id="frame_file" src="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meeting.id}&from=portal"
                                                style="width:100%;height:100%"></iframe>
                                            
                                        <div class="moduleEdit" style="text-align:right">
                                            <p><span><a class="thickbox"
                                                        href="${ctx}/admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meeting.id}&keepThis=true&TB_iframe=true&height=350&width=800">编辑</a></span>
                                            </p>
                                        </div>
                                        </c:otherwise>
                                        </c:choose>        
                                      
                                    </div>
                                </div>
                            </div>                            
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
    </div>

    <%@ include file="/pages/common/footer.html" %>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("span.wordbreak").each(function(){
		$(this).html($(this).html().replace(/\n/g,"<br/>"));
	});

});
    function query() {
        $('#listMeetingForm').submit();
    }
    function forwardReq(url) {
        //window.location.href = url;
        window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingById.action";
    }
    function modify(meetingId) {
        //window.location.href = url;
        window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingById.action?meeting.id=" + meetingId;
    }
    function del(meetingId) {
        if (confirm("确定要删除选择的信息吗?")) {
            doDelete(meetingId);
        }
    }

    function doDelete(meetingId) {
        var url = '${ctx}/pages/admin/pri/meeting/deleteMeeting.action?meeting.id=' + meetingId;

        $.getJSON(url, callback);
    }
    //回调函数
    function callback(data) {
        alert(data.retmsg);
        if (data.retcode == "0") {
            returnList();
        }
    }

    function returnList() {
        window.location.href = "${ctx}/pages/admin/pri/listMeeting.action";
    }
</script>
</body>
</html>