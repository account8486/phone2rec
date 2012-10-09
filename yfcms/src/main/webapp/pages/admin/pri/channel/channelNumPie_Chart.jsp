<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>栏目图标统计分析</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".easyui-tabs").tabs({  
				onSelect:function(title){  
					var tab = $(this).tabs("getTab", title); 
					var href = tab.attr("link");
					if (href) {
						location.href = href;
						showLoading(title);
						return false;
					}
				}  
			});	
		});
    </script>
</head>
<body>
<div class="easyui-tabs" border="false" style="padding:10px;">	
 <div title="柱状图" link="${ctx}/admin/pri/graphic/genChannelNumBarChart.action"  style="padding:10px;"></div>
 <div title="饼状图" style="padding:10px;" selected="true" >
 <img src="${graphURL}" width=600 height=400 border=0 usemap="#${filename}"> 
 </img></div>

 </div>

</body>




