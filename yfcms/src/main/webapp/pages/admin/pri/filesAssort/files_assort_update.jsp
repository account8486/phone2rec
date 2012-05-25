<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分类编辑-{CURRENT_MEETING}</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}   
	${jmpopups_js}      
    ${util_js}   
  <script>
   function doSbFrm(){
	   var uploadTxt=$("#upload").val();
	   if(uploadTxt==''){
		   alert("请选择要上传的文件!");
		   return;
	   }
	   
	   $("#addFrm").submit();
   }
   
   
   /**
    *返回列表
    */
   function backToList(){
	   var url="${ctx}/admin/pri/files/getFilesAssortPages.action?meetingId=${meetingFilesAssort.meetingId}";
	   window.location.href=url;
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
	
	try{   
		 window.opener.location.href;   
	   }catch(e){   
		    window.opener=null;   
		}   
	
	if(window.opener!=null){   
		//刷新父页面
		window.opener.location.reload();
	}else if(parent){
		window.parent.location.reload();
	} 

	
	
	window.close();
	</script>
<%
	}
%>


	<div class="mainbox">
		<div class="page_title">
		<h3>更新文件夹</h3>
	</div>
	
<div class="page_form">
<form id="addFrm" action="${ctx}/admin/pri/files/updateAssort.action"  method="POST" >

	        
<fieldset>
<dl>
<dt class="title"><label for="title">文件夹名称</label></dt>
<dd><input class="half" name="assortName" id="assortName" type="text" value="${meetingFilesAssort.assortName}" maxlength="20"/>
</dd>
</dl>

<dl>
<dt><label for="title">描述</label></dt>
<dd>
<input class="half" name="description" id="description" type="text"  value="${meetingFilesAssort.description}"  maxlength="40"/>
<input type="hidden" id="meetingId" name="meetingId" value="${meetingFilesAssort.meetingId}" />
<input type="hidden" id="id" name="id" value="${meetingFilesAssort.id}" />
</dd>
</dl>
</fieldset>


<div class="page_form_sub">
 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">更新</a> 
 <font color="red"><s:fielderror/></font> 
 <a href="#" onclick="backToList();" id="add" class="btn_common btn_false">返回列表 </a>
</div>

</form>
</div></div>
</body>
</html>