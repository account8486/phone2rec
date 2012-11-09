<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>卡牌管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	 ${util_js}
</head>
<body>
	<div class="page_title" style="display:block"><h3>卡牌管理  -- ${CURRENT_MEETING}</h3></div>
	
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="卡牌信息" link="${ctx }/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}" style="padding:10px;"></div>	
		<div title="卡牌发放" selected="true" style="padding:10px;"></div>
		<div title="自动发卡" link="${ctx}/admin/pri/meeting/card_issueReq.action?meetingId=${meetingId}&flag=auto" style="padding:10px;"></div>
		<%--
		<div title="触发任务管理" link="${ctx}/admin/pri/meeting/card_taskConfigReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		--%>
	</div>	
	
	<%-- 
	<div class="page_form_sub" style="float:left;">
        <a href="javascript:returnList();" id="retUserListBtn" class="btn_common btn_false">返回</a>
    </div>
    --%>
    
    <div class="page_tools">
		<form id="listUserForm" action="${ctx}/admin/pri/meeting/card_queryOwner.action" method="post">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table width="80%">
				<tr>
				<th style="width: 60px; ">姓 名：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px; " id="user.name" name="issueCard.owner.name" value="${issueCard.owner.name}"/></td>
           		<th style="width: 100px; ">手机：</th>
				<td style="width: 150px; "><input type="text" style="width: 120px; " id="user.mobile" name="issueCard.owner.mobile" value="${issueCard.owner.mobile}"/></td>
           		
				<td style="width: 250px; ">
					<a href="#" id="queryForList" onclick="query();" class="btn_common btn_true">搜 索</a>
				</td>
				</tr>
			</table>
		</form>
		</div>
	    
	<table class="page_datalist">
	    	<thead>
	        	<tr>
	            	<th width="2px" style="border-right:0"></th>
	            	<th width="15%">参会人姓名</th>
	            	<th width="25%">参会人手机</th>
                    <th width="">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	            <s:if test="pager.pageRecords.size() > 0">
                        <s:iterator var="u" value="pager.pageRecords" status="stat">
                            <tr class="${stat.count % 2 == 0 ? 'even' : '' }">
                                <td ></td>
                                <td>${u.name }</td>
                                <td>${u.mobile }</td>
                                <td>
                                <s:if test="userIssueStatus[#u.id]">
                                	已经发放过卡牌
                                </s:if>
                                <s:else>
                                	<a href="#" onclick="issueCard(${u.id});">发卡</a>
                                </s:else>
                                </td>
                            </tr>
                        </s:iterator>
                    </s:if>
                    <s:else>
                        <tr class="datarow">
                            <td colspan="5" align="center"> 没有搜索到参会人员信息.</td>
                        </tr>
                   </s:else>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>		
		
	</div>
<script type="text/javascript">

	$(function() {
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

	function query() {
		$("#listUserForm").submit();	
	}
	
	function issueCard(id) {
		window.location.href = "${ctx}/admin/pri/meeting/card_issueReq.action?meetingId=${meetingId}&issueCard.owner.id="+ id;
	}
	
	// 返回列表
	function returnList(){
		window.location.href = "${ctx}/admin/pri/meeting/card_queryOwnerReq.action?meetingId=${meetingId}";
	}
</script>
</body>
</html>