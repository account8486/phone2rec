<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960" style="min-height: 300px">
	<div class="cbox"><div class="title"><h5 id="caption">嘉宾信息</h5></div></div>
		<div>
			<form id="mainForm" action="${ctx}/portal/pri/meeting/guest_findAllGuest.action">
				<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
                <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
                <input type="hidden" name="meetingId"  value="${meetingId}"/>
                <input type="hidden" name="menu_id" id="menu_id" value="${param.menu_id}"/>
			<table class="content_table">
		      <tr>
		        <td >
	            	姓 名：
	            	<input type="text" style="width: 120px; " id="queryName" name="queryName"
	                                   value="${queryName}"/>
	            	<a href="#" id="queryForList"  class="btn_blue">搜 索</a> 
	            </td>
		       
		      </tr>
		    </table>
			</form>
		
		 <table class="content_table" >
                <thead>
                <tr >
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
	                                <a class="btn_blue findGuest" href="#" guestId="${guest.id }">详细</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="5" align="center">
                                没有嘉宾信息.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
		
		<div class="clear"></div>
		<%@ include file="/pages/common/page.jsp" %>
		</div>

	</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
	
$(function(){
	$(document).ready(function(){
		// 查询
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
		

		/*查看嘉宾详细信息*/
		$(".findGuest").click(function(e){
			var id=$(e.target).attr("guestId");
			window.location.href="${ctx}/portal/pri/meeting/guest_findGuestById.action?id="+id+"&menu_id=${param.menu_id}";
		});
	});
});
    
</script>