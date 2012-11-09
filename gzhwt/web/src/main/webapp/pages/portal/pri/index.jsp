<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/portal/common/header.jsp" %>

<div class="w960">


    <div class="cbody">
        <div style="min-height:200px">
        	
        </div>
	</div>
    
	
</div>

<%@ include file="/pages/portal/common/footer.html" %>
<script type="text/javascript">
$(document).ready(function() {
	//alert($("#menu li:first a").attr("href"));
	$("#menu li:first a").click();
	//alert($("#menu li:first a").attr("href"));
	if($("#menu li:first a").attr("href")!='undefined' && $("#menu li:first a").attr("href")!=null & $("#menu li:first a").attr("href")!='' ){
		showLoading("正在进入，请稍候...");
		window.location = $("#menu li:first a").attr("href");
	}


});
</script>