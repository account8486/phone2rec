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

		<title>配电监管</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<%
			DevMonitorpdDao dao=new DevMonitorpdDao();
			List<DevMonitorpdBean> list= dao.getListByWhere
				   (
				     " and DEVNAME IN ('13层北1区UPSA','13层南3区UPSA','13层南2区UPSA','13层南1区UPSA','14层北区UPSA','14层南区UPSA','13层北2区市电','13层北1区市电','13层南3区市电','13层南2区市电','13层南1区市电','14层北区市电','14层南区市电','调度大厅市电','13层北2区UPSB','13层北1区UPSB','13层南3区UPSB','13层南2区UPSB','13层南1区UPSB','14层北区UPSB','14层南区UPSB','调度大厅UPS','地下室市电','地下室UPS','十三层北二区配电柜1','十三层北一区配电柜1','十三层南一区配电柜2','十三层南二区配电柜1','十三层南三区配电柜1','十四楼北区配电柜3','十四楼北区配电柜1','十三层北二区配电柜3','十三层北一区配电柜3','十三层南一区配电柜1','十三层北一区配电柜5','十三层北一区配电柜4','十四楼南区配电柜3','十四楼南区配电柜2','十四楼南区配电柜5','十三层北二区配电柜2','十三层北一区配电柜2','十三层南一区配电柜3','十三层南二区配电柜2','十三层南三区配电柜2','十四楼北区配电柜4','十四楼北区配电柜2','十四楼调度大厅UPS配电柜','地下室市电配电柜','地下室UPS配电柜','地下室北侧UPS1','地下室北侧UPS2','12层北侧UPS1','12层北侧UPS2','地下室南侧UPS1','地下室南侧UPS2','地下室1号UPS','地下室2号UPS','北侧电源间梅兰UPS-1','北侧电源间梅兰UPS-2','南侧电源间爱默生UPS-1','南侧电源间爱默生UPS-2')");
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
					最大阀值
				</td>
				<td>
				     最小阀值
				</td>			
			</tr>
			<%
				if(list!=null&&list.size()>0){
			           for(int i=0;i<list.size();i++){
			             DevMonitorpdBean bean=(DevMonitorpdBean)list.get(i);
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
