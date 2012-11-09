<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会务通平台</title>
<link href="../../../css/reset.css" rel="stylesheet" type="text/css" />
<link href="../../../css/basic.css" rel="stylesheet" type="text/css" />
<link href="../../../.ss/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../../../js/jquery-jtemplates.js"></script>
<script type="text/javascript" src="../../../js/ajax-public.js"></script>
<script type="text/javascript" src="../../../css/style_temp.css"></script>

<style>
 #mytable {   
    padding: 0;
    margin: 0;   
    border-collapse:collapse;
}

td {
    border: 1px solid #C1DAD7;   
    background: #fff;
    font-size:15px;
    padding: 6px 6px 6px 12px;
    color: #4f6b72;
}

td.alt {
    background: #F5FAFA;
    color: #797268;
}
</style>

<script>
$(document).ready(function(){
	var originalUrl=window.location.href;
    var meetingId=getUrlParam(originalUrl,'meetingId');
    
    /**
    	通知公告   0
  		会议须知   1           
  		用餐安排   2	      
  		酒店信息   3	    
    */
    //通知通告
    var contentType="0";
    //alert(meetingId);
	var url="/client/pri/meeting/meetingContentList.action?meetingId="+meetingId+"&contentType="+contentType;
	function initData(data){
		 var meeting=data.meetingContentList;
		 
		 
		//填充模板
			$("#meetingContent").setTemplateElement("meetingContentTpl");
		    $("#meetingContent").processTemplate(data);
		 
	}
	
	ajaxRequest(url,initData);
	
	
	
});

</script>

</head>
<body>
	<div class="container"> 
		<%@ include file="../../portal/common/header.html" %> 
		
		<div class="mainbox">
			
			<!-- 循环体 -->
<div id="meetingContent">
<textarea id="meetingContentTpl" style="display:none">

   <table windth="100%">
	{#foreach $T.meetingContentList as meetingContent}
	
  	<tr>
    <td width="30%"><span>内容标题：</span></td>
    <td width="70%">{$T.meetingContent.contentTitle}</td></tr>
    <tr>
    <td><span>内容类型：</span></td><td>{$T.meetingContent.contentType}</td></tr>
    <tr>
    <td><span>内容正文：</span></td><td>{$T.meetingContent.content}</td></tr>
    
</div>
 {#/for}
 </table>
 
</textarea>
</div>
<!--  结束 -->
			
			
		</div>		
		
		<%@ include file="../../portal/common/footer.html" %> 
	</div>
</body>
</html>