<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>礼品管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block"><h3>礼品管理  -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="礼品列表" style="padding:10px;">
		<div class="page_tools">
	<form id="mainForm" action="${ctx}/admin/pri/gift/list.action">
		<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
		<table>
	      <tr>
	        <th>礼品名称：</th>
	        <td><input type="text" id="giftName" name="gift.name"
                                   value="${gift.name }"/></td>
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
	                <th width="80">会议ID</th>
	                <th width="160" >礼品名称</th>
	                <th width="60" >价格</th>
                    <th width="120" >更新时间</th>
                    <th width="200">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="gift" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${gift.meetingId }</td>
                                <td>${fn:substring(gift.name, 0, 38) }</td>
                                <td><fmt:formatNumber value="${gift.price }" type="currency" pattern="#0.00元"/> </td>
                                <td ><fmt:formatDate value="${gift.modifyTime}"
                                                                 type="both"
                                                                 pattern="MM月d日 HH:mm"/></td>
                                <td><a href="${ctx}/admin/pri/gift/goUpdate.action?meetingId=${meetingId }&id=${gift.id}">编辑</a>
                                <a href="#" onclick="previewGift(${gift.id});">预览</a>
                                <a href="#" onclick="delGift(${gift.id});">删除</a></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="5" align="center"> 没有礼品信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>		
		</div>
		<div title="新增礼品" link="${ctx}/admin/pri/gift/goAdd.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="礼品订单" link="${ctx}/admin/pri/gift/orderList.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
<script type="text/javascript">
	
	// 删除
	function delGift(id)
	{
		if(confirm("您确认要删除该礼品吗？"))
		{
			window.location.href = "${ctx}/admin/pri/gift/del.action?meetingId=${meetingId}&id="+ id;
		}
	}
	
	// 预览
	function previewGift(id){
		url = "${ctx}/admin/pri/gift/preview.action?meetingId=${meetingId}&id="+id;
		window.open(url, "_blank", "location=no,menubar=no,scrollbars=yes,resizable=yes,status=no,height=600,width=800,top=200,left=200");
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
    			case 'giftName':$('#queryForList').click();break;
    			case 'jumpPage':jumpTo();break;
    		}
    	} 
    })
</script>
</body>
</html>