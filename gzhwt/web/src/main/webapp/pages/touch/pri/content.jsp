<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>

<style type="text/css">
.date {
	background-color: #c9c999;
}
.article strong{font-style:normal;font-weight:bold;}
</style>



	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">${content.contentTitle }</span></a></li>
        <li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    
     <div class="tab_c" style="display:block;">
	  	${content.content }
	</div>
	
<%@ include file="/pages/touch/common/footer.jsp" %>