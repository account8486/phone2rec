<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资料管理  -- ${CURRENT_MEETING}</title>
	${admin_css}                                   
	${jquery_js}                          
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}   
	${jmpopups_js}      
    ${util_js}   
  <script>
  

	  
	  $(document).ready(function() {
		  $("#isImgFold").val('${meetingFilesAssort.isImgFold}');
		  
		});
  
  
  
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
		<h3>资料管理  -- ${CURRENT_MEETING}</h3>
	</div>
	
<div class="page_form">
<form id="addFrm" action="${ctx}/admin/pri/files/updateAssort.action" enctype="multipart/form-data" method="POST" >

	        
<fieldset>
<dl>
<dt class="title"><label for="title">文件夹名称</label></dt>
<dd><input class="half" name="assortName" id="assortName" type="text" value="${meetingFilesAssort.assortName}" maxlength="20"/>
</dd>
</dl>

<dl>
					<dt class="title"><label for="title">属于图片文件夹</label></dt>
				   <dd>
					<select name="isImgFold" id="isImgFold" style="width:300px;">
					<option value="1"  <c:if test="${meetingFilesAssort.isImgFold eq 'true'}"> selected="selected"  </c:if>  >是</option>
					<option value="0"  <c:if test="${meetingFilesAssort.isImgFold eq 'false'}"> selected="selected"  </c:if>  >否</option>
					</select>
				   </dd>
</dl>

<dl>
					<dt class="title"><label for="title">上报单位</label></dt>
					<dd><input class="half" name="applyDepartment" id="applyDepartment" value="${meetingFilesAssort.applyDepartment}" type="text" maxlength="20"/>
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
				
				
				
					<dl>
					<dt class="title"><label for="title">图片封面</label></dt>
					<dd>
					<input class="half" name="pageImg" id="pageImg" type="file" maxlength="20"/> <br/>
					<img alt="" src="${serverUrl}/${meetingFilesAssort.pageImgUrl}" style="width:300px;height:200px;">
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