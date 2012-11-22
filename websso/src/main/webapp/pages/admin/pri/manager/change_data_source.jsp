<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
${jquery_js}                          
${jquery_form_js}  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>切换数据源</title>
</head>
<body>

<form id="configFrm" name="configFrm" method="post" action="${ctx}/pri/admin/configSsoSystem.action">
  <table  border="1">
    <tr>
      <td colspan="2" align="center">配置切换数据源</td>
    </tr>
   
     <tr>
      <td colspan="2" >数据来源</td>
      <td>
     
    </tr>
    
    <tr>
      <td width="40%">数据库</td>
      <td width="60%"><input type="radio" name="radio" id="radio" value="radio" /> </td>
    </tr>

    <tr>
      <td width="40%">缓存文件</td>
      <td width="60%"><input type="radio" name="radio" id="radio" value="radio" /></td>
    </tr>
    
    
     <tr>
      <td colspan="2" align="center"><input type="button" name="button" id="button" onClick="" value="配置" />
     
    </tr>
    
  </table>
</form>

</body>
</html>


<script>
/**
 * 配置
 */
function configSsoSystem(){
	//alert("ddddd");
	$("#configFrm").submit();
}

function backToList(){
	var url="${ctx}/pri/admin/getSsoSystemList.action";
	document.location.href=url;
}
</script>