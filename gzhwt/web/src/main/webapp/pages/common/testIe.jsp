<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>


<%

java.util.Enumeration names = request.getHeaderNames(); 
while (names.hasMoreElements()) { 
String name = (String) names.nextElement(); 
out.println(name +  "<br/>"); 
out.println("["+name + "]:" + request.getHeader(name) + "<br/>"); 
}

%>
