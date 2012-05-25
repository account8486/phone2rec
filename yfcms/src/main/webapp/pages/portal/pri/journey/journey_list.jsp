<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960">

	<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
		<div>
			<table  class="content_table">
                <thead>
                   <tr >	                   
                    <th style="width:20%">行程</th>
                    <th style="width:20%;">发车安排</th>
                    <th style="width:60%;">人员</th>
               	   </tr>
                </thead> 
                <tbody> 
                <c:choose>
	            	<c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="JourneyView" items="${pager.pageRecords}" varStatus="status">
                            <tr  <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            
                                <td> ${JourneyView.name }
                                <br/>车牌号:<span style="color:blue">${JourneyView.vehicleNumber}</span>
                                <br/>司&nbsp;机:<span style="color:blue">${JourneyView.driverName }</span></td>
                               
                                <td>${fn:substring(JourneyView.startTime,0,16)}&nbsp;<br/><span style="color:blue">${JourneyView.departure}</span>&nbsp;发车
                                <br/>${fn:substring(JourneyView.endTime,0,16)}&nbsp;<br/><span style="color:blue">${JourneyView.destination}</span>&nbsp;到达 </td>
                              
                                <td>${JourneyView.journeyMembers}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
				</tbody>
			</table>
			<%@ include file="/pages/common/page.jsp" %> 
		</div>

	</div>

<%@ include file="/pages/portal/common/footer.html" %>
 <script type="text/javascript">
	$().ready(function() {
		var menu_id = getMenuId();
		$('#menu_id').val(menu_id);
	
	});
	
    function query() {
        $('#listUserForm').submit();
    }
    
    //定义回车键的行为
    jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'user_mobile':
    			case 'user_name':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
    
    //发送私信
    function sendPriMsg(userId){
    	window.open("${ctx}/portal/pri/message/list.action?meetingId=${meeting.id}&select=new&menu_id=" + $("menu_id").val() + "&selectuser=" +userId,"_self");

    }
    
</script>