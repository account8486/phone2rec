<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会议向导</title>
    ${admin_css} ${jquery_js} ${jmpopups_js} ${util_js}
	<script type="text/javascript">
		$(document).ready(function() {
						
		});
    
    </script>
</head>
<body>
	<div class="page_title"><h3>会议向导  -> 启动</h3></div>

	<div>
		<div style="margin:20px;">
			<p>
				<h1>通过会议向导，可以在7步之内，快速地创建一个具有基本信息的会议，在向导结束后，也可以通过会议功能菜单，完善会议的其他信息。</h1>
				<br/>
				<h1 style="color:red;">注意：在向导完成第一步之后，会议信息已经创建，如果需要撤消，可以通过会议列表来删除所创建的新会议信息。</h1>
			</p>
			
			<img src="${ctx }/images/guide.jpg" style="float:right; margin-right:100px;" width="128" height="128"/>
			
			<br/><br/>
			
			<div class="page_form_sub">
		        <a href="${ctx}/admin/pri/meeting/guide_step1.action?guideStep=step1" id="submitBtn" class="btn_common btn_true">开&nbsp;&nbsp;始</a>    
		        <%--for testing 
		        <a href="${ctx}/admin/pri/meeting/guide_step2.action?guideStep=step2&meeting.id=169" id="submitBtn" class="btn_common btn_true">2</a> 
		        <a href="${ctx}/admin/pri/meeting/guide_step3.action?guideStep=step3&meeting.id=169" id="submitBtn" class="btn_common btn_true">3</a> 
		        <a href="${ctx}/admin/pri/meeting/guide_step4.action?guideStep=step4&meeting.id=169" id="submitBtn" class="btn_common btn_true">4</a>
		        --%> 
		    </div>
		</div>

	<%@ include file="/pages/common/page.jsp" %> 
	</div>

</body>
</html>