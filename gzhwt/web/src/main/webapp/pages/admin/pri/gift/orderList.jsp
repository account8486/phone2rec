<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>礼品订单管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block"><h3>礼品管理  -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">
		<div title="礼品列表" link="${ctx}/admin/pri/gift/list.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="礼品订单" selected="true" style="padding:10px;">
		<div class="page_tools">
		<form id="mainForm" action="${ctx}/admin/pri/gift/orderList.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<table>
			  <tr>
				<th>用户手机：</th>
				<td><input type="text" id="userMobile" name="giftOrder.user.mobile"
									   value="${giftOrder.user.mobile }"/></td>
				<th>礼品名称：</th>
				<td><input type="text" id="giftName" name="giftOrder.gift.name"
									   value="${giftOrder.gift.name }"/></td>
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
	                <th width="80">手机号码</th>
	                <th width="160" >礼品名称</th>
	                <th width="60" >价格</th>
	                <th width="60" >数量</th>
	                <th width="60" >应付款</th>
                    <th width="120" >更新时间</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="giftOrder" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${giftOrder.user.mobile }</td>
                                <td>${fn:substring(giftOrder.gift.name, 0, 30) }</td>
                                <td><fmt:formatNumber value="${giftOrder.gift.price }" type="currency" pattern="#0.00元"/> </td>
                                <td>${giftOrder.amount }</td>
                                <td><fmt:formatNumber value="${giftOrder.totalPrice }" type="currency" pattern="#0.00元"/> </td>
                                <td ><fmt:formatDate value="${giftOrder.modifyTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 HH:mm"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="7" align="center"> 没有礼品订单信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>
	</div>
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
		// 查询
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
	});
	
    jQuery(document).keypress(function(e){
    	if(e.which == 13 ) {
    		var act = document.activeElement.id;
    		switch(act){
    			case 'giftName':
    			case 'userMobile':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>