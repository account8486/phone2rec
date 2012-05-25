<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.wondertek.meeting.model.MeetingFiles" %>
<%@ include file="../../../../pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议短信管理</title>
    ${admin_css}  ${jquery_js} ${admin_js} ${jmpopups_js} ${util_js} 
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>    
	<script>
		function pageSkip(cpage){
			$('#currentPage').val(cpage);
			$('#queryForList').click();
		}

		function query(){
			$('#listMeetingFileForm').submit();
		}

		function openUploadWin(){
			var url="${ctx}/admin/pri/message/preSendMessage.action?meetingId="+$("#meetingId").val();
			document.location.href=url;
		}

		function delFile(meetingFileid){
			var url="${ctx}/admin/pri/meeting/deleteMeetingFile.action?meetingFileId="+meetingFileid;
			document.location.href=url;
		}

		function download(meetingFileid){
			var url="${ctx}/admin/pri/meeting/doDownloadFile.action?meetingFileId="+meetingFileid;
			window.open(url,"_blank", "height=200,width=500,top=200,left=200");
		}

		function batchDelete(){
		    var retString = "";
		    var meetingId=${meetingId};
		    var checks = document.getElementsByName("messageId");
		    if (checks) {
		        for (var i = 0; i < checks.length; i++) {
		            var chkObj = checks[i];
		            if (chkObj.checked)
		                retString += chkObj.value + ",";
		        }
		    }
		    if(retString==""){
		    	alert("请选择你要删除的短信!");
		    	return;
		    }
			if(confirm("你确定要批量删除所选短信吗?")){
				var url="${ctx}/admin/pri/message/batchDelMessage.action?meetingId="+meetingId+"&ids="+retString;
				this.location=url;
			}
		}

		$(document).ready(function() {
		    $('.easyui-tabs').tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs('getTab', title); 
					var href = tab.attr('link');
					if (href) {
						showLoading(title);
						location.href = href;
						return false;
					}
				}  
			}); 
		    $("#all_check").change(function () {
		        if (this.checked) {
		            $("[name='messageId']").attr("checked", $("#all_check").attr("checked"));
		        } else {
		            $("[name='messageId']").removeAttr("checked");
		        }
		
		    });
		    //有一个不选上则全不选
		    $('input[type="checkbox"][name="messageId"]').click(function () {
		           var ckall = true;
		           $('input[type="checkbox"][name="messageId"]').each(function (){
		           	if (!this.checked){ 
		           		ckall = false;  
		   				//直接退出循环,不在进行each循环
		           		return false; 
		          	    }});
				$('input[type="checkbox"][name="all_check"]').attr('checked', ckall);
		    });		    
		});
</script>
</head>
<body>
    <!-- title -->
	<div class="mainbox"><div class=page_title><h3>短信管理  -- ${CURRENT_MEETING}</h3></div></div>

	<!-- 标签 -->
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="短信列表" style="padding:10px;">

		<!-- search -->    
		<div class="page_tools">
		<p>此会议下短信可发条数为:<span style="color:red">${preCount}</span>条,已发条数为:<span style="color:red">${smsActCount}</span>条,剩余短信条数为:<span style="color:red">${canSendCount}</span>条<p>
		<form id="listMeetingFileForm" action="${ctx}/admin/pri/message/getMessageList.action">
            <input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
            <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
            <input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<table>
		    <tr>
			    <th style="width: 80px; ">短信内容：</th>
			    <td style="width: 400px; ">
					<input maxlength="35" type="text" style="width: 400px; " id="messages" name="messages" value="${messages}" maxlength="35" />
				</td>
		        <th style="width: 80px; ">发送状态：</th>
		        <td>
		            <select name="state">
						<option value="">请选择</option>
						<option value="1" <c:if test="${state eq 1}"> selected="selected"</c:if> >已发送</option>
						<option value="0" <c:if test="${state eq 0}"> selected="selected"</c:if> >未发送</option>
		            </select>
		        </td>
			    <td>
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
			        <a href="#" onClick="batchDelete()" class="btn_common btn_false">批量删除</a>
			    </td>
			</tr>
			</table>
		</form>
		</div>
		   
		<!-- list -->        
		<table class="page_datalist">
		<thead>
		<tr>
		<th width="10px" style="border-right: 0px"><input type="checkbox" name="all_check" id="all_check"></input></th>
		<th width="300px" align="left">短信内容</th>
		<th width="100px">接收人</th>
		<th width="100px">接收手机号</th>
		<th width="80px">发送状态</th>
		<th width="90px">定时发送</th>
		<th width="120px">发送时间</th>
		<th width="120px">创建时间</th>
		</tr>
		</thead>
                
		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="meetingSms" items="${pager.pageRecords}" varStatus="status">
		<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
       	<td><input type="checkbox" name="messageId" value="${meetingSms.id}"></input></td>
        <td align="left">
            <a href="${ctx}/admin/pri/message/getMessage.action?meetingId=${meetingId}&messageId=${meetingSms.id}">
            <c:choose>
			<c:when test="${fn:length(meetingSms.messages)>50}">${fn:substring(meetingSms.messages,0,50)}......</c:when>
            <c:otherwise>${meetingSms.messages} </c:otherwise>
            </c:choose>
            </a>
        </td>
        <td align="left">
		<c:if test="${not empty meetingSms.recipient}">
			<c:forEach var="recipient" items="${meetingSms.recipient}" varStatus="tttt">
			 ${recipient.name}
			</c:forEach>
		</c:if>
		</td>  
		<td align="left">
		<c:if test="${not empty meetingSms.recipient}">
			<c:forEach var="recipient" items="${meetingSms.recipient}" varStatus="tttt">${recipient.mobile}</c:forEach>
		</c:if>
		</td>  
		
		<td align="left">
		<c:choose>
			<c:when test="${ meetingSms.state eq 0}"><font style="color:red;">未发送</font> </c:when>
			<c:when test="${ meetingSms.state eq 1}">已发送</c:when>
		</c:choose>
		</td>       
		<td>
        <c:choose>
	        <c:when test="${meetingSms.delay eq true}">是</c:when>
			<c:otherwise>否</c:otherwise>
	    </c:choose>
        </td>   
        <td align="left">
		<c:choose>
			<c:when test="${meetingSms.delay eq true}">
				 <fmt:formatDate value="${meetingSms.sendTime}" type="both"  pattern="MM月d日 HH:mm"/>
			</c:when>
			<c:otherwise>
				<fmt:formatDate value="${meetingSms.createTime}" type="both"  pattern="MM月d日 HH:mm"/>
			</c:otherwise>
		</c:choose>
		</td>   
		<td align="left"><fmt:formatDate value="${meetingSms.createTime}" type="both"  pattern="MM月d日 HH:mm"/></td>   
		</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
		<tr class="datarow"><td colspan="10" align="center">此会议下无短信内容</td></tr>
		</c:otherwise>
		</c:choose>
		</table>
		<!-- pager -->         
		<div><%@ include file="/pages/common/page.jsp" %></div>
		</div>
		<div title="发送短信" link="${ctx}/admin/pri/message/preSendMessage.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
</body>
</html>