<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption" value="选择收信人"></h5></div></div>
		<div>
		<form id="listUserForm" action="${ctx}/portal/pri/message/selectUser.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meeting.id" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
	        <input type="hidden" name="selectUser" id="selectUser" value=""/>
	        <input type="hidden" name="userIds" id="userIds" value=""/>
			<table class="content_table">
		      <tr>
		        <td style="text-align:right;">
		        	手机号码：
		        	<input type="text" style="width: 120px; " id="user_mobile" name="mobile"
	                                   value="${mobile}"/>                      
	            	姓 名：
	            	<input type="text" style="width: 120px; " id="user_name" name="username"
	                                   value="${username}"/>
	            	<a href="#" id="queryForList" class="btn_blue">搜 索</a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            	<a href="#" id="done" class="btn_blue">确认</a>
	            	<a href="${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}" class="btn_blue">返回</a>
	            </td>
		      </tr>
		      <tr>
		      <td sytle="padding: 0px;">
		        	<ul class="message_addressbox"></ul>
		       </td>
		       </tr>
		    </table>
		</form>
		</div>
	
		<div>
			<table  class="content_table" >
                <thead>
                   <tr >	                   
                    <th>选择</th>
                    <th>姓名</th>
                    <th>手机号码</th>
                    <th>职务</th>
                    <th>城市</th>
                    <th>电子邮箱</th>
                </tr>
                </thead> 
                <tbody> 
                <c:choose>
	            	<c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td><input type="checkbox" class="checkbox" user="${user.name}" id="${user.id }"/></td>
                                <td>${user.name }</td>
                                <td >
                                    <c:choose><c:when test="${user.meetingMember.mobileIsDisplay eq 1}">${user.mobile }</c:when>
                                        <c:otherwise>未公开</c:otherwise></c:choose>
                                </td>
                                <td>
                                    <c:choose><c:when test="${user.meetingMember.jobIsDisplay eq 1}">${user.meetingMember.job }</c:when>
                                        <c:otherwise>未公开</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${user.meetingMember.city }</td>
                                <td>${user.meetingMember.mailbox }</td>
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
$(document).ready(function() {
	var menu_id = getMenuId();
	var addContact = function(id, name) {
		var html = "<li id=\"" + id + "\" class=\"contact\"><span class=\"name\">" + name + "</span><a class=\"delete\" href=\"javascript:void(0);\"></a></li>";
		$("ul.message_addressbox").append(html);
	};
	var setUserIds = function() {
		var jsonStr = "";
		var userIds = "";
		$('li.contact').each(function(index) {
			var id = $(this).attr("id");
			var name = $(this).children("span:first").text();
			if (index == 0) {
				jsonStr = "{\"id\":" + id +",\"name\":\""+ name +"\"}";
				userIds = id;
			} else {
				jsonStr = jsonStr + ",{\"id\":" + id +",\"name\":\""+ name +"\"}";
				userIds = userIds + "," + id;
			}
		});
		$("#selectUser").val("[" + jsonStr + "]");
		$("#userIds").val(userIds);
	};

	$('#menu_id').val(menu_id);
	var selectUser = $.parseJSON('${selectUser}');
	if (selectUser) {$.each(selectUser, function(index, data){
		addContact(data.id, data.name);
		$("input.checkbox[id='"+data.id+"']").attr("checked", true);
	});}
	
	$("input.checkbox").click (function() {
		var id = $(this).attr("id");
		var name = $(this).attr("user");
		if($(this).attr("checked")) {
			var node = $("ul.message_addressbox").find("li[id='"+id+"']");
			if (node.length == 0) {addContact(id, name);}
		} else {
			//$("#selectUser").val($(this).attr('id'));
			var node = $("ul.message_addressbox").find("li[id='"+id+"']");
			if (node.length > 0) {
				$("ul.message_addressbox").find("li[id='"+id+"']").remove();
			}
		}
	});
	
	$("ul.message_addressbox").delegate("a", "click", function(event) {
		var id = $(this).parent().attr("id");
		$("input.checkbox[id='"+id+"']").attr("checked", false);
		$(this).parent().remove();
	  
	});
	
	$("#queryForList").click(function(){
		setUserIds();
		$('#listUserForm').submit();
	});
	
	$("#done").click(function(){
		setUserIds();
		location.href = "${ctx}/portal/pri/message/list.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}&selectuser="+$("#userIds").val();
	});
});
    
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
</script>