<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>导入分桌信息</title>
	${admin_css}                                   
	${jquery_js}
  
  <script>
   function doSbFrm(){
	   var uploadTxt=$("#upload").val();
	   if(uploadTxt==''){
		   alert("请选择要上传的文件!");
		   return false;
	   }
	   var pos = uploadTxt.lastIndexOf(".");
 	   var lastname = uploadTxt.substring(pos,uploadTxt.length)
	   
	   if (lastname.toLowerCase()!=".xls")
	   {
		     alert("您上传的文件类型为"+lastname+"，文件类型必须为xls");
		     return false;
	   }
	   //文件大小判断
	   /*var image=new Image();
	   image.dynsrc=uploadTxt;
	   alert(image.fileSize)
	   if(image.fileSize > 2048000){
	   		alert("文件不能大于");
	   }*/
	   
	   $("#upFrm").submit();
   }
   function back2ListDinner(){
		window.location.href = "${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId="+${dinner.meetingId};
	}
  </script>
</head>
<body>
<%
	String retMsg=(String)request.getAttribute("retMsg");
	if(retMsg!=null&&!"".equals(retMsg)){
%>
	<script type="text/javascript">
	alert('<%=retMsg%>');
	</script>
<%
	}
%>

<div class="page_title">
		<h3><c:set var="section" value="${1 == dinner.section ? '早':2==dinner.section?'中':'晚'}"></c:set>
<c:set var="type" value="${1 == dinner.type ? '自助餐':2 == dinner.type ?'宴会餐':'其他'}"></c:set>
${dinner.dinnerDate } 在${dinner.address} 安排 ${section}餐(${dinner.startTime }-${dinner.endTime },${type})</h3>
</div>

<div class="page_form">	
<form id="upFrm" action="${ctx}/admin/pri/meeting/doImport.action" enctype="multipart/form-data" method="POST" >
<fieldset>
<dl>
<dt>分桌信息文件</dt>
<dd><input name="upload" id="upload" type="file"/>
	<input type="hidden"" name="dinner.id" id="dinnerId" value="${dinner.id}"/>
	<br>
	<span style="color:red">*已经分桌的用户将根据导入的数据进行更新；不在该会议的用户和数据不正确的记录将被忽略。</span>
</dd>
</dl>

</fieldset>
	<div class="neidi">&nbsp;</div>
   <div class="page_form_sub">
	       <div style="margin-bottom: 10px; margin-top: 10px;" align="left">
	        <a href="javascript:void();" onclick="doSbFrm();return false;"  class="btn_common btn_true">上传</a>
	        <a href="javascript:void();" onclick="back2ListDinner();return false;"  class="btn_common btn_false">返回列表</a>
	        </div>
	    </div>

</form>


<div class="crumbs2"><a href="${ctx}/xlstemplate/用餐分桌导入模版.xls">导入模板下载</a></div>
</div>

</body>
</html>