<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>


	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">私信</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>


	<input type="hidden" name="content" id="content" value="${content}"/>
	 <div class="tab_c" style="display:block;">
	<div class="cbox"><div class="title"><h5 id="caption" value="选择收信人"></h5></div></div>
		<div>
		<form id="listUserForm" action="${ctx}/touch/pri/msg/selectUser.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meeting.id" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
	        <input type="hidden" name="selectUser" id="selectUser" value=""/>
	        <input type="hidden" name="userIds" id="userIds" value=""/>
			<table class="content_table">
		      <tr>
		        <td style="text-align:left;">
		        	手机号：
		        	<input type="text" style="width: 99px; " id="user_mobile" name="mobile"
	                                   value="${mobile}"/>                      
	            	姓 名：
	            	<input type="text" style="width: 60px; " id="user_name" name="username"
	                                   value="${username}"/>
	            </td>
		      </tr>
		      <tr>
		        <td style="text-align:right;">
	            	<a href="#" id="queryForList" class="login_btn1">搜 索</a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            	<a href="#" id="done" class="login_btn1">确认</a>
	            	<a href="${ctx}/touch/pri/msg/list.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}&content=${content}&selectuser=${userIds}" class="login_btn1">返回</a>
	            </td>
		      </tr>
		      <tr>
		      <td sytle="padding: 0px;">
		        	<ul class="message_addressbox">
		        	</ul>
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
                    <%--
                    <th>职务</th>
                    <th>城市</th>
                    <th>电子邮箱</th> --%>
                </tr>
                </thead> 
                <tbody> 
              
                <c:choose>
	            	<c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="user" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            	<td><input type="checkbox" class="checkbox" user="${user.name}" id="${user.id }" <c:if test="${fn:contains(userIds,user.id)}"> checked="checked" </c:if> /></td>
                                <td>${user.name }</td>
                                <td >
                                    <c:choose><c:when test="${user.meetingMember.mobileIsDisplay eq 1}">${user.mobile }</c:when>
                                        <c:otherwise>未公开</c:otherwise></c:choose>
                                </td>
                                 <%--
                                <td>
                                    <c:choose><c:when test="${user.meetingMember.jobIsDisplay eq 1}">${user.meetingMember.job }</c:when>
                                        <c:otherwise>未公开</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${user.meetingMember.city }</td>
                                <td>${user.meetingMember.mailbox }</td> --%>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
				</tbody>
			</table>
			<%@ include file="/pages/common/page.jsp" %> 
		</div>
	</div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style>
	.message_addressbox li.contact {
	    background-color: #EBF2F7;
	    border: 1px solid #BDDAF2;
	    color: #666666;
	    display: inline-block;
	    float: left;
	    margin: 0 2px 2px;
	    padding: 2px;
	}
	.message_addressbox a.delete {
	    background-image: url("${ctx}/images/close.gif");
	    background-position: 0 -11px;
	    float: left;
	    height: 11px;
	    margin-right: 2px;
	    text-decoration: none;
	    width: 11px;
	}
	.content_tab {
	    display: block;
	}
	.content_tab li {
	    display: inline-block;
	    float: left;
	}
	.content_tab a {
	    color: #333333;
	    display: block;
	    line-height: 30px;
	    padding: 0 10px;
	}
	.content_tab a.message {
	    background-color: #2392E1;
	    color: #FFFFFF;
	    padding: 0;
	    text-align: center;
	    width: 80px;
	}
	.content_tab li.act a {
	    background: url("../images/portal/content_tab_act.gif") no-repeat scroll center top transparent;
	    color: #FFFFFF;
	    font-weight: bold;
	    height: 35px;
	    line-height: 30px;
	    text-align: center;
	    width: 80px;
	}
	.content_table {
	    margin: 5px auto;
	    width: 98%;
	}
	.content_table, .content_table th, .content_table td {
	    border: 1px solid #BBD3E5;
	    border-collapse: collapse;
	    font-size: 14px;
	    padding: 5px 10px;
	    text-align: left;
	}
	.content_table thead {
	    background: none repeat scroll 0 0 #EBF2F7;
	}
	.content_table tbody {
	    background: none repeat scroll 0 0 #F5F5F5;
	}
	.content_table thead th {
	    font-weight: bold;
	}
	.content_tab_date {
	    display: -moz-grid-line;
	    float: right;
	}
	.content_tab_message {
	    display: -moz-grid-line;
	    float: left;
	}
	.content_table_date, .content_table_date th, .content_table_date td {
	    font-size: 12px;
	    padding: 2px;
	}
	a.btn_blue {
	    background: url("../images/portal/btn.jpg") no-repeat scroll 0 0 transparent;
	    color: #FFFFFF;
	    display: inline-block;
	    font-weight: bold;
	    height: 30px;
	    line-height: 30px;
	    text-align: center;
	    width: 80px;
	}
	a.btn_blue:hover {
	    background-position: 0 -30px;
	}
	a.btn_blue:active {
	    background-position: 0 -60px;
	}
	a.login_btn1 { width:80px; margin-left:3px; height:30px; display:block; float:left; line-height:30px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:2px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var menu_id = "";
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
	
	$("input.checkbox").each(function(){ 
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
		location.href = "${ctx}/touch/pri/msg/list.action?meetingId=${meetingId}&select=new&menu_id=${param.menu_id}&selectuser="+$("#userIds").val()+"&content="+$("#content").val();
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