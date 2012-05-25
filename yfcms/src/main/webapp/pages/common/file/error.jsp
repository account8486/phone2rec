<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
function CloseWindow(){
	  window.opener=null;
	  window.open('','_self');
	  window.close();	
	} 
	
alert("文件不存在,请联系会议管理员.");
CloseWindow();
</script>