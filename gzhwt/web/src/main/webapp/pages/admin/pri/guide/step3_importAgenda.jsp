<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会议议程</title>
${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
<script type="text/javascript">
	$(function(){
		$("#backMeeting").click(function(){
			if(confirm("返回我的会议将结束会议向导，您确定退出会议向导吗？")) {
				window.location.href = "${ctx}/admin/pri/meeting/guide_finish.action?fromStep=step4&meeting.id=${meeting.id}";
			}
			
		});
		
		var intro="会议议程是会议很重要的环节,是对会议的具体划分和实际内容的讨论，主要包括议题,参议人员,地点,开始时间,结束时间等信息";
		$("#intro").html(intro);
		
	});
</script>
</head>
<body>



<div style="width: 75%;float:left;margin-left: 5px;margin-right: 20px;">
<div class="page_title"><h3>会议向导 -> 第三步：导入会议议程信息</h3></div>

		<div class="page_form5">	
		<form id="upFrm" action="${ctx}/admin/pri/agenda/agendaImport.action" enctype="multipart/form-data" method="POST" >
			<input type="hidden" name="guideStep" value="step3" />
		
			<fieldset>
				<legend>从电子表格导入议程</legend>
				<dl>
					<dt style="padding-top: 12px;">请选择电子表格</dt>
					<dd><input name="upload" id="upload" type="file" style="color:#666;"/>
						<span id="tip_file" style="display:none; color:red;">请选择电子表格。</span>
						<div><a href="${ctx}/xlstemplate/agenda_import_template.xls">点击下载议程导入模板</a></div>
					</dd>
				</dl>
			</fieldset>
			<div class="page_form_sub"> 
				<input type="hidden"" name="meetingId" id="meetingId" value="${meetingId}"/>
				<input type="hidden"" name="action" id="action" value="doImport"/>
			</div>
			<div id="importMsg" style="color:red;margin-left:35px;">${importMsg}</div>
		</form>
		
		<div class="page_form_sub">
	        <a href="${ctx}/admin/pri/meeting/guide_step2.action?fromStep=step3&meeting.id=${meetingId}" class="btn_common btn_true">上一步</a>
	        <a href="#" id="agendaImport"   class="btn_common btn_true">下一步</a>
	    </div>
	</div>
</div>

<jsp:include page="guideCommonRight.jsp" />
<div style="clear: both"></div>


</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
	$('#upload').blur(function() {
		$(this).removeClass("btn_false");
		$(this).addClass("btn_true");
	});
	
	$("#agendaImport").click(function(){
		if (isEmpty($("#upload").val())) {
			//$("#tip_file").show();
			if(confirm("您没有选择要导入的议程数据信息文件，确定忽略议程信息导入直接进入下一步吗？")) {
			   location.href = "${ctx}/admin/pri/meeting/guide_step4.action?guideStep=step4&meeting.id=${meetingId}";
		   	}
			return false;
		} 
		if ($(this).is('.btn_true')) {
			$(this).removeClass("btn_true");
			$(this).addClass("btn_false");
			$("form").submit();
			return false;
		}
	});
});
</script>