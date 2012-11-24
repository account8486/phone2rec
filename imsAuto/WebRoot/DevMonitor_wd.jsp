<%@ page language="java" import="java.util.*,bean.*,dao.*"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>温度监管</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<%
			DevMonitorwdDao dao=new DevMonitorwdDao();
			List<DevMonitorwdBean> list= dao.getListByWhere
				(
				     "and  ch like '%温度%'" +  "and  devname like '%温湿度%'"
				);
		%>

	</head>

	<body >
		<table border="1">
			<tr>
				<td>
					序号
				</td>
				<td>
					设备名称
				</td>
				<td>
					编号
				</td>
				<td>
					设备属性
				</td>
				<td>
					监控值
				</td>
				<td>
					最小阀值
				</td>
				<td>
					最大阀值
				</td>
				<td>
					判断标志
				</td>
			</tr>
			<%
				if(list!=null&&list.size()>0){
			           for(int i=0;i<list.size();i++){
			             DevMonitorwdBean bean=(DevMonitorwdBean)list.get(i);
			%>
			<tr>
				<td>
					<%=bean.getOrderno() %>
				</td>

				<td>
					<%=bean.getDevname() %>
				</td>

				<td>
					<%=bean.getId() %>
				</td>

				<td>
					<%=bean.getCh() %>
				</td>

				<td>
					<%=bean.getValue() %>
				</td>
				
				<% if (bean.getThreshold_min()==null){ %>
					<td align="center">
					  ---
					</td >
					<% }else{%>
					<td align="center">
					  <%=bean.getThreshold_min()%>
					</td>
					
				<%} %>
					<% if (bean.getThreshold_max()==null){ %>
					<td align="center">
					  ---
					</td>
					<% }else{%>
					<td align="center">
					  <%=bean.getThreshold_max()%>
					</td>
					
				<%} %>				
					<td>
					<%=bean.getFlag() %>
					</td>
			</tr>
			<%
            }
          }
       %>

		</table>
	</body>
</html>
