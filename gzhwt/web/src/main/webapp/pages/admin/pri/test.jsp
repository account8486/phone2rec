<%@ page language="java" import="com.wondertek.meeting.util.IPRequest" pageEncoding="UTF-8"%>
<%@ page import="java.nio.charset.Charset, java.util.*" %>
<html>
<body>
<%
	String userAgent = request.getHeader("user-agent");
	String localaddr =  IPRequest.getLocalAdress();
%>
<%= userAgent %>
<br>
<%= localaddr %>
<br>

<% 
out.println("<br>当前JRE：" + System.getProperty("java.version")); 
out.println("<br>当前JVM的默认字符集：" + Charset.defaultCharset()); 
out.println("<br>当前JRE可用的字符集列表：\n" + genJVMCharset()); 
out.println("<br>当前JVM运行时系统属性列表\n：" + genJVMProperties()); 
%>

<%!
/** 
 * 获取JVM所支持的字符集列表（格式为：字符集标准名称:[别名，别名...] 
 * 
 * @return 字符集列表 
 */ 
public static String genJVMCharset() { 
        StringBuilder sb = new StringBuilder(); 
        SortedMap<String, Charset> map = Charset.availableCharsets(); 
        for (Map.Entry<String, Charset> entry : map.entrySet()) { 
                sb.append(entry.getKey()).append(":").append(entry.getValue().aliases()).append("\n"); 
        } 
        return sb.toString(); 
} 

/** 
 * 获取当前JVM运行时系统属性信息,并按照名称进行排序 
 * 
 * @return 系统属性信息 
 */ 
public static String genJVMProperties() { 
        StringBuilder sb = new StringBuilder(); 
        Properties props = System.getProperties(); 
        List<String> keylist = new ArrayList<String>(); 
        for (Object o : props.keySet()) { 
                keylist.add(o.toString()); 
        } 
        Collections.sort(keylist, String.CASE_INSENSITIVE_ORDER); 
        for (String s : keylist) { 
                sb.append(s).append("=").append(props.get(s)).append("\n"); 
        } 
        return sb.toString(); 
} 

%>
</body>
</html>
