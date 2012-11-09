<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
		<div>
		<form id="listUserForm">
			<input type="hidden" id="menu_id" name="menu_id" />
			<input type="hidden" id="meeting.id" name="meeting.id" value="${meeting.id}"/>
			<input type="hidden" id="meetingId" name="meetingId" value="${meeting.id}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table class="content_table">
		      <tr>
		        <td >
		        	手机号码：
		        	<input type="text" style="width: 120px; " id="user_mobile" name="mobile"
	                                   value="${mobile}"/>                      
	            	姓 名：
	            	<input type="text" style="width: 120px; " id="user_name" name="username"
	                                   value="${username}"/>
	            	<a href="#" id="queryForList" onclick="query();" class="btn_blue">搜 索</a> 
	            	<!-- 
	          	 	<a href="#" id="exportUserBtn"  class="btn_blue">导出通讯录</a> 
	          	 	 -->
	            </td>
		       
		      </tr>
		    </table>
		</form>
		</div>
	
		<div>
			<table  class="content_table" >
                <thead>
                   <tr >	                   
                    <th style="width:10%">姓名</th>
                    <th style="width:10%;">手机号码</th>
                    <th style="width:10%;">职位</th>
                    <th style="width:12%;">单位</th>
                    
                    <%--
                    <th style="width:8%;">房间号</th>
                    <th style="width:8%;">城市</th>
                    <th style="width:10%;">电子邮箱</th> --%>
                    <!--<th style="width:10%;">发送私信</th>-->
                   </tr>
                </thead> 
                <tbody> 
                <c:choose>
	            	<c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td>${user.name }</td>
                                <td >
                                    <c:choose>
                                        <c:when test="${user.meetingMember.mobileIsDisplay eq 1}">
                                        	${user.mobile }
                                        </c:when>
                                        <c:otherwise>
                                            	未公开
										</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    ${user.meetingMember.bookJob }
                                </td>
                                <td>${user.meetingMember.department }</td>
                                
                                 <%--
                                <td>
                                    <c:choose>
                                        <c:when test="${user.meetingMember.roomNumberIsDisplay eq 1}">
                                        	${user.meetingMember.roomNumber }
                                        </c:when>
                                        <c:otherwise>
                                            	未公开
										</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${user.meetingMember.city }</td>
                                <td>${user.meetingMember.mailbox }</td>--%>
                                <!--<td><a class="btn_blue" href="#" onclick="sendPriMsg('${user.id}');">发送私信</a></td>
                            --></tr>
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
		
		$("#exportUserBtn").click(function(){
			exportUsers();
		});
	
	});
	
    function query() {
    	$('#listUserForm').attr("action","${ctx}/portal/pri/meeting/getMeetingUsers.action");
        $('#listUserForm').submit();
    }
    
    
    function exportUsers() {
    	$('#listUserForm').attr("action","${ctx}/portal/pri/export/exportUserBook.action");
    	$('#listUserForm').attr("target","_blank");
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