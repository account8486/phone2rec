<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>客户端版本上传</title>
  <link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css"/>
  <link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css"/>
  <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css"/>
	${admin_css}                                   
	${jquery_js}
	${util_js}
  <script>
   function doSbFrm(){
	   var uploadTxt = $("#upload").val();
	   if(uploadTxt == ''){
		   alert("请选择要上传的文件!");
		   return;
	   }
	   
	   var version = $("#version").val();

       if (isEmpty(version)) {
           $("#tip_version").html("请输入版本号。");
           $("#tip_version").show();
           return;
       }

       var bool = /^[0-9]{1,6}$/.test(version);
       if (bool == false) {
           $("#tip_version").html("请输入6位以内整数的版本号。");
           $("#tip_version").show();
           return;
       }
       
       $("#tip_version").hide();
	   $("#upFrm").submit();
   }
  </script>
</head>
<body>

<%
	String errMsg=(String)request.getAttribute("errMsg");
	if(errMsg!=null&&!"".equals(errMsg)){
%>
	<script type="text/javascript">
	alert('<%=errMsg%>');
	
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
			<h3>上传客户端安装文件</h3>
		</div>
	
		<div class="page_form">
		<form id="upFrm" action="${ctx}/admin/pri/version/upload.action" enctype="multipart/form-data" method="POST" >
			<fieldset>
			
			<dl>
				<dt class="title"><font color="red">*</font><label for="title">请选择文件：</label></dt>
				<dd>
					<input name="upload" id="upload" type="file"/><font color="red">&nbsp;系统最大支持100M文件。</font>
				</dd>
			</dl>
			
			<dl>
				<dt><font color="red">*</font><label for="title">版本号：</label></dt>
				<dd>
					<input class="half" id="version" name="version.version" type="text" />
					<font id="tip_version" style="display: none;" color="red"></font>
					<font color="red">(请严格输入客户端开发商提供的版本号。)</font>
				</dd>
			</dl>
			
			</fieldset>
			
			<div class="page_form_sub">
			 <a href="#" onclick="doSbFrm();" id="addUserBtn" class="btn_common btn_true">上传 </a> <font color="red"><s:fielderror/></font> 
			</div>
		</form>
		</div>
	</div>
</body>
</html>