<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>卡牌管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block"><h3>卡牌管理  -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="卡牌信息" style="padding:10px;">
		<div class="page_tools">
		<form id="mainForm" action="${ctx}/admin/pri/meeting/card_listIssue.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			
			<table>
		      <tr>
		        <th>标签UID：</th>
		        <td><input type="text" id="uid" name="issueCard.card.uid" value="${issueCard.card.uid }" style="width:120px;"/></td>
		        <th>参会人：</th>
		        <td><input type="text" id="username" name="issueCard.owner.name" value="${issueCard.owner.name }" style="width:120px;"/></td>
		        <th>参会人手机：</th>
		        <td><input type="text" id="mobile" name="issueCard.owner.mobile" value="${issueCard.owner.mobile }" style="width:120px;"/></td>
		        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a>
				<font color="red">&nbsp;&nbsp;&nbsp;${errMsg }</font>
				</td>
		      </tr>
		    </table>
		</form>
	</div>	
		
	<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	            	<th width="20%">标签UID</th>
	            	<th width="10%">参会人</th>
	            	<th width="15%">参会人手机</th>
	                <th width="20%" >发放时间</th>
	                <th width="10%" >状态</th>
                    <th width="">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="issue" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${issue.card.uid }</td>
                                <td>${issue.owner.name }</td>
                                <td>${issue.owner.mobile }</td>
                                <td ><fmt:formatDate value="${issue.issueTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td>${issue.state == 1 ? "正常" : "挂失" }</td>
                                <td>
                                 	<a href="#" onclick="delCard(${issue.id});">删除</a>
                                	<c:if test="${issue.state == 1}">
                                	<a href="${ctx}/admin/pri/meeting/card_lostReq.action?meetingId=${meetingId }&issueCard.id=${issue.id}">挂失</a>
                                	</c:if>
                               </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="5" align="center"> 没有卡牌登记信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>		
		</div>
		<%-- 
		<div title="卡牌登记" link="${ctx}/admin/pri/meeting/card_editReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		--%>
		<div title="卡牌发放" link="${ctx}/admin/pri/meeting/card_queryOwnerReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="触发任务管理" link="${ctx}/admin/pri/meeting/card_taskConfigReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
<script type="text/javascript">
	
	// 删除
	function delCard(id)
	{
		if(confirm("确认要删除该卡牌登记吗？"))
		{
			window.location.href = "${ctx}/admin/pri/meeting/card_deleteIssue.action?meetingId=${meetingId}&issueCard.id="+ id;
		}
	}
	
	
	$(document).ready(function(){
		// 查询
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
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
	
    jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'uid':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>