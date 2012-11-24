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

		<title>消防监管</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<%
			DevMonitorxfDao dao=new DevMonitorxfDao();
				   List<DevMonitorxfBean> list= dao.getListByWhere
				   (
				   " and (DEVNAME IN ('漏水状态','消防监测'))"
				    + " and id IN ('1804002','2701001','2706002')");
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
			</tr>
			<%
				if(list!=null&&list.size()>0){
			           for(int i=0;i<list.size();i++){
			             DevMonitorxfBean bean= (DevMonitorxfBean)list.get(i);
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
				<td>
					<%=bean.getThreshold_min()%>
				</td>	
				<td>
					<%=bean.getThreshold_max()%>
				</td>					
			</tr>
			<%
           
           }
          
          }
       	
       
       %>

		</table>
	</body>
</html>
