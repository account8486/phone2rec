<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab">
        <li id="li_home" class="act"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
<%@ include file="/pages/touch/common/footer.jsp" %>
<script>
	$(function(){
		$("#li_home").click();
	});
</script>