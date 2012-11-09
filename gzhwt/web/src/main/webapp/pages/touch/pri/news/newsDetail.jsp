<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">新闻详情</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <div class="tab_c" style="display:block;">
    	<h3 class="info_title">${news.title }</h3>
    	<div class="home_pic" style="margin-bottom:0px;">
			<img id="home_pic" alt="${wd:limit(news.title,30) }" src="${serverUrl }${news.thumbnailImage}"/>
		</div>
        <div class="info_detail">
        	${wd:base64Decode(news.content) }
        </div>
    </div>
    
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	h3.info_title { text-align:center; display:block; font-size:24px; line-height:2; border-bottom:1px solid #ccc; font-weight:bold;}
	.info_detail { padding:10px; line-height:1.8; font-size:16px;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});
</script>