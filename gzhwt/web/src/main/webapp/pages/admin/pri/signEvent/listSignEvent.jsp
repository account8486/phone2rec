<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache" />
    <title>会务通平台</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}                   
	${admin_js}    
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".easyui-tabs").tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs("getTab", title); 
					var href = tab.attr("link");
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});
		});
	</script>
</head>
<body>
<div>
		<div class="page_title"><h3>签到事件  -- ${CURRENT_MEETING}</h3></div>
		
		<div class="easyui-tabs" border="false" style="padding:10px;">
			<!-- <div title="签到管理" link="${ctx }/admin/pri/sign/list.action?meetingId=${rfidSignIn.meetingId}" style="padding:10px;"></div> -->
			<div title="签到记录" link="${ctx }/admin/pri/meeting/card_listRfidSignIn.action?rfidSignIn.meetingId=${meetingId}" style="padding:10px;"></div>
			<!-- <div title="签到统计" link="${ctx }/admin/pri/meeting/card_rfidSignInForStaic.action?rfidSignIn.meetingId=${meetingId}" style="padding:10px;"></div>	-->
			<div title="签到事件" selected="true" style="padding:10px;"></div>		
		</div>
        
        <div>
          		<div class="page_tools page_toolbar">
	                 <a class="btn_common btn_false" href="${ctx }/admin/pri/meeting/signEvent_switchAdd.action?meetingId=${meetingId}">添加事件</a>
	             </div>

            <table class="page_datalist" >
                <thead>
                <tr>
                    <th width="1%" style="border-right: 0"></th>
                    <th width="8%">会议编号</th>
                    <th width="20%">事件名称</th>
                    <th width="10%">签到日期</th>
                    <th width="10%">签到时段</th>
                    <th width="10%">签退时段</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="event" items="${list}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td align="left">&nbsp;</td>
                                <td align="left">${event.meetId}</td>
                                <td align="left">${event.eventName }</td>
                                <td align="left">${event.signDate }</td>
                                <td align="left">${event.signInBeginTime}-${event.signInEndTime}</td>
                                <td align="left">${event.signOutBeginTime}-${event.signOutEndTime}</td>
                                <td align="center">
	                                <a href="#" class="updateSign" signId="${event.id }">编辑</a>&nbsp;
	                                <a href="#" class="deleteSign" signId="${event.id }">删除</a>&nbsp;
	                                <a href="${ctx }/admin/pri/meeting/card_listRfidSignIn.action?rfidSignIn.meetingId=${meetingId}&rfidSignIn.signEvent.id=${event.id}">查看签到记录</a>&nbsp;
	                                <a href="${ctx }/admin/pri/meeting/card_rfidSignInForStaic.action?rfidSignIn.meetingId=${meetingId}&rfidSignIn.signEvent.id=${event.id}"  signId="${event.id }">查看签到统计</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="6" align="center">
                                此会议没有签到事件.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <div>
            </div>
        </div>
</div>


<script type="text/javascript">
    $(function(){
    	
    	var errMsg="${errMsg}"
    	if(errMsg!=null&&errMsg!=''){
    		alert(errMsg);
    	}
    	/*删除签到事件*/
		$(".deleteSign").click(function(e){
			var id=$(e.target).attr("signId");
			if(confirm("确认要删除吗?")){
				window.location.href="${ctx }/admin/pri/meeting/signEvent_delete.action?meetingId=${meetingId}&id="+id;
			}
		});
    	
		/*修改签到信息*/
		$(".updateSign").click(function(e){
			var id=$(e.target).attr("signId");
			window.location.href="${ctx}/admin/pri/meeting/signEvent_switchAdd.action?meetingId=${meetingId}&id="+id;
		});
		
    })
</script>
</body>
</html>