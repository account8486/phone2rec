<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户导入</title>

	${admin_css}                                   
	${jquery_js}
	${style_css}
    ${jquery_js}   
    ${jmpopups_js} ${util_js}  
    <link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/tabs.css">
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.js"></script>
	  
  <script>
  
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
		
		
  
   function doSbFrm(){
	   var uploadTxt=$("#upload").val();
	   if(uploadTxt==''){
		   alert("请选择要导入的模板!");
		   return;
	   }
	   
	   showLoading("正在导入用户......");
	   $("#upFrm").submit();
   }
   
   function returnToUserList(){
		document.location.href="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?isAdmin=1&meeting.id=${meeting.id}";
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
	//刷新父页面
	window.opener.location.reload();
	window.close();
	</script>
<%
	}
%>



<div class="page_title">
		<h3>参会人员管理 -- ${CURRENT_MEETING}</h3>
</div>

<div class="easyui-tabs" border="false" style="padding:10px;">	
	<div title="用户列表" link="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=${meeting.id}&isAdmin=1" style="padding:10px;"></div>
    <div title="批量添加" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=batch_add_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
    <div title="添加用户" link="${ctx}/pages/admin/pri/meeting/getMeetingById.action?returnType=manage_members&meeting.id=${meeting.id}" style="padding:10px;"></div>
	<div title="导入用户" selected="true" style="padding:10px;"></div>
	<div title="临时加入审批" link="${ctx}/admin/pri/apply/getUserApplyPager.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
	<div title="发言人管理" link="${ctx}/admin/pri/spokesman/getSpokesManLst.action?meetingId=${meeting.id}" style="padding:10px;"></div>
	<div title="嘉宾管理" link="${ctx}/admin/pri/guest/guest_findAllGuest.action?meetingId=${meeting.id}" style="padding:10px;"></div>	
	
</div>
	

<div class="page_form">	
<form id="upFrm" action="${ctx}/admin/pri/import/doImportUser.action" enctype="multipart/form-data" method="POST" >
<fieldset>
<dl>
<dt>请选择导入模板</dt>
<dd><input name="upload" id="upload" type="file"/>
	<input type="hidden"" name="meetingId" id="meetingId" value="${meeting.id}"/>
	
	<span style="color:red">*如果已经导入过模板中的用户信息,系统将以最后一次导入的信息为准。</span>
</dd>
</dl>


<dl>
<dt>会员级别</dt>
<dd>
<select name="memberLevel" style="width:20%;">
<option value="1">1级</option>
<option value="2">2级</option>
<option value="3">3级</option>
<option value="4">4级</option>
<option value="5">5级</option>
</select>
</dd>
</dl>

 <div class="page_form_sub">
	        <a href="#" onclick="doSbFrm();"  class="btn_common btn_true">导入</a>
	        <a href="##" onclick="returnToUserList();" id="submitBtn" class="btn_common btn_false">返回列表</a>    
 </div>
   
<div style="font-size:100px;"><a href="${ctx}/xlstemplate/user_import_template.xls"><span style="color:red;">点击此处下载导入模板</span></a></div>
</fieldset>


	    
	    

</form>



</div>

</body>
</html>