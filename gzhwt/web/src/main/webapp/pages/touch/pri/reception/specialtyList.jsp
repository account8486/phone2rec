<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">本地土特产</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>

<style type="text/css">
	.non_info {
		padding:10px;
		border: 1px solid #C1C1C1; 
		margin-bottom:300px;
		font-size:16px;
	}
	
	.header_info {
		font-size:14px;
		border:1px solid #C1C1C1;
		margin:10px;
		padding:10px;
		background:#F0F0F0;
	}
	
	.specialty_info {
		border:1px dashed #666;
		margin:10px;
	}
	
	.specialty_info .specialty_image {
		float:right;
		margin:10px;
	}
	
	.specialty_info .specialty_name {
		font-weight:bold;
		font-size:16px;
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

	<div class="tab_c" style="display:block;">
    	<form id="listForm" action="${ctx}/touch/pri/reception/specialty_show.action" method="post">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		</form>
        
        <div>
		<s:if test="specialty == null || specialty.state == 0">
            <div class="non_info">当前会议尚未发布本地土特产信息。</div>
        </s:if>
        <s:else>
            <div class="header_info">${specialty.memo }</div>
            
            <s:iterator var="sp" value="pager.pageRecords">
            <div class="specialty_info">
         		<c:if test="${not empty sp.image}">
         		<img class="specialty_image" src="${serverUrl}${sp.image}" width="120" height="80"></img>
         		</c:if>
         		<div class="specialty_name"><span>${sp.name }</span></div>
         		<div class="specialty_memo">${sp.memo }</div>
				<div class="clear"></div>
         	</div>
            </s:iterator>
		</s:else>
		</div>
    </div>
    
    
	
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	ul.info_list {}
	ul.info_list li { padding:10px; border-bottom:1px solid #ccc;}
	ul.info_list li.even { background:#f2f2f2;}
	ul.info_list li a { background:url(${ctx}/images/touch/icon-list-link.png) center right no-repeat; display:block; -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; line-height:32px; font-weight:bold; }
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});
</script>