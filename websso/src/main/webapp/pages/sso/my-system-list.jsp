<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
<title>我的系统列表</title>
${jquery_js}                          
${jquery_form_js}   
</head>

<body>
<table width="480" height="132" border="1">
  <tr>
    <td width="317">系统名称</td>
    <td width="147">点击进入</td>
  </tr>
  <tr>
    <td>会务系统</td>
    <td><label>
    <a href="#" onclick="jump('admin','111111','http://127.0.0.1/yf/admin/base/ssologin.action');">
      <input type="button" name="进入" id="进入" value="进入系统" />
    </a>
    </label></td>
  </tr>
  
  
  <c:if test="${not empty mySystemLoginLst }">
  <c:forEach var="sys" items="${mySystemLoginLst}" varStatus="status">
  
   <tr>
    <td>${sys.appname}</td>
    <td><label>
    <a href="#" onclick="jump('admin','111111','${sys.apploginurl}');">
      <input type="button" name="进入" id="进入" value="进入系统" />
    </a>
    </label></td>
  </tr>
  </c:forEach>
  </c:if>
  
  
</table>


<form name="ssoFrm" id="ssoFrm" method="post" target="_blank">
  <input type="hidden" name="userName" id="userName" />
  <input type="hidden" name="username" id="username" />
  <input type="hidden" name="password" id="password" />
  <input type="hidden" name="mobile"   id="mobile" />
</form>
  
</body>
</html>


<script>
/**
 * 跳转页面
 */
function jump(userName,password,url){
	//var url="http://127.0.0.1/yf/admin/base/ssologin.action";
	$("#userName").val(userName);
	$("#username").val(userName);
	$("#mobile").val(userName);
	$("#password").val(password);
	//alert("dddddd");
	$("#ssoFrm").attr("action",url);
	$("#ssoFrm").submit();
}
</script>
