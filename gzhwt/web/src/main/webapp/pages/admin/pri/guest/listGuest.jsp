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
		<div class="page_title"><h3>嘉宾管理  -- ${CURRENT_MEETING}</h3></div>
		
		<div class="easyui-tabs" border="false" style="padding:10px;">	
			<div title="用户列表" link="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}" style="padding:10px;"></div>
			<div title="批量添加" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=batch_add_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
			<div title="添加用户" style="padding:10px;"></div>
			<div title="导入用户" link="${ctx}/admin/pri/import/preImportUser.action?meetingId=${meeting.id}" style="padding:10px;"></div>
			<div title="临时加入审批" link="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
			<div title="发言人管理" link="${ctx}/admin/pri/spokesman/getSpokesManLst.action?meetingId=${meeting.id}" style="padding:10px;"></div>
			<div title="嘉宾管理"  selected="true" style="padding:10px;"></div>
		</div>
        
        <div>
        <div class="page_tools">
            <form id="listGuestForm" action="${ctx}/admin/pri/guest/guest_findAllGuest.action">
                <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId"  value="${meetingId}"/>
             
                <table style="">
                    <tr>
                        <td align="right" width="320">
                            	嘉宾名称：
                            <input type="text" style="width: 220px; "  name="queryName" maxlength="25" value="${queryName}"/>

                        </td>
                        <td width="80" align="center">                            
							<a id="queryForList" class="btn_common btn_true"  href="#">查询</a>
                        </td>
                    </tr>
                </table>                
               
                 <div class="page_tools page_toolbar">
	                 <a class="btn_common btn_false" href="${ctx}/admin/pri/guest/guest_switchToAddGuest.action?meetingId=${meetingId}">添加嘉宾</a>
	             </div>
            </form>
</div>

            <table class="page_datalist" >
                <thead>
                <tr >
                    <th width="1%" style="border-right: 0"></th>
                    <th width="10%">会议编号</th>
                    <th width="15%">姓名</th>
                    <th width="20%">头衔</th>
                    <th width="20%">简介</th>
                    <th width="18%" colspan="1">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="guest" items="${pager.pageRecords}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if> content="guest" >
                                <td align="left"></td>
                                <td align="left">${guest.meeting.id }</td>
                                <td align="left">${guest.name }</td>
                                <td align="left">${guest.rank }</td>
                                <td align="left">
                                	<c:if test="${fn:length(guest.about)>10}">
                                		<c:out value="${fn:substring(guest.about,0,10) }..."></c:out>
                                	</c:if>
                                	<c:if test="${fn:length(guest.about)<=10}">
                                		<c:out value="${guest.about}"></c:out>
                                	</c:if>
                                </td>
                                
                                <td align="center">
	                                <a href="#" class="updateGuest" guestId="${guest.id }">编辑</a>&nbsp;
	                                <a href="#" class="findGuest" guestId="${guest.id }">详细</a>&nbsp;
	                                <a href="#" class="deleteGuest" guestId="${guest.id }">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="6" align="center">
                                没有嘉宾信息.
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
    $(function(){
    	$("#queryForList").click(function(){
    		$('#listGuestForm').submit();
    	});
    	
    	/*删除嘉宾*/
		$(".deleteGuest").click(function(e){
			var id=$(e.target).attr("guestId");
			var param={"id":id};
			if(confirm("确认要删除吗?")){
				$.post("${ctx}/admin/pri/guest/guest_deleteGuest.action",param,function(data){
					$(e.target).parent().parent().remove();
					var size=$("tr[content]").size();
					if(size<=0){
						window.location.href="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meetingId}";
					}
					
				});
			}
		});
    	
		/*修改嘉宾信息*/
		$(".updateGuest").click(function(e){
			var id=$(e.target).attr("guestId");
			window.location.href="${ctx}/admin/pri/guest/guest_findGuestById.action?queryName=${queryName}&currentPage=${pager.currentPage}&flag=edit&meetingId=${meetingId}&id="+id;
		});
		
		/*查看嘉宾详细信息*/
		$(".findGuest").click(function(e){
			var id=$(e.target).attr("guestId");
			window.location.href="${ctx}/admin/pri/guest/guest_findGuestById.action?meetingId=${meetingId}&id="+id;
		});
    })
</script>
</body>
</html>