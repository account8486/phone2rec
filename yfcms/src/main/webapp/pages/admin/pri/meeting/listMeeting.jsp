<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache" />
    <title>安徽电信会议云平台管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}                   
	${admin_js}    
</head>
<body>
<div>

        <div>
            <div class="page_title">
				<h3>会议列表</h3>
			</div>
            <form id="listMeetingForm" action="${ctx}/pages/admin/pri/meeting/listMeeting.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <div class="page_tools page_serach">
                <table style="">
                    <tr>
                        <td align="right" width="320">
                            	会议名称：
                            <input type="text" style="width: 220px; " id="meeting.name" name="meeting.name" maxlength="25" value="${meeting.name}"/>

                        </td>
                        <td width="80" align="center">                            
							<a id="queryForList" class="btn_common btn_true" onclick="query();" href="##">查询</a>
                        </td>
                    </tr>
                </table>                
                 </div>
                <c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
	                <div class="page_tools page_toolbar">
	                 <a class="btn_common btn_false" href="javascript:forwardReq('meeting/addMeeting.jsp')">添加会议</a>
	              	</div>
              	</c:if>
            </form>

            <table class="page_datalist" >
                <thead>
                <tr >
                    <th width="1%" style="border-right: 0"></th>
                    <th width="10%">会议编号</th>
                    <th width="15%">会议类型</th>
                    <th width="20%">会议名称</th>
                     <th width="20%">会议地点</th>
                    <th width="12%">开始时间</th>
                    <th width="8%">状态</th>
                    <th width="18%" colspan="1">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="meeting" items="${pager.pageRecords}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> >
                                    <%--<td align="center">${status.index+1 }</td>--%>

                                <td align="left"></td>
                                <td align="left">${meeting.id }</td>
                                <td align="left">${meeting.meetingType.name }</td>
                                <td align="left"><a href="#" onclick="modify('${meeting.id}');">${meeting.name }</a></td>
                                 <td align="left"><c:out value="${meeting.location}" escapeXml="true" /></td>
                                <td align="left"><fmt:formatDate value="${meeting.startTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 "/></td>
                                                                 
                                 <td align="left">
	                                 <c:if test="${meeting.state==1}"> 有效</c:if>       
	                                 <c:if test="${meeting.state==0}"> <font style="color:red;">无效</font></c:if>                                  
                                 </td>                                 	
                                <td align="center">
	                                <!-- <a href="#" onclick="preview('${meeting.id}');">预览</a>&nbsp;  -->
	                                <a href="#" onclick="modify('${meeting.id}');">编辑</a>&nbsp;
	                                <c:if test="${ SESSION_ADMIN_USER.role.id eq 1 || SESSION_ADMIN_USER.role.id eq 3 }">
	                                <a href="#" onclick="del('${meeting.id}');">删除</a>
	                                </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="10" align="center">
                                没有会议信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div>
           <%@ include file="/pages/common/page.jsp" %>
            </div>
        </div>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		$("span.wordbreak").each(function(){
			$(this).html($(this).html().replace(/\n/g,"<br/>"));
		});

	});

    function query(){
		$('#listMeetingForm').submit();
	}
	function forwardReq(url){
		//window.location.href = url;
		window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo";
		hideObj($(window.parent.document).find('#menu_frame').contents().find("#editMeeting"));
	}
	function modify(meetingId){
		//window.location.href = url;
		//window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingById.action?meeting.id="+meetingId;
		//window.open("${ctx}/pages/admin/pri/meeting/getMeetingById.action?meeting.id="+meetingId);
		//alert($(window.parent.document).find('#menu_frame').contents().find("#editMeeting").html());
		window.location.href = "${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage&meeting.id="+meetingId;
		//$(window.parent.document).find('#menu_frame').contents().find("#editMeeting").show();
		showEditMenu("${ctx}",meetingId);
	}
	function del(meetingId){
		if(confirm("确定要删除选择的信息吗?")){
			doDelete(meetingId);
		}
	}
	
	function doDelete(meetingId){
		var url = '${ctx}/pages/admin/pri/meeting/deleteMeeting.action?meeting.id='+meetingId;
		
		$.getJSON(url, callback);
	}
	//回调函数
	function callback(data){
		//alert(data.retmsg);
		if(data.retcode == "0"){
			returnList();
		}
	}
	
	function returnList(){
		window.location.href = "${ctx}/pages/admin/pri/meeting/listMeeting.action";
	}
	
	function preview(meetingId){
		//window.location.href = url;
		window.open("${ctx}/pages/admin/pri/meeting/previewMeeting.action?returnType=preview&meeting.id="+meetingId,"_preview_");
	}
</script>
</body>
</html>