<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../pages/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会议向导</title>

	${admin_css}                                   
	${jquery_js}
	${style_css}
    ${jquery_js}   
    ${jmpopups_js} ${util_js}  
	  
  <script>
	function doFinish() {
		if(confirm("确定结束此会议向导操作吗？")) {
			location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meetingId}";
		}
	}
  
	$(document).ready(function() {
		var intro="参会人员主要指参加会议的人员,在会议期间可以浏览会议信息,并和可以和其他会议人员进行互动";
		$("#intro").html(intro);
	});
		
		
  
   function doSbFrm(){
	   var uploadTxt=$("#upload").val();
	   if(uploadTxt==''){
		   //alert("请选择要导入的模板!");
		   if(confirm("您没有选择要导入的参会人员数据文件，确定忽略参会人员信息导入直接进入下一步吗？")) {
			   location.href = "${ctx}/admin/pri/meeting/guide_step3.action?guideStep=step3&meeting.id=${meeting.id}";
		   }
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


<script type="text/javascript">
	$(function(){
		$("#backMeeting").click(function(){
			if(confirm("返回我的会议将结束会议向导，您确定退出会议向导吗？")) {
				window.location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meeting.id}";
			}
			
		});
		
	});
</script>


<div style="width: 75%;float:left;margin-left: 5px;margin-right: 20px;">
			<div class="page_title">
			<h3>会议向导 -> 第二步：导入参会人员信息</h3>
		</div>
		
		<div class="page_form5">	
		<form id="upFrm" action="${ctx}/admin/pri/import/doImportUser.action" enctype="multipart/form-data" method="POST" >
		<input type="hidden" name="guideStep" value="step2" />
		
		<fieldset>
		<dl>
		<dt>请选择导入模板</dt>
		<dd><input name="upload" id="upload" type="file"/>
			<input type="hidden"" name="meetingId" id="meetingId" value="${meeting.id}"/>
			<br>
			<span style="color:red">如果已经导入过模板中的用户信息,系统将以最后一次导入的信息为准。</span>
			<div style="font-size:100px;"><a href="${ctx}/xlstemplate/user_import_template.xls">
				<span>点击此处下载导入模板</span></a></div>
		</dd>
		</dl>
		
		
		<dl>
		<dt>会员级别</dt>
		<dd>
		<select name="memberLevel">
		<option value="1">1级</option>
		<option value="2">2级</option>
		<option value="3">3级</option>
		<option value="4">4级</option>
		<option value="5">5级</option>
		</select>
		</dd>
		</dl>
		
		</fieldset>
		
		</form>
		
			<div class="page_form_sub">
		        <a href="${ctx}/admin/pri/meeting/guide_step1.action?fromStep=step2&meeting.id=${meeting.id}" class="btn_common btn_true">上一步</a>
		        <a href="#" onclick="doSbFrm();"  class="btn_common btn_true">下一步</a>
		    </div>
		</div>

</div>


<jsp:include page="guideCommonRight.jsp" />
<div style="clear: both"></div>

</body>
</html>