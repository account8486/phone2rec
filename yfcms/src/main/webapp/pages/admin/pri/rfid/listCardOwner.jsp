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
		<div title="待发卡的参会人员信息" style="padding:10px;">
		<div class="page_tools">
			<a href="${ctx}/admin/pri/meeting/card_queryOwnerReq.action?meetingId=${meetingId}" class="btn_common btn_true">返回</a>
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
	            <s:if test="ownerList.size() > 0">
                        <s:iterator var="u" value="ownerList" status="stat">
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
	</div>
<script type="text/javascript">
	function issueCard(id) {
		window.location.href = "${ctx}/admin/pri/meeting/card_issueReq.action?meetingId=${meetingId}&issueCard.owner.id="+ id;
	}
</script>
</body>
</html>