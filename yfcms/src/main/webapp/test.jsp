<%@ page language="java" import="com.wondertek.meeting.util.IPRequest" pageEncoding="UTF-8"%>
<html>
<body>
<%
	String userAgent = request.getHeader("user-agent");
	String localaddr =  IPRequest.getLocalAdress();
%>
<%= userAgent %>
<br>
<%= localaddr %>
</body>
</html>
