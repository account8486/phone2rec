<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

<%@ include file="/pages/portal/common/header.jsp" %>

<style type="text/css">
	.non_info {
		padding:10px;
		border: 1px solid #C1C1C1; 
		margin-bottom:300px;
		font-size:16px;
	}
	
	.header_info {
		font-size:16px;
		border:1px solid #C1C1C1;
		margin:20px 0px;
		padding:10px;
		background:#F0F0F0;
	}
	
	.specialty_info {
		border:1px dashed #666;
		margin:20px 0px;
	}
	
	.specialty_info .specialty_image {
		width:180px;
		height:120px;
		float:right;
		margin:10px;
	}
	
	.specialty_info .specialty_name {
		font-weight:bold;
		font-size:20px;
		padding:10px;
	}
	
	.specialty_info .specialty_memo {
		padding:10px;
	}
	
	.clear {
		float: none;
		clear: both;
	}
</style>

	<div class="w960">
	<div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
		<div>
		<form id="listForm" action="${ctx}/portal/pri/reception/specialty_show.action">
			<input type="hidden" id="menu_id" name="menu_id" />
			<input type="hidden" id="menu_name" name="menu_name" value="${param.menu_name}" />
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
			<a href="#" id="queryForList" onclick="query();" style="display:none;" class="btn_blue">搜 索</a> 
		</form>
		</div>
	
		<div>
		<s:if test="specialty == null">
            <div class="non_info">当前会议尚未发布本地土特产信息。</div>
        </s:if>
        <s:else>
            <div class="header_info">${specialty.memo }</div>
            
            <s:iterator var="sp" value="pager.pageRecords">
            <div class="specialty_info">
         		<img class="specialty_image" src="${serverUrl}${sp.image}"></img>
         		<div class="specialty_name"><span>${sp.name }</span></div>
         		<div class="specialty_memo">${sp.memo }</div>
				<div class="clear"></div>
         	</div>
            </s:iterator>
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