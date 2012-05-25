<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#queryForList").click (function() {
		$("#list-div").load(
			"${ctx}/admin/pri/statistics/interaction.action",
			{
				"treeId":$("#treeId").val(),
				"totalPage":$("#totalPage").val(),
				"currentPage":$("#currentPage").val()
			}
		);
	});
	$(".detail").click (function() {
		$("#list-div").load(
			"${ctx}/admin/pri/statistics/interaction.action",
			{
				"meetingId":$(this).attr('id'),
				"totalPage":$("#totalPage").val(),
				"currentPage":$("#currentPage").val()
			}
		);
	});
});
</script>
<div class="page_tools">
	<form id="pagerForm" action="${ctx}/admin/pri/statistics/interaction.action">
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
		<input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<input type="hidden" name="treeId" id="treeId" value="${treeId}"/>
		<input type="hidden" id="queryForList" />
	</form>

	<c:choose>
	<c:when test="${not empty pager.pageRecords}">
	<table class="page_datalist">
    	<thead>
			<tr>
				<th scope="col" width="40%">会议名称</th>
				<th scope="col" width="20%">私信条数</th>
				<th scope="col" width="20%">交流次数</th>
				<th scope="col" width="20%">评论次数</th>
			</tr>	    	
        </thead>
        <tbody>
		<c:forEach var="data" items="${pager.pageRecords}" varStatus="status">
		<tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
			<td align="left"><a href="#" class="detail" id="${data.meetingId}">${data.meetingName }</a></td>
			<td align="left">${data.messageCount }</td>
			<td align="left">${data.postCount }</td>
			<td align="left">${data.commentCount }</td>
		</tr>
		</c:forEach>
        </tbody>
    </table>
	</c:when>
	<c:otherwise>
	<table class="page_datalist">
	<tr class="datarow">
		<td align="center">没有互动交流统计信息.</td>
	</tr>
	</table>
	</c:otherwise>
	</c:choose>	
	
	<%@ include file="/pages/common/page.jsp" %>
</div>