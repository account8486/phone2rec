<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会议资料上传</title>

	${admin_css}                                   
	${jquery_js}                                
	${jquery_form_js}                                 
	${validate_js}                                
	${WdatePicker_js}                           
	${admin_js} 
</head>
<body>
<script language="JavaScript" type="text/javascript">
function cl()
{
window.opener='';
window.close();
}

function jump(){
	//http://127.0.0.1:8080/meeting/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=44
	document.location.href="${ctx}/pages/admin/pri/meeting/getMeetingUsers.action?meeting.id=${meetingId}&isAdmin=1";
}
</script>


<div class="page_title"><h3>会议成员导入结果</h3></div>


<div class="page_form" style="border:blue 1px solid;">
<fieldset>
 	<dl>
        	<dd>
        	导入结果:
        	</dd>
  </dl>
  
   <dl>
        	<dd>
        	${importMsg}
        	
        	
<c:if test="${importFlag=='F' }">
</c:if>
        	</dd>
        	
        	<dd>
      
        	</dd>
    </dl>
  

</fieldset>

</div>

     <div class="page_form_sub">
            <a href="##" onclick="jump();" id="submitBtn" class="btn_common btn_false">确定</a>    
    	 </div>
</body>
</html>