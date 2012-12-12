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

  <c:if test="${not empty mySystemLoginLst }">
  <c:forEach var="sys" items="${mySystemLoginLst}" varStatus="status">
  
   <tr>
    <td>${sys.appname}</td>
    <td><label>
    <a href="#" onclick="getUserLoginInfo('${sys.appId}','${userName}','${sys.account_Column_Name}','${sys.user_Name_Cfg}','${sys.password_Cfg}','${password}','${sys.form_action_url}','${sys.configId}');">
      <input type="button" name="进入" id="进入" value="进入系统" />
    </a>
    </label></td>
  </tr>
  </c:forEach>
  </c:if>
  
  
</table>

<form name="ssoFrm" id="ssoFrm" method="post" target="_blank">
 
</form>
  
</body>
</html>


<script>

/**
 *  * 获取此系统，此用户的登录信息，动态生成表单元素
 *     从而进行登录操作
 *      appId:应用的ID,userAccount:门户登录名
 *      accountColumnName：在t_idm_user表中的业务系统对应的用户账号字段名
 *      userNameCfg：用户参数名      passwordCfg：密码参数名
 *      passwordValue：密码的值
 *
 */
function getUserLoginInfo(appId,userAccount,accountColumnName,userNameCfg,passwordCfg,passwordValue,appLoginUrl,configId){
	//alert(appId+","+userAccount+","+accountColumnName+","+userNameCfg+","+passwordCfg+","+passwordValue+","+appLoginUrl);
	alert(configId);
	
	var columnNameValue="";
	//
    $.post(
            "${ctx}/sso/getUserLoginInfo.action",
            {
                "appId":appId,
                "accountColumnName":accountColumnName,
                "userAccount":userAccount,
                "password":passwordValue,
                "formatter":'',
                "configId":configId
            },
            function (data, textStatus) {
                if (textStatus == "success") {
                    if (data.result) {
                    	alert(data.password);
                    	//alert("columnNameValue:"+data.columnNameValue);
                    	columnNameValue=data.columnNameValue;
                    	//alert(userNameCfg);
                    	//创建input:text
                        $('<input />',{ 
                        type:"text", 
                        val:columnNameValue, 
                        name:userNameCfg 
                        }).appendTo("#ssoFrm"); 
                    	
                        $('<input />',{ 
                            type:"text", 
                            val:data.password, 
                            name:passwordCfg
                            }).appendTo("#ssoFrm"); 
                        
                        $("#ssoFrm").attr("action",appLoginUrl);
                        $("#ssoFrm").submit();
                    }
                    else {
                    	alert("msg:"+data.msg);
                    }
                } else {
                   alert("请求异常!");
                }
            },
            "json"
	);
	
	
	
  //创建input:text 
}



</script>
