<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
${jquery_js}                          
${jquery_form_js}  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>配置单点系统的业务系统</title>
</head>
<body>

<form id="configFrm" name="configFrm" method="post" action="${ctx}/pri/admin/configSsoSystem.action">
  <table width="582" border="1">
    <tr>
      <td colspan="2" align="center">配置系统登录参数</td>
    </tr>
   
     <tr>
      <td align="center">系统名称</td>
      <td>
         <input type="hidden" name="hiddenField" id="hiddenField" />
        <input type="hidden" name="appid" id="appid" value="${systemEntity.appid}" />
      ${systemEntity.appname}</td>
    </tr>
    
     
    <tr>
      <td width="148" align="center">配置T_IDM_USER账号字段名</td>
      <td width="418"><input type="text" name="accountColumnName" id="accountColumnName" value="${systemConfig.accountColumnName}" /></td>
    </tr>
    
    
    <tr>
      <td width="148" align="center">配置参数(用户名)</td>
      <td width="418"><input type="text" name="userName" id="userName" value="${systemConfig.userNameCfg }" /></td>
    </tr>

    <tr>
      <td align="center">配置参数(密码)</td>
      <td><input type="text" name="password" id="password" value="${systemConfig.passwordCfg}" /></td>
    </tr>
    
    <tr>
      <td align="center">实际登录FORM(URL)</td>
      <td><input type="text" name="formActionUrl" id="formActionUrl" value="${systemConfig.formActionUrl}" /></td>
    </tr>
    
    
    
    <c:if test="${not empty systemConfig }">
      <input type="hidden" name="systemCofigId" id="systemCofigId" value="${systemConfig.id}" />
    </c:if>
    
    
     <tr>
      <td colspan="2" align="center"><input type="button" name="button" id="button" onClick="configSsoSystem()" value="配置系统参数" />
      <input type="button" name="button2" id="button2" value="返回系统列表"  onclick="backToList()"/></td>
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