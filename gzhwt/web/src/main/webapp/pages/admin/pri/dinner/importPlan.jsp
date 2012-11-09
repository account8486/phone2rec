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
		window.location.href = "${ctx}/admin/pri/meeting/listMeetingDinner.action?meetingId="+${param.meetingId};
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
		<h3>导入用餐计划</h3>
</div>

<div class="page_form">	
	<form id="upFrm" action="${ctx}/admin/pri/meeting/doImportPlan.action" enctype="multipart/form-data" method="POST" >
	<fieldset>
	<dl>
	<dt>用餐计划文件</dt>
	<dd><input name="upload" id="upload" type="file"/>
		<input type="hidden"" name="meetingId" id="meetingId" value="${param.meetingId}"/>
		<br>
		<span style="color:red"></span>
		<div class="crumbs2"><a href="${ctx}/xlstemplate/dine_import_template.xls">导入模板下载</a></div>
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
	
	<div class=crumbs2></div>
</div>

</body>
</html>