<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>预览土特产信息</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}         
	${jmpopups_js} 
	${util_js}
	
<style type="text/css">
	.specialty_body {
		width:98%;
		padding:0px 10px;
	}
	
	.non_info {
		padding:10px;
		border: 1px solid #C1C1C1; 
		margin-bottom:30px;
		font-size:16px;
	}
	
	.header_info {
		font-size:16px;
		border:1px solid #C1C1C1;
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

<script>
	$(document).ready(function() {
		
		// 查询
		$("#queryForList").click(function(){
			$("#mainForm").submit();
		});
	});

</script>
</head>
<body>
	<div class="page_title">
		<h3>预览土特产信息  -- ${CURRENT_MEETING}</h3>
	</div>
	
		<form id="mainForm" action="${ctx}/admin/pri/reception/specialty_preview.action">
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" id="currentPage" name="currentPage" value="${pager.currentPage}"/>
			
		    <a href="#" id="queryForList" style="display:none;">搜 索</a>&nbsp;&nbsp;
		</form>
	
		<div class="specialty_body">
			<div class="page_tools">
	          <a href="${ctx}/admin/pri/reception/specialty_show.action?meetingId=${meetingId}">返回</a>
	       	</div>
	             
		<s:if test="specialty == null || specialty.state != 1">
            <div class="non_info">当前会议尚未发布本地土特产信息。</div>
        </s:if>
        <s:else>
            <div class="header_info">${specialty.memo }</div>
            
            <s:iterator var="sp" value="pager.pageRecords">
            <div class="specialty_info">
            	<c:if test="${not empty sp.image}">
         		<img class="specialty_image" src="${serverUrl}${sp.image}"></img>
         		</c:if>
         		<div class="specialty_name"><span>${sp.name }</span></div>
         		<div class="specialty_memo">${sp.memo }</div>
				<div class="clear"></div>
         	</div>
            </s:iterator>
		</s:else>
			<%@ include file="/pages/common/page.jsp" %> 
		</div>
	
</body>
</html>