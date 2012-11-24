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

		<title>漏水监管</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<%
			DevMonitorlsDao dao=new DevMonitorlsDao();
				   List<DevMonitorlsBean> list= dao.getListByWhere
				   (
				       " and (DEVNAME IN ('地下一楼漏水监测', '十三楼漏水监测二', '十三楼漏水监测一', '十四楼漏水监测', '配电间漏水监测', '西机房北漏水监测','东机房东漏水监测','东机房西漏水监测','西机房南漏水监测','漏水监测1','漏水监测2','漏水模块（旧机房）','漏水状态'))"
				    + " and id IN ('1801010','1801001','1801002','1801003','1804001','1802001','1805000','1803001')"
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
			</tr>
			<%
				if(list!=null&&list.size()>0){
			           for(int i=0;i<list.size();i++){
			             DevMonitorlsBean bean= (DevMonitorlsBean)list.get(i);
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
