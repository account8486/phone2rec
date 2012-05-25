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
		<form id="mainForm" action="${ctx}/admin/pri/meeting/card_list.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			
			<table>
		      <tr>
		        <th>标签UID：</th>
		        <td><input type="text" id="uid" name="card.uid" value="${card.uid }"/></td>
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
	                <th width="30%">标签UID</th>
	                <th width="20%" >登记时间</th>
	                <th width="10%" >状态</th>
                    <th width="">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="card" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${card.uid }</td>
                                <td ><fmt:formatDate value="${card.registerTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td>${card.state == 1 ? "有效" : "作废" }</td>
                                <td><a href="${ctx}/admin/pri/meeting/card_editReq.action?meetingId=${meetingId }&card.id=${card.id}">编辑</a>
                                <a href="#" onclick="delCard(${card.id});">删除</a></td>
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
		<div title="卡牌登记" link="${ctx}/admin/pri/meeting/card_editReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="卡牌发放" link="${ctx}/admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}" style="padding:10px;"></div>
		<div title="卡牌挂失" link="${ctx}/admin/pri/meeting/card_listLoss.action?meetingId=${meetingId}" style="padding:10px;"></div>
	</div>
<script type="text/javascript">
	
	// 删除
	function delCard(id) {
		if(confirm("确认要删除该卡牌吗？")) {
			window.location.href = "${ctx}/admin/pri/meeting/card_delete.action?meetingId=${meetingId}&card.id="+ id;
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