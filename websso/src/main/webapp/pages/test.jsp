<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>


<html>
<head>
<title>系统列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
<link href='${ctx}/css/redmond/jquery-ui.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
${util_js}
${jquery_js}                          
${jquery_form_js}  

<script>







	function query(){
		$("#sbFrm").submit();
	}
	
	$("#tabs").css("display","block").tabs('resize');
	$(document).ready(function() {
		$(".easyui-tabs").tabs({  
			onSelect:function(title){ 
				alert(title);
				var tab = $(this).tabs("getTab", title); 
				var href = tab.attr("link");
				if (href) {
					location.href = href;
					return false;
				}
			}  
		});
	});
		
		
		
</script>

</head>
<body>


 <div id="mainTab" class="easyui-tabs" style="width:500px;height:250px;">  
    <div title="Tab1" style="padding:20px;display:none;">  
        tab1   
    </div>  
    <div title="Tab2" closable="true" style="overflow:auto;padding:20px;display:none;">  
        tab2   
    </div>  
    <div title="Tab3" iconCls="icon-reload" closable="true" style="padding:20px;display:none;">  
        tab3   
    </div>
</div>


 <div id="tab_menu" class="easyui-menu" style="width:150px;">  
        <div id="tab_menu-tabclose">关闭</div>  
     <div id="tab_menu-tabcloseall">关闭全部</div>  
       <div id="tab_menu-tabcloseother">关闭其他</div>  
        <div class="menu-sep"></div>  
       <div id="tab_menu-tabcloseright">关闭右侧标签</div>  
       <div id="tab_menu-tabcloseleft">关闭左侧标签</div>  

</div>  




</body>
</html>




