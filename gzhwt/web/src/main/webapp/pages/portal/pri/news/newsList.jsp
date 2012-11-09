<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

	<div class="w960">
	<div class="cbox">
		<div class="title"><h5 id="caption">${param.menu_name}</h5></div>
	</div>
		<div>
		<form id="listForm" action="${ctx}/portal/pri/news/show.action">
			<input type="hidden" id="menu_id" name="menu_id" />
			<input type="hidden" id="menu_name" name="menu_name" value="${param.menu_name}" />
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<a href="#" id="queryForList" onclick="query();" style="display:none;" class="btn_blue">搜 索</a> 
		</form>
		</div>
	
		<div>
		<s:if test="pager.total == 0">
            <div class="non_info">当前会议尚未发布新闻信息。</div>
        </s:if>
        <s:else>
            <div>
         		<table  class="content_table" >
                <thead>
                   <tr >	                   
                    <th style="width:75%">标题</th>
                    <th style="width:20%;">发布时间</th>
                </tr>
                </thead> 
                <tbody> 
                <s:iterator var="entity" value="pager.pageRecords">
                  <tr>
                    <td title="${entity.title }"><a href="${ctx }/portal/pri/news/detail.action?meetingId=${meetingId}&id=${entity.id}&menu_id=${param.menu_id}&menu_name=${param.menu_name}" target="_blank">${wd:limit(entity.title,40) }</a></td>
                    <td><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                  </tr>
                  </s:iterator>
                </tbody>
                </table>
         	</div>
		</s:else>
			<%@ include file="/pages/common/page.jsp" %> 
		</div>

	</div>

<%@ include file="/pages/portal/common/footer.html" %>


<script type="text/javascript">
	$(function() {
		var menu_id = getMenuId();
		$('#menu_id').val(menu_id);
	});
	
	function query() {
        $('#listForm').submit();
    }
	
</script>