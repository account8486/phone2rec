<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>页面主题配置</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/theme.css" />
    ${admin_css}
    ${jquery_js}
    ${util_js}
    
<script type="text/javascript">
	$(document).ready(function(){
		$("#saveTheme").click(function() {
			if(confirm("确定把所选择的页面主题设为当前会议的主题吗？")) {
				$("#mainForm").submit();
				return true;
			}
			return false;			
		});
	});
</script>
</head>
<body>
	<div class="page_title" style="display:block">
		<h3>页面主题配置 -- ${CURRENT_MEETING}</h3>
	</div>

	<div>
		<div class="container">
		<div class="currentThemeTitle">
			<h3>当前会议使用的页面主题 -- <span>${currentTheme.title}</span></h3>
		</div>
		
		<form id="mainForm" action="${ctx}/admin/pri/custom/savePageTheme.action">
			<input type="hidden" name="meetingId" value="${meetingId }" />
			
			<s:iterator value="pageThemeList" var="theme">
			<div class="goods">
				<div class="pic"><img src="${ctx}/css/${theme.name}/images/${theme.thumbnailName}" /></div>
				<div class="description">
					<div class="title">
						<label>
							<input type="radio" name="themeId" value="${theme.id }" ${currentTheme.id==theme.id ? "checked" : "" } />&nbsp;&nbsp;${theme.title}
						</label>
					</div>
					<div class="comments">${theme.description}</div>
				</div>
			</div>
			</s:iterator>
			
			<div class="clear"></div>
			
			<a href="#" id="saveTheme" class="btn_common btn_true">保存主题</a>
		</form>
		</div>
		
		<%@ include file="/pages/common/page.jsp" %>
	</div>
</body>
</html>