<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>土特产管理</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
</head>
<body>
	<div class="page_title" style="display:block"><h3>土特产管理  -- ${CURRENT_MEETING}</h3></div>
	<div class="easyui-tabs" border="false" style="padding:10px;">	
		<div title="土特产信息" style="padding:10px;">
		<s:if test="specialty != null">
			<div class="page_datalist" style="width:98%; border:1px dashed #000000; padding:10px; margin-bottom:10px;">
				<div style="color:#999;">${specialty.memo }</div>
				<div style="text-align:right; padding-right:10px;">
					发布时间：<s:date name="specialty.createTime" format="yyyy-MM-dd HH:mm" />&nbsp;&nbsp;&nbsp;&nbsp;
					有效状态：${specialty.state == 1 ? "有效" : "无效" }&nbsp;&nbsp;
					<a href="${ctx}/admin/pri/reception/specialty_editReq.action?meetingId=${meetingId }&meetingSpecialtyId=${specialty.id}">编辑</a>&nbsp;
					<a href="javascript:del(${specialty.id })">删除</a>
				</div>
			</div>
		</s:if>
		<s:else>
			<div class="page_datalist" style="margin:10px 0;">本会议尚未发布土特产信息</div>
			<div><a href="${ctx}/admin/pri/reception/specialty_addReq.action?meetingId=${meetingId}" id="submitBtn" class="btn_common btn_true">发布特产</a></div>
		</s:else>
		
		<div style="${empty specialty ? 'display:none;' : ''}"> <!-- begin 土特产信息 -->
		<div class="page_tools">
		<form id="mainForm" action="${ctx}/admin/pri/reception/specialty_show.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			
			<table>
		      <tr>
		        <th>土特产名称：</th>
		        <td><input type="text" id="name" name="localSpecialty.name" value="${localSpecialty.name }" style="width:250px;" /></td>
		        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a>&nbsp;&nbsp;
		        <a href="${ctx}/admin/pri/reception/specialty_addLocalSpecialtyReq.action?meetingId=${meetingId}&meetingSpecialtyId=${specialty.id}" id="queryForList" class="btn_common btn_true">添加特产</a>
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
	            	<th width="70%">土特产名称</th>
                    <th width="">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                 <c:choose>
                    <c:when test="${not empty pager.pageRecords}">
                        <c:forEach var="localSpec" items="${pager.pageRecords}" varStatus="status">
                            <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                                <td ></td>
                                <td>${localSpec.name }</td>
                                <td>
                                	<a href="${ctx}/admin/pri/reception/specialty_editLocalSpecialtyReq.action?meetingId=${meetingId }&meetingSpecialtyId=${specialty.id}&localSpecialtyId=${localSpec.id}">编辑</a>
                                 	<a href="#" onclick="removeLocal(${localSpec.id});">移除</a>
                               </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="5" align="center"> 没有具体的土特产发布信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
		<%@ include file="/pages/common/page.jsp" %>		
		</div>
		
		</div> <!-- end 土特产信息 -->
		<%-- 
		<div title="发布会议特产" link="${ctx}/admin/pri/reception/specialty_addReq.action?meetingId=${meetingId}" style="padding:10px;"></div>
		--%>
	</div>
<script type="text/javascript">
	
	// 删除
	function del(id) {
		if(confirm("确认要删除会议特产信息吗？")) {
			window.location.href = "${ctx}/admin/pri/reception/specialty_delete.action?meetingId=${meetingId }&meetingSpecialtyId="+ id;
		}
		return false;
	}
	
	//移除已经添加土特产信息
	// 删除
	function removeLocal(id) {
		if(confirm("确认要移除本特产信息吗？")) {
			window.location.href = "${ctx}/admin/pri/reception/specialty_removeLocalSpecialty.action?meetingId=${meetingId }&meetingSpecialtyId=${specialty.id}&localSpecialtyId="+ id;
		}
		return false;
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