<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %> 
<script type="text/javascript">
    // 加载右侧组织列表
    $(document).ready(function(){
    	
    	$("#queryForList").click (function() {
    		$("#list-div").load(
   				"${ctx}/admin/pri/statistics/statSmsByMeeting.action",
   				{
   					"meetingName":$("#meetingName").val(),
   					"totalPage":$("#totalPage").val(),
   					"currentPage":$("#currentPage").val()
   				}
   			);
    	});
    });
</script>
	<div class="page_title" style="display:block"><h3>统计查询 -- 短信统计</h3></div>
	
	<!-- 查询FORM Start-->
	<div class="page_tools">
	<form id="searchForm" action="${ctx}/admin/pri/statistics/statSmsByMeeting.action">
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
		<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table>
		<tr>
			<td>会议名称：</th>
			<td><input type="text" style="width:300px;" id="meetingName" name="meetingName" value="${meetingName}"/></td>
			<td><a href="#" id="queryForList" class="btn_common btn_true">查询</a></td>
		</tr>
		</table>
	</form>
	</div>
	<!-- 查询FORM End-->

	<div>
        <!-- 显示内容TABLE -->
		<table class="page_datalist">
		<thead>
		<tr>
			<th width="5%" align="left">会议号</th>
			<th width="20%">会议名称</th>
			<th width="8%">可发条数</th>
			<th width="8%">已发条数</th>
		</tr>
		</thead>

		<c:choose>
		<c:when test="${not empty pager.pageRecords}">
		<c:forEach var="obj" items="${pager.pageRecords}" varStatus="status">
		<tr <c:if test="${status.count % 2 ne 0}"> class="odd"</c:if>>
			<td>${obj[0]}</td>
			<td align="left">${obj[1]}</td>
			<td align="left">${obj[2]}</td>  
			<td align="left">${obj[3]}</td>             
		</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
		<tr class="datarow">
			<td colspan="4" align="center">无统计结果</td>
		</tr>
		</c:otherwise>
		</c:choose>
		</table>
		
		<%@ include file="/pages/common/page.jsp" %>
	</div>