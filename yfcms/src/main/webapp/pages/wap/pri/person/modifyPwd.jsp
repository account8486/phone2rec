<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/wap/common/header.jsp" %>
<%@ include file="/pages/wap/common/navigation.jsp" %>
<div class="main">
	<div class="path">
     ${meeting_list_url} > ${param.menu_name}
    </div>
    
	<form id="mainForm" action="${ctx}/wap/pri/person/modifyPwd.action" method="post">
		<div style="margin-left:20px;">
			<div class="article">
		   		<font color="red">${errMsg }</font>
		   	</div>
		   	
		    <div class="article">
		   	<font color="red">* </font>当前密码：<br/>
		   	<input type="password" id="oldPass" name="oldPass" value="${oldPass}" maxlength="20"/>
		   	</div>
		   	
		   	<div class="article">
		   	<font color="red">* </font>新密码(4到20位数字)：<br/>
		   	<input type="password" id="pass" name="pass" value="${pass }" maxlength="20"/>
		   	</div>
		   	
		   	<div class="article">
		   	<font color="red">* </font>确认新密码：<br/>
		   	<input type="password" id="passAg" name="passAg" value="${passAg }" maxlength="20"/>
		   	</div>
		   	
		   	<div class="article" style="margin-top:10px;">
		   		<input style="margin-left:60px;" type="submit" value="&nbsp;保 存&nbsp;"/>&nbsp;
		   	</div>
	   	</div>
	</form>
	
	<%@ include file="/pages/wap/common/tools.jsp" %>
</div>
<%@ include file="/pages/wap/common/footer.jsp" %>