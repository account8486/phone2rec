<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">新闻中心</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	<form id="listForm" action="${ctx}/touch/pri/news/show.action">
			<input type="hidden" id="menu_id" name="menu_id" value="${param.menu_id}"/>
			<input type="hidden" id="meetingId" name="meetingId" value="${meetingId}"/>
			<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
	        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		</form>
		<ul class="info_list">
		<s:iterator var="entity" value="pager.pageRecords">
            <li>
            	<a href="${ctx }/touch/pri/news/detail.action?meetingId=${meetingId}&id=${entity.id}&menu_id=${param.menu_id}&menu_name=${param.menu_name}">${wd:limit(entity.title,30) }</a>
            </li>
        </s:iterator>
        </ul>
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