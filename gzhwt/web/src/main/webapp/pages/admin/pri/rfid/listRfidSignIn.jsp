<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>RFID签到管理</title>
    ${admin_css}
    ${jquery_js}
    ${util_js}
    
    <link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>签到记录  -- ${CURRENT_MEETING}</h3>
	</div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<!--  <div title="签到管理" link="${ctx }/admin/pri/sign/list.action?meetingId=${rfidSignIn.meetingId}" style="padding:10px;"></div>-->
		<div title="签到记录" selected="true" style="padding:10px;"></div>
		<!--<div title="签到统计" link="${ctx }/admin/pri/meeting/card_rfidSignInForStaic.action?rfidSignIn.meetingId=${rfidSignIn.meetingId}" style="padding:10px;"></div>-->	
		<div title="签到事件" link="${ctx }/admin/pri/meeting/signEvent_findAll.action?meetingId=${rfidSignIn.meetingId}" style="padding:10px;"></div>	
	</div>
	
	<div class="page_tools page_serach">
	<form id="mainForm" action="${ctx }/admin/pri/meeting/card_listRfidSignIn.action">
		<input type="hidden" id="meetingId" name="rfidSignIn.meetingId" value="${rfidSignIn.meetingId}"/>
		<input type="hidden" id="userId" name="rfidSignIn.user.id" value="${rfidSignIn.user.id}"/>
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table width="98%">
	      <tr>
	        <th width="5%">签到事件：</th>
	        <td width="10%">
	        	<select name="rfidSignIn.signEvent.id">
	        		<option value="">请选择</option>
	        		<c:forEach items="${signEvents }" var="event">
	        		<option value="${event.id }" ${rfidSignIn.signEvent.id == event.id ? "selected" : "" }>${event.eventName }</option>
	        		</c:forEach>
	        	</select>
	        </td>
	        <th width="5%">签到人：</th>
	        <td width="5%"><input type="text" id="mobile" name="rfidSignIn.user.name" value="${rfidSignIn.user.name }" style="width:100px;"/></td>
	        <th width="5%">签到状态：</th>
	        <td width="12%">
	        	<select name="rfidSignIn.signState" style="width:80px;">
	        		<option value="">请选择</option>
	        		<option value="0" ${rfidSignIn.signState == 0 ? "selected" : "" }>无效签到</option>
	        		<option value="1" ${rfidSignIn.signState == 1 ? "selected" : "" }>正常签到</option>
	        		<option value="2" ${rfidSignIn.signState == 2 ? "selected" : "" }>正常签退</option>
	        		<option value="3" ${rfidSignIn.signState == 3 ? "selected" : "" }>迟到</option>
	        		<option value="4" ${rfidSignIn.signState == 4 ? "selected" : "" }>早退</option>
	        	</select>
	        </td>
	        <td>
	        	<a href="#" id="queryForList" class="btn_common btn_true">搜 索</a>
	        </td>
	      </tr>
	    </table>
	</form>
	</div>
	
	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	                <th width="8%">会议ID</th>
	                <th width="25%">签到事件</th>
	                <th width="8%" >签到人</th>
	                <th width="13%" >手机号码</th>
                    <th width="15%" >签到时间</th>
                    <th>签到状态</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="sign" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${sign.meetingId }</td>
                                <td>${sign.signEvent.eventName}</td>
                                <td>${sign.user.name }</td>
                                <td>${sign.user.mobile }</td>
                                <td >${sign.signDate }&nbsp;${sign.signTime }</td>
                                <td>
                                	<c:choose>
                                		<c:when test="${sign.signType == 1 && sign.signState == 1 }">正常签到</c:when>
                                		<c:when test="${sign.signType == 1 && sign.signState == 3 }"><span style="color:red">迟到</span></c:when>
                                		<c:when test="${sign.signType == 1 && sign.signState == 0 }"><span style="color:red">无效签到</span></c:when>
                                		<c:when test="${sign.signType == 2 && sign.signState == 2 }">正常签退</c:when>
                                		<c:when test="${sign.signType == 2 && sign.signState == 4 }"><span style="color:red">早退</span></c:when>
                                		<c:when test="${sign.signType == 2 && sign.signState == 0 }"><span style="color:red">无效签 退</span></c:when>
                                	</c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center"> 没有RFID签到信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
<script type="text/javascript">
	
	$(document).ready(function(){
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
		
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
	});
	
    jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'mobile':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>