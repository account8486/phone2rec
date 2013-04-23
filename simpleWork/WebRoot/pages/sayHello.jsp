<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head><title>第一个Spring MVC实例</title></head> 
<%
String str = (String)request.getAttribute("helloWorld");
%>
<body> 
   <h1>您输入的欢迎语是<%=str%></h1>
   
   <c:if test="${not empty lstUser}">
   <c:forEach var="user"  items="${lstUser}" varStatus="status">
        <c:out value="${user.systemUserId}"></c:out>;
        <c:out value="${user.staffId}"></c:out>;
        <c:out value="${user.staffName}"></c:out>
        <br>
   </c:forEach>
   </c:if>
   
   
</body> 


</html>

