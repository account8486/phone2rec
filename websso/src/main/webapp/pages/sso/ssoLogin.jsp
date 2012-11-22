<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一页面</title>
${jquery_js}                          
${jquery_form_js}  

</head>


<body>
<form id="form1" name="form1" method="post" action="${ctx}/sso/integrateLogin.action">
<div align="center">

  <table width="544" border="1">
    <tr>
      <td colspan="2">统一页面</td>
    </tr>
    
    <tr>
      <td width="101">用户名</td>
      <td width="427"><input type="text" name="userName" id="userName" /></td></tr>
    <tr>
      <td>密码</td>
      <td><input type="password" name="password" id="password" /></td>
    </tr>
    
    <tr>
      <td colspan="2">
      	 <input type="submit" value="登录"/>
      </td>
    </tr>
  </table>
    </div>
</form>
</body>
</html>
