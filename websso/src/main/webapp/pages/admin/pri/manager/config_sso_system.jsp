<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/admin/pri/admin_top.jsp" %>
${jquery_js}                          
${jquery_form_js}  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>配置单点系统的业务系统</title>
</head>
<body>

<form id="configFrm" name="configFrm" method="post" action="${ctx}/pri/admin/configSsoSystem.action">
 <table width="98%" height="500" border="0" cellspacing="0" cellpadding="0"  class="mhjl-style" style="margin:0px;">
  
    <tr class=tab-add height="40">
      <td align="center">系统名称</td>
      <td align="left">
         <input type="hidden" name="hiddenField" id="hiddenField" />
        <input type="hidden" name="appid" id="appid" value="${systemEntity.appid}" />
      ${systemEntity.appname}</td>
    </tr>
    
     
    <tr class=tab-even height="40" >
      <td width="148" align="left">配置T_IDM_USER账号字段名</td>
      <td align="left"><input type="text" name="accountColumnName" id="accountColumnName" value="${systemConfig.accountColumnName}" /></td>
    </tr>
    
    
   <tr class=tab-even height="40" >
      <td width="148" align="center">配置参数(用户名)</td>
      <td width="418"><input type="text" name="userName" id="userName" value="${systemConfig.userNameCfg }" /></td>
    </tr>

   <tr class=tab-even height="40" >
      <td align="center">配置参数(密码)</td>
      <td><input type="text" name="password" id="password" value="${systemConfig.passwordCfg}" /></td>
    </tr>
    
  <tr class=tab-even height="40" >
      <td align="center">实际登录FORM(URL)</td>
      <td><input type="text" name="formActionUrl" id="formActionUrl" value="${systemConfig.formActionUrl}" /></td>
    </tr>
    
    <tr class=tab-even height="40" >
      <td align="center">是否启用</td>
      <td>
        <select name="usingEnabled" id="usingEnabled">
         <option>未设定</option>
         <option value="true">启用</option>
         <option value="false">禁用</option>
        </select>
      </td>
    </tr>
    
    <tr class=tab-even height="40" >
      <td align="center">是否加密</td>
      <td>
        <select name="passwordEncode" id="passwordEncode">
         <option>未设定</option>
         <option value="Y">加密</option>
         <option value="N">不加密</option>
        </select>
      </td>
    </tr>
    
        
    <tr class=tab-even height="40" >
      <td align="center">加密方式</td>
      <td>
        <select name="encodeStyle" id="encodeStyle">
         <option>未设定</option>
         <option value="md5">MD5</option>
         <option value="base64">BASE64</option>
         
        </select>
      </td>
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
<%@ include file="/pages/admin/pri/admin_bottom.jsp" %>
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


$(document).ready(function(){
	//alert('${systemConfig.usingEnabled}');
	$("#usingEnabled").val('${systemConfig.usingEnabled}');
	$("#passwordEncode").val('${systemConfig.passwordEncode}');
	$("#encodeStyle").val('${systemConfig.encodeStyle}');
});



</script>